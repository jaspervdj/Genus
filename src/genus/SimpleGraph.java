package genus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class SimpleGraph implements Graph
{
    private Map<Integer, SimpleVertex> vertices;
    private Set<SimpleEdge> edges;

    public SimpleGraph()
    {
        vertices = new HashMap<Integer, SimpleVertex>();
        edges = new HashSet<SimpleEdge>();
    }

    @Override
    public boolean addEdge(int v0, int v1)
    {
        SimpleVertex vertex0 = vertices.get(v0), vertex1 = vertices.get(v1);

        /* Add the vertices if nessecary. */
        if(vertex0 == null)
            vertices.put(v0, vertex0 = new SimpleVertex(v0));
        if(vertex1 == null)
            vertices.put(v1, vertex1 = new SimpleVertex(v1));

        SimpleEdge edge = new SimpleEdge(vertex0, vertex1);

        if(edges.add(edge)) {
            vertex0.addEdge(edge);
            vertex1.addEdge(edge);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeEdge(int v0, int v1)
    {
        SimpleVertex vertex0 = vertices.get(v0), vertex1 = vertices.get(v1);

        if(vertex0 == null || vertex1 == null)
            return false;

        SimpleEdge edge = new SimpleEdge(vertex0, vertex1);

        if(edges.contains(edge)) {
            edges.remove(edge);
            vertex0.removeEdge(edge);
            vertex1.removeEdge(edge);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeVertex(int v)
    {
        SimpleVertex vertex = vertices.get(v);
        if(vertex == null)
            return false;

        /* Edges without the ones connected to vertex. */
        List<SimpleEdge> garbage = new ArrayList<SimpleEdge>();
        for(SimpleEdge edge: edges) {
            if(edge.connects(vertex))
                garbage.add(edge);
        }

        /* Remove garbage. */
        for(SimpleEdge edge: garbage)
            edges.remove(edge);

        vertices.remove(v);

        return true;
    }
}
