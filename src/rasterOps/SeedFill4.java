package rasterOps;

import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;

import java.util.Optional;
import java.util.function.Predicate;

public class SeedFill4<P> implements SeedFill<P> {

    @Override
    public void fill(@NotNull RasterImage<P> img, int c, int r,
                     @NotNull P pixelValue, @NotNull Predicate<P> isInArea) {
        // Get pixel value at pixel adress (c, r)
        // Test if pixel lies in area
        // If so, set the pixel at (c, r) to the new pixelValue
        // Recursively for neighbourhood

        Optional<P> currentPixel = img.getPixel(c, r);
        if (currentPixel.isEmpty() || !isInArea.test(currentPixel.get())) {
            return;
        }

        img.setPixel(c, r, pixelValue);
        fill(img, c + 1, r, pixelValue, isInArea);
        fill(img, c, r + 1, pixelValue, isInArea);
        fill(img, c - 1, r, pixelValue, isInArea);
        fill(img, c, r - 1, pixelValue, isInArea);
    }
}
