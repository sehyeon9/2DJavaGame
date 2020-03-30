package GameModel;

import GameModel.Inventory.Inventory;
import Identifier.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    
    private double x, y;
    private int width, height;
    private boolean isWall;
    private ID id;
    private BufferedImage img;
    private Game game;
    private int health;
    private BufferedImage[] healthBar;
    private Inventory inventory;
    
    private static final double MOVE_STEP = 0.1;
    private static final int HEART_COUNT = 3;
    private static final int HEART_SIZE = 10;
    
    public Player(int x, int y, int width, int height, boolean isWall, ID id, BufferedImage img, Game game) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isWall = isWall;
        this.id = id;
        this.img = img;
        this.game = game;
        health = HEART_COUNT * 2;
        healthBar = makeHealthBar();
        this.inventory = new Inventory();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g; 
        g2D.drawImage(img, (int) x, (int) y, width, height, null);
        drawHealthBar(g2D);
    }
    
    private void drawHealthBar(Graphics2D g2D) {
        g2D.setColor(Color.WHITE);
        g2D.fillRect(10, 10, (HEART_COUNT * 2) * HEART_SIZE, HEART_SIZE);
        for (int i = 0; i < health; i++) {
            g2D.drawImage(healthBar[i], (i + 1) * 10, 10, HEART_SIZE, HEART_SIZE, null);
        }
    }

    @Override
    public void update() {
        updateUserPosition();
        interactWithTiles();
    }
    
    private void updateUserPosition() {
        if (game.getKeyHandler().up) {
            y -= MOVE_STEP;
        }
        if (game.getKeyHandler().down) {
            y += MOVE_STEP;
        }
        if (game.getKeyHandler().left) {
            x -= MOVE_STEP;
        }
        if (game.getKeyHandler().right) {
            x += MOVE_STEP;
        }
    }
    
    private void interactWithTiles() {
        for (Tile tile : game.getManager().getTiles()) {
            if (tile.isWall()) {
                if (getBoundsTop().intersects(tile.getBounds())) {
                    y += MOVE_STEP;
                }
                if (getBoundsBot().intersects(tile.getBounds())) {
                    y -= MOVE_STEP;
                }
                if (getBoundsLeft().intersects(tile.getBounds())) {
                    x += MOVE_STEP;
                }
                if (getBoundsRight().intersects(tile.getBounds())) {
                    x -= MOVE_STEP;
                }
            }
            //TODO: have player move to any neighboring maps instead of one
            if (tile.getID() == ID.PORTAL) {
                if (getBounds().intersects(tile.getBounds()) && game.getMapID() == 1) {
                    setX(10);
                    setY(game.getDisplay().getDisplayHeight() / 3);
                    game.changeMap(2);
                } else if (getBounds().intersects(tile.getBounds()) && game.getMapID() == 2) {
                    setX(20);
                    setY(game.getDisplay().getDisplayHeight() / 3);
                    game.changeMap(3);
                } else if (getBounds().intersects(tile.getBounds()) && game.getMapID() == 3) {
                    setX(20);
                    setY(game.getDisplay().getDisplayHeight() - 40);
                    game.changeMap(4);
                }
            }
        }
    }
    
    private void interactWithItems() {
        
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
        return new Rectangle(((int) Math.round(x)) + 1, (int) Math.round(y), width - 1, 1);
    }

    @Override
    public Rectangle getBoundsBot() {
        return new Rectangle(((int) Math.round(x)) + 1, ((int) Math.round(y)) + height - 1, width - 1, 1);
    }

    @Override
    public Rectangle getBoundsLeft() {
        return new Rectangle((int) Math.round(x), ((int) Math.round(y)) + 1, 1, height - 1);
    }

    @Override
    public Rectangle getBoundsRight() {
        return new Rectangle(((int) Math.round(x)) + width - 1, ((int) Math.round(y)) + 1, 1, height - 1);
    }

    public void die() {
         x = 1200;
         y = 1200;
         game.stop();
    }
    
    private BufferedImage[] makeHealthBar() {
        BufferedImage[] healthBar = new BufferedImage[HEART_COUNT * 2];
        for (int i = 0; i < healthBar.length; i++) {
            if (i % 2 == 0) {
                healthBar[i] = game.getImg().getLeftHeart();
            } else {
                healthBar[i] = game.getImg().getRightHeart();
            }
        }
        return healthBar;
    }
    
    public int getHealth() {
        return health;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }
    
}
