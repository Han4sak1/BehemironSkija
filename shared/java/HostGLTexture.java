package io.github.humbleui.skija;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.*;

/**
 * GL texture supplied by an embedding host.
 */
@AllArgsConstructor @Data
public final class HostGLTexture implements HostTexture {
    private final int _width;
    private final int _height;
    private final int _target;
    private final int _textureId;
    private final int _textureFormat;
    private final boolean _mipmapped;
    private final boolean _isProtected;
    private final SurfaceOrigin _surfaceOrigin;
    private final ColorType _colorType;
    private final ColorAlphaType _colorAlphaType;
    private final ColorSpace _colorSpace;

    public HostGLTexture(int width, int height, int target, int textureId, int textureFormat) {
        this(width, height, target, textureId, textureFormat, false, false, SurfaceOrigin.BOTTOM_LEFT, ColorType.RGBA_8888, ColorAlphaType.PREMUL, null);
    }

    @Override
    public @NotNull HostBackend getBackend() {
        return HostBackend.GL;
    }

    @Override
    public BackendTexture makeBackendTexture() {
        return BackendTexture.makeGL(_width, _height, _mipmapped, new GLTextureInfo(_target, _textureId, _textureFormat, _isProtected));
    }
}
