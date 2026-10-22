package org.example.graph.algorithm;

import java.util.*;

import org.example.graph.Graph;
import org.example.graph.models.Vertex;

public class KahnTopologicalSorter implements TopologicalSorter {

    @Override
    public List<Vertex> sort(Graph graph) {
        Map<Vertex, Integer> inDegree = new HashMap<>();

        for (Vertex vertex : graph.getVertices()) {
            inDegree.put(vertex, 0);
        }

        for (Vertex vertex : graph.getVertices()) {
            for (Vertex neighbor : graph.getNeighbors(vertex)) {
                inDegree.put(
                        neighbor,
                        inDegree.get(neighbor) + 1
                );
            }
        }

        Queue<Vertex> queue = new ArrayDeque<>();

        for (Vertex vertex : graph.getVertices()) {
            if (inDegree.get(vertex) == 0) {
                queue.add(vertex);
            }
        }

        List<Vertex> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            Vertex vertex = queue.remove();

            result.add(vertex);

            for (Vertex neighbor : graph.getNeighbors(vertex)) {
                int newDegree = inDegree.get(neighbor) - 1;

                inDegree.put(neighbor, newDegree);

                if (newDegree == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (result.size() != graph.vertexCount()) {
            throw new IllegalStateException(
                    "Graph contains a cycle"
            );
        }

        return result;
    }
}
