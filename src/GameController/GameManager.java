package GameController;

import GameModel.Enemy;
import GameModel.Game;
import GameModel.Player;
import GameModel.Tile;
import Identifier.ID;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This object holds all the tiles and the player in one map so the programmer does not need to write multiple lines
 */
public class GameManager {
    
    private Game game;
    private Player player;
    private Enemy enemy;
    private List<Tile> tiles;

    private static final double MOVE_STEP = 0.1;
    private static final int RECOIL_WHEN_HIT_BY_ENEMY = 300;
    
    public GameManager(Player player, Enemy enemy, Game game) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;
        tiles = new LinkedList<>();
    }
    
    public void draw(Graphics g) {
        for (Tile tile : tiles) {
            tile.draw(g);
        }
        player.draw(g);
        if (game.getMapID() == 3) {
            enemy.draw(g);
        }
    }
    
    public void update() {
        for (Tile tile : tiles) {
            tile.update();
        }
        player.update();
        if (game.getMapID() == 3) {
            enemy.update();
            playerInteractWithEnemy();
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

    private void playerInteractWithEnemy() {
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
    }
    
}
