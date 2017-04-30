package io.github.ddebree.game.ai.concurrent.state;

import io.github.ddebree.game.ai.concurrent.move.ConcurrentMove;

import javax.annotation.Nonnull;

public interface IConcurrentNextStateBuilder<S, P, M> {

    @Nonnull
    S buildNextState(@Nonnull S currentState, @Nonnull ConcurrentMove<P, M> move);

}
