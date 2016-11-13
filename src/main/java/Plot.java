/**
 * Created by olzhas on 12.11.2016.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Plot extends JPanel{
    private Terrain terrain;

    public Plot(Terrain terrain) {
        this.terrain = terrain;
        JFrame frame = new JFrame("Radiation map");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);

        try {
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            frame.paint(graphics2D);
            ImageIO.write(image, "png", new File("radiation.png"));
        } catch (Exception exception) {
            //code
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        Place[][] places = terrain.getTerrain();
        for (int i = 0; i < places.length; i++) {
            for (int j = 0; j < places[i].length; j++) {
                double r = places[i][j].getRadiation();
                float rad = (float) r;
                g.setColor(Color.getHSBColor(rad / 3500000, 1, 1));
                if (places[i][j].isPath()) {
                    g.setColor(Color.CYAN);
                    g.drawLine(i, j, i + 1, j);
                } else {
                    if (places[i][j].isStart()) {
                        g.setColor(Color.BLUE);
                    } else if (places[i][j].isFinish()) {
                        g.setColor(Color.GREEN);
                    } else if (places[i][j].getType() == 1) {
                        g.setColor(Color.BLACK);
                    } else if (places[i][j].getType() == 2) {
                        g.setColor(Color.ORANGE);
                    }
                        g.drawLine(i, j, i, j);
                }


            }
        }
    }


}
