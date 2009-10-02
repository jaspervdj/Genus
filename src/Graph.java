// Do not alter this file in any way!
package genus;

public interface Graph {

  public boolean addEdge(int v0, int v1);
  /* Adds an edge to the graph. In case a vertex
     is not present it is created. Note that
     it is not necessary for vertices to have
     consecutive numbers. Returns true if the
     edge was added successfully and false if the
     edge was already present. */

  public boolean removeEdge(int v0, int v1);
  /* Removes an edge from the graph. Returns true
     if the edge was removed successfully and
     false if the edge was not present. */ 

  public boolean removeVertex(int v);
  /* Removes a vertex from the graph. Possible
     incident edges are removed as well. Returns
     true if the vertex was removed successfully
     and false if the vertex was not present. */

}


