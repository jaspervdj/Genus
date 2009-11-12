import genus.Graph;
import genus.DefaultFindGenus;
import genus.BoundedFindGenus;
import genus.SortedFindGenus;
import genus.DefaultGraph;
import graphviz.GraphDotWriter;
import graph.ZGraph;
import graph.RandomGraph;

public class SimpleFaceCountTest
{
    /** Number of tests to run. */
    private final static int TESTS = 1;

    public static void main(String[] args)
    {
        DefaultFindGenus finder = new SortedFindGenus();
        Graph graph;

        double averageTime = 0.0;
        int faces = 0;
        for(int i = 0; i < TESTS; i++) {
            if(args.length < 1) {
                graph = new RandomGraph(10, 30);
            } else {
                graph = new ZGraph(args[0]);
            }
            GraphDotWriter writer = new GraphDotWriter(graph);

            long start = System.currentTimeMillis();
            //DefaultGraph defaultGraph = new DefaultGraph(graph);
            //faces = finder.findFaces(defaultGraph);
            finder.findGenus(graph);
            long stop = System.currentTimeMillis();

            //System.out.println("Girth: " + defaultGraph.getGirth());
            //System.out.println("Estimate faces: " + defaultGraph.estimate());
            System.out.println("Faces: " + faces);
            //System.out.println("Genus: " +
                    //finder.findGenus(defaultGraph, faces));
            System.out.println("Time " + (i + 1) + ": " +
                    (stop - start) + "ms.");

            averageTime += ((double) (stop - start)) / (double) TESTS;
        }

        System.out.println();
        System.out.println("Average time: " + averageTime);
    }
}
