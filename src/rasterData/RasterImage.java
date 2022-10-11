package rasterData;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface RasterImage<P> {

    int getWidth();

    int getHeight();

    Optional<P> getPixel(final int x, final int y);

    void setPixel(final int x, final int y, final @NotNull P newValue);

    void clear(final @NotNull P newValue);

    void setRGB(final int x, final int y, final @NotNull P newValue);

}
