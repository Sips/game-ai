package io.github.ddebree.game.ai.core.optimization;

import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

public class SimulatedAnnealing<S> implements UnaryOperator<S> {

    private double startTemperature = 10000;
    private double coolingRate = 0.003;
    private UnaryOperator<S> newStateFunction;
    private ToDoubleFunction<S> temperatureFunction;

    public S apply(S initialSate) {
        double temperature = startTemperature;

        S currentBest = initialSate;
        double currentTemperature = temperatureFunction.applyAsDouble(currentBest);

        while (temperature > 1) {

            S newSolution = newStateFunction.apply(currentBest);
            double newTemperature =  temperatureFunction.applyAsDouble(newSolution);

            if (newTemperature < currentTemperature) {
                currentBest = newSolution;
                currentTemperature = newTemperature;
            } else {
                double acceptanceProb = Math.exp((currentTemperature - newTemperature) / temperature);
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

    public SimulatedAnnealing<S> withTemperatureFunction(ToDoubleFunction<S> temperatureFunction) {
        this.temperatureFunction = temperatureFunction;
        return this;
    }
}
