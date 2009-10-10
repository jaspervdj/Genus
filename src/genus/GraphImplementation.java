package genus;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/** An implementation of the Graph interface. This is used as a bridge between
 * the Graph interface and the graph implementation for the findGenus algorithm.
 */
public class GraphImplementation implements Graph
{
    /** A map that, for every vertex, contains it's neighbours. */
    private Map<Integer, ArrayList<Integer>> neighbours;

    /** Constructor.
     */
    public GraphImplementation()
    {
        neighbours = new HashMap<Integer, ArrayList<Integer>>();
    }

    @Override
    public boolean addEdge(int v0, int v1)
    {
        /* Get the vertices. */
        ArrayList<Integer> neighbours0 = neighbours.get(v0),
                neighbours1 = neighbours.get(v1);

        /* Create the vertices if they don't exist yet. */
        if(neighbours0 == null)
            neighbours.put(v0, neighbours0 = new ArrayList<Integer>());
        if(neighbours1 == null)
            neighbours.put(v1, neighbours1 = new ArrayList<Integer>());

        /* Make sure the edge isn't there yet. */
        if(neighbours0.contains(v1)) {
            return false;
        /* Add a new edge. */
        } else {
            neighbours0.add(v1);
            neighbours1.add(v0);
            return true;
        }
    }

    @Override
    public boolean removeEdge(int v0, int v1)
    {
        /* Get the vertices. */
        ArrayList<Integer> neighbours0 = neighbours.get(v0),
                neighbours1 = neighbours.get(v1);

        /* Assert that both vertices exist. */
        if(neighbours0 == null || neighbours1 == null)
            return false;

        /* Remove the edge between them, if existant. */
        if(neighbours0.contains(v1)) {
            neighbours0.remove(new Integer(v1));
            neighbours1.remove(new Integer(v0));
            return true;
        /* The edge wasn't there in the first place .*/
        } else {
            return false;
        }
    }

    @Override
    public boolean removeVertex(int v)
    {
        /* Get the vertex. */
        ArrayList<Integer> vNeighbours = neighbours.get(v);

        /* Assert he exists. */
        if(vNeighbours == null)
            return false;

        /* Remove all edges leading to this vertex. */
        for(Integer i: vNeighbours) {
            neighbours.get(i).remove(new Integer(v));
        }

        /* Remove the vertex. */
        neighbours.remove(v);
        return true;
    }

    @Override
    public List<Integer> getVertices()
    {
        /* Build a list with vertices and return it. */
        List<Integer> list = new ArrayList<Integer>();
        for(Integer i: neighbours.keySet())
            list.add(i);
        return list;
    }

    @Override
    public List<Integer> getNeighbours(int v)
    {
        return neighbours.get(v);
    }
}
