package io.github.ddebree.game.ai.tictactoe;

import io.github.ddebree.game.ai.core.player.TwoPlayerKey;
import org.junit.Test;

import static io.github.ddebree.game.ai.tictactoe.StateBuilder.builder;
import static org.junit.Assert.assertEquals;

public class StateScorerTest {

    private final StateScorer stateScorer = new StateScorer();

    @Test
    public void testBasicScorer() {
        //Empty Board has zero score:
        assertEquals(0, stateScorer.scoreState(builder().build(), TwoPlayerKey.PLAYER_1));

        //The other player has won if they occupy every node on the board:
        assertEquals(-1, stateScorer.scoreState(builder().allOccupiedBy(TwoPlayerKey.PLAYER_2).build(), TwoPlayerKey.PLAYER_1));

        //I have won if I occupy every node on the board:
        assertEquals(1, stateScorer.scoreState(builder().allOccupiedBy(TwoPlayerKey.PLAYER_1).build(), TwoPlayerKey.PLAYER_1));
    }

}
