package it.unibo.coffebreak.model.impl.entity.platform;

import java.util.Objects;

import it.unibo.coffebreak.model.api.entity.platform.Platform;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * A decorator that adds movement capability to a platform.
 * This class extends {@link PlatformDecorator} to create platforms that move with
 * a constant velocity. It overrides the default friction to 0.7 to better reflect
 * the physical properties of moving surfaces.
 */
public class MovingPlatform extends PlatformDecorator {

    /**
     * The modified friction coefficient for moving platforms.
     */
    private static final float FRICTION = 0.7f;

    /**
     * The velocity vector of this platform.
     */
    private final Vector2D velocity;

    /**
     * Constructs a new moving platform decorator.
     *
     * @param basePlatform the platform to decorate 
     * @param velocity the movement velocity vector 
     * @throws IllegalArgumentException if either parameter is null
     */
    public MovingPlatform(final Platform basePlatform, final Vector2D velocity) {
        super(Objects.requireNonNull(basePlatform, "Base platform cannot be null"));
        this.velocity = Objects.requireNonNull(velocity, "Velocity cannot be null");
    }

    /**
     * Gets the modified friction coefficient for this moving platform.
     *
     * @return the constant friction value
     */
    @Override
    public float getFriction() {
        return FRICTION;
    }

    /**
     * Gets the velocity vector of this platform.
     *
     * @return a copy of the velocity vector 
     */
    public Vector2D getVelocity() {
        return new Vector2D(velocity.getX(), velocity.getY());
    }

    /**
     * Updates the platform's position based on its velocity.
     *
     * @param deltaTime the time elapsed since last update 
     */
    @Override
    public void update(final long deltaTime) {
        this.setPosition(new Position(this.getPlatformPosition().x() + velocity.getX() * deltaTime / 1000.0f, 
                                        this.getPlatformPosition().y() + velocity.getY() * deltaTime / 1000.0f));
    }
}
