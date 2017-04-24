package io.github.ddebree.game.ai.core.player;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class TwoPlayerKeyTest {

    /**
     * Test of values method, of class TwoPlayer.
     */
    @Test
    public void testValues() {
        TwoPlayerKey[] expResult = new TwoPlayerKey[] {TwoPlayerKey.PLAYER_1, TwoPlayerKey.PLAYER_2};
        TwoPlayerKey[] result = TwoPlayerKey.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of otherPlayer method, of class TwoPlayer.
     */
    @Test
    public void testOtherPlayer() {
        assertEquals(TwoPlayerKey.PLAYER_1.otherPlayer(), TwoPlayerKey.PLAYER_2);
        assertEquals(TwoPlayerKey.PLAYER_2.otherPlayer(), TwoPlayerKey.PLAYER_1);
    }
    
}
