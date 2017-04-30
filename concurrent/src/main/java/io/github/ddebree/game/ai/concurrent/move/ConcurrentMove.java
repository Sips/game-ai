package io.github.ddebree.game.ai.concurrent.move;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * A move that occurs when two or more players move at the same time per round.
 * In games that require this class, the game is not turn based, but rather all the players move concurrently.
 *
 * @param <P> The player key type
 * @param <M> The move type (per player)
 */
public class ConcurrentMove<P, M> {

    private final ImmutableMap<P, M> playerMoves;

    public ConcurrentMove(Map<P, M> playerMoves) {
        this.playerMoves = ImmutableMap.copyOf(playerMoves);
    }

    public ImmutableMap<P, M> getPlayerMoves() {
        return playerMoves;
    }

    @Override
    public String toString() {
        return "ConcurrentMove{" +
                "playerMoves=" + playerMoves +
                '}';
    }
}
