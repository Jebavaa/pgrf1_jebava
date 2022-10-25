package rasterOps;

import objectData.Edge;
import objectData.Point2D;
import objectData.Polygon2D;
import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;
import rasterOps.Liner;
import rasterOps.Polygoner;
import rasterOps.ScanLine;

import java.util.ArrayList;
import java.util.List;

public class ScanLineImpl<P> implements ScanLine<P> {
    @Override
    public void fill(final @NotNull RasterImage<P> img, final @NotNull Polygon2D polygon,
                     final @NotNull P areaPixel, final @NotNull Polygoner<P> polygoner,
                     final @NotNull Liner<P> liner, final @NotNull P polygonPixel) {

        final @NotNull List<Point2D> points = polygon.getPointsAsList();
        final @NotNull List<Edge> edges = new ArrayList<>();

        // TODO fill the list with edges

    }
}
