package io.github.ddebree.game.ai.core.executor.player;

import io.github.ddebree.game.ai.core.move.picker.AnyMovePicker;
import io.github.ddebree.game.ai.core.move.picker.IMovePicker;
import io.github.ddebree.game.ai.core.strategy.IStrategy;
import io.github.ddebree.game.ai.core.strategy.noop.NoopStrategy;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

public class PlayerExecutor<S, P, M> {

    public static <S, P, M> PlayerExecutor<S, P, M> aPlayerExecutor(@Nonnull P playerKey, @Nonnull String name) {
        return new PlayerExecutor<>(playerKey, name);
    }

    @Nonnull
    private final P playerKey;
    @Nonnull
    private final String name;
    @Nonnull
    private IStrategy<S, P, M> strategy = new NoopStrategy<>();
    @Nonnull
    private IMovePicker<S, M> movePicker = new AnyMovePicker<>();

    private PlayerExecutor(@Nonnull P playerKey, @Nonnull String name) {
        this.playerKey = checkNotNull(playerKey);
        this.name = checkNotNull(name);
    }

    public M getMove(S state) {
        return movePicker.pickAMove(state, strategy.getBestMoves(state, getPlayerKey()));
    }

    public P getPlayerKey() {
        return playerKey;
    }

    public String getName() {
        return name;
    }

    public PlayerExecutor<S, P, M> withStrategy(@Nonnull IStrategy<S, P, M> strategy) {
        this.strategy = checkNotNull(strategy);
        return this;
    }

    public PlayerExecutor<S, P, M> withMovePicker(@Nonnull IMovePicker<S, M> movePicker) {
        this.movePicker = checkNotNull(movePicker);
        return this;
    }


}
