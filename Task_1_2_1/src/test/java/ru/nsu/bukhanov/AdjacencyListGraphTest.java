package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListGraphTest {

    @Test
    void addAndRemoveVertexAndEdge() {
        Graph g = new AdjacencyListGraph();

        assertTrue(g.addVertex("A"));
        assertTrue(g.addVertex("B"));
        assertFalse(g.addVertex("A")); // дубликат

        assertEquals(2, g.vertexCount());
        assertEquals(0, g.edgeCount());

        assertTrue(g.addEdge("A", "B"));
        assertFalse(g.addEdge("A", "B")); // то же ребро

        assertEquals(1, g.edgeCount());
        assertEquals(Set.of("A", "B"), g.getVertices());
        assertEquals(List.of("B"), List.copyOf(g.getNeighbors("A")));

        assertTrue(g.removeEdge("A", "B"));
        assertFalse(g.removeEdge("A", "B"));
        assertEquals(0, g.edgeCount());

        assertTrue(g.removeVertex("A"));
        assertFalse(g.removeVertex("A"));
        assertEquals(1, g.vertexCount());
        assertEquals(Set.of("B"), g.getVertices());

        g.clear();
        assertEquals(0, g.vertexCount());
        assertEquals(0, g.edgeCount());
        assertTrue(g.getVertices().isEmpty());
    }

    @Test
    void addEdgeWithUnknownVertexThrows() {
        Graph g = new AdjacencyListGraph();
        g.addVertex("A");

        assertThrows(IllegalArgumentException.class,
                () -> g.addEdge("A", "B"));
        assertThrows(IllegalArgumentException.class,
                () -> g.addEdge("B", "A"));
    }

    @Test
    void getNeighborsUnknownVertexThrows() {
        Graph g = new AdjacencyListGraph();
        g.addVertex("A");

        assertThrows(IllegalArgumentException.class,
                () -> g.getNeighbors("B"));
    }
}
