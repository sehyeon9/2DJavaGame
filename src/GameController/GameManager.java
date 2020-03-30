package GameController;

import GameModel.Enemy;
import GameModel.Game;
import GameModel.Inventory.Item;
import GameModel.Player;
import GameModel.Tile;

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

    private void playerInteractWithEnemies(Enemy enemy) {
        if (player.getBoundsTop().intersects(enemy.getX(), enemy.getY() + enemy.getHeight(), enemy.getWidth(), 0.01)) {
            int y = player.getY();
            y += MOVE_STEP * RECOIL_WHEN_HIT_BY_ENEMY;
            player.setY(y);
            int health = player.getHealth();
            player.setHealth(--health);
        } else if (player.getBoundsBot().intersects(enemy.getX(), enemy.getY() + 0.01, enemy.getWidth(), 0.01)) {
            int y = player.getY();
            y -= MOVE_STEP * RECOIL_WHEN_HIT_BY_ENEMY;
            player.setY(y);
            int health = player.getHealth();
            player.setHealth(--health);
        } else if (player.getBoundsLeft().intersects(enemy.getX() + enemy.getWidth() , enemy.getY(), 0.01, enemy.getHeight())) {
            int x = player.getX();
            x += MOVE_STEP * RECOIL_WHEN_HIT_BY_ENEMY;
            player.setX(x);
            int health = player.getHealth();
            player.setHealth(--health);
        } else if (player.getBoundsRight().intersects(enemy.getX(), enemy.getY(), 0.01, enemy.getHeight())) {
            int x = player.getX();
            x -= MOVE_STEP * RECOIL_WHEN_HIT_BY_ENEMY;
            player.setX(x);
            int health = player.getHealth();
            player.setHealth(--health);
        }
        if (player.getInventory().getUniqueItems().contains("sabre")) {
            enemy.setHealth(enemy.getHealth() - 30);
        }
    }
    
}
