package io.github.ddebree.game.ai.core.move.picker;

import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Randomly select a move from the moves provided on the stream
 *
 * @param <S> The class that represents a game's state
 * @param <M> The class used to represent a player's move
 */
public class RandomMovePicker<S, M> implements IMovePicker<S, M> {

    private static final Logger LOG = LogManager.getLogger(RandomMovePicker.class);

    private final Random random = new Random();

    @Nonnull
    public M pickAMove(S state, Set<M> bestMoves) {
        LOG.info("Selecting from moves: {}", bestMoves);
        if (bestMoves.isEmpty()) {
            throw new RuntimeException("No moves to select from!");
        } else if (bestMoves.size() == 1) {
            return bestMoves.iterator().next();
        } else {
            List<M> moves = Lists.newArrayList(bestMoves);
            return moves.get(random.nextInt(moves.size()));
        }
    }
}
