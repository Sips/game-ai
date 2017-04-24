package io.github.ddebree.game.ai.core.strategy.fixed;

import io.github.ddebree.game.ai.core.move.IIsValidMoveTester;
import io.github.ddebree.game.ai.core.strategy.IStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Always return the same move. Optionally test if the move is valid before returning it.
 *
 * @param <S> The class that represents a game's state
 * @param <P> The class that represents a key to the current player
 * @param <M> The class used to represent a player's move
 */
public class FixedMoveStrategy<S, P, M> implements IStrategy<S, P, M> {

    @Nullable
    private final IIsValidMoveTester<S, P, M> hasNextStateTester;
    @Nonnull
    private final M defaultMove;

    public FixedMoveStrategy(@Nonnull final M defaultMove) {
        this(defaultMove, null);
    }

    public FixedMoveStrategy(@Nonnull final M defaultMove,
                             @Nullable final IIsValidMoveTester<S, P, M> hasNextStateTester) {
        this.defaultMove = checkNotNull(defaultMove);
        this.hasNextStateTester = hasNextStateTester;
    }

    @Nonnull
    @Override
    public Stream<M> getBestMoves(@Nonnull S state, P playerKey) {
        if (hasNextStateTester == null || hasNextStateTester.isValidMove(state, playerKey, defaultMove)) {
            return Stream.of(defaultMove);
        } else {
            return Stream.empty();
        }
    }
    
    @Override
    public String toString() {
        return "Default Move strategy (always returning: " + defaultMove + ").";
    }
}
