package io.github.ddebree.game.ai.board.geometry;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Range;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class NonEmptyRegion extends Region {

    private static final LoadingCache<Point, NonEmptyRegion> SINGLE_POINT_REGIONS = CacheBuilder.newBuilder()
            .build(
                    new CacheLoader<Point, NonEmptyRegion>() {
                        public NonEmptyRegion load(@Nonnull Point point) {
                            return new SinglePointRegion(point);
                        }
                    });

    public static NonEmptyRegion at(Point point) {
        return SINGLE_POINT_REGIONS.getUnchecked(point);
    }

    public static NonEmptyRegion between(Point p1, Point p2) {
        //Shortcut if it is the same point:
        if (p1.equals(p2)) {
            return at(p1);
        }
        return new TwoPointRegion(p1, p2);
    }

    public static NonEmptyRegion sized(Point point, int width, int height) {
        return between(point, Point.at(
                point.getX() + width - 1,
                point.getY() + height - 1
        ));
    }

    @Nonnull
    public abstract Point getTopLeftPoint();
    @Nonnull
    public abstract Point getBottomRightPoint();
    @Nonnull
    public abstract Point getCenterPoint();

    private static class SinglePointRegion extends NonEmptyRegion {

        private Point point;

        public SinglePointRegion(Point point) {
            this.point = point;
        }

        @Override
        public Stream<Point> getAllPoints() {
            return Stream.of(point);
        }

        @Override
        public IntStream getXRange() {
            return IntStream.of(point.getX());
        }

        @Override
        public IntStream getYRange() {
            return IntStream.of(point.getY());
        }

        @Override
        public Region span(Point point) {
            if (this.point.equals(point)) {
                return this;
            } else {
                return between(this.point, point);
            }
        }

        @Override
        public Region span(Region region) {
            return region.span(point);
        }

        @Nonnull
        @Override
        public Point getTopLeftPoint() {
            return point;
        }

        @Nonnull
        @Override
        public Point getBottomRightPoint() {
            return point;
        }

        @Nonnull
        @Override
        public Point getCenterPoint() {
            return point;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public boolean isConnected(Region region) {
            return region.encloses(point);
        }

        public boolean encloses(Region region) {
            return region.size() == 1
                    && checkNotNull(region.getTopLeftPoint()).equals(point);
        }

        @Override
        public boolean encloses(Point p) {
            return p.equals(point);
        }
    }

    private static class TwoPointRegion extends NonEmptyRegion {

        private final int minx, miny, maxx, maxy;

        public TwoPointRegion(Point p1, Point p2) {
            this(Math.min(p1.getX(), p2.getX()),
                    Math.min(p1.getY(), p2.getY()),
                    Math.max(p1.getX(), p2.getX()),
                    Math.max(p1.getY(), p2.getY()));
        }

        TwoPointRegion(int minx, int miny, int maxx, int maxy) {
            this.minx = minx;
            this.miny = miny;
            this.maxx = maxx;
            this.maxy = maxy;
        }

        @Override
        public Stream<Point> getAllPoints() {
            //TODO: Could maybe do this without making a set. Directly using streams:
            Set<Point> toreturn = new HashSet<>();
            getXRange().forEach( x -> getYRange().forEach(y -> toreturn.add(Point.at(x, y))));
            return toreturn.stream();
        }

        @Override
        public IntStream getXRange() {
            return IntStream.range(minx, maxx + 1);
        }

        @Override
        public IntStream getYRange() {
            return IntStream.range(miny, maxy + 1);
        }

        @Override
        public Region span(Point point) {
            int minx = Math.min(this.minx, point.getX());
            int miny = Math.min(this.miny, point.getY());
            int maxx = Math.max(this.maxx, point.getX());
            int maxy = Math.max(this.maxy, point.getY());
            if (minx != this.minx || miny != this.miny || maxx != this.maxx || maxy != this.maxy) {
                return new TwoPointRegion(minx, miny, maxx, maxy);
            } else {
                return this;
            }
        }

        @Override
        public Region span(Region region) {
            return region.span(Point.at(minx, miny))
                    .span(Point.at(maxx, maxy));
        }

        @Nonnull
        @Override
        public Point getTopLeftPoint() {
            return Point.at(minx, miny);
        }

        @Nonnull
        @Override
        public Point getBottomRightPoint() {
            return Point.at(maxx, maxy);
        }

        @Nonnull
        @Override
        public final Point getCenterPoint() {
            return Point.at(
                    (minx + maxx) / 2,
                    (miny + maxy) / 2
            );
        }

        @Override
        public int size() {
            return (maxx - minx + 1) * (maxy - miny + 1);
        }

        @Override
        public boolean isConnected(Region region) {
            Point topLeft = region.getTopLeftPoint();
            Point bottomRight = region.getBottomRightPoint();
            return topLeft != null
                    && bottomRight != null
                    && Range.closed(topLeft.getX(), bottomRight.getX()).isConnected(Range.closed(minx, maxx))
                    && Range.closed(topLeft.getY(), bottomRight.getY()).isConnected(Range.closed(miny, maxy));
        }

        @Override
        public boolean encloses(Region region) {
            Point topLeft = region.getTopLeftPoint();
            Point bottomRight = region.getBottomRightPoint();
            return topLeft != null
                    && bottomRight != null
                    && topLeft.getX() >= minx
                    && topLeft.getY() >= miny
                    && bottomRight.getX() <= maxx
                    && bottomRight.getY() <= maxy;
        }

        public final boolean encloses(Point p) {
            return p.getX() >= minx
                    && p.getY() >= miny
                    && p.getX() <= maxx
                    && p.getY() <= maxy;
        }
    }

}
