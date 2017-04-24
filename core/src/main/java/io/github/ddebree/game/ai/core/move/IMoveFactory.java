package io.github.ddebree.game.ai.core.move;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

/**
 * A factory class that generates all possible move for the player at a particular game state
 *
 * @param <S> The class that represents a game's state
 * @param <P> The class that represents a key to the current player
 * @param <M> The class used to represent a player's move
 */
public interface IMoveFactory<S, P, M> {

    /**
     * Get all the moves that a player could make at the current game state
     *
     * @param state The current game state
     * @param playerKey The player to determine the moves for
     * @return A stream of moves that the provided player could perform at this game state
     */
    @Nonnull
    Stream<M> getMoves(@Nonnull S state, P playerKey);

}
