package io.github.ddebree.game.ai.core.optimization;

import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

/**
 * Algorithm to find the local maximum value
 *
 * @param <S>
 */
public class SimulatedAnnealing<S> implements SolutionFinder<S> {

    private double startTemperature = 10000;
    private double coolingRate = 0.003;
    private S initialState;
    private UnaryOperator<S> newStateFunction;

    public S findMaximumSolution(ToDoubleFunction<S> temperatureFunction) {
        double temperature = startTemperature;

        S currentBest = initialState;
        double currentTemperature = temperatureFunction.applyAsDouble(currentBest);

        while (temperature > 1) {

            S newSolution = newStateFunction.apply(currentBest);
            double newTemperature =  temperatureFunction.applyAsDouble(newSolution);

            if (newTemperature > currentTemperature) {
                currentBest = newSolution;
                currentTemperature = newTemperature;
            } else {
                double acceptanceProb = Math.exp((newTemperature - currentTemperature) / temperature);
                if (acceptanceProb > Math.random()) {
                    currentBest = newSolution;
                    currentTemperature = newTemperature;
                }
            }
            temperature *= 1 - coolingRate;
        }
        return currentBest;
    }

    public static <S> SimulatedAnnealing<S> simulatedAnnealing() {
        return new SimulatedAnnealing<>();
    }

    public SimulatedAnnealing<S> withInitialState(S initialState) {
        this.initialState = initialState;
        return this;
    }

    public SimulatedAnnealing<S> withStartTemperature(double startTemperature) {
        this.startTemperature = startTemperature;
        return this;
    }

    public SimulatedAnnealing<S> withCoolingRate(double coolingRate) {
        this.coolingRate = coolingRate;
        return this;
    }

    public SimulatedAnnealing<S> withNewStateFunction(UnaryOperator<S> newStateFunction) {
        this.newStateFunction = newStateFunction;
        return this;
    }
}
