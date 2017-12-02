package io.github.ddebree.game.ai.graph.search;

import io.github.ddebree.game.ai.graph.GraphBasedGraphFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DepthFirstSearchTest {
    @Test
    public void testSearch() throws Exception {
        DepthFirstSearch<String> depthFirstSearch = new DepthFirstSearch<>();
        List<String> path = depthFirstSearch.search(new GraphBasedGraphFactory<>(SampleData.GRAPH),
                SampleData.START_NODE,
                SampleData.END_NODE);
        assertEquals(SampleData.EXPECTED_PATH, path);
    }

}