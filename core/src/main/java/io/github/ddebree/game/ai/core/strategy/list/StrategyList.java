package io.github.ddebree.game.ai.core.strategy.list;

import com.google.common.collect.ImmutableList;

import io.github.ddebree.game.ai.core.strategy.IStrategy;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Run through a list of strategies, and pick the first strategy that returns a result.
 *
 * This is useful if you want to build a series of different strategies depending on what state the
 * game is in. The first one that produces a a result will be used.
 *
 * @param <S> The class that represents a game's state
 * @param <P> The class that represents a key to the current player
 * @param <M> The class used to represent a player's move
 */
public class StrategyList<S, P, M> implements IStrategy<S, P, M> {
    
    private static final Logger LOG = LogManager.getLogger(StrategyList.class);
    
    protected final ImmutableList<IStrategy<S, P, M>> strategies;

    public StrategyList(@Nonnull IStrategy<S, P, M>... strategy) {
        this(ImmutableList.copyOf(strategy));
    }

    public StrategyList(@Nonnull Iterable<IStrategy<S, P, M>> strategies) {
        checkNotNull(strategies);
        this.strategies = ImmutableList.copyOf(strategies);
        int i = 0;
        for (IStrategy<S, P, M> strategy : strategies) {
            LOG.log(Level.INFO, "Strategy[{0}] = {1}", i, strategy);
            i++;
        }
    }

    @Nonnull
    @Override
    public Stream<M> getBestMoves(@Nonnull final S state, P playerKey) {
        for (IStrategy<S, P, M> strategy : strategies) {
            List<M> moves = strategy.getBestMoves(state, playerKey).collect(Collectors.toList());
            if ( ! moves.isEmpty()) {
                LOG.info("Strategy {} returned a result. Using it", strategy);
                return moves.stream();
            } else {
                LOG.info("Strategy {} returned nothing. Moving on to next strategy", strategy);
            }
        }
        return Stream.empty();
    }
    
}
