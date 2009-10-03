package genus;
import java.util.Set;
import java.util.HashSet;

public class SimpleVertex implements Cloneable
{
    /** Label of this vertex. */
    private int label;

    /** Edges arriving in this vertex. */
    Set<SimpleEdge> inbound;

    /** Edges starting from this vertex. */
    Set<SimpleEdge> outbound;

    /** Constructor.
     *  @param label Label for the vertex.
     */
    public SimpleVertex(int label)
    {
        this.label = label;
        inbound = new HashSet<SimpleEdge>();
        outbound = new HashSet<SimpleEdge>();
    }

    /** Obtain the label for the vertex.
     *  @return The label for this vertex.
     */
    public int getLabel()
    {
        return label;
    }

    /** Obtain the inbound edges.
     *  @return The inbound edges.
     */
    public Set<SimpleEdge> getInbound()
    {
        return inbound;
    }

    /** Obtain the outbound edges.
     *  @return The outbound edges.
     */
    public Set<SimpleEdge> getOutbound()
    {
        return outbound;
    }

    /** Add an inbound edge.
     *  @param edge Edge to add.
     */
    public void addInbound(SimpleEdge edge)
    {
        inbound.add(edge);
    }

    /** Add an outbound edge.
     *  @param edge Edge to add.
     */
    public void addOutbound(SimpleEdge edge)
    {
        outbound.add(edge);
    }

    /** Remove an inbound edge.
     *  @param edge Edge to remove.
     */
    public void removeInbound(SimpleEdge edge)
    {
        inbound.remove(edge);
    }

    /** Remove an outbound edge.
     *  @param edge Edge to remove.
     */
    public void removeOutbound(SimpleEdge edge)
    {
        outbound.remove(edge);
    }

    /** Check if this vertex is isolated.
     *  @return If this vertex is isolated.
     */
    public boolean isIsolated()
    {
        return inbound.isEmpty() && outbound.isEmpty();
    }

    @Override
    public int hashCode()
    {
        return label;
    }

    @Override
    public boolean equals(Object other)
    {
        return (other instanceof SimpleVertex) &&
                ((SimpleVertex) other).label == label;
    }
}
