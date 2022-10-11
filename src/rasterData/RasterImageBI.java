package rasterData;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class RasterImageBI implements RasterImage<Integer>, Presentable<Graphics> {

    private final @NotNull BufferedImage bufferedImage;

    public RasterImageBI(final int width, final int height) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    @Override
    public @NotNull Optional<Integer> getPixel(final int x, final int y) {
        if(getWidth() > x && getHeight() > y && x >= 0 && y >= 0){
            return Optional.of(bufferedImage.getRGB(x, y));
        }
        return Optional.empty();
    }

    @Override
    public void setPixel(final int x, final int y, final @NotNull Integer newValue) {
        if(getWidth() > x && getHeight() > y && x >= 0 && y >= 0){
            bufferedImage.setRGB(x, y, newValue);
        }
    }

    @Override
    public void clear(final @NotNull Integer newValue) {
        final @Nullable Graphics g = bufferedImage.getGraphics();
        if (g != null) {
            g.setColor(new Color(newValue));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public @NotNull Graphics present(final @NotNull Graphics device) {
        device.drawImage(bufferedImage, 0, 0, null);
        return device;
    }

    public void setRGB(final @NotNull int x, final @NotNull int y, final @NotNull Integer newValue)
    {
        bufferedImage.setRGB(x, y, newValue);
    }







}
