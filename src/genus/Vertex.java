package genus;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

/** A graph vertex.
 */
public class Vertex
{
    /** Id of the vertex. */
    private int id;

    /** The permutation cycles per vertex. */
    private Map<Vertex, CycleNode> cycleMap;

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
    }

    /** Set the vertex neighbours.
     *  @param neighbours The vertex neighbours.
     */
    public void setNeighbours(ArrayList<Vertex> neighbours)
    {
        cycleMap = new HashMap<Vertex, CycleNode>();

        for(Vertex neighbour: neighbours) {
            CycleNode cycle = new CycleNode(neighbour.getId());
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
            CycleNode aCycle = cycleMap.get(a), bCycle = cycleMap.get(b);

            if(aCycle.getFirst() == bCycle.getFirst()) {
                return false;
            } else {
                numberOfCycles--;

                /* Join the cycles. */
                aCycle.append(bCycle);
            }
        } else {
            saturated = true;
        }

        return true;
    }

    /** Split the cycle after a.
     *  @param a Vertex a.
     */
    public void split(Vertex a, Vertex b)
    {
        if(a == null)
            return;

        if(saturated) {
            saturated = false;
        } else {
            CycleNode aCycle = cycleMap.get(a);
            aCycle.split();

            numberOfCycles++;
        }
    }

    public Set<Integer> getCandidates(Vertex from)
    {
        Set<Integer> candidates = new HashSet<Integer>();
        CycleNode containingCycle = cycleMap.get(from);
        
        /* Exception when only one cycle. */
        if(numberOfCycles == 1) {
            /* First vertex from the cycle. */
            if(from == null)
                candidates.add(cycleMap.values().
                        iterator().next().getFirst().getValue());
            else
                candidates.add(cycleMap.get(from).getFirst().getValue());
        } else {
            for(CycleNode cycle: cycleMap.values()) {
                if(containingCycle == null ||
                        cycle.getFirst() != containingCycle.getFirst()) {
                    candidates.add(cycle.getFirst().getValue());
                }
            }
        }

        return candidates;
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
