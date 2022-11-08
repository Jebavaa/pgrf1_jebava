package rasterOps;

import objectData.Point2D;
import objectData.Polygon2D;
import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Polygoner<P> {


    public void drawPolygon(final @NotNull Polygon2D polygon, final @NotNull RasterImage<P> img,
                            final @NotNull P pixelValue, final @NotNull Liner<P> liner) {

        //Iterator<Point2D> iterator = new Iterator<Point2D>()

        Point2D[] points = polygon.getPoints();


        for(int i = 0; i < points.length; i++)
        {
            if(i + 1 == points.length)
            {
                liner.drawLine(img, points[i].getX(), points[i].getY(), points[0].getX(), points[0].getY(), pixelValue);
            }
            else
            {
                liner.drawLine(img, points[i].getX(), points[i].getY(), points[i+1].getX(), points[i+1].getY(), pixelValue);
            }
        }

    }

    public void drawFuturePoint(final @NotNull Polygon2D polygon, final @NotNull RasterImage<P> img,
                                final @NotNull P pixelValue, final @NotNull Liner<P> liner, final @NotNull Point2D futurePoint)
    {
        Point2D lastPoint = new Point2D(polygon.getPoints()[polygon.getPoints().length - 1].getX(), polygon.getPoints()[polygon.getPoints().length - 1].getY());

        liner.drawLine(img, lastPoint.getX(), lastPoint.getY(), futurePoint.getX(), futurePoint.getY(), pixelValue);
        liner.drawLine(img, polygon.getPoints()[0].getX(), polygon.getPoints()[0].getY(), futurePoint.getX(), futurePoint.getY(), pixelValue);
    }
}
