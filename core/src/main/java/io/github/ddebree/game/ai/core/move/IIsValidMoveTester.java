package io.github.ddebree.game.ai.core.move;

import javax.annotation.Nonnull;

/**
 * A class that determines if the provided move is valid and legal given the current game state
 *
 * @param <S> The class that represents a game's state
 * @param <P> The class that represents a player
 * @param <M> The class used to represent a player's move
 */
public interface IIsValidMoveTester<S, P, M> {

    /**
     * Determine if the provided move is valid for the player, given the current game state
     *
     * @param state The current game state
     * @param player The player to check for valid move
     * @param move The move to confirm is valid
     * @return true if the move is valid for the player, given the current game state, false otherwise
     */
    boolean isValidMove(@Nonnull S state, @Nonnull P player, @Nonnull M move);

}
