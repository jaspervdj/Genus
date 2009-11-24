import genus.Graph;
import genus.DefaultFindGenus;
import genus.BoundedFindGenus;
import genus.SortedFindGenus;
import genus.DefaultGraph;
import genus.ShowTreeFindGenus;
import writers.GraphDotWriter;
import graph.ZGraph;
import graph.RandomGraph;
import genus.GraphCloner;

public class SimpleFaceCountTest
{
    /** Number of tests to run. */
    private final static int TESTS = 10;

    public static void main(String[] args)
    {
        DefaultFindGenus finder;
        try {
            Class finderClass = Class.forName(args[0]);
            finder = (DefaultFindGenus) finderClass.newInstance();
        } catch(Exception exception) {
            finder = new DefaultFindGenus();
        }

        double averageTime = 0.0;
        int faces = 0;
        for(int i = 0; i < TESTS; i++) {
            Graph graph = new ZGraph(args[1]);

            long start = System.currentTimeMillis();
            //DefaultGraph defaultGraph = new DefaultGraph(graph);
            //faces = finder.findFaces(defaultGraph);
            int genus = finder.findGenus(graph);
            long stop = System.currentTimeMillis();

            //System.out.println("Girth: " + defaultGraph.getGirth());
            //System.out.println("Estimate faces: " + defaultGraph.estimate());
            //System.out.println("Faces: " + faces);
            System.out.println("Genus: " + genus);
            System.out.println("Time " + (i + 1) + ": " +
                    (stop - start) + "ms.");

            averageTime += ((double) (stop - start)) / (double) TESTS;
        }

        System.out.println();
        System.out.println("Average time: " + averageTime);
    }
}
