package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractGraphTest {

    @Test
    void toStringPrintsVerticesAndNeighbors() {
        Graph g = new AdjacencyListGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");

        String nl = System.lineSeparator();
        String expected =
                "A -> [B]" + nl +
                        "B -> []" + nl;

        assertEquals(expected, g.toString());
    }

    @Test
    void equalsSameStructure() {
        Graph g1 = new AdjacencyListGraph();
        Graph g2 = new AdjacencyListGraph();

        g1.addVertex("A");
        g1.addVertex("B");
        g1.addEdge("A", "B");

        g2.addVertex("A");
        g2.addVertex("B");
        g2.addEdge("A", "B");

        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }

    @Test
    void notEqualsDifferentEdges() {
        Graph g1 = new AdjacencyListGraph();
        Graph g2 = new AdjacencyListGraph();

        g1.addVertex("A");
        g1.addVertex("B");
        g1.addEdge("A", "B");

        g2.addVertex("A");
        g2.addVertex("B");
        // без ребра

        assertNotEquals(g1, g2);
    }

    @Test
    void equalsHandlesNullAndOtherType() {
        Graph g = new AdjacencyListGraph();
        g.addVertex("A");

        assertEquals(g, g);
        assertNotEquals(g, null);
        assertNotEquals(g, "not a graph");
    }
}
