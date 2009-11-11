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

    /** Orders matrix. */
    private Order[][] orders;

    /** The number of orders. */
    private int numberOfOrders;

    /** If this vertex has any candidates left. */
    private boolean candidates;

    /** Constructor.
     *  @param id Id for this vertex.
     */
    public Vertex(int id, Order[][] orders)
    {
        this.id = id;
        this.orders = orders;
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
            Order order = new Order(neighbour.getId());
            orders[id][neighbour.getId()] = order;
        }

        numberOfOrders = neighbours.size();

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

        if(numberOfOrders > 1) {
            Order fromOrder = orders[id][from],
                    destinationOrder = orders[id][destination];

            if(fromOrder.getFirst() == destinationOrder.getFirst()) {
                return false;
            } else {
                numberOfOrders--;

                /* Join the orders. */
                fromOrder.append(destinationOrder);
            }
        } else {
            candidates = false;
        }

        return true;
    }

    /** Split the order at this vertex (undo a connect).
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
            Order fromOrder = orders[id][from];
            fromOrder.split();

            numberOfOrders++;
        }
    }

    /** Check if two edges can be connected.
     *  @param from Vertex we're coming from.
     *  @param destination Vertex we're going to.
     *  @return If the two edges can be connected.
     */
    public boolean isCandidate(int from, int destination)
    {
        if(from < 0 || numberOfOrders == 1) {
            /* First node in an order. */
            return orders[id][destination].getFirst().getValue() ==
                    destination;
        } else {
            /* First node in an order, and not in the same order. */
            return orders[id][destination].getFirst().getValue() ==
                    destination &&
                    destination != orders[id][from].getFirst().getValue();
        }
    }

    /** Get a random candidate. Suppose we are standing in this vertex, and we
     *  want to start a new cycle. This method then returns a vertex where we
     *  can travel next.
     *  @return A next candidate.
     */
    public int getCandidate()
    {
        return orders[id][neighbours[0]].getFirst().getValue();
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
