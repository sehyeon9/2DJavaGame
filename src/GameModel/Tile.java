package GameModel;

import Identifier.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The tile object holds valuable information such as its position on the x,y axis, width and height of the tile,
 * whether it's a solid tile or go through, ID to easily identify it as a tile from other objects, and an image
 * representing the graphics component of the tile
 */

/**
 * The below 3 wall png files represent the walls in the underworld and collision should apply
 * wall01.png represents corner wall used for top left or bottom right corners
 * wall02.png represents corner wall used for top right or bottom left corners
 * wall03.png represents regular blue walls (VERTICAL)
 * wall04.png represents regular blue walls (HORIZONTAL)
 * 
 * voidspace.png is the black tile representing space in the underworld
 * treasurechest.png is breakable and is a wall and it holds the key leading to the underworld boss
 * topcliff.png is the top edge connecting the floor and the void space
 * bottomcliff.png is the bottom edge connecting the floor and the void space
 * 
 */
public class Tile extends GameObject {

    private int x, y;
    private static int width = 20;
    private static int height = 20;
    private boolean isWall;
    private ID id;
    private BufferedImage img;

    public Tile(int x, int y, boolean isWall, ID id, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
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
        return isWall;
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
        this.isWall = isWall;
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
    
    public BufferedImage getTile() {
        return img;
    }
}
