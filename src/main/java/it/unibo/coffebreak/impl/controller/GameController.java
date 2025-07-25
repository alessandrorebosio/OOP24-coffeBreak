package it.unibo.coffebreak.impl.controller;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.controller.action.Action;
import it.unibo.coffebreak.api.controller.mapper.KeyActionMapper;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;
import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.controller.mapper.StandardKeyMapper;
import it.unibo.coffebreak.impl.model.GameModel;

/**
 * Concrete implementation of the game {@link Controller}.
 * <p>
 * Bridges input handling and model updates in the MVC pattern by:
 * <ul>
 * <li>Receiving input events from the view</li>
 * <li>Translating them into game commands</li>
 * <li>Applying commands to the model</li>
 * <li>Managing the game loop updates</li>
 * </ul>
 * 
 * @author Alessandro Rebosio
 */
public class GameController implements Controller {

    private final Queue<Action> commandQueue = new ConcurrentLinkedQueue<>();
    private final KeyActionMapper input = new StandardKeyMapper();
    private final Model model;

    /**
     * Constructs a new {@code GameController} using the specified {@link Loader}.
     * Initializes the game model with the provided loader.
     *
     * @param loader the loader used to initialize the game model
     */
    public GameController(final Loader loader) {
        this.model = new GameModel(loader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processInput() {
        while (!this.commandQueue.isEmpty()) {
            final Action action = this.commandQueue.poll();
            if (action != null) {
                model.handleAction(action);
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates the game state based on elapsed time.
     */
    @Override
    public void updateModel(final float deltaTime) {
        this.model.update(deltaTime);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Creates and queues the appropriate action based on the key pressed.
     * Uses the InputManager to maintain clean separation of concerns.
     */
    @Override
    public void keyPressed(final int keyCode) {
        this.input.getAction(keyCode)
                .ifPresent(this.commandQueue::add);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Creates and queues the appropriate action based on the key released.
     * Handles stopping movement and other release-based actions.
     */
    @Override
    public void keyReleased(final int keyCode) {
        this.input.getAction(keyCode).ifPresent(action -> {
            this.model.getMainCharacter().ifPresent(character -> {
                switch (action) {
                    case LEFT, RIGHT -> {
                        final var currentVelocity = character.getVelocity();
                        character.setVelocity(new Vector(0.0f, currentVelocity.y()));
                    }
                    case UP, DOWN -> {
                        final var currentVelocity = character.getVelocity();
                        character.setVelocity(new Vector(currentVelocity.x(), 0.0f));
                    }
                    default -> {
                        // Other release actions can be added here
                    }
                }
            });
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entity> getEntities() {
        return this.model.getEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScoreValue() {
        return this.model.getScoreValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBonusValue() {
        return this.model.getBonusValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entry> getLeaderBoard() {
        return this.model.getLeaderBoard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHighestScore() {
        return this.model.getHighestScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevelIndex() {
        return this.model.getLevelIndex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCharacterLives() {
        return this.model.getMainCharacter()
                .map(MainCharacter::getLives)
                .orElse(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelState getGameState() {
        return this.model.getGameState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return this.model.isRunning();
    }
}
