package io.github.ddebree.game.ai.tictactoe;

import com.google.common.collect.ImmutableMap;
import io.github.ddebree.game.ai.core.player.TwoPlayerKey;

import java.util.*;

/**
 *
 */
public class State {

    private final ImmutableMap<Move, TwoPlayerKey> occupiedPoints;

    public State() {
        occupiedPoints = ImmutableMap.of();
    }

    public State(Map<Move, TwoPlayerKey> occupiedPoints) {
        this.occupiedPoints = ImmutableMap.copyOf(occupiedPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(occupiedPoints);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        return Objects.equals(this.occupiedPoints, other.occupiedPoints);
    }

    public Map<Move, TwoPlayerKey> getOccupiedPoints() {
        return occupiedPoints;
    }

    @Override
    public String toString() {
        return "+-------+\n" + line1() + "\n" + line2() + "\n" + line3() + "\n+-------+\n";
    }

    public String line1() {
        return "| " + pointText(Move.TOP_LEFT) + pointText(Move.TOP_MIDDLE) + pointText(Move.TOP_RIGHT) + "|";
    }

    public String line2() {
        return "| " + pointText(Move.MIDDLE_LEFT) + pointText(Move.MIDDLE_MIDDLE) + pointText(Move.MIDDLE_RIGHT) + "|";
    }

    public String line3() {
        return "| " + pointText(Move.BOTTOM_LEFT) + pointText(Move.BOTTOM_MIDDLE) + pointText(Move.BOTTOM_RIGHT) + "|";
    }

    private String pointText(Move p) {
        TwoPlayerKey player = occupiedPoints.get(p);
        if (TwoPlayerKey.PLAYER_1 == player) {
            return "X ";
        } else if (TwoPlayerKey.PLAYER_2 == player) {
            return "O ";
        } else {
            return ". ";
        }
    }
}
