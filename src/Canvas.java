import objectData.Point2D;
import objectData.Polygon2D;
import objectData.Triangle2D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rasterData.Presentable;
import rasterData.RasterImage;
import rasterData.RasterImageBI;
import rasterOps.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Canvas {


    private JFrame frame;
    private JPanel panel;
    private final @NotNull
    RasterImage<Integer> img;
    private final @NotNull Presentable<Graphics> presenter;
    private final @NotNull Liner<Integer> liner;
    private final @NotNull Liner<Integer> dottedLiner;
    private final @NotNull Polygoner<Integer> polygoner;
    private final @NotNull Trianglener<Integer> trianglener;

    private final @NotNull ScanLineImpl<Integer> scanLiner = new ScanLineImpl<Integer>();
    private int x1, y1, x2, y2;

    private boolean trojuhelnikMode = false;
    private boolean polygonMode = false;
    private boolean dottedLineMode = false;
    private boolean lineMode = true;


    Polygon2D polygon = new Polygon2D();
    Triangle2D triangle = new Triangle2D();


    public Canvas(int width, int height) {
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
        trianglener = new Trianglener<>();

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                clear();

                if (polygonMode) {

                    polygoner.drawPolygon(polygon, img, 0x00fa00, liner);
                    polygoner.drawFuturePoint(polygon, img, 0x00fafa, dottedLiner, new Point2D(e.getX(), e.getY()));
                }
                if(trojuhelnikMode)
                {

                    if(triangle.getPoints().length == 1)
                    {
                        liner.drawLine(img, triangle.getPoints()[0].getX(), triangle.getPoints()[0].getY(), e.getX(), e.getY(), 0x00fa00);
                    }
                    else if(triangle.getPoints().length == 2)
                    {
                        liner.drawLine(img, triangle.getPoints()[0].getX(), triangle.getPoints()[0].getY(),
                                triangle.getPoints()[1].getX(), triangle.getPoints()[1].getY(), 0x00fa00);
                        trianglener.drawFuturePoint(triangle, img, 0x00fafa, dottedLiner, new Point2D(e.getX(), e.getY()));
                    }

                }
                if(lineMode || dottedLineMode)
                {
                    if(polygon.getPoints().length > 2)
                    {
                        polygon = new Polygon2D();
                    }
                    if(polygon.getPoints().length == 0)
                    {
                        polygon.addPoint2D(new Point2D(e.getX(), e.getY()));
                    }

                    if(lineMode)
                    {
                        liner.drawLine(img, polygon.getPoints()[0].getX(), polygon.getPoints()[0].getY(), e.getX(), e.getY(), 0x00fa00);
                    }
                    if(dottedLineMode)
                    {
                        dottedLiner.drawLine(img, polygon.getPoints()[0].getX(), polygon.getPoints()[0].getY(), e.getX(), e.getY(), 0x00fa00);;
                    }
                }

                present();
            }
        });


        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {


                if (polygon.getPoints().length < 1)
                {
                    polygon.addPoint2D(new Point2D(e.getX(), e.getY()));
                }
                if (triangle.getPoints().length < 1)
                {
                    triangle.addPoint2D(new Point2D(e.getX(), e.getY()));
                }
                if(trojuhelnikMode)
                {
                    if(triangle.getPoints().length == 4)
                    {
                        clear();
                        triangle = new Triangle2D();
                        triangle.addPoint2D(new Point2D(e.getX(), e.getY()));
                    }
                    if(triangle.getPoints().length == 0)
                    {
                        triangle.addPoint2D(new Point2D(e.getX(), e.getY()));
                    }
                }
                if(lineMode || dottedLineMode)
                {
                    clear();
                    polygon = new Polygon2D();
                    polygon.addPoint2D(new Point2D(e.getX(), e.getY()));
                }


            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {


                if (polygonMode) {

                    clear();
                    polygon.addPoint2D(new Point2D(e.getX(), e.getY()));
                    polygoner.drawPolygon(polygon, img, 0x00fa00, liner);
                }
                if(trojuhelnikMode)
                {
                    if(triangle.getPoints().length == 1)
                    {
                        triangle.addPoint2D(new Point2D(e.getX(), e.getY()));
                        liner.drawLine(img, triangle.getPoints()[0].getX(), triangle.getPoints()[0].getY(),
                                triangle.getPoints()[1].getX(), triangle.getPoints()[1].getY(), 0x00fa00);
                    }
                    if(triangle.getPoints().length == 2 ){
                        triangle.addPoint2D(new Point2D(e.getX(), e.getY()));
                        trianglener.drawTriangle(triangle, img, 0x00fa00, liner);
                    }



                }

                present();
            }
        });


        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    clearAll();
                }
                if (e.getKeyCode() == KeyEvent.VK_T) {
                    if(!trojuhelnikMode)
                    {
                        clearAll();
                        trojuhelnikMode = true;
                        frame.setTitle("Trojúhelník režim");
                        polygonMode = false;
                        lineMode = false;
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_Z)
                {
                    if(!polygonMode)
                    {
                        clearAll();
                        polygonMode = true;
                        frame.setTitle("Polygon režim");
                        trojuhelnikMode = false;
                        lineMode = false;
                        dottedLineMode = false;
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_U)
                {
                    if(!dottedLineMode)
                    {
                        clearAll();
                        dottedLineMode = true;
                        frame.setTitle("Přerušovaná čára režim");
                        trojuhelnikMode = false;
                        lineMode = false;
                        polygonMode = false;
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_I)
                {
                    if(!lineMode)
                    {
                        clearAll();
                        lineMode = true;
                        frame.setTitle("Čára režim");
                        trojuhelnikMode = false;
                        dottedLineMode = false;
                        polygonMode = false;
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_O)
                {
                    scanLiner.fill(img, polygon, polygoner, liner,  0x005900);
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
        img.clear(0x2f2f2f);
    }
    public void clearAll()
    {
        clear();
        polygon = new Polygon2D();
        triangle = new Triangle2D();
        present();
    }

    public void present(Graphics graphics) {
        presenter.present(graphics);
    }

    public void present() {
        final @Nullable Graphics g = panel.getGraphics();
        if (g != null) {
            presenter.present(g);
        }
    }


    public void start() {
        img.setPixel(img.getWidth() / 2, img.getHeight() / 2, 0xffff00);
        present();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Canvas(800, 600).start();
        });
    }


}
