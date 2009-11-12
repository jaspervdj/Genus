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

    private int[] labels;
    private boolean[][] matrix;

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

        labels = new int[vertices.length];
        matrix = new boolean[vertices.length][vertices.length];
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
     *  @return If the operation was succesful.
     */
    public boolean connect(int from, int through, int destination)
    {
        if(vertices[through].connect(from, destination)) {
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

    public int estimate()
    {
        for(int i = 0; i < labels.length; i++)
            labels[i] = i;

        for(int i = 0; i + 1 < vertices.length; i++)
            for(int j = i + 1; j < vertices.length; j++)
                matrix[i][j] = false;

        int faces = 0;

        for(int v0 = 0; v0 < vertices.length; v0++) {
            for(int v1: vertices[v0].getNeighbours()) {
                if(vertices[v0].isCandidate(-1, v1)) {
                    int minV = v0 < v1 ? v0 : v1;
                    int maxV = v0 < v1 ? v1 : v0;
                    if(!matrix[minV][maxV]) {
                        if(labels[v0] == labels[v1]) {
                            faces++;
                        } else {
                            int minLabel = labels[v0] < labels[v1] ?
                                    labels[v0] : labels[v1];
                            int maxLabel = labels[v0] < labels[v1] ?
                                    labels[v1] : labels[v0];
                            for(int i = 0; i < labels.length; i++)
                                if(labels[i] == maxLabel)
                                    labels[i] = minLabel;
                        }

                        matrix[minV][maxV] = true;
                    }
                }
            }
        }

        int components = 1;
        Arrays.sort(labels);
        for(int i = 1; i < labels.length; i++) {
            if(labels[i - 1] != labels[i])
                components++;
        }

        return faces + components;
    }

    public double completeness()
    {
        int completeEdges = (vertices.length - 1) * vertices.length / 2;
        return 0.5 * (double) numberOfEdges / (double) completeEdges;
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
