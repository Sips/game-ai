package io.github.ddebree.game.ai.tictactoe;

import io.github.ddebree.game.ai.core.player.TwoPlayerKey;

import java.util.HashMap;
import java.util.Map;

public class StateBuilder {

    private Map<Move, TwoPlayerKey> occupiedPoints = new HashMap<>();

    public static StateBuilder builder() {
        return new StateBuilder();
    }

    public StateBuilder allOccupiedBy(TwoPlayerKey player) {
        for (Move p : Move.values()) {
            occupiedPoints.put(p, player);
        }
        return this;
    }

    public StateBuilder playerOnPoints(TwoPlayerKey player, Move... points) {
        for (Move p : points) {
            occupiedPoints.put(p, player);
        }
        return this;
    }

    public StateBuilder emptyPoints(Move... points) {
        for (Move p : points) {
            occupiedPoints.remove(p);
        }
        return this;
    }

    public State build() {
        return new State(occupiedPoints);
    }

}
