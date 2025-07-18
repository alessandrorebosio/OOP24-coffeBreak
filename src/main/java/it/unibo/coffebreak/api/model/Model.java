package it.unibo.coffebreak.api.model;

import java.util.List;
import java.util.Optional;

import it.unibo.coffebreak.api.controller.action.Action;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;
import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.impl.common.BoundigBox;

/**
 * Represents the main model interface for the game.
 * <p>
 * The model acts as the central point for accessing game entities,
 * player information, and game state management. It handles the core
 * game logic and maintains the game world state.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public interface Model {
    /**
     * Changes the current game state to the specified one.
     * 
     * @param newState the new game state
     * @throws NullPointerException if newState is null
     */
    void setState(ModelState newState);

    /**
     * Handles an action by delegating it to the current game state.
     * This method acts as a bridge between the controller and the current
     * model state, ensuring that actions are processed according to the
     * current game context.
     * 
     * @param action the action to be handled
     */
    void handleAction(Action action);

    /**
     * Starts the first level of the game.
     */
    void start();

    /**
     * Stops the game simulation and triggers any necessary cleanup.
     */
    void stop();

    /**
     * Checks if the game simulation is currently running.
     * 
     * @return true if the game is running, false otherwise
     */
    boolean isRunning();

    /**
     * Returns the game bounds of the game area.
     *
     * @return the game bounds of the game as an integer
     */
    BoundigBox getGameBound();

    /**
     * Gets the current game state.
     * 
     * @return the current game state, never null
     */
    ModelState getGameState();

    /**
     * Gets all entities currently present in the game world.
     * 
     * @return an unmodifiable list of all game entities, never null
     */
    List<Entity> getEntities();

    /**
     * Adds an entity to the game model.
     * 
     * @param entity the {@link Entity} to be added
     * @return true if the entity was added successfully, false otherwise
     * @throws NullPointerException if entity is null
     */
    boolean addEntity(Entity entity);

    /**
     * Resets all entities in the current level to their initial state.
     */
    void initialEntitiesState();

    /**
     * Transforms certain entities according to game logic.
     */
    void transformEntities();

    /**
     * Gets the player character.
     * 
     * @return the main character instance, never null
     */
    Optional<MainCharacter> getMainCharacter();

    /**
     * Gets the current player score.
     * 
     * @return the current score value
     */
    int getScoreValue();

    /**
     * Gets the current bonus value.
     * 
     * @return the current bonus value
     */
    int getBonusValue();

    /**
     * Advances to the next map in the game sequence.
     */
    void nextMap();

    /**
     * Gets the current level index.
     * 
     * @return the current level identifier
     */
    int getLevelIndex();

    /**
     * Calculates and applies time-based bonus effects.
     * 
     * @param deltaTime the time elapsed since the last frame (in seconds)
     */
    void calculateBonus(float deltaTime);

    /**
     * Gets the current leaderboard data.
     * 
     * @return an unmodifiable list of leaderboard entries, sorted by score (highest
     *         first)
     */
    List<Entry> getLeaderBoard();

    /**
     * Gets the highest score currently present in the leaderboard.
     *
     * @return the highest score value, or 0 if the leaderboard is empty
     */
    int getHighestScore();

    /**
     * Adds the current player's score to the leaderboard.
     * 
     * @param name the player name to associate with the score
     * @throws NullPointerException if name is null
     */
    void addEntry(String name);

    /**
     * Updates the game logic based on elapsed time.
     * 
     * @param deltaTime time in seconds since last update
     * @throws IllegalArgumentException if deltaTime is negative
     */
    void update(float deltaTime);
}
