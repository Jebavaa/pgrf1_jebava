import objectData.Cube;
import objectData.Object3D;
import objectData.Point2D;
import objectData.Polygon2D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rasterData.Presentable;
import rasterData.RasterImage;
import rasterData.RasterImageBI;
import rasterOps.*;
import transforms.Camera;
import transforms.Mat3Transl2D;
import transforms.Mat4PerspRH;
import transforms.Vec3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Canvas
{


    private JFrame frame;
    private JPanel panel;
    private final @NotNull
    RasterImage<Integer> img;
    private final @NotNull Presentable<Graphics> presenter;
    private final @NotNull Liner<Integer> liner;
    private final @NotNull WireRenderer wireRenderer;
    private List<Object3D> scene;
    Camera camera;

    private Integer BGColour = 0x2f2f2f;

    public Canvas(int width, int height)
    {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("Grafika : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final @NotNull RasterImageBI auxRasterImage = new RasterImageBI(width, height);
        img = auxRasterImage;
        presenter = auxRasterImage;
        liner = new TrivialLiner<>();
        scene = new ArrayList<Object3D>();
        Cube cube = new Cube();
        double cameraSpeed = 0.1;


        // View
        camera = new Camera(
                new Vec3D(-2,-3,2), // position
                Math.toRadians(60), // azimuth
                Math.toRadians(-30),  // zenith
                Math.toRadians(90), // radius
                false // first person
        );


        // Projection
        //MVP - Model, View, Projection
        Mat4PerspRH projectionMatrix = new Mat4PerspRH(Math.toRadians(60),height/(double) width,0.1,200 ); // úhel kamery, poměr, minimal render distance, render distance
        wireRenderer = new WireRenderer(img, 0x22ff00, liner, camera.getViewMatrix(), projectionMatrix);



        panel = new JPanel()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));

        panel.addMouseMotionListener(new MouseAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                present();
            }
        });


        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {

                //Solid cube2 = new Cube();
                //Mat4 scale = new Mat4Scale(0.5);
                //Mat4 tra = new Mat4Transl(0.2,0,0);
                //cube2.setModel(scale.mul(tra));

                clear();

                scene.add(cube);

                present();
            }
        });

        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                present();
            }
        });

        //region Oddalování a přibližování kamery
        panel.addMouseWheelListener(new MouseAdapter()
        {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                clear();
                if (e.getWheelRotation() < 0)
                {
                    camera = camera.addRadius(Math.toRadians(5));
                    wireRenderer.setView(camera.getViewMatrix());
                }
                else
                {
                    camera = camera.addRadius(Math.toRadians(-5));
                    wireRenderer.setView(camera.getViewMatrix());
                }
                present();
            }
        });
        //endregion


        panel.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                clear();

                //region Posouvání kamery
                if (e.getKeyCode() == KeyEvent.VK_RIGHT  && !e.isShiftDown())
                {
                    camera = camera.right(cameraSpeed);
                    wireRenderer.setView(camera.getViewMatrix());
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT && !e.isShiftDown())
                {
                    camera = camera.left(cameraSpeed);
                    wireRenderer.setView(camera.getViewMatrix());
                }
                if (e.getKeyCode() == KeyEvent.VK_UP && !e.isShiftDown())
                {
                    camera = camera.up(cameraSpeed);
                    wireRenderer.setView(camera.getViewMatrix());
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN && !e.isShiftDown())
                {
                    camera = camera.down(cameraSpeed);
                    wireRenderer.setView(camera.getViewMatrix());
                }
                if ((e.getKeyCode() == KeyEvent.VK_DOWN && e.getKeyCode() == KeyEvent.VK_LEFT) && !e.isShiftDown())
                {
                    camera = camera.down(cameraSpeed);
                    camera = camera.left(cameraSpeed);
                    wireRenderer.setView(camera.getViewMatrix());
                }
                if ((e.getKeyCode() == KeyEvent.VK_DOWN && e.getKeyCode() == KeyEvent.VK_RIGHT) && !e.isShiftDown())
                {
                    camera = camera.down(cameraSpeed);
                    camera = camera.right(cameraSpeed);
                    wireRenderer.setView(camera.getViewMatrix());
                }
                if ((e.getKeyCode() == KeyEvent.VK_UP && e.getKeyCode() == KeyEvent.VK_RIGHT) && !e.isShiftDown())
                {
                    camera = camera.up(cameraSpeed);
                    camera = camera.right(cameraSpeed);
                    wireRenderer.setView(camera.getViewMatrix());
                }
                if ((e.getKeyCode() == KeyEvent.VK_UP && e.getKeyCode() == KeyEvent.VK_LEFT) && !e.isShiftDown())
                {
                    camera = camera.up(cameraSpeed);
                    camera = camera.left(cameraSpeed);
                    wireRenderer.setView(camera.getViewMatrix());
                }
                //endregion

                //region Otáčení kamery
                if (e.getKeyCode() == KeyEvent.VK_LEFT && e.isShiftDown())
                {
                    camera = camera.addAzimuth(Math.toRadians(5));
                    wireRenderer.setView(camera.getViewMatrix());
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && e.isShiftDown())
                {
                    camera = camera.addAzimuth(Math.toRadians(-5));
                    wireRenderer.setView(camera.getViewMatrix());
                }
                if (e.getKeyCode() == KeyEvent.VK_UP && e.isShiftDown())
                {
                    camera = camera.addZenith(Math.toRadians(5));
                    wireRenderer.setView(camera.getViewMatrix());
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN && e.isShiftDown())
                {
                    camera = camera.addZenith(Math.toRadians(-5));
                    wireRenderer.setView(camera.getViewMatrix());
                }
                //endregion

                present();
            }


        });


        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        panel.grabFocus();
    }


    public void clear()
    {
        img.clear(BGColour);
    }

    /*
    public void clearAll()
    {
        clear();

        present();
    }
*/

    public void present(Graphics graphics)
    {
        presenter.present(graphics);
    }

    public void present()
    {
        wireRenderer.renderScene(scene);
        final @Nullable Graphics g = panel.getGraphics();
        if (g != null)
        {
            presenter.present(g);
        }
    }


    public void start()
    {
        img.setPixel(img.getWidth() / 2, img.getHeight() / 2, 0xffff00);
        present();
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            new Canvas(800, 600).start();
        });
    }


}
