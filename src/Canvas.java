import objectData.Point2D;
import objectData.Polygon2D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rasterData.Presentable;
import rasterData.RasterImage;
import rasterData.RasterImageBI;
import rasterOps.*;
import transforms.Mat3Transl2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.function.Predicate;

public class Canvas
{


    private JFrame frame;
    private JPanel panel;
    private final @NotNull
    RasterImage<Integer> img;
    private final @NotNull Presentable<Graphics> presenter;
    private final @NotNull Liner<Integer> liner;
    private final @NotNull Liner<Integer> dottedLiner;
    private final @NotNull Polygoner<Integer> polygoner;

    private final @NotNull ScanLineImpl<Integer> scanLiner = new ScanLineImpl<Integer>();
    private final @NotNull SeedFill4<Integer> seedFill4 = new SeedFill4<Integer>();
    private final @NotNull SeedFill8<Integer> seedFill8 = new SeedFill8<Integer>();
    private int x1, y1, x2, y2;
    private Integer BGColour = 0x2f2f2f;

    private boolean seedFill4PatternMode = false;
    private boolean seedFill4Mode = false;
    private boolean seedFill8Mode = false;
    private boolean polygonMode = true;



    Polygon2D polygon = new Polygon2D();



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
        dottedLiner = new DottedLiner<>();
        polygoner = new Polygoner<>();

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


                if (polygonMode)
                {
                    clear();
                    polygoner.drawPolygon(polygon, img, 0x00fa00, liner);
                    polygoner.drawFuturePoint(polygon, img, 0x00fafa, dottedLiner, new Point2D(e.getX(), e.getY()));
                }

                present();
            }
        });


        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {


                if (polygon.getPoints().length < 1)
                {
                    polygon.addPoint2D(new Point2D(e.getX(), e.getY()));
                }
                if(seedFill4Mode)
                {
                    seedFill4.fill(img, e.getX(), e.getY(), Optional.of(0x005900), img.getPixel(0,0));
                }
                if(seedFill8Mode)
                {
                    seedFill8.fill(img, e.getX(), e.getY(), Optional.of(0x005933), img.getPixel(0,0));
                }
                if(seedFill4PatternMode)
                {
                    seedFill4.fillPatern(img, e.getX(), e.getY(), 0x005900, 0x552200, img.getPixel(0,0));
                }
                present();


            }
        });

        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {

                if (polygonMode)
                {

                    clear();
                    polygon.addPoint2D(new Point2D(e.getX(), e.getY()));
                    polygoner.drawPolygon(polygon, img, 0x00fa00, liner);
                }

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
                    clearAll();
                }
                /*
                if (e.getKeyCode() == KeyEvent.VK_T)
                {
                    clear();
                    polygon = polygon.transform(new Mat3Transl2D(1,0));
                    polygoner.drawPolygon(polygon, img, 0xffffff, liner);
                    present();
                }*/
                if (e.getKeyCode() == KeyEvent.VK_Z)
                {
                    if (!polygonMode)
                    {
                        clearAll();
                        polygonMode = true;
                        frame.setTitle("Polygon režim");
                        seedFill8Mode = false;
                        seedFill4Mode = false;
                        seedFill4PatternMode = false;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_U)
                {
                    if (!seedFill4PatternMode)
                    {
                        seedFill4PatternMode = true;
                        frame.setTitle("Pattern seedFill4 režim");
                        polygonMode = false;
                        seedFill8Mode = false;
                        seedFill4Mode = false;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_P)
                {
                    if (!seedFill4Mode)
                    {
                        seedFill4Mode = true;
                        seedFill8Mode = false;
                        frame.setTitle("SeedFill4 režim");
                        polygonMode = false;
                        seedFill4PatternMode = false;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_R)
                {
                    if (!seedFill8Mode)
                    {
                        seedFill8Mode = true;
                        seedFill4Mode = false;
                        frame.setTitle("SeedFill8 režim");
                        polygonMode = false;
                        seedFill4PatternMode = false;

                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_O)
                {
                    scanLiner.fill(img, polygon, polygoner, liner, 0x005900);
                }
                if (e.getKeyCode() == KeyEvent.VK_I)
                {
                    scanLiner.fillPattern(img, polygon, polygoner, liner, 0x22550A);
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

    public void clearAll()
    {
        clear();
        polygon = new Polygon2D();
        present();
    }

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
