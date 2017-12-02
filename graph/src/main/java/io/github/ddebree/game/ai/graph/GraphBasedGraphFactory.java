package io.github.ddebree.game.ai.graph;

import com.google.common.graph.Graph;

import java.util.Set;

public class GraphBasedGraphFactory<N> implements GraphFactory<N> {

    private final Graph<N> graph;

    public GraphBasedGraphFactory(Graph<N> graph) {
        this.graph = graph;
    }

    @Override
    public Set<N> getSuccessors(N node) {
        return graph.successors(node);
    }

}
