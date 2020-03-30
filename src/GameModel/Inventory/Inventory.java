package GameModel.Inventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Inventory {
    
    private Map<Item, Integer> inventory;
    private Set<String> uniqueItems;
    private int size;
    
    public Inventory() {
        this.inventory = new HashMap<>();
        uniqueItems = new HashSet<>();
        this.size = 10;
    }
    
    public void addItem(Item item) {
        if (inventory.size() >= size) {
            //display "Bag full!" message
        } else {
            if (!inventory.containsKey(item)) {
                inventory.put(item, 0);
                uniqueItems.add(item.getName().toLowerCase());
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
    
    public Set<String> getUniqueItems() {
        return uniqueItems;
    }
    
}
