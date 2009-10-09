import genus.Graph;
import genus.GraphImplementation;
import genus.DefaultFindGenus;
import genus.GraphLoader;
import genus.DefaultGraph;

public class SimpleFaceCountTest
{
    public static void main(String[] args)
    {
        if(args.length < 1) {
            System.out.println("Please specify a filename.");
        } else {
            DefaultFindGenus finder = new DefaultFindGenus();
            Graph graph = new GraphImplementation();
            GraphLoader loader = new GraphLoader(graph);
            loader.load(args[0]);
            long start = System.currentTimeMillis();
            int faces = finder.findFaces(
                    new DefaultGraph(graph), null, null, null, null, 0);
            long stop = System.currentTimeMillis();
            System.out.println("Faces: " + faces);
            System.out.println("Time taken: " + (stop - start));
        }
    }
}
