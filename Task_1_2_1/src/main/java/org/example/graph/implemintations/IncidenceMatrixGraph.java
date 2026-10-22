package org.example.graph.implemintations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.example.graph.Graph;
import org.example.graph.models.Edge;
import org.example.graph.models.Vertex;

/**
 * Хранит граф в виде матрицы инцидентности.
 */
public class IncidenceMatrixGraph implements Graph {

    private final List<Vertex> vertices;
    private final List<Edge> edges;
    private final List<List<Integer>> matrix;

    /**
     * Создаёт пустой граф.
     */
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
        int vertexIndex = getVertexIndex(vertex);

        for (int edgeIndex = edges.size() - 1; edgeIndex >= 0; edgeIndex--) {
            Edge edge = edges.get(edgeIndex);

            if (edge.from().equals(vertex) || edge.to().equals(vertex)) {
                removeEdgeAtIndex(edgeIndex);
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
        int edgeIndex = getEdgeIndex(edge);
        removeEdgeAtIndex(edgeIndex);
    }

    @Override
    public Set<Vertex> getNeighbors(Vertex vertex) {
        getVertexIndex(vertex);

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

    private int getVertexIndex(Vertex vertex) {
        int index = vertices.indexOf(vertex);
        if (index == -1) {
            throw new IllegalArgumentException("Vertex does not exist: " + vertex);
        }
        return index;
    }

    private int getEdgeIndex(Edge edge) {
        int index = edges.indexOf(edge);
        if (index == -1) {
            throw new IllegalArgumentException("Edge does not exist: " + edge);
        }
        return index;
    }

    private void removeEdgeAtIndex(int edgeIndex) {
        edges.remove(edgeIndex);
        for (List<Integer> row : matrix) {
            row.remove(edgeIndex);
        }
    }

    /**
     * Compares graphs by their vertices and directed edges, regardless of implementation.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Graph graph)) {
            return false;
        }
        return getVertices().equals(graph.getVertices())
                && getEdges().equals(graph.getEdges());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVertices(), getEdges());
    }

    @Override
    public String toString() {
        return "IncidenceMatrixGraph{vertices=" + getVertices() + ", edges=" + getEdges() + '}';
    }

}
