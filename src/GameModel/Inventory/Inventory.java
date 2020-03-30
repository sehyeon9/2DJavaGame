package GameModel.Inventory;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    
    private Map<Item, Integer> inventory;
    private int size;
    
    public Inventory() {
        this.inventory = new HashMap<>();
        this.size = 10;
    }
    
    public void addItem(Item item) {
        if (inventory.size() >= size) {
            //display "Bag full!" message
        } else {
            if (!inventory.containsKey(item)) {
                inventory.put(item, 0);
            }
            inventory.put(item, inventory.get(item) + 1);
        }
    }
    
    public void removeItem(Item item) {
        if (inventory.size() > 0) {
            inventory.remove(item);
        }
    }
    
    public Map<Item, Integer> getInventory() {
        return inventory;
    }
    
}
