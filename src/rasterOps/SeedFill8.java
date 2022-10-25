package rasterOps;

import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;

import java.util.Optional;
import java.util.function.Predicate;

public class SeedFill8<P> implements SeedFill<P> {

    @Override
    public void fill(@NotNull RasterImage<P> img, int x, int y,
                     @NotNull P pixelValue, @NotNull Predicate<P> isInArea) {
        // Get pixel value at pixel adress (c, r)
        // Test if pixel lies in area
        // If so, set the pixel at (c, r) to the new pixelValue
        // Recursively for neighbourhood

        Optional<P> currentPixel = img.getPixel(x, y);
        if (currentPixel.isEmpty() || !isInArea.test(currentPixel.get())) {
            return;
        }

        img.setPixel(x, y, pixelValue);
        fill(img, x + 1, y, pixelValue, isInArea);
        fill(img, x, y + 1, pixelValue, isInArea);
        fill(img, x - 1, y, pixelValue, isInArea);
        fill(img, x, y - 1, pixelValue, isInArea);

        fill(img, x + 1, y - 1, pixelValue, isInArea);
        fill(img, x - 1, y + 1, pixelValue, isInArea);
        fill(img, x + 1, y + 1, pixelValue, isInArea);
        fill(img, x, y - 1, pixelValue, isInArea);
    }
}
