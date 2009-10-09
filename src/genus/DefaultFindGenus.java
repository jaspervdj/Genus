package genus;
import java.util.Set;

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
        return findFaces(graph, null, null, null, null, 0);
    }

    public int findFaces(DefaultGraph graph, Vertex cycleStart,
            Vertex cycleSecond,
            Vertex lastVertex, Vertex currentVertex, int currentFaces)
    {
        if(currentVertex == null) {
            /* Get a random vertex with outbound edges left. */
            Vertex vertex = graph.getUnsaturatedVertex();

            /* End of recursion. */
            if(vertex == null) {
                return currentFaces;
            }

            /* Continue with the random vertex. */
            return findFaces(graph, vertex, null, null, vertex, currentFaces);
        }

        Set<Integer> candidates = currentVertex.getCandidates(lastVertex);

        int max = 0, result = 0;
        for(int candidateId: candidates) {

            Vertex candidate = graph.getVertex(candidateId);
            //System.out.println("Going to " + candidate.getId());

            /* Connect the edge. */
            if(!currentVertex.connect(lastVertex, candidate)) {
                return currentFaces;
            }

            /* We created a cycle. */
            if(candidate == cycleStart &&
                    cycleStart.connect(currentVertex, cycleSecond)) {
                result = findFaces(graph, null, null, null, null,
                        currentFaces + 1);
                cycleStart.split(currentVertex, cycleSecond);
            /* We just continue. */
            } else {
                if(cycleSecond == null)
                    result = findFaces(graph, cycleStart, candidate,
                            currentVertex, candidate, currentFaces);
                else
                    result = findFaces(graph, cycleStart, cycleSecond,
                            currentVertex, candidate, currentFaces);
            }

            if(result > max)
                max = result;

            /* Reset the edge. */
            currentVertex.split(lastVertex, candidate);
        }

        /* Return the solution with the most cycles. */
        return max;
    }
}
