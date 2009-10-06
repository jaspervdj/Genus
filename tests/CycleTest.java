import genus.Vertex;
import java.util.List;
import java.util.ArrayList;

public class CycleTest
{
    public static void printCandidates(Vertex v, Vertex from)
    {
        List<Vertex> candidates = v.getCandidates(from);
        for(Vertex candidate: candidates) {
            System.out.print(candidate.getId() + " ");
        }
        System.out.println();
    }

    public static void main(String[] args)
    {
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        for(int i = 0; i < 5; i++)
            vertices.add(new Vertex(i));

        Vertex v = new Vertex(10);
        v.setNeighbours(vertices);
        v.connect(vertices.get(1), vertices.get(3));
        v.connect(vertices.get(3), vertices.get(4));
        v.connect(vertices.get(4), vertices.get(2));
        v.connect(vertices.get(2), vertices.get(0));
        v.connect(vertices.get(0), vertices.get(1));
        System.out.println(v);
        System.out.println("Saturated: " + v.isSaturated());
        v.split(vertices.get(4), vertices.get(2));
        System.out.println("Saturated: " + v.isSaturated());
    }
}
