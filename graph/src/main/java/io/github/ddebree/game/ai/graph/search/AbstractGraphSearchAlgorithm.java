package io.github.ddebree.game.ai.graph.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class AbstractGraphSearchAlgorithm<N> implements GraphSearchAlgorithm<N> {

    protected List<N> reconstructPath(Map<N, N> parentNode, N startNode, N endNode) {
        List<N> pathList = new ArrayList<>();

        N currentNode = endNode;
        pathList.add(currentNode);
        while ( ! currentNode.equals(startNode)) {
            currentNode = parentNode.get(currentNode);
            pathList.add(currentNode);
        }

        Collections.reverse(pathList);

        return pathList;
    }

}
