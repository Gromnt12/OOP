package ru.nsu.bukhanov;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class GraphIO {

    private GraphIO() {
    }

    public static void loadInto(Graph graph, Path path) throws IOException {
        graph.clear();

        try (BufferedReader reader =
                     Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split(":");
                if (parts.length == 0) {
                    continue;
                }

                String vertex = parts[0].trim();
                if (vertex.isEmpty()) {
                    continue;
                }

                graph.addVertex(vertex);

                if (parts.length == 1) {
                    continue;
                }

                String rest = parts[1].trim();
                if (rest.isEmpty()) {
                    continue;
                }

                String[] neighbors = rest.split("\\s+");
                for (String neighbor : neighbors) {
                    if (neighbor.isEmpty()) {
                        continue;
                    }
                    graph.addVertex(neighbor);
                    graph.addEdge(vertex, neighbor);
                }
            }
        }
    }

    public static void save(Graph graph, Path path) throws IOException {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            for (String v : graph.getVertices()) {
                StringBuilder sb = new StringBuilder();
                sb.append(v).append(":");
                for (String n : graph.getNeighbors(v)) {
                    sb.append(" ").append(n);
                }
                writer.write(sb.toString());
                writer.newLine();
            }
        }
    }
}
