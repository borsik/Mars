import java.awt.image.BufferedImage;

/**
 * Created by laptop on 12.11.2016.
 */
public class Main {
    public static void main(String [] args) {
        String path = "map.png";
        BufferedImage map = ImageConvert.loadImage(path);
        Terrain cityMap = ImageConvert.convertTo2D(map);


    }
}
