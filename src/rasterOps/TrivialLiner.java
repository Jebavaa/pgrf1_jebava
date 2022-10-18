package rasterOps;

import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;

public class TrivialLiner<P> implements Liner<P> {

    @Override
    public void drawLine(final @NotNull RasterImage<P> img, int x1, int y1,
                         int x2, int y2, final @NotNull P pixelValue) {
        double k = (y2 - y1) / (double) (x2 - x1);
        double q = y1 - k * x1;
        int helper;


        if(x2 < x1)
        {
            helper = x1;
            x1 = x2;
            x2 = helper;
        }

        if(y2 < y1)
        {
            helper = y1;
            y1 = y2;
            y2 = helper;
        }

        if(x2 - x1 == 0)
        {
            for (int y = y1; y <= y2; y++) {
                int x = x1;
                img.setPixel(x, y, pixelValue);
            }
        }
        else if(Math.abs(k) <= 1) {
            for (int x = x1; x <= x2; x++) {
                int y = (int) Math.round(k * x + q);
                img.setPixel(x, y, pixelValue);
            }
        }
        else if(Math.abs(k) > 1)
        {
            for (int y = y1; y <= y2; y++) {
                int x = (int) Math.round((y - q)/k);
                img.setPixel(x, y, pixelValue);
            }
        }
    }
}
