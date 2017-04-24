package io.github.ddebree.game.ai.core.move.picker;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

/**
 * Class to pick a single move from a series of possible moves provided by the strategy
 *
 * @param <S> The class that represents a game's state
 * @param <M> The class used to represent a player's move
 */
public interface IMovePicker<S, M> {

    @Nonnull
    M pickAMove(S state, Stream<M> moves);

}
