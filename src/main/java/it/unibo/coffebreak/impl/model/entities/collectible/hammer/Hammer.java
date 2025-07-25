package it.unibo.coffebreak.impl.model.entities.collectible.hammer;

import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.collectible.AbstractCollectible;
import it.unibo.coffebreak.impl.model.entities.mario.states.withhammer.WithHammerState;

/**
 * Represents a hammer collectible item in the game. When collected by a player
 * character, it provides both points to the player's score and potentially
 * modifies the player's state.
 * Extends {@link AbstractCollectible} to inherit basic collectible properties
 * and behavior.
 * 
 * @author Alessandro Rebosio
 */
public class Hammer extends AbstractCollectible {

    private static final int VALUE = 500;

    /**
     * Constructs a new Hammer with the specified position, dimensions, and point
     * value.
     *
     * @param position  the 2D position of the hammer in the game world
     * @param dimension the 2D dimension of the hammer in the game world
     */
    public Hammer(final Position position, final BoundigBox dimension) {
        super(position, dimension, VALUE);
    }

    /**
     * Applies the effect of collecting this hammer to the player character.
     * When collected, the hammer adds its value to the player's score and
     * may modify the player's state (e.g., granting special abilities).
     * 
     * @param character the character that collected this hammer
     */
    @Override
    protected void applyEffect(final MainCharacter character) {
        super.applyEffect(character);
        character.changeState(WithHammerState::new);
    }
}
