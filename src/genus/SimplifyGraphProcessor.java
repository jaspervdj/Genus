package genus;
import java.util.List;
import java.util.ArrayList;

/** A class that simplifies a graph so finding the genus can happen more
 *  efficiently.
 */
public class SimplifyGraphProcessor implements GraphProcessor
{
    /** Constructor.
     */
    public SimplifyGraphProcessor()
    {
    }

    @Override
    public void process(Graph graph)
    {
        /* Vertices that can be thrown away safely. */
        List<Integer> garbage = new ArrayList<Integer>();

        /* Loop through all vertices. */
        for(int vertex: graph.getVertices()) {
            List<Integer> neighbours = graph.getNeighbours(vertex);

            /* Detect vertices with only 2 neighbours. Only add an edge when
             * there is no edge present yet. */
            if(neighbours.size() == 2 &&
                    !neighbours.get(0).equals(neighbours.get(1)) &&
                    graph.addEdge(neighbours.get(0), neighbours.get(1))) {
                /* Remove the old edges. */
                graph.removeEdge(vertex, neighbours.get(1));
                graph.removeEdge(neighbours.get(0), vertex);
                garbage.add(vertex);
            }
        }

        /* Remove the garbage. */
        for(int vertex: garbage) {
            graph.removeVertex(vertex);
        }
    }
}
