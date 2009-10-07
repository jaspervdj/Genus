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

        Set<Vertex> candidates = currentVertex.getCandidates(lastVertex);
        System.out.println();
        System.out.println("Standing in " + currentVertex.getId() +
            " " + currentVertex);
        if(lastVertex != null)
            System.out.println("Coming from " + lastVertex.getId());
        else
            System.out.println("Coming from nil");
        System.out.print("Candidates: ");
        for(Vertex candidate: candidates) {
            System.out.print(candidate.getId() + " ");
        }
        System.out.println();
            

        int max = 0, result = 0;
        for(Vertex candidate: candidates) {

            System.out.println("Going to " + candidate.getId());

            /* Connect the edge. */
            if(!currentVertex.connect(lastVertex, candidate)) {
                System.out.println("Simple fault, backtracking.");
                return 0;
            }

            /* We created a cycle. */
            if(candidate == cycleStart) {
                //GraphDotWriter writer = new GraphDotWriter(graph);
                //writer.write(currentFaces + ".dot");
                if(cycleStart.connect(currentVertex, cycleSecond)) {
                    System.out.println("Cycle closed." + cycleStart.getId() +
                        ": " + cycleStart);
                    result = findFaces(graph, null, null, null, null,
                            currentFaces + 1);
                    cycleStart.split(currentVertex, cycleSecond);
                } else {
                    System.out.println("Fault, backtracking.");
                    result = 0;
                }
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
