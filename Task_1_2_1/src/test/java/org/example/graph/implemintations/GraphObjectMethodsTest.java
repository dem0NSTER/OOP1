package org.example.graph.implemintations;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.function.Supplier;

import org.example.graph.Graph;
import org.example.graph.models.Edge;
import org.example.graph.models.Vertex;
import org.junit.jupiter.api.Test;

class GraphObjectMethodsTest {

    private final List<Supplier<Graph>> factories = List.of(
            AdjacencyListGraph::new,
            AdjacencyMatrixGraph::new,
            IncidenceMatrixGraph::new
    );

    @Test
    void shouldCompareStructureRegardlessOfImplementationAndInsertionOrder() {
        for (Supplier<Graph> leftFactory : factories) {
            for (Supplier<Graph> rightFactory : factories) {
                Graph left = createGraph(leftFactory, false);
                Graph right = createGraph(rightFactory, true);

                assertEquals(left, left);
                assertEquals(left, right);
                assertEquals(right, left);
                assertEquals(left.hashCode(), right.hashCode());
                assertNotEquals(null, left);
                assertNotEquals("graph", left);
                assertEquals(leftFactory.get(), rightFactory.get());
                assertEquals(leftFactory.get().hashCode(), rightFactory.get().hashCode());
            }
        }
    }

    @Test
    void shouldDistinguishEdgeDirectionAndIsolatedVertices() {
        for (Supplier<Graph> factory : factories) {
            Graph original = createGraph(factory, false);
            Graph changed = createGraph(factory, false);
            Edge edge = new Edge(new Vertex(1), new Vertex(2));

            changed.removeEdge(edge);
            assertNotEquals(original, changed);

            changed.addEdge(new Edge(edge.to(), edge.from()));
            assertNotEquals(original, changed);

            changed.removeEdge(new Edge(edge.to(), edge.from()));
            changed.addEdge(edge);
            changed.addVertex(new Vertex(4));
            assertNotEquals(original, changed);
        }
    }

    @Test
    void shouldDescribeGraphContents() {
        for (Supplier<Graph> factory : factories) {
            Graph graph = createGraph(factory, false);

            assertEquals(graph.getClass().getSimpleName() + "{vertices="
                    + graph.getVertices() + ", edges=" + graph.getEdges() + '}',
                    graph.toString());
            assertEquals(graph.getClass().getSimpleName() + "{vertices=[], edges=[]}",
                    factory.get().toString());
        }
    }

    private Graph createGraph(Supplier<Graph> factory, boolean reverseOrder) {
        Graph graph = factory.get();
        for (int i = 1; i <= 3; i++) {
            graph.addVertex(new Vertex(reverseOrder ? 4 - i : i));
        }
        Edge first = new Edge(new Vertex(1), new Vertex(2));
        Edge second = new Edge(new Vertex(2), new Vertex(3));
        graph.addEdge(reverseOrder ? second : first);
        graph.addEdge(reverseOrder ? first : second);
        return graph;
    }
}
