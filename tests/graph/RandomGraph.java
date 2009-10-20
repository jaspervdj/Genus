package graph;

import genus.Graph;
import genus.GraphImplementation;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/** A random graph.
 */
public class RandomGraph extends GraphImplementation
{
    private class Edge
    {
        public int v1;
        public int v2;

        public Edge(int v1, int v2)
        {
            this.v1 = v1;
            this.v2 = v2;
        }
    }

    /** Constructor.
     *  @param v Maximum number of vertices.
     *  @param e Number of edges.
     */
    public RandomGraph(int v, int e)
    {
        super();

        List<Edge> all = new ArrayList<Edge>();

        /* Add all possible edges to a list. */
        for(int i = 0; i + 1 < v; i++) {
            for(int j = i + 1; j < v; j++) {
                all.add(new Edge(i, j));
            }
        }

        Collections.shuffle(all);

        for(int i = 0; i < e && i < all.size(); i++) {
            addEdge(all.get(i).v1, all.get(i).v2);
        }
    }
}
