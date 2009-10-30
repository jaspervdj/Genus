package graph;

import genus.Graph;
import genus.GraphImplementation;

/** A complete bipartite graph.
 */
public class CompleteBipartiteGraph extends GraphImplementation
{
    /** Constructor.
     *  @param n Vertices on one side of the complete bipartite graph.
     *  @param m Vertices on the other side of the complete bipartite graph.
     */
    public CompleteBipartiteGraph(int n, int m)
    {
        super();

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                addEdge(i, n + j);
            }
        }
    }
}
