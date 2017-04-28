package io.github.ddebree.game.ai.core.strategy.score;

import io.github.ddebree.game.ai.core.exception.InvalidMoveException;
import io.github.ddebree.game.ai.core.move.IMoveFactory;
import io.github.ddebree.game.ai.core.score.IStateScorer;
import io.github.ddebree.game.ai.core.score.MinMaxScore;
import io.github.ddebree.game.ai.core.state.INextStateBuilder;
import io.github.ddebree.game.ai.core.strategy.IStrategy;

import javax.annotation.Nonnull;

import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A very simple strategy that looks one move in advance and picks the
 * one that results in the highest score
 *
 * @param <S> The state object
 * @param <M> The move object
 */
public class SimpleScoreStrategy<S, PK, M> implements IStrategy<S, PK, M> {

    @Nonnull
    private final IMoveFactory<S, PK, M> moveFactory;
    @Nonnull
    private final INextStateBuilder<S, PK, M> nextStateFactory;
    @Nonnull
    private final IStateScorer<S, PK> stateScorer;

    public SimpleScoreStrategy(@Nonnull final IMoveFactory<S, PK, M> moveFactory,
                                  @Nonnull final IStateScorer<S, PK> stateScorer,
                                  @Nonnull final INextStateBuilder<S, PK, M> nextStateFactory) {
        this.moveFactory = checkNotNull(moveFactory);
        this.nextStateFactory = checkNotNull(nextStateFactory);
        this.stateScorer = checkNotNull(stateScorer);
    }

    @Nonnull
    @Override
    public Stream<M> getBestMoves(@Nonnull S state, PK playerKey) {
        final MinMaxScore.Builder<M> results = MinMaxScore.builder();
        moveFactory.getMoves(state, playerKey).forEach(
                move -> {
                    try {
                        S nextState = nextStateFactory.buildNextState(state, playerKey, move);
                        results.addScoredMove(move, stateScorer.scoreState(nextState, playerKey));
                    } catch (InvalidMoveException ignored) {
                    }
                }
        );
        return results.build().getBestMoves().stream();
    }

}
