package io.github.ddebree.game.ai.graph.search;

import com.google.common.collect.ImmutableList;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;

import java.util.List;

public class SampleData {

    public static final String START_NODE = "node1";
    public static final String END_NODE = "node5";
    public static final ImmutableGraph<String> GRAPH;
    public static final List<String> EXPECTED_PATH = ImmutableList.of(START_NODE, "node4", END_NODE);

    static {
        MutableGraph<String> graph = GraphBuilder.directed()
                .build();

        graph.putEdge(START_NODE, "node2");
        graph.putEdge(START_NODE, "node4");
        graph.putEdge("node2", "node3");
        graph.putEdge("node4", END_NODE);

        GRAPH = ImmutableGraph.copyOf(graph);
    }

}
