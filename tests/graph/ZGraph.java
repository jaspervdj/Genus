package graph;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import genus.Graph;
import genus.GraphImplementation;

/** Graph loaded from the zgraph format.
 */
public class ZGraph extends GraphImplementation
{
    /** Constructor. Load the graph from a given file.
     *  @param fileName File name of the file to load.
     */
    public ZGraph(String fileName)
    {
        try {
            BufferedReader reader =
                    new BufferedReader(new FileReader(fileName));
            try {
                String line = reader.readLine();
                while(line != null) {
                    /* Ignore comments. */
                    if(!line.startsWith("#")) {
                        int split = line.indexOf("<->", 0);

                        String[] from = line.substring(0, split).split("[, ]+");
                        String[] to = line.substring(split + 3).split("[, ]+");

                        for(String fromVertex: from) {
                            for(String toVertex: to) {
                                if(!fromVertex.equals(toVertex)) {
                                    addEdge(Integer.parseInt(fromVertex),
                                            Integer.parseInt(toVertex));
                                }
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
