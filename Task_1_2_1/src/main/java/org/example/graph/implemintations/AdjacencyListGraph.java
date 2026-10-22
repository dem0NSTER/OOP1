package org.example.graph.implemintations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.example.graph.Graph;
import org.example.graph.models.Edge;
import org.example.graph.models.Vertex;

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
        if (!adjacencyList.containsKey(vertex)) {
            throw new IllegalArgumentException(
                    "Vertex does not exist: " + vertex
            );
        }

        adjacencyList.remove(vertex);

        for (Set<Vertex> neighbors : adjacencyList.values()) {
            neighbors.remove(vertex);
        }
    }

    @Override
    public void addEdge(Edge edge) {
        Vertex from = edge.from();
        Vertex to = edge.to();

        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            throw new IllegalArgumentException(
                    "Both vertices must exist"
            );
        }

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

        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            throw new IllegalArgumentException(
                    "Both vertices must exist"
            );
        }

        Set<Vertex> fromNeighbors = adjacencyList.get(from);

        if (!fromNeighbors.contains(to)) {
            throw new IllegalArgumentException(
                    "Edge does not exist: " + edge
            );
        }

        fromNeighbors.remove(to);
    }

    @Override
    public Set<Vertex> getNeighbors(Vertex vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            throw new IllegalArgumentException(
                    "Vertex does not exist: " + vertex
            );
        }

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

}
