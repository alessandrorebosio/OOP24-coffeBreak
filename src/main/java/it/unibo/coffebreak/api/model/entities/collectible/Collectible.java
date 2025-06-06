package it.unibo.coffebreak.api.model.entities.collectible;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;

/**
 * Represents a collectible item in the game world (e.g., Pauline's purse, hat,
 * hammer).
 * When collected, it may provide points or special abilities to the player.
 * 
 * @author Alessandro Rebosio
 */
public interface Collectible extends Entity {
    /**
     * Collects this item, triggering its effect (e.g., adding points).
     * 
     * @param character the character who collects this item
     */
    void collect(MainCharacter character);

    /**
     * @return true if this item has been collected, false otherwise
     */
    boolean isCollected();
}
