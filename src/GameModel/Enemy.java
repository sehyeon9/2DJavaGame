package GameModel;

import Identifier.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends GameObject {

    private double x, y;
    private int width, height;
    private boolean isWall;
    private ID id;
    private BufferedImage img;
    private Game game;
    private static final double MOVE_STEP = 0.02;
    private boolean facingLeft;
    private int health;

    public Enemy(int x, int y, int width, int height, boolean isWall, ID id, BufferedImage img, Game game, int health) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isWall = isWall;
        this.id = id;
        this.img = img;
        this.game = game;
        facingLeft = false;
        this.health = health;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(img, (int) x, (int) y, width, height, null);
    }

    @Override
    public void update() {
        if (x <= 100) {
            facingLeft = false;
        } else if (x >= game.getDisplay().getDisplayWidth() - 100) {
            facingLeft = true;
        }
        if (facingLeft) {
            x -= MOVE_STEP;
        } else {
            x += MOVE_STEP;
        }
    }

    @Override
    public int getX() {
        return (int) x;
    }

    @Override
    public int getY() {
        return (int) y;
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
        return new Rectangle((int) x, (int) y, width, height);
    }

    @Override
    public Rectangle getBoundsTop() {
        return new Rectangle();
    }

    @Override
    public Rectangle getBoundsBot() {
        return new Rectangle();
    }

    @Override
    public Rectangle getBoundsLeft() {
        return new Rectangle();
    }

    @Override
    public Rectangle getBoundsRight() {
        return new Rectangle();
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }
    
    public int getHealth() {
        return health;
    }
    
    public boolean isAlive() {
        return health > 0;
    }
}
