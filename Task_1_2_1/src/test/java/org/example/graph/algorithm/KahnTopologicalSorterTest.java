package org.example.graph.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.example.graph.Graph;
import org.example.graph.implemintations.AdjacencyListGraph;
import org.example.graph.implemintations.AdjacencyMatrixGraph;
import org.example.graph.implemintations.IncidenceMatrixGraph;
import org.example.graph.models.Edge;
import org.example.graph.models.Vertex;
import org.junit.jupiter.api.Test;

class KahnTopologicalSorterTest {

    @Test
    void shouldSortDagForAllGraphTypes() {
        Graph[] graphs = {
                new AdjacencyListGraph(),
                new AdjacencyMatrixGraph(),
                new IncidenceMatrixGraph()
        };

        for (Graph graph : graphs) {
            Vertex v1 = new Vertex(1);
            Vertex v2 = new Vertex(2);
            Vertex v3 = new Vertex(3);
            graph.addVertex(v1);
            graph.addVertex(v2);
            graph.addVertex(v3);
            graph.addEdge(new Edge(v3, v2));
            graph.addEdge(new Edge(v2, v1));

            KahnTopologicalSorter sorter = new KahnTopologicalSorter();
            List<Vertex> result = sorter.sort(graph);

            checkOrder(graph, result);
        }
    }

    @Test
    void shouldAllowDifferentCorrectOrders() {
        Graph graph = new AdjacencyListGraph();
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);
        Vertex v4 = new Vertex(4);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addEdge(new Edge(v1, v3));
        graph.addEdge(new Edge(v2, v3));
        // v1 и v2 могут идти в любом порядке, v4 не связана с другими вершинами.

        KahnTopologicalSorter sorter = new KahnTopologicalSorter();
        List<Vertex> result = sorter.sort(graph);

        checkOrder(graph, result);
    }

    @Test
    void shouldSortEmptyGraph() {
        Graph graph = new AdjacencyListGraph();
        KahnTopologicalSorter sorter = new KahnTopologicalSorter();

        List<Vertex> result = sorter.sort(graph);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldSortSingleVertex() {
        Graph graph = new AdjacencyListGraph();
        Vertex vertex = new Vertex(1);
        graph.addVertex(vertex);
        KahnTopologicalSorter sorter = new KahnTopologicalSorter();

        List<Vertex> result = sorter.sort(graph);

        assertEquals(1, result.size());
        assertEquals(vertex, result.get(0));
    }

    @Test
    void shouldRejectCycle() {
        Graph graph = new AdjacencyListGraph();
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(new Edge(v1, v2));
        graph.addEdge(new Edge(v2, v1));
        KahnTopologicalSorter sorter = new KahnTopologicalSorter();

        assertThrows(IllegalStateException.class, () -> sorter.sort(graph));
    }

    private void checkOrder(Graph graph, List<Vertex> result) {
        assertEquals(graph.vertexCount(), result.size());
        for (Vertex vertex : graph.getVertices()) {
            assertTrue(result.contains(vertex));
        }
        for (Edge edge : graph.getEdges()) {
            int fromIndex = result.indexOf(edge.from());
            int toIndex = result.indexOf(edge.to());
            assertTrue(fromIndex < toIndex);
        }
    }
}
