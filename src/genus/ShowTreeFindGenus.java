package genus;
import java.util.Set;

/** An implementation of FindGenus used to examine the search tree.
 */
public class ShowTreeFindGenus extends DefaultFindGenus
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
            System.out.println(levels[previousLevel] + " -- " + id + ";");
        /* Same branch. */
        } else {
            System.out.println((id - 1) + " -- " + id + ";");
        }

        levels[level] = id;

        int indent = graph.getNumberOfEdges() - edgesLeft;
        for(int i = 0; i < indent; i++)
            System.out.print(" ");
        System.out.println(current);
        return super.onRecurse(graph, cycleStart, cycleSecond, lastVertex,
                current, currentFaces, edgesLeft, edgesInCurrentCycle);
    }
}
