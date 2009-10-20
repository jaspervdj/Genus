package graph;

import genus.Graph;
import genus.GraphImplementation;

/** A complete graph.
 */
public class CompleteGraph extends GraphImplementation
{
    /** Constructor.
     *  @param n Vertices in the complete graph.
     */
    public CompleteGraph(int n)
    {
        super();

        for(int i = 0; i + 1 < n; i++) {
            for(int j = i + 1; j < n; j++) {
                addEdge(i, j);
            }
        }
    }
}
