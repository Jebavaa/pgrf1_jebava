package objectOps;

import org.jetbrains.annotations.NotNull;
import rasterData.RasterImage;
import rasterOps.Liner;
import rasterOps.TrivialLiner;
import transforms.Camera;
import transforms.Mat4;

public class RendererLineList<P> implements Renderer<P>
{

    private @NotNull Camera camera;
    private @NotNull Mat4 mat4;
    private P pixelValue;
    private final @NotNull RasterImage<P> img;
    private final @NotNull Liner<P> liner;

    public RendererLineList(Camera camera, Mat4 projectionMatrix, P pixelValue, RasterImage<P> img)
    {
        this.camera = camera;
        this.mat4 = projectionMatrix;
        this.pixelValue = pixelValue;
        this.img = img;
        this.liner = new TrivialLiner<P>();
    }

    @Override
    public void renderScene(@NotNull Scene scene)
    {
        for(int i = 0; i < scene.getSolids().size(); i++)
        {
            renderSolid(scene.getSolids().get(i), scene.getModelMats().get(i));
        }
    }

    @Override
    public void renderSolid(@NotNull Solid solid, Mat4 modelMat)
    {
        // compute final transformation matrix
        var finalMatrix = modelMat.mul(camera.getViewMatrix()).mul(projectionMatrix);

        // transformation of vert
        for(var vert : solid.verticies())
        {
            vert.
        }

        // cut ot w, dehomog, transform to viewport, draw line
    }
}
