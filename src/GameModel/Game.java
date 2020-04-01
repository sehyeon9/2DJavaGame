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
            Enemy enemy = new Enemy(150, 250, 60, 60, true, ID.ENEMY, img.getBoss(),
                    this, 1000);
            manager.addEnemy(enemy);
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
                true, ID.TILE, img.getMarbleSwitch()));
        
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
    private void drawMap3() {
        //top left corner wall
        manager.addOneTile(new Tile(0, TILE_SIZE, true, ID.TILE, img.getWall01()));
        //top horizontal wall should be very similar to bottom horizontal wall from drawMap02
        manager.addOneTile(new Tile(TILE_SIZE, TILE_SIZE, false, ID.TILE, img.getPortal()));
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
        manager.addOneTile(new Tile(display.getDisplayWidth() - TILE_SIZE, display.getDisplayHeight() - (TILE_SIZE * 2),
                false, ID.PORTAL, img.getPortal()));
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
        
    }
    
    private void drawMap10() {
        
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
