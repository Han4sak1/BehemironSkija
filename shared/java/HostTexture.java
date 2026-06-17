package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

/**
 * Describes an immutable texture supplied by an embedding host.
 *
 * <p>The bridge only wraps host-owned GPU objects. It does not take ownership of the
 * underlying texture, image, memory, or synchronization primitives.</p>
 */
public interface HostTexture {
    @NotNull HostBackend getBackend();

    int getWidth();

    int getHeight();

    @NotNull SurfaceOrigin getSurfaceOrigin();

    @NotNull ColorType getColorType();

    @NotNull ColorAlphaType getColorAlphaType();

    @Nullable ColorSpace getColorSpace();

    @NotNull BackendTexture makeBackendTexture();
}
