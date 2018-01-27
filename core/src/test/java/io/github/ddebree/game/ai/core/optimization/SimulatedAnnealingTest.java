package io.github.ddebree.game.ai.core.optimization;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

/**
 * Travelling salesperson problem, solved using simulated annealing
 *
 */
public class SimulatedAnnealingTest {

    private final static int NUMBER_OF_CITIES = 20;
    private final static int GRID_SIZE = 100;

    @Test
    public void testSingleTour() {
        SingleTour initialSolution = randomTour();

        SingleTour bestSolution = SimulatedAnnealing.<SingleTour>simulatedAnnealing()
                .withInitialState(initialSolution)
                .withStartTemperature(10000)
                .withCoolingRate(0.003)
                .withNewStateFunction(this::randomlySwappedCities)
                .findMinimumSolution(this::getDistance);

        System.out.println("Initial solution distance: " + getDistance(initialSolution));
        System.out.println("Final approximated solution's distance is: " + getDistance(bestSolution));
        System.out.println("Tour: " + bestSolution);

        assertTrue(getDistance(bestSolution) < getDistance(initialSolution));

    }

    private City randomCity() {
        int x = (int) (Math.random() * GRID_SIZE);
        int y = (int) (Math.random() * GRID_SIZE);
        return new City(x, y);
    }

    private SingleTour randomTour() {
        ImmutableList.Builder<City> builder = ImmutableList.builder();
        for (int i = 0; i < NUMBER_OF_CITIES; i++) {
            builder.add(randomCity());
        }
        return new SingleTour(builder.build());
    }

    private SingleTour randomlySwappedCities(SingleTour input) {
        List<City> newTour = Lists.newArrayList(input.tour);

        int randomIndex1 = (int) (NUMBER_OF_CITIES * Math.random());
        City city1 = newTour.get(randomIndex1);

        int randomIndex2 = (int) (NUMBER_OF_CITIES * Math.random());
        City city2 = newTour.get(randomIndex2);

        newTour.set(randomIndex2, city1);
        newTour.set(randomIndex1, city2);

        return new SingleTour(newTour);
    }

    private double getDistance(SingleTour input) {
        double tourDistance = 0;
        for (int i = 0; i < input.tour.size(); i++) {
            City fromCity = input.tour.get(i);
            City destinationCity;

            if (i + 1 < input.tour.size()) {
                destinationCity = input.tour.get(i + 1);
            } else {
                destinationCity = input.tour.get(0);
            }
            tourDistance += fromCity.distanceTo(destinationCity);
        }
        return tourDistance;
    }

    private class SingleTour {

        private final ImmutableList<City> tour;

        private SingleTour(List<City> tour) {
            this.tour = ImmutableList.copyOf(tour);
        }

        @Override
        public String toString() {
            return Joiner.on(" -> ").join(tour);
        }
    }

    private class City {
        final int x;
        final int y;

        City(int x, int y) {
            this.x = x;
            this.y = y;
        }

        double distanceTo(City otherPoint) {
            double deltaX = x - otherPoint.x;
            double deltaY = y - otherPoint.y;
            return Math.sqrt( (deltaX * deltaX) + (deltaY * deltaY) );
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final City other = (City) obj;
            return x == other.x && y == other.y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ')';
        }
    }

}
