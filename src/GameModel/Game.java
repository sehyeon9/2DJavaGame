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
    private PortalID portals;
    
    public Game(int mapID) {
        img = new Images();
        Player player = new Player(150, 150, 20, 20, true, ID.PLAYER, img.getPlayer(), this);
        manager = new GameManager(player, this);
        gameOver = false;
        bgm = new BackgroundMusic("./bgm/ShatteredTime.wav");
        this.mapID = mapID;
        keyHandler = new KeyHandler();
        mapIsDisplayed = false;
        portals = new PortalID();
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
        drawTilesHorizontally(0, TILE_SIZE * 2, display.getDisplayWidth(), true, ID.TILE, img.getWall01());
        
        //floor
        drawTilesBy(0, TILE_SIZE * 3, display.getDisplayWidth(), display.getDisplayHeight() - TILE_SIZE,
                    false, ID.TILE, img.getFloor());
        
        //draws bottom part of map
        drawTilesHorizontally(0, display.getDisplayHeight() - TILE_SIZE, display.getDisplayWidth() - (TILE_SIZE * 5),
                                true, ID.TILE, img.getWall03());
        drawTilesHorizontally(display.getDisplayWidth() - (TILE_SIZE * 5), display.getDisplayHeight() - TILE_SIZE,
                                display.getDisplayWidth(), true, ID.TILE, img.getWall02());
        
        //the right bounds
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE * 2, display.getDisplayHeight() / 3,
                            true, ID.TILE, img.getWall01());
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, (display.getDisplayHeight() / 3) + (TILE_SIZE * 2),
                            display.getDisplayHeight(), true, ID.TILE, img.getWall01());
        
        //for left bounds
        drawTilesVertically(0, TILE_SIZE * 2, display.getDisplayHeight() / 3, true, ID.TILE, img.getWall01());
        drawTilesVertically(0, (display.getDisplayHeight() / 3) + (TILE_SIZE * 2), display.getDisplayHeight(),
                            true, ID.TILE, img.getWall01());
        Tile portalToMap10 = new Tile(0, display.getDisplayHeight() / 3, false, ID.PORTAL, img.getPortal());
        manager.addOneTile(portalToMap10);
        portals.addPortal(portalToMap10);
        portals.addPortalMapping(portalToMap10, 10);
        
        //draw bird and laser for top bound
        manager.addOneTile(new Tile(display.getDisplayWidth() / 4, TILE_SIZE * 2, true, ID.TILE,
                 img.getLaserFaceDown()));
        manager.addOneTile(new Tile(display.getDisplayWidth() / 2, TILE_SIZE * 2,true, ID.TILE,
                 img.getBird()));
        manager.addOneTile(new Tile(3 * (display.getDisplayWidth() / 4), TILE_SIZE * 2, true,
                 ID.TILE, img.getLaserFaceDown()));
        
        //draw two statues in the middle
        manager.addOneTile(new Tile(display.getDisplayWidth() / 4, display.getDisplayHeight() / 4,
                true, ID.TILE, img.getStatue()));
        manager.addOneTile(new Tile(3 * (display.getDisplayWidth() / 4), display.getDisplayHeight() / 4,
                true, ID.TILE, img.getStatue()));
        
        //draw marble in between the two statues
        manager.addOneTile(new Tile(display.getDisplayWidth() / 2, display.getDisplayHeight() / 4,
                true, ID.MARBLE, img.getMarbleSwitch()));
        
        //draw the sky-blue portal to the right
        Tile tile = new Tile(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() / 3,
                false, ID.PORTAL, img.getPortal());
        manager.addOneTile(tile);
        portals.addPortal(tile);
        portals.addPortalMapping(tile, 2);
    }
    
    /** portal to map 3 is in 20, 380 and portal to map 1 is in 0, 133 */
    private void drawMap2() {
        //top left corner wall
        manager.addOneTile(new Tile(0, TILE_SIZE, true, ID.TILE, img.getWall01()));
        
        //top horizontal wall
        drawTilesHorizontally(TILE_SIZE, TILE_SIZE, display.getDisplayWidth() - TILE_SIZE, true,
                ID.TILE, img.getWall04());
        
        //top right corner wall
        manager.addOneTile(new Tile(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE, true, ID.TILE,
                img.getWall02()));
        
        //left vertical wall
        drawTilesVertically(0, TILE_SIZE * 2, display.getDisplayHeight() / 3, true, ID.TILE,
                img.getWall03());
        drawTilesVertically(0, (display.getDisplayHeight() / 3) + (TILE_SIZE * 2), display.getDisplayHeight(),
                            true, ID.TILE, img.getWall03());
        
        //left floor by portal between vertical walls
        manager.addOneTile(new Tile(0, (display.getDisplayHeight() / 3) + TILE_SIZE, false, ID.TILE,
                img.getFloor()));
        
        //right vertical wall
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE * 2, 
                display.getDisplayHeight() - TILE_SIZE, true, ID.TILE, img.getWall03());
        
        //bottom right corner wall
        manager.addOneTile(new Tile(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                true, ID.TILE, img.getWall01()));
        
        //bottom portal to map 3
        Tile portalToMap3 = new Tile(TILE_SIZE, display.getDisplayHeight() - TILE_SIZE, false,
                ID.PORTAL, img.getPortal());
        manager.addOneTile(portalToMap3);
        portals.addPortal(portalToMap3);
        portals.addPortalMapping(portalToMap3, 3);
        
        //portal between left vertical walls to map 1
        Tile portalToMap1 = new Tile(0, display.getDisplayHeight() / 3, false, ID.PORTAL, img.getPortal());
        manager.addOneTile(portalToMap1);
        portals.addPortal(portalToMap1);
        portals.addPortalMapping(portalToMap1, 1);
        
        //bottom floor between horizontal walls
        manager.addOneTile(new Tile(TILE_SIZE * 2, display.getDisplayHeight() - TILE_SIZE, 
                false, ID.TILE, img.getFloor()));
        
        //bottom horizontal wall
        drawTilesHorizontally(TILE_SIZE * 3, display.getDisplayHeight() - TILE_SIZE, 
                display.getDisplayWidth() - TILE_SIZE, true, ID.TILE, img.getWall04());
        
        //floor
        drawTilesBy(TILE_SIZE, TILE_SIZE * 2, display.getDisplayWidth() - TILE_SIZE, 
                display.getDisplayHeight() - TILE_SIZE, false, ID.TILE, img.getFloor());
        
        //top left jars
        manager.addOneTile(new Tile(TILE_SIZE, TILE_SIZE * 2, true, ID.JAR, img.getJar()));
        manager.addOneTile(new Tile(TILE_SIZE * 2, TILE_SIZE * 2, true, ID.JAR, img.getJar()));
        manager.addOneTile(new Tile(TILE_SIZE, TILE_SIZE * 3,true, ID.JAR, img.getJar()));
        
        //bottom right jars
        manager.addOneTile(new Tile(display.getDisplayWidth() - (TILE_SIZE * 2), display.getDisplayHeight() - (TILE_SIZE * 2),
                true, ID.JAR, img.getJar()));
        manager.addOneTile(new Tile(display.getDisplayWidth() - (TILE_SIZE * 3), display.getDisplayHeight() - (TILE_SIZE * 2),
                true, ID.JAR, img.getJar()));
        manager.addOneTile(new Tile(display.getDisplayWidth() - (TILE_SIZE * 2), display.getDisplayHeight() - (TILE_SIZE * 3),
                true, ID.JAR, img.getJar()));
    }
    
    /** portal to map 2 is in 20, 20 and to map 4 is in 380, 360 */
    //TODO when player activates marble and is teleported from map 1 to map 3, the area is too small and causing bugs
    //TODO increase that area and allow player to move around the enclosed area
    private void drawMap3() {
        //top left corner wall
        manager.addOneTile(new Tile(0, TILE_SIZE, true, ID.TILE, img.getWall01()));
        
        //top horizontal wall should be very similar to bottom horizontal wall from drawMap02
        //portal to map 2
        Tile portalToMap2 = new Tile(TILE_SIZE, TILE_SIZE, false, ID.PORTAL, img.getPortal());
        manager.addOneTile(portalToMap2);
        portals.addPortal(portalToMap2);
        portals.addPortalMapping(portalToMap2, 2);
        manager.addOneTile(new Tile(TILE_SIZE * 2, TILE_SIZE, false, ID.TILE, img.getFloor()));
        drawTilesHorizontally(TILE_SIZE * 3, TILE_SIZE, display.getDisplayWidth() - TILE_SIZE, 
                true, ID.TILE, img.getWall04());
        
        //left vertical wall
        drawTilesVertically(0, TILE_SIZE * 2, display.getDisplayHeight() / 2,
                true, ID.TILE, img.getWall03());
        manager.addOneTile(new Tile(0, display.getDisplayHeight() / 2,  true, ID.TILE, img.getBird()));
        drawTilesVertically(0, (display.getDisplayHeight() / 2) + TILE_SIZE, 
                display.getDisplayHeight() - TILE_SIZE, true, ID.TILE, img.getWall03());
        //bottom left corner wall
        manager.addOneTile(new Tile(0, display.getDisplayHeight() - TILE_SIZE, true, ID.TILE, img.getWall02()));
        //bottom horizontal wall with lasers in between
        drawTilesHorizontally(TILE_SIZE, display.getDisplayHeight() - TILE_SIZE, 
                display.getDisplayWidth() / 4, true, ID.TILE, img.getWall04());
        manager.addOneTile(new Tile(display.getDisplayWidth() / 4, display.getDisplayHeight() - TILE_SIZE,
                true, ID.TILE, img.getLaserFaceUp()));
        drawTilesHorizontally((display.getDisplayWidth() / 4) + TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                3 * (display.getDisplayWidth() / 4), true, ID.TILE, img.getWall04());
        manager.addOneTile(new Tile(3 * (display.getDisplayWidth() / 4), display.getDisplayHeight() - TILE_SIZE,
                true, ID.TILE, img.getLaserFaceUp()));
        drawTilesHorizontally(3 * (display.getDisplayWidth() / 4) + TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                display.getDisplayWidth(), true, ID.TILE, img.getWall04());
        //top right corner wall
        manager.addOneTile(new Tile(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE, true, ID.TILE, img.getWall02()));
        //right vertical wall
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE * 2,
                display.getDisplayHeight() - (TILE_SIZE * 3), true, ID.TILE, img.getWall03());
        //right floor by in between vertical walls
        manager.addOneTile(new Tile(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - (TILE_SIZE * 3),
                false, ID.TILE, img.getFloor()));
        
        //portal to map 4
        Tile portalToMap4 = new Tile(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - (TILE_SIZE * 2),
                false, ID.PORTAL, img.getPortal());
        manager.addOneTile(portalToMap4);
        portals.addPortal(portalToMap4);
        portals.addPortalMapping(portalToMap4, 4);
        
        //bottom right corner wall
        manager.addOneTile(new Tile(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                true, ID.TILE, img.getWall01()));
        //silver square at the far left from center
        manager.addOneTile(new Tile((display.getDisplayWidth() / 2) - (TILE_SIZE * 2), display.getDisplayHeight() / 2,
                true, ID.TILE, img.getSilverSquareWall()));
        //silver square far right from center
        manager.addOneTile(new Tile((display.getDisplayWidth() / 2) + (TILE_SIZE * 2), display.getDisplayHeight() / 2,
                true, ID.TILE, img.getSilverSquareWall()));
        //top and bottom silver squares from center
        for (int i = (display.getDisplayWidth() / 2) - TILE_SIZE; i <= (display.getDisplayWidth() / 2) + TILE_SIZE; i += TILE_SIZE) {
            manager.addOneTile(new Tile(i, (display.getDisplayHeight() / 2) - TILE_SIZE, true, ID.TILE, img.getSilverSquareWall()));
            manager.addOneTile(new Tile(i, (display.getDisplayHeight() / 2) + TILE_SIZE,
                                            true, ID.TILE, img.getSilverSquareWall()));
        }
        //treasure chest
        manager.addOneTile(new Tile((display.getDisplayWidth() / 2) - TILE_SIZE, display.getDisplayHeight() / 2,
                true, ID.CONTAINER, img.getTreasureChest()));
        //mid floor
        manager.addOneTile(new Tile(display.getDisplayWidth() / 2, display.getDisplayHeight() / 2, true, ID.TILE, img.getFloor()));
        //marble
        manager.addOneTile(new Tile((display.getDisplayWidth() / 2) + TILE_SIZE, display.getDisplayHeight() / 2,
                true, ID.MARBLE, img.getMarbleSwitch()));
        //the rest is floor
        //top left quadrilateral section of floor
        drawTilesBy(TILE_SIZE, TILE_SIZE * 2, (display.getDisplayWidth() / 2) - TILE_SIZE,
                display.getDisplayHeight() / 2, false, ID.TILE, img.getFloor());
        //top mid quad section of floor
        drawTilesBy((display.getDisplayWidth() / 2) - TILE_SIZE, TILE_SIZE * 2, 
                (display.getDisplayWidth() / 2) + (TILE_SIZE * 2), (display.getDisplayHeight() / 2) - TILE_SIZE,
                false, ID.TILE, img.getFloor());
        //top right quad section of floor
        drawTilesBy((display.getDisplayWidth() / 2) + (TILE_SIZE * 2), TILE_SIZE * 2,
                display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() / 2,
                false, ID.TILE, img.getFloor());
        //left mid section of floor
        drawTilesHorizontally(TILE_SIZE, display.getDisplayHeight() / 2, 
                (display.getDisplayWidth() / 2) - (TILE_SIZE * 2), false, ID.TILE, img.getFloor());
        //right mid section of floor
        drawTilesHorizontally((display.getDisplayWidth() / 2) + (TILE_SIZE * 3), display.getDisplayHeight() / 2,
                display.getDisplayWidth() - TILE_SIZE, false, ID.TILE, img.getFloor());
        //bottom left section of floor
        drawTilesBy(TILE_SIZE, (display.getDisplayHeight() / 2) + TILE_SIZE, 
                (display.getDisplayWidth() / 2) - TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                false, ID.TILE, img.getFloor());
        //bottom mid section of floor
        drawTilesBy((display.getDisplayWidth() / 2) - TILE_SIZE, (display.getDisplayHeight() / 2) + (TILE_SIZE * 2),
                (display.getDisplayWidth() / 2) + (TILE_SIZE * 2), display.getDisplayHeight() - TILE_SIZE,
                false, ID.TILE, img.getFloor());
        //bottom right section of floor
        drawTilesBy((display.getDisplayWidth() / 2) + (TILE_SIZE * 2), (display.getDisplayHeight() / 2) + TILE_SIZE,
                display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                false, ID.TILE, img.getFloor());
    }
    
    private void drawMap4() {
        //top left corner wall
        manager.addOneTile(new Tile(0, TILE_SIZE, true, ID.TILE, img.getWall01()));
        //top horizontal walls
        drawTilesHorizontally(TILE_SIZE, TILE_SIZE, TILE_SIZE * 5, true, ID.TILE, img.getWall04());
        manager.addOneTile(new Tile(TILE_SIZE * 5, TILE_SIZE, true, ID.TILE, img.getLaserFaceDown()));
        drawTilesHorizontally(TILE_SIZE * 6, TILE_SIZE, display.getDisplayWidth() - (TILE_SIZE * 5),
                true, ID.TILE, img.getWall04());
        manager.addOneTile(new Tile(display.getDisplayWidth() - (TILE_SIZE * 5), TILE_SIZE,
                                    true, ID.TILE, img.getLaserFaceDown()));
        drawTilesHorizontally(display.getDisplayWidth() - (TILE_SIZE * 4), TILE_SIZE, 
                display.getDisplayWidth() - TILE_SIZE, true, ID.TILE, img.getWall04());
        //top right corner wall
        manager.addOneTile(new Tile(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE,
                true, ID.TILE, img.getWall02()));
        //right vertical wall
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE * 2,
                display.getDisplayHeight() - (TILE_SIZE * 2), true, ID.TILE, img.getWall03());
        //bottom right corner wall
        manager.addOneTile(new Tile(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                true, ID.TILE, img.getWall01()));
        //bottom left corner wall
        manager.addOneTile(new Tile(0, display.getDisplayHeight() - TILE_SIZE, true, ID.TILE, img.getWall02()));
        //bottom horizontal wall
        drawTilesHorizontally(TILE_SIZE, display.getDisplayHeight() - TILE_SIZE, TILE_SIZE * 5,
                true, ID.TILE, img.getWall04());
        manager.addOneTile(new Tile(TILE_SIZE * 5, display.getDisplayHeight() - TILE_SIZE,
                            true, ID.TILE, img.getLaserFaceUp()));
        drawTilesHorizontally(TILE_SIZE * 6, display.getDisplayHeight() - TILE_SIZE,
                display.getDisplayWidth() - (TILE_SIZE * 5), true, ID.TILE, img.getWall04());
        manager.addOneTile(new Tile(display.getDisplayWidth() - (TILE_SIZE * 5), display.getDisplayHeight() - TILE_SIZE,
                true, ID.TILE, img.getLaserFaceUp()));
        drawTilesHorizontally(display.getDisplayWidth() - (TILE_SIZE * 4), display.getDisplayHeight() - TILE_SIZE,
                display.getDisplayWidth() - TILE_SIZE, true, ID.TILE, img.getWall04());
        //left vertical wall
        drawTilesVertically(0, TILE_SIZE * 2, display.getDisplayHeight() - (TILE_SIZE * 3),
                true, ID.TILE, img.getWall03());
        drawTilesVertically(0, display.getDisplayHeight() - (TILE_SIZE * 3), 
                display.getDisplayHeight() - TILE_SIZE, false, ID.TILE, img.getFloor());
        //black space and silver squares (pressed) in the middle
        for (int y = TILE_SIZE * 2; y < display.getDisplayHeight() - TILE_SIZE; y += TILE_SIZE) {
            manager.addOneTile(new Tile(TILE_SIZE * 5, y, true, ID.TILE, img.getVoidSpace()));
            manager.addOneTile(new Tile(display.getDisplayWidth() - (TILE_SIZE * 5), y, true, ID.TILE, img.getVoidSpace()));
            manager.addOneTile(new Tile(((TILE_SIZE * 5) + (display.getDisplayWidth() - (TILE_SIZE * 5))) / 2,
                    y, false, ID.TILE, img.getSilverSquare()));
            //black square walls in between the two vertical black space
            for (int x = TILE_SIZE * 6; x < (((TILE_SIZE * 5) + (display.getDisplayWidth() - (TILE_SIZE * 5))) / 2);
                x += TILE_SIZE) {
                manager.addOneTile(new Tile(x, y, true, ID.TILE, img.getBlackWall()));
            }
            for (int x = (((TILE_SIZE * 5) + (display.getDisplayWidth() - (TILE_SIZE * 5))) / 2) + TILE_SIZE;
                    x < display.getDisplayWidth() - (TILE_SIZE * 5); x += TILE_SIZE) {
                manager.addOneTile(new Tile(x, y, true, ID.TILE, img.getBlackWall()));
            }
        }
        //black void space going horizontally
        drawTilesHorizontally(TILE_SIZE, display.getDisplayHeight() / 2, TILE_SIZE * 5,
                true, ID.TILE, img.getVoidSpace());
        drawTilesHorizontally(display.getDisplayWidth() - (TILE_SIZE * 4), display.getDisplayHeight() / 2,
                display.getDisplayWidth() - TILE_SIZE, true, ID.TILE, img.getVoidSpace());
        //top left section of floor
        drawTilesBy(TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 5, display.getDisplayHeight() / 2,
                false, ID.TILE, img.getFloor());
        //top right section of floor
        drawTilesBy(display.getDisplayWidth() - (TILE_SIZE * 4), TILE_SIZE * 2,
                display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() / 2,
                false, ID.TILE, img.getFloor());
        //bottom left section of floor
        drawTilesBy(TILE_SIZE, (display.getDisplayHeight() / 2) + TILE_SIZE, TILE_SIZE * 5,
                display.getDisplayHeight() - TILE_SIZE, false, ID.TILE, img.getFloor());
        //bottom right section of floor
        drawTilesBy(display.getDisplayWidth() - (TILE_SIZE * 4), (display.getDisplayHeight() / 2) + TILE_SIZE,
                display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                false, ID.TILE, img.getFloor());
    }
    
    //TODO this is the final map which includes the boss and is not quite yet finished
    //TODO i want to finish it after all the non-dungeon maps are implemented
    private void drawMap5() {
        //top left corner wall
        manager.addOneTile(new Tile(0, TILE_SIZE, true, ID.TILE, img.getWall01()));
        //top horizontal walls, lasers, door
        int xSpacing = display.getDisplayWidth() / 5;
        drawTilesHorizontally(TILE_SIZE, TILE_SIZE, xSpacing, true, ID.TILE, img.getWall04());
        manager.addOneTile(new Tile(xSpacing, TILE_SIZE, true, ID.TILE, img.getLaserFaceDown()));
        drawTilesHorizontally(xSpacing + TILE_SIZE, TILE_SIZE, xSpacing * 2, 
                            true, ID.TILE, img.getWall04());
        manager.addOneTile(new Tile(xSpacing * 2, TILE_SIZE, true, ID.TILE, img.getLaserFaceDown()));
        drawTilesHorizontally((xSpacing * 2) + TILE_SIZE, TILE_SIZE, xSpacing * 4,
                            true, ID.TILE, img.getWall04());
        manager.addOneTile(new Tile(xSpacing * 4, TILE_SIZE, true, ID.LOCK, img.getLockedDoor()));
        drawTilesHorizontally((xSpacing * 4) + TILE_SIZE, TILE_SIZE, (xSpacing * 5) - TILE_SIZE,
                            true, ID.TILE, img.getWall04());
        //top right corner wall
        manager.addOneTile(new Tile((xSpacing * 5) - TILE_SIZE, TILE_SIZE, true, ID.TILE, img.getWall01()));
        //left vertical wall
        int ySpacing = display.getDisplayHeight() / 5;
        drawTilesVertically(0, TILE_SIZE * 2, ySpacing, true, ID.TILE, img.getWall03());
        manager.addOneTile(new Tile(0, ySpacing, true, ID.TILE, img.getLaserFacingRight()));
        drawTilesVertically(0, ySpacing + TILE_SIZE, ySpacing * 3, true, ID.TILE, img.getWall03());
        manager.addOneTile(new Tile(0, ySpacing * 3, true, ID.TILE, img.getLaserFacingRight()));
        drawTilesVertically(0, (ySpacing * 3) + TILE_SIZE, (ySpacing * 5) - TILE_SIZE, true,
                                ID.TILE, img.getWall03());
        //bottom left corner wall
        manager.addOneTile(new Tile(0, display.getDisplayHeight() - TILE_SIZE, true,
                                ID.TILE, img.getWall02()));
        //bottom right corner wall
        manager.addOneTile(new Tile(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - TILE_SIZE,
                                true, ID.TILE, img.getWall01()));
        //right vertical wall
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE * 2, ySpacing, true, ID.TILE,
                img.getWall03());
        manager.addOneTile(new Tile(display.getDisplayWidth() - TILE_SIZE, ySpacing, true, ID.TILE,
                img.getLaserFacingRight()));
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, ySpacing + TILE_SIZE, ySpacing * 3,
                true, ID.TILE, img.getWall03());
        manager.addOneTile(new Tile(display.getDisplayWidth() - TILE_SIZE, ySpacing * 3, true, ID.TILE,
                img.getLaserFacingRight()));
        drawTilesVertically(display.getDisplayWidth() - TILE_SIZE, (ySpacing * 3) + TILE_SIZE,
                (ySpacing * 5) - TILE_SIZE, true, ID.TILE, img.getWall03());
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
                display.getDisplayHeight() - TILE_SIZE, false, ID.TILE, img.getGrass());
    }
    
    //this is the non-dungeon map that connects the outer world to the dungeon
    private void drawMap10() {
        int x = (display.getDisplayWidth() / 8) * 3; //when frame is 400x400 this is 150
        //default sand layer
        manager.addOneTile(new Tile(x - TILE_SIZE, TILE_SIZE * 2, false, ID.TILE, img.getSand()));
        drawTilesBy(x - TILE_SIZE, TILE_SIZE * 3, display.getDisplayWidth() - TILE_SIZE,
                display.getDisplayHeight() - TILE_SIZE, false, ID.TILE, img.getSand());
        drawTilesHorizontally(TILE_SIZE, display.getDisplayHeight() - (TILE_SIZE * 2),
                display.getDisplayWidth() - (TILE_SIZE * 2), false, ID.TILE, img.getSand());
        drawTilesHorizontally(x + (TILE_SIZE * 4), TILE_SIZE * 2,
                display.getDisplayWidth() - TILE_SIZE, false, ID.TILE, img.getSand());
        
        //default dirt layer
        drawTilesBy(TILE_SIZE, TILE_SIZE * 2, x - TILE_SIZE, 
                display.getDisplayHeight() - (TILE_SIZE * 2), false, ID.TILE, img.getDirt());
        
        //top left corner
        manager.addOneTile(new Tile(0, TILE_SIZE, true, ID.TILE, img.getBotRightOfTree()));
        
        //top horizontal trees
        for (int i = TILE_SIZE; i < x; i += TILE_SIZE * 2) {
            Tile botLeftOfTree = new Tile(i, TILE_SIZE, true, ID.TILE, img.getBotLeftOfTree());
            Tile botRightOfTree = new Tile(i + TILE_SIZE, TILE_SIZE, true, ID.TILE, img.getBotRightOfTree());
            manager.addOneTile(botLeftOfTree);
            manager.addOneTile(botRightOfTree);
        }
        for (int i = x + (TILE_SIZE * 5); i < display.getDisplayWidth() - TILE_SIZE; i += TILE_SIZE * 2) {
            Tile botLeftOfTree = new Tile(i, TILE_SIZE, true, ID.TILE, img.getBotLeftOfTree());
            Tile botRightOfTree = new Tile(i + TILE_SIZE, TILE_SIZE, true, ID.TILE, img.getBotRightOfTree());
            manager.addOneTile(botLeftOfTree);
            manager.addOneTile(botRightOfTree);
        }
        
        //dungeon entrance
        manager.addOneTile(new Tile(x, TILE_SIZE, TILE_SIZE * 2,
                TILE_SIZE * 2, true, ID.TILE, img.getBottomLeftOfDungeonEntrance()));
        
        Tile portalToMap1 = new Tile(x + (TILE_SIZE * 2), TILE_SIZE, TILE_SIZE, TILE_SIZE * 2,
                false, ID.PORTAL, img.getDoorToDungeon());
        manager.addOneTile(portalToMap1);
        manager.addOneTile(portalToMap1);
        portals.addPortal(portalToMap1);
        portals.addPortalMapping(portalToMap1, 1);
        
        manager.addOneTile(new Tile(x + (TILE_SIZE * 3), TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 2,
                true, ID.TILE, img.getBottomRightOfDungeonEntrance()));
        
        //top right corner
        manager.addOneTile(new Tile(display.getDisplayWidth() - TILE_SIZE, TILE_SIZE, true, ID.TILE,
                img.getBotLeftOfTree()));
        
        //right vertical trees
        x = display.getDisplayWidth() - TILE_SIZE;
        for (int j = TILE_SIZE * 2; j < display.getDisplayHeight() - TILE_SIZE; j += TILE_SIZE * 2) {
            Tile topLeftOfTree = new Tile(x, j, true, ID.TILE, img.getTopLeftOfTree());
            Tile botLeftOfTree = new Tile(x, j + TILE_SIZE, true, ID.TILE, img.getBotLeftOfTree());
            manager.addOneTile(topLeftOfTree);
            manager.addOneTile(botLeftOfTree);
        }
        
        //left vertical trees
        for (int j = TILE_SIZE * 2; j < display.getDisplayHeight() - (TILE_SIZE * 3); j += TILE_SIZE * 2) {
            Tile topRightOfTree= new Tile(0, j, false, ID.TILE, img.getTopRightOfTree());
            Tile botRightOfTree = new Tile(0, j + TILE_SIZE, false, ID.TILE, img.getBotRightOfTree());
            manager.addOneTile(topRightOfTree);
            manager.addOneTile(botRightOfTree);
        }
        
        //weird statues
        Tile weirdStatue = new Tile((display.getDisplayWidth() / 8) * 3, display.getDisplayHeight() / 2,
                TILE_SIZE, TILE_SIZE * 2, true, ID.TILE, img.getWeirdStatue());
        manager.addOneTile(weirdStatue);
        Tile weirdStatue2 = new Tile((display.getDisplayWidth() / 8) * 5, 3 * (display.getDisplayHeight() / 4),
                TILE_SIZE, TILE_SIZE * 2, true, ID.TILE, img.getWeirdStatue());
        manager.addOneTile(weirdStatue2);
        Tile weirdStatue3 = new Tile((display.getDisplayWidth() / 8) * 7, display.getDisplayHeight() / 2,
                TILE_SIZE, TILE_SIZE * 2, true, ID.TILE, img.getWeirdStatue());
        manager.addOneTile(weirdStatue3);
        
        //portal to map 9
        Tile portalToMap9 = new Tile(0, display.getDisplayHeight() - (TILE_SIZE * 2), false, ID.PORTAL,
                img.getPortal());
        manager.addOneTile(portalToMap9);
        portals.addPortal(portalToMap9);
        portals.addPortalMapping(portalToMap9, 9);
        
        //bottom horizontal wall
        drawTilesHorizontally(0, display.getDisplayHeight() - TILE_SIZE, 
                display.getDisplayWidth(), true, ID.TILE, img.getWall05());
    }

    private void drawTilesHorizontally(int x, int y, int horizontalLength, boolean isWall, ID id, BufferedImage img) {
        for (int i = x; i < horizontalLength; i += TILE_SIZE) {
            manager.addOneTile(new Tile(i, y, isWall, id, img));
        }
    }

    private void drawTilesVertically(int x, int y, int verticalLength, boolean isWall, ID id, BufferedImage img) {
        for (int j = y; j < verticalLength; j += TILE_SIZE) {
            manager.addOneTile(new Tile(x, j, isWall, id, img));
        }
    }

    private void drawTilesBy(int x, int y, int horizontalLength, int verticalLength, boolean isWall, ID id, BufferedImage img) {
        for (int i = x; i < horizontalLength; i += TILE_SIZE) {
            for (int j = y; j < verticalLength; j += TILE_SIZE) {
                manager.addOneTile(new Tile(i, j, isWall, id, img));
            }
        }
    }
    
    private void clearMap() {
        manager.getTiles().clear();
        portals = new PortalID();
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
    
    public PortalID getPortals() {
        return portals;
    }
    
}
