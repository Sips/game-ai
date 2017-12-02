package io.github.ddebree.game.ai.graph.search;

import com.google.common.base.MoreObjects;
import io.github.ddebree.game.ai.graph.GraphFactory;
import io.github.ddebree.game.ai.graph.ValueGraphFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarSearch<N> extends AbstractGraphSearchAlgorithm<N> implements GraphSearchAlgorithm<N> {

    private final HeuristicCalculator<N> heuristicCalculator;

    public AStarSearch(HeuristicCalculator<N> heuristicCalculator) {
        this.heuristicCalculator = heuristicCalculator;
    }

    public List<N> search(GraphFactory<N> graphFactory, N sourceNode, N goalNode) {
        final Map<N, Double> gScore = new HashMap<>();
        final Map<N, Double> fScore = new HashMap<>();
        final Map<N, N> parentNode = new HashMap<>();

        final Set<N> exploredNodes = new HashSet<>();

        PriorityQueue<N> unexploredNodes = new PriorityQueue<>(
                Comparator.comparingDouble(o -> MoreObjects.firstNonNull(fScore.get(o), 0.0)));
        gScore.put(sourceNode, 0.0);
        unexploredNodes.add(sourceNode);

        while ( ! unexploredNodes.isEmpty()) {
            N currentNode = unexploredNodes.poll();
            exploredNodes.add(currentNode);

            if (currentNode.equals(goalNode)) {
                return reconstructPath(parentNode, sourceNode, goalNode);
            }

            for (N childNode : graphFactory.getSuccessors(currentNode)) {
                final double cost;
                if (graphFactory instanceof ValueGraphFactory) {
                    cost = ((ValueGraphFactory<N>)graphFactory).edgeCost(currentNode, childNode);
                } else {
                    cost = 1.0;
                }
                double tempGScore = gScore.get(currentNode) + cost;
                double tempFScore = tempGScore + heuristicCalculator.heuristic(childNode, goalNode);

                if (exploredNodes.contains(childNode) && (tempFScore >= fScore.get(childNode))) {
                    continue;
                } else if (!unexploredNodes.contains(childNode) || (tempFScore < fScore.get(childNode))) {

                    parentNode.put(childNode, currentNode);
                    gScore.put(childNode, tempGScore);
                    fScore.put(childNode, tempFScore);

                    if (unexploredNodes.contains(childNode)) {
                        unexploredNodes.remove(childNode);
                    }

                    unexploredNodes.add(childNode);
                }
            }
        }

        return Collections.emptyList();
    }

    public interface HeuristicCalculator<N> {
        double heuristic(N node1, N node2);
    }

}
