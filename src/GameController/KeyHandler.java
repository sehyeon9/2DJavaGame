package GameController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This object handles user's keyboard inputs and assigns corresponding variables
 * true if a certain key is pressed and false if released
 */
public class KeyHandler implements KeyListener {

    private boolean[] keys;
    public boolean up, down, left, right, interact;

    public KeyHandler() {
        keys = new boolean[256];
    }

    public void update() {
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        interact = keys[KeyEvent.VK_F];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
}
