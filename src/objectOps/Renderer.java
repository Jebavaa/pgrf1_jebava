package objectOps;

import org.jetbrains.annotations.NotNull;
import transforms.Mat4;

/**
 *  Represents a transformation of a object data to {@link rasterdata.RasterImage} with pixels of the given type
 * @param <P>
 */
public interface Renderer<P>
{
    void rendererScene(final @NotNull Scene scene);
    void renderSolid(final @NotNUll Solid solid, final @NotNull Mat4 modelMat);
}
