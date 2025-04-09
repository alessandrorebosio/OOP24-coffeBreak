package it.unibo.coffebreak.model.entity.impl;

import it.unibo.coffebreak.model.entity.EnemyType;
import it.unibo.coffebreak.model.utility.Dimension;
import it.unibo.coffebreak.model.utility.Position;
import it.unibo.coffebreak.model.utility.Vector2D;

/**
 * Represents a fire enemy in the game.
 */
public class Fire extends Enemy {

    /**
     * Constructs a Fire enemy with the specified position, dimension, state, and velocity.
     *
     * @param position the position of the fire enemy
     * @param dimension the dimension of the fire enemy
     * @param state the state of the fire enemy
     * @param velocity the velocity of the fire enemy
     */
    public Fire(final Position position, final Dimension dimension, final EnemyType state, final Vector2D velocity) {
        super(position, dimension, state, velocity);
    }

}
