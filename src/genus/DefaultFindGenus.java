package genus;

/** The default implementation of the FindGenus interface.
 */
public class DefaultFindGenus implements FindGenus
{
    @Override
    public int findGenus(Graph graph)
    {
        return findGenus(new DefaultGraph(graph));
    }

    /** Find the genus of a DefaultGraph.
     *  @param graph Graph to find the genus for.
     *  @return The graph genus.
     */
    public int findGenus(DefaultGraph graph)
    {
        return 4;
    }

    public int findGenus(DefaultGraph graph, int label)
    {
        /* Obtain the last edge/point.
           If none, take a random vertex with outbound edges left. */

        /* If there is only one outbound edge, take that as next edge.
         * Otherwise, choose a non-returning and if possible non-double edge.
         * For all these edges: */

                /* Label the edge. */

                /* Detect if we closed a cycle and recurse. */

                /* Reset the edge. */

        /* Return the solution with the most cycles. */
        return 5;
    }
}
