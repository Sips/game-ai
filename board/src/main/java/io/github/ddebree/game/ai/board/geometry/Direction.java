package io.github.ddebree.game.ai.board.geometry;

/**
 *
 *
 */
public enum Direction {
    
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);
    
    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
    
    private final int deltaX;
    private final int deltaY;
    
    public int getNextX(int x) {
        return x + deltaX;
    }
    
    public int getNextX(int x, int distance) {
        return x + (deltaX * distance);
    }
    
    public int getNextY(int y) {
        return y + deltaY;
    }
    
    public int getNextY(int y, int distance) {
        return y + (deltaY * distance);
    }

    public Point nextPoint(final Point p) {
        return Point.at(getNextX(p.getX()), getNextY(p.getY()));
    }

    public Point nextPoint(final Point p, final int distance) {
        return Point.at(getNextX(p.getX(), distance), getNextY(p.getY(), distance));
    }

    public boolean isUpDown() {
        return this.deltaX == 0;
    }
    
    public boolean isLeftRight() {
        return this.deltaY == 0;
    }
    
    public Direction oppositeDirection() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default: throw new RuntimeException("Unknown direction: " + this);
        }
    }
    
    public Direction leftTurnDirection() {
        switch (this) {
            case UP: return LEFT;
            case DOWN: return RIGHT;
            case LEFT: return DOWN;
            case RIGHT: return UP;
            default: throw new RuntimeException("Unknown direction: " + this);
        }
    }
    
    public Direction rightTurnDirection() {
        switch (this) {
            case UP: return RIGHT;
            case DOWN: return LEFT;
            case LEFT: return UP;
            case RIGHT: return DOWN;
            default: throw new RuntimeException("Unknown direction: " + this);
        }
    }
    
}
