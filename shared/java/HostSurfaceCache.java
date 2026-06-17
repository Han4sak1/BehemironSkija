package io.github.humbleui.skija;

import lombok.Getter;
import org.jetbrains.annotations.*;

/**
 * Reuses a Skia surface for repeated draws into the same host render target.
 *
 * <p>Targets passed to this cache must be immutable descriptors. Use a new descriptor
 * when the host framebuffer or image changes.</p>
 */
public final class HostSurfaceCache implements AutoCloseable {
    @Getter
    private final DirectContext _context;
    private HostRenderTarget _target;
    private HostSurface _hostSurface;

    public HostSurfaceCache(@NotNull DirectContext context) {
        assert context != null : "Can't create HostSurfaceCache with context == null";
        _context = context;
    }

    @NotNull
    public HostSurface begin(@NotNull HostRenderTarget target) {
        assert target != null : "Can't begin HostSurfaceCache with target == null";
        if (_hostSurface == null || _target == null || !_target.matches(target)) {
            replace(target);
        }
        return _hostSurface;
    }

    public void end() {
        if (_hostSurface != null) {
            _hostSurface.flush();
        }
    }

    public void endAndSubmit() {
        endAndSubmit(false);
    }

    public void endAndSubmit(boolean syncCpu) {
        if (_hostSurface != null) {
            _hostSurface.flushAndSubmit(syncCpu);
        }
    }

    @Nullable
    public HostSurface getCurrentSurface() {
        return _hostSurface;
    }

    private void replace(@NotNull HostRenderTarget target) {
        closeSurface();
        BackendRenderTarget renderTarget = target.makeBackendRenderTarget();
        try {
            Surface surface = Surface.wrapBackendRenderTarget(
                _context,
                renderTarget,
                target.getSurfaceOrigin(),
                target.getColorType(),
                target.getColorSpace(),
                target.getSurfaceProps());
            _target = target;
            _hostSurface = new HostSurface(this, _context, renderTarget, surface);
        } catch (RuntimeException | Error e) {
            renderTarget.close();
            throw e;
        }
    }

    private void closeSurface() {
        HostSurface hostSurface = _hostSurface;
        if (hostSurface != null) {
            _hostSurface = null;
            _target = null;
            hostSurface.close();
        }
    }

    void onHostSurfaceClosed(@NotNull HostSurface hostSurface) {
        if (_hostSurface == hostSurface) {
            _hostSurface = null;
            _target = null;
        }
    }

    @Override
    public void close() {
        closeSurface();
    }
}
