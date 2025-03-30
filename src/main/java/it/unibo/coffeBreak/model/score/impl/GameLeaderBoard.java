package it.unibo.coffeBreak.model.score.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import it.unibo.coffeBreak.model.score.api.Entry;
import it.unibo.coffeBreak.model.score.api.LeaderBoard;

/**
 * Implementation of a game leaderboard that maintains the top scoring entries.
 * The leaderboard has a maximum capacity and entries are automatically sorted
 * in descending order of score.
 */
public class GameLeaderBoard implements LeaderBoard<Entry> {

    /** The maximum number of entries allowed in the leaderboard */
    private static final int MAX_ENTRIES = 5;

    /**
     * The list containing the leaderboard entries.
     * Maintained in descending order based on score.
     */
    private final List<Entry> leaderBoard;

    /**
     * Constructs an empty leaderboard.
     */
    public GameLeaderBoard() {
        this.leaderBoard = new ArrayList<>(MAX_ENTRIES);
    }

    /**
     * Constructs a leaderboard initialized with the provided entries.
     * The entries will be sorted and trimmed to the maximum capacity.
     *
     * @param leaderBoard the initial list of entries
     * @throws NullPointerException if the provided list is null
     */
    public GameLeaderBoard(final List<Entry> leaderBoard) {
        this.leaderBoard = new ArrayList<>(Objects.requireNonNull(leaderBoard));
        this.leaderBoard.sort(Entry::compareTo);
        this.trimToMaxSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entry> getLeaderBoard() {
        return Collections.unmodifiableList(this.leaderBoard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntry(final Entry entry) {
        Objects.requireNonNull(entry);

        if (this.isEligible(entry)) {
            this.leaderBoard.add(entry);
            this.leaderBoard.sort(Entry::compareTo);
            this.trimToMaxSize();
        }
    }

    /**
     * Checks if an entry is eligible to be added to the leaderboard.
     * An entry is eligible if:
     * - The leaderboard isn't full, or
     * - The entry has a higher score than the lowest score in the leaderboard
     *
     * @param entry the entry to check
     * @return true if the entry is eligible, false otherwise
     */
    private boolean isEligible(final Entry entry) {
        return this.leaderBoard.size() < MAX_ENTRIES
                || this.leaderBoard.isEmpty()
                || entry.compareTo(this.leaderBoard.get(this.leaderBoard.size() - 1)) > 0;
    }

    /**
     * Trims the leaderboard to the maximum allowed size if necessary.
     */
    private void trimToMaxSize() {
        if (this.leaderBoard.size() > MAX_ENTRIES) {
            this.leaderBoard.subList(MAX_ENTRIES, this.leaderBoard.size()).clear();
        }
    }

}
