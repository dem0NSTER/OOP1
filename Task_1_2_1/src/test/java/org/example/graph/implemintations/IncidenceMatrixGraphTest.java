package org.example.graph.implemintations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.example.graph.models.Edge;
import org.example.graph.models.Vertex;
import org.junit.jupiter.api.Test;

class IncidenceMatrixGraphTest {

    @Test
    void shouldAddVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        Vertex vertex = new Vertex(1);

        graph.addVertex(vertex);

        assertTrue(graph.containsVertex(vertex));
        assertEquals(1, graph.vertexCount());
    }

    @Test
    void shouldNotAddSameVertexTwice() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        Vertex vertex = new Vertex(1);

        graph.addVertex(vertex);

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.addVertex(vertex)
        );
    }

    @Test
    void shouldRemoveVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        Vertex vertex = new Vertex(1);

        graph.addVertex(vertex);
        graph.removeVertex(vertex);

        assertFalse(graph.containsVertex(vertex));
        assertEquals(0, graph.vertexCount());
    }

    @Test
    void shouldRemoveEdgesConnectedToRemovedVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        Edge e1 = new Edge(v1, v2);
        Edge e2 = new Edge(v3, v2);

        graph.addEdge(e1);
        graph.addEdge(e2);

        graph.removeVertex(v2);

        assertEquals(0, graph.edgeCount());
        assertFalse(graph.containsEdge(e1));
        assertFalse(graph.containsEdge(e2));
    }

    @Test
    void shouldAddEdge() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        graph.addVertex(v1);
        graph.addVertex(v2);

        Edge edge = new Edge(v1, v2);

        graph.addEdge(edge);

        assertTrue(graph.containsEdge(edge));
        assertEquals(1, graph.edgeCount());
    }

    @Test
    void shouldNotAddEdgeWithoutVertices() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        graph.addVertex(v1);

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge(new Edge(v1, v2))
        );
    }

    @Test
    void shouldNotAddLoop() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        Vertex vertex = new Vertex(1);
        graph.addVertex(vertex);

        assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge(new Edge(vertex, vertex))
        );
    }

    @Test
    void shouldRemoveEdge() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        graph.addVertex(v1);
        graph.addVertex(v2);

        Edge edge = new Edge(v1, v2);

        graph.addEdge(edge);
        graph.removeEdge(edge);

        assertFalse(graph.containsEdge(edge));
        assertEquals(0, graph.edgeCount());
    }

    @Test
    void shouldReturnNeighbors() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        graph.addEdge(new Edge(v1, v2));
        graph.addEdge(new Edge(v1, v3));

        assertEquals(
                Set.of(v2, v3),
                graph.getNeighbors(v1)
        );
    }

    @Test
    void shouldReturnAllVertices() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        assertEquals(
                Set.of(v1, v2, v3),
                graph.getVertices()
        );
    }

    @Test
    void shouldReturnAllEdges() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        Edge e1 = new Edge(v1, v2);
        Edge e2 = new Edge(v1, v3);

        graph.addEdge(e1);
        graph.addEdge(e2);

        assertEquals(
                Set.of(e1, e2),
                graph.getEdges()
        );
    }
}
