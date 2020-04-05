package GameController;

import GameModel.*;
import GameModel.Inventory.Item;
import Identifier.ID;
import Identifier.Item.ItemType;
import Identifier.Portal.PortalID;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * This object holds all the tiles, enemies, and the player in one map
 */
public class GameManager {
    
    private Game game;
    
    private Player player;
    private List<Enemy> enemies;
    private List<Tile> tiles;
    private List<Item> items;
    private PortalID portals;
    private int windowWidth;
    private int windowHeight;
    
    private boolean guardianHasDied;
    private boolean marbleUnlocked;

    private static final double MOVE_STEP = 0.1;
    //This recoil number is 300 so it "bounces" the player back by some steps so they will have to move towards the enemy
    //in order to engage the enemy again if hit
    private static final int RECOIL_WHEN_HIT_BY_ENEMY = 300;
    
    public GameManager(Player player, Game game) {
        this.game = game;
        windowWidth = 400;
        windowHeight = 400;
        this.player = player;
        this.enemies = new LinkedList<>();
        this.tiles = new LinkedList<>();
        this.items = new LinkedList<>();
        portals = new PortalID();
        guardianHasDied = false;
        marbleUnlocked = false;
    }

    /**
     * The tiles must come first because we want the map displayed on the screen 
     * and everything else can go on top of it
     */
    public void draw(Graphics g) {
        for (Tile tile : tiles) {
            tile.draw(g);
        }
        for (Item item : items) {
            item.draw(g);
        }
        player.draw(g);
        for (Enemy enemy : enemies) {
            if (game.getMapID() == 3) {
                if (enemy.isAlive()) {
                    enemy.draw(g);
                }
            }
        }
    }

    //Order of tile, item, player, enemy really matter
    public void update() {
//        for (Tile tile : tiles) {
//            tile.update();
//        }
        for (Item item : items) {
            item.update();
        }
        player.update();
        playerInteractWithTiles();
        playerInteractWithItem();
        for (Enemy enemy : enemies) {
            if (game.getMapID() == 3) {
                if (enemy.isAlive()) {
                    enemy.update();
                    playerInteractWithEnemies(enemy);
                } else {
                    guardianHasDied = true;
                }
            }
        }
        if (guardianHasDied) {
            unlockMarbleFeature();
        }
    }
    
    public void addTile(int x, int y, BufferedImage image) {
        Tile tile = new Tile(x, y, image, game);
        tiles.add(tile);
    }
    
    public void addTile(int x, int y, int width, int height, BufferedImage img) {
        Tile tile = new Tile(x, y, width, height, img, game);
        tiles.add(tile);
    }
    
    public void addPortal(int x, int y, BufferedImage image, int mapID) {
        Tile tile = new Tile(x, y, image, game);
        tiles.add(tile);
        if (image == game.getImg().getPortal() || image == game.getImg().getDoorToDungeon()) {
            portals.addPortal(tile);
            portals.addPortalMapping(tile, mapID);
        }
    }

    public void addPortal(int x, int y, int width, int height, BufferedImage image, int mapID) {
        Tile tile = new Tile(x, y, width, height, image, game);
        tiles.add(tile);
        if (image == game.getImg().getPortal() || image == game.getImg().getDoorToDungeon()) {
            portals.addPortal(tile);
            portals.addPortalMapping(tile, mapID);
        }
    }
    
    public void removeTile(Tile tile) {
        tiles.remove(tile);
    }
    
    public List<Tile> getTiles() {
        return tiles;
    }
    
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }
    
    public List<Enemy> getEnemies() {
        return enemies;
    }
    
    public void addItem(Item item) {
        items.add(item);
    }
    
    public void removeItem(Item item) {
        items.remove(item);
    }
    
    public List<Item> getItems() {
        return items;
    }

    private void playerInteractWithTiles() {
        for (Tile tile : tiles) {
            checkForCollision(tile);
            playerInteractWithPortal(tile);
            playerInteractWithJar(tile);
            playerInteractWithMarble(tile);
            playerInteractWithChest(tile);
        }
    }
    
    private void playerInteractWithPortal(Tile tile) {
        //i could probably reduce code here and produce same effect
        if (tile.getID() == ID.PORTAL && player.getBounds().intersects(tile.getBounds())) {
            int portalID = portals.getMappedPortal(tile);
            if (game.getMapID() == 1) {
                if (portalID == 2) {
                    player.setX(21);
                    player.setY(windowHeight / 3);
                    game.changeMap(2);
                } else if (portalID == 10) {
                    player.setX(((windowWidth / 8) * 3) + 40);
                    player.setY(61);
                    game.changeMap(10);
                }
            } else if (game.getMapID() == 2) {
                if (portalID == 3) {
                    player.setX(21);
                    player.setY(40);
                    game.changeMap(3);
                } else if (portalID == 1) {
                    player.setX(windowWidth - 41);
                    player.setY(windowHeight / 3);
                    game.changeMap(1);
                }
            } else if (game.getMapID() == 3) {
                if (portalID == 4) {
                    player.setX(21);
                    player.setY(windowHeight - 40);
                    game.changeMap(4);
                } else if (portalID == 2) {
                    player.setX(20);
                    player.setY(windowHeight - 40);
                    game.changeMap(2);
                }
            } else if (game.getMapID() == 4) {
                if (portalID == 5) {
                    game.changeMap(5);
                } else if (portalID == 3) {
                    player.setX(windowWidth - 40);
                    game.changeMap(3);
                }
            } else if (game.getMapID() == 5) {
                if (portalID == 4) {
                    game.changeMap(4);
                }
            } else if (game.getMapID() == 6) {
                if (portalID == 7) {
                    player.setX(20);
                    game.changeMap(7);
                }
            } else if (game.getMapID() == 7) {
                if (portalID == 6) {
                    player.setX(windowWidth - 40);
                    game.changeMap(6);
                } else if (portalID == 8) {
                    player.setX(21);
                    game.changeMap(8);
                } else if (portalID == 9) {
                    player.setY(40);
                    game.changeMap(9);
                }
            } else if (game.getMapID() == 8) {
                if (portalID == 7) {
                    player.setX(windowWidth - 40);
                    game.changeMap(7);
                }
            } else if (game.getMapID() == 9) {
                if (portalID == 7) {
                    player.setY(windowHeight - 40);
                    game.changeMap(7);
                } else if (portalID == 10) {
                    player.setX(20);
                    player.setY(windowHeight - 40);
                    game.changeMap(10);
                }
            } else if (game.getMapID() == 10) {
                if (portalID == 1) {
                    player.setX(21);
                    player.setY(windowHeight / 3);
                    game.changeMap(1);
                } else if (portalID == 9) {
                    player.setX(windowWidth - 40);
                    player.setY(windowHeight - 40);
                    game.changeMap(9);
                }
            }
        }
    }

    private void playerInteractWithJar(Tile tile) {
        if (player.getBounds().intersects(tile.getBounds()) && tile.getID() == ID.JAR && game.getKeyHandler().interact) {
            tile.setTileProperty(false, ID.TILE, game.getImg().getFloor());
            //certain jars drop certain items
            if (tile.getX() == 20 && tile.getY() == 60) {
                Item weapon = new Item(tile.getX(), tile.getY(), true, ItemType.Weapon, "sabre",
                        game.getImg().getSabre());
                addItem(weapon);
            } else if (tile.getX() == 360 && tile.getY() == 360) {
                Item key = new Item(tile.getX(), tile.getY(), true, ItemType.Key, "key",
                        game.getImg().getKey());
                addItem(key);
            }
        }
    }

    private void playerInteractWithItem() {
        List<Item> removedItems = new LinkedList<>();
        for (Item item : game.getManager().getItems()) {
            checkForCollision(item);
            if (player.getBounds().intersects(item.getBounds()) && game.getKeyHandler().interact) {
                if (!player.getInventory().isFull()) {
                    player.getInventory().addItem(item);
                    removedItems.add(item);
                }
            }
        }
        for (Item item : removedItems) {
            if (game.getManager().getItems().contains(item)) {
                game.getManager().removeItem(item);
            }
        }
    }

    /**
     * x and y must not be integers, otherwise it will not be an accurate collision
     * @param object any GameObject the player may come in contact with
     * post: revert player's position
     */
    private void checkForCollision(GameObject object) {
        if (object.isWall()) {
            double y = player.getYWithAccuracy();
            double x = player.getXWithAccuracy();
            if (player.getBoundsTop().intersects(object.getBounds())) {
                y += MOVE_STEP;
                player.setY(y);
            }
            if (player.getBoundsBot().intersects(object.getBounds())) {
                y -= MOVE_STEP;
                player.setY(y);
            }
            if (player.getBoundsLeft().intersects(object.getBounds())) {
                x += MOVE_STEP;
                player.setX(x);
            }
            if (player.getBoundsRight().intersects(object.getBounds())) {
                x -= MOVE_STEP;
                player.setX(x);
            }
        }
    }

    //TODO: maybe a good way to go about this is put enemy.setHealth(enemy.getHealth() - player.getWeapon().getDamage();
    private void playerInteractWithEnemies(Enemy enemy) {
        if (player.getBoundsTop().intersects(enemy.getX(), enemy.getY() + enemy.getHeight(), enemy.getWidth(), 0.01)) {
            int y = player.getY();
            y += MOVE_STEP * RECOIL_WHEN_HIT_BY_ENEMY;
            player.setY(y);
            int health = player.getHealth();
            player.setHealth(--health);
            if (isPlayerArmed()) {
                enemy.setHealth(enemy.getHealth() - 30);
            }
        } else if (player.getBoundsBot().intersects(enemy.getX(), enemy.getY() + 0.01, enemy.getWidth(), 0.01)) {
            int y = player.getY();
            y -= MOVE_STEP * RECOIL_WHEN_HIT_BY_ENEMY;
            player.setY(y);
            int health = player.getHealth();
            player.setHealth(--health);
            if (isPlayerArmed()) {
                enemy.setHealth(enemy.getHealth() - 30);
            }
        } else if (player.getBoundsLeft().intersects(enemy.getX() + enemy.getWidth() , enemy.getY(), 0.01, enemy.getHeight())) {
            int x = player.getX();
            x += MOVE_STEP * RECOIL_WHEN_HIT_BY_ENEMY;
            player.setX(x);
            int health = player.getHealth();
            player.setHealth(--health);
            if (isPlayerArmed()) {
                enemy.setHealth(enemy.getHealth() - 30);
            }
        } else if (player.getBoundsRight().intersects(enemy.getX(), enemy.getY(), 0.01, enemy.getHeight())) {
            int x = player.getX();
            x -= MOVE_STEP * RECOIL_WHEN_HIT_BY_ENEMY;
            player.setX(x);
            int health = player.getHealth();
            player.setHealth(--health);
            if (isPlayerArmed()) {
                enemy.setHealth(enemy.getHealth() - 30);
            }
        }
    }
    
    //TODO this only checks for one weapon so far as the others are not yet implemented
    private boolean isPlayerArmed() {
        Set<String> weapons = player.getInventory().getUniqueItems();
        return weapons.contains("sabre");
    }
    
    private void unlockMarbleFeature() {
        if (!marbleUnlocked) {
            marbleUnlocked = true;
        }
    }
    
    private void playerInteractWithMarble(Tile tile) {
        if (player.getBounds().intersects(tile.getBounds()) && tile.getID() == ID.MARBLE && marbleUnlocked
                && game.getKeyHandler().interact && game.getMapID() == 1) {
            game.changeMap(3);
            player.setX(windowWidth / 2);
            player.setY(windowHeight / 2);
        }
    }
    
    //the logic isn't there yet, but once implemented, this allows teleportation tiles to start being drawn to map 4
    //once the player travels from map 3 to 4, the correct teleportation pattern will allow the player to get to
    //      the bottom right button on the floor
    //the button, once activated, moves the player back to map 10, right outside the dungeon entrance
    //when player enters the portal from map 3 to map 4 again, they will notice that map 4 is changed
    //the player did not spawn in the usual place, rather,
    //the player spawned at the very bottom on the central path that leads to map 5
    //map 5 is now unlocked and the final boss is waiting
    private void playerInteractWithChest(Tile tile) {
        if (player.getBounds().intersects(tile.getBounds()) && tile.getID() == ID.CONTAINER &&
                game.getKeyHandler().interact && game.getMapID() == 3 && 
                player.getInventory().getUniqueItems().contains("key")) {
            //if the player doesn't have a key for the chest, nothing happens
            for (Item item : player.getInventory().getInventory().keySet()) {
                if (item.getName().equalsIgnoreCase("key")) {
                    player.getInventory().removeItem(item);
                    tile.setTileProperty(true, ID.TILE, game.getImg().getUnlockedChest());
                    break;
                }
            }
        }
    }
    
}
