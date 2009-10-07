package genus;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

/** A graph vertex.
 */
public class Vertex
{
    /** Id of the vertex. */
    private int id;

    /** Set of inbound edges. */
    private Set<Edge> inbound;

    /** Set of outbound edges. */
    private Set<Edge> outbound;

    /** The permutation cycles per vertex. */
    private Map<Vertex, ArrayList<Vertex>> cycleMap;

    /** The number of unique cycles. */
    private int numberOfCycles;

    /** If this vertex is saturated */
    private boolean saturated;

    /** Constructor.
     *  @param id Id for this vertex.
     */
    public Vertex(int id)
    {
        this.id = id;
        inbound = new HashSet<Edge>();
        outbound = new HashSet<Edge>();
    }

    /** Set the vertex neighbours.
     *  @param neighbours The vertex neighbours.
     */
    public void setNeighbours(ArrayList<Vertex> neighbours)
    {
        cycleMap = new HashMap<Vertex, ArrayList<Vertex>>();

        for(Vertex neighbour: neighbours) {
            ArrayList<Vertex> cycle = new ArrayList<Vertex>();
            cycle.add(neighbour);
            cycleMap.put(neighbour, cycle);
        }

        numberOfCycles = neighbours.size();

        saturated = false;
    }

    /** Get the vertex id.
     *  @return The vertex id.
     */
    public int getId()
    {
        return id;
    }

    /** Add an inbound edge.
     *  @param edge Inbound edge to add.
     */
    public void addInbound(Edge edge)
    {
        inbound.add(edge);
    }

    /** Add an outbound edge.
     *  @param edge Outbound edge to add.
     */
    public void addOutbound(Edge edge)
    {
        outbound.add(edge);
    }

    /** Check if this vertex is connected to a given vertex.
     *  @param vertex Vertex to check connection with.
     *  @return If this vertex is connected to the other vertex.
     */
    public boolean isConnected(Vertex vertex)
    {
        for(Edge edge: outbound) {
            if(edge.getEnd() == vertex)
                return true;
        }

        return false;
    }

    /** Check if this vertex has an inbound edge with the given label.
     *  @param label Label to check for.
     *  @return If this vertex has such a labeled edge.
     */
    boolean hasInbound(int label)
    {
        for(Edge edge: inbound) {
            if(edge.getLabel() == label)
                return true;
        }

        return false;
    }

    /** Check if this vertex has an outbound edge with the given label.
     *  @param label Label to check for.
     *  @return If this vertex has such a labeled edge.
     */
    boolean hasOutbound(int label)
    {
        for(Edge edge: outbound) {
            if(edge.getLabel() == label)
                return true;
        }

        return false;
    }

    /** Check if this vertex has unlabeled outbound edges.
     *  @return If this vertex has unlabeled outbound edges.
     */
    public boolean hasUnlabeledOutbound()
    {
        for(Edge edge: outbound) {
            if(!edge.hasLabel())
                return true;
        }

        return false;
    }

    /** Get the next edge in the cycle, assuming that we currently are in this
     *  vertex.
     *  @param currentLabel The current label.
     *  @param lastEdge Last edge token.
     *  @return The next cycle candidates.
     */
    public List<Edge> getNextCandidates(int currentLabel, Edge lastEdge)
    {
        List<Edge> list = new ArrayList<Edge>();

        /* An exception on edges with two outbound edges. */
        if(outbound.size() <= 2) {
            for(Edge edge: outbound) {
                /* We cannot simply return. */
                if(!edge.hasLabel() &&
                        edge.getReverse().getLabel() != currentLabel) {
                    list.add(edge);
                }
            }
        } else {
            for(Edge edge: outbound) {
                /* We cannot simply return. */
                if(!edge.hasLabel() &&
                        edge.getReverse().getLabel() != currentLabel &&
                        (lastEdge == null ||
                        !lastEdge.getReverse().hasLabel() ||
                        lastEdge.getReverse().getLabel() !=
                                edge.getReverse().getLabel())) {
                    list.add(edge);
                }
            }
        }

        return list;
    }

    /** Check if this vertex is saturated.
     *  @return If this vertex is saturated.
     */
    public boolean isSaturated()
    {
        return saturated;
    }

    /** Tell the vertex that we took a path from a to b with this vertex in the
     *  middle.
     *  @param a Path start.
     *  @param b Path end.
     */
    public boolean connect(Vertex a, Vertex b)
    {
        if(a == null)
            return true;

        if(numberOfCycles > 1) {
            ArrayList<Vertex> aCycle = cycleMap.get(a),
                    bCycle = cycleMap.get(b);

            if(aCycle == bCycle) {
                return false;
            } else {
                numberOfCycles--;

                /* Join the cycles. */
                aCycle.addAll(bCycle);

                /* Update all references. */
                for(Vertex v: bCycle) {
                    cycleMap.put(v, aCycle);
                }
            }
        } else {
            saturated = true;
        }

        return true;
    }

    /** Split the cycle between a and b.
     *  @param a Vertex a.
     *  @param b Vertex b.
     */
    public void split(Vertex a, Vertex b)
    {
        if(a == null)
            return;

        if(saturated) {
            saturated = false;
        } else {
            ArrayList<Vertex> aCycle = cycleMap.get(a),
                    bCycle = new ArrayList<Vertex>();

            ListIterator<Vertex> iterator = aCycle.listIterator();

            while(iterator.hasNext() && iterator.next() != a) {
                /* Do nothing. */
            }

            while(iterator.hasNext()) {
                bCycle.add(iterator.next());
                iterator.remove();
            }

            for(Vertex vertex: bCycle) {
                cycleMap.put(vertex, bCycle);
            }

            numberOfCycles++;
        }

        System.out.println(">> " + this);
    }

    public Set<Vertex> getCandidates(Vertex from)
    {
        Set<Vertex> candidates = new HashSet<Vertex>();
        ArrayList<Vertex> containingCycle = cycleMap.get(from);
        
        /* Exception when only one cycle. */
        if(numberOfCycles == 1) {
            /* First vertex from the cycle. */
            if(from == null)
                candidates.add(cycleMap.values().iterator().next().get(0));
            else
                candidates.add(cycleMap.get(from).get(0));
        } else {
            for(ArrayList<Vertex> cycle: cycleMap.values()) {
                if(cycle != containingCycle) {
                    candidates.add(cycle.get(0));
                }
            }
        }

        return candidates;
    }

    @Override
    public String toString()
    {
        /*String string = "";
        for(ArrayList<Vertex> cycle: cycles.keySet()) {
            string += "{ ";
            for(Vertex v: cycle) {
                string += v.getId() + " ";
            }
            string += "} ";
        } */
        return "" + id;
    }

    @Override
    public int hashCode()
    {
        return id;
    }

    @Override
    public boolean equals(Object object)
    {
        return (object instanceof Vertex) && ((Vertex) object).id == id;
    }
}
