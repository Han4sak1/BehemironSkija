package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

/**
 * Describes an immutable render target supplied by an embedding host.
 *
 * <p>The bridge only wraps host-owned GPU objects. It does not take ownership of the
 * underlying framebuffer, image, memory, or synchronization primitives.</p>
 */
public interface HostRenderTarget {
    @NotNull HostBackend getBackend();

    int getWidth();

    int getHeight();

    int getSampleCount();

    @NotNull SurfaceOrigin getSurfaceOrigin();

    @NotNull ColorType getColorType();

    @Nullable ColorSpace getColorSpace();

    @Nullable SurfaceProps getSurfaceProps();

    @NotNull BackendRenderTarget makeBackendRenderTarget();

    default boolean matches(@NotNull HostRenderTarget other) {
        return getBackend() == other.getBackend()
            && getWidth() == other.getWidth()
            && getHeight() == other.getHeight()
            && getSampleCount() == other.getSampleCount()
            && getSurfaceOrigin() == other.getSurfaceOrigin()
            && getColorType() == other.getColorType()
            && java.util.Objects.equals(getColorSpace(), other.getColorSpace())
            && java.util.Objects.equals(getSurfaceProps(), other.getSurfaceProps());
    }
}
