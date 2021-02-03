package ac.ir.urmia.Eight.DS.GraphToolkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;


/**
 * Graph Class is the heart of the Graph toolkit project.
 * Graph is designed to work with different directed-graphs.
 *
 * @param <V> The type of object you are going to store in graph.
 *            Dedicated to E.M, without whom, this project would be unnamed :)
 */


public class Graph<V> {

    private final HashTable<V, HashTable<V, Double>> adjacencyTable = new HashTable<>();

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();

        graph.addEdge("C", "A");
        graph.addEdge("C", "D");
        graph.addEdge("C", "B");
        graph.addEdge("B", "D");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("D", "C");

//     graph.addEdge("Tehran", "Urmia",800.0);
//     graph.addEdge("Sanandaj", "Laos",800.0);
//     graph.addEdge("Laos", "Tehran",800.0);
//     graph.addEdge("Tehran", "Mashhad", 900.0);
//     graph.addEdge("Urmia", "Sanandaj", 250.0);
//     graph.addEdge("Urmia", "LosAngles", 100000.0);
//     graph.addEdge("Urmia", "LosPolos", 200000.0);
//     graph.addEdge("Urmia", "SaoPaolo", 250000.0);
//     graph.addEdge("Urmia", "NewMexico", 5000000.0);
//     System.out.println(graph.getNeighbors("Tehran"));
//     System.out.println(graph.getNeighbors("Urmia"));
//     System.out.println(graph.removeEdge("Tehran", "Mashhad"));
//     System.out.println(graph.getNeighbors("Tehran"));
//     System.out.println(graph.getWeight("Tehran", "Urmia"));
//     System.out.println(graph.getWeight("Tehran", "Urmu"));
        // System.out.println(graph.removeNode("Urmia"));
        // graph.BFS("Tehran", "NewMexico");
        // graph.DFS("Tehran", "NewMexico");
        // graph.pageRank();
        // graph.addEdge(5, 2);
        // graph.addEdge(5, 0);
        // graph.addEdge(4, 0);
        // graph.addEdge(4, 1);
        // graph.addEdge(2, 3);
        // graph.addEdge(3, 1);
        // graph.topologicalSort();
        System.out.println(graph.graphColoring());

        graph.saveToFile("Graph.txt");
        Graph myg = readFromFile("Graph.txt");
        System.out.println(Arrays.toString(myg.adjacencyTable.table));
        myg.saveToFile("Graph.txt");

    }

    /**
     * Static method that reads and constructs a graph of the given file.
     *
     * @param path the path of .txt file you want import the graph from.
     * @return A graph constructed from the file.
     */
    private static Graph<Object> readFromFile(String path) {
        Graph<Object> graph = new Graph<>();
        StringBuilder builder = new StringBuilder();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine())
                builder.append(scanner.nextLine()).append("\n");

        } catch (FileNotFoundException e) {
            e.printStackTrace();


        }
        for (String s : builder.toString().split("\n")) {
            String[] p = s.split(" {4}");
            graph.addEdge(p[0], p[1], Double.valueOf(p[2]));
        }
        return graph;
    }

    /**
     * This method exports the graph into a txt file with given name.
     *
     * @param filename the file's name that's intended to store the graph in.
     */
    private void saveToFile(String filename) {
        StringBuilder builder = new StringBuilder();


        for (HashTable.node<V, ?> node : this.adjacencyTable.table) {
            while (node != null) {
                for (V neighbor : this.getNeighbors(node.getKey()))
                    builder.append(node.getKey()).append("    ").append(neighbor).append("    ").append(this.getWeight(node.getKey(), neighbor)).append("\n");

                node = node.getNext();
            }
        }


        try {
            FileWriter fw = new FileWriter(filename);

            fw.write(builder.toString());
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("The graph is written to file named: " + "Graph.txt");
    }


    /**
     * Checks if the node v exists or not.
     *
     * @param v the node that's wanted to be checked.
     * @return
     */
    boolean nodeExists(V v) {
        return adjacencyTable.containsKey(v);
    }

    /**
     * Adds a node to graph.
     *
     * @param v the node we want to add.
     */

    void addNode(V v) {
        if (nodeExists(v))
            throw new IllegalArgumentException("Node already exists: " + v.toString());
        adjacencyTable.put(v, new HashTable<>());
    }


    /**
     * Gets the weight of the edge between nodes v and w.
     *
     * @param v node in graph.
     * @param w node in graph.
     * @return Weight of the edge in a Double.
     */
    Double getWeight(V v, V w) {
        HashTable<V, Double> list = adjacencyTable.get(v);
        if (list == null)
            return null;
        return list.get(w);
    }

    /**
     * add edge between two nodes.
     *
     * @param v node in graph.
     * @param w node in graph.
     */

    void addEdge(V v, V w) {
        addEdge(v, w, 0.0);

    }

    /**
     * add a weighted edge between two nodes.
     *
     * @param v      node in graph.
     * @param w      node in graph.
     * @param weight the weight of the edge.
     */
    void addEdge(V v, V w, Double weight) {
        if (!nodeExists(v))
            addNode(v);
        if (!nodeExists(w))
            addNode(w);

        adjacencyTable.get(v).put(w, weight);

    }

    /**
     * Removes a node and all its occurrences from the Hashtable.
     *
     * @param v the node that is wanted to be removed.
     * @return the corresponding adjacency table after node v is removed.
     */

    HashTable<V, Double> removeNode(V v) {

        for (HashTable.node<V, ?> node : adjacencyTable.table) {
            while (node != null) {
                if (adjacencyTable.get(node.getKey()) != null)

                    adjacencyTable.get(node.getKey()).remove(v);


                node = node.getNext();
            }
        }

        return adjacencyTable.remove(v);
    }

    /**
     * Removes the edge between two nodes in graph.
     *
     * @param v node in graph.
     * @param w node in graph.
     * @@return the corresponding adjacency table after node v is removed.
     */
    Double removeEdge(V v, V w) {
        return adjacencyTable.get(v).remove(w);
    }

    /**
     * neighbours of the node v.
     * (In this implementation of directed-graph we only count nodes as neighbors that have at least one edge
     * From this node)
     *
     * @param v the node we want to get its neighbors.
     * @return a set of the nodes that are node v's neighbors.
     */
    Set<V> getNeighbors(V v) {
        return adjacencyTable.get(v).keySet();
    }


    /**
     * Implementation of BFS.
     *
     * @param start  the node that is wanted to start the search from.
     * @param target the node that is intended to be found.
     */

    public void BFS(V start, V target) {
        LinkedList<V> queue = new LinkedList<>();
        Set<V> visitedSet = new HashSet<>();
        queue.add(start);
        visitedSet.add(start);
        //   System.out.println(start.toString());

        while (queue.size() != 0) {

            start = queue.poll();


            for (V neighbor : getNeighbors(start)) {
                if (!visitedSet.contains(neighbor)) {
                    visitedSet.add(neighbor);
                    queue.add(neighbor);
                    //  System.out.println(neighbor.toString());
                    if (neighbor.equals(target)) {
                        System.out.println(neighbor.toString());
                        return;
                    }


                }
            }
        }

    }


    /**
     * Implementation of DFS.
     *
     * @param s      the node that is wanted to start the search from.
     * @param target the node that is intended to be found.
     */
    public void DFS(V s, V target) {
        Set<V> visitedSet = new HashSet<>();


        Stack<V> stack = new Stack<>();

        stack.push(s);

        while (!stack.empty()) {

            s = stack.peek();
            stack.pop();

            if (!visitedSet.contains(s))

                visitedSet.add(s);


            for (V neighbor : getNeighbors(s)) {

                if (neighbor.equals(target)) {
                    System.out.println("Found: " + neighbor.toString());
                }

                if (!visitedSet.contains(neighbor))
                    stack.push(neighbor);
            }

        }
    }


    /**
     * Utility function that's necessary for the implementation of topological sort .
     *
     * @param s
     * @param visitedSet
     * @param stack
     */
    private void topologicalSortUtil(V s, Set<V> visitedSet, Stack<V> stack) {
        visitedSet.add(s);

        for (V neighbor : getNeighbors(s)) {
            if (!visitedSet.contains(neighbor))
                topologicalSortUtil(neighbor, visitedSet, stack);
        }

        stack.push(s);
    }

    /**
     * prints the topologically sorted form of the graph.
     */
    public void topologicalSort() {
        Stack<V> stack = new Stack<>();

        Set<V> visitedSet = new HashSet<>();

        for (V v : adjacencyTable.keySet()) {
            if (!visitedSet.contains(v))
                topologicalSortUtil(v, visitedSet, stack);


        }


        while (!stack.empty())
            System.out.print(stack.pop() + " ");
    }


    /**
     * An implementation of the PageRank Algorithm.
     * prints the rank-points of the graph's nodes.
     */
    public void pageRank() {


        Map<V, Integer> occurrences = new HashMap<>();
        StringBuilder builder = new StringBuilder();

        for (HashTable.node<V, ?> node : this.adjacencyTable.table) {
            while (node != null) {

                for (V neighbor : this.getNeighbors(node.getKey()))
                    builder.append(node.getKey()).append("    ").append(neighbor).append("    ");
                node = node.getNext();
            }
        }
        for (String s : builder.toString().split(" {4}")) {
            Integer oldCount = occurrences.get(s);
            if (oldCount == null) {
                oldCount = 0;
            }
            occurrences.put((V) s, oldCount + 1);
        }


        Map<V, Double> prev = new HashMap<>();
        Map<V, Double> curr = new HashMap<>();

        for (V v : occurrences.keySet()) {
            prev.put(v, 1.0 / occurrences.size());
        }


        for (int j = 0; j < 5; j++) {


            for (V v : occurrences.keySet()) {
                double sum = 0.0;
                for (V z : occurrences.keySet()) {
                    if (z.equals(v)) continue;
                    if (this.getNeighbors(z).contains(v)) {
                        sum += prev.get(z) / this.getNeighbors(z).size();
                    }

                }
                curr.put(v, sum);

            }
            prev.putAll(curr);
        }

        System.out.println(curr.toString());


    }


    /**
     * A creative solving method for solving the graph coloring problem.
     *
     * @return the minimum number of colors that are needed to color the graph.
     */

    public int graphColoring() {


        Map<V, Integer> occurrences = new HashMap<>();
        Map<V, Integer> nodeColor = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        Set<Integer> colors = new HashSet<>();
        colors.add(0);

        for (HashTable.node<V, ?> node : this.adjacencyTable.table) {
            while (node != null) {

                for (V neighbor : this.getNeighbors(node.getKey()))
                    builder.append(node.getKey()).append("    ").append(neighbor).append("    ");
                node = node.getNext();
            }
        }
        for (String s : builder.toString().split(" {4}")) {
            Integer oldCount = occurrences.get(s);
            if (oldCount == null) {
                oldCount = 0;
            }
            occurrences.put((V) s, oldCount + 1);
        }

        colors.add(0);
        for (V v : occurrences.keySet()) {
            int pc = 0;
            int color = 0;
            nodeColor.put(v, color);

            for (V z : occurrences.keySet()) {
                if (z.equals(v)) continue;
                if (this.getNeighbors(z).contains(v)) {
                    nodeColor.put(z, color++);
                    colors.add(color);
                }

            }

            for (V neighbor : this.getNeighbors(v)) {
                if (neighbor == null)
                    if (nodeColor.get(neighbor).equals(color)) {
                        nodeColor.put(v, color++);
                        colors.add(color);
                    }
            }

        }

        return colors.size();
    }


}










