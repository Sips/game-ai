package io.github.ddebree.game.ai.core.optimization.genetic;

import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.function.ToIntFunction;

public class GeneticAlgorithmTest {

    private static final int NUMBER_OF_GENES = 20;
    private static Random RANDOM_GENERATOR = new Random();

    @Test
    public void evolvePopulation() throws Exception {

        List<Integer> fittestIndividual = GeneticAlgorithm.<Integer>aGeneticAlgorithmSearch()
                .withNumberOfGenes(NUMBER_OF_GENES)
                .withSufficientFitness(NUMBER_OF_GENES)
                .withNumberOfGenerations(600)

                .withFitnessFunction(new ToIntFunction<List<Integer>>() {
                    @Override
                    public int applyAsInt(List<Integer> value) {
                        int fitness = 0;
                        for (int i = 0; i < NUMBER_OF_GENES; i++) {
                            if (value.get(i) == (i % 10)) {
                                fitness++;
                            }
                        }
                        return fitness;
                    }
                })
                .withGeneFactory(new GeneticAlgorithm.GeneFactory<Integer>() {
                    @Override
                    public Integer getRandomGene(int index) {
                        return RANDOM_GENERATOR.nextInt(10);
                    }
                })
                .findFittestIndivdual();

        System.out.println("Solution found:");
        System.out.println(fittestIndividual);
    }

}