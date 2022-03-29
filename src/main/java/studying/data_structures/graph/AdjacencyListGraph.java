package studying.data_structures.graph;

import studying.data_structures.graph.model.Vertex;
import studying.data_structures.queue.Queue;

import java.util.*;

public class AdjacencyListGraph {
    private final static int VERTICES_NUMBER = 100;
    private final Map<Vertex, LinkedHashSet<Vertex>> map;
    public int numberOfEdges = 0;


    /**
     * Default constructor
     */
    public AdjacencyListGraph() {
        this(VERTICES_NUMBER);
    }

    /**
     * Constructor with a given number of vertices
     *
     * @param verticesNumber The starting vertices number of the Graph
     */
    public AdjacencyListGraph(int verticesNumber) {
        map = new HashMap<>();

        for (int i = 0; i < verticesNumber; i++)
            map.put(new Vertex(i), new LinkedHashSet<>());
    }

    /**
     * Adds a new vertex to the graph, the index of the new graph must be unique
     *
     * @param vertexIndex The index that will be applied to the new vertex (Must be unique)
     * @return True if the given index is not already contained in the graph, false otherwise
     */
    public boolean addVertex(int vertexIndex) {
        if (map.containsKey(new Vertex(vertexIndex)))
            return false;

        map.put(new Vertex(vertexIndex), new LinkedHashSet<>());
        return true;
    }

    /**
     * Adds a new edge from the given 'from' vertex to the 'to' vertex
     *
     * @param from Starting vertex's edge
     * @param to   Ending vertex's edge
     * @return false if the given vertices outbounds the matrix size, true otherwise
     */
    public boolean addEdge(int from, int to) {
        return this.addEdge(from, to, false);
    }

    /**
     * Adds a new edge from the given 'from' vertex to the 'to' vertex, it also
     * adds the reverse edge if the 'isUndirected' param is given true
     *
     * @param from         Starting vertex's edge
     * @param to           Ending vertex's edge
     * @param isUndirected Decides whether to add the reverse edge or not
     * @return false if the given vertices outbounds the matrix size, true otherwise
     */
    public boolean addEdge(int from, int to, boolean isUndirected) {
        if (from > map.size() || to > map.size() || from == to)
            return false;

        Vertex vFrom = new Vertex(from);
        Vertex vTo = new Vertex(to);

        if (!map.get(vFrom).contains(vTo)) {
            numberOfEdges++;
            map.get(vFrom).add(vTo);
        }

        if (isUndirected && !map.get(vTo).contains(vFrom)) {
            numberOfEdges++;
            map.get(vTo).add(vFrom);
        }

        return true;
    }

    /**
     * Removes and existing edge from the graph
     *
     * @param from Starting vertex's edge
     * @param to   Ending vertex's edge
     * @return False if the given edge does not exist, true otherwise
     */
    public boolean removeEdge(int from, int to) {
        Vertex vFrom = new Vertex(from);
        Vertex vTo = new Vertex(to);

        if (map.get(vFrom).contains(vTo)) {
            numberOfEdges--;
            map.get(vFrom).remove(vTo);

            return true;
        }

        return false;
    }

    /**
     * Performs a Depth First Search on the graph starting from the given vertex
     *
     * @param v Starting vertex
     * @return List of connected vertices in order of visit
     */
    public ArrayList<Integer> iterativeDfs(int v) {
        if (v > map.size())
            throw new IndexOutOfBoundsException();

        ArrayList<Integer> res = new ArrayList<>(map.size());
        boolean[] visited = new boolean[map.size()];
        Stack<Integer> stack = new Stack<>();
        stack.push(v);

        while (!stack.isEmpty()) {
            v = stack.pop();

            if (!visited[v]) {
                visited[v] = true;
                res.add(v);

                for (Vertex adj : map.get(new Vertex(v))) {
                    if (!visited[adj.i])
                        stack.push(adj.i);
                }
            }
        }

        return res;
    }

    /**
     * Performs a Breadth First Search on the graph starting from the given vertex
     *
     * @param v Starting vertex
     * @return List of connected vertices in order of visit
     */
    public ArrayList<Integer> iterativeBfs(int v) {
        if (v > map.size())
            throw new IndexOutOfBoundsException();

        ArrayList<Integer> res = new ArrayList<>(map.size());
        boolean[] visited = new boolean[map.size()];
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(v);
        visited[v] = true;

        while (!queue.isEmpty()) {
            v = queue.dequeue();
            LinkedHashSet<Vertex> neighbours = map.get(new Vertex(v));
            res.add(v);

            for (Vertex adj : neighbours) {
                if (!visited[adj.i]) {
                    queue.enqueue(adj.i);
                    visited[adj.i] = true;
                }
            }
        }

        return res;
    }

    /**
     * Computes and returns the shortest path between the two given Vertices
     *
     * @param s Index of the Starting Vertex
     * @param e Index of the Ending vertex
     * @return An array containing the vertices that compose the shortest path from 's' to 'e'
     */
    public ArrayList<Integer> shortestPath(int s, int e) {
        ArrayList<Integer> prev = new ArrayList<>(Collections.nCopies(map.size(), null));
        ArrayList<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[map.size()];
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);

        // first performing a BFS
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            LinkedHashSet<Vertex> neighbours = map.get(new Vertex(v));

            for (Vertex adj : neighbours) {
                if (!visited[adj.i]) {
                    queue.enqueue(adj.i);
                    visited[adj.i] = true;
                    prev.set(adj.i, v);
                }
            }
        }

        // second we reconstruct the shortest path in reverse
        int at = e;

        while (prev.get(at) != null) {
            at = prev.get(at);
            path.add(at);
        }

        this.reverse(path);

        if (path.size() > 0 && path.get(0) == s)
            return path;

        return new ArrayList<>();
    }

    /**
     * Utility function that simply reverses an ArrayList in place
     *
     * @param list The list to reverse
     */
    private void reverse(ArrayList<Integer> list) {
        int left = 0;
        int right = list.size() - 1;

        while (left < right) {
            int temp = list.get(left);
            list.set(left, list.get(right));
            list.set(right, temp);

            left++;
            right--;
        }
    }

}
