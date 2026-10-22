package org.example.graph.implemintations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.example.graph.Graph;
import org.example.graph.models.Edge;
import org.example.graph.models.Vertex;

/**
 * Хранит граф в виде списков смежности.
 */
public class AdjacencyListGraph implements Graph {

    private final Map<Vertex, Set<Vertex>> adjacencyList;

    public AdjacencyListGraph() {
        adjacencyList = new HashMap<>();
    }

    @Override
    public void addVertex(Vertex vertex) {

        if (adjacencyList.containsKey(vertex)) {
            throw new IllegalArgumentException(
                    "Vertex already exists: " + vertex
            );
        }

        adjacencyList.put(vertex, new HashSet<>());
    }

    @Override
    public void removeVertex(Vertex vertex) {
        checkVertexExists(vertex);

        adjacencyList.remove(vertex);

        for (Set<Vertex> neighbors : adjacencyList.values()) {
            neighbors.remove(vertex);
        }
    }

    @Override
    public void addEdge(Edge edge) {
        Vertex from = edge.from();
        Vertex to = edge.to();

        checkEdgeVerticesExist(edge);

        if (from.equals(to)) {
            throw new IllegalArgumentException(
                    "Loops are not allowed"
            );
        }

        Set<Vertex> fromNeighbors = adjacencyList.get(from);

        if (fromNeighbors.contains(to)) {
            throw new IllegalArgumentException(
                    "Edge already exists: " + edge
            );
        }

        fromNeighbors.add(to);
    }

    @Override
    public void removeEdge(Edge edge) {
        Vertex from = edge.from();
        Vertex to = edge.to();

        checkEdgeVerticesExist(edge);

        Set<Vertex> fromNeighbors = adjacencyList.get(from);

        checkEdgeExists(edge);

        fromNeighbors.remove(to);
    }

    @Override
    public Set<Vertex> getNeighbors(Vertex vertex) {
        checkVertexExists(vertex);

        return new HashSet<>(adjacencyList.get(vertex));
    }

    @Override
    public Set<Vertex> getVertices() {
        return new HashSet<>(adjacencyList.keySet());
    }

    @Override
    public Set<Edge> getEdges() {
        Set<Edge> edges = new HashSet<>();

        for (Map.Entry<Vertex, Set<Vertex>> entry : adjacencyList.entrySet()) {
            Vertex from = entry.getKey();

            for (Vertex to : entry.getValue()) {
                edges.add(new Edge(from, to));
            }
        }

        return new HashSet<>(edges);
    }

    @Override
    public boolean containsVertex(Vertex vertex) {
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public boolean containsEdge(Edge edge) {
        Vertex from = edge.from();
        Vertex to = edge.to();

        if (!adjacencyList.containsKey(from)) {
            return false;
        }

        return adjacencyList.get(from).contains(to);
    }

    @Override
    public int vertexCount() {
        return adjacencyList.size();
    }

    @Override
    public int edgeCount() {
        int count = 0;

        for (Set<Vertex> neighbors : adjacencyList.values()) {
            count += neighbors.size();
        }

        return count;
    }

    private void checkVertexExists(Vertex vertex) {
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException("Vertex does not exist: " + vertex);
        }
    }

    private void checkEdgeVerticesExist(Edge edge) {
        if (!containsVertex(edge.from()) || !containsVertex(edge.to())) {
            throw new IllegalArgumentException("Both vertices must exist");
        }
    }

    private void checkEdgeExists(Edge edge) {
        if (!containsEdge(edge)) {
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
        return "AdjacencyListGraph{vertices=" + getVertices() + ", edges=" + getEdges() + '}';
    }

}
