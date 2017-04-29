package io.github.ddebree.game.ai.core.strategy.list;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Stream<M> getBestMoves(@Nonnull final S state, @Nonnull final P playerKey) {
        checkNotNull(state);
        checkNotNull(playerKey);

        List<Future<Stream<M>>> results = new ArrayList<>(strategies.size());
        for (IStrategy<S, P, M> strategy : strategies) {
            results.add(executorService.submit(strategy.asCallable(state, playerKey)));
        }
        for (Future<Stream<M>> result : results) {
            try {
                List<M> val = result.get().collect(Collectors.toList());
                if ( ! val.isEmpty()) {
                    return val.stream();
                }
            } catch (InterruptedException | ExecutionException ignored) {
            }
        }
        
        return Stream.empty();
    }

}
