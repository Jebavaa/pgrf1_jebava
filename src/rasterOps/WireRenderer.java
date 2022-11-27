package rasterOps;

import objectData.Object3D;
import objectData.Polygon2D;
import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;
import transforms.Mat4;
import transforms.Mat4PerspRH;
import transforms.Point3D;
import transforms.Vec3D;

import java.util.List;

public class WireRenderer<P>
{

    private Liner<P> liner;
    private int hi,wi;
    private Mat4PerspRH projectionMatrix;
    private Object3D object3D;
    private P pixelValue;
    private RasterImage<P> img;

    private Mat4 view;

    public WireRenderer(final @NotNull RasterImage<P> img, final @NotNull P pixelValue,
                        Liner<P> liner, Mat4 view, Mat4PerspRH projectionMatrix) {
        this.liner = liner;
        this.projectionMatrix = projectionMatrix;
        this.view = view;
        this.img = img;
        this.pixelValue = pixelValue;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void renderScene(List<Object3D> scene){
        for(Object3D object:scene){
            renderObject(object);
        }
    }

    public void renderObject(Object3D object3D){
        int index1,index2;
        Point3D p1,p2;

        for(int i = 0; i < object3D.getIb().size(); i+=2){
            index1 = object3D.getIb().get(i);
            index2 = object3D.getIb().get(i + 1);

            p1 = object3D.getVb().get(index1);
            p2 = object3D.getVb().get(index2);

            p1 = p1.mul(object3D.getModel()).mul(view).mul(projectionMatrix);
            p2 = p2.mul(object3D.getModel()).mul(view).mul(projectionMatrix);

            //dehomogenizace
            Point3D aDehomog = p1.mul(1/p1.getW());
            Point3D bDehomog = p2.mul(1/p2.getW());

            Vec3D v1 = transfromToWindow(new Vec3D(aDehomog));
            Vec3D v2 = transfromToWindow(new Vec3D(bDehomog));

            liner.drawLine(img, (int)Math.round(v1.getX()),(int)Math.round(v1.getY()),
                    (int)Math.round(v2.getX()),(int)Math.round(v2.getY()), pixelValue);

        }
    }

    private Vec3D transfromToWindow(Vec3D point){
        return point.mul(new Vec3D(1,-1,1))
                .add(new Vec3D(1,1,0))
                .mul(new Vec3D((800-1)/2,(600-1)/2,1)) //TODO raster rozměry

                ;
    }
}
