package GameModel.Attribute;

import Identifier.ID;

import java.awt.image.BufferedImage;

//TODO still need to think about how to design and incorporate skills into the game
public class ActiveSkill extends Skill{
    
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean solid;
    private ID id;
    private BufferedImage img;


    public ActiveSkill(int x, int y, int width, int height, boolean solid, ID id, BufferedImage img) {
        super(x, y, width, height, solid, id, img);
    }

    @Override
    public void setCountdown() {
        
    }

    @Override
    public void getCountdown() {

    }

    public void setDamage() {
        
    }
    
}
