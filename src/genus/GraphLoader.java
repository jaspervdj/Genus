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
            BufferedReader reader =
                    new BufferedReader(new FileReader(fileName));
            try {
                String line = reader.readLine();
                while(line != null) {
                    int split = line.indexOf("<->", 0);

                    String[] from = line.substring(0, split).split("[, ]+");
                    String[] to = line.substring(split + 3).split("[, ]+");

                    for(String fromVertex: from) {
                        for(String toVertex: to) {
                            if(!fromVertex.equals(toVertex)) {
                                graph.addEdge(Integer.parseInt(fromVertex),
                                        Integer.parseInt(toVertex));
                            }
                        }
                    }

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
