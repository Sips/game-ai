package io.github.ddebree.game.ai.core.optimization.genetic;

import com.google.common.collect.ImmutableList;
import io.github.ddebree.game.ai.core.move.picker.RandomMovePicker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.ToIntFunction;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class GeneticAlgorithm<G> {

    private static final Logger LOG = LogManager.getLogger(RandomMovePicker.class);

    private int populationSize = 100;
    private int tournamentSize = 5;

    private double crossoverRate = 0.05;
    private double mutationRate = 0.015;

    private int numberOfGenes = -1;
    private int maxFitness = Integer.MAX_VALUE;
    private int numberOfGenerations = -1;
    private GeneFactory<G> geneFactory;
    private ToIntFunction<List<G>> fitnessFunction;

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
            int fitness = currentFittest.getFitness();
            LOG.debug("Generation: {}, Fittest: {} (Fittest: {}", generationCount, currentFittest, fitness);
            if (fitness >= maxFitness) {
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

    public GeneticAlgorithm<G> withNumberOfGenes(int numberOfGenes) {
        this.numberOfGenes = numberOfGenes;
        return this;
    }

    public GeneticAlgorithm<G> withNumberOfGenerations(int numberOfGenerations) {
        this.numberOfGenerations = numberOfGenerations;
        return this;
    }

    public GeneticAlgorithm<G> withMaximumFitness(int maxFitness) {
        this.maxFitness = maxFitness;
        return this;
    }

    public GeneticAlgorithm<G> withGeneFactory(GeneFactory<G> geneFactory) {
        this.geneFactory = geneFactory;
        return this;
    }

    public GeneticAlgorithm<G> withFitnessFunction(ToIntFunction<List<G>> fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
        return this;
    }

    private Population evolvePopulation(Population population) {

        List<Individual> newPopulation = new ArrayList<>(population.individuals.size());

        for (int index = 0; index < population.individuals.size(); ++index) {
            Individual firstParent = randomSelection(population);
            Individual secondParent = randomSelection(population);

            Individual child = crossoverAndMutate(firstParent, secondParent);

            newPopulation.add(child);
        }

        return new Population(newPopulation);
    }

    private Individual randomSelection(Population population) {
        return population.getFittestInSubPopulation(tournamentSize);
    }

    private Individual crossoverAndMutate(Individual firstParent, Individual secondParent) {
        List<G> newSolution = new ArrayList<>(numberOfGenes);
        for (int geneIndex = 0; geneIndex < numberOfGenes; ++geneIndex) {
            G gene;
            if (ThreadLocalRandom.current().nextDouble() <= crossoverRate) {
                gene = firstParent.genes.get(geneIndex);
            } else {
                gene = secondParent.genes.get(geneIndex);
            }
            if (ThreadLocalRandom.current().nextDouble() <= mutationRate) {
                gene = geneFactory.getRandomGene(geneIndex);
            }
            newSolution.add(gene);

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
            Individual fittestIndividual = individuals.get(0);
            for (Individual individual : individuals) {
                if (fittestIndividual.getFitness() <= individual.getFitness()) {
                    fittestIndividual = individual;
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

        int getFitness() {
            return fitnessFunction.applyAsInt(genes);
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
