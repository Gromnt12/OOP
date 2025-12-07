package ru.nsu.bukhanov;


import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AdjacencyMatrixGraph extends AbstractGraph {

    private final Map<String, Integer> indexByVertex = new LinkedHashMap<>();
    private boolean[][] matrix = new boolean[0][0];

    @Override
    public boolean addVertex(String v) {
        if (indexByVertex.containsKey(v)) {
            return false;
        }
        int newIndex = indexByVertex.size();
        indexByVertex.put(v, newIndex);
        ensureCapacity(indexByVertex.size());
        return true;
    }

    private void ensureCapacity(int size) {
        int n = matrix.length;
        if (n >= size) {
            return;
        }
        boolean[][] newMatrix = new boolean[size][size];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, n);
        }
        matrix = newMatrix;
    }

    @Override
    public boolean removeVertex(String v) {
        Integer idxObj = indexByVertex.remove(v);
        if (idxObj == null) {
            return false;
        }
        int idx = idxObj;
        int n = matrix.length;
        boolean[][] newMatrix = new boolean[n - 1][n - 1];

        for (int i = 0, ni = 0; i < n; i++) {
            if (i == idx) continue;
            for (int j = 0, nj = 0; j < n; j++) {
                if (j == idx) continue;
                newMatrix[ni][nj] = matrix[i][j];
                nj++;
            }
            ni++;
        }

        matrix = newMatrix;

        Map<String, Integer> newMap = new LinkedHashMap<>();
        int newIndex = 0;
        for (String vertex : indexByVertex.keySet()) {
            newMap.put(vertex, newIndex++);
        }
        indexByVertex.clear();
        indexByVertex.putAll(newMap);
        return true;
    }

    @Override
    public boolean addEdge(String from, String to) {
        Integer i = indexByVertex.get(from);
        Integer j = indexByVertex.get(to);
        if (i == null || j == null) {
            throw new IllegalArgumentException("Both vertices must exist");
        }
        boolean changed = !matrix[i][j];
        matrix[i][j] = true;
        return changed;
    }

    @Override
    public boolean removeEdge(String from, String to) {
        Integer i = indexByVertex.get(from);
        Integer j = indexByVertex.get(to);
        if (i == null || j == null) {
            return false;
        }
        boolean changed = matrix[i][j];
        matrix[i][j] = false;
        return changed;
    }

    @Override
    public Set<String> getVertices() {
        return new LinkedHashSet<>(indexByVertex.keySet());
    }

    @Override
    public Set<String> getNeighbors(String v) {
        Integer idx = indexByVertex.get(v);
        if (idx == null) {
            throw new IllegalArgumentException("Unknown vertex: " + v);
        }
        Set<String> result = new LinkedHashSet<>();
        for (Map.Entry<String, Integer> entry : indexByVertex.entrySet()) {
            int j = entry.getValue();
            if (matrix[idx][j]) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    @Override
    public int vertexCount() {
        return indexByVertex.size();
    }

    @Override
    public int edgeCount() {
        int count = 0;
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public void clear() {
        indexByVertex.clear();
        matrix = new boolean[0][0];
    }
}
