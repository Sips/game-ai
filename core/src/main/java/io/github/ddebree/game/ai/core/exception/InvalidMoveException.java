package io.github.ddebree.game.ai.core.exception;

/**
 * An exception that is thrown when the move that is provided is invalid
 */
public class InvalidMoveException extends Exception {
    
    public static final InvalidMoveException INSTANCE = new InvalidMoveException();

    private InvalidMoveException() {
    }

}
