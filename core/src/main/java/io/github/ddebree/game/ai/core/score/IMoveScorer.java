package io.github.ddebree.game.ai.core.score;

import io.github.ddebree.game.ai.core.exception.InvalidMoveException;

/**
 * Utility class to score a move
 *
 * @param <S> The state class
 * @param <P> The player key class
 */
public interface IMoveScorer<S, P> {

    int scoreMove(S state, P move) throws InvalidMoveException;

}
