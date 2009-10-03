package genus;

public class SimpleEdge
{
    private int source, destination;
    private int label;

    public SimpleEdge(int source, int destination)
    {
        this.source = source;
        this.destination = destination;
        label = 0;
    }

    public void setLabel(int label)
    {
        this.label = label;
    }

    public int getLabel()
    {
        return label;
    }

    /** Check if this edge connects the given vertex with any other vertex.
     *  @return If this edge connects the given vertex.
     */
    public boolean connects(int vertex)
    {
        return source == vertex || destination == vertex;
    }

    /** Obtain the source point of the edge.
     *  @return The source point of the edge.
     */
    public int getSource()
    {
        return source;
    }

    /** Obtain the destination point of the edge.
     *  @return The destination point of the edge.
     */
    public int getDestination()
    {
        return destination;
    }

    @Override
    public int hashCode()
    {
        return source + destination;
    }

    @Override
    public boolean equals(Object other)
    {
        return (other instanceof SimpleEdge) && 
                source == ((SimpleEdge) other).source &&
                destination == ((SimpleEdge) other).destination;
    }
}
