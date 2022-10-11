package rasterOps;

import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;

public class TrivialLiner<P> implements Liner<P> {

    @Override
    public void drawLine(final @NotNull RasterImage<P> img, final int x1, final int y1,
                         final int x2, final int y2, final @NotNull P pixelValue) {
        double k = (y2 - y1) / (double) (x2 - x1);
        double q = y1 - k * x1;
        for (int x = x1; x <= x2; x++) {
            int y = (int) Math.round(k * x + q);
            img.setPixel(x, y, pixelValue);
        }
    }
}
