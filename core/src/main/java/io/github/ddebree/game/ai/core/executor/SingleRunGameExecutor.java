package io.github.ddebree.game.ai.core.executor;

import io.github.ddebree.game.ai.core.executor.player.PlayerExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

public class SingleRunGameExecutor<S, P, M> implements Runnable {

    private static final Logger LOG = LogManager.getLogger(SingleRunGameExecutor.class);

    private final long startTime = System.currentTimeMillis();

    private Supplier<S> stateReader;
    private PlayerExecutor<S, P, M> player;
    private Consumer<M> stateWriter;

    public static <S, P, M> SingleRunGameExecutor<S, P, M> aSingleGameRun() {
        return new SingleRunGameExecutor<>();
    }

    private SingleRunGameExecutor() {}

    public void run() {
        checkNotNull(stateReader);
        checkNotNull(player);
        checkNotNull(stateWriter);

        S state = stateReader.get();

        M bestMove = player.getMove(state);

        LOG.info("Player picked the best move to be: " + bestMove);

        stateWriter.accept(bestMove);

        long runTime = System.currentTimeMillis() - startTime;
        LOG.info("Single Game run finished in " + runTime + " ms.");
    }

    public SingleRunGameExecutor<S, P, M> withStateReader(@Nonnull Supplier<S> stateReader) {
        this.stateReader = checkNotNull(stateReader);
        return this;
    }

    public SingleRunGameExecutor<S, P, M> withPlayerExecutor(@Nonnull PlayerExecutor<S, P, M> player) {
        this.player = checkNotNull(player);
        return this;
    }

    public SingleRunGameExecutor<S, P, M> withStateWriter(@Nonnull Consumer<M> stateWriter) {
        this.stateWriter = checkNotNull(stateWriter);
        return this;
    }

}
