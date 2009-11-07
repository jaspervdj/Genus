package genus;

import java.util.Queue;
import java.util.LinkedList;

/** Class to find the girth of a graph.
 */
public class FindGirth
{
    /** Constructor.
     */
    public FindGirth()
    {
    }

    /** Find the girth of a graph.
     *  @param graph Graph to find the girth for.
     *  @return The girth of a graph.
     */
    public int findGirth(DefaultGraph graph)
    {
        /** Local inner class to represent vertices found at a certain depth.
         */
        class Node
        {
            public int vertex, depth;

            public Node(int vertex, int depth)
            {
                this.vertex = vertex;
                this.depth = depth;
            }
        }

        /* Used for labelling vertices. */
        int[] labels = new int[graph.getNumberOfVertices()];

        /* Best (smallest) cycle length. */
        int best = graph.getNumberOfVertices() - 1;

        /* Queue for our BFS. */
        Queue<Node> queue = new LinkedList<Node>();

        /* Start a BFS from every vertex except the last two (not needed). */
        int root = 0;
        while(root < graph.getNumberOfVertices() - 2 && best > 3) {

            /* Reset labels. */
            for(int i = 0; i < labels.length; i++)
                labels[i] = -1;

            /* Add initial node to the queue. */
            labels[root] = 0;
            queue.add(new Node(root, 0));
    
            /* Take next item from the queue. */
            Node node = queue.poll();
            while(node != null && best > 3 && (node.depth + 1) * 2 - 1 < best) {

                /* Depth of neighbours. */
                int depth = node.depth + 1;

                /* Check all neighbours. */
                for(int neighbour:
                            graph.getVertex(node.vertex).getNeighbours()) {
                    /* We haven't seen this neighbour before. */
                    if(labels[neighbour] < 0) {
                        queue.add(new Node(neighbour, depth));
                        labels[neighbour] = depth;
                    /* Cycle with odd number of edges. */
                    } else if(labels[neighbour] == depth - 1) {
                        if(depth * 2 - 1 < best)
                            best = depth * 2 - 1;
                    /* Cycle with even number of edges. */
                    } else if(labels[neighbour] == depth) {
                        if(depth * 2 < best)
                            best = depth * 2;
                    }
                }

                /* Take next item from the queue. */
                node = queue.poll();
            }

            /* Clear the queue and prepare to start a BFS from a next vertex. */
            queue.clear();
            root++;
        }

        return best;
    }
}
