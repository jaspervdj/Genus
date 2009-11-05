package genus;
import java.util.Set;

/** The default implementation of the FindGenus interface.
 */
public class DefaultFindGenus implements FindGenus
{
    /** The current max number of faces found. */
    private int globalMax;

    public DefaultFindGenus()
    {
        globalMax = 0;
    }

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
        int faces = findFaces(graph);
        return 1 - (graph.getNumberOfVertices() + faces -
                graph.getNumberOfEdges() / 2 ) / 2;
    }

    /** Find the maximum number of faces in a graph.
     *  @param graph Graph to determine the number of faces for.
     *  @return The maximum number of faces in the graph.
     */
    public int findFaces(DefaultGraph graph)
    {
        globalMax = 0;
        return findFaces(graph, null, null, null, null,
                0, graph.getNumberOfEdges(), 0);
    }

    /** The main recursing method used to find the number of faces in
     *  a graph.
     *  @param graph Graph to find the number of faces for.
     *  @param cycleStart Starting vertex of the current cycle.
     *  @param cycleSecond Second vertex of the current cycle.
     *  @param lastVertex Last vertex visited.
     *  @param currentVertex Current location in the graph.
     *  @param currentFaces Number of faces currently found.
     *  @param edgesLeft Edges left in the graph.
     *  @param edgesInCurrentCycle Number of edges used in the cumber cycle.
     *  @return the maximum number of faces in the graph.
     */
    public int findFaces(DefaultGraph graph, Vertex cycleStart,
            Vertex cycleSecond, Vertex lastVertex,
            Vertex currentVertex, int currentFaces,
            int edgesLeft, int edgesInCurrentCycle)
    {
        /* Simple bounding based on edges left/current number of faces. */
        int estimate = currentFaces + (edgesLeft +
                (edgesInCurrentCycle > 2 ? 2 : edgesInCurrentCycle)) / 3;

        if(estimate <= globalMax) {
            /* Since we're not going to get any result larger than our maximum,
             * it doesn't really matter what we return. */
            return 0;
        }

        float depth = (float) edgesLeft / (float) graph.getNumberOfEdges();
        if(globalMax >= 0 && currentVertex == null &&
                estimate * 0.9f <= globalMax && depth >= 0.7f) {
            if(graph.estimate() <= globalMax) {
                return 0;
            }
        }

        /* We need to start a new cycle. */
        if(currentVertex == null) {
            /* Get a random vertex with outbound edges left, and a next
             * candidate. */
            Vertex vertex = graph.getVertexWithCandidates();

            /* End of recursion. */
            if(vertex == null) {
                /* Update our maximum. */
                if(currentFaces + 1 > globalMax)
                    globalMax = currentFaces + 1;

                return currentFaces;
            }

            /* Continue with the random vertex. */
            Vertex candidate = graph.getVertex(vertex.getCandidate());
            return findFaces(graph, vertex, candidate, vertex,
                    candidate, currentFaces, edgesLeft - 1, 1);
        }

        /* Ask our current vertex where we can go next. */
        Set<Integer> candidates = currentVertex.getCandidates(lastVertex);

        /* Create a branch for every candidate. */
        int max = 0, result = 0;
        for(int candidateId: candidates) {

            Vertex candidate = graph.getVertex(candidateId);

            /* Connect the edge if this fails, we made an illegal move somewhere
             * in our algorithm, so we backtrack. To check: we can assume it
             * will always connect, so we can safely skip this check. */
            if(!currentVertex.connect(lastVertex, candidate)) {
                System.out.println("Illegal move.");
                return 0;
            }

            /* Check if we created a circle, which is the case if we are back in
             * our starting point and we can legally connect the cycle without
             * breaking any previous permutations. */
            if(candidate == cycleStart &&
                    cycleStart.connect(currentVertex, cycleSecond)) {

                /* Recurse with one more face found. */
                result = findFaces(graph, null, null, null, null,
                        currentFaces + 1, edgesLeft - 1, 0);

                /* Disconnect the cycle again. */
                cycleStart.split(currentVertex, cycleSecond);

            /* We did not create a cycle, so we just continue. */
            } else {
                result = findFaces(graph, cycleStart,
                        cycleSecond == null ? candidate : cycleSecond,
                        currentVertex, candidate, currentFaces,
                        edgesLeft - 1, edgesInCurrentCycle + 1);
            }

            /* We're only interested in the maximum number of faces we can find
             * in the graph. */
            if(result > max)
                max = result;

            /* Disconnect the cycle again. */
            currentVertex.split(lastVertex, candidate);
        }

        /* Return the solution with the most cycles. */
        return max;
    }
}
