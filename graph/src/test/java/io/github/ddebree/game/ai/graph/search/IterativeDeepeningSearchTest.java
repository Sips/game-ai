package io.github.ddebree.game.ai.graph.search;

import io.github.ddebree.game.ai.graph.GraphBasedGraphFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class IterativeDeepeningSearchTest {

    @Test
    public void testSearch() throws Exception {
        IterativeDeepeningSearch<String> iterativeDeepeningSearch = new IterativeDeepeningSearch<>();
        List<String> path = iterativeDeepeningSearch.search(new GraphBasedGraphFactory<>(SampleData.GRAPH),
                SampleData.START_NODE,
                SampleData.END_NODE);
        assertEquals(SampleData.EXPECTED_PATH, path);
    }

}