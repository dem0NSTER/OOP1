package org.example.graph.implemintations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.example.graph.Graph;
import org.example.graph.models.Edge;
import org.example.graph.models.Vertex;
import org.junit.jupiter.api.Test;

class GraphObjectMethodsTest {

    @Test
    void shouldCompareAllGraphTypes() {
        Graph list = new AdjacencyListGraph();
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);

        list.addVertex(v1);
        list.addVertex(v2);
        list.addEdge(new Edge(v1, v2));

        // Другой порядок добавления вершин не меняет сам граф.
        Graph adjacencyMatrix = new AdjacencyMatrixGraph();
        adjacencyMatrix.addVertex(v2);
        adjacencyMatrix.addVertex(v1);
        adjacencyMatrix.addEdge(new Edge(v1, v2));

        Graph incidenceMatrix = new IncidenceMatrixGraph();
        incidenceMatrix.addVertex(v1);
        incidenceMatrix.addVertex(v2);
        incidenceMatrix.addEdge(new Edge(v1, v2));

        assertEquals(list, adjacencyMatrix);
        assertEquals(adjacencyMatrix, list);
        assertEquals(list, incidenceMatrix);
        assertEquals(incidenceMatrix, list);
        assertEquals(adjacencyMatrix, incidenceMatrix);
        assertEquals(incidenceMatrix, adjacencyMatrix);
        assertEquals(list.hashCode(), adjacencyMatrix.hashCode());
        assertEquals(list.hashCode(), incidenceMatrix.hashCode());
    }

    @Test
    void shouldDistinguishDifferentGraphs() {
        Graph first = new AdjacencyListGraph();
        Graph second = new AdjacencyListGraph();
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        first.addVertex(v1);
        first.addVertex(v2);
        second.addVertex(v1);

        assertNotEquals(first, second);

        second.addVertex(v2);
        first.addEdge(new Edge(v1, v2));
        second.addEdge(new Edge(v2, v1));

        assertNotEquals(first, second);
    }

    @Test
    void shouldDescribeEmptyGraphs() {
        assertEquals("AdjacencyListGraph{vertices=[], edges=[]}",
                new AdjacencyListGraph().toString());
        assertEquals("AdjacencyMatrixGraph{vertices=[], edges=[]}",
                new AdjacencyMatrixGraph().toString());
        assertEquals("IncidenceMatrixGraph{vertices=[], edges=[]}",
                new IncidenceMatrixGraph().toString());
    }
}
