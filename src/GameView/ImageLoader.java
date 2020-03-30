package GameView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageLoader {

    /**
     * @param filepath a path representing where our image file is being stored in our computer
     * @return a BufferedImage of our image file, since the g.drawImage() accepts a BufferedImage as its parameter
     */
    public static BufferedImage loadImage(String filepath) {
        try {
            return ImageIO.read(new FileInputStream(new File(filepath)));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
