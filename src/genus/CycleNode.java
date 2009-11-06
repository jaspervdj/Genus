package genus;

/** A custom linked list class to represent partial permutation cycles.
 *  Note that every linked node is a list on itself, too.
 */
public class CycleNode
{
    /** Value of this node. */
    private int value;

    /** Next node in the cycle. */
    private CycleNode next;

    /** First node of the cycle. */
    private CycleNode first;

    /** Last node of the cycle. */
    private CycleNode last;

    /** Constructor.
     *  @param value Initial value of the first element.
     */
    public CycleNode(int value)
    {
        this.value = value;
        next = null;
        first = last = this;
    }

    /** Get the node value.
     *  @return The node value.
     */
    public int getValue()
    {
        return value;
    }

    /** Get the first element of the cycle.
     *  @return The first element of the cycle.
     */
    public CycleNode getFirst()
    {
        return first;
    }

    /** Check if this element is the first in the cycle.
     *  @return If this element is the first in the cycle.
     */
    public boolean isFirst()
    {
        return this == first;
    }

    /** Add a cycle to the end of this cycle.
     *  @param cycle Cycle to add.
     */
    public void append(CycleNode cycle)
    {
        /* Set last reference for this cycle. */
        first.last = cycle.last;
        next = cycle;

        CycleNode tmp = cycle.first;
        /* Set the first reference for the appended cycle. */
        while(tmp != null) {
            tmp.first = first;
            tmp = tmp.next;
        }
    }

    /** Split the cycle after this node.
     */
    public void split()
    {
        /* Set last reference for this cycle. */
        first.last = this;

        /* Set the first reference for the split cycle. */
        CycleNode tmp = next;
        while(tmp != null) {
            tmp.first = next;
            tmp = tmp.next;
        }

        next = null;
    }

    /* Used for debugging. */
    @Override
    public String toString()
    {
        String str = "";
        CycleNode tmp = first;
        while(tmp != null) {
            str += " " + tmp.value;
            tmp = tmp.next;
        }

        return str;
    }
}
