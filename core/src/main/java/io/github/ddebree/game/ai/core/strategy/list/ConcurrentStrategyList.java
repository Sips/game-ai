package io.github.ddebree.game.ai.core.strategy.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import io.github.ddebree.game.ai.core.strategy.IStrategy;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The same as the normal strategy list, but each strategy is run in parallel in separate threads
 *
 * @param <S> The class that represents a game's state
 * @param <P> The class that represents a key to the current player
 * @param <M> The class used to represent a player's move
 */
public class ConcurrentStrategyList<S, P, M> extends StrategyList<S, P, M> {
    
    private final ExecutorService executorService;
    
    public ConcurrentStrategyList(final Iterable<IStrategy<S, P, M>> strategies, final ExecutorService executorService) {
        super(strategies);
        this.executorService = executorService;
    }

    @Nonnull
    @Override
    public Set<M> getBestMoves(@Nonnull final S state, @Nonnull final P playerKey) {
        checkNotNull(state);
        checkNotNull(playerKey);

        List<Future<Set<M>>> results = new ArrayList<>(strategies.size());
        for (IStrategy<S, P, M> strategy : strategies) {
            results.add(executorService.submit(strategy.asCallable(state, playerKey)));
        }
        for (Future<Set<M>> result : results) {
            try {
                Set<M> val = result.get();
                if ( ! val.isEmpty()) {
                    return val;
                }
            } catch (InterruptedException | ExecutionException ignored) {
            }
        }
        
        return Collections.EMPTY_SET;
    }

}
