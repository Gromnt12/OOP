package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphAlgorithmsTest {

    @Test
    void topologicalSortHandlesMultipleSourcesAndIsolatedVertices() {
        Graph g = new AdjacencyListGraph();
        // A -> C, B -> C
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "C");
        g.addEdge("B", "C");

        // изолированная вершина D
        g.addVertex("D");

        List<String> order = g.topologicalSort();

        int idxA = order.indexOf("A");
        int idxB = order.indexOf("B");
        int idxC = order.indexOf("C");
        int idxD = order.indexOf("D");

        assertTrue(idxA != -1 && idxB != -1 && idxC != -1 && idxD != -1);
        assertTrue(idxA < idxC);
        assertTrue(idxB < idxC);
        // D может стоять где угодно — ничего не проверяем по относительному порядку
    }

    @Test
    void directCallDetectsCycle() {
        Graph g = new AdjacencyMatrixGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");
        g.addEdge("B", "A");

        assertThrows(IllegalStateException.class,
                () -> GraphAlgorithms.topologicalSort(g));
    }
}
