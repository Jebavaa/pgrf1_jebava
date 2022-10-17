package rasterOps;

import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;

/**
 * Represents an algorithm for drawing lines on a {@link rasterData.RasterImage} with pixels of the given type
 * @param <P> pixel type
 */
public interface Liner<P> {

    /**
     * Draws a line onto the given {@link  RasterImage} with the given pixel
     * @param img {@link RasterImage} to be used for drawing
     * @param x1 column address of the start point
     * @param y1 row address of the start point
     * @param x2 column address of the end point
     * @param y2 row address of the end point
     * @param pixelValue new pixel value
     */
    void drawLine(final @NotNull RasterImage<P> img, final int x1,
                  final int y1, final int x2, final int y2, final @NotNull P pixelValue);
}