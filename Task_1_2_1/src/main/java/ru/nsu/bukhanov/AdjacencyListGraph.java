package ru.nsu.bukhanov;


import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AdjacencyListGraph extends AbstractGraph {

    private final Map<String, Set<String>> adjacency = new LinkedHashMap<>();

    @Override
    public boolean addVertex(String v) {
        if (adjacency.containsKey(v)) {
            return false;
        }
        adjacency.put(v, new LinkedHashSet<>());
        return true;
    }

    @Override
    public boolean removeVertex(String v) {
        if (!adjacency.containsKey(v)) {
            return false;
        }
        adjacency.remove(v);
        for (Set<String> neighbors : adjacency.values()) {
            neighbors.remove(v);
        }
        return true;
    }

    @Override
    public boolean addEdge(String from, String to) {
        if (!adjacency.containsKey(from) || !adjacency.containsKey(to)) {
            throw new IllegalArgumentException("Both vertices must exist");
        }
        return adjacency.get(from).add(to);
    }

    @Override
    public boolean removeEdge(String from, String to) {
        Set<String> neighbors = adjacency.get(from);
        if (neighbors == null) {
            return false;
        }
        return neighbors.remove(to);
    }

    @Override
    public Set<String> getVertices() {
        return new LinkedHashSet<>(adjacency.keySet());
    }

    @Override
    public Set<String> getNeighbors(String v) {
        Set<String> neighbors = adjacency.get(v);
        if (neighbors == null) {
            throw new IllegalArgumentException("Unknown vertex: " + v);
        }
        return new LinkedHashSet<>(neighbors);
    }

    @Override
    public int vertexCount() {
        return adjacency.size();
    }

    @Override
    public int edgeCount() {
        int count = 0;
        for (Set<String> neighbors : adjacency.values()) {
            count += neighbors.size();
        }
        return count;
    }

    @Override
    public void clear() {
        adjacency.clear();
    }
}
