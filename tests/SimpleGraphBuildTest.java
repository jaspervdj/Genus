import genus.Graph;
import genus.GraphImplementation;

public class SimpleGraphBuildTest
{
    public static void main(String[] args)
    {
        Graph graph = new GraphImplementation();
        assert(graph.addEdge(1, 2));
        assert(graph.addEdge(2, 3));
        assert(graph.removeEdge(1, 2));
        assert(graph.addEdge(3, 6));
        assert(!graph.removeEdge(1, 6));
        assert(graph.removeVertex(3));
        assert(!graph.removeVertex(3));
        assert(!graph.removeVertex(10));
        assert(graph.addEdge(3, 6));
    }
}
