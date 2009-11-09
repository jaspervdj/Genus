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
    private int[] neighbours;

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
    public int[] getNeighbours()
    {
        return neighbours;
    }

    /** Set the vertex neighbours.
     *  @param neighbours The vertex neighbours.
     */
    public void setNeighbours(ArrayList<Vertex> neighbours)
    {
        this.neighbours = new int[neighbours.size()];
        for(int i = 0; i < this.neighbours.length; i++)
            this.neighbours[i] = neighbours.get(i).getId();

        for(Vertex neighbour: neighbours) {
            CycleNode cycle = new CycleNode(neighbour.getId());
            cycleNodes[id][neighbour.getId()] = cycle;
        }

        numberOfCycles = neighbours.size();

        candidates = this.neighbours.length >= 1;
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
     *  @param from Vertex we're coming from.
     *  @param destination Vertex we're going to.
     */
    public boolean connect(int from, int destination)
    {
        if(from < 0)
            return true;

        if(numberOfCycles > 1) {
            CycleNode fromCycle = cycleNodes[id][from],
                    destinationCycle = cycleNodes[id][destination];

            if(fromCycle.getFirst() == destinationCycle.getFirst()) {
                return false;
            } else {
                numberOfCycles--;

                /* Join the cycles. */
                fromCycle.append(destinationCycle);
            }
        } else {
            candidates = false;
        }

        return true;
    }

    /** Split the cycle at this vertex (undo a connect).
     *  @param from Vertex we're coming from.
     *  @param destination Vertex we're going to.
     */
    public void split(int from, int destination)
    {
        if(from < 0)
            return;

        if(!candidates) {
            candidates = true;
        } else {
            CycleNode fromCycle = cycleNodes[id][from];
            fromCycle.split();

            numberOfCycles++;
        }
    }

    /** Check if two edges can be connected.
     *  @param from Vertex we're coming from.
     *  @param destination Vertex we're going to.
     *  @return If the two edges can be connected.
     */
    public boolean isCandidate(int from, int destination)
    {
        if(from < 0 || numberOfCycles == 1) {
            /* First node in a cycle. */
            return cycleNodes[id][destination].getFirst().getValue() ==
                    destination;
        } else {
            /* First node in a cycle, and not in the same cycle. */
            return cycleNodes[id][destination].getFirst().getValue() ==
                    destination &&
                    destination != cycleNodes[id][from].getFirst().getValue();
        }
    }

    /** Get a random candidate. Suppose we are standing in this vertex, and we
     *  want to start a new cycle. This method then returns a vertex where we
     *  can travel next.
     *  @return A next candidate.
     */
    public int getCandidate()
    {
        return cycleNodes[id][neighbours[0]].getFirst().getValue();
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
