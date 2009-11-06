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

    /** Cyclenodes matrix. */
    private CycleNode[][] cycleNodes;

    /** The number of unique cycles. */
    private int numberOfCycles;

    /** If this vertex has any candidates left. */
    private boolean candidates;

    /** Constructor.
     *  @param id Id for this vertex.
     */
    public Vertex(int id, CycleNode[][] cycleNodes)
    {
        this.id = id;
        this.cycleNodes = cycleNodes;
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

        for(Vertex neighbour: neighbours) {
            CycleNode cycle = new CycleNode(neighbour.getId());
            cycleNodes[id][neighbour.getId()] = cycle;
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
            CycleNode aCycle = cycleNodes[id][a.getId()],
                    bCycle = cycleNodes[id][b.getId()];

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
            CycleNode aCycle = cycleNodes[id][a.getId()];
            aCycle.split();

            numberOfCycles++;
        }
    }

    public boolean isCandidate(Vertex from, Vertex candidate)
    {
        if(from == null || numberOfCycles == 1) {
            /* First node in a cycle. */
            return cycleNodes[id][candidate.getId()].getFirst().getValue() ==
                    candidate.getId();
        } else {
            /* First node in a cycle, and not in the same cycle. */
            return cycleNodes[id][candidate.getId()].getFirst().getValue() ==
                    candidate.getId() &&
                    candidate.getId() !=
                    cycleNodes[id][from.getId()].getFirst().getValue();
        }
    }

    /** Get a random candidate. Suppose we are standing in this vertex, and we
     *  want to start a new cycle. This method then returns a vertex where we
     *  can travel next.
     *  @return A next candidate.
     */
    public int getCandidate()
    {
        return cycleNodes[id][neighbours[0].getId()].getFirst().getValue();
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
