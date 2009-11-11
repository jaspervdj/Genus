package genus;

/** Class to make a copy of a graph.
 */
public class GraphCloner
{
    /** Create a copy of a graph.
     *  @param original Graph to clone.
     *  @return An exact clone.
     */
    public Graph clone(Graph original)
    {
        Graph graph = new GraphImplementation();

        for(int vertex: original.getVertices()) {
            for(int neighbour: original.getNeighbours(vertex)) {
                /* Only add edges once. */
                if(vertex < neighbour) {
                    graph.addEdge(vertex, neighbour);
                }
            }
        }

        return graph;
    }
}
