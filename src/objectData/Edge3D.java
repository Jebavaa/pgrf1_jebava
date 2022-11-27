package objectData;

import objectData.Point2D;
import org.jetbrains.annotations.NotNull;
import transforms.Point3D;

public class Edge3D {

    private final @NotNull Point3D start;
    private final @NotNull Point3D end;

    double x1, x2, y1, y2, z1, z2;
    double k, q;

    public Edge3D(final @NotNull Point3D start, final @NotNull Point3D end) {
        this.start = start;
        this.end = end;
        x1 = start.getX();
        y1 = start.getY();
        z1 = start.getZ();
        x2 = end.getX();
        y2 = end.getY();
        z2 = end.getZ();
        k = (y2 - y1) / (double) (x2 - x1);
        q = y1 - k * x1;
    }
}
