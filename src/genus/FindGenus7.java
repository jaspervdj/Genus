package genus;
import java.util.Set;

/** A sorted implementation of FindGenus.
 */
public class FindGenus7 extends FindGenus6
{
    @Override
    public int findGenus(Graph graph)
    {
        return findGenus(new SortedGraph(graph));
    }
}
