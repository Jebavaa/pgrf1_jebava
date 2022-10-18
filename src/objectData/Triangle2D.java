package objectData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Triangle2D {

    private final ArrayList<Point2D> pointsList = new ArrayList<Point2D>();
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

    public void addPoint2D(final @NotNull Point2D point) {
        pointsList.add(point);
    }
}
