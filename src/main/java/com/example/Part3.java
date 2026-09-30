// SD Portfolio 3
// Mads Degn & Daniel Holst Pedersen
// 20/11-25

// Graph Coloring via Greedy Independent Set Algorithm: Partitions subjects into groups
// with no internal edges (no student combinations), enabling parallel scheduling

package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part3 {

    public static void main(String[] args) {
        AdjListGraph g = new AdjListGraph();
        GraphAlgorithms.readGraph(g, "combi.txt");

        ArrayList<Set<Vertex>> groups = new ArrayList<>();
        // Shuffle vertices for randomized greedy approach (improves grouping diversity)
        List<Vertex> vertices = new ArrayList<>(g.vertices());
        Collections.shuffle(vertices);
        System.out.println("Greedy grouping of subject modules (no shared students within a group):\n");

        // Greedy algorithm: iteratively form independent sets where no two vertices are connected
        while (!vertices.isEmpty()) {
            Vertex v0 = vertices.get(0);
            Set<Vertex> group = new HashSet<>();
            group.add(v0);

            // Add vertex if it has no edges to any vertex already in group
            for (Vertex v1 : vertices) {
                boolean canBeAdded = true;
                for (Vertex v2 : group) {
                    // If edge exists between v1 and any member of group, cannot add v1
                    if (g.getWeight(v1, v2) != null) {
                        canBeAdded = false;
                    }
                }
                if (canBeAdded) {
                    group.add(v1);
                }
            }

            vertices.removeAll(group);
            groups.add(group);
            System.out.println("Group " + groups.size() + ": " + group);
        }

        System.out.println("\nNumber of groups (timeslots needed): " + groups.size());
    }
}
