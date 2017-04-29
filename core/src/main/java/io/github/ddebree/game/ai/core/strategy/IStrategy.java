package io.github.ddebree.game.ai.core.strategy;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

/**
 * A basic representation of a player's strategy.
 *
 * At a given state, for a particular player, what is the best possible set of moves
 *
 * @param <S> The class that represents a game's state
 * @param <P> The class that represents a key to the current player
 * @param <M> The class used to represent a player's move
 */
public interface IStrategy<S, P, M> {

    /**
     * Get the best moves that a player could make at the current game state
     *
     * @param state The current game state
     * @param playerKey The player to determine the best current state for
     * @return A stream of moves that would result in the best outcome for the provided player
     */
    @Nonnull
    Stream<M> getBestMoves(@Nonnull S state, @Nonnull P playerKey);

    /**
     * Get a callable representation of this strategy
     *
     * @param state The current game state
     * @param playerKey The player to determine the best current state for
     * @return A stream of moves that would result in the best outcome for the provided player
     */
    default Callable<Stream<M>> asCallable(@Nonnull final S state, @Nonnull final P playerKey) {
        return () -> getBestMoves(state, playerKey);
    }
}
