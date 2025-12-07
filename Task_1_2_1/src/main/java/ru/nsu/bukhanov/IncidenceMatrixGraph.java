package ru.nsu.bukhanov;


import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class IncidenceMatrixGraph extends AbstractGraph {

    private final Map<String, Integer> indexByVertex = new LinkedHashMap<>();
    private final List<String> vertices = new ArrayList<>();
    private int[][] matrix = new int[0][0]; // rows: vertices, cols: edges
    private int edgeCount = 0;

    @Override
    public boolean addVertex(String v) {
        if (indexByVertex.containsKey(v)) {
            return false;
        }
        int newIndex = vertices.size();
        vertices.add(v);
        indexByVertex.put(v, newIndex);

        int rows = vertices.size();
        int cols = edgeCount;
        int[][] newMatrix = new int[rows][cols];
        for (int i = 0; i < rows - 1; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, cols);
        }
        matrix = newMatrix;
        return true;
    }

    @Override
    public boolean removeVertex(String v) {
        if (!indexByVertex.containsKey(v)) {
            return false;
        }

        Map<String, Set<String>> adj = buildAdjacency();
        adj.remove(v);
        for (Set<String> neighbors : adj.values()) {
            neighbors.remove(v);
        }

        vertices.clear();
        indexByVertex.clear();
        for (String vertex : adj.keySet()) {
            indexByVertex.put(vertex, vertices.size());
            vertices.add(vertex);
        }

        rebuildFromAdjacency(adj);
        return true;
    }

    private Map<String, Set<String>> buildAdjacency() {
        Map<String, Set<String>> adj = new LinkedHashMap<>();
        for (String vertex : vertices) {
            adj.put(vertex, new LinkedHashSet<>());
        }
        int rows = vertices.size();
        for (int e = 0; e < edgeCount; e++) {
            int fromIdx = -1;
            int toIdx = -1;
            for (int r = 0; r < rows; r++) {
                int val = matrix[r][e];
                if (val == -1) {
                    fromIdx = r;
                } else if (val == 1) {
                    toIdx = r;
                }
            }
            if (fromIdx >= 0 && toIdx >= 0) {
                String from = vertices.get(fromIdx);
                String to = vertices.get(toIdx);
                adj.get(from).add(to);
            }
        }
        return adj;
    }

    private void rebuildFromAdjacency(Map<String, Set<String>> adj) {
        int rows = vertices.size();
        int cols = 0;
        for (Set<String> ns : adj.values()) {
            cols += ns.size();
        }

        matrix = new int[rows][cols];
        edgeCount = 0;

        for (Map.Entry<String, Set<String>> entry : adj.entrySet()) {
            String from = entry.getKey();
            int fromIdx = indexByVertex.get(from);
            for (String to : entry.getValue()) {
                Integer toIdxObj = indexByVertex.get(to);
                if (toIdxObj == null) {
                    continue;
                }
                int toIdx = toIdxObj;
                matrix[fromIdx][edgeCount] = -1;
                matrix[toIdx][edgeCount] = 1;
                edgeCount++;
            }
        }
    }

    @Override
    public boolean addEdge(String from, String to) {
        Integer fromIdx = indexByVertex.get(from);
        Integer toIdx = indexByVertex.get(to);
        if (fromIdx == null || toIdx == null) {
            throw new IllegalArgumentException("Both vertices must exist");
        }

        if (hasEdge(from, to)) {
            return false;
        }

        int rows = vertices.size();
        int colsOld = edgeCount;
        int colsNew = colsOld + 1;
        int[][] newMatrix = new int[rows][colsNew];
        for (int r = 0; r < rows; r++) {
            System.arraycopy(matrix[r], 0, newMatrix[r], 0, colsOld);
        }

        newMatrix[fromIdx][colsOld] = -1;
        newMatrix[toIdx][colsOld] = 1;

        matrix = newMatrix;
        edgeCount = colsNew;
        return true;
    }

    private boolean hasEdge(String from, String to) {
        Integer fromIdx = indexByVertex.get(from);
        Integer toIdx = indexByVertex.get(to);
        if (fromIdx == null || toIdx == null) {
            return false;
        }
        for (int e = 0; e < edgeCount; e++) {
            if (matrix[fromIdx][e] == -1 && matrix[toIdx][e] == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeEdge(String from, String to) {
        Integer fromIdx = indexByVertex.get(from);
        Integer toIdx = indexByVertex.get(to);
        if (fromIdx == null || toIdx == null) {
            return false;
        }

        int edgeIndex = -1;
        for (int e = 0; e < edgeCount; e++) {
            if (matrix[fromIdx][e] == -1 && matrix[toIdx][e] == 1) {
                edgeIndex = e;
                break;
            }
        }

        if (edgeIndex == -1) {
            return false;
        }

        int rows = vertices.size();
        int colsNew = edgeCount - 1;
        int[][] newMatrix = new int[rows][colsNew];

        for (int r = 0; r < rows; r++) {
            for (int e = 0, ne = 0; e < edgeCount; e++) {
                if (e == edgeIndex) continue;
                newMatrix[r][ne] = matrix[r][e];
                ne++;
            }
        }

        matrix = newMatrix;
        edgeCount = colsNew;
        return true;
    }

    @Override
    public Set<String> getVertices() {
        return new LinkedHashSet<>(vertices);
    }

    @Override
    public Set<String> getNeighbors(String v) {
        Integer idx = indexByVertex.get(v);
        if (idx == null) {
            throw new IllegalArgumentException("Unknown vertex: " + v);
        }

        Set<String> result = new LinkedHashSet<>();
        int rows = vertices.size();

        for (int e = 0; e < edgeCount; e++) {
            if (matrix[idx][e] == -1) {
                int toIdx = -1;
                for (int r = 0; r < rows; r++) {
                    if (matrix[r][e] == 1) {
                        toIdx = r;
                        break;
                    }
                }
                if (toIdx >= 0) {
                    result.add(vertices.get(toIdx));
                }
            }
        }

        return result;
    }

    @Override
    public int vertexCount() {
        return vertices.size();
    }

    @Override
    public int edgeCount() {
        return edgeCount;
    }

    @Override
    public void clear() {
        vertices.clear();
        indexByVertex.clear();
        matrix = new int[0][0];
        edgeCount = 0;
    }
}
