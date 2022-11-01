package objectData;

import objectData.Point2D;
import org.jetbrains.annotations.NotNull;

public class Edge {

    private final @NotNull Point2D start;
    private final @NotNull Point2D end;

    int x1, x2, y1, y2;
    double k, q;

    public Edge(final @NotNull Point2D start, final @NotNull Point2D end) {
        this.start = start;
        this.end = end;
        x1 = start.getX();
        y1 = start.getY();
        x2 = end.getX();
        y2 = end.getY();
        k = (y2 - y1) / (double) (x2 - x1);
        q = y1 - k * x1;
    }

    /**
     * Returns an Edge such that the y coordinate of its start point <= y coordinate of its end point
     * @return an oriented Edge
     */
    public @NotNull Edge oriented() {
        if (start.getY() > end.getY()) {
            return new Edge(end, start);
        }
        return this;
    }

    /**
     * Returns whether this Edge intersects with y row, assumes an {@link Edge#oriented} Edge
     * @param y y row
     * @return a boolean indicating whether an intersection exists
     */
    public boolean hasIntersection(final int y) {
        return start.getY() <= y && y <= end.getY();
    }

    /**
     * Returns this Edge shortened by one pixel, assumes start.y != end.y
     * @return a new Edge shorter by one pixel
     */
    public @NotNull Edge shorten() {
        if(start.getX() == end.getX()) {
            return new Edge(start, new Point2D(end.getX(), end.getY() - 1));
        }
        return new Edge(start, new Point2D((int) Math.round(((y2 - 1) - q)/k), end.getY() - 1));

    }

    /**
     * Returns the x coordinate of the intersection of this Edge with y
     * @return a single value representing the intersection's x coordinate
     */
    public int intersect(final int y) {
        if(start.getX() == end.getX()) {
            return start.getX();
        }
        int x = (int) Math.round((y - q)/k);
        return x;
    }

    /**
     * Returns whether this edge is horizontal
     * @return
     */
    public boolean isHorizontal()
    {
        return start.getY() ==  end.getY();
    }

    public int getY1()
    {
        return start.getY();
    }
    public int getY2()
    {
        return end.getY();
    }
    public int getX1()
    {
        return start.getX();
    }
    public int getX2()
    {
        return end.getX();
    }
}
