package unit;
import genus.Graph;
import genus.GraphImplementation;

/** A test class that tests our graph implementation by adding/removing some
 *  vertices and edges.
 */
public class SimpleGraphBuildTest extends UnitTest
{
    @Override
    public void run(String[] args)
    {
        Graph graph = new GraphImplementation();
        test(graph.addEdge(1, 2), "add edge 1<->2");
        test(graph.addEdge(2, 3), "add edge 2<->3");
        test(graph.removeEdge(1, 2), "remove edge 1<->2");
        test(graph.addEdge(3, 6), "add edge 3<->6");
        test(!graph.removeEdge(1, 6), "remove non-existant edge 1<-6>");
        test(graph.removeVertex(3), "remove vertex 3");
        test(!graph.removeVertex(3), "remove non-existant vertex 3");
        test(!graph.removeVertex(10), "remove non-existant vertex 10");
        test(graph.addEdge(3, 6), "add edge 3<-6>");
    }

    public static void main(String[] args)
    {
        SimpleGraphBuildTest test = new SimpleGraphBuildTest();
        test.runTests(args);
    }
}
