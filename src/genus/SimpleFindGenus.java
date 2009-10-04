package genus;

public class SimpleFindGenus implements FindGenus
{
    @Override
    public int findGenus(Graph graph)
    {
        return 0;
    }

    public int findFaces(SimpleGraph graph)
    {
        /* Copy vertices. */
        SimpleGraph newGraph = new SimpleGraph(graph);
        /* Let's get this party started. */
        return findFaces(new SimpleGraph(graph), graph, null, 0);
    }

    /** Actual implementation of the findGenus method.
     */
    public int findFaces(SimpleGraph newGraph, SimpleGraph oldGraph, 
            SimpleEdge lastEdge, int currentLabel)
    {
        System.out.println("Recursing. " + oldGraph.getEdges().size() + 
                " edges left.");

        /* We reached the end of our recursion here. */
        if(oldGraph.isEmpty()) {
            System.out.println("Empty.");
            return currentLabel;
        } else if(lastEdge == null) {
            /* Pick a random edge. */
            SimpleEdge edge = oldGraph.getRandomEdge();
            oldGraph.removeEdge(edge);
            edge.setLabel(currentLabel + 1);
            newGraph.addEdge(edge);
            return findFaces(newGraph, oldGraph, edge, currentLabel + 1);
        } else {
            /* Get the vertex. */
            SimpleVertex vertex = oldGraph.getVertex(lastEdge.getDestination());
            int max = 0, result;

            /* For all edges this vertex is connected to... */
            Object[] edges = vertex.getOutbound().toArray();
            for(Object edgeObject: edges) {
                SimpleEdge edge = (SimpleEdge) edgeObject;

                /* Do not take the way back. */
                if(edge.getDestination() != lastEdge.getSource()) {
                    oldGraph.removeEdge(edge);
                    edge.setLabel(currentLabel);
                    newGraph.addEdge(edge);
                    /* We closed a cycle. */
                    if(newGraph.getVertex(
                            edge.getDestination()).hasOutbound(currentLabel)) {
                        GraphDotWriter writer = new GraphDotWriter(newGraph);
                        writer.write(currentLabel + ".dot");
                        result = findFaces(newGraph, oldGraph, null, currentLabel);
                    } else {
                        result = findFaces(newGraph, oldGraph, edge, currentLabel);
                    }

                    if(result > max)
                        max = result;

                    /* Revert. */
                    newGraph.removeEdge(edge);
                    edge.setLabel(-1);
                    oldGraph.addEdge(edge);
                }
            }

            return max;
        }
    }
}
