package genus;
import java.util.Set;

/** A implementation of FindGenus that does some preprocessing.
 */
public class ProcessedFindGenus extends DefaultFindGenus
{
    /** Constructor.
     */
    public ProcessedFindGenus()
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
