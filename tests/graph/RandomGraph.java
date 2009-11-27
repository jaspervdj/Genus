package graph;

import genus.Graph;
import genus.BasicGraph;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Random;

/** A random graph.
 */
public class RandomGraph extends BasicGraph
{
    /** Random number generator. */
    private static final Random RANDOM = new Random();

    /** Constructor.
     *  @param v Maximum number of vertices.
     *  @param e Number of edges.
     */
    public RandomGraph(int v, int e)
    {
        super();

        /* Ensure we have enough edges. */
        if(e < v - 1 || e > (v - 1) * v / 2)
            throw new IllegalArgumentException("Cannot construct a graph with "
                    + e + " edges and " + v + " vertices.");

        /* Create a list of all possible edges. */
        Set<Edge> remaining = new HashSet<Edge>();
        for(int i = 0; i + 1 < v; i++) {
            for(int j = i + 1; j < v; j++) {
                remaining.add(new Edge(i, j));
            }
        }

        /* The different components of the graph. */
        List<List<Integer>> components = new ArrayList<List<Integer>>();
        for(int i = 0; i < v; i++) {
            List<Integer> component = new ArrayList<Integer>();
            component.add(i);
            components.add(component);
        }

        /* Join the components in a random way, so we create a spanning tree. */
        while(components.size() > 1) {
            /* Take two (different) random components. */
            int componentIndex0 = RANDOM.nextInt(components.size());
            int componentIndex1 = RANDOM.nextInt(components.size() - 1);
            if(componentIndex1 >= componentIndex0)
                componentIndex1++;
            List<Integer> component0 = components.get(componentIndex0);
            List<Integer> component1 = components.get(componentIndex1);

            /* Connect two random vertices from the components. */
            int v0 = component0.get(RANDOM.nextInt(component0.size()));
            int v1 = component1.get(RANDOM.nextInt(component1.size()));
            Edge edge = new Edge(v0, v1);
            addEdge(edge);
            remaining.remove(edge);

            /* Append the second component to the first, the throw it away. */
            component0.addAll(component1);
            components.remove(componentIndex1);
        }

        /* At the point we have a pretty random spanning tree. Now all we have
         * to do is randomly add some edges until we reach the required number.
         * Since our spanning tree contains v - 1 edges, we still have to add
         * e - v + 1 edges. */

        /* Start by shuffling the remaining edges. */
        List<Edge> shuffled = new ArrayList<Edge>();
        shuffled.addAll(remaining);
        Collections.shuffle(shuffled);

        /* Randomly add edges. */
        for(int i = 0; i < e - v + 1; i++)
            addEdge(shuffled.get(i));
    }

    /** Add an edge.
     *  @param edge Edge to add.
     */
    private void addEdge(Edge edge)
    {
        addEdge(edge.getV0(), edge.getV1());
    }

    /** Private class representing edges in our graph generating
     *  algorithm.
     */
    private class Edge
    {
        /** One vertex on the edge. */
        private int v0;

        /** The other vertex on the edge. */
        private int v1;

        /** Constructor.
         *  @param v0 First vertex on the edge.
         *  @param v1 Second vertex on the edge.
         */
        public Edge(int v0, int v1)
        {
            this.v0 = v0;
            this.v1 = v1;
        }

        /** Obtain the first vertex.
         *  @return The first vertex.
         */
        public int getV0()
        {
            return v0;
        }

        /** Obtain the second vertex.
         *  @return The second vertex.
         */
        public int getV1()
        {
            return v1;
        }

        @Override
        public int hashCode()
        {
            return v0 + v1;
        }

        @Override
        public boolean equals(Object other)
        {
            if(other instanceof Edge && other != null) {
                Edge edge = (Edge) other;
                return (v0 == edge.v0 && v1 == edge.v1) ||
                        (v0 == edge.v1 && v1 == edge.v0);
            } else {
                return false;
            }
        }
    }
}
