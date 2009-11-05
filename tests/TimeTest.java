import genus.Graph;
import genus.DefaultFindGenus;
import genus.DefaultGraph;
import graph.RandomGraph;

public class TimeTest
{
    /** Number of tests to run. */
    private final static int TESTS = 200;

    public static void main(String[] args)
    {
        DefaultFindGenus finder = new DefaultFindGenus();
        Graph graph;

        int vertices = 10;
        int edges = 30;
        if(args.length >= 2) {
            vertices = Integer.parseInt(args[0]);
            edges = Integer.parseInt(args[1]);
        }

        for(int i = 0; i < TESTS; i++) {
            long start = System.currentTimeMillis();
            graph = new RandomGraph(vertices, edges);
            DefaultGraph defaultGraph = new DefaultGraph(graph);
            int faces = finder.findFaces(defaultGraph);
            long stop = System.currentTimeMillis();
            System.out.println(i + " " + (stop - start));
        }
    }
}
