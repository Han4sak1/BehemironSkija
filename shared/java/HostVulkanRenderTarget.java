package io.github.humbleui.skija;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.*;

/**
 * Vulkan image supplied by an embedding host.
 */
@AllArgsConstructor @Data
public final class HostVulkanRenderTarget implements HostRenderTarget {
    private final int _width;
    private final int _height;
    private final VkImageInfo _imageInfo;
    private final SurfaceOrigin _surfaceOrigin;
    private final ColorType _colorType;
    private final ColorSpace _colorSpace;
    private final SurfaceProps _surfaceProps;

    public HostVulkanRenderTarget(int width, int height, VkImageInfo imageInfo, SurfaceOrigin surfaceOrigin, ColorType colorType) {
        this(width, height, imageInfo, surfaceOrigin, colorType, null, null);
    }

    @Override
    public @NotNull HostBackend getBackend() {
        return HostBackend.VULKAN;
    }

    @Override
    public int getSampleCount() {
        return _imageInfo.getSampleCount();
    }

    @Override
    public BackendRenderTarget makeBackendRenderTarget() {
        return BackendRenderTarget.makeVulkan(_width, _height, _imageInfo);
    }

    @Override
    public boolean matches(@NotNull HostRenderTarget other) {
        if (!(other instanceof HostVulkanRenderTarget)) {
            return false;
        }
        HostVulkanRenderTarget target = (HostVulkanRenderTarget) other;
        return HostRenderTarget.super.matches(other)
            && _imageInfo.equals(target._imageInfo);
    }
}
