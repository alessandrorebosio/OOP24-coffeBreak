package it.unibo.coffebreak.impl.repository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;
import it.unibo.coffebreak.api.repository.Repository;
import it.unibo.coffebreak.api.repository.filemanager.FileManager;
import it.unibo.coffebreak.impl.repository.filemanager.ScoreFileManager;

/**
 * A file-based repository implementation using Java serialization
 * for storing and loading Entry objects. Supports backup and recovery.
 * 
 * @author Alessandro Rebosio
 */
public class ScoreRepository implements Repository<List<Entry>> {

    private static final String FOLDER = ".coffeBreak";
    private static final String FILE_NAME = "dk_leaderboard.ser";

    private final FileManager fileManager;

    /**
     * Constructs a new {@code ScoreRepository} instance using the default file
     * manager. Initializes an empty list when no data file is available.
     */
    public ScoreRepository() {
        this.fileManager = new ScoreFileManager(FOLDER, FILE_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(final List<Entry> data) {
        if (Objects.requireNonNull(data, "List cannot be null").isEmpty()) {
            return true;
        }

        this.fileManager.createBackup();

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(this.fileManager.getDataFile()))) {
            oos.writeObject(data);
            return true;
        } catch (final IOException e) {
            throw new RepositoryException("Error while saving data", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Entry> load() {
        if (!Files.exists(this.fileManager.getDataFile())) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(this.fileManager.getDataFile()))) {
            return (List<Entry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            if (this.fileManager.restoreFromBackup()) {
                return load();
            }
            throw new RepositoryException("Error while loading data", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAllFiles() {
        return this.fileManager.deleteAll();
    }

    /**
     * Indicates repository-specific errors during persistence operations.
     * Wraps lower-level IO and serialization exceptions.
     */
    public static class RepositoryException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        /**
         * Creates an exception with detailed context.
         * 
         * @param message the operational context
         */
        public RepositoryException(final String message) {
            super(message);
        }

        /**
         * Creates an exception with detailed context and cause.
         * 
         * @param message the operational context
         * @param cause   the root failure cause
         */
        public RepositoryException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
