package genus;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;

/** A graph vertex.
 */
public class Vertex implements Comparable
{
    /** Id of the vertex. */
    private int id;

    /** List of neighbours. */
    private Vertex[] neighbours;

    /** The permutation cycles per vertex. */
    private Map<Vertex, CycleNode> cycleMap;

    /** The number of unique cycles. */
    private int numberOfCycles;

    /** If this vertex has any candidates left. */
    private boolean candidates;

    /** Constructor.
     *  @param id Id for this vertex.
     */
    public Vertex(int id)
    {
        this.id = id;
    }

    /** Get the neighbours of this Vertex.
     *  @return  The neighbours of this Vertex.
     */
    public Vertex[] getNeighbours()
    {
        return neighbours;
    }

    /** Set the vertex neighbours.
     *  @param neighbours The vertex neighbours.
     */
    public void setNeighbours(ArrayList<Vertex> neighbours)
    {
        this.neighbours =
                (Vertex[]) neighbours.toArray(new Vertex[neighbours.size()]);
        cycleMap = new HashMap<Vertex, CycleNode>();

        for(Vertex neighbour: neighbours) {
            CycleNode cycle = new CycleNode(neighbour.getId());
            cycleMap.put(neighbour, cycle);
        }

        numberOfCycles = neighbours.size();

        candidates = true;
    }

    /** Get the vertex id.
     *  @return The vertex id.
     */
    public int getId()
    {
        return id;
    }

    /** Check if this vertex has candidates left.
     *  @return If this vertex has candidates left.
     */
    public boolean hasCandidates()
    {
        return candidates;
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
            candidates = false;
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

        if(!candidates) {
            candidates = true;
        } else {
            CycleNode aCycle = cycleMap.get(a);
            aCycle.split();

            numberOfCycles++;
        }
    }

    /** Get a next candidate in our current cycle. Suppose we are coming from
     *  the vertex in the parameter from, and we ask this vertex where we can
     *  travel next. This method returns those candidates.
     *  @param from The vertex we are coming from.
     *  @return Candidates for our next vertex.
     */
    public Set<Integer> getCandidates(Vertex from)
    {
        Set<Integer> candidates = new TreeSet<Integer>();
        CycleNode containingCycle = from == null ? null : cycleMap.get(from);
        
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

    public boolean isCandidate(Vertex from, Vertex candidate)
    {
        if(from == null || numberOfCycles == 1) {
            /* First node in a cycle. */
            return cycleMap.get(candidate).getFirst().getValue() ==
                    candidate.getId();
        } else {
            /* First node in a cycle, and not in the same cycle. */
            return cycleMap.get(candidate).getFirst().getValue() ==
                    candidate.getId() &&
                    cycleMap.get(candidate).getFirst().getValue() !=
                    cycleMap.get(from).getFirst().getValue();
        }
    }

    public boolean isAvailable(int edge)
    {
        return cycleMap.get(edge).getFirst().getValue() == edge;
    }

    /** Get a random candidate. Suppose we are standing in this vertex, and we
     *  want to start a new cycle. This method then returns a vertex where we
     *  can travel next.
     *  @return A next candidate.
     */
    public int getCandidate()
    {
        return cycleMap.values().iterator().next().getFirst().getValue();
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

    @Override
    public int compareTo(Object object)
    {
        return id - ((Vertex) object).id;
    }
}
