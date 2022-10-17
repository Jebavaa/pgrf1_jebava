package rasterData;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Represents 2D raster data with pixels of the given type
 * @param <P> pixel type
 */
public interface RasterImage<P> {

    /**
     * Returns the number of columns
     * @return
     */
    int getWidth();

    /**
     * Returns the number of rows
     * @return
     */
    int getHeight();

    /**
     * Returns the pixel value at the specified address
     * @param x column address
     * @param y row address
     * @return
     */
    Optional<P> getPixel(final int x, final int y);

    /**
     * Overrides the current pixel value with the new one specified by the given pixel address
     * @param x column address
     * @param y row address
     * @param newValue new pixel value
     */
    void setPixel(final int x, final int y, final @NotNull P newValue);

    /**
     * Changes all pixel's values to the given value
     * @param newValue
     */
    void clear(final @NotNull P newValue);

    void setRGB(final int x, final int y, final @NotNull P newValue);

}
