// SD Portfolio 3
// Mads Degn & Daniel Holst Pedersen
// 20/11-25

// Graph Connectivity Analysis: Determines if the graph is connected and identifies components.
// Algorithm: Breadth-first search for reachability analysis - Time Complexity: O(V + E).

package com.example;

import java.util.Set;

public class Part2 {

    /**
     * Checks if graph is connected using BFS from an arbitrary vertex.
     * A connected graph means all subjects are related through student combinations.
     * @return true if all vertices are reachable from any starting vertex
     */
    public static boolean isConnected(Graph g) {
        if (g.vertices().isEmpty()) {
            return true;
        }

        // Start BFS from any vertex and compare reachable count with total vertices
        Vertex start = g.vertices().iterator().next();
        Set<Vertex> reachableVertices = GraphAlgorithms.visitBreadthFirst(g, start);
        return reachableVertices.size() == g.vertices().size();
    }

    /**
     * Identifies disconnected components within the graph.
     * Each component represents an isolated group of subject modules.
     */
    public static void findComponents(Graph g) {
        Set<Vertex> unvisited = new java.util.HashSet<>(g.vertices());
        int componentCount = 0;

        while (!unvisited.isEmpty()) {
            Vertex start = unvisited.iterator().next();
            Set<Vertex> component = GraphAlgorithms.visitBreadthFirst(g, start);
            componentCount++;

            System.out.println("\nComponent " + componentCount + ":");
            for (Vertex v : component) {
                System.out.println("  " + v.name());
            }

            unvisited.removeAll(component);
        }
    }

    public static void main(String[] args) {
        AdjListGraph g = new AdjListGraph();
        GraphAlgorithms.readGraph(g, "combi.txt");

        boolean connected = isConnected(g);
        System.out.println("Graph Connectivity Analysis:\n");
        System.out.println("The subject module graph is " + (connected ? "connected" : "not connected"));

        if (!connected) {
            System.out.println("\nThe university has separate groupings of subjects:");
            System.out.println("Each group represents a set of subjects where students");
            System.out.println("take combinations only within that group.");
            findComponents(g);
        }
    }
}
