package io.github.ddebree.game.ai.graph.search;

import com.google.common.collect.ImmutableList;
import io.github.ddebree.game.ai.graph.GraphFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class IterativeDeepeningSearch<N> extends AbstractGraphSearchAlgorithm<N> implements GraphSearchAlgorithm<N> {

    @Override
    public List<N> search(GraphFactory<N> graphFactory, N rootVertex, N targetVertex) {
        Map<N, N> parentNodes = new HashMap<>();
        int depth = 0;
        boolean isTargetFound = false;
        while ( ! isTargetFound) {
            isTargetFound = dfs(graphFactory, rootVertex, targetVertex, depth, parentNodes);
            if (isTargetFound) {
                return reconstructPath(parentNodes, rootVertex, targetVertex);
            }
            depth++;
        }
        return ImmutableList.of();
    }

    private boolean dfs(GraphFactory<N> graphFactory, N sourceVertex, N targetVertex, int depthLevel, Map<N, N> parentNodes) {
        Stack<N> stack = new Stack<>();
        stack.push(sourceVertex);

        Map<N, Integer> depths = new HashMap<>();
        depths.put(sourceVertex, 0);

        while (!stack.isEmpty()) {
            N currentNode = stack.pop();

            if (currentNode.equals(targetVertex)) {
                return true;
            }

            Integer nodeDepth = depths.get(currentNode);
            if (nodeDepth == null) {
                nodeDepth = 0;
            }

            if (nodeDepth >= depthLevel) {
                continue;
            }

            for (N node : graphFactory.getSuccessors(currentNode)) {
                parentNodes.put(node, currentNode);
                depths.put(node, nodeDepth + 1);
                stack.push(node);
            }
        }
        return false;
    }
}
