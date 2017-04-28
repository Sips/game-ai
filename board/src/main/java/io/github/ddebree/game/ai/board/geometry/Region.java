package io.github.ddebree.game.ai.board.geometry;

import javax.annotation.Nullable;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class Region {

    private static final Region EMPTY_REGION = new EmptyRegion();

    public static Region empty() {
        return EMPTY_REGION;
    }

    public abstract Stream<Point> getAllPoints();

    public abstract IntStream getXRange();
    public abstract IntStream getYRange();

    @Nullable
    public abstract Point getTopLeftPoint();
    @Nullable
    public abstract Point getBottomRightPoint();
    @Nullable
    public abstract Point getCenterPoint();

    public abstract int size();
    public abstract boolean isConnected(Region region);

    public abstract boolean encloses(Region region);
    public abstract boolean encloses(Point p);

    public abstract Region span(Point point);
    public abstract Region span(Region region);

    private static class EmptyRegion extends Region {

        @Override
        public Stream<Point> getAllPoints() {
            return Stream.empty();
        }

        @Override
        public IntStream getXRange() {
            return IntStream.empty();
        }

        @Override
        public IntStream getYRange() {
            return IntStream.empty();
        }

        @Override
        public NonEmptyRegion span(Point point) {
            return NonEmptyRegion.at(point);
        }

        @Override
        public Region span(Region region) {
            return region;
        }

        @Nullable
        @Override
        public Point getTopLeftPoint() {
            return null;
        }

        @Nullable
        @Override
        public Point getBottomRightPoint() {
            return null;
        }

        @Nullable
        @Override
        public Point getCenterPoint() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isConnected(Region region) {
            return false;
        }

        @Override
        public boolean encloses(Region region) {
            return false;
        }

        @Override
        public boolean encloses(Point p) {
            return false;
        }
    }

}
