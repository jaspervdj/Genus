package genus;

/** Interface for classes that take a graph and process it. Processing can be
 *  simplifying, optimizing...
 */
public interface GraphProcessor
{
    /** Process a graph.
     *  @param graph Graph to process.
     */
    public void process(Graph graph);
}
