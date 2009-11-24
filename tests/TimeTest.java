import genus.Graph;
import genus.FindGenus;
import genus.DefaultFindGenus;
import graph.RandomGraph;
import genus.GraphCloner;

public class TimeTest
{
    /** Number of tests to run. */
    private final static int TESTS = 20;

    public static void main(String[] args)
    {
        if(args.length < 7) {
            System.out.println(
                    "Usage: <vertices start> <vertices step> <vertices stop> " +
                    "<edges start> <edges step> <edges stop> " +
                    "[<FindGenusClass> ...]");
            return;
        }

        int verticesStart = Integer.parseInt(args[0]);
        int verticesStep = Integer.parseInt(args[1]);
        int verticesStop = Integer.parseInt(args[2]);
        int edgesStart = Integer.parseInt(args[3]);
        int edgesStep = Integer.parseInt(args[4]);
        int edgesStop = Integer.parseInt(args[5]);

        FindGenus finders[] = new FindGenus[args.length - 6];
        
        for(int i = 0; i < finders.length; i++) {
            try {
                Class finderClass = Class.forName(args[6 + i]);
                finders[i] = (FindGenus) finderClass.newInstance();
            } catch(Exception exception) {
                finders[i] = new DefaultFindGenus();
            }
        }

        GraphCloner cloner = new GraphCloner();

        int vertices = verticesStart, edges = edgesStart;
        while(vertices < verticesStop && edges < edgesStop) {

            double[] averageTimes = new double[finders.length];
            for(int i = 0; i < TESTS; i++) {
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

            System.out.println("(" + vertices + " vertices, " + edges +
                    " edges)");

            for(int j = 0; j < finders.length; j++) {
                averageTimes[j] /= (double) TESTS;
                System.out.println(args[6 + j] + ": " + averageTimes[j]);
            }

            vertices += verticesStep;
            edges += edgesStep;
        }
    }
}
