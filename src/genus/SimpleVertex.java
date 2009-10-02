package genus;
import java.util.List;
import java.util.ArrayList;

public class SimpleVertex
{
    private int label;
    List<SimpleEdge> edges;

    public SimpleVertex(int label)
    {
        this.label = label;
        edges = new ArrayList<SimpleEdge>();
    }

    public int getLabel()
    {
        return label;
    }

    public void addEdge(SimpleEdge edge)
    {
        edges.add(edge);
    }

    public void removeEdge(SimpleEdge edge)
    {
        edges.remove(edge);
    }

    public int getNumberOfEdges()
    {
        return edges.size();
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
