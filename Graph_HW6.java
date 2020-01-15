/* Ivan Wolansky
 * iaw2110
   Graph */

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class Graph {

	// Keep a fast index to nodes in the map
	private Map<Integer, Vertex> vertexNames;

	/**
	 * Construct an empty Graph with a map. The map's key is the name of a
	 * vertex and the map's value is the vertex object.
	 */
	public Graph() {
		vertexNames = new HashMap<>();
	}

	/**
	 * Adds a vertex to the graph. Throws IllegalArgumentException if two
	 * vertices with the same name are added.
	 * 
	 * @param v
	 *            (Vertex) vertex to be added to the graph
	 */
	public void addVertex(Vertex v) {
		if (vertexNames.containsKey(v.name))
			throw new IllegalArgumentException(
					"Cannot create new vertex with existing name.");
		vertexNames.put(v.name, v);
	}

	/**
	 * Gets a collection of all the vertices in the graph
	 * 
	 * @return (Collection<Vertex>) collection of all the vertices in the graph
	 */
	public Collection<Vertex> getVertices() {
		return vertexNames.values();
	}

	/**
	 * Gets the vertex object with the given name
	 * 
	 * @param name
	 *            (String) name of the vertex object requested
	 * @return (Vertex) vertex object associated with the name
	 */
	public Vertex getVertex(String name) {
		return vertexNames.get(name);
	}

	/**
	 * Adds a directed edge from vertex u to vertex v
	 * 
	 * @param nameU
	 *            (String) name of vertex u
	 * @param nameV
	 *            (String) name of vertex v
	 * @param cost
	 *            (double) cost of the edge between vertex u and v
	 */
	public void addEdge(int nameU, int nameV, Double cost) {
		if (!vertexNames.containsKey(nameU))
			throw new IllegalArgumentException(
					nameU + " does not exist. Cannot create edge.");
		if (!vertexNames.containsKey(nameV))
			throw new IllegalArgumentException(
					nameV + " does not exist. Cannot create edge.");
		Vertex sourceVertex = vertexNames.get(nameU);
		Vertex targetVertex = vertexNames.get(nameV);
		Edge newEdge = new Edge(sourceVertex, targetVertex, cost);
		sourceVertex.addEdge(newEdge);
	}

	/**
	 * Adds an undirected edge between vertex u and vertex v by adding a
	 * directed edge from u to v, then a directed edge from v to u
	 * 
	 * @param name
	 *            (String) name of vertex u
	 * @param name2
	 *            (String) name of vertex v
	 * @param cost
	 *            (double) cost of the edge between vertex u and v
	 */
	public void addUndirectedEdge(int name, int name2, double cost) {
		addEdge(name, name2, cost);
		addEdge(name2, name, cost);
	}

	/**
	 * Computes the euclidean distance between two points as described by their
	 * coordinates
	 * 
	 * @param ux
	 *            (double) x coordinate of point u
	 * @param uy
	 *            (double) y coordinate of point u
	 * @param vx
	 *            (double) x coordinate of point v
	 * @param vy
	 *            (double) y coordinate of point v
	 * @return (double) distance between the two points
	 */
	public double computeEuclideanDistance(double ux, double uy, double vx,
			double vy) {
		return Math.sqrt(Math.pow(ux - vx, 2) + Math.pow(uy - vy, 2));
	}

	/**
	 * Computes euclidean distance between two vertices as described by their
	 * coordinates
	 * 
	 * @param u
	 *            (Vertex) vertex u
	 * @param v
	 *            (Vertex) vertex v
	 * @return (double) distance between two vertices
	 */
	public double computeEuclideanDistance(Vertex u, Vertex v) {
		return computeEuclideanDistance(u.x, u.y, v.x, v.y);
	}

	/**
	 * Calculates the euclidean distance for all edges in the map using the
	 * computeEuclideanCost method.
	 */
	public void computeAllEuclideanDistances() {
		for (Vertex u : getVertices())
			for (Edge uv : u.adjacentEdges) {
				Vertex v = uv.target;
				uv.distance = computeEuclideanDistance(u.x, u.y, v.x, v.y);
			}
	}

	// STUDENT CODE STARTS HERE

	// generates random vertices on the graph
	public void generateRandomVertices(int n) {
		vertexNames = new HashMap<>(); // reset the vertex hashmap
		Random rand = new Random();
		for (int i = 0; i <= n - 1; i++) {
			Vertex v = new Vertex(i, rand.nextInt(101), rand.nextInt(101));
			addVertex(v);
			if (i > 0) {
				for (int j = 0; j <= i; j++) {
					if (i != j) {
						addUndirectedEdge(i, j, v.distance);
					}
				}
			}
		}
		computeAllEuclideanDistances(); // compute distances
	}

	// this returns a list of Edges that is supposed to be the shortest
	// distances to traverse from vertex to vertex, however, it is a
	// greedy algorithm so it is not always correct, hence why we use
	// the brute force algorithm
	public List<Edge> nearestNeighborTsp() {
		Random rand = new Random();
		// if there are less then 2 vertices it can't do the algorithm
		// since there's only one vertex then
		if (vertexNames.size() < 2) {
			return null;
		}

		// makes each vertex's known field false
		for (Vertex v : getVertices()) {
			v.known = false;
		}

		List<Edge> neighborEdges = new LinkedList<>();

		// begins at a random vertex on the graph
		Vertex v = vertexNames.get(rand.nextInt(vertexNames.size()));

		// visits each vertex traveling along shortest edges from one vertex
		// to the next one
		for (int i = 1; i < vertexNames.size(); i++) {
			// marks the current vertex as visited
			v.known = true;

			// finds the edge for the closest neighboring vertex
			Edge e = v.adjacentEdges.stream().filter(e1 -> !e1.target.known)
					.min((e1, e2) -> Double.compare(e1.distance, e2.distance))
					.get();
			neighborEdges.add(e);
			v = e.target;
		}
		// adds the final edge to complete the cycle, otherwise there will
		// be one edge not highlighted
		neighborEdges.add(new Edge(v, neighborEdges.get(0).source,
				computeEuclideanDistance(v, neighborEdges.get(0).source)));
		return neighborEdges;
	}

	/**
	 * Finds the shortest cycle by checking every permutation
	 * 
	 * @return (List<Edge>) Simple cycle representing shortest path
	 */
	// computes the actual shortest cycle from a random node
	public List<Edge> bruteForceTsp() {
		// if there are less then 2 vertices it can't do the algorithm
		// since there's only one vertex then
		if (vertexNames.size() < 2) {
			return null;
		}
		List<Edge> bruteEdges = new LinkedList<>();
		PermuteInt perm = new PermuteInt(
				vertexNames.keySet().stream().filter(i -> i != 0));

		// this stores the shortest path thus far
		double lowestDist = Double.MAX_VALUE;
		// has to be initialized to null so that it can be used
		// to construct a cycle
		int[] shortestPath = null;

		for (int[] path : perm.getPermutations()) {
			// adds up to find the sum for the distance of the path
			double dist = computeEuclideanDistance(vertexNames.get(0),
					vertexNames.get(path[0]));
			for (int i = 1; i < path.length; i++) {
				dist += computeEuclideanDistance(vertexNames.get(path[i - 1]),
						vertexNames.get(path[i]));
			}
			dist += computeEuclideanDistance(
					vertexNames.get(path[path.length - 1]), vertexNames.get(0));

			// check if shortest path so far
			if (dist < lowestDist) {
				lowestDist = dist;
				shortestPath = path;
			}
		}

		// creates list of edges for shortest cycle
		int[] cycle = new int[shortestPath.length + 2];
		// adds the origin vertex to complete cycle
		cycle[0] = cycle[cycle.length - 1] = vertexNames.get(0).name;
		for (int i = 0; i < shortestPath.length; i++) {
			cycle[i + 1] = shortestPath[i];
		}
		for (int i = 1; i < cycle.length; i++) {
			double dist = computeEuclideanDistance(
					vertexNames.get(cycle[i - 1]), vertexNames.get(cycle[i]));
			bruteEdges.add(new Edge(vertexNames.get(cycle[i - 1]),
					vertexNames.get(cycle[i]), dist));
		}
		return bruteEdges;
	}

	// PermuteInt class calculates permutations for a set integers
	private class PermuteInt {
		int n;
		int[] set;
		ArrayDeque<int[]> permutations;

		public PermuteInt(Stream<Integer> set) {
			this.set = set.mapToInt(i -> i).toArray();
			n = this.set.length;
			permutations = new ArrayDeque<>();
			if (n >= 2) {
				calculatePermutations(this.set, n - 2);
			} else {
				permutations.offer(this.set);
			}
		}
		
		// returns the permutations in order
		public ArrayDeque<int[]> getPermutations() {
			return permutations;
		}

		// Recursive method to permute the set by only swapping adjacent
		// elements where level is the distance to the whole array
		private void calculatePermutations(int[] array, int level) {
			if (level == 0) {
				permutations.offer(array);
				return;
			}
			calculatePermutations(Arrays.copyOf(array, array.length), level - 1);
			for (int i = level; i < array.length; i++) {
				swapReferences(array, i - 1, i);
				calculatePermutations(Arrays.copyOf(array, array.length), level - 1);
			}
		}

		// Swaps indices a and b of an Array
		private void swapReferences(int[] array, int a, int b) {
			int tmp = array[a];
			array[a] = array[b];
			array[b] = tmp;
		}
	}

	// STUDENT CODE ENDS HERE

	/**
	 * Prints out the adjacency list of the graph for debugging
	 */
	public void printAdjacencyList() {
		for (int u : vertexNames.keySet()) {
			StringBuilder sb = new StringBuilder();
			sb.append(u);
			sb.append(" -> [ ");
			for (Edge e : vertexNames.get(u).adjacentEdges) {
				sb.append(e.target.name);
				sb.append("(");
				sb.append(e.distance);
				sb.append(") ");
			}
			sb.append("]");
			System.out.println(sb.toString());
		}
	}
}
