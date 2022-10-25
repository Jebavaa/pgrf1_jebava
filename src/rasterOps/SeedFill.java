package rasterOps;

import org.jetbrains.annotations.NotNull;
import rasterData.*;

import java.util.function.Predicate;

/**
 * Algorithm capable of filling a polygon in a {@link RasterImage} defined by a seed address and isInArea function
 * @param <P> pixel type
 */
public interface SeedFill<P> {

    /**
     * Fills a polygon in a {@link RasterImage}
     * @param img Rasterimage used for filling
     * @param c seed column address
     * @param r seed row address
     * @param pixelValue pixel value used for filling
     * @param isInArea predicate deciding whether a pixel lies inside the polygon
     */
    void fill(final @NotNull RasterImage<P> img, int c, int r,
              @NotNull P pixelValue, @NotNull Predicate<P> isInArea);



}
