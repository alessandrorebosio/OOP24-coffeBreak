package it.unibo.coffeBreak.model.score.api;

import java.util.List;

/**
 * Interface for a generic repository that handles saving and loading of
 * elements.
 *
 * @param <X> the type of elements stored in the repository
 */
public interface Repository<X> {

    /**
     * Saves a list of elements to the repository.
     *
     * @param list the list of elements to be saved
     * @return true if the operation was successful, false otherwise
     */
    boolean save(List<X> list);

    /**
     * Loads all elements from the repository.
     *
     * @return a list of all elements stored in the repository
     */
    List<X> load();

}
