package rasterOps;

import objectData.Point2D;
import objectData.Polygon2D;
import objectData.Triangle2D;
import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;
import rasterOps.Liner;

import javax.sound.sampled.Line;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Trianglener<P> {


    public void drawTriangle(final @NotNull Triangle2D triangle2D, final @NotNull RasterImage<P> img,
                             final @NotNull P pixelValue, final @NotNull Liner<P> liner) {

        Point2D[] points = triangle2D.getPoints();

        if(points[2] == null) {
            liner.drawLine(img, points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY(), pixelValue);
        }
        else
        {
            liner.drawLine(img, points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY(), pixelValue);
            liner.drawLine(img, points[1].getX(), points[1].getY(), points[2].getX(), points[2].getY(), pixelValue);
            liner.drawLine(img, points[2].getX(), points[2].getY(), points[0].getX(), points[0].getY(), pixelValue);
        }


    }

    public void drawFuturePoint(final @NotNull Triangle2D triangle2D, final @NotNull RasterImage<P> img,
                                final @NotNull P pixelValue, final @NotNull Liner<P> liner, final @NotNull Point2D futurePoint)
    {
        Point2D[] points = triangle2D.getPoints();

        liner.drawLine(img, points[1].getX(), points[1].getY(), futurePoint.getX(), futurePoint.getY(), pixelValue);
        liner.drawLine(img, futurePoint.getX(), futurePoint.getY(), points[0].getX(), points[0].getX(), pixelValue);
    }
}
