package GameModel;

import GameController.GameManager;
import GameController.KeyHandler;
import GameView.BackgroundMusic;
import GameView.Display;
import GameView.Images;
import Identifier.ID;
import Identifier.Portal.PortalID;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game implements Runnable {
    
    private Display display;
    private Thread thread;
    private GameManager manager;
    private boolean gameOver;
    private KeyHandler keyHandler;
    private Enemy enemy;
    
    private static Images img;
    private static BackgroundMusic bgm;
    private static final int TILE_SIZE = 20;

    private int mapID;
    private boolean mapIsDisplayed;
    
    public Game(int mapID) {
        img = new Images();
        Player player = new Player(150, 150, 20, 20, true, ID.PLAYER, img.getPlayer(), this);
        manager = new GameManager(player, this);
        gameOver = false;
        bgm = new BackgroundMusic("./bgm/ShatteredTime.wav");
        this.mapID = mapID;
        keyHandler = new KeyHandler();
        mapIsDisplayed = false;
        enemy = new Enemy(150, 250, 60, 60, true, ID.ENEMY, img.getBoss(),
                this, 1000);
    }
    
    private void setupGame() {
        display = new Display(400, 400);
        display.getFrame().addKeyListener(keyHandler);
        updateMap();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setupGame();
        while (!gameOver) {
            if (!bgm.getClip().isRunning()) {
                bgm.play();
            }
            update();
            draw();
        }
    }
    
    private void draw() {
        BufferStrategy bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        
        manager.draw(g);
        
        g.dispose();
        bs.show();
    }
    
    private void update() {
        keyHandler.update();
        manager.update();
        if (!mapIsDisplayed) {
            updateMap();
        }
    }
    
    public void changeMap(int mapID) {
        this.mapID = mapID;
        mapIsDisplayed = false;
        clearMap();
    }
    
    private void updateMap() {
        if (mapID == 1) {
            drawMap1();
        } else if (mapID == 2) {
            drawMap2();
        } else if (mapID == 3) {
            drawMap3();
            //this should be moved somewhere else
            if (!manager.getEnemies().contains(enemy)) {
                manager.addEnemy(enemy);
            }
        } else if (mapID == 4) {
            drawMap4();
        } else if (mapID == 5) {
            drawMap5();
        } else if (mapID == 6) {
            drawMap6();
        } else if (mapID == 7) {
            drawMap7();
        } else if (mapID == 8) {
            drawMap8();
        } else if (mapID == 9) {
            drawMap9();
        } else if (mapID == 10) {
            drawMap10();
        }
        mapIsDisplayed = true;
    }

    private void drawMap1() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clearMap();

        //draws top part of map
        drawTilesHorizontally(0, TILE_SIZE * 2, display.getDisplayWidth(), img.getWall01());

        //floor
        drawTilesBy(0, TILE_SIZE * 3, display.getDisplayWidth(), display.getDisplayHeight() - TILE_SIZE,
                img.getFloor());

        //draws bottom part of map
        drawTilesHorizontally(0, display.getDisplayHeight() - TILE_SIZE,
                display.getDisplayWidth() - (TILE_SIZE * 5), img.getWall03());
        drawTilesHorizontally(display.getDisplayWidth() - (TILE_SIZE * 5), display.getDisplayHeight() - TILE_SIZE,
                display.getDisplayWidth(), img.getWall02());

        //the right bounds
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE * 2, display.getDisplayHeight() / 3,
                img.getWall01());
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, (display.getDisplayHeight() / 3) + (TILE_SIZE * 2),
                display.getDisplayHeight(), img.getWall01());

        //for left bounds
        drawTilesVertically(0, TILE_SIZE * 2, display.getDisplayHeight() / 3, img.getWall01());
        drawTilesVertically(0, (display.getDisplayHeight() / 3) + (TILE_SIZE * 2), display.getDisplayHeight(),
                img.getWall01());
        manager.addPortal(0, display.getDisplayHeight() / 3, img.getPortal(), 10);

        //draw bird and laser for top bound
        manager.addTile(display.getDisplayWidth() / 4, TILE_SIZE * 2, img.getLaserFaceDown());
        manager.addTile(display.getDisplayWidth() / 2, TILE_SIZE * 2, img.getBird());
        manager.addTile(3 * (display.getDisplayWidth() / 4), TILE_SIZE * 2, img.getLaserFaceDown());

        //draw two statues in the middle
        manager.addTile(display.getDisplayWidth() / 4, display.getDisplayHeight() / 4, img.getStatue());
        manager.addTile(3 * (display.getDisplayWidth() / 4), display.getDisplayHeight() / 4, img.getStatue());

        //draw marble in between the two statues
        manager.addTile(display.getDisplayWidth() / 2, display.getDisplayHeight() / 4, img.getMarbleSwitch());

        //draw the sky-blue portal to the right
        manager.addPortal(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() / 3, img.getPortal(), 2);
    }
    
    /** portal to map 3 is in 20, 380 and portal to map 1 is in 0, 133 */
    private void drawMap2() {
        //top left corner wall
        manager.addTile(0, TILE_SIZE, img.getWall01());

        //top horizontal wall
        drawTilesHorizontally(TILE_SIZE, TILE_SIZE, display.getDisplayWidth() - TILE_SIZE, img.getWall04());

        //top right corner wall
        manager.addTile(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE, img.getWall02());

        //left vertical wall
        drawTilesVertically(0, TILE_SIZE * 2, display.getDisplayHeight() / 3, img.getWall03());
        drawTilesVertically(0, (display.getDisplayHeight() / 3) + (TILE_SIZE * 2), display.getDisplayHeight(),
                img.getWall03());

        //left floor by portal between vertical walls
        manager.addTile(0, (display.getDisplayHeight() / 3) + TILE_SIZE, img.getFloor());

        //right vertical wall
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE * 2, 
                display.getDisplayHeight() - TILE_SIZE, img.getWall03());

        //bottom right corner wall
        manager.addTile(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - TILE_SIZE, img.getWall01());

        //bottom portal to map 3
        manager.addPortal(TILE_SIZE, display.getDisplayHeight() - TILE_SIZE, img.getPortal(), 3);

        //portal between left vertical walls to map 1
        manager.addPortal(0, display.getDisplayHeight() / 3, img.getPortal(), 1);

        //bottom floor between horizontal walls
        manager.addTile(TILE_SIZE * 2, display.getDisplayHeight() - TILE_SIZE, img.getFloor());

        //bottom horizontal wall
        drawTilesHorizontally(TILE_SIZE * 3, display.getDisplayHeight() - TILE_SIZE, 
                display.getDisplayWidth() - TILE_SIZE, img.getWall04());

        //floor
        drawTilesBy(TILE_SIZE, TILE_SIZE * 2, display.getDisplayWidth() - TILE_SIZE, 
                display.getDisplayHeight() - TILE_SIZE, img.getFloor());

        //top left jars
        manager.addTile(TILE_SIZE, TILE_SIZE * 2, img.getJar());
        manager.addTile(TILE_SIZE * 2, TILE_SIZE * 2, img.getJar());
        manager.addTile(TILE_SIZE, TILE_SIZE * 3, img.getJar());

        //bottom right jars
        manager.addTile(display.getDisplayWidth() - (TILE_SIZE * 2), display.getDisplayHeight() - (TILE_SIZE * 2), img.getJar());
        manager.addTile(display.getDisplayWidth() - (TILE_SIZE * 3), display.getDisplayHeight() - (TILE_SIZE * 2),
                img.getJar());
        manager.addTile(display.getDisplayWidth() - (TILE_SIZE * 2), display.getDisplayHeight() - (TILE_SIZE * 3),
                img.getJar());
    }

    /** portal to map 2 is in 20, 20 and to map 4 is in 380, 360 */
    //TODO when player activates marble and is teleported from map 1 to map 3, the area is too small and causing bugs
    //TODO increase that area and allow player to move around the enclosed area
    private void drawMap3() {
        //top left corner wall
        manager.addTile(0, TILE_SIZE, img.getWall01());

        //top horizontal wall should be very similar to bottom horizontal wall from drawMap02
        //portal to map 2
        manager.addPortal(TILE_SIZE, TILE_SIZE, img.getPortal(), 2);
        manager.addTile(TILE_SIZE * 2, TILE_SIZE, img.getFloor());
        drawTilesHorizontally(TILE_SIZE * 3, TILE_SIZE, display.getDisplayWidth() - TILE_SIZE, 
                img.getWall04());
        
        //left vertical wall
        drawTilesVertically(0, TILE_SIZE * 2, display.getDisplayHeight() / 2, img.getWall03());
        manager.addTile(0, display.getDisplayHeight() / 2, img.getBird());
        drawTilesVertically(0, (display.getDisplayHeight() / 2) + TILE_SIZE, 
                display.getDisplayHeight() - TILE_SIZE, img.getWall03());
        
        //bottom left corner wall
        manager.addTile(0, display.getDisplayHeight() - TILE_SIZE, img.getWall02());
        
        //bottom horizontal wall with lasers in between
        drawTilesHorizontally(TILE_SIZE, display.getDisplayHeight() - TILE_SIZE, 
                display.getDisplayWidth() / 4, img.getWall04());
        manager.addTile(display.getDisplayWidth() / 4, display.getDisplayHeight() - TILE_SIZE, img.getLaserFaceUp());
        drawTilesHorizontally((display.getDisplayWidth() / 4) + TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                3 * (display.getDisplayWidth() / 4), img.getWall04());
        manager.addTile(3 * (display.getDisplayWidth() / 4), display.getDisplayHeight() - TILE_SIZE, img.getLaserFaceUp());
        drawTilesHorizontally(3 * (display.getDisplayWidth() / 4) + TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                display.getDisplayWidth(), img.getWall04());
        
        //top right corner wall
        manager.addTile(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE, img.getWall02());
        
        //right vertical wall
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE * 2,
                display.getDisplayHeight() - (TILE_SIZE * 3), img.getWall03());
        
        //right floor by in between vertical walls
        manager.addTile(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - (TILE_SIZE * 3), img.getFloor());

        //portal to map 4
        manager.addPortal(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - (TILE_SIZE * 2), 
                img.getPortal(), 4);

        //bottom right corner wall
        manager.addTile(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - TILE_SIZE, img.getWall01());
        
        //silver square at the far left from center
        manager.addTile((display.getDisplayWidth() / 2) - (TILE_SIZE * 2), display.getDisplayHeight() / 2,
                img.getSilverSquareWall());
        
        //silver square far right from center
        manager.addTile((display.getDisplayWidth() / 2) + (TILE_SIZE * 2), display.getDisplayHeight() / 2,
                img.getSilverSquareWall());
        
        //top and bottom silver squares from center
        for (int i = (display.getDisplayWidth() / 2) - TILE_SIZE; i <= (display.getDisplayWidth() / 2) + TILE_SIZE; i += TILE_SIZE) {
            manager.addTile(i, (display.getDisplayHeight() / 2) - TILE_SIZE, img.getSilverSquareWall());
            manager.addTile(i, (display.getDisplayHeight() / 2) + TILE_SIZE, img.getSilverSquareWall());
        }
        
        //treasure chest
        manager.addTile((display.getDisplayWidth() / 2) - TILE_SIZE, display.getDisplayHeight() / 2, img.getTreasureChest());
        
        //mid floor
        manager.addTile(display.getDisplayWidth() / 2, display.getDisplayHeight() / 2, img.getFloor());
        
        //marble
        manager.addTile((display.getDisplayWidth() / 2) + TILE_SIZE, display.getDisplayHeight() / 2,
                img.getMarbleSwitch());
        
        //the rest is floor
        //top left quadrilateral section of floor
        drawTilesBy(TILE_SIZE, TILE_SIZE * 2, (display.getDisplayWidth() / 2) - TILE_SIZE,
                display.getDisplayHeight() / 2, img.getFloor());
        
        //top mid quad section of floor
        drawTilesBy((display.getDisplayWidth() / 2) - TILE_SIZE, TILE_SIZE * 2, 
                (display.getDisplayWidth() / 2) + (TILE_SIZE * 2),
                (display.getDisplayHeight() / 2) - TILE_SIZE, img.getFloor());
        
        //top right quad section of floor
        drawTilesBy((display.getDisplayWidth() / 2) + (TILE_SIZE * 2), TILE_SIZE * 2,
                display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() / 2,
                 img.getFloor());
        
        //left mid section of floor
        drawTilesHorizontally(TILE_SIZE, display.getDisplayHeight() / 2, 
                (display.getDisplayWidth() / 2) - (TILE_SIZE * 2), img.getFloor());
        
        //right mid section of floor
        drawTilesHorizontally((display.getDisplayWidth() / 2) + (TILE_SIZE * 3), display.getDisplayHeight() / 2,
                display.getDisplayWidth() - TILE_SIZE, img.getFloor());
        
        //bottom left section of floor
        drawTilesBy(TILE_SIZE, (display.getDisplayHeight() / 2) + TILE_SIZE, 
                (display.getDisplayWidth() / 2) - TILE_SIZE,
                display.getDisplayHeight() - TILE_SIZE, img.getFloor());
        
        //bottom mid section of floor
        drawTilesBy((display.getDisplayWidth() / 2) - TILE_SIZE, (display.getDisplayHeight() / 2) + (TILE_SIZE * 2),
                (display.getDisplayWidth() / 2) + (TILE_SIZE * 2),
                display.getDisplayHeight() - TILE_SIZE, img.getFloor());
        
        //bottom right section of floor
        drawTilesBy((display.getDisplayWidth() / 2) + (TILE_SIZE * 2), (display.getDisplayHeight() / 2) + TILE_SIZE,
                display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                img.getFloor());
    }

    private void drawMap4() {
        //top left corner wall
        manager.addTile(0, TILE_SIZE, img.getWall01());
        
        //top horizontal walls
        drawTilesHorizontally(TILE_SIZE, TILE_SIZE, TILE_SIZE * 5, img.getWall04());
        manager.addTile(TILE_SIZE * 5, TILE_SIZE, img.getLaserFaceDown());
        drawTilesHorizontally(TILE_SIZE * 6, TILE_SIZE, display.getDisplayWidth() - (TILE_SIZE * 5),
                img.getWall04());
        manager.addTile(display.getDisplayWidth() - (TILE_SIZE * 5), TILE_SIZE, img.getLaserFaceDown());
        drawTilesHorizontally(display.getDisplayWidth() - (TILE_SIZE * 4), TILE_SIZE, 
                display.getDisplayWidth() - TILE_SIZE, img.getWall04());
        
        //top right corner wall
        manager.addTile(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE, img.getWall02());
        
        //right vertical wall
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE * 2,
                display.getDisplayHeight() - (TILE_SIZE * 2), img.getWall03());
        
        //bottom right corner wall
        manager.addTile(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                img.getWall01());
        
        //bottom left corner wall
        manager.addTile(0, display.getDisplayHeight() - TILE_SIZE, img.getWall02());
        
        //bottom horizontal wall
        drawTilesHorizontally(TILE_SIZE, display.getDisplayHeight() - TILE_SIZE, TILE_SIZE * 5,
                img.getWall04());
        manager.addTile(TILE_SIZE * 5, display.getDisplayHeight() - TILE_SIZE, img.getLaserFaceUp());
        drawTilesHorizontally(TILE_SIZE * 6, display.getDisplayHeight() - TILE_SIZE,
                display.getDisplayWidth() - (TILE_SIZE * 5), img.getWall04());
        manager.addTile(display.getDisplayWidth() - (TILE_SIZE * 5), display.getDisplayHeight() - TILE_SIZE,
                img.getLaserFaceUp());
        drawTilesHorizontally(display.getDisplayWidth() - (TILE_SIZE * 4), display.getDisplayHeight() - TILE_SIZE,
                display.getDisplayWidth() - TILE_SIZE, img.getWall04());
        
        //left vertical wall
        drawTilesVertically(0, TILE_SIZE * 2, display.getDisplayHeight() - (TILE_SIZE * 3),
                img.getWall03());
        drawTilesVertically(0, display.getDisplayHeight() - (TILE_SIZE * 3), 
                display.getDisplayHeight() - TILE_SIZE, img.getFloor());
        
        //black space and silver squares (pressed) in the middle
        for (int y = TILE_SIZE * 2; y < display.getDisplayHeight() - TILE_SIZE; y += TILE_SIZE) {
            manager.addTile(TILE_SIZE * 5, y, img.getVoidSpace());
            manager.addTile(display.getDisplayWidth() - (TILE_SIZE * 5), y, img.getVoidSpace());
            manager.addTile(((TILE_SIZE * 5) + (display.getDisplayWidth() - (TILE_SIZE * 5))) / 2, y,
                    img.getSilverSquare());
            
            //TODO Going to change the below 2 for loops later to make it look like prison instead of a simple rectangle
            //black square walls in between the two vertical black space
            for (int x = TILE_SIZE * 6; x < (((TILE_SIZE * 5) + (display.getDisplayWidth() - (TILE_SIZE * 5))) / 2);
                x += TILE_SIZE) {
                manager.addTile(x, y, img.getBlackWall());
            }
            for (int x = (((TILE_SIZE * 5) + (display.getDisplayWidth() - (TILE_SIZE * 5))) / 2) + TILE_SIZE;
                    x < display.getDisplayWidth() - (TILE_SIZE * 5); x += TILE_SIZE) {
                manager.addTile(x, y, img.getBlackWall());
            }
        }
        
        //black void space going horizontally
        drawTilesHorizontally(TILE_SIZE, display.getDisplayHeight() / 2, TILE_SIZE * 5,
                img.getVoidSpace());
        drawTilesHorizontally(display.getDisplayWidth() - (TILE_SIZE * 4), display.getDisplayHeight() / 2,
                display.getDisplayWidth() - TILE_SIZE, img.getVoidSpace());
        
        //top left section of floor
        drawTilesBy(TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 5,
                display.getDisplayHeight() / 2, img.getFloor());
        
        //top right section of floor
        drawTilesBy(display.getDisplayWidth() - (TILE_SIZE * 4), TILE_SIZE * 2,
                display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() / 2,
                img.getFloor());
        
        //bottom left section of floor
        drawTilesBy(TILE_SIZE, (display.getDisplayHeight() / 2) + TILE_SIZE, TILE_SIZE * 5,
                display.getDisplayHeight() - TILE_SIZE, img.getFloor());
        
        //bottom right section of floor
        drawTilesBy(display.getDisplayWidth() - (TILE_SIZE * 4), (display.getDisplayHeight() / 2) + TILE_SIZE,
                display.getDisplayWidth() - TILE_SIZE,
                display.getDisplayHeight() - TILE_SIZE, img.getFloor());
    }

    //TODO this is the final map which includes the boss and is not quite yet finished
    //TODO i want to finish it after all the non-dungeon maps are implemented
    private void drawMap5() {
        //top left corner wall
        manager.addTile(0, TILE_SIZE, img.getWall01());
        
        //top horizontal walls, lasers, door
        int xSpacing = display.getDisplayWidth() / 5;
        drawTilesHorizontally(TILE_SIZE, TILE_SIZE, xSpacing, img.getWall04());
        manager.addTile(xSpacing, TILE_SIZE, img.getLaserFaceDown());
        drawTilesHorizontally(xSpacing + TILE_SIZE, TILE_SIZE, xSpacing * 2, img.getWall04());
        manager.addTile(xSpacing * 2, TILE_SIZE, img.getLaserFaceDown());
        drawTilesHorizontally((xSpacing * 2) + TILE_SIZE, TILE_SIZE, xSpacing * 4, img.getWall04());
        manager.addTile(xSpacing * 4, TILE_SIZE, img.getLockedDoor());
        drawTilesHorizontally((xSpacing * 4) + TILE_SIZE, TILE_SIZE, (xSpacing * 5) - TILE_SIZE,
                            img.getWall04());
        
        //top right corner wall
        manager.addTile((xSpacing * 5) - TILE_SIZE, TILE_SIZE, img.getWall01());
        
        //left vertical wall
        int ySpacing = display.getDisplayHeight() / 5;
        drawTilesVertically(0, TILE_SIZE * 2, ySpacing, img.getWall03());
        manager.addTile(0, ySpacing, img.getLaserFacingRight());
        drawTilesVertically(0, ySpacing + TILE_SIZE, ySpacing * 3, img.getWall03());
        manager.addTile(0, ySpacing * 3, img.getLaserFacingRight());
        drawTilesVertically(0, (ySpacing * 3) + TILE_SIZE, (ySpacing * 5) - TILE_SIZE, img.getWall03());
        
        //bottom left corner wall
        manager.addTile(0, display.getDisplayHeight() - TILE_SIZE, img.getWall02());
        
        //bottom right corner wall
        manager.addTile(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                img.getWall01());
        
        //right vertical wall
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE * 2, ySpacing, img.getWall03());
        manager.addTile(display.getDisplayWidth() - TILE_SIZE, ySpacing, img.getLaserFacingRight());
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, ySpacing + TILE_SIZE, ySpacing * 3,
                img.getWall03());
        manager.addTile(display.getDisplayWidth() - TILE_SIZE, ySpacing * 3, img.getLaserFacingRight());
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, (ySpacing * 3) + TILE_SIZE,
                (ySpacing * 5) - TILE_SIZE, img.getWall03());
    }

    private void drawMap6() {

    }

    private void drawMap7() {

    }

    private void drawMap8() {

    }

    private void drawMap9() {
        //default grass layer
        drawTilesBy(TILE_SIZE, TILE_SIZE * 2, display.getDisplayWidth() - (TILE_SIZE * 2), 
                display.getDisplayHeight() - TILE_SIZE, img.getGrass());

        //bottom horizontal walls
        drawTilesHorizontally(0, display.getDisplayHeight() - TILE_SIZE, display.getDisplayWidth(),
                img.getWall05());

        //top left corner
        manager.addTile(0, TILE_SIZE * 2, img.getBotRightOfTree());

        //left vertical trees
        for (int y = TILE_SIZE * 2; y < display.getDisplayHeight() - (TILE_SIZE * 2); y += TILE_SIZE * 2) {
            manager.addTile(0, y, img.getTopRightOfTree());
            manager.addTile(0, y + TILE_SIZE, img.getBotRightOfTree());
        }

        //right vertical trees
        int x = display.getDisplayWidth() - TILE_SIZE;
        for (int y = TILE_SIZE; y < display.getDisplayHeight() - (TILE_SIZE * 3); y += TILE_SIZE * 2) {
            manager.addTile(x, y, img.getTopLeftOfTree());
            manager.addTile(x, y + TILE_SIZE, img.getBotLeftOfTree());
        }

        //portal to map 10
        manager.addPortal(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - (TILE_SIZE * 2),
                img.getPortal(), 10);

        // trees at (100, 100), (300, 100), (100, 300), (300, 300) these (x, y) cords must be for bot right of tree 

        // (100, 100)
        x = TILE_SIZE * 5;
        int y = TILE_SIZE * 5;
        drawEntireTree(x, y);

        // (300, 100)
        x = display.getDisplayWidth() - (TILE_SIZE * 5);
        drawEntireTree(x, y);

        // (300, 300)
        y = display.getDisplayHeight() - (TILE_SIZE * 5);
        drawEntireTree(x, y);

        // (100, 300)
        x = TILE_SIZE * 5;
        drawEntireTree(x, y);

    }

    private void drawEntireTree(int x, int y) {
        manager.addTile(x - TILE_SIZE, y - TILE_SIZE, img.getTopLeftOfTree());
        manager.addTile(x, y - TILE_SIZE, img.getTopRightOfTree());
        manager.addTile(x - TILE_SIZE, y, img.getBotLeftOfTree());
        manager.addTile(x, y, img.getBotRightOfTree());
    }

    //this is the non-dungeon map that connects the outer world to the dungeon
    private void drawMap10() {
        int x = (display.getDisplayWidth() / 8) * 3; //when frame is 400x400 this is 150
        //default sand layer
        manager.addTile(x - TILE_SIZE, TILE_SIZE * 2, img.getSand());
        drawTilesBy(x - TILE_SIZE, TILE_SIZE * 3, display.getDisplayWidth() - TILE_SIZE,
                display.getDisplayHeight() - TILE_SIZE, img.getSand());
        drawTilesHorizontally(TILE_SIZE, display.getDisplayHeight() - (TILE_SIZE * 2),
                display.getDisplayWidth() - (TILE_SIZE * 2), img.getSand());
        drawTilesHorizontally(x + (TILE_SIZE * 4), TILE_SIZE * 2,
                display.getDisplayWidth() - TILE_SIZE, img.getSand());

        //default dirt layer
        drawTilesBy(TILE_SIZE, TILE_SIZE * 2, x - TILE_SIZE, 
                display.getDisplayHeight() - (TILE_SIZE * 2), img.getDirt());

        //top left corner
        manager.addTile(0, TILE_SIZE, img.getBotRightOfTree());

        //top horizontal trees
        for (int i = TILE_SIZE; i < x; i += TILE_SIZE * 2) {
            manager.addTile(i, TILE_SIZE, img.getBotLeftOfTree());
            manager.addTile(i + TILE_SIZE, TILE_SIZE, img.getBotRightOfTree());
        }
        for (int i = x + (TILE_SIZE * 5); i < display.getDisplayWidth() - TILE_SIZE; i += TILE_SIZE * 2) {
            manager.addTile(i, TILE_SIZE, img.getBotLeftOfTree());
            manager.addTile(i + TILE_SIZE, TILE_SIZE, img.getBotRightOfTree());
        }

        //dungeon entrance
        manager.addTile(x, TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 2, img.getBottomLeftOfDungeonEntrance());

        Tile portalToMap1 = new Tile(x + (TILE_SIZE * 2), TILE_SIZE, TILE_SIZE, TILE_SIZE * 2, img.getDoorToDungeon(),
                this);
        manager.addPortal(x + (TILE_SIZE * 2), TILE_SIZE, TILE_SIZE, TILE_SIZE * 2, img.getDoorToDungeon(), 1);

        manager.addTile(x + (TILE_SIZE * 3), TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 2, 
                img.getBottomRightOfDungeonEntrance());

        //top right corner
        manager.addTile(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE, img.getBotLeftOfTree());

        //right vertical trees
        x = display.getDisplayWidth() - TILE_SIZE;
        for (int j = TILE_SIZE * 2; j < display.getDisplayHeight() - TILE_SIZE; j += TILE_SIZE * 2) {
            manager.addTile(x, j, img.getTopLeftOfTree());
            manager.addTile(x, j + TILE_SIZE, img.getBotLeftOfTree());
        }

        //left vertical trees
        for (int j = TILE_SIZE * 2; j < display.getDisplayHeight() - (TILE_SIZE * 3); j += TILE_SIZE * 2) {
            manager.addTile(0, j, img.getTopRightOfTree());
            manager.addTile(0, j + TILE_SIZE, img.getBotRightOfTree());
        }

        //weird statues
        manager.addTile(display.getDisplayWidth() / 8 * 3, display.getDisplayHeight() / 2,
                TILE_SIZE, TILE_SIZE * 2, img.getWeirdStatue());
        manager.addTile(display.getDisplayWidth() / 8 * 5, 3 * (display.getDisplayHeight() / 4),
                TILE_SIZE, TILE_SIZE * 2, img.getWeirdStatue());
        manager.addTile(display.getDisplayWidth() / 8 * 7, display.getDisplayHeight() / 2,
                TILE_SIZE, TILE_SIZE * 2, img.getWeirdStatue());

        //portal to map 9
        manager.addPortal(0, display.getDisplayHeight() - (TILE_SIZE * 2), img.getPortal(), 9);

        //bottom horizontal wall
        drawTilesHorizontally(0, display.getDisplayHeight() - TILE_SIZE, display.getDisplayWidth(), img.getWall05());
    }

    private void drawTilesHorizontally(int x, int y, int horizontalLength, BufferedImage img) {
        for (int i = x; i < horizontalLength; i += TILE_SIZE) {
            manager.addTile(i, y, img);
        }
    }

    private void drawTilesVertically(int x, int y, int verticalLength, BufferedImage img) {
        for (int j = y; j < verticalLength; j += TILE_SIZE) {
            manager.addTile(x, j, img);
        }
    }

    private void drawTilesBy(int x, int y, int horizontalLength, int verticalLength, BufferedImage img) {
        for (int i = x; i < horizontalLength; i += TILE_SIZE) {
            for (int j = y; j < verticalLength; j += TILE_SIZE) {
                manager.addTile(i, j, img);
            }
        }
    }
    
    private void clearMap() {
        manager.getTiles().clear();
        manager.getItems().clear();
    }
    
    public int getMapID() {
        return mapID;
    }
    
    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
    }
    
    public synchronized void stop() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public GameManager getManager() {
        return manager;
    }
    
    public Images getImg() {
        return img;
    }
    
    public Display getDisplay() {
        return display;
    }
    
}
