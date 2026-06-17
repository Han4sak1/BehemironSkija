package io.github.humbleui.skija;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.*;

/**
 * Frame-scoped drawing surface wrapping a host render target.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class HostSurface implements AutoCloseable {
    private final HostSurfaceCache _owner;
    private final DirectContext _context;
    private final BackendRenderTarget _renderTarget;
    private final Surface _surface;
    private boolean _closed;

    @NotNull
    public Canvas getCanvas() {
        return _surface.getCanvas();
    }

    public void flush() {
        _context.flush(_surface);
    }

    public void flushAndSubmit() {
        _context.flushAndSubmit(_surface);
    }

    public void flushAndSubmit(boolean syncCpu) {
        _context.flushAndSubmit(_surface, syncCpu);
    }

    @Override
    public void close() {
        if (_closed) {
            return;
        }
        _closed = true;
        _surface.close();
        _renderTarget.close();
        _owner.onHostSurfaceClosed(this);
    }
}
