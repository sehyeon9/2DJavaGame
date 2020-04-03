package GameModel;

import GameView.Images;
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

//TODO: What if my Tile creates all the tile objects prior to being instantiated, that way it only accepts x, y for parameters
public class Tile extends GameObject {

    private int x, y;
    private int width;
    private int height;
    private boolean isWall;
    private ID id;
    private BufferedImage img;
    private Images images;

    /**
     * The default tile size for this game is 20
     */
    public Tile(int x, int y, BufferedImage img, Game game) {
        this.x = x;
        this.y = y;
//        this.isWall = isWall;
//        this.id = id;
        this.img = img;
        width = 20;
        height = 20;
        images = game.getImg();
        defineProperty();
    }
    
    //this isn't used yet, but I plan to use it for other maps soon
    public Tile(int x, int y, int width, int height, BufferedImage img, Game game) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.width = width;
        this.height = height;
        images = game.getImg();
        defineProperty();
    }
    
    private void defineProperty() {
        if (img == images.getWall01() || img == images.getWall02() || img == images.getWall03() ||
            img == images.getWall04() || img == images.getWall05() ||
            img == images.getSilverSquareWall() || img == images.getBotRightOfTree() ||
            img == images.getBotLeftOfTree() || img == images.getTopRightOfTree() ||
            img == images.getTopLeftOfTree() || img == images.getLaserFaceDown() || 
            img == images.getLaserFaceUp() || img == images.getLaserFacingRight() ||
            img == images.getLaserFacingLeft() || img == images.getVoidSpace() ||
            img == images.getWeirdStatue() || img == images.getBird() || img == images.getBlackWall() ||
            img == images.getSabre() || img == images.getStatue() || img == images.getBottomLeftOfDungeonEntrance() ||
            img == images.getBottomRightOfDungeonEntrance() || img == images.getPartRightAboveDoorToDungeon() ||
            img == images.getTopLeftOfDungeonEntrance() || img == images.getTopPartOfDungeonEntrance() ||
            img == images.getTopRightOfDungeonEntrance()) {
            isWall = true;
            id = ID.TILE;
        } else {
            if (img == images.getPortal()) {
                isWall = false;
                id = ID.PORTAL;
            } else if (img == images.getMarbleSwitch() || img == images.getMarbleSwitchSpace()) {
                isWall = true;
                id = ID.MARBLE;
            } else if (img == images.getLockedDoor()) {
                isWall = true;
                id = ID.LOCK;
            } else if (img == images.getTreasureChest()) {
                isWall = true;
                id = ID.CONTAINER;
            } else if (img == images.getFloor() || img == images.getDirt() || img == images.getGrass()) {
                isWall = false;
                id = ID.TILE;
            } else if (img == images.getJar()) {
                isWall = true;
                id = ID.JAR;
            }
        }
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
    
    public void setTileProperty(boolean isWall, ID id, BufferedImage img) {
        this.isWall = isWall;
        this.id = id;
        this.img = img;
    }
}
