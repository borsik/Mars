import java.awt.image.BufferedImage;

/**
 * Created by laptop on 12.11.2016.
 */
public class Main {
    public static void main(String [] args) {
        String path = "map.png";
        BufferedImage map = ImageConvert.loadImage(path);
        Terrain cityMap = ImageConvert.convertTo2D(map);
        cityMap.calculateRadiation();
        OptimalPath escape = new OptimalPath(cityMap);
        escape.calculateOptimalPath(1400.0);
        System.out.println(escape.showOptimalPath());


    }
}
