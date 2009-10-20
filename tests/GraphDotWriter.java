import genus.Graph;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/** Class that is able to write graphs to .dot files.
 *  See: graphviz.org
 */
public class GraphDotWriter
{
    /** Graph to write. */
    private Graph graph;

    /** Constructor.
     *  @param graph Graph to write.
     */
    public GraphDotWriter(Graph graph)
    {
        this.graph = graph;
    }

    /** Write the graph to a file.
     *  @param fileName File name of the file to write.
     */
    public void write(String fileName)
    {
        try {
            PrintWriter writer = new PrintWriter(
                    new FileOutputStream(fileName));
            try {
                writer.println("graph g{");
                for(int vertex: graph.getVertices()) {
                    for(int neighbour: graph.getNeighbours(vertex)) {
                        /* Make sure we only render every edge once. */
                        if(vertex < neighbour) {
                            writer.println("    " + vertex + " -- " +
                                    neighbour + ";");
                        }
                    }
                }
                writer.println("}");
            } finally {
                writer.close();
            }
        } catch(IOException exception) {
            System.out.println("Error writing the file " + fileName);
        }
    }
}
