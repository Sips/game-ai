package io.github.ddebree.game.ai.travelling.salesman;

import io.github.ddebree.game.ai.board.geometry.Point;
import io.github.ddebree.game.ai.core.optimization.SimulatedAnnealing;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {

        List<Point> cities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            cities.add(Point.random(100));
        }

        SingleTour initialSolution = SingleTour.randomTour(cities);

        SingleTour bestSolution = SimulatedAnnealing.<SingleTour>simulatedAnnealing()
                .withInitialState(initialSolution)
                .withStartTemperature(10000)
                .withCoolingRate(0.003)
                .withNewStateFunction(SingleTour::randomlySwappedCities)
                .findMaximumSolution(SingleTour::getDistance);

        System.out.println("Initial solution distance: " + initialSolution.getDistance());
        System.out.println("Final approximated solution's distance is: " + bestSolution.getDistance());
        System.out.println("Tour: " + bestSolution);
    }
}
