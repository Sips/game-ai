package io.github.ddebree.game.ai.core.state;

import io.github.ddebree.game.ai.core.exception.InvalidMoveException;
import io.github.ddebree.game.ai.core.move.IIsValidMoveTester;

import javax.annotation.Nonnull;

/**
 * A class that implements this interface should build the next state for the user's provided move
 *
 * @param <S> The class that represents a game's state
 * @param <P> The class that represents a key to the current player
 * @param <M> The class used to represent a player's move
 */
public interface INextStateBuilder<S, P, M> extends IIsValidMoveTester<S, P, M> {

    @Nonnull
    S buildNextState(@Nonnull S currentState, @Nonnull P playerKey, @Nonnull M move) throws InvalidMoveException;

    default boolean isValidMove(@Nonnull S currentState, @Nonnull P playerKey, @Nonnull M move) {
        try {
            buildNextState(currentState, playerKey, move);
            return true;
        } catch (InvalidMoveException ex) {
            return false;
        }
    }


}
