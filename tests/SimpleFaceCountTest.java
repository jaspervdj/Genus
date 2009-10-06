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
            System.out.println("Faces: " + finder.findFaces(
                    new DefaultGraph(graph), null, 0, 0, null));
        }
    }
}
