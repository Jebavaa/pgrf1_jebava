package rasterOps;

import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;


public class SeedFill4<P> implements SeedFill<P> {

    @Override
    public void fill(@NotNull RasterImage<P> img, int x, int y,
                     @NotNull P pixelValue, @NotNull P BGColour) {


        // Get pixel value at pixel address (x, y)
        // Test if pixel lies in area
        // If so, set the pixel at (x, y) to the new pixelValue
        // Recursively for neighbourhood

        if(x < 0 || y < 0 || y > img.getHeight() || x > img.getWidth())
        {
            return;
        }

        img.setPixel(x, y, pixelValue);
        if(img.getPixel(x+1, y).equals(BGColour))
        {
            fill(img, x + 1, y, pixelValue, BGColour);
        }
        if(img.getPixel(x, y+1).equals(BGColour))
        {
            fill(img, x, y + 1, pixelValue, BGColour);
        }
        if(img.getPixel(x-1, y).equals(BGColour))
        {
            fill(img, x - 1, y, pixelValue, BGColour);
        }
        if(img.getPixel(x, y-1).equals(BGColour))
        {
            fill(img, x , y-1, pixelValue, BGColour);
        }
    }

    public void fillPatern(@NotNull RasterImage<P> img, int x, int y,
                     @NotNull P pixelValue,@NotNull P pixelValue2, @NotNull P BGColour) {


        // Get pixel value at pixel address (x, y)
        // Test if pixel lies in area
        // If so, set the pixel at (x, y) to the new pixelValue
        // Recursively for neighbourhood

        if(x < 0 || y < 0 || y > img.getHeight() || x > img.getWidth())
        {
            return;
        }


        if(img.getPixel(x, y).equals(BGColour))
        img.setPixel(x, y, pixelValue);
        if(img.getPixel(x+1, y).equals(BGColour))
        img.setPixel(x+1, y, pixelValue);
        if(img.getPixel(x+2, y).equals(BGColour))
        img.setPixel(x+2, y, pixelValue);
        if(img.getPixel(x+3, y).equals(BGColour))
        img.setPixel(x+3, y, pixelValue);
        if(img.getPixel(x+4, y).equals(BGColour))
        img.setPixel(x+4, y, pixelValue);

        if(img.getPixel(x, y+1).equals(BGColour))
        img.setPixel(x, y+1, pixelValue);
        if(img.getPixel(x+1, y+1).equals(BGColour))
        img.setPixel(x+1, y+1, pixelValue2);
        if(img.getPixel(x+2, y+1).equals(BGColour))
        img.setPixel(x+2, y+1, pixelValue2);
        if(img.getPixel(x+3, y+1).equals(BGColour))
        img.setPixel(x+3, y+1, pixelValue2);
        if(img.getPixel(x+4, y+1).equals(BGColour))
        img.setPixel(x+4, y+1, pixelValue);

        if(img.getPixel(x, y+2).equals(BGColour))
        img.setPixel(x, y+2, pixelValue);
        if(img.getPixel(x+1, y+2).equals(BGColour))
        img.setPixel(x+1, y+2, pixelValue2);
        if(img.getPixel(x+2, y+2).equals(BGColour))
        img.setPixel(x+2, y+2, pixelValue);
        if(img.getPixel(x+3, y+2).equals(BGColour))
        img.setPixel(x+3, y+2, pixelValue2);
        if(img.getPixel(x+4, y+2).equals(BGColour))
        img.setPixel(x+4, y+2, pixelValue);

        if(img.getPixel(x, y+3).equals(BGColour))
        img.setPixel(x, y+3, pixelValue);
        if(img.getPixel(x+1, y+3).equals(BGColour))
        img.setPixel(x+1, y+3, pixelValue2);
        if(img.getPixel(x+2, y+3).equals(BGColour))
        img.setPixel(x+2, y+3, pixelValue2);
        if(img.getPixel(x+3, y+3).equals(BGColour))
        img.setPixel(x+3, y+3, pixelValue2);
        if(img.getPixel(x+4, y+3).equals(BGColour))
        img.setPixel(x+4, y+3, pixelValue);

        if(img.getPixel(x, y+4).equals(BGColour))
        img.setPixel(x, y+4, pixelValue);
        if(img.getPixel(x+1, y+4).equals(BGColour))
        img.setPixel(x+1, y+4, pixelValue);
        if(img.getPixel(x+2, y+4).equals(BGColour))
        img.setPixel(x+2, y+4, pixelValue);
        if(img.getPixel(x+3, y+4).equals(BGColour))
        img.setPixel(x+3, y+4, pixelValue);
        if(img.getPixel(x+4, y+4).equals(BGColour))
        img.setPixel(x+4, y+4, pixelValue);


        if(
        img.getPixel(x, y+5).equals(BGColour) &&
                img.getPixel(x, y+4).equals(BGColour) &&
                img.getPixel(x, y+3).equals(BGColour) &&
                img.getPixel(x, y+2).equals(BGColour) &&
                img.getPixel(x, y+1).equals(BGColour) &&
        img.getPixel(x+5, y).equals(BGColour) ||img.getPixel(x+5, y+5).equals(BGColour) &&
                img.getPixel(x+4, y).equals(BGColour) ||img.getPixel(x+4, y+4).equals(BGColour) &&
                img.getPixel(x+3, y).equals(BGColour) ||img.getPixel(x+3, y+3).equals(BGColour)&&
                img.getPixel(x+2, y).equals(BGColour) ||img.getPixel(x+2, y+2).equals(BGColour)&&
                img.getPixel(x+1, y).equals(BGColour) ||img.getPixel(x+1, y+1).equals(BGColour))
            fillPatern(img, x + 5, y + 5, pixelValue,pixelValue2, BGColour);

        if(
        img.getPixel(x, y-5).equals(BGColour)&&
                img.getPixel(x, y-4).equals(BGColour)&&
                img.getPixel(x, y-3).equals(BGColour)&&
                img.getPixel(x, y-2).equals(BGColour)&&
                img.getPixel(x, y-1).equals(BGColour)&&
        img.getPixel(x-5, y-5).equals(BGColour) ||img.getPixel(x-5, y).equals(BGColour) &&
                img.getPixel(x-4, y-4).equals(BGColour) ||img.getPixel(x-4, y).equals(BGColour) &&
                img.getPixel(x-3, y-3).equals(BGColour) ||img.getPixel(x-3, y).equals(BGColour) &&
                img.getPixel(x-2, y-2).equals(BGColour) ||img.getPixel(x-2, y).equals(BGColour) &&
                img.getPixel(x-1, y-1).equals(BGColour) ||img.getPixel(x-1, y).equals(BGColour))
            fillPatern(img, x-5, y - 5, pixelValue, pixelValue2,BGColour);


        if(img.getPixel(x+5, y-5).equals(BGColour) || img.getPixel(x, y-5).equals(BGColour) &&
                img.getPixel(x+4, y-4).equals(BGColour) || img.getPixel(x, y-4).equals(BGColour) &&
                img.getPixel(x+3, y-3).equals(BGColour) || img.getPixel(x, y-3).equals(BGColour) &&
                img.getPixel(x+2, y-2).equals(BGColour) || img.getPixel(x, y-2).equals(BGColour) &&
                img.getPixel(x+1, y-1).equals(BGColour) || img.getPixel(x, y-1).equals(BGColour) &&
                img.getPixel(x+5, y).equals(BGColour) &&
                img.getPixel(x+4, y).equals(BGColour) &&
                img.getPixel(x+3, y).equals(BGColour) &&
                img.getPixel(x+2, y).equals(BGColour) &&
                img.getPixel(x+1, y).equals(BGColour)


        )
            fillPatern(img, x + 5, y - 5, pixelValue,pixelValue2, BGColour);

        if(img.getPixel(x-4, y+4).equals(BGColour) || img.getPixel(x, y+4).equals(BGColour) &&
                img.getPixel(x-3, y+3).equals(BGColour) || img.getPixel(x, y+3).equals(BGColour) &&
                img.getPixel(x-2, y+2).equals(BGColour) || img.getPixel(x, y+2).equals(BGColour) &&
                img.getPixel(x-1, y+1).equals(BGColour) || img.getPixel(x, y+1).equals(BGColour) &&
                img.getPixel(x-5, y+5).equals(BGColour) || img.getPixel(x, y+5).equals(BGColour) &&
        img.getPixel(x-5, y).equals(BGColour) &&
                img.getPixel(x-4, y).equals(BGColour)&&
                img.getPixel(x-3, y).equals(BGColour)&&
                img.getPixel(x-2, y).equals(BGColour)&&
                img.getPixel(x-1, y).equals(BGColour)
        )
            fillPatern(img, x - 5, y + 5, pixelValue,pixelValue2, BGColour);



    }

}
