package unit;

import java.util.Random;
import graphviz.GraphDotWriter;
import genus.Graph;
import graph.RandomGraph;
import genus.FindGenus;
import genus.DefaultFindGenus;
import genus.BoundedFindGenus;

/** Tests the genus of a complete graph.
 */
public class RandomGraphGenusTest extends UnitTest
{
    /** Number of graphs to test. */
    private static int NUMBER_OF_TESTS = 100;

    /** Min number of vertices in the random graph. */
    private static int MIN_VERTICES = 3;

    /** Max number of vertices in the random graph. */
    private static int MAX_VERTICES = 6;

    /** Random generator instance. */
    private static Random RANDOM = new Random();

    @Override
    public void run(String[] args)
    {
        for(int i = 0; i < NUMBER_OF_TESTS; i++) {
            /* Determine number of vertices/edges. */
            int vertices = MIN_VERTICES +
                    RANDOM.nextInt(MAX_VERTICES - MIN_VERTICES);
            int minEdges = vertices - 1;
            int maxEdges = vertices * (vertices - 1) / 2;
            int edges = minEdges + RANDOM.nextInt(maxEdges - minEdges + 1);

            /* Create a random graph. */
            Graph graph = new RandomGraph(vertices, edges);

            GraphDotWriter writer = new GraphDotWriter(graph);
            writer.write("out.dot");
            
            /* Find the genus two times. */
            int genus0 = new DefaultFindGenus().findGenus(graph);
            int genus1 = new BoundedFindGenus().findGenus(graph);

            test(genus0 == genus1, "random graph with " + vertices + " and " +
                    edges + " edges");
        }
    }

    public static void main(String[] args)
    {
        RandomGraphGenusTest test = new RandomGraphGenusTest();
        test.runTests(args);
    }
}
