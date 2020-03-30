package GameModel.Inventory;

import GameModel.GameObject;
import Identifier.ID;
import Identifier.Item.Type;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The type tells which type of item it is classified as. For example, there may be 3 HP potions all classified under
 * the same type. To tell them apart, a name has to be given to the item so that one hp potion may increase health by
 * half heart, but another one by 1 full heart etc.
 * img gives us a visual description of the item
 */
public class Item extends GameObject {
    
    private Type type;
    private String name;
    private BufferedImage img;
    private int x;
    private int y;
    private boolean isWall;
    
    private static final int width = 15;
    private static final int height = 15;
    
    public Item(int x, int y, boolean isWall, Type type, String name, BufferedImage img) {
        this.type = type;
        this.name = name;
        this.img = img;
        this.x = x;
        this.y = y;
        this.isWall = isWall;
    }
    
    public Type getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }
    
    public BufferedImage getImg() {
        return img;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    @Override
    public void update() {

    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setWall(boolean isWall) {

    }

    @Override
    public ID getID() {
        return null;
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public Rectangle getBoundsTop() {
        return null;
    }

    @Override
    public Rectangle getBoundsBot() {
        return null;
    }

    @Override
    public Rectangle getBoundsLeft() {
        return null;
    }

    @Override
    public Rectangle getBoundsRight() {
        return null;
    }
}
