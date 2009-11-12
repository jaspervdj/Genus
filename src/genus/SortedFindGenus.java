package genus;
import java.util.Set;

/** A sorted implementation of FindGenus.
 */
public class SortedFindGenus extends BoundedFindGenus
{
    @Override
    public int findGenus(Graph graph)
    {
        return findGenus(new SortedGraph(graph));
    }
}
