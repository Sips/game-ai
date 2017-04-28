package io.github.ddebree.game.ai.tictactoe;

import com.google.common.collect.ImmutableSet;
import io.github.ddebree.game.ai.core.move.IMoveFactory;
import io.github.ddebree.game.ai.core.player.TwoPlayerKey;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public class MoveFactory implements IMoveFactory<State, TwoPlayerKey, Move> {

    private static final ImmutableSet<Move> ALL_MOVES = ImmutableSet.copyOf(Move.values());

    @Nonnull
    @Override
    public Stream<Move> getMoves(@Nonnull State state, @Nonnull TwoPlayerKey playerKey) {
        return ALL_MOVES.stream()
                .filter(move -> state.getOccupiedPoints().get(move) == null);
    }
}
