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
    boolean pyramidMode = false;
    boolean cubeMode = false;
    int x, y = 0;

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
        double cameraSpeed = 0.010;
        Object3D activeObject = cube;


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
        scene.add(cube);


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
                clear();



                if(e.getY() > y)
                {
                    camera = camera.up(cameraSpeed);
                }
                if (e.getY() < y)
                {
                    camera = camera.down(cameraSpeed);
                }
                if(e.getX() > x)
                {
                    camera = camera.left(cameraSpeed);
                }
                if(e.getX() < x)
                {
                    camera = camera.right(cameraSpeed);
                }
                y = e.getY();
                x = e.getX();



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

                x = e.getX();
                y = e.getY();

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
                if (e.getKeyCode() == KeyEvent.VK_D  && !e.isShiftDown())
                {
                    camera = camera.right(cameraSpeed);
                }
                if (e.getKeyCode() == KeyEvent.VK_A && !e.isShiftDown())
                {
                    camera = camera.left(cameraSpeed);
                }
                if (e.getKeyCode() == KeyEvent.VK_W && !e.isShiftDown())
                {
                    camera = camera.up(cameraSpeed);
                }
                if (e.getKeyCode() == KeyEvent.VK_S && !e.isShiftDown())
                {
                    camera = camera.down(cameraSpeed);
                }
                if ((e.getKeyCode() == KeyEvent.VK_S && e.getKeyCode() == KeyEvent.VK_A) && !e.isShiftDown())
                {
                    camera = camera.down(cameraSpeed);
                    camera = camera.left(cameraSpeed);
                }
                if ((e.getKeyCode() == KeyEvent.VK_S && e.getKeyCode() == KeyEvent.VK_D) && !e.isShiftDown())
                {
                    camera = camera.down(cameraSpeed);
                    camera = camera.right(cameraSpeed);
                }
                if ((e.getKeyCode() == KeyEvent.VK_W && e.getKeyCode() == KeyEvent.VK_D) && !e.isShiftDown())
                {
                    camera = camera.up(cameraSpeed);
                    camera = camera.right(cameraSpeed);
                }
                if ((e.getKeyCode() == KeyEvent.VK_W && e.getKeyCode() == KeyEvent.VK_A) && !e.isShiftDown())
                {
                    camera = camera.up(cameraSpeed);
                    camera = camera.left(cameraSpeed);
                }
                //endregion

                //region Otáčení kamery
                if (e.getKeyCode() == KeyEvent.VK_LEFT && e.isShiftDown())
                {
                    camera = camera.addAzimuth(Math.toRadians(5));
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && e.isShiftDown())
                {
                    camera = camera.addAzimuth(Math.toRadians(-5));
                }
                if (e.getKeyCode() == KeyEvent.VK_UP && e.isShiftDown())
                {
                    camera = camera.addZenith(Math.toRadians(5));
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN && e.isShiftDown())
                {
                    camera = camera.addZenith(Math.toRadians(-5));
                }
                //endregion


                if(e.getKeyCode() == KeyEvent.VK_1 && !cubeMode) // Ovládání kostky
                {
                    cubeMode = true;
                    pyramidMode = false;
                }
                if(e.getKeyCode() == KeyEvent.VK_2 && !pyramidMode) // Ovládání jehlanu
                {
                    cubeMode = false;
                    pyramidMode = true;
                }

                if(cubeMode)
                {

                }

                if(pyramidMode)
                {

                }


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
        wireRenderer.setView(camera.getViewMatrix());
        wireRenderer.renderScene(scene);
        final @Nullable Graphics g = panel.getGraphics();
        if (g != null)
        {
            presenter.present(g);
        }
    }


    public void start()
    {
        //img.setPixel(img.getWidth() / 2, img.getHeight() / 2, 0xffff00);
        present();
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            new Canvas(800, 600).start();
        });
    }


}
