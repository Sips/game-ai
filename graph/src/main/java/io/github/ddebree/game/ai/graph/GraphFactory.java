package io.github.ddebree.game.ai.graph;

import java.util.Set;

public interface GraphFactory<N> {

    Set<N> getSuccessors(N node);

}
