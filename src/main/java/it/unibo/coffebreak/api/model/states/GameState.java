package it.unibo.coffebreak.api.model.states;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.Model;

/**
 * Interface for handling Game Phases.
 * 
 * @author Filippo Ricciotti
 */
public interface GameState {
    /**
     * method for signaling the entrance in a new Phase.
     * 
     * @param model the model changing state
     */
    void onEnter(Model model);

    /**
     * method for signaling a Phase leaving.
     * 
     * @param model the model changing state
     */
    void onExit(Model model);

    /**
     * method that handle input depending on the Phase the model is currently in.
     * 
     * @param command Input to handle.
     * @param model   the game model containing the possible Phase to change.
     */
    void handleCommand(Model model, Command command);

    /**
     * Updates the logic of the current game Phase based on deltaTime.
     * 
     * @param model     model to update
     * @param deltaTime time in milliseconds since the last update call.
     */
    void update(Model model, float deltaTime);
}
