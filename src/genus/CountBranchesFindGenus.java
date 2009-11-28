package genus;
import java.util.Set;

/** An implementation of FindGenus used to examine the search tree.
 */
public class CountBranchesFindGenus extends FindGenus6
{
    int branches;

    @Override
    public void onRecursionStart(DefaultGraph graph)
    {
        super.onRecursionStart(graph);
        branches = 0;
    }

    @Override
    public void onRecursionEnd(int faces)
    {
        branches++;
    }

    @Override
    public void afterRecursion()
    {
        System.out.println("Branches finished: " + branches);
    }
}
