package io.github.ddebree.game.ai.tictactoe;

import io.github.ddebree.game.ai.core.executor.gameover.IGameOverTester;
import io.github.ddebree.game.ai.core.player.TwoPlayerKey;

import java.util.Optional;

public class GameOverTester implements IGameOverTester<State, TwoPlayerKey> {

    private static final Move[][] BOARD_LINES = new Move[][]{
            //Crosses:
            { Move.TOP_LEFT, Move.MIDDLE_MIDDLE, Move.BOTTOM_RIGHT },
            { Move.BOTTOM_LEFT, Move.MIDDLE_MIDDLE, Move.TOP_RIGHT },

            //Vertical:
            { Move.TOP_LEFT, Move.MIDDLE_LEFT, Move.BOTTOM_LEFT},
            { Move.TOP_MIDDLE, Move.MIDDLE_MIDDLE, Move.BOTTOM_MIDDLE },
            { Move.TOP_RIGHT, Move.MIDDLE_RIGHT, Move.BOTTOM_RIGHT},

            //Horizontal:
            { Move.TOP_LEFT, Move.TOP_MIDDLE, Move.TOP_RIGHT },
            { Move.MIDDLE_LEFT, Move.MIDDLE_MIDDLE, Move.MIDDLE_RIGHT },
            { Move.BOTTOM_LEFT, Move.BOTTOM_MIDDLE, Move.BOTTOM_RIGHT }
    };

    public boolean isGameOver(State state) {
        return getWinner(state).isPresent() || state.getOccupiedPoints().size() == Move.values().length;
    }

    @Override
    public Optional<TwoPlayerKey> getWinner(State state) {
        for (Move[] boardLine : BOARD_LINES) {
            TwoPlayerKey player0 = state.getOccupiedPoints().get(boardLine[0]);
            if (player0 != null
                    && player0 == state.getOccupiedPoints().get(boardLine[1])
                    && player0 == state.getOccupiedPoints().get(boardLine[2])) {
                return Optional.of(player0);
            }
        }
        return Optional.empty();
    }


}
