package GameController;

import GameModel.*;
import GameModel.Inventory.Item;
import Identifier.ID;
import Identifier.Item.Type;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This object holds all the tiles and the player in one map so the programmer does not need to write multiple lines
 */
public class GameManager {
    
    private Game game;
    private Player player;
    private List<Enemy> enemies;
    private List<Tile> tiles;
    private List<Item> items;

    private static final double MOVE_STEP = 0.1;
    private static final int RECOIL_WHEN_HIT_BY_ENEMY = 300;
    
    public GameManager(Player player, Game game) {
        this.game = game;
        this.player = player;
        this.enemies = new LinkedList<>();
        this.tiles = new LinkedList<>();
        this.items = new LinkedList<>();
    }
    
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
    
    public void update() {
        for (Tile tile : tiles) {
            tile.update();
        }
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

                }
            }
        }
    }
    
    public void addOneTile(Tile tile) {
        tiles.add(tile);
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
        for (Tile tile : game.getManager().getTiles()) {
            checkForCollision(tile);
            //TODO: have player move to any neighboring maps instead of one
            if (tile.getID() == ID.PORTAL) {
                if (player.getBounds().intersects(tile.getBounds()) && game.getMapID() == 1) {
                    player.setX(10);
                    player.setY(game.getDisplay().getDisplayHeight() / 3);
                    game.changeMap(2);
                } else if (player.getBounds().intersects(tile.getBounds()) && game.getMapID() == 2) {
                    player.setX(20);
                    player.setY(game.getDisplay().getDisplayHeight() / 3);
                    game.changeMap(3);
                } else if (player.getBounds().intersects(tile.getBounds()) && game.getMapID() == 3) {
                    player.setX(20);
                    player.setY(game.getDisplay().getDisplayHeight() - 40);
                    game.changeMap(4);
                }
            }
            playerInteractWithJars(tile);
        }
    }

    private void playerInteractWithJars(Tile tile) {
        if (player.getBounds().intersects(tile.getBounds()) && tile.getID() == ID.JAR && game.getKeyHandler().interact) {
            tile.setTileProperty(false, ID.TILE, game.getImg().getFloor());
            Item weapon = new Item(tile.getX(), tile.getY(), true, Type.Weapon, "sabre", game.getImg().getSabre());
            addItem(weapon);
        }
    }

    private void playerInteractWithItem() {
        List<Item> removedItems = new LinkedList<>();
        for (Item item : game.getManager().getItems()) {
            checkForCollision(item);
            if (player.getBounds().intersects(item.getBounds()) && game.getKeyHandler().interact) {
                if (item.getImg() == game.getImg().getSabre()) {
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
    
    private boolean isPlayerArmed() {
        return player.getInventory().getUniqueItems().contains("sabre");
    }
    
}
