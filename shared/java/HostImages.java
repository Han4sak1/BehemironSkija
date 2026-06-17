package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

/**
 * Image helpers for textures supplied by an embedding host.
 */
public final class HostImages {
    private HostImages() {
    }

    @NotNull
    public static Image borrowTexture(@NotNull DirectContext context, @NotNull HostTexture texture) {
        return borrowTexture(context, texture, null);
    }

    @NotNull
    public static Image borrowTexture(@NotNull DirectContext context, @NotNull HostTexture texture, @Nullable Runnable releaseProc) {
        try (BackendTexture backendTexture = texture.makeBackendTexture()) {
            return Image.borrowTextureFrom(
                context,
                backendTexture,
                texture.getSurfaceOrigin(),
                texture.getColorType(),
                texture.getColorAlphaType(),
                texture.getColorSpace(),
                releaseProc
            );
        }
    }

}
