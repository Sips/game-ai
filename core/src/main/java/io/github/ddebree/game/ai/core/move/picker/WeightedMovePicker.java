package io.github.ddebree.game.ai.core.move.picker;

import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Randomly select a move, but weight some moves above others
 *
 * @param <S> The class that represents a game's state
 * @param <M> The class used to represent a player's move
 */
public abstract class WeightedMovePicker<S, M> implements IMovePicker<S, M> {

    protected abstract int getWeight(final S state, final M move);

    private final Random random = new Random();

    @Nonnull
    public M pickAMove(S state, Set<M> bestMovesSet) {
        List<M> bestMoves = Lists.newArrayList(bestMovesSet);
        if (bestMoves.isEmpty()) {
            throw new RuntimeException("No moves to select from!");
        } else if (bestMoves.size() == 1) {
            return bestMoves.iterator().next();
        } else {
            final ArrayList<M> moves = new ArrayList<>(bestMoves.size());
            for (final M move : bestMoves) {
                int weight = getWeight(state, move);
                if (weight > 0) {
                    for (int i = 0; i < weight; i++) {
                        moves.add(move);
                    }
                }
            }
            if (moves.isEmpty()) {
                throw new RuntimeException("All moves eliminated by the weight calculations");
            } else {
                return moves.get(random.nextInt(moves.size()));
            }
        }
    }

}
