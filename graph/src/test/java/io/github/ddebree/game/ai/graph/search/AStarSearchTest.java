package io.github.ddebree.game.ai.graph.search;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import io.github.ddebree.game.ai.graph.ValueGraphFactory;
import org.junit.Test;

import java.util.List;
import java.util.Set;

public class AStarSearchTest {

    @Test
    public void testSearch() throws Exception {
        MutableValueGraph<String, Double> graph = ValueGraphBuilder.directed()
                .build();

        graph.putEdgeValue("node1", "node2", 4.0);
        graph.putEdgeValue("node1", "node3", 2.0);

        graph.putEdgeValue("node2", "node3", 5.0);
        graph.putEdgeValue("node2", "node4", 10.0);

        graph.putEdgeValue("node3", "node5", 3.0);

        graph.putEdgeValue("node4", "node6", 11.0);

        graph.putEdgeValue("node5", "node4", 4.0);

        // Manhattan heuristic/distance !!!
        AStarSearch<String> AStarSearch = new AStarSearch<>((node1, node2) -> Math.abs(node1.hashCode() - node2.hashCode()));

        List<String> path = AStarSearch.search(new ValueGraphFactory<String>() {
            @Override
            public Set<String> getSuccessors(String node) {
                return graph.successors(node);
            }

            @Override
            public double edgeCost(String from, String to) {
                return graph.edgeValue(from, to);
            }
        }, "node1", "node6");
        System.out.println("Path " + path);
    }

}