package genus;

public class SimpleEdge
{
    private SimpleVertex first, second;
    private int label;

    public SimpleEdge(SimpleVertex first, SimpleVertex second)
    {
        this.first = first;
        this.second = second;
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

    public boolean connects(SimpleVertex vertex)
    {
        return first == vertex || second == vertex;
    }

    @Override
    public int hashCode()
    {
        return first.hashCode() + second.hashCode();
    }

    @Override
    public boolean equals(Object other)
    {
        return (other instanceof SimpleEdge) && 
                first.equals(((SimpleEdge) other).first) &&
                second.equals(((SimpleEdge) other).second);
    }
}
