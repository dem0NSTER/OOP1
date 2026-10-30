package org.example.graph;

import java.util.Set;
import org.example.graph.models.Edge;
import org.example.graph.models.Vertex;

/**
 * Ориентированный граф без петель и кратных рёбер.
 */
public interface Graph {

    void addVertex(Vertex vertex);

    void removeVertex(Vertex vertex);

    void addEdge(Edge edge);

    void removeEdge(Edge edge);

    Set<Vertex> getNeighbors(Vertex vertex);

    Set<Vertex> getVertices();

    Set<Edge> getEdges();

    boolean containsVertex(Vertex vertex);

    boolean containsEdge(Edge edge);

    int vertexCount();

    int edgeCount();

}
