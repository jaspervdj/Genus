package genus;
import java.util.Set;

/** An implementation of FindGenus used to examine the search tree.
 */
public class ShowTreeFindGenus extends SortedFindGenus
{
    int id;
    int[] levels;

    /** Constructor.
     */
    public ShowTreeFindGenus()
    {
    }

    @Override
    public void onRecursionStart(DefaultGraph graph)
    {
        super.onRecursionStart(graph);
        id = 0;
        levels = new int[graph.getNumberOfEdges() + 1];
    }

    @Override
    public boolean onRecurse(DefaultGraph graph, int cycleStart,
            int cycleSecond, int lastVertex, int current, int currentFaces,
            int edgesLeft, int edgesInCurrentCycle)
    {
        int level = graph.getNumberOfEdges() - edgesLeft;
        id++;
        
        int previousLevel = level - 1;
        while(previousLevel >= 0 && levels[previousLevel] < 0)
            previousLevel--;

        /* New branch. */
        if(level > 0 && levels[previousLevel] != id - 1) {
            System.out.println(levels[previousLevel] + " -- " + id +
                    " [label=\"v" + current + "\"];");
        /* Same branch. */
        } else {
            System.out.println((id - 1) + " -- " + id +
                    " [label=\"v" + current + "\"];");
        }

        levels[level] = id;

        return super.onRecurse(graph, cycleStart, cycleSecond, lastVertex,
                current, currentFaces, edgesLeft, edgesInCurrentCycle);
    }
}
