package unit;

import genus.Graph;
import graph.CompleteBipartiteGraph;
import genus.FindGenus;
import genus.DefaultFindGenus;

/** Tests the genus of a complete graph.
 */
public class CompleteBipartiteGraphGenusTest extends UnitTest
{
    /** Max number of vertices on one side of the bipartite graph. */
    private static int MAX_N = 4;

    @Override
    public void run(String[] args)
    {
        for(int n = 2; n <= MAX_N; n++) {
            for(int m = n; m <= MAX_N; m++) {
                /* Create a complete bipartite graph. */
                Graph graph = new CompleteBipartiteGraph(n, m);
                
                /* Find the genus. */
                FindGenus finder = new DefaultFindGenus();
                int foundGenus = finder.findGenus(graph);

                /* Calculate the genus. */
                int genus = ((n - 2) * (m - 2) + 3) / 4;

                /* Test. */
                test(foundGenus == genus, "genus of k" + n + ", " + m);
            }
        }
    }

    public static void main(String[] args)
    {
        CompleteBipartiteGraphGenusTest test =
                new CompleteBipartiteGraphGenusTest();
        test.runTests(args);
    }
}
