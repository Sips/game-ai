package io.github.ddebree.game.ai.core.score;

/**
 * Scorer to score a particular state from the point of the player.
 *
 * @param <S> The state class
 * @param <P> The player key class
 */
public interface IStateScorer<S, P> {

    /**
     * The relative score for the particular state (at that point in the game)
     *
     * Higher values indicate a "better" score
     *
     * The strategies will generally try to find the moves that result in the highest possible
     * score values
     *
     * @param state The state to score
     * @param playerKey The player we wish to score
     * @return the relative score value
     */
    int scoreState(S state, P playerKey);

}
