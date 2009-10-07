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

    /** Constructor. Construct a graph from any class implementing the Graph
     *  interface.
     *  @param graph Graph to construct the DefaultGraph from.
     */
    public DefaultGraph(Graph graph)
    {
        vertices = new HashMap<Integer, Vertex>();

        for(Integer id: graph.getVertices())
            vertices.put(id, new Vertex(id));    

        for(int id: graph.getVertices()) {
            Vertex vertex = vertices.get(id);

            ArrayList<Vertex> neighbours = new ArrayList<Vertex>();

            for(int neighbourId: graph.getNeighbours(id)) {
                Vertex neighbour = vertices.get(neighbourId);
                neighbours.add(neighbour);
            }

            vertex.setNeighbours(neighbours);
        }
    }

    public Vertex getUnsaturatedVertex()
    {
        for(int id: vertices.keySet()) {
            Vertex vertex = vertices.get(id);
            if(!vertex.isSaturated())
                return vertex;
        }

        return null;
    }
}
