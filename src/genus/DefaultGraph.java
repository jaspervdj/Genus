package genus;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/** A default graph, to be used with DefaultFindGenus.
 */
public class DefaultGraph
{
    /** The vertices, stored by mapped id. */
    private Vertex[] vertices;

    /** Total number of edges. */
    private int numberOfEdges;

    /** Constructor. Construct a graph from any class implementing the Graph
     *  interface.
     *  @param graph Graph to construct the DefaultGraph from.
     */
    public DefaultGraph(Graph graph)
    {
        /* Process the graph first. */
        GraphProcessor processor = new SimplifyGraphProcessor();
        processor.process(graph);

        /* A map to translate the labels given by the input to a 0..n numbering,
         * so we can use it in an array more easily. */
        Map<Integer, Integer> translation = new HashMap<Integer, Integer>();
        vertices = new Vertex[graph.getVertices().size()];

        /* Build the translation map and store the vertices.. */
        int index = 0;
        for(int id: graph.getVertices()) {
            translation.put(id, index);
            vertices[index] = new Vertex(index);
            index++;
        }

        /* Set the neighbours. */
        for(int id: graph.getVertices()) {
            Vertex vertex = vertices[translation.get(id)];
            ArrayList<Vertex> neighbours = new ArrayList<Vertex>();

            for(int neighbourId: graph.getNeighbours(id)) {
                Vertex neighbour = vertices[translation.get(neighbourId)];
                neighbours.add(neighbour);
                numberOfEdges++;
            }

            vertex.setNeighbours(neighbours);
        }
    }

    /** Get the number of vertices in the graph.
     *  @return The number of vertices in the graph.
     */
    public int getNumberOfVertices()
    {
        return vertices.length;
    }

    /** Get a random vertex with candidates left.
     *  @return A random vertex with candidates left.
     */
    public Vertex getVertexWithCandidates()
    {
        for(Vertex vertex: vertices) {
            if(vertex.hasCandidates())
                return vertex;
        }

        return null;
    }

    /** Get a vertex by id.
     *  @param id Id of the requested vertex.
     *  @return The requested vertex.
     */
    public Vertex getVertex(int id)
    {
        return vertices[id];
    }

    /** Get the number of edges in the graph.
     *  @return The number of edges in the graph.
     */
    public int getNumberOfEdges()
    {
        return numberOfEdges;
    }
}
