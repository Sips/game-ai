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
 * @param <S> The class that represents a game's state
 * @param <P> The class that represents a key to the current player
 * @param <M> The class used to represent a player's move
 */
public class SimpleScoreStrategy<S, P, M> implements IStrategy<S, P, M> {

    @Nonnull
    private final IMoveFactory<S, P, M> moveFactory;
    @Nonnull
    private final INextStateBuilder<S, P, M> nextStateFactory;
    @Nonnull
    private final IStateScorer<S, P> stateScorer;

    public SimpleScoreStrategy(@Nonnull final IMoveFactory<S, P, M> moveFactory,
                                  @Nonnull final IStateScorer<S, P> stateScorer,
                                  @Nonnull final INextStateBuilder<S, P, M> nextStateFactory) {
        this.moveFactory = checkNotNull(moveFactory);
        this.nextStateFactory = checkNotNull(nextStateFactory);
        this.stateScorer = checkNotNull(stateScorer);
    }

    @Nonnull
    @Override
    public Stream<M> getBestMoves(@Nonnull S state, P playerKey) {
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
