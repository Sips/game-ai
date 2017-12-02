package io.github.ddebree.game.ai.graph.search;

import com.google.common.collect.ImmutableList;
import io.github.ddebree.game.ai.graph.GraphFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DepthFirstSearch<N> implements GraphSearchAlgorithm<N> {

    @Override
    public List<N> search(GraphFactory<N> graphFactory, N startNode, N endNode) {
        Set<N> visited = new HashSet<>();
        List<N> nodes = searchDelegate(graphFactory, visited, startNode, endNode);
        if (nodes.isEmpty()) {
            return nodes;
        } else {
            return ImmutableList.<N>builder()
                    .add(startNode)
                    .addAll(nodes)
                    .build();
        }
    }

    private List<N> searchDelegate(GraphFactory<N> graphFactory, Set<N> visited, N currentNode, N endNode) {
        for (N childNode : graphFactory.getSuccessors(currentNode)) {
            if (childNode.equals(endNode)) {
                return ImmutableList.of(endNode);
            }
            if ( ! visited.contains(childNode)) {
                visited.add(childNode);
                List<N> nodes = searchDelegate(graphFactory, visited, childNode, endNode);
                if (nodes.isEmpty()) {
                    return nodes;
                } else {
                    return ImmutableList.<N>builder()
                            .add(childNode)
                            .addAll(nodes)
                            .build();
                }
            }
        }
        return ImmutableList.of();
    }

}
