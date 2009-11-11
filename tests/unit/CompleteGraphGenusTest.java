package unit;

import genus.Graph;
import graph.CompleteGraph;
import genus.FindGenus;
import genus.DefaultFindGenus;
import genus.BoundedFindGenus;

/** Tests the genus of a complete graph.
 */
public class CompleteGraphGenusTest extends UnitTest
{
    /** Max complete graph to test. */
    private static int MAX_N = 9;

    @Override
    public void run(String[] args)
    {
        for(int n = 3; n <= MAX_N; n++) {
            /* Create a complete graph. */
            Graph graph = new CompleteGraph(n);
            
            /* Find the genus. */
            FindGenus finder = new BoundedFindGenus();
            int foundGenus = finder.findGenus(graph);

            /* Calculate the genus. */
            int genus = ((n - 3) * (n - 4) + 11) / 12;

            /* Test. */
            test(foundGenus == genus, "genus of k" + n);
        }
    }

    public static void main(String[] args)
    {
        CompleteGraphGenusTest test = new CompleteGraphGenusTest();
        test.runTests(args);
    }
}
