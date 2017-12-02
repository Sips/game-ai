package io.github.ddebree.game.ai.graph.search;

import com.google.common.collect.ImmutableList;
import io.github.ddebree.game.ai.graph.GraphFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSearch<N> extends AbstractGraphSearchAlgorithm<N> implements GraphSearchAlgorithm<N> {

    @Override
    public List<N> search(GraphFactory<N> graphFactory, N sourceNode, N targetNode) {
        Set<N> visited = new HashSet<>();
        Queue<N> queue = new LinkedList<>();
        Map<N, N> parentNodes = new HashMap<>();

        visited.add(sourceNode);
        queue.add(sourceNode);

        while ( ! queue.isEmpty()) {
            N currentNode = queue.remove();
            for (N v : graphFactory.getSuccessors(currentNode)) {
                if (! visited.contains(v)) {
                    parentNodes.put(v, currentNode);
                    if (v.equals(targetNode)) {
                        return reconstructPath(parentNodes, sourceNode, targetNode);
                    }
                    visited.add(v);
                    queue.add(v);
                }
            }
        }
        return ImmutableList.of();
    }

}
