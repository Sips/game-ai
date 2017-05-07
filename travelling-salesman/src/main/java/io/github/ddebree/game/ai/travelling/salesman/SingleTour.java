package io.github.ddebree.game.ai.travelling.salesman;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import io.github.ddebree.game.ai.board.geometry.Point;

import java.util.Collections;
import java.util.List;

public class SingleTour {

    private final ImmutableList<Point> tour;
    private final double distance;

    public static SingleTour randomTour(List<Point> cities) {
        List<Point> tour = Lists.newArrayList(cities);
        Collections.shuffle(tour);
        return new SingleTour(tour);
    }

    private SingleTour(List<Point> tour) {
        this.tour = ImmutableList.copyOf(tour);
        int tourDistance = 0;
        for (int i = 0; i < tour.size(); i++) {
            Point fromCity = tour.get(i);
            Point destinationCity;

            if (i + 1 < tour.size()) {
                destinationCity = tour.get(i + 1);
            } else {
                destinationCity = tour.get(0);
            }
            tourDistance += fromCity.distanceTo(destinationCity);
        }
        this.distance = tourDistance;
    }

    public SingleTour randomlySwappedCities() {
        List<Point> newTour = Lists.newArrayList(tour);

        int randomIndex1 = (int) (tour.size() * Math.random());
        Point city1 = newTour.get(randomIndex1);

        int randomIndex2 = (int) (tour.size() * Math.random());
        Point city2 = newTour.get(randomIndex2);

        newTour.set(randomIndex2, city1);
        newTour.set(randomIndex1, city2);

        return new SingleTour(newTour);
    }

    public double getDistance() {
        return this.distance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Point city : tour) {
            if (sb.length() > 0) {
                sb.append(" -> ");
            }
            sb.append("(")
                    .append(city.getX())
                    .append("-")
                    .append(city.getY())
                    .append(")");
        }
        return sb.toString();
    }
}
