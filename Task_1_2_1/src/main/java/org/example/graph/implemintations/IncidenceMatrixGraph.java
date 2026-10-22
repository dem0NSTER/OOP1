package org.example.graph.implemintations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.example.graph.Graph;
import org.example.graph.models.Edge;
import org.example.graph.models.Vertex;

public class IncidenceMatrixGraph implements Graph {

    private final List<Vertex> vertices;
    private final List<Edge> edges;
    private final List<List<Integer>> matrix;

    public IncidenceMatrixGraph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        matrix = new ArrayList<>();
    }

    @Override
    public void addVertex(Vertex vertex) {
        if (vertices.contains(vertex)) {
            throw new IllegalArgumentException(
                    "Vertex already exists: " + vertex
            );
        }

        List<Integer> newRow = new ArrayList<>();

        for (int i = 0; i < edges.size(); i++) {
            newRow.add(0);
        }

        vertices.add(vertex);
        matrix.add(newRow);
    }

    @Override
    public void removeVertex(Vertex vertex) {
        int vertexIndex = vertices.indexOf(vertex);

        if (vertexIndex == -1) {
            throw new IllegalArgumentException(
                    "Vertex does not exist: " + vertex
            );
        }

        for (int edgeIndex = edges.size() - 1; edgeIndex >= 0; edgeIndex--) {
            Edge edge = edges.get(edgeIndex);

            if (edge.from().equals(vertex) || edge.to().equals(vertex)) {
                edges.remove(edgeIndex);

                for (List<Integer> row : matrix) {
                    row.remove(edgeIndex);
                }
            }
        }

        vertices.remove(vertexIndex);
        matrix.remove(vertexIndex);
    }

    @Override
    public void addEdge(Edge edge) {
        if (!vertices.contains(edge.from()) || !vertices.contains(edge.to())) {
            throw new IllegalArgumentException(
                    "Both vertices must exist"
            );
        }

        if (edge.from().equals(edge.to())) {
            throw new IllegalArgumentException(
                    "Loops are not allowed"
            );
        }

        if (edges.contains(edge)) {
            throw new IllegalArgumentException(
                    "Edge already exists: " + edge
            );
        }

        edges.add(edge);

        for (int i = 0; i < vertices.size(); i++) {
            Vertex vertex = vertices.get(i);

            if (vertex.equals(edge.from())) {
                matrix.get(i).add(-1);
            } else if (vertex.equals(edge.to())) {
                matrix.get(i).add(1);
            } else {
                matrix.get(i).add(0);
            }
        }
    }

    @Override
    public void removeEdge(Edge edge) {
        int edgeIndex = edges.indexOf(edge);

        if (edgeIndex == -1) {
            throw new IllegalArgumentException(
                    "Edge does not exist: " + edge
            );
        }

        edges.remove(edgeIndex);

        for (List<Integer> row : matrix) {
            row.remove(edgeIndex);
        }
    }

    @Override
    public Set<Vertex> getNeighbors(Vertex vertex) {
        if (!vertices.contains(vertex)) {
            throw new IllegalArgumentException(
                    "Vertex does not exist: " + vertex
            );
        }

        Set<Vertex> neighbors = new HashSet<>();

        for (Edge edge : edges) {
            if (edge.from().equals(vertex)) {
                neighbors.add(edge.to());
            }
        }

        return neighbors;
    }

    @Override
    public Set<Vertex> getVertices() {
        return Set.copyOf(vertices);
    }

    @Override
    public Set<Edge> getEdges() {
        return Set.copyOf(edges);
    }

    @Override
    public boolean containsVertex(Vertex vertex) {
        return vertices.contains(vertex);
    }

    @Override
    public boolean containsEdge(Edge edge) {
        return edges.contains(edge);
    }

    @Override
    public int vertexCount() {
        return vertices.size();
    }

    @Override
    public int edgeCount() {
        return edges.size();
    }

}
