package io.github.ddebree.game.ai.graph.search;

import io.github.ddebree.game.ai.graph.GraphFactory;

import java.util.List;

public interface GraphSearchAlgorithm<N> {

    List<N> search(GraphFactory<N> graphFactory, N rootVertex, N targetVertex);

}
