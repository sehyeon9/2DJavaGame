package Identifier.Portal;

import GameModel.Tile;

import java.util.HashMap;
import java.util.Map;

/**
 * Nothing can be retrieved so as to privatize the portal IDs
 */
public class PortalID {
    
    private Map<Tile, Integer> portalIDs;
    private Map<Tile, Integer> mappedPortals;
    
    public PortalID() {
        portalIDs = new HashMap<>();
        mappedPortals = new HashMap<>();
    }
    
    public void addPortal(Tile tile) {
        if (!portalIDs.containsKey(tile)) {
            portalIDs.put(tile, tile.hashCode());
        }
    }
    
    public void removePortal(Tile tile) {
        portalIDs.remove(tile);
        mappedPortals.remove(tile);
    }
    
    public void addPortalMapping(Tile tile, int mapID) {
        if (portalIDs.containsKey(tile)) {
            mappedPortals.put(tile, mapID);
        }
    }

    /**
     * @param tile the tile the user stepped on
     * @return the map ID associated with that tile
     */
    public int getMappedPortal(Tile tile) {
        if (!portalIDs.containsKey(tile)) {
            throw new IllegalArgumentException("The tile is not a part of the game!");
        }
        return mappedPortals.get(tile);
    }
    
}
