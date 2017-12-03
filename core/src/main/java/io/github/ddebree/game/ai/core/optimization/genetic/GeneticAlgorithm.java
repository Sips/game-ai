package io.github.ddebree.game.ai.core.optimization.genetic;

import com.google.common.collect.ImmutableList;
import io.github.ddebree.game.ai.core.move.picker.RandomMovePicker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.ToDoubleFunction;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * "Genetic algorithm (GA) is a metaheuristic inspired by the process of natural selection that belongs to the
 * larger class of evolutionary algorithms (EA). Genetic algorithms are commonly used to generate high-quality
 * solutions to optimization and search problems by relying on bio-inspired operators such as mutation, crossover
 * and selection" (Wikipedia)
 *
 * A genetic algorithm is a type of algorithm designed to find the optimal solution to a problem by progressively
 * applying concepts such as crossover and mutation to an input into a problem.
 *
 * @param <G>
 */
public class GeneticAlgorithm<G> {

    private static final Logger LOG = LogManager.getLogger(RandomMovePicker.class);

    private int populationSize = 100;
    private int tournamentSize = 5;

    private double crossoverRate = 0.05;
    private double mutationRate = 0.015;

    private boolean elitismSelection = true;
    private int numberOfGenes = -1;
    private int sufficientFitness = Integer.MAX_VALUE;
    private int numberOfGenerations = -1;
    private GeneFactory<G> geneFactory;
    private ToDoubleFunction<List<G>> fitnessFunction;

    public static <G1> GeneticAlgorithm<G1> aGeneticAlgorithmSearch() {
        return new GeneticAlgorithm<>();
    }

    public List<G> findFittestIndivdual() {
        checkState(numberOfGenes > 0);
        checkState(numberOfGenerations > 0);
        checkNotNull(geneFactory);
        checkNotNull(fitnessFunction);

        Population population = new Population(populationSize);

        Individual currentFittest = population.getFittest();

        for (int generationCount = 0; generationCount < numberOfGenerations; generationCount++) {
            double fitness = currentFittest.getFitness();
            LOG.debug("Generation: {}, Fittest: {} (Fittest: {}", generationCount, currentFittest, fitness);
            if (fitness >= sufficientFitness) {
                break;
            }
            population = evolvePopulation(population);
            currentFittest = population.getFittest();
        }

        LOG.debug("Solution found: {}", currentFittest);
        return currentFittest.genes;
    }

    public GeneticAlgorithm<G> withPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
    }

    public GeneticAlgorithm<G> withTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
        return this;
    }

    public GeneticAlgorithm<G> withCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
        return this;
    }

    public GeneticAlgorithm<G> withMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
        return this;
    }

    public GeneticAlgorithm<G> withElitismSelection(boolean elitismSelection) {
        this.elitismSelection = elitismSelection;
        return this;
    }

    public GeneticAlgorithm<G> withNumberOfGenes(int numberOfGenes) {
        this.numberOfGenes = numberOfGenes;
        return this;
    }

    public GeneticAlgorithm<G> withNumberOfGenerations(int numberOfGenerations) {
        this.numberOfGenerations = numberOfGenerations;
        return this;
    }

    public GeneticAlgorithm<G> withSufficientFitness(int sufficientFitness) {
        this.sufficientFitness = sufficientFitness;
        return this;
    }

    public GeneticAlgorithm<G> withGeneFactory(GeneFactory<G> geneFactory) {
        this.geneFactory = geneFactory;
        return this;
    }

    public GeneticAlgorithm<G> withFitnessFunction(ToDoubleFunction<List<G>> fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
        return this;
    }

    private Population evolvePopulation(Population population) {

        List<Individual> newPopulation = new ArrayList<>(populationSize);

        if (elitismSelection) {
            newPopulation.add(population.getFittest());
        }

        while (newPopulation.size() < populationSize) {
            Individual firstParent = population.getFittestInSubPopulation(tournamentSize);
            Individual secondParent = population.getFittestInSubPopulation(tournamentSize);

            newPopulation.add(crossoverAndMutate(firstParent, secondParent));
        }

        return new Population(newPopulation);
    }

    private Individual crossoverAndMutate(Individual firstParent, Individual secondParent) {
        List<G> newSolution = new ArrayList<>(numberOfGenes);
        for (int geneIndex = 0; geneIndex < numberOfGenes; ++geneIndex) {
            //If we are going to mutate, do that first, otherwise select from one of the two parents:
            if (ThreadLocalRandom.current().nextDouble() <= mutationRate) {
                newSolution.add(geneFactory.getRandomGene(geneIndex));
            } else if (ThreadLocalRandom.current().nextDouble() <= crossoverRate) {
                newSolution.add(firstParent.genes.get(geneIndex));
            } else {
                newSolution.add(secondParent.genes.get(geneIndex));
            }
        }
        return new Individual(newSolution);
    }

    private class Population {
        final List<Individual> individuals;

        Population(int size) {
            individuals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                individuals.add(new Individual());
            }
        }

        Population(List<Individual> individuals) {
            this.individuals = individuals;
        }

        private Individual getFittest() {
            Individual fittestIndividual = null;
            double currentBestFitness = 0;
            for (Individual individual : individuals) {
                double newFitness = individual.getFitness();
                if (fittestIndividual == null
                        || currentBestFitness <= newFitness) {
                    fittestIndividual = individual;
                    currentBestFitness = newFitness;
                }
            }
            return fittestIndividual;
        }

        private Individual getFittestInSubPopulation(int subPopulationSize) {
            Individual fittestIndividual = individuals.get(ThreadLocalRandom.current().nextInt(individuals.size()));
            for (int i = 0; i < subPopulationSize - 1; i++) {
                Individual nextIndividual = individuals.get(ThreadLocalRandom.current().nextInt(individuals.size()));
                if (fittestIndividual.getFitness() <= nextIndividual.getFitness()) {
                    fittestIndividual = nextIndividual;
                }

            }
            return fittestIndividual;
        }

    }

    private class Individual {
        final ImmutableList<G> genes;

        private Individual() {
            ImmutableList.Builder<G> builder = ImmutableList.builder();
            for (int i = 0; i < numberOfGenes; i++) {
                builder.add(geneFactory.getRandomGene(i));
            }
            this.genes = builder.build();
        }

        private Individual(List<G> genes) {
            this.genes = ImmutableList.copyOf(genes);
        }

        double getFitness() {
            return fitnessFunction.applyAsDouble(genes);
        }

        @Override
        public String toString() {
            return "Individual{" + genes + '}';
        }
    }

    public interface GeneFactory<G> {
        G getRandomGene(int index);
    }
}
