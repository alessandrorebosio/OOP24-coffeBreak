package it.unibo.coffebreak.impl.model.states.menu;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.states.GameState;
import it.unibo.coffebreak.impl.model.states.AbstractState;
import it.unibo.coffebreak.impl.model.states.ingame.InGameState;

/**
 * Implementation of {@link GameState} interface;
 * <p>
 * Represents the <b>Main Menu</b> state of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class MenuState extends AbstractState {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ENTER:
                model.start();
                model.setState(InGameState::new);
                break;
            case QUIT:
                model.stop();
                break;
            default:
                break;
        }
    }
}
