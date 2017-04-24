package io.github.ddebree.game.ai.core.move.picker;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

/**
 * Pick any move that is read from the move stream
 *
 * @param <S> The class that represents a game's state
 * @param <M> The class used to represent a player's move
 */
public class AnyMovePicker<S, M> implements IMovePicker<S, M> {

    @Nonnull
    public M pickAMove(S state, Stream<M> bestMoves) {
        return bestMoves.findAny()
                .orElseThrow(() -> new RuntimeException("No moves to select from!"));
    }
}
