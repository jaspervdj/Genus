import genus.Graph;
import genus.DefaultFindGenus;
import genus.BoundedFindGenus;
import genus.DefaultGraph;
import graph.ZGraph;
import graph.RandomGraph;

public class SimpleFaceCountTest
{
    /** Number of tests to run. */
    private final static int TESTS = 10;

    public static void main(String[] args)
    {
        DefaultFindGenus finder = new BoundedFindGenus();
        Graph graph;

        if(args.length < 1) {
            graph = new RandomGraph(7, 18);
        } else {
            graph = new ZGraph(args[0]);
        }

        double averageTime = 0.0;
        int faces = 0;
        for(int i = 0; i < TESTS; i++) {
            long start = System.currentTimeMillis();
            DefaultGraph defaultGraph = new DefaultGraph(graph);
            faces = finder.findFaces(defaultGraph);
            long stop = System.currentTimeMillis();
            System.out.println("Completeness: " + defaultGraph.completeness());
            System.out.println("Girth: " + defaultGraph.getGirth());
            System.out.println("Estimate faces: " + defaultGraph.estimate());
            System.out.println("Faces: " + faces);
            System.out.println("Genus: " +
                    finder.findGenus(defaultGraph, faces));
            System.out.println("Time " + (i + 1) + ": " +
                    (stop - start) + "ms.");
            averageTime += ((double) (stop - start)) / (double) TESTS;
        }

        System.out.println();
        System.out.println("Average time: " + averageTime);
    }
}
