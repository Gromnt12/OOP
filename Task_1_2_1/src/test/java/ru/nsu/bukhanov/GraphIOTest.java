package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphIOTest {

    @Test
    void loadFromFileParsesSimpleGraph() throws IOException {
        Path temp = Files.createTempFile("graph-io-test", ".txt");
        String content = String.join(System.lineSeparator(),
                "# comment line",
                "A: B C",
                "B: C",
                "C:",
                ""
        );
        Files.writeString(temp, content);

        Graph g = new AdjacencyListGraph();
        g.loadFromFile(temp);

        assertEquals(3, g.vertexCount());
        assertEquals(3, g.edgeCount()); // A->B, A->C, B->C
        assertEquals(List.of("B", "C"),
                List.copyOf(g.getNeighbors("A")));
        assertEquals(List.of("C"),
                List.copyOf(g.getNeighbors("B")));
        assertTrue(g.getNeighbors("C").isEmpty());
    }

    @Test
    void loadClearsExistingGraph() throws IOException {
        Path temp = Files.createTempFile("graph-io-test2", ".txt");
        String content = String.join(System.lineSeparator(),
                "X: Y",
                "Y:"
        );
        Files.writeString(temp, content);

        Graph g = new AdjacencyListGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");

        g.loadFromFile(temp);

        assertEquals(2, g.vertexCount());
        assertEquals(List.of("Y"),
                List.copyOf(g.getNeighbors("X")));
    }

    @Test
    void saveAndLoadRoundTrip() throws IOException {
        Graph original = new AdjacencyMatrixGraph();
        original.addVertex("A");
        original.addVertex("B");
        original.addVertex("C");
        original.addEdge("A", "B");
        original.addEdge("B", "C");

        Path temp = Files.createTempFile("graph-io-roundtrip", ".txt");
        original.saveToFile(temp);

        Graph restored = new AdjacencyListGraph();
        restored.loadFromFile(temp);

        assertEquals(original, restored);
    }
}
