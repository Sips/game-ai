package io.github.ddebree.game.ai.core.executor;

import io.github.ddebree.game.ai.core.exception.InvalidMoveException;
import io.github.ddebree.game.ai.core.player.TwoPlayerKey;
import io.github.ddebree.game.ai.core.executor.gameover.IGameOverTester;
import io.github.ddebree.game.ai.core.executor.player.PlayerExecutor;
import io.github.ddebree.game.ai.core.state.INextStateBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

public class TurnBasedLoopingGameExecutor<S, M> implements Runnable {

    private static final Logger LOG = LogManager.getLogger(TurnBasedLoopingGameExecutor.class);

    private Supplier<? extends S> stateReader;
    private Supplier<TwoPlayerKey> firstPlayerSelector = () -> TwoPlayerKey.PLAYER_1;
    private INextStateBuilder<S, TwoPlayerKey, M> nextStateBuilder;
    private Map<TwoPlayerKey, PlayerExecutor<S, TwoPlayerKey, M>> players = new HashMap<>();
    private IGameOverTester<S, TwoPlayerKey> gameOverTester;
    private int loopLimit = Integer.MAX_VALUE;

    public void run() {
        checkNotNull(stateReader);
        checkNotNull(nextStateBuilder);

        long startTime = System.currentTimeMillis();

        S state = stateReader.get();

        TwoPlayerKey turn = firstPlayerSelector.get();

        int loopCount = 0;

        while ( ! gameOverTester.isGameOver(state) && loopCount < loopLimit) {
            loopCount++;

            System.out.println();
            System.out.println("###########################################");
            System.out.println();
            System.out.println(turn + "'s turn");
            System.out.println(state.toString());

            M move = players.get(turn).getMove(state);

            try {
                state = nextStateBuilder.buildNextState(state, turn, move);
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }

            turn = turn.otherPlayer();
        }

        System.out.println("\n###########################################\nFinal Board state\n");
        System.out.println(state.toString());

        Optional<TwoPlayerKey> winner = gameOverTester.getWinner(state);
        if (winner.isPresent() && winner.get() == TwoPlayerKey.PLAYER_1) {
            System.out.println("Unfortunately, you lost!");
        } else if (winner.isPresent() && winner.get() == TwoPlayerKey.PLAYER_2) {
            System.out.println("You win!");
        } else {
            System.out.println("It's a draw!");
        }

        long runTime = System.currentTimeMillis() - startTime;
        LOG.info("Game executor finished in " + runTime + " ms.");
    }

    public TurnBasedLoopingGameExecutor<S, M> withFirstPlayerSelector(Supplier<TwoPlayerKey> firstPlayerSelector) {
        this.firstPlayerSelector = firstPlayerSelector;
        return this;
    }

    public TurnBasedLoopingGameExecutor<S, M> withPlayer1Executor(PlayerExecutor<S, TwoPlayerKey, M> player1Player) {
        this.players.put(TwoPlayerKey.PLAYER_1, player1Player);
        return this;
    }

    public TurnBasedLoopingGameExecutor<S, M> withPlayer2Executor(PlayerExecutor<S, TwoPlayerKey, M> player2Player) {
        this.players.put(TwoPlayerKey.PLAYER_2, player2Player);
        return this;
    }

    public TurnBasedLoopingGameExecutor<S, M> withNextStateBuilder(INextStateBuilder<S, TwoPlayerKey, M> nextStateBuilder) {
        this.nextStateBuilder = nextStateBuilder;
        return this;
    }

    public TurnBasedLoopingGameExecutor<S, M> withGameOverTester(IGameOverTester<S, TwoPlayerKey> gameOverTester) {
        this.gameOverTester = gameOverTester;
        return this;
    }

    public TurnBasedLoopingGameExecutor<S, M> withStateReader(Supplier<? extends S> stateReader) {
        this.stateReader = stateReader;
        return this;
    }

    public TurnBasedLoopingGameExecutor<S, M> withLoopLimit(int loopLimit) {
        this.loopLimit = loopLimit;
        return this;
    }
}
