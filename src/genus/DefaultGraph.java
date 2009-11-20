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

    /** Edge labels. */
    public int[][] labels;

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

        labels = new int[vertices.length][vertices.length];
        for(int i = 0; i < labels.length; i++)
            for(int j = 0; j < labels[i].length; j++)
                labels[i][j] = -1;
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

    /** Connect two edges.
     *  @param from Vertex we're coming from.
     *  @param through Vertex we're going through.
     *  @param destination Vertex we're going to.
     *  @param label Label for the connection.
     *  @return If the operation was succesful.
     */
    public boolean connect(int from, int through, int destination, int label)
    {
        if(vertices[through].connect(from, destination)) {
            labels[from][through] = label;
            labels[through][destination] = label;
            return true;
        } else {
            return false;
        }
    }

    /** Split two edges (undo a connect).
     *  @param from Vertex we're coming from.
     *  @param through Vertex we're going through.
     *  @param destination Vertex we're going to.
     */
    public void split(int from, int through, int destination)
    {
        vertices[through].split(from, destination);
        labels[from][through] = -1;
        labels[through][destination] = -1;
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

    /** Get the label of a certain directed edge.
     *  @param from Start of the edge.
     *  @param to End of the edge.
     */
    public int getLabel(int from, int to)
    {
        return labels[from][to];
    }

    /** Check if a given vertex has an outgoing edge with a given label.
     *  @param vertex Vertex to check for labels.
     *  @param label Label to check for.
     */
    public boolean hasOutboundEdgeWithLabel(int vertex, int label)
    {
        for(int neighbour: vertices[vertex].getNeighbours()) {
            if(labels[vertex][neighbour] == label) {
                return true;
            }
        }

        return false;
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

    public boolean isSplitable(int inbound, int vertex, int label)
    {
        /* Neighbours of the vertex. */
        int[] neighbours = vertices[vertex].getNeighbours();

        /* Search for first inbound with similar label. */
        int firstInboundIndex = -1;
        int i = 0;
        while(i < neighbours.length && firstInboundIndex < 0) {
            if(labels[neighbours[i]][vertex] == label &&
                    neighbours[i] != inbound)
                firstInboundIndex = i;
            i++;
        }

        /* No inbound with similar label, return. */
        if(firstInboundIndex < 0)
            return false;
        int firstInbound = neighbours[firstInboundIndex];

        /* The first outbound is logically the follower of the first inbound. */
        int firstOutbound =
                orders[vertex][firstInbound].getNext().getValue();

        /* Incoming should be connectable to first outbound. */
        if(orders[vertex][firstOutbound].getFirst().getValue() ==
                orders[vertex][firstInbound].getFirst().getValue())
            return false;

        /* There should be a free outbound so that the first inbound can be
         * connected to this outbound. */
        i = 0;
        boolean found = false;
        while(i < neighbours.length && !found) {
            if(neighbours[i] != firstInbound &&
                    neighbours[i] != firstOutbound &&
                    neighbours[i] != inbound &&
                    orders[vertex][i].getFirst().getValue() !=
                    orders[vertex][inbound].getFirst().getValue())
                found = true;
            i++;
        }
         
        return found;
    }
}
