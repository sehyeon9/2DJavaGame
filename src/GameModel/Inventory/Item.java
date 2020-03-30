package GameModel.Inventory;

import Identifier.Item.Type;

import java.awt.image.BufferedImage;

/**
 * The type tells which type of item it is classified as. For example, there may be 3 HP potions all classified under
 * the same type. To tell them apart, a name has to be given to the item so that one hp potion may increase health by
 * half heart, but another one by 1 full heart etc.
 * img gives us a visual description of the item
 */
public class Item {
    
    private Type type;
    private String name;
    private BufferedImage img;
    
    public Item(Type type, String name, BufferedImage img) {
        this.type = type;
        this.name = name;
        this.img = img;
    }
    
    public Type getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }
    
    public BufferedImage getImg() {
        return img;
    }
    
}
