package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixGraphTest {

    @Test
    void addRemoveVerticesAndEdgesMatrixResizes() {
        Graph g = new AdjacencyMatrixGraph();

        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");

        assertEquals(3, g.vertexCount());

        assertTrue(g.addEdge("A", "B"));
        assertTrue(g.addEdge("B", "C"));
        assertEquals(2, g.edgeCount());

        assertEquals(List.of("B"), List.copyOf(g.getNeighbors("A")));
        assertEquals(List.of("C"), List.copyOf(g.getNeighbors("B")));

        // удаляем серединную вершину B
        assertTrue(g.removeVertex("B"));
        assertEquals(2, g.vertexCount());
        assertEquals(Set.of("A", "C"), g.getVertices());
        assertEquals(0, g.edgeCount());
    }

    @Test
    void addEdgeRequiresExistingVertices() {
        Graph g = new AdjacencyMatrixGraph();
        g.addVertex("A");

        assertThrows(IllegalArgumentException.class,
                () -> g.addEdge("A", "B"));
        assertThrows(IllegalArgumentException.class,
                () -> g.addEdge("B", "A"));
    }

    @Test
    void getNeighborsUnknownVertexThrows() {
        Graph g = new AdjacencyMatrixGraph();

        assertThrows(IllegalArgumentException.class,
                () -> g.getNeighbors("X"));
    }

    @Test
    void clearResetsGraph() {
        Graph g = new AdjacencyMatrixGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");

        g.clear();

        assertEquals(0, g.vertexCount());
        assertEquals(0, g.edgeCount());
        assertTrue(g.getVertices().isEmpty());
    }
}
