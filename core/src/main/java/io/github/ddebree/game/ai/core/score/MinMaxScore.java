package io.github.ddebree.game.ai.core.score;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MinMaxScore<M> {

    private static final Logger LOG = LogManager.getLogger(MinMaxScore.class);

    private final ImmutableSet<M> bestMoves;

    //Private since must use the builder method...
    private MinMaxScore(ImmutableSet<M> bestMoves) {
        this.bestMoves = bestMoves;
    }

    public ImmutableSet<M> getBestMoves() {
        return bestMoves;
    }


    public static <M> MinMaxScore.Builder<M> builder() {
        return new MinMaxScore.Builder<>();
    }

    public static class Builder<M> {

        private final ConcurrentHashMap<M, MinScore> scores = new ConcurrentHashMap<>();

        /**
         * Add a score for a particular move.
         * Since it is possible for the opponent to make various moves, this method could be called multiple
         * times with the same input move, and different scores. The opponent will most likely choose
         * the move that results in the lowest possible score, and so this is the score we capture for the
         * move itself. So, when it comes time to find the best move, we select the one that gives the maximum minimum
         * value.
         *
         * @param move The move
         * @param score One of the scores for the move
         */
        public final void addScoredMove(M move, int score) {
            MinScore oldScore = scores.get(move);
            if (oldScore == null) {
                oldScore = scores.putIfAbsent(move, new MinScore(score));
            }
            if (oldScore != null) {
                oldScore.addScore(score);
            }
        }

        /**
         * Build up the resultant MinMaxScore
         *
         * @return
         */
        @Nonnull
        public MinMaxScore<M> build() {
            if (scores.isEmpty()) {
                return new MinMaxScore<>(ImmutableSet.of());
            }

            ListMultimap<Integer, M> groupedScores = ArrayListMultimap.create();
            for (Map.Entry<M, MinScore> entry : scores.entrySet()) {
                groupedScores.put(entry.getValue().getMinScore(), entry.getKey());
            }

            Integer max = groupedScores.keySet().stream().reduce(Integer::max).get();

            ImmutableSet<M> topMoves = ImmutableSet.copyOf(groupedScores.get(max));

            LOG.info("Found best score to be " + max + ", moves that result in this is: " + topMoves);

            return new MinMaxScore<>(topMoves);
        }

        private static class MinScore {

            private int minScore;

            public MinScore(int minScore) {
                this.minScore = minScore;
            }

            public void addScore(int score) {
                synchronized (this) {
                    minScore = Math.min(score, minScore);
                }
            }

            public int getMinScore() {
                synchronized (this) {
                    return minScore;
                }
            }
        }

    }

}
