package io.github.ddebree.game.ai.graph;

public interface ValueGraphFactory<N> extends GraphFactory<N> {

    default double edgeCost(N from, N to) {
        return 1.0;
    }

}
