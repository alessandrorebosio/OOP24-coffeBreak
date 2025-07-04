package it.unibo.coffebreak.model.leaderboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.leaderboard.Leaderboard;
import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;
import it.unibo.coffebreak.impl.model.leaderboard.GameLeaderboard;
import it.unibo.coffebreak.impl.model.leaderboard.entry.ScoreEntry;
import it.unibo.coffebreak.impl.repository.ScoreRepository;

/**
 * Comprehensive test suite for {@link Leaderboard} interface and
 * {@link GameLeaderboard} implementation.
 */
class TestLeaderBoard {

    private static final String PLAYER_1 = "REB";
    private static final String PLAYER_2 = "RIC";
    private static final String PLAYER_3 = "GRA";
    private static final int SCORE_1 = 1000;
    private static final int SCORE_2 = 2000;
    private static final int SCORE_3 = 3000;
    private static final String EMPTY_NAME = "";

    private Leaderboard leaderBoard;

    /**
     * Initializes a fresh GameLeaderBoard instance before each test.
     */
    @BeforeEach
    void setUp() {
        new ScoreRepository().deleteAllFiles();
        leaderBoard = new GameLeaderboard();
    }

    /**
     * Tests that entries are added in correct descending score order.
     */
    @Test
    void shouldMaintainDescendingOrder() {
        leaderBoard.addEntry(new ScoreEntry(PLAYER_2, SCORE_2));
        leaderBoard.addEntry(new ScoreEntry(PLAYER_1, SCORE_1));
        leaderBoard.addEntry(new ScoreEntry(PLAYER_3, SCORE_3));

        final List<Entry> expected = List.of(
                new ScoreEntry(PLAYER_3, SCORE_3),
                new ScoreEntry(PLAYER_2, SCORE_2),
                new ScoreEntry(PLAYER_1, SCORE_1));

        assertEquals(expected, leaderBoard.getTopScores());
    }

    /**
     * Tests that adding null entries throws NullPointerException.
     */
    @Test
    void shouldRejectNullEntries() {
        assertThrows(NullPointerException.class,
                () -> leaderBoard.addEntry(null));
    }

    /**
     * Tests that leaderboard respects maximum capacity.
     */
    @Test
    void shouldEnforceMaximumCapacity() {
        fillLeaderBoardToCapacity();

        final Entry lowScoreEntry = new ScoreEntry("NewPlayer", 50);
        leaderBoard.addEntry(lowScoreEntry);

        assertEquals(GameLeaderboard.MAX_ENTRIES, leaderBoard.getTopScores().size());
        assertFalse(leaderBoard.getTopScores().contains(lowScoreEntry));
    }

    /**
     * Tests that higher scores replace lower scores when at capacity.
     */
    @Test
    void shouldReplaceLowestScoreWhenFull() {
        fillLeaderBoardToCapacity();

        final Entry highScoreEntry = new ScoreEntry("Champion", Integer.MAX_VALUE);
        leaderBoard.addEntry(highScoreEntry);

        assertTrue(leaderBoard.getTopScores().contains(highScoreEntry));
        assertEquals(highScoreEntry, leaderBoard.getTopScores().get(0));
    }

    /**
     * Tests handling of entries with empty names.
     */
    @Test
    void shouldAcceptEmptyPlayerNames() {
        final Entry entry = new ScoreEntry(EMPTY_NAME, SCORE_1);
        leaderBoard.addEntry(entry);
        assertEquals(EMPTY_NAME, leaderBoard.getTopScores().get(0).name());
    }

    /**
     * Tests that changes are persisted to repository.
     */
    @Test
    void shouldPersistChangesToRepository() {
        leaderBoard.addEntry(new ScoreEntry(PLAYER_1, SCORE_1));
        leaderBoard.save();

        final Leaderboard newLeaderboard = new GameLeaderboard();
        assertEquals(1, newLeaderboard.getTopScores().size());
        assertEquals(PLAYER_1, newLeaderboard.getTopScores().get(0).name());
    }

    /**
     * Helper method to fill leaderboard to maximum capacity.
     */
    private void fillLeaderBoardToCapacity() {
        for (int i = 1; i <= GameLeaderboard.MAX_ENTRIES; i++) {
            leaderBoard.addEntry(new ScoreEntry("Player" + i, i * 100));
        }
    }
}
