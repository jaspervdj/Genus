package genus;
import java.util.Set;

/** A implementation of FindGenus that does some preprocessing.
 */
public class FindGenus2 extends FindGenus1
{
    /** Constructor.
     */
    public FindGenus2()
    {
    }

    @Override
    public int findGenus(Graph graph)
    {
        GraphProcessor processor;

        processor = new SimplifyGraphProcessor();
        processor.process(graph);

        return super.findGenus(graph);
    }
}
