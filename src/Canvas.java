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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        final WireRenderer wireRenderer;
        Camera camera;
        Cube cube = new Cube();


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

                Object3D cube = new Cube();
                List<Object3D> scene = new ArrayList<Object3D>();
                scene.add(cube);

                wireRenderer.renderScene(scene);
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


        panel.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_C)
                {
                    //clearAll();
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
