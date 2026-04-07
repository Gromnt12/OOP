package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class IncidenceMatrixGraphTest {

    @Test
    void addRemoveEdgesAndVertices() {
        Graph g = new IncidenceMatrixGraph();

        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");

        assertTrue(g.addEdge("A", "B"));
        assertTrue(g.addEdge("A", "C"));
        assertEquals(2, g.edgeCount());

        assertEquals(List.of("B", "C"),
                List.copyOf(g.getNeighbors("A")));

        // удаляем ребро
        assertTrue(g.removeEdge("A", "B"));
        assertFalse(g.removeEdge("A", "B"));
        assertEquals(1, g.edgeCount());
        assertEquals(List.of("C"),
                List.copyOf(g.getNeighbors("A")));

        // удаляем вершину C, граф должен пересобраться
        assertTrue(g.removeVertex("C"));
        assertEquals(2, g.vertexCount());
        assertEquals(Set.of("A", "B"), g.getVertices());
        assertEquals(0, g.edgeCount());
    }

    @Test
    void addEdgeRequiresExistingVertices() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex("A");

        assertThrows(IllegalArgumentException.class,
                () -> g.addEdge("A", "B"));
        assertThrows(IllegalArgumentException.class,
                () -> g.addEdge("B", "A"));
    }

    @Test
    void getNeighborsUnknownVertexThrows() {
        Graph g = new IncidenceMatrixGraph();

        assertThrows(IllegalArgumentException.class,
                () -> g.getNeighbors("X"));
    }

    @Test
    void clearResetsGraph() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");

        g.clear();

        assertEquals(0, g.vertexCount());
        assertEquals(0, g.edgeCount());
        assertTrue(g.getVertices().isEmpty());
    }
}
