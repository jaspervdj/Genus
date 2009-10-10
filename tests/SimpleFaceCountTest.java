import genus.Graph;
import genus.GraphImplementation;
import genus.DefaultFindGenus;
import genus.GraphLoader;
import genus.DefaultGraph;

public class SimpleFaceCountTest
{
    private final static int TESTS = 1;

    public static void main(String[] args)
    {
        if(args.length < 1) {
            System.out.println("Please specify a filename.");
        } else {
            DefaultFindGenus finder = new DefaultFindGenus();
            Graph graph = new GraphImplementation();
            GraphLoader loader = new GraphLoader(graph);
            loader.load(args[0]);

            double averageTime = 0.0;
            int faces = 0;
            for(int i = 0; i < TESTS; i++) {
                long start = System.currentTimeMillis();
                faces = finder.findFaces(
                        new DefaultGraph(graph), null, null, null, null, 0);
                long stop = System.currentTimeMillis();
                averageTime += ((double) (stop - start)) / (double) TESTS;
            }

            System.out.println("Faces: " + faces);
            System.out.println("Average time taken: " + averageTime);
        }
    }
}
