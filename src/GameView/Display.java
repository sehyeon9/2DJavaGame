package GameView;

import javax.swing.*;
import java.awt.*;

public class Display {

    private JFrame frame;
    private Canvas canvas;
    private int width, height;

    public Display(int width, int height) {
        this.width = width;
        this.height = height;
        createDisplay();
    }

    private void createDisplay() {
        frame = new JFrame("Hello, World!");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);
        frame.add(canvas);
        frame.pack();
    }

    public JFrame getFrame() {
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public int getDisplayWidth() {
        return width;
    }

    public int getDisplayHeight() {
        return height;
    }
    
}
