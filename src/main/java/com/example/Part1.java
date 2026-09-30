// SD Portfolio 3
// Mads Degn & Daniel Holst Pedersen
// 20/11-25

// Graph Construction: Reads student combination data and builds bidirectional adjacency 
// list representation

package com.example;

public class Part1 {

    public static void main(String[] args) {
        AdjListGraph g = new AdjListGraph();

        // Parse combi.txt file: edges represent subject module pairs with student count as weight
        GraphAlgorithms.readGraph(g, "combi.txt");

        System.out.println("Subject Module Combinations Graph:\n");
        GraphAlgorithms.printGraph(g);
    }
}
