// SD Portfolio 3
// Mads Degn & Daniel Holst Pedersen
// 20/11-25

// Graph Transformation: Constructs a metagraph where vertices are the independent sets from Part 3
// Edge weights represent inter-group dependencies (students combining modules across groups)

package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part4 {
    public static void main(String[] args) {
        // Step 1: Read the original graph and create groups (from Part 3)
        AdjListGraph originalGraph = new AdjListGraph();
        GraphAlgorithms.readGraph(originalGraph, "combi.txt");

        ArrayList<Set<Vertex>> groups = createGroups(originalGraph);
        System.out.println("Number of groups: " + groups.size());
        System.out.println("Groups: " + groups);

        // Step 2: Create a new graph where vertices are groups (metagraph representation)
        AdjListGraph groupGraph = new AdjListGraph();
        
        // Create group vertices - each group becomes a single vertex in the new graph
        Map<Set<Vertex>, Vertex> groupToVertex = new HashMap<>();
        for (int i = 0; i < groups.size(); i++) {
            Vertex groupVertex = new Vertex("Group" + i);
            groupToVertex.put(groups.get(i), groupVertex);
        }

        // Step 3: Add edges between groups
        // Edge weight = sum of students combining modules from one group with modules from another
        for (int i = 0; i < groups.size(); i++) {
            for (int j = i + 1; j < groups.size(); j++) {
                Set<Vertex> group1 = groups.get(i);
                Set<Vertex> group2 = groups.get(j);
                
                // Calculate total students requiring different timeslots between groups
                int totalWeight = calculateGroupWeight(originalGraph, group1, group2);
                
                if (totalWeight > 0) {
                    Vertex v1 = groupToVertex.get(group1);
                    Vertex v2 = groupToVertex.get(group2);
                    // Insert bidirectional edges for undirected graph representation
                    groupGraph.insertEdge(v1, v2, totalWeight);
                    groupGraph.insertEdge(v2, v1, totalWeight);
                }
            }
        }

        // Step 4: Print the group graph
        System.out.println("\n=== Group Graph ===");
        printGroupGraph(groupGraph, groupToVertex, groups);
    }

    /**
     * Creates groups of subject modules with no overlapping students (from Part 3 algorithm)
     */
    private static ArrayList<Set<Vertex>> createGroups(AdjListGraph g) {
        ArrayList<Set<Vertex>> groups = new ArrayList<>();
        List<Vertex> vertices = new ArrayList<>(g.vertices());
        Collections.shuffle(vertices);

        while (!vertices.isEmpty()) {
            Vertex v0 = vertices.get(0);
            Set<Vertex> group = new HashSet<>();
            group.add(v0);

            for (Vertex v1 : vertices) {
                boolean canBeAdded = true;
                for (Vertex v2 : group) {
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
        }

        return groups;
    }

    /**
     * Calculates the total weight of edges between two groups
     * Weight = sum of students combining modules from group1 with modules from group2
     */
    private static int calculateGroupWeight(AdjListGraph originalGraph, Set<Vertex> group1, Set<Vertex> group2) {
        int totalWeight = 0;
        
        // Sum all inter-group edges: represents students who need both groups scheduled at different times
        for (Vertex v1 : group1) {
            for (Vertex v2 : group2) {
                Integer weight = originalGraph.getWeight(v1, v2);
                if (weight != null) {
                    totalWeight += weight;
                }
            }
        }
        
        return totalWeight;
    }

    /**
     * Prints the group graph in a readable format showing group composition and dependencies
     */
    private static void printGroupGraph(AdjListGraph groupGraph, Map<Set<Vertex>, Vertex> groupToVertex, ArrayList<Set<Vertex>> groups) {
        for (int i = 0; i < groups.size(); i++) {
            Vertex groupVertex = groupToVertex.get(groups.get(i));
            System.out.println("\n" + groupVertex + " contains: " + groups.get(i));
            
            for (Edge e : groupGraph.outEdge(groupVertex)) {
                System.out.println("  -> " + e.to() + ": " + e.weight() + " students");
            }
        }
    }
}
