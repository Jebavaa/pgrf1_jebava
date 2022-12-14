package rasterOps;

import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;

import java.util.Optional;
import java.util.function.Predicate;

public class SeedFill8<P> implements SeedFill<P> {

    @Override
    public void fill(@NotNull RasterImage<P> img, int x, int y,
                     @NotNull Optional<P> pixelValue, @NotNull P BGColour) {

        // Get pixel value at pixel adress (x, y)
        // Test if pixel lies in area
        // If so, set the pixel at (x, y) to the new pixelValue
        // Recursively for neighbourhood

        if(x < 0 || y < 0 || y > img.getHeight() || x > img.getWidth())
        {
            return;
        }
        if(pixelValue.isPresent())
            img.setPixel(x, y, pixelValue.get());
        if(img.getPixel(x+1, y).equals(BGColour))
        fill(img, x + 1, y, pixelValue, BGColour);
        if(img.getPixel(x, y+1).equals(BGColour))
        fill(img, x, y + 1, pixelValue, BGColour);
        if(img.getPixel(x-1, y).equals(BGColour))
        fill(img, x - 1, y, pixelValue, BGColour);
        if(img.getPixel(x, y-1).equals(BGColour))
        fill(img, x, y - 1, pixelValue, BGColour);

        if(img.getPixel(x+1, y).equals(BGColour) || img.getPixel(x, y-1).equals(BGColour))
        fill(img, x + 1, y - 1, pixelValue, BGColour);
        if(img.getPixel(x-1, y).equals(BGColour) || img.getPixel(x, y+1).equals(BGColour))
        fill(img, x - 1, y + 1, pixelValue, BGColour);
        if(img.getPixel(x+1, y).equals(BGColour) ||img.getPixel(x, y+1).equals(BGColour))
        fill(img, x + 1, y + 1, pixelValue, BGColour);
        if(img.getPixel(x, y-1).equals(BGColour) ||img.getPixel(x-1, y).equals(BGColour))
        fill(img, x-1, y - 1, pixelValue, BGColour);
    }

}
