import genus.Graph;
import genus.FindGirth;
import graph.RandomGraph;
import genus.DefaultGraph;
import java.util.Random;

public class GirthTimeTest
{
    /** Number of tests to run. */
    private final static int TESTS = 100;

    /** Random generator instance. */
    private final static Random RANDOM = new Random();

    public static void main(String[] args)
    {
        if(args.length < 3) {
            System.out.println(
                    "Usage: <vertices start> <vertices step> <vertices stop> ");
            return;
        }

        int verticesStart = Integer.parseInt(args[0]);
        int verticesStep = Integer.parseInt(args[1]);
        int verticesStop = Integer.parseInt(args[2]);

        FindGirth finder = new FindGirth();
        
        int vertices = verticesStart;
        while(vertices < verticesStop) {

            double averageTime = 0.0;
            for(int i = 0; i < TESTS; i++) {

                int edges = (vertices - 1) + RANDOM.nextInt(
                        (vertices - 1) * vertices / 2 - vertices + 2);

                DefaultGraph graph =
                        new DefaultGraph(new RandomGraph(vertices, edges));
                int genus = -1;
                long start = System.currentTimeMillis();
                genus = finder.findGirth(graph);
                long stop = System.currentTimeMillis();
                averageTime += (double) (stop - start);
            }

            System.out.println("(" + vertices + " vertices)");
            averageTime /= (double) TESTS;
            System.out.println(": " + averageTime);

            vertices += verticesStep;
        }
    }
}
