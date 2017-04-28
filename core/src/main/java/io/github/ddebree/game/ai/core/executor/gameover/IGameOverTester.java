package io.github.ddebree.game.ai.core.executor.gameover;

import java.util.Optional;

public interface IGameOverTester<S, P> {

    boolean isGameOver(S state);

    default Optional<P> getWinner(S state) {
        return Optional.empty();
    }

}
