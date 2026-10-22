package org.example.graph.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.example.graph.Graph;
import org.example.graph.implemintations.AdjacencyListGraph;
import org.example.graph.models.Edge;
import org.example.graph.models.Vertex;
import org.junit.jupiter.api.Test;

class GraphReaderTest {

    @Test
    void shouldReadGraphFromFile() throws FileNotFoundException {
        Graph graph = GraphReader.read(
                "src/test/resources/graph.txt", AdjacencyListGraph::new);
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        assertEquals(3, graph.vertexCount());
        assertEquals(2, graph.edgeCount());
        assertTrue(graph.containsVertex(v1));
        assertTrue(graph.containsVertex(v2));
        assertTrue(graph.containsVertex(v3));
        assertTrue(graph.containsEdge(new Edge(v1, v2)));
        assertTrue(graph.containsEdge(new Edge(v2, v3)));
    }

    @Test
    void shouldReadEmptyGraph() throws FileNotFoundException {
        Graph graph = GraphReader.read(
                "src/test/resources/empty-graph.txt", AdjacencyListGraph::new);

        assertEquals(0, graph.vertexCount());
        assertEquals(0, graph.edgeCount());
    }

    @Test
    void shouldRejectNegativeVertexCount() {
        assertThrows(IllegalArgumentException.class, () -> GraphReader.read(
                "src/test/resources/negative-vertices.txt", AdjacencyListGraph::new));
    }

    @Test
    void shouldRejectNegativeEdgeCount() {
        assertThrows(IllegalArgumentException.class, () -> GraphReader.read(
                "src/test/resources/negative-edges.txt", AdjacencyListGraph::new));
    }
}
