package rasterData;

import org.jetbrains.annotations.NotNull;

public interface Presentable<D> {

    @NotNull D present(@NotNull final D device);
}
