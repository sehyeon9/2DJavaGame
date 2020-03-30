package Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    
    private BufferedImage sheet;
    
    public SpriteSheet(String filepath) {
        try {
            sheet = ImageIO.read(getClass().getResource(filepath));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public BufferedImage getSprite(int x, int y) {
        return sheet.getSubimage(x * 32 - 32, y * 32 - 32, 32, 32);
    }
    
}
