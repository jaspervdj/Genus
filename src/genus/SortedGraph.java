package genus;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;

/** A sorted graph, to be used with DefaultFindGenus.
 */
public class SortedGraph extends DefaultGraph
{
    /** Constructor.
     *  @param graph Graph to create.
     */
    public SortedGraph(final Graph graph)
    {
        super(graph);
    }

    @Override
    public void sort(List<Integer> vertices, final Graph graph)
    {
        Collections.sort(vertices,
            new Comparator() {
                public int compare(Object o0, Object o1) {
                    return graph.getNeighbours((Integer) o0).size() -
                            graph.getNeighbours((Integer) o1).size();
                }
            }
        );
    }
}
