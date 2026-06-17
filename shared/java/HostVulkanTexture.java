package io.github.humbleui.skija;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.*;

/**
 * Vulkan image used as a texture by an embedding host.
 */
@AllArgsConstructor @Data
public final class HostVulkanTexture implements HostTexture {
    private final int _width;
    private final int _height;
    private final VkImageInfo _imageInfo;
    private final SurfaceOrigin _surfaceOrigin;
    private final ColorType _colorType;
    private final ColorAlphaType _colorAlphaType;
    private final ColorSpace _colorSpace;

    public HostVulkanTexture(int width, int height, VkImageInfo imageInfo, SurfaceOrigin surfaceOrigin, ColorType colorType, ColorAlphaType colorAlphaType) {
        this(width, height, imageInfo, surfaceOrigin, colorType, colorAlphaType, null);
    }

    @Override
    public @NotNull HostBackend getBackend() {
        return HostBackend.VULKAN;
    }

    @Override
    public BackendTexture makeBackendTexture() {
        return BackendTexture.makeVulkan(_width, _height, _imageInfo);
    }
}
