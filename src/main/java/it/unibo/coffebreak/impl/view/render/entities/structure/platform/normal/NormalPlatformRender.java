package it.unibo.coffebreak.impl.view.render.entities.structure.platform.normal;

import java.awt.Graphics2D;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.view.render.entities.structure.platform.AbstractPlatformRender;

/**
 * A renderer for the Normal Platform.
 * 
 * @author Alessandro Rebosio
 */
public class NormalPlatformRender extends AbstractPlatformRender {

    /**
     * Constructs a new NormalPlatformRender with the specified screen dimensions.
     * The entity dimensions will be scaled according to these dimensions.
     *
     * @param resource
     */
    public NormalPlatformRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void renderPlatform(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
    }
}
