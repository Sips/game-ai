package io.github.ddebree.game.ai.core.strategy.score;

import io.github.ddebree.game.ai.core.exception.InvalidMoveException;
import io.github.ddebree.game.ai.core.move.IMoveFactory;
import io.github.ddebree.game.ai.core.score.IMoveScorer;
import io.github.ddebree.game.ai.core.score.MinMaxScore;
import io.github.ddebree.game.ai.core.strategy.IStrategy;

import javax.annotation.Nonnull;

import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A strategy that scores based on the move itself, without any real
 * knowledge of what the future state looks like
 *
 * @param <S> The class that represents a game's state
 * @param <P> The class that represents a key to the current player
 * @param <M> The class used to represent a player's move
 */
public class MoveScoreStrategy<S, P, M> implements IStrategy<S, P, M> {

    @Nonnull
    private final IMoveFactory<S, P, M> moveFactory;
    @Nonnull
    private final IMoveScorer<S, M> moveScorer;

    protected MoveScoreStrategy(@Nonnull final IMoveFactory<S, P, M> moveFactory,
                                @Nonnull final IMoveScorer<S, M> moveScorer) {
        this.moveFactory = checkNotNull(moveFactory);
        this.moveScorer = checkNotNull(moveScorer);
    }

    @Nonnull
    @Override
    public Stream<M> getBestMoves(@Nonnull S state, P playerKey) {
        final MinMaxScore.Builder<M> results = MinMaxScore.builder();
        moveFactory.getMoves(state, playerKey).forEach(
                move -> {
                    try {
                        results.addScoredMove(move, moveScorer.scoreMove(state, move));
                    } catch (InvalidMoveException ignored) {
                    }
                }
        );
        return results.build().getBestMoves().stream();
    }



}
