package io.github.ddebree.game.ai.tictactoe;

import io.github.ddebree.game.ai.core.strategy.minmax.MinMaxStrategy;

public class BoardMinMax extends MinMaxStrategy<State, Move> {

    public BoardMinMax() {
        super(new MoveFactory(), new GameOverTester(), new NextStateBuilder());
    }

}
