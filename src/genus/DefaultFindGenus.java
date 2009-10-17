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
        return findFaces(graph, null, null, null, null, 0, 0);
    }

    /** The main recursing method used to find the number of faces in
     *  a graph.
     *  @param graph Graph to find the number of faces for.
     *  @param cycleStart Starting vertex of the current cycle.
     *  @param cycleSecond Second vertex of the current cycle.
     *  @param lastVertex Last vertex visited.
     *  @param currentVertex Current location in the graph.
     *  @param currentFaces Number of faces currently found.
     *  @param usedEdges The number of edges already used.
     *  @return the maximum number of faces in the graph.
     */
    public int findFaces(DefaultGraph graph, Vertex cycleStart,
            Vertex cycleSecond, Vertex lastVertex,
            Vertex currentVertex, int currentFaces,
            int usedEdges)
    {
        /* Simple bounding based on edges left/current number of faces. */
        if(currentFaces + 1 + (graph.getNumberOfEdges() - usedEdges - 1) / 3
                < globalMax) {
            return 0;
        }

        /* We need to start a new cycle. */
        if(currentVertex == null) {
            /* Get a random vertex with outbound edges left, and a next
             * candidate. */
            Vertex vertex = graph.getUnsaturatedVertex();

            /* End of recursion. */
            if(vertex == null) {
                if(currentFaces > globalMax)
                    globalMax = currentFaces;

                return currentFaces;
            }

            /* Continue with the random vertex. */
            Vertex candidate = graph.getVertex(vertex.getCandidate());
            return findFaces(graph, vertex, candidate, vertex,
                    candidate, currentFaces, usedEdges + 1);
        }

        /* Ask our current vertex where we can go next. */
        Set<Integer> candidates = currentVertex.getCandidates(lastVertex);

        /* Create a branch for every candidate. */
        int max = 0, result = 0;
        for(int candidateId: candidates) {

            Vertex candidate = graph.getVertex(candidateId);

            /* Connect the edge. */
            if(!currentVertex.connect(lastVertex, candidate)) {
                return currentFaces;
            }

            /* Check if we created a circle, which is the case if we are back in
             * our starting point and we can legally connect the cycle without
             * breaking any previous permutations. */
            if(candidate == cycleStart &&
                    cycleStart.connect(currentVertex, cycleSecond)) {
                /* Recurse with one more face found. */
                result = findFaces(graph, null, null, null, null,
                        currentFaces + 1, usedEdges + 1);

                /* Disconnect the cycle again. */
                cycleStart.split(currentVertex, cycleSecond);

            /* We did not create a cycle, so we just continue. */
            } else {
                if(cycleSecond == null)
                    result = findFaces(graph, cycleStart, candidate,
                            currentVertex, candidate, currentFaces,
                            usedEdges + 1);
                else
                    result = findFaces(graph, cycleStart, cycleSecond,
                            currentVertex, candidate, currentFaces,
                            usedEdges + 1);
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
