import genus.Graph;
import genus.GraphImplementation;
import genus.SimplifyGraphProcessor;

public class SimplifyGraphProcessorTest extends UnitTest
{
    @Override
    public void run(String[] args)
    {
        /* Our processor. */
        SimplifyGraphProcessor processor = new SimplifyGraphProcessor();

        /* Build a new graph in the form of a circle. */
        Graph circle = new GraphImplementation();
        for(int i = 0; i < 100; i++)
            circle.addEdge(i, i + 1);
        circle.addEdge(0, 100);

        /* Process it. After processing, the circle should become the smallest
         * circle possible, a triangle. */
        processor.process(circle);
        test(circle.getVertices().size() == 3, "circle simplify");

        /* Build a new graph in the form of a chain. */
        Graph chain = new GraphImplementation();
        for(int i = 0; i < 100; i++)
            chain.addEdge(i, i + 1);

        /* Process it. After processing, the chain should become a very small
         * chain with only two vertices. */
        processor.process(chain);
        test(chain.getVertices().size() == 2, "chain simplify");

        /* Build a complete graph. */
        Graph k5 = new GraphImplementation();
        for(int i = 0; i < 4; i++)
            for(int j = i + 1; j < 5; j++)
                k5.addEdge(i, j);

        /* Process it. This should not change the graph, since it is very
         * compact already. */
        processor.process(k5);
        test(k5.getVertices().size() == 5, "k5 simplify");
    }
    public static void main(String[] args)
    {
        SimplifyGraphProcessorTest test = new SimplifyGraphProcessorTest();
        test.runTests(args);
    }
}
