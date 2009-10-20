import genus.Graph;
import genus.DefaultFindGenus;
import genus.DefaultGraph;
import graph.ZGraph;

public class SimpleFaceCountTest
{
    private final static int TESTS = 100;

    public static void main(String[] args)
    {
        if(args.length < 1) {
            System.out.println("Please specify a filename.");
        } else {
            DefaultFindGenus finder = new DefaultFindGenus();
            Graph graph = new ZGraph(args[0]);

            double averageTime = 0.0;
            int faces = 0;
            for(int i = 0; i < TESTS; i++) {
                long start = System.currentTimeMillis();
                faces = finder.findFaces(new DefaultGraph(graph));
                long stop = System.currentTimeMillis();
                System.out.println("Run " + (i + 1) + ": " +
                        (stop - start) + "ms, " + faces + " faces.");
                averageTime += ((double) (stop - start)) / (double) TESTS;
            }

            System.out.println();
            System.out.println("Average time: " + averageTime);
        }
    }
}
