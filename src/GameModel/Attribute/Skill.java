package GameModel.Attribute;

import GameModel.GameObject;
import Identifier.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

//todo still in design process
public abstract class Skill extends GameObject {
    
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean solid;
    private ID id;
    private BufferedImage img;

    public Skill(int x, int y, int width, int height, boolean solid, ID id, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.id = id;
        this.img = img;
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
        return solid;
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
        this.solid = isWall;
    }

    @Override
    public ID getID() {
        return id;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public Rectangle getBoundsTop() {
        return new Rectangle(x, y, width, 1);
    }

    @Override
    public Rectangle getBoundsBot() {
        return new Rectangle(x, y + height - 1, width, 1);
    }

    @Override
    public Rectangle getBoundsLeft() {
        return new Rectangle(x, y, 1, height);
    }

    @Override
    public Rectangle getBoundsRight() {
        return new Rectangle(x + width - 1, y, 1, height);
    }
    
    public abstract void setCountdown();
    
    public abstract void getCountdown();
    
    
    
}
