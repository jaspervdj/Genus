package genus;
import java.util.Set;

/** A bounded implementation of FindGenus.
 */
public class BoundedFindGenus extends DefaultFindGenus
{
    /** A lower bound for the number of faces. */
    private int lowerBound;

    /** Constructor.
     */
    public BoundedFindGenus()
    {
        lowerBound = 0;
    }

    @Override
    public void onRecursionStart()
    {
        lowerBound = 0;
    }

    /** Check some bounding criteria.
     */
    @Override
    public boolean onRecurse(DefaultGraph graph, int cycleStart,
            int cycleSecond, int lastVertex, int current, int currentFaces,
            int edgesLeft, int edgesInCurrentCycle)
    {
        /* Simple bounding based on edges left/current number of faces. */
        int estimate = currentFaces + (edgesLeft +
                (edgesInCurrentCycle > 2 ? 2 : edgesInCurrentCycle)) / 3;

        if(estimate <= lowerBound) {
            return false;
        }

        float depth = (float) edgesLeft / (float) graph.getNumberOfEdges();
        if(lowerBound >= 0 && current < 0 &&
                estimate * 0.8f <= lowerBound && depth >= 0.8f) {
            if(graph.estimate() <= lowerBound) {
                return false;
            }
        }

        return true;
    }

    /** Keep the maximum found.
     */
    @Override
    public void onRecursionEnd(int faces)
    {
        if(faces > lowerBound)
            lowerBound = faces;
    }
}
