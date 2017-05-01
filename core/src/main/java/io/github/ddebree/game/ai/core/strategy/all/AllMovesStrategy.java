package io.github.ddebree.game.ai.core.strategy.all;

import io.github.ddebree.game.ai.core.move.IMoveFactory;
import io.github.ddebree.game.ai.core.strategy.IStrategy;

import javax.annotation.Nonnull;

import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Return all the moves!
 *
 * @param <S> The class that represents a game's state
 * @param <P> The class that represents a key to the current player
 * @param <M> The class used to represent a player's move
 */
public class AllMovesStrategy<S, P, M> implements IStrategy<S, P, M> {

    @Nonnull
    private final IMoveFactory<S, P, M> moveFactory;

    public AllMovesStrategy(@Nonnull final IMoveFactory<S, P, M> moveFactory) {
        this.moveFactory = checkNotNull(moveFactory);
    }

    @Nonnull
    @Override
    public Set<M> getBestMoves(@Nonnull S state, @Nonnull P player) {
        return moveFactory.getMoves(state, player).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "All Moves strategy";
    }
}
