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

    /** Constructor.
     */
    public SimpleGraph()
    {
        vertices = new HashMap<Integer, SimpleVertex>();
        edges = new HashSet<SimpleEdge>();
    }

    /** Constructor. Create a new Graph that has the same vertices as the
     *  original graph, but none of the edges.
     *  @param other Graph to take the vertices of.
     */
    public SimpleGraph(SimpleGraph other)
    {
        this();
        for(Integer i: other.vertices.keySet())
            vertices.put(i, new SimpleVertex(i));
    }

    /** Obtain the edges in the graph.
     *  @return The edges in the graph.
     */
    public Set<SimpleEdge> getEdges()
    {
        return edges;
    }

    /** Check if this graph contains no edges at all.
     *  @return If this graph contains no edges at all.
     */
    public boolean isEmpty()
    {
        return edges.isEmpty();
    }

    /** Check if this graph contains a given vertex.
     *  @param label Label of the vertex to search for.
     *  @return If this graph contains the given vertex.
     */
    public boolean contains(int label)
    {
        return vertices.get(label) != null;
    }

    /** Get a random vertex that has at least one edge.
     *  @return A random vertex that has at least one edge.
     */
    public SimpleVertex getRandomEdgedVertex()
    {
        /* Assume there are no isolated vertices. */
        return vertices.values().iterator().next();
    }

    /** This function assumes the two vertices are already present.
     */
    public void addEdge(SimpleEdge edge)
    {
        SimpleVertex sourceVertex = vertices.get(edge.getSource()),
                destinationVertex = vertices.get(edge.getDestination());

        edges.add(edge);
        sourceVertex.addOutbound(edge);
        destinationVertex.addInbound(edge);
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

        SimpleEdge edge0 = new SimpleEdge(v0, v1),
                edge1 = new SimpleEdge(v1, v0);

        if(edges.add(edge0) && edges.add(edge1)) {
            vertex0.addInbound(edge1);
            vertex0.addOutbound(edge0);
            vertex1.addInbound(edge0);
            vertex1.addOutbound(edge1);
            return true;
        } else {
            return false;
        }
    }

    /** This function leaves the vertices where they are. */
    public boolean removeEdge(SimpleEdge edge)
    {
        SimpleVertex sourceVertex = vertices.get(edge.getSource()),
                destinationVertex = vertices.get(edge.getDestination());

        edges.remove(edge);
        sourceVertex.removeOutbound(edge);
        destinationVertex.removeOutbound(edge);
    }

    @Override
    public boolean removeEdge(int v0, int v1)
    {
        SimpleVertex vertex0 = vertices.get(v0), vertex1 = vertices.get(v1);

        if(vertex0 == null || vertex1 == null)
            return false;

        SimpleEdge edge0 = new SimpleEdge(v0, v1),
                edge1 = new SimpleEdge(v1, v0);

        if(edges.contains(edge0)) {
            edges.remove(edge0);
            edges.remove(edge1);
            vertex0.removeInbound(edge1);
            vertex0.removeOutbound(edge0);
            vertex1.removeInbound(edge0);
            vertex1.removeOutbound(edge1);

            if(vertex0.isIsolated())
                vertices.remove(v0);

            if(vertex1.isIsolated())
                vertices.remove(v1);

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
            if(edge.connects(v))
                garbage.add(edge);
        }

        /* Remove garbage. */
        for(SimpleEdge edge: garbage)
            edges.remove(edge);

        vertices.remove(v);

        return true;
    }
}
