import java.awt.image.BufferedImage;


/**
 * Created by olzhas on 12.11.2016.
 */
public class Main {
    public static void main(String [] args) {
        String path = "map.png";
        BufferedImage map = ImageConvert.loadImage(path);
        Terrain cityMap = ImageConvert.convertTo2D(map);
        cityMap.calculateRadiation();
        System.out.println("radiation at start " + cityMap.getTerrain()[cityMap.getStart().y()][cityMap.getStart().x()].getRadiation());
        OptimalPath escape = new OptimalPath(cityMap);
        escape.calculateOptimalPath(1300.0);
        System.out.println(escape.showOptimalPath());

        Plot plot = new Plot(cityMap);
    }
}
