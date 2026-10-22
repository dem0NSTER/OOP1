package org.example.graph.algorithm;

import java.util.List;

import org.example.graph.Graph;
import org.example.graph.models.Vertex;

public interface TopologicalSorter {

    List<Vertex> sort(Graph graph);

}
