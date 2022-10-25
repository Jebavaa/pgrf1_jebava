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

    public void addPoint2D(final @NotNull Point2D point) {
        pointsList.add(point);
    }
}
