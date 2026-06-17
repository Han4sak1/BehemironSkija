package io.github.humbleui.skija;

import org.jetbrains.annotations.*;

/**
 * Convenience factory for contexts and surface caches used by embedding hosts.
 */
public final class HostDirectContext {
    private HostDirectContext() {
    }

    @NotNull
    public static DirectContext makeGL() {
        return DirectContext.makeGL();
    }

    @NotNull
    public static DirectContext makeVulkan(long instancePtr,
                                           long physicalDevicePtr,
                                           long devicePtr,
                                           long queuePtr,
                                           int graphicsQueueIndex,
                                           long instanceProcAddr,
                                           long getProcAddr,
                                           int apiVersion) {
        return DirectContext.makeVulkan(instancePtr, physicalDevicePtr, devicePtr, queuePtr, graphicsQueueIndex, instanceProcAddr, getProcAddr, apiVersion);
    }

    @NotNull
    public static HostSurfaceCache newSurfaceCache(@NotNull DirectContext context) {
        return new HostSurfaceCache(context);
    }
}
