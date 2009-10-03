import genus.SimpleGraph;
import genus.SimpleFindGenus;
import genus.GraphLoader;

public class SimpleFaceCountTest
{
    public static void main(String[] args)
    {
        if(args.length < 1) {
            System.out.println("Please specify a filename.");
        } else {
            SimpleFindGenus finder = new SimpleFindGenus();
            SimpleGraph graph = new SimpleGraph();
            GraphLoader loader = new GraphLoader(graph);
            loader.load(args[0]);
            System.out.println("Faces: " + finder.findFaces(graph));
        }
    }
}
