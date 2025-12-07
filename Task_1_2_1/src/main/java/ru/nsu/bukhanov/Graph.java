package ru.nsu.bukhanov;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public interface Graph {

    boolean addVertex(String v);

    boolean removeVertex(String v);

    boolean addEdge(String from, String to);

    boolean removeEdge(String from, String to);

    Set<String> getVertices();

    Set<String> getNeighbors(String v);

    int vertexCount();

    int edgeCount();

    void clear();

    default List<String> topologicalSort() {
        return GraphAlgorithms.topologicalSort(this);
    }

    default void loadFromFile(Path path) throws IOException {
        GraphIO.loadInto(this, path);
    }

    default void saveToFile(Path path) throws IOException {
        GraphIO.save(this, path);
    }
}
