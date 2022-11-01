package objectData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Polygon2D {

    private final @NotNull List<Point2D> pointsList = new ArrayList<>();
    
    private Point2D[] pointsField;

    public Point2D[] getPoints() {
        int i = 0;
        pointsField = new Point2D[pointsList.size()];
        for (Point2D point : pointsList) {
            pointsField[i] = point;
            i++;
        }
        return pointsField;
    }

    public List<Point2D> getPointsAsList()
    {
        return pointsList;
    }

    public List<Edge> getEdges(){
        List<Edge> edgeList = new ArrayList<Edge>();
        Point2D[] points = this.getPoints();

        for (int i = 0; i < points.length; i++) {
            if(i + 1 != points.length) {
                Edge edge = new Edge(points[i], points[i + 1]);
                edgeList.add(edge);
            }
            else
            {
                Edge edge = new Edge(points[i], points[0]);
                edgeList.add(edge);
            }

        }
        return edgeList;
    }

    public void addPoint2D(final @NotNull Point2D point) {
        pointsList.add(point);
    }
}
