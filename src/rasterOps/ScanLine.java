package rasterOps;

import objectData.Polygon2D;
import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;
import rasterOps.Liner;
import rasterOps.Polygoner;

public interface ScanLine<P> {


    void fill(final @NotNull RasterImage<P> img, final @NotNull Polygon2D polygon,
              final @NotNull P areaPixel, final @NotNull Polygoner<P> polygoner,
              final @NotNull Liner<P> liner, final @NotNull P polygonPixel);

}
