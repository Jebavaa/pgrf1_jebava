package rasterOps;

import org.jetbrains.annotations.NotNull;
import rasterData.*;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Algorithm capable of filling a polygon in a {@link RasterImage} defined by a seed address and isInArea function
 * @param <P> pixel type
 */
public interface SeedFill<P> {

    /**
     * Fills a polygon in a {@link RasterImage}
     * @param img Rasterimage used for filling
     * @param x seed column address
     * @param y seed row address
     * @param pixelValue pixel value used for filling
     * @param BGColour colour of the background
     */
    void fill(final @NotNull RasterImage<P> img, int x, int y,
              @NotNull Optional<P> pixelValue, @NotNull P BGColour);


}
