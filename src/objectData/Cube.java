package objectData;

import org.jetbrains.annotations.NotNull;
import transforms.Mat3;
import transforms.Mat4;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cube extends Object3D
{
public Cube()
{
    // Geometrie
    vb.add(new Point3D(-1, -1, 1));
    vb.add(new Point3D(1, -1, 1));
    vb.add(new Point3D(1, 1, 1));
    vb.add(new Point3D(-1, 1, 1));

    vb.add(new Point3D(-1, -1, -1));
    vb.add(new Point3D(1, -1, -1));
    vb.add(new Point3D(1, 1, -1));
    vb.add(new Point3D(-1, 1, -1));

    // Topologie
    ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 1, 2, 2, 3, 3, 0, 4, 5, 5, 6, 6, 7, 7, 4, 7, 3, 4, 0, 5, 1, 2, 6));
    addIndices(list);
}
}
