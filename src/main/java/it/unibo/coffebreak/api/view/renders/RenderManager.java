package it.unibo.coffebreak.api.view.renders;

import java.awt.Graphics2D;
import java.util.List;

import it.unibo.coffebreak.api.model.entities.Entity;

/**
 * Simplified and more focused render manager interface.
 * Handles the rendering pipeline for game entities and static elements.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface RenderManager {
    /**
     * Main rendering method that draws all game elements.
     * 
     * @param g      Graphics2D context to render onto
     * @param width
     * @param height
     */
    void render(Graphics2D g, int width, int height);

    /**
     * Updates the list of active entities to render.
     * 
     * @param entities List of game entities (can be empty but not null)
     */
    void updateEntities(List<Entity> entities);

    /**
     * Registers a renderer for entities of specific type.
     * @param type the class of entities this renderer can handle
     * @param renderer the renderer implementation
     */
    void addEntityRenderer(Class<? extends Entity> type, EntityRender renderer);

    /**
     * Adds a static renderer (background, UI, etc.).
     * 
     * @param render Static render implementation
     */
    void addStaticRenderer(StaticRender render);
}
