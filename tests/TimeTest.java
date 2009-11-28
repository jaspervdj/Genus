import genus.Graph;
import genus.FindGenus;
import genus.FindGenus1;
import graph.RandomGraph;
import genus.GraphCloner;
import java.util.Random;

public class TimeTest
{
    /** Number of tests to run. */
    private final static int TESTS = 10;

    /** Random generator instance. */
    private final static Random RANDOM = new Random();

    public static void main(String[] args)
    {
        if(args.length < 4) {
            System.out.println(
                    "Usage: <vertices start> <vertices step> <vertices stop> " +
                    "[<FindGenusClass> ...]");
            return;
        }

        int verticesStart = Integer.parseInt(args[0]);
        int verticesStep = Integer.parseInt(args[1]);
        int verticesStop = Integer.parseInt(args[2]);

        FindGenus finders[] = new FindGenus[args.length - 3];
        
        for(int i = 0; i < finders.length; i++) {
            try {
                Class finderClass = Class.forName(args[3 + i]);
                finders[i] = (FindGenus) finderClass.newInstance();
            } catch(Exception exception) {
                finders[i] = new FindGenus1();
            }
        }

        GraphCloner cloner = new GraphCloner();

        int vertices = verticesStart;
        while(vertices < verticesStop) {

            double[] averageTimes = new double[finders.length];
            for(int i = 0; i < TESTS; i++) {

                int edges = (vertices - 1) + RANDOM.nextInt(
                        (vertices - 1) * vertices / 2 - vertices + 2);

                Graph graph = new RandomGraph(vertices, edges);
                int genus = -1;
                for(int j = 0; j < finders.length; j++) {
                    Graph clone = cloner.clone(graph);
                    long start = System.currentTimeMillis();
                    if(genus < 0) {
                        genus = finders[j].findGenus(clone);
                    } else if(genus != finders[j].findGenus(clone)) {
                        System.out.println("Genus is not correct!");
                        System.exit(1);
                    }
                    long stop = System.currentTimeMillis();
                    averageTimes[j] += (double) (stop - start);
                }
            }

            System.out.println("(" + vertices + " vertices)");

            for(int j = 0; j < finders.length; j++) {
                averageTimes[j] /= (double) TESTS;
                System.out.println(args[3 + j] + ": " + averageTimes[j]);
            }

            vertices += verticesStep;
        }
    }
}
