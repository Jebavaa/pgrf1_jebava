package rasterOps;

import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;

public interface Liner<P> {


    void drawLine(final @NotNull RasterImage<P> img, final int x1,
                  final int y1, final int x2, final int y2, final @NotNull P pixelValue);
}