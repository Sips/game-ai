package io.github.ddebree.game.ai.board.printer;

import io.github.ddebree.game.ai.board.geometry.NonEmptyRegion;
import io.github.ddebree.game.ai.board.geometry.Point;
import io.github.ddebree.game.ai.board.geometry.Region;

import java.util.Collection;

/**
 * A utility class to build a toString representation of a set of points
 */
public class PointsToStringHelper extends AbstractToStringHelper<Collection<Point>> {

    @Override
    protected Region getRegion(Collection<Point> points) {
        Region tempRegion = NonEmptyRegion.at(Point.at(0, 0));
        for (Point p : points) {
            tempRegion = tempRegion.span(p);
        }
        return tempRegion;
    }

    @Override
    protected char printPoint(Collection<Point> points, Point point) {
        if (points.contains(point)) {
            return '*';
        } else {
            return ' ';
        }
    }

}
