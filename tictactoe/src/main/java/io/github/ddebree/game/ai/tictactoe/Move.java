package io.github.ddebree.game.ai.tictactoe;

import io.github.ddebree.game.ai.board.geometry.Point;

/**
 * A move by the player
 */
public enum Move {
    
    TOP_LEFT(Point.at(0, 0)),
    TOP_MIDDLE(Point.at(1, 0)),
    TOP_RIGHT(Point.at(2, 0)),
    
    MIDDLE_LEFT(Point.at(0, 1)),
    MIDDLE_MIDDLE(Point.at(1, 1)),
    MIDDLE_RIGHT(Point.at(2, 1)),
    
    BOTTOM_LEFT(Point.at(0, 2)),
    BOTTOM_MIDDLE(Point.at(1, 2)),
    BOTTOM_RIGHT(Point.at(2, 2));

    private final Point point;

    Move(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

}
