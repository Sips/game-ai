package io.github.ddebree.game.ai.tictactoe;

import io.github.ddebree.game.ai.core.executor.TurnBasedLoopingGameExecutor;
import io.github.ddebree.game.ai.core.executor.player.PlayerExecutor;
import io.github.ddebree.game.ai.core.player.TwoPlayerKey;

import java.util.Random;

public class SelfRunningTicTacToe extends TurnBasedLoopingGameExecutor<State, Move> {

    public static void main(String[] args) {
        new SelfRunningTicTacToe()
                .withStateReader(State::new)
                .withPlayer1Executor(PlayerExecutor.<State, TwoPlayerKey, Move>aPlayerExecutor(TwoPlayerKey.PLAYER_1, "Player 1")
                        .withStrategy(new BoardMinMax())
                )
                .withPlayer2Executor(PlayerExecutor.<State, TwoPlayerKey, Move>aPlayerExecutor(TwoPlayerKey.PLAYER_2, "Player 2")
                        .withStrategy(new BoardMinMax())
                )
                .withFirstPlayerSelector(() -> new Random().nextBoolean() ? TwoPlayerKey.PLAYER_1 : TwoPlayerKey.PLAYER_2
                )
                .withGameOverTester(new GameOverTester())
                .run();
    }

    @Override
    protected State placeMove(State state, Move userMove, TwoPlayerKey turn) {
        return state.placeAMove(userMove, turn);
    }

}