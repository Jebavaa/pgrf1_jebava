package objectData;

import objectData.Point2D;
import org.jetbrains.annotations.NotNull;

public class Edge {

    private final @NotNull Point2D start;
    private final @NotNull Point2D end;

    public Edge(final @NotNull Point2D start, final @NotNull Point2D end) {
        this.start = start;
        this.end = end;
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
    public @NotNull Edge shortened() {
        // TODO
        return new Edge(new Point2D(0,0), new Point2D(0,0));
    }

    /**
     * Returns the x coordinate of the intersection of this Edge with y
     * @return a single value representing the intersection's x coordinate
     */
    public int intersect(final int y) {
        // TODO
        return 0;
    }
}
