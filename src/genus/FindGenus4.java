package genus;
import java.util.Set;

/** A bounded implementation of FindGenus.
 */
public class FindGenus4 extends FindGenus2
{
    /** A lower bound for the number of faces. */
    private int previousResult;

    /** Constructor.
     */
    public FindGenus4()
    {
        previousResult = Integer.MIN_VALUE;
    }

    @Override
    public void onRecursionStart(DefaultGraph graph)
    {
        super.onRecursionStart(graph);
        previousResult = Integer.MIN_VALUE;
    }

    /** Check some bounding criteria.
     */
    @Override
    public boolean onRecurse(DefaultGraph graph, int cycleStart,
            int cycleSecond, int lastVertex, int current, int currentFaces,
            int edgesLeft, int edgesInCurrentCycle)
    {
        int girth = graph.getGirth();

        /* Minimum number of edges needed to finnish current cycle. */
        int neededInCurrent;
        if(edgesInCurrentCycle >= girth) {
            neededInCurrent = 1;
        } else {
            neededInCurrent = girth - edgesInCurrentCycle;
        }

        /* Simple bounding based on edges left/current number of faces. The
         * +1 is the cycle we're currently working on. */
        int estimate = currentFaces + 1 + (edgesLeft - neededInCurrent) / girth;

        /* If we are not going to get higher than our previous result, we can
         * bound. */
        if(estimate <= previousResult) {
            return false;
        }

        return true;
    }

    /** Keep the maximum found.
     */
    @Override
    public void onRecursionEnd(int faces)
    {
        super.onRecursionEnd(faces);
        if(faces > previousResult)
            previousResult = faces;
    }
}
