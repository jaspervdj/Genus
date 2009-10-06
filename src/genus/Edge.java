package genus;

/** Class representing a directed edge.
 */
public class Edge
{
    /** Start and end vertices. */
    Vertex start, end;

    /** Label given to the edge. */
    int label;

    /** The reverse edge. When this edge leads from a to b, the reverse edge
     *  leads from b to a. */
    Edge reverse; 

    /** Constructor.
     *  @param start Start vertex of the egde.
     *  @param end End vertex of the edge.
     */
    public Edge(Vertex start, Vertex end)
    {
        this.start = start;
        this.end = end;
        label = -1;
        reverse = null;
    }

    /** Get the start vertex.
     *  @return The start vertex.
     */
    public Vertex getStart()
    {
        return start;
    }

    /** Get the end vertex.
     *  @return The end vertex.
     */
    public Vertex getEnd()
    {
        return end;
    }

    /** Check if this edge has a label.
     *  @return If this edge has a label.
     */
    public boolean hasLabel()
    {
        return label >= 0;
    }

    /** Get the edge label.
     *  @return The edge label.
     */
    public int getLabel()
    {
        return label;
    }

    /** Set the label for the edge.
     *  @param label The new label for the edge.
     */
    public void setLabel(int label)
    {
        this.label = label;
    }

    /** Unset the edge label.
     */
    public void clearLabel()
    {
        label = -1;
    }

    /** Get the reverse edge.
     *  @return The reverse edge.
     */
    public Edge getReverse()
    {
        return reverse;
    }

    /** Set the reverse edge.
     *  @param reverse The reverse edge.
     */
    public void setReverse(Edge reverse)
    {
        this.reverse = reverse;
    }
}
