package objectData;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Object3D
{
    protected List<Point3D> vb = new ArrayList<>();
    protected List<Integer> ib = new ArrayList<>();

    private Mat4 model = new Mat4Identity();

    public Mat4 getModel() {
        return model;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public List<Point3D> getVb() {
        return vb;
    }

    public List<Integer> getIb() {
        return ib;
    }

    protected void addIndices(ArrayList<Integer> indices) {
        ib.addAll(indices);
    }
}
