package genus;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/** Class that is able to write graphs to .dot files.
 */
public class GraphDotWriter
{
    /** Graph to write. */
    private DefaultGraph graph;

    /** Constructor.
     *  @param graph Graph to write.
     */
    public GraphDotWriter(DefaultGraph graph)
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
                writer.println("digraph g{");
                /*for(Edge edge: graph.getEdges()) {
                    writer.println("    " + edge.getStart().getId() + " -> " +
                            edge.getEnd().getId() + " [label=\"" +
                            edge.getLabel() + "\"];");
                }*/
                writer.println("}");
            } finally {
                writer.close();
            }
        } catch(IOException exception) {
            System.out.println("Error writing the file " + fileName);
        }
    }
}
