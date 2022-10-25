package rasterOps;

import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;

import java.util.function.Predicate;

public class SeedFillQueue<P> implements SeedFill<P> {
    @Override
    public void fill(@NotNull RasterImage<P> img, int c, int r,
                     @NotNull P pixelValue, @NotNull Predicate<P> isInArea) {

        // Intialize an empty queue
        // Insert seed into the queue
        // While queue not empty
        // Test if pixel lies in area
        // Set current pixel to the new value
        // Insert all neighbours into the queue

    }
}
