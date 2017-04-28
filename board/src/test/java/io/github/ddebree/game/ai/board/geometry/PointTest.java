package io.github.ddebree.game.ai.board.geometry;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class PointTest {

    private final Point toTest = Point.at(10, 20);
    
    /**
     * Test of at method, of class Point.
     */
    @Test
    public void testGetXY() {
        assertEquals(toTest.getX(), 10);
        assertEquals(toTest.getY(), 20);
    }

    /**
     * Test of hashCode method, of class Point.
     */
    @Test
    public void testHashCode() {
        assertNotEquals(toTest.hashCode(), 0);
        assertEquals(toTest.hashCode(), Point.at(10, 20).hashCode());
    }

    /**
     * Test of equals method, of class Point.
     */
    @Test
    public void testEquals() {
        assertFalse(toTest.equals(null));
        assertFalse(toTest.equals(new Object()));
        assertTrue(toTest.equals(toTest));
        assertTrue(toTest.equals(Point.at(10, 20)));
        assertFalse(toTest.equals(Point.at(11, 20)));
        assertFalse(toTest.equals(Point.at(10, 21)));
    }

    /**
     * Test of toString method, of class Point.
     */
    @Test
    public void testToString() {
        assertEquals(toTest.toString(), "Point{x=10, y=20}");
    }
    
}
