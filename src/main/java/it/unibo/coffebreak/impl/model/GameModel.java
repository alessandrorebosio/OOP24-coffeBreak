package it.unibo.coffebreak.impl.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.leaderboard.Leaderboard;
import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;
import it.unibo.coffebreak.api.model.level.LevelManager;
import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.model.leaderboard.GameLeaderboard;
import it.unibo.coffebreak.impl.model.leaderboard.entry.ScoreEntry;
import it.unibo.coffebreak.impl.model.level.GameLevelManager;
import it.unibo.coffebreak.impl.model.states.menu.MenuModelState;

/**
 * Concrete implementation of the game {@link Model}.
 * <p>
 * Maintains the game state including entities, player information, and state
 * management.
 * Provides thread-safe access to model components through proper
 * synchronization.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public class GameModel implements Model {

    private final BoundigBox gameBounds = new BoundigBox(600, 800);
    private final Leaderboard leaderBoard = new GameLeaderboard();
    private final LevelManager levelManager = new GameLevelManager();

    private ModelState currentState;
    private volatile boolean running;

    /**
     * Constructs a new {@code GameModel} with the specified game boundaries.
     * Initializes the game as running and sets the initial state to the menu.
     */
    public GameModel() {
        this.running = true;

        this.setState(new MenuModelState());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setState(final ModelState newState) {
        if (currentState != null) {
            currentState.onExit(this);
        }
        currentState = Objects.requireNonNull(newState, "The new state cannot be null");
        currentState.onEnter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.levelManager.loadCurrentEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.leaderBoard.save();
        this.running = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return this.running;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoundigBox getGameBound() {
        return this.gameBounds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelState getGameState() {
        return this.currentState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entity> getEntities() {
        return this.levelManager.getEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEntity(final Entity entity) {
        return this.levelManager.addEntity(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialEntitiesState() {
        this.levelManager.loadCurrentEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transformEntities() {
        this.levelManager.transformEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MainCharacter> getMainCharacter() {
        return this.levelManager.getMainCharacter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScoreValue() {
        if (this.getMainCharacter().isPresent()) {
            return this.getMainCharacter().get().getScoreValue();
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBonusValue() {
        return this.levelManager.getBonusValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextMap() {
        this.levelManager.advance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevelIndex() {
        return this.levelManager.getLevelIndex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculateBonus(final float deltaTime) {
        this.levelManager.calculateBonus(deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entry> getLeaderBoard() {
        return this.leaderBoard.getTopScores();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHighestScore() {
        return this.leaderBoard.getTopScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntry(final String name) {
        this.leaderBoard.addEntry(new ScoreEntry(name, this.getScoreValue()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Command command) {
        this.currentState.handleCommand(this, command);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime) {
        this.currentState.update(this, deltaTime);
    }
}
