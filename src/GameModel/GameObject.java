package GameModel;

import Identifier.ID;

import java.awt.*;

/**
 * All enemies, players, tiles, equipment, anything displayed inside our frame is a GameObject and must extend this
 */
public abstract class GameObject {

    /**
     * Graphics g - this is what draws to our frame
     * This is a must for all game objects to be able to be drawn on the frame and be seen by the user
     */
    public abstract void draw(Graphics g);

    /**
     * This updates the object's position in the frame
     */
    public abstract void update();

    /**
     * returns the current x position of our object
     */
    public abstract int getX();

    /**
     * returns the current y position of our object
     */
    public abstract int getY();

    /**
     * returns true if object is something that is hard bounded and should produce a collision effect,
     *      and false otherwise (e.g. floor objects the player object moves on top of)
     */
    public abstract boolean isWall();

    /**
     * sets the object's x position to the given, new x
     */
    public abstract void setX(int x);

    /**
     * sets the object's y position to the given, new y
     */
    public abstract void setY(int y);

    /**
     * Use this if you want to make your tile unlock to a "soft-bound" after the user triggers some event
     * or vise versa unlock a "room" after the user has entered the correct password
     * You may believe only tiles need this functionality, but for scale purposes, what if you wanted to make
     * your player or enemy visible on the frame as a ghost image? Surely ghosts can go through walls!
     * sets the tile's bounds to be hard-bounded if true otherwise soft-bounded
     */
    public abstract void setWall(boolean isWall);

    /**
     * returns an ID to identify its GameObject Type
     */
    public abstract ID getID();

    /**
     * returns a rectangle that represents the object's full size
     */
    public abstract Rectangle getBounds();

    /**
     * returns a rectangle that only represents the top bound of the object
     */
    public abstract Rectangle getBoundsTop();

    /**
     * returns a rectangle that only represents the bottom bound of the object
     */
    public abstract Rectangle getBoundsBot();

    /**
     * returns a rectangle that only represents the left bound of the object
     */
    public abstract Rectangle getBoundsLeft();

    /**
     * returns a rectangle that only represents the right bound of the object
     */
    public abstract Rectangle getBoundsRight();
    
}
