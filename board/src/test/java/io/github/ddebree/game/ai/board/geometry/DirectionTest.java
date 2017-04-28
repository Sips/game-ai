package io.github.ddebree.game.ai.board.geometry;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class DirectionTest {

    @Test
    public void testValues() {
        assertEquals(Direction.values().length, 4);
    }

    @Test
    public void testGetNextX_int() {
        assertEquals(Direction.UP.getNextX(5), 5);
        assertEquals(Direction.DOWN.getNextX(5), 5);
        assertEquals(Direction.LEFT.getNextX(5), 4);
        assertEquals(Direction.RIGHT.getNextX(5), 6);
    }

    @Test
    public void testGetNextX_int_int() {
        assertEquals(Direction.UP.getNextX(5, 3), 5);
        assertEquals(Direction.DOWN.getNextX(5, 3), 5);
        assertEquals(Direction.LEFT.getNextX(5, 3), 2);
        assertEquals(Direction.RIGHT.getNextX(5, 3), 8);
    }

    @Test
    public void testGetNextY_int() {
        assertEquals(Direction.UP.getNextY(5), 4);
        assertEquals(Direction.DOWN.getNextY(5), 6);
        assertEquals(Direction.LEFT.getNextY(5), 5);
        assertEquals(Direction.RIGHT.getNextY(5), 5);
    }

    @Test
    public void testGetNextY_int_int() {
        assertEquals(Direction.UP.getNextY(5, 3), 2);
        assertEquals(Direction.DOWN.getNextY(5, 3), 8);
        assertEquals(Direction.LEFT.getNextY(5, 3), 5);
        assertEquals(Direction.RIGHT.getNextY(5, 3), 5);
    }

    /**
     * Test of nextPoint method, of class Direction.
     */
    @Test
    public void testNextPoint_Direction() {
        final Point testPoint = Point.at(10, 20);
        assertEquals(Point.at(10, 19), Direction.UP.nextPoint(testPoint));
        assertEquals(Point.at(10, 21), Direction.DOWN.nextPoint(testPoint));
        assertEquals(Point.at(9,  20), Direction.LEFT.nextPoint(testPoint));
        assertEquals(Point.at(11, 20), Direction.RIGHT.nextPoint(testPoint));
    }

    /**
     * Test of nextPoint method, of class Direction.
     */
    @Test
    public void testNextPoint_Direction_int() {
        final Point testPoint = Point.at(10, 20);
        assertEquals(Point.at(10, 17), Direction.UP.nextPoint(testPoint, 3));
        assertEquals(Point.at(10, 23), Direction.DOWN.nextPoint(testPoint, 3));
        assertEquals(Point.at(7,  20), Direction.LEFT.nextPoint(testPoint, 3));
        assertEquals(Point.at(13, 20), Direction.RIGHT.nextPoint(testPoint, 3));
    }

    @Test
    public void testIsUpDown() {
        assertTrue(Direction.UP.isUpDown());
        assertTrue(Direction.DOWN.isUpDown());
        assertFalse(Direction.LEFT.isUpDown());
        assertFalse(Direction.RIGHT.isUpDown());
    }

    @Test
    public void testIsLeftRight() {
        assertFalse(Direction.UP.isLeftRight());
        assertFalse(Direction.DOWN.isLeftRight());
        assertTrue(Direction.LEFT.isLeftRight());
        assertTrue(Direction.RIGHT.isLeftRight());
    }

    @Test
    public void testOppositeDirection() {
        assertEquals(Direction.UP.oppositeDirection(), Direction.DOWN);
        assertEquals(Direction.DOWN.oppositeDirection(), Direction.UP);
        assertEquals(Direction.LEFT.oppositeDirection(), Direction.RIGHT);
        assertEquals(Direction.RIGHT.oppositeDirection(), Direction.LEFT);
    }

    @Test
    public void testLeftTurn() {
        assertEquals(Direction.UP.leftTurnDirection(), Direction.LEFT);
        assertEquals(Direction.DOWN.leftTurnDirection(), Direction.RIGHT);
        assertEquals(Direction.LEFT.leftTurnDirection(), Direction.DOWN);
        assertEquals(Direction.RIGHT.leftTurnDirection(), Direction.UP);
    }

    @Test
    public void testRightTurn() {
        assertEquals(Direction.UP.rightTurnDirection(), Direction.RIGHT);
        assertEquals(Direction.DOWN.rightTurnDirection(), Direction.LEFT);
        assertEquals(Direction.LEFT.rightTurnDirection(), Direction.UP);
        assertEquals(Direction.RIGHT.rightTurnDirection(), Direction.DOWN);
    }

    @Test
    public void testTurns() {
        assertEquals(Direction.UP.rightTurnDirection().oppositeDirection(), Direction.UP.leftTurnDirection());
        assertEquals(Direction.DOWN.rightTurnDirection().oppositeDirection(), Direction.DOWN.leftTurnDirection());
        assertEquals(Direction.LEFT.rightTurnDirection().oppositeDirection(), Direction.LEFT.leftTurnDirection());
        assertEquals(Direction.RIGHT.rightTurnDirection().oppositeDirection(), Direction.RIGHT.leftTurnDirection());
    }
    
}
