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
                     final @NotNull Polygoner<P> polygoner,
                     final @NotNull Liner<P> liner, final @NotNull P polygonPixel) {

        final @NotNull List<Point2D> points = polygon.getPointsAsList();
        @NotNull List<Edge> edges = polygon.getEdges();

        List<Edge> newEdges = new ArrayList<Edge>();

        for (Edge edge: edges)
        {
            if (!edge.isHorizontal())
            {
                newEdges.add(edge.oriented().shorten());
            }
        }

        edges.clear();
        edges = newEdges;


        Point2D minMax = findMaxMin(edges); // x je min, y je max (ve skutečnosti jsou obě y)

        List<Point2D> intersections  = new ArrayList<Point2D>();

        for (Edge edge:edges) {

            for (int i = minMax.getX(); i < minMax.getY(); i++) {
                if(edge.hasIntersection(i))
                {
                    intersections.add(new Point2D(edge.intersect(i), i));
                }
            }
        }

        for (int i = 0; i<intersections.size();i++)
        {
            if (i % 2 == 0)
            {
                liner.drawLine(img,intersections.get(i).getX(), intersections.get(i).getY(),
                        intersections.get(i + 1).getX(), intersections.get(i + 1).getY(), polygonPixel);
            }
        }
        polygoner.drawPolygon(polygon, img, polygonPixel, liner);

        // fill the list with edges, remove horizontal lines, orient all edges, shorten by one pixel
        // find yMax, yMin, for each y in range (yMin, yMax), initialize List of intersections, for each edge from edges
        // if intersection exits calculate intersection and add to our list, sort list of intersections
        // draw lines between odd and even intersections
        // draw polygon

    }

    public Point2D findMaxMin(List<Edge> edges)
    {

        Point2D minMax;
        int min = edges.get(0).getY1();
        int max = edges.get(0).getY1();

        for (Edge edge: edges
             ) {
            if(edge.getY1() > max)
            {
                max = edge.getY1();
            }
            if(edge.getY2() > max)
            {
                max = edge.getY2();
            }
            if(edge.getY1() < min)
            {
                min = edge.getY1();
            }
            if(edge.getY2() < min)
            {
                min = edge.getY2();
            }
        }

        minMax = new Point2D(min, max);
        return minMax;
    }



}
