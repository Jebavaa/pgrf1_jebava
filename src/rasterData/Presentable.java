package rasterData;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a data structure capable of presenting itself in a device on the given type
 * @param <D> device type
 */
public interface Presentable<D> {

    /**
     * Present itself in the given device
     * @param device
     * @return
     */
    @NotNull D present(@NotNull final D device);
}
