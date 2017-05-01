package io.github.ddebree.game.ai.core.strategy.noop;

import io.github.ddebree.game.ai.core.strategy.IStrategy;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

/**
 * The do nothing strategy
 *
 * @param <S> The class that represents a game's state
 * @param <P> The class that represents a key to the current player
 * @param <M> The class used to represent a player's move
 */
public class NoopStrategy<S, P, M> implements IStrategy<S, P, M> {

    @Nonnull
    @Override
    public Set<M> getBestMoves(@Nonnull S state, P playerKey) {
        return Collections.EMPTY_SET;
    }

}
