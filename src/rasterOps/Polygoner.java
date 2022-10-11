package rasterOps;

import objectData.Point2D;
import objectData.Polygon2D;
import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;

import java.util.Iterator;
import java.util.Optional;

public class Polygoner<P> {


    public void drawPolygon(final @NotNull Polygon2D polygon, final @NotNull RasterImage<P> img,
                            final @NotNull P pixelValue, final @NotNull Liner<P> liner) {


        Iterator<Point2D> point2DIterator = polygon.getPoints().iterator();
        Optional<Point2D> lastEnd = Optional.empty();
        boolean iterate = point2DIterator.hasNext();

        while (iterate) {
            Point2D a = lastEnd.isPresent() ? point2DIterator.next() : lastEnd.get();
            Point2D b;

            if (point2DIterator.hasNext()) {
                b = point2DIterator.next();
            } else {
                b = polygon.getPoints().get(0);
                iterate = false;
            }

            lastEnd = Optional.of(b);
            liner.drawLine(img, a.getX(), a.getY(), b.getX(), b.getY(), pixelValue);
        }
    }
}
