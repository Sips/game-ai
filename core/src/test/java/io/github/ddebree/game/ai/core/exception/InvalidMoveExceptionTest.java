package io.github.ddebree.game.ai.core.exception;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class InvalidMoveExceptionTest {
    
    @Test
    public void testIsASingleton() {
        InvalidMoveException result1 = InvalidMoveException.INSTANCE;
        InvalidMoveException result2 = InvalidMoveException.INSTANCE;
        assertSame(result1, result2);
        assertTrue(result1 instanceof Exception);
    }
    
}
