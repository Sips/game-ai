package io.github.ddebree.game.ai.tictactoe;

import io.github.ddebree.game.ai.core.executor.gameover.IGameOverTester;
import io.github.ddebree.game.ai.core.player.TwoPlayerKey;
import io.github.ddebree.game.ai.core.score.IStateScorer;

import java.util.Optional;

public class StateScorer implements IStateScorer<State, TwoPlayerKey> {

    private IGameOverTester<State, TwoPlayerKey> gameOverTester = new GameOverTester();

    @Override
    public int scoreState(State state, TwoPlayerKey twoPlayer) {
        Optional<TwoPlayerKey> winner = gameOverTester.getWinner(state);
        if (winner.isPresent()) {
            return winner.get() == twoPlayer ? +1 : -1;
        }
        return 0;
    }

}
