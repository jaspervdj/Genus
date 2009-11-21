package genus;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;

/** A default graph, to be used with DefaultFindGenus.
 */
public class DefaultGraph
{
    /** The vertices, stored by mapped id. */
    private Vertex[] vertices;

    /** Total number of edges. */
    private int numberOfEdges;

    /** Orders for the edges. */
    private Order[][] orders;

    /** Girth of this graph. */
    public int girth;

    /** Constructor. Construct a graph from any class implementing the Graph
     *  interface.
     *  @param graph Graph to construct the DefaultGraph from.
     */
    public DefaultGraph(final Graph graph)
    {
        /* A map to translate the labels given by the input to a 0..n numbering,
         * so we can use it in an array more easily. */
        Map<Integer, Integer> translation = new HashMap<Integer, Integer>();
        vertices = new Vertex[graph.getVertices().size()];

        orders = new Order[vertices.length][vertices.length];

        /* Sort the vertices to have less top-level branches. */
        List<Integer> sortedVertices = graph.getVertices();
        sort(sortedVertices, graph);

        /* Build the translation map and store the vertices.. */
        int index = 0;
        for(int id: sortedVertices) {
            translation.put(id, index);
            vertices[index] = new Vertex(index, orders);
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

        /* Compute the girth of this graph. */
        FindGirth findGirth = new FindGirth();
        girth = findGirth.findGirth(this);
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
    public int getVertexWithCandidates()
    {
        for(int i = 0; i < vertices.length; i++) {
            if(vertices[i].hasCandidates())
                return i;
        }

        return -1;
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

    /** See if there is an edge between two vertices.
     *  @param v0 One end of the possible edge.
     *  @param v1 The other end of the possible edge.
     *  @return If an edge exists between those two vertices.
     */
    public boolean hasEdge(int v0, int v1)
    {
        return v0 >= 0 && v1 >= 0 && orders[v0][v1] != null;
    }

    /** Check if this graph is a complete graph.
     *  @return If this graph is a complete graph.
     */
    public boolean isCompleteGraph()
    {
        int completeEdges = (vertices.length - 1) * vertices.length;
        return numberOfEdges == completeEdges;
    }

    /** Gets the girth of this graph.
     *  @return The girth of this graph.
     */
    public int getGirth()
    {
        return girth;
    }

    /** Apply a sort to the vertices to get an optimal search tree. By default,
     *  it does nothing, override it.
     *  @param vertices List to be sorted.
     *  @param graph Graph containing the vertices.
     */
    public void sort(List<Integer> vertices, final Graph graph)
    {
    }
}
