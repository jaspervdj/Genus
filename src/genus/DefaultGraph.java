package genus;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;

/** A default graph, to be used with DefaultFindGenus.
 */
public class DefaultGraph
{
    /** The vertices, stored by mapped id. */
    private Vertex[] vertices;

    /** Total number of edges. */
    private int numberOfEdges;

    int[] labels;
    boolean[][] matrix;

    /** Constructor. Construct a graph from any class implementing the Graph
     *  interface.
     *  @param graph Graph to construct the DefaultGraph from.
     */
    public DefaultGraph(final Graph graph)
    {
        /* Process the graph first. */
        GraphProcessor processor = new SimplifyGraphProcessor();
        processor.process(graph);

        /* A map to translate the labels given by the input to a 0..n numbering,
         * so we can use it in an array more easily. */
        Map<Integer, Integer> translation = new HashMap<Integer, Integer>();
        vertices = new Vertex[graph.getVertices().size()];

        /* Sort the vertices to have less top-level branches. */
        List<Integer> sortedVertices = graph.getVertices();
        Collections.sort(sortedVertices,
            new Comparator() {
                public int compare(Object o0, Object o1) {
                    return graph.getNeighbours((Integer) o0).size() -
                            graph.getNeighbours((Integer) o1).size();
                }
            }
        );

        /* Build the translation map and store the vertices.. */
        int index = 0;
        for(int id: sortedVertices) {
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

    public int estimate()
    {
        int[] labels = new int[vertices.length];
        for(int i = 0; i < labels.length; i++)
            labels[i] = i;

        for(int i = 0; i + 1 < vertices.length; i++)
            for(int j = i + 1; j < vertices.length; j++)
                matrix[i][j] = false;

        int faces = 0;

        for(int v0 = 0; v0 < vertices.length; v0++) {
            for(int v1: vertices[v0].getCandidates(null)) {
                int minV = v0 < v1 ? v0 : v1;
                int maxV = v0 < v1 ? v1 : v0;
                if(!matrix[minV][maxV]) {
                    if(labels[v0] == labels[v1]) {
                        faces++;
                    } else {
                        int label = labels[v0] < labels[v1] ?
                                labels[v0] : labels[v1];
                        labels[v0] = label;
                        labels[v1] = label;
                    }

                    matrix[minV][maxV] = true;
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
}
