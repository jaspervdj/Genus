package genus;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class GraphLoader
{
    /** Graph to load. */
    private Graph graph;

    /** Constructor.
     */
    public GraphLoader(Graph graph)
    {
        this.graph = graph;
    }

    /** Load a given file into the graph.
     *  @param fileName File name of the file to load.
     */
    public void load(String fileName)
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            try {
                String line = reader.readLine();
                while(line != null) {
                    int space = line.indexOf(' ', 0);
                    int vertex0 = Integer.parseInt(line.substring(0, space));
                    int vertex1 = Integer.parseInt(line.substring(space + 1));
                    graph.addEdge(vertex0, vertex1);
                    line = reader.readLine();
                }
            } finally {
                reader.close();
            }
        } catch(IOException exception) {
            System.out.println("Error reading the file " + fileName);
        }
    }
}
