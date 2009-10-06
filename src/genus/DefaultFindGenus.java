package genus;
import java.util.List;

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
        return findFaces(graph, null, 0, 0, null);
    }

    public int findFaces(DefaultGraph graph, Vertex lastVertex,
            int currentLabel, int currentFaces, Edge lastEdge)
    {
        System.out.println(graph.getNumberOfUnlabeledEdges() + " edges left.");

        if(lastVertex == null) {
            /* Get a random vertex with outbound edges left. */
            Vertex vertex = graph.getOutboundVertex();

            /* End of recursion. */
            if(vertex == null)
                return currentFaces;

            /* Continue with the random vertex. */
            return findFaces(graph, vertex, currentLabel + 1,
                    currentFaces, null);
        }

        List<Edge> candidates = lastVertex.getNextCandidates(
                currentLabel, lastEdge);

        /* No more candidates... */
        if(candidates.isEmpty()) {
            return findFaces(graph, null, currentLabel, currentFaces, null);
        }

        int max = 0, result;
        for(Edge candidate: candidates) {

            /* The next vertex. */
            Vertex vertex = candidate.getEnd();

            boolean addCycle = vertex.hasOutbound(currentLabel) &&
                    !vertex.hasInbound(currentLabel);

            /* Label the edge. */
            candidate.setLabel(currentLabel);

            /* We created a cycle. */
            if(addCycle) {
                GraphDotWriter writer = new GraphDotWriter(graph);
                writer.write(currentFaces + ".dot");
                result = findFaces(graph, null, currentLabel,
                        currentFaces + 1, candidate);
            /* We just continue. */
            } else {
                result = findFaces(graph, vertex, currentLabel,
                        currentFaces, candidate);
            }

            if(result > max)
                max = result;

            /* Reset the edge. */
            candidate.clearLabel();
        }

        /* Return the solution with the most cycles. */
        return max;
    }
}
