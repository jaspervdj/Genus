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
