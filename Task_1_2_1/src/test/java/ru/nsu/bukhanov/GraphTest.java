package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.AdjacencyListGraph;
import ru.nsu.bukhanov.AdjacencyMatrixGraph;
import ru.nsu.bukhanov.Graph;
import ru.nsu.bukhanov.IncidenceMatrixGraph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void topologicalSortSimple() {
        Graph g = new AdjacencyListGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("B", "C");

        List<String> sorted = g.topologicalSort();
        assertEquals(List.of("A", "B", "C"), sorted);
    }

    @Test
    void equalsBetweenImplementations() {
        Graph g1 = new AdjacencyListGraph();
        Graph g2 = new AdjacencyMatrixGraph();

        for (String v : List.of("A", "B", "C")) {
            g1.addVertex(v);
            g2.addVertex(v);
        }
        g1.addEdge("A", "B");
        g1.addEdge("A", "C");
        g2.addEdge("A", "B");
        g2.addEdge("A", "C");

        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }

    @Test
    void incidenceMatrixWorks() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("A", "C");

        assertEquals(List.of("B", "C"),
                List.copyOf(g.getNeighbors("A")));
    }

    @Test
    void cycleDetection() {
        Graph g = new AdjacencyListGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");
        g.addEdge("B", "A");

        assertThrows(IllegalStateException.class, g::topologicalSort);
    }
}
