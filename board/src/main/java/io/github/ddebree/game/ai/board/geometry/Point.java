package io.github.ddebree.game.ai.board.geometry;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

/**
 * A point on two dimensional board
 */
public class Point implements Comparable<Point> {

    private static final Table<Integer, Integer, Point> POINTS = TreeBasedTable.create();

    private final int x;
    private final int y;

    public static Point at(int x, int y) {
        Point point = POINTS.get(x, y);
        if (point == null) {
            point = new Point(x, y);
            synchronized (POINTS) {
                final Point oldPoint = POINTS.get(x, y);
                //Catch the case where someone else already put a point in the map:
                if (oldPoint == null) {
                    POINTS.put(x, y, point);
                } else {
                    point = oldPoint;
                }
            }
        }
        return point;
    }

    public static Point random(int size) {
        int x = (int) (Math.random() * size);
        int y = (int) (Math.random() * size);
        return Point.at(x, y);
    }

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double distanceTo(Point otherPoint) {
        double deltaX = x - otherPoint.x;
        double deltaY = y - otherPoint.y;
        return Math.sqrt( (deltaX * deltaX) + (deltaY * deltaY) );
    }

    public int manhattanDistance(Point otherPoint) {
        int deltaX = x - otherPoint.x;
        int deltaY = y - otherPoint.y;
        return Math.abs(deltaX) + Math.abs(deltaY);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        return hash;
    }

    @Override
    public int compareTo(Point o) {
        return ComparisonChain.start()
                .compare(x, o.x)
                .compare(y, o.y)
                .result();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Point{x=" + x + ", y=" + y + '}';
    }

}
