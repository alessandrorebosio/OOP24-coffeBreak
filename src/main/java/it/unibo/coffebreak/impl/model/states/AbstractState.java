package it.unibo.coffebreak.impl.model.states;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.states.GameState;

/**
 * An abstract base class representing a game State.
 * Implements basic functionality for game states, such as
 * handling Actions, exiting and entering States.
 * 
 * @author Filippo Ricciotti
 */
public abstract class AbstractState implements GameState {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnter(final Model model) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onExit(final Model model) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Model model, final float deltaTime) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void handleCommand(Model model, Command command);
}
