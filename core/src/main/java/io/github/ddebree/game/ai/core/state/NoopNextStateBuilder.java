package io.github.ddebree.game.ai.core.state;

import io.github.ddebree.game.ai.core.exception.InvalidMoveException;

import javax.annotation.Nonnull;

/**
 * A simple next state builder that simply returns the current state.
 *
 * @param <S> The class that represents a game's state
 * @param <P> The class that represents a key to the current player
 * @param <M> The class used to represent a player's move
 */
public class NoopNextStateBuilder<S, P, M> implements INextStateBuilder<S, P, M> {

    @Nonnull
    @Override
    public S buildNextState(@Nonnull S currentState, @Nonnull P playerKey, @Nonnull M move) throws InvalidMoveException {
        return currentState;
    }
}
