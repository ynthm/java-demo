package com.ynthm.algorithm;

import java.util.*;


// Data structure to store heap nodes
class Node {
    int vertex, weight;

    public Node(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }
};

// class to represent a graph object
class Graph {
    // A List of Lists to represent an adjacency list
    List<List<WeightedEdge>> adjList = null;

    // Constructor
    Graph(List<WeightedEdge> edges, int N) {
        adjList = new ArrayList<>(N);

        for (int i = 0; i < N; i++) {
            adjList.add(i, new ArrayList<>());
        }

        // add edges to the undirected graph
        for (WeightedEdge edge : edges) {
            adjList.get(edge.source).add(edge);
        }
    }
}

public class Dijkstra {

    private static void printRoute(int prev[], int i) {
        if (i < 0)
            return;

        printRoute(prev, prev[i]);
        System.out.print(i + " ");
    }

    // Run Dijkstra's algorithm on given graph
    public static void shortestPath(Graph graph, int source, int N) {
        // create min heap and push source node having distance 0
        PriorityQueue<Node> minHeap = new PriorityQueue<>(
                (lhs, rhs) -> lhs.weight - rhs.weight);

        minHeap.add(new Node(source, 0));

        // set infinite distance from source to v initially
        List<Integer> dist = new ArrayList<>(
                Collections.nCopies(N, Integer.MAX_VALUE));

        // distance from source to itself is zero
        dist.set(source, 0);

        // boolean array to track vertices for which minimum
        // cost is already found
        boolean[] done = new boolean[N];
        done[0] = true;

        // stores predecessor of a vertex (to print path)
        int prev[] = new int[N];
        prev[0] = -1;

        // run till minHeap is not empty
        while (!minHeap.isEmpty()) {
            // Remove and return best vertex
            Node node = minHeap.poll();

            // get vertex number
            int u = node.vertex;

            // do for each neighbor v of u
            for (WeightedEdge edge : graph.adjList.get(u)) {
                int v = edge.dest;
                int weight = edge.weight;

                // Relaxation step
                if (!done[v] && (dist.get(u) + weight) < dist.get(v)) {
                    dist.set(v, dist.get(u) + weight);
                    prev[v] = u;
                    minHeap.add(new Node(v, dist.get(v)));
                }
            }

            // marked vertex u as done so it will not get picked up again
            done[u] = true;
        }

        for (int i = 1; i < N; ++i) {
            System.out.print("Path from vertex 0 to vertex " + i +
                    " has minimum cost of " + dist.get(i) +
                    " and the route is [ ");
            printRoute(prev, i);
            System.out.println("]");
        }
    }

    public static void main(String[] args) {
        // initialize edges as per above diagram
        // (u, v, w) triplet represent undirected edge from
        // vertex u to vertex v having weight w
        List<WeightedEdge> edges = Arrays.asList(
                new WeightedEdge(0, 1, 10), new WeightedEdge(0, 4, 3),
                new WeightedEdge(1, 2, 2), new WeightedEdge(1, 4, 4),
                new WeightedEdge(2, 3, 9), new WeightedEdge(3, 2, 7),
                new WeightedEdge(4, 1, 1), new WeightedEdge(4, 2, 8),
                new WeightedEdge(4, 3, 2)
        );

        // Set number of vertices in the graph
        final int N = 5;

        // construct graph
        Graph graph = new Graph(edges, N);

        shortestPath(graph, 0, N);
    }
}