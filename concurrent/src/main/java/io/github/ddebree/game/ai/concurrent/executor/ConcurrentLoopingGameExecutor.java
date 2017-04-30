package io.github.ddebree.game.ai.concurrent.executor;

import com.google.common.base.Stopwatch;
import io.github.ddebree.game.ai.concurrent.move.ConcurrentMove;
import io.github.ddebree.game.ai.concurrent.state.IConcurrentNextStateBuilder;
import io.github.ddebree.game.ai.core.executor.gameover.IGameOverTester;
import io.github.ddebree.game.ai.core.executor.player.PlayerExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class ConcurrentLoopingGameExecutor<S, P, M> implements Runnable {

    private static final Logger LOG = LogManager.getLogger(ConcurrentLoopingGameExecutor.class);

    private List<PlayerExecutor<S, P, M>> players = new ArrayList<>();

    @Nonnull
    private IGameOverTester<S, P> gameOverTester;
    @Nonnull
    private Supplier<? extends S> stateReader;
    @Nonnull
    private IConcurrentNextStateBuilder<S, P, M> nextStateBuilder;

    public void run() {
        checkNotNull(stateReader);

        long startTime = System.currentTimeMillis();

        S state = stateReader.get();

        runGameLoop(state);

        long runTime = System.currentTimeMillis() - startTime;
        LOG.info("Game executor finished in " + runTime + " ms.");
    }

    protected void runGameLoop(S state) {
        int round = 0;
        while ( ! gameOverTester.isGameOver(state)) {
            round++;

            Map<P, M> playerMoves = new HashMap<>();

            System.out.println();
            System.out.println("###########################################");
            System.out.println("Round " + round);
            System.out.println();
            System.out.println(state.toString());
            System.out.println();

            for (PlayerExecutor<S, P, M> player : players) {
                final Stopwatch stopwatch = Stopwatch.createStarted();
                System.out.println("------------------------------------------");
                System.out.println("Getting " + player.getName() + "'s move");
                M move = player.getMove(state);
                System.out.println(player.getName() + "'s move is " + move + ". Move found in " + stopwatch.toString());

                playerMoves.put(player.getPlayerKey(), move);
            }

            state = nextStateBuilder.buildNextState(state, new ConcurrentMove<>(playerMoves));
        }

        System.out.println("\n###########################################\nFinal Board state\n");
        System.out.println(state.toString());

        Optional<P> winner = gameOverTester.getWinner(state);
        if (winner.isPresent()) {
            System.out.println(winner.get() + " won!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    public ConcurrentLoopingGameExecutor<S, P, M> withPlayer(PlayerExecutor<S, P, M> player) {
        this.players.add(player);
        return this;
    }

    public ConcurrentLoopingGameExecutor<S, P, M> withNextStateBuilder(IConcurrentNextStateBuilder<S, P, M> nextStateBuilder) {
        this.nextStateBuilder = checkNotNull(nextStateBuilder);
        return this;
    }

    public ConcurrentLoopingGameExecutor<S, P, M> withGameOverTester(IGameOverTester<S, P> gameOverTester) {
        this.gameOverTester = gameOverTester;
        return this;
    }



    public ConcurrentLoopingGameExecutor<S, P, M> withStateReader(Supplier<? extends S> stateReader) {
        this.stateReader = stateReader;
        return this;
    }

}
