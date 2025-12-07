package ru.nsu.bukhanov;


import java.util.*;

public final class GraphAlgorithms {

    private GraphAlgorithms() {
    }

    public static List<String> topologicalSort(Graph graph) {
        Map<String, Integer> indegree = new HashMap<>();

        for (String v : graph.getVertices()) {
            indegree.put(v, 0);
        }

        for (String v : graph.getVertices()) {
            for (String n : graph.getNeighbors(v)) {
                indegree.merge(n, 1, Integer::sum);
            }
        }

        Deque<String> queue = new ArrayDeque<>();
        for (Map.Entry<String, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.addLast(entry.getKey());
            }
        }

        List<String> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            String v = queue.removeFirst();
            result.add(v);
            for (String n : graph.getNeighbors(v)) {
                int deg = indegree.merge(n, -1, Integer::sum);
                if (deg == 0) {
                    queue.addLast(n);
                }
            }
        }

        if (result.size() != indegree.size()) {
            throw new IllegalStateException("Graph contains a cycle");
        }

        return result;
    }
}
