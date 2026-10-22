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
 * Хранит граф в виде матрицы смежности.
 */
public class AdjacencyMatrixGraph implements Graph {

    private final List<Vertex> vertices;
    private final List<List<Boolean>> edgesMatrix;

    public AdjacencyMatrixGraph() {
        vertices = new ArrayList<>();
        edgesMatrix = new ArrayList<>();
    }

    @Override
    public void addVertex(Vertex vertex) {
        if (vertices.contains(vertex)) {
            throw new IllegalArgumentException(
                    "Vertex already exists: " + vertex
            );
        }

        // Добавляем новый столбец во все существующие строки
        for (List<Boolean> row : edgesMatrix) {
            row.add(false);
        }

        // Добавляем новую строку
        List<Boolean> newLine = new ArrayList<>();

        int newSize = vertices.size() + 1;

        for (int i = 0; i < newSize; i++) {
            newLine.add(false);
        }

        edgesMatrix.add(newLine);
        vertices.add(vertex);
    }

    @Override
    public void removeVertex(Vertex vertex) {
        int index = getVertexIndex(vertex);

        // Удаляем строку
        edgesMatrix.remove(index);

        // Удаляем соответствующий столбец
        for (List<Boolean> row : edgesMatrix) {
            row.remove(index);
        }

        vertices.remove(index);
    }

    @Override
    public void addEdge(Edge edge) {
        int fromIndex = vertices.indexOf(edge.from());
        int toIndex = vertices.indexOf(edge.to());

        checkEdgeVerticesExist(fromIndex, toIndex);

        if (fromIndex == toIndex) {
            throw new IllegalArgumentException(
                    "Loops are not allowed"
            );
        }

        if (edgesMatrix.get(fromIndex).get(toIndex)) {
            throw new IllegalArgumentException(
                    "Edge already exists: " + edge
            );
        }

        edgesMatrix.get(fromIndex).set(toIndex, true);
    }

    @Override
    public void removeEdge(Edge edge) {
        int fromIndex = vertices.indexOf(edge.from());
        int toIndex = vertices.indexOf(edge.to());

        checkEdgeVerticesExist(fromIndex, toIndex);

        checkEdgeExists(edge, fromIndex, toIndex);

        edgesMatrix.get(fromIndex).set(toIndex, false);
    }

    @Override
    public Set<Vertex> getNeighbors(Vertex vertex) {
        int index = getVertexIndex(vertex);

        Set<Vertex> neighbors = new HashSet<>();

        for (int i = 0; i < vertices.size(); i++) {
            if (edgesMatrix.get(index).get(i)) {
                neighbors.add(vertices.get(i));
            }
        }

        return new HashSet<>(neighbors);
    }

    @Override
    public Set<Vertex> getVertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public Set<Edge> getEdges() {
        Set<Edge> edges = new HashSet<>();

        for (int from = 0; from < vertices.size(); from++) {
            for (int to = 0; to < vertices.size(); to++) {

                if (edgesMatrix.get(from).get(to)) {
                    edges.add(
                            new Edge(
                                    vertices.get(from),
                                    vertices.get(to)
                            )
                    );
                }
            }
        }

        return edges;
    }

    @Override
    public boolean containsVertex(Vertex vertex) {
        return vertices.contains(vertex);
    }

    @Override
    public boolean containsEdge(Edge edge) {
        int fromIndex = vertices.indexOf(edge.from());
        int toIndex = vertices.indexOf(edge.to());

        if (fromIndex == -1 || toIndex == -1) {
            return false;
        }

        return edgesMatrix.get(fromIndex).get(toIndex);
    }

    @Override
    public int vertexCount() {
        return vertices.size();
    }

    @Override
    public int edgeCount() {
        int count = 0;

        for (List<Boolean> row : edgesMatrix) {
            for (boolean hasEdge : row) {
                if (hasEdge) {
                    count++;
                }
            }
        }

        return count;
    }

    private int getVertexIndex(Vertex vertex) {
        int index = vertices.indexOf(vertex);
        if (index == -1) {
            throw new IllegalArgumentException("Vertex does not exist: " + vertex);
        }
        return index;
    }

    private void checkEdgeVerticesExist(int fromIndex, int toIndex) {
        if (fromIndex == -1 || toIndex == -1) {
            throw new IllegalArgumentException("Both vertices must exist");
        }
    }

    private void checkEdgeExists(Edge edge, int fromIndex, int toIndex) {
        if (!edgesMatrix.get(fromIndex).get(toIndex)) {
            throw new IllegalArgumentException("Edge does not exist: " + edge);
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
        return "AdjacencyMatrixGraph{vertices=" + getVertices() + ", edges=" + getEdges() + '}';
    }

}
