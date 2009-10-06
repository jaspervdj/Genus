package genus;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/** A default graph, to be used with DefaultFindGenus.
 */
public class DefaultGraph
{
    /** The vertices, stored by id. */
    private Map<Integer, Vertex> vertices;

    /** The edges. */
    private List<Edge> edges;

    /** Constructor. Construct a graph from any class implementing the Graph
     *  interface.
     *  @param graph Graph to construct the DefaultGraph from.
     */
    public DefaultGraph(Graph graph)
    {
        vertices = new HashMap<Integer, Vertex>();
        edges = new ArrayList<Edge>();

        for(Integer id: graph.getVertices())
            vertices.put(id, new Vertex(id));    

        for(int id: graph.getVertices()) {
            Vertex vertex = vertices.get(id);

            ArrayList<Vertex> neighbours = new ArrayList<Vertex>();

            for(int neighbourId: graph.getNeighbours(id)) {
                Vertex neighbour = vertices.get(neighbourId);

                neighbours.add(neighbour);
                
                /* We add the edges two add a time, so we have to check we
                 * didn't already add it. */
                if(!vertex.isConnected(neighbour)) {
                    Edge edge0 = new Edge(vertex, neighbour);
                    Edge edge1 = new Edge(neighbour, vertex);
                    edge0.setReverse(edge1);
                    edge1.setReverse(edge0);
                    vertex.addOutbound(edge0);
                    neighbour.addInbound(edge0);
                    neighbour.addOutbound(edge1);
                    vertex.addInbound(edge1);
                    edges.add(edge0);
                    edges.add(edge1);
                }
            }

            vertex.setNeighbours(neighbours);
        }
    }

    /** Get a random vertex that has unlabeled outbound edges.
     *  @return A vertex with unlabeled outbound edges.
     */
    public Vertex getOutboundVertex()
    {
        for(int id: vertices.keySet()) {
            Vertex vertex = vertices.get(id);
            if(vertex.hasUnlabeledOutbound())
                return vertex;
        }

        return null;
    }

    public int getNumberOfUnlabeledEdges()
    {
        int total = 0;
        for(Edge edge: edges)
            if(!edge.hasLabel())
                total++;
        return total;
    }

    public List<Edge> getEdges()
    {
        return edges;
    }
}
