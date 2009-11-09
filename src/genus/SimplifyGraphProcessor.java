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
        boolean shouldContinue = true;

        List<Integer> vertices = graph.getVertices();
        List<Integer> garbage = new ArrayList<Integer>();
        while(vertices.size() > 2 && shouldContinue) {
            shouldContinue = false;

            for(int vertex: vertices) {
                List<Integer> neighbours = graph.getNeighbours(vertex);
                if(neighbours.size() == 1) {
                    garbage.add(vertex);
                    shouldContinue = true;
                } else if(neighbours.size() == 2) {
                    /* Next statement might fail. */
                    graph.addEdge(neighbours.get(0), neighbours.get(1));
                    garbage.add(vertex);
                    shouldContinue = true;
                }
            }

            /* Decide how many vertices we're going to remove. We can't leave
             * an empty graph, of course. */
            int toRemove = vertices.size() - garbage.size() < 2 ?
                    garbage.size() - 2 : garbage.size();
            
            for(int i = 0; i < toRemove; i++) {
                graph.removeVertex(garbage.get(i));
            }

            garbage.clear();

            vertices = graph.getVertices();
        }
    }
}
