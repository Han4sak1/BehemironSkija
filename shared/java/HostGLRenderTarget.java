package io.github.humbleui.skija;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.*;

/**
 * GL framebuffer supplied by an embedding host.
 */
@AllArgsConstructor @Data
public final class HostGLRenderTarget implements HostRenderTarget {
    private final int _width;
    private final int _height;
    private final int _framebufferId;
    private final int _framebufferFormat;
    private final int _sampleCount;
    private final int _stencilBits;
    private final SurfaceOrigin _surfaceOrigin;
    private final ColorType _colorType;
    private final ColorSpace _colorSpace;
    private final SurfaceProps _surfaceProps;

    public HostGLRenderTarget(int width, int height, int framebufferId, int framebufferFormat) {
        this(width, height, framebufferId, framebufferFormat, 0, 8, SurfaceOrigin.BOTTOM_LEFT, ColorType.RGBA_8888, null, null);
    }

    @Override
    public @NotNull HostBackend getBackend() {
        return HostBackend.GL;
    }

    @Override
    public BackendRenderTarget makeBackendRenderTarget() {
        return BackendRenderTarget.makeGL(_width, _height, _sampleCount, _stencilBits, _framebufferId, _framebufferFormat);
    }

    @Override
    public boolean matches(@NotNull HostRenderTarget other) {
        if (!(other instanceof HostGLRenderTarget)) {
            return false;
        }
        HostGLRenderTarget target = (HostGLRenderTarget) other;
        return HostRenderTarget.super.matches(other)
            && _framebufferId == target._framebufferId
            && _framebufferFormat == target._framebufferFormat
            && _stencilBits == target._stencilBits;
    }
}
