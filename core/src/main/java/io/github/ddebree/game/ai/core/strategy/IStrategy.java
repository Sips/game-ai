package io.github.ddebree.game.ai.core.strategy;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

/**
 * A basic representation of a player's strategy.
 *
 * At a given state, for a particular player, what is the best possible set of moves
 *
 * @param <S> The class that represents a game state
 * @param <P> The class that represents a player
 * @param <M> The class used to represent a player's move
 *
 * @author Dean de Bree
 */
public interface IStrategy<S, P, M> {

    /**
     * Get the best moves that a player could make at the current game state
     *
     * @param state The current game state
     * @param player The player to determine the best current state for
     * @return A stream of moves that would result in the best outcome for the provided player
     */
    @Nonnull
    Stream<M> getBestMoves(@Nonnull S state, P player);
    
}