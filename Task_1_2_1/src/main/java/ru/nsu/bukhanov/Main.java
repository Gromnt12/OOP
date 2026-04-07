package ru.nsu.bukhanov;


import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: Main <path-to-graph-file>");
            return;
        }

        Path path = Path.of(args[0]);

        Graph graph = new AdjacencyListGraph();
        graph.loadFromFile(path);

        System.out.println("Graph:");
        System.out.println(graph);

        List<String> sorted = graph.topologicalSort();
        System.out.println("Topological order:");
        System.out.println(sorted);
    }
}
