import objectData.Point2D;
import objectData.Polygon2D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rasterData.Presentable;
import rasterData.RasterImage;
import rasterData.RasterImageBI;
import rasterOps.Liner;
import rasterOps.Polygoner;
import rasterOps.TrivialLiner;

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
    private final @NotNull Polygoner<Integer> polygoner;
    private int x1, y1, x2, y2;
    private boolean tPressed = false;

    Polygon2D polygon = new Polygon2D();


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
        polygoner = new Polygoner<>();

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

                if (!tPressed) {

                    polygoner.drawPolygon(polygon, img, 0x00fa00, liner);
                    polygoner.drawFuturePoint(polygon, img, 0x00fa00, liner, new Point2D(e.getX(), e.getY()));
                }
                else
                {
                    if(polygon.getPoints().length < 3) {
                        polygoner.drawPolygon(polygon, img, 0x00fa00, liner);
                        polygoner.drawFuturePoint(polygon, img, 0x00fa00, liner, new Point2D(e.getX(), e.getY()));
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
                if(tPressed)
                {
                    if(polygon.getPoints().length > 3)
                    {
                        clear();
                        polygon = new Polygon2D();
                        polygon.addPoint2D(new Point2D(e.getX(), e.getY()));
                    }
                }

            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                clear();
                if (!tPressed) {

                    polygon.addPoint2D(new Point2D(e.getX(), e.getY()));
                    polygoner.drawPolygon(polygon, img, 0x00fa00, liner);
                }
                else
                {
                    if(polygon.getPoints().length < 3) {
                        polygon.addPoint2D(new Point2D(e.getX(), e.getY()));
                        polygoner.drawPolygon(polygon, img, 0x00fa00, liner);
                    }
                    else
                    {
                        polygon = new Polygon2D();
                    }

                }

                present();
            }
        });



        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    clear();
                    present();
                }
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_T) {
                    clear();
                    if(!tPressed)
                    {
                        tPressed = true;
                        frame.setTitle("Trojúhelník režim");
                    }
                    else
                    {
                        tPressed = false;
                        frame.setTitle("Polygon režim");
                    }
                }
            }
        });


        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        panel.grabFocus();
    }

    public void clear() {
        img.clear(0x2f2f2f);
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
