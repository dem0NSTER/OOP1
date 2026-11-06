package org.example.graph;

import java.util.Objects;

/**
 * Общие методы сравнения и строкового представления графов.
 */
public abstract class AbstractGraph implements Graph {

    /**
     * Сравнивает вершины и направленные рёбра независимо от реализации графа.
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
        return getClass().getSimpleName() + "{vertices=" + getVertices()
                + ", edges=" + getEdges() + '}';
    }
}
