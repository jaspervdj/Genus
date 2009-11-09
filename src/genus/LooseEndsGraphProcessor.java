package genus;
import java.util.List;
import java.util.ArrayList;

/** A class that simplifies a graph by removing loose ends.
 */
public class LooseEndsGraphProcessor implements GraphProcessor
{
    /** Constructor.
     */
    public LooseEndsGraphProcessor()
    {
    }

    @Override
    public void process(Graph graph)
    {
        boolean shouldContinue = true;

        List<Integer> vertices = graph.getVertices();
        while(vertices.size() >= 3 && shouldContinue) {
            shouldContinue = false;

            for(int vertex: vertices) {
                if(graph.getNeighbours(vertex).size() == 1) {
                    graph.removeVertex(vertex);
                    shouldContinue = true;
                }
            }

            vertices = graph.getVertices();
        }
    }
}
