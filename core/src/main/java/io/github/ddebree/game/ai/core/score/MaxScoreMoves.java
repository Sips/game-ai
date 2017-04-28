package io.github.ddebree.game.ai.core.score;

import java.util.HashSet;
import java.util.Set;

public class MaxScoreMoves<M> {

    private int bestScore = Integer.MIN_VALUE;
    private final Set<M> bestMoves = new HashSet<>();

    public void addMove(M move, int score) {
        synchronized (bestMoves) {
            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
            }
            if (score == bestScore) {
                bestMoves.add(move);
            }
        }
    }

    public Set<M> getMoves() {
        return bestMoves;
    }

    @Override
    public String toString() {
        return "MaxScoreMoves{" +
                "bestScore=" + bestScore +
                ", bestMoves=" + bestMoves +
                '}';
    }
}
