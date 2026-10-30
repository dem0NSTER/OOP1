package org.example.graph.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.Supplier;
import org.example.graph.Graph;
import org.example.graph.models.Edge;
import org.example.graph.models.Vertex;

/**
 * Читает граф из текстового файла.
 */
public class GraphReader {
    // формат файл
    //    3        : количество вершин
    //    1 2 3    : id вершин
    //    2        : количество рёбер
    //    1 2      : ребро 1 -> 2
    //    2 3      : ребро 2 -> 3

    private GraphReader() {
    }

    /**
     * Читает количество вершин, их идентификаторы, количество рёбер и пары их концов.
     *
     * @param filename путь к файлу
     * @param graphFactory способ создания пустого графа
     * @return прочитанный граф
     * @throws FileNotFoundException если файл не найден
     */
    public static Graph read(String filename, Supplier<Graph> graphFactory)
            throws FileNotFoundException {

        Graph graph = graphFactory.get();

        try (Scanner scanner = new Scanner(new File(filename))) {
            int vertexCount = scanner.nextInt();
            if (vertexCount < 0) {
                throw new IllegalArgumentException("Vertex count must not be negative");
            }

            for (int i = 0; i < vertexCount; i++) {
                int id = scanner.nextInt();
                graph.addVertex(new Vertex(id));
            }

            int edgeCount = scanner.nextInt();
            if (edgeCount < 0) {
                throw new IllegalArgumentException("Edge count must not be negative");
            }

            for (int i = 0; i < edgeCount; i++) {
                int from = scanner.nextInt();
                int to = scanner.nextInt();

                graph.addEdge(
                        new Edge(
                                new Vertex(from),
                                new Vertex(to)
                        )
                );
            }
        }

        return graph;
    }
}
