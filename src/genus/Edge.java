package genus;

/** Class representing a directed edge.
 */
public class Edge
{
    /** Start and end vertices. */
    int start, end;

    /** Label given to the edge. */
    int label;

    /** Constructor.
     *  @param start Start vertex of the egde.
     *  @param end End vertex of the edge.
     */
    public Edge(int start, int end)
    {
        this.start = start;
        this.end = end;
        label = -1;
    }

    /** Get the start vertex.
     *  @return The start vertex.
     */
    public int getStart()
    {
        return start;
    }

    /** Get the end vertex.
     *  @return The end vertex.
     */
    public int getEnd()
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
}
