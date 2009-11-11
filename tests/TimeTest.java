import genus.Graph;
import genus.FindGenus;
import genus.DefaultFindGenus;
import graph.RandomGraph;
import genus.GraphCloner;

public class TimeTest
{
    /** Number of tests to run. */
    private final static int TESTS = 200;

    public static void main(String[] args)
    {
        if(args.length < 3) {
            System.out.println(
                    "Usage: <vertices> <edges> [<FindGenusClass> ...]");
        }

        int vertices = Integer.parseInt(args[0]);
        int edges = Integer.parseInt(args[1]);

        FindGenus finders[] = new FindGenus[args.length - 2];
        
        for(int i = 0; i < finders.length; i++) {
            try {
                Class finderClass = Class.forName(args[2 + i]);
                finders[i] = (FindGenus) finderClass.newInstance();
            } catch(Exception exception) {
                finders[i] = new DefaultFindGenus();
            }
        }

        GraphCloner cloner = new GraphCloner();

        for(int i = 0; i < TESTS; i++) {
            System.out.println("Test " + (i + 1));
            Graph graph = new RandomGraph(vertices, edges);
            for(int j = 0; j < finders.length; j++) {
                Graph clone = cloner.clone(graph);
                long start = System.currentTimeMillis();
                finders[j].findGenus(clone);
                long stop = System.currentTimeMillis();
                System.out.println(args[j + 2] + ": " + (stop - start));
            }
        }
    }
}
