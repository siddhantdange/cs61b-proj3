/* WUGraph.java */

package graph;

import dict.*;
import set.*;
import list.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
	
	HashTableChained aList; 
	HashTableChained eList; 
	DList keys;
	int edges;

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph(){
	  this.aList = new HashTableChained();   
	  this.eList = new HashTableChained();
	  this.keys = new DList();
	  this.edges = 0;
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
	  return this.aList.size();
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
	  return this.edges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices(){
	  return new Object[9];
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex){
	  
	  if(this.aList.find(vertex) == null){
		  this.aList.insert(vertex, new DList());
	  }
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex){
	  DList list = (DList)this.aList.remove(vertex).value();
	  if(list != null){
		  try{
			  DListNode first = (DListNode)list.front();
			  while(first != null){

				  try{
					  VertexPair key = new VertexPair(vertex, first.item());
					  VertexPair pair = (VertexPair)((VertexPair)this.eList.find(key).value()).object1;   
					  if(pair != null){
						  if(((DListNode)pair.object1).item().equals(vertex)){
							  ((DListNode)pair.object2).remove();
						  } else{
							  ((DListNode)pair.object1).remove();
						  }
					  }
					  
					  first = (DListNode)first.next();
				  } catch(Exception e){
					  break;
				  }
			  }
		  } catch(Exception e){}
	  }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
	  return this.aList.find(vertex) != null;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex){
	  DList list = (DList)this.aList.find(vertex).value();   
	  if(list != null){
		  return list.length();
	  }
	  
	  return 0;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex){
	  Neighbors one = new Neighbors();
	  Object[] neighborList;
	  int[] weights;
	  
	  DList neighbors = (DList)this.aList.find(vertex).value();   
	  neighborList = new Object[neighbors.length()];
	  weights = new int[neighborList.length];
	  int counter = 0;
	  try{
		  DListNode first = (DListNode)neighbors.front();
		  while(first != null){
			  try{
				  neighborList[counter] = first;
				  VertexPair key = new VertexPair(vertex, first);   
				  weights[counter] = (Integer)((VertexPair)this.eList.find(key).value()).object2;   
				  
				  first = (DListNode)first.next();
				  counter++;
			  } catch(Exception e){
				  break;
			  }
		  }
	  } catch(Exception e){}
	  
	  
	  one.neighborList = neighborList;
	  one.weightList = weights;
	  return one;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight){
	  VertexPair pair = new VertexPair(u, v);
	  DListNode first = (DListNode)((DList)this.aList.find(u).value()).insertBack(v);   
	  DListNode sec = (DListNode)((DList)this.aList.find(v).value()).insertBack(u);
	  
	  VertexPair option = new VertexPair(new VertexPair(first, sec), weight);
	  this.eList.insert(pair, option);
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v){
	  VertexPair pair = (VertexPair)this.eList.remove(new VertexPair(u,v)).value();   
	  if(pair != null){
		  VertexPair nodes = (VertexPair)pair.object1;
		  
		  ((DListNode)nodes.object1).remove();
		  ((DListNode)nodes.object2).remove();
	  }
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v){
	  return this.eList.find(new VertexPair(u,v)) != null;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v){
	  VertexPair pair = (VertexPair)this.eList.find(new VertexPair(u,v)).value();   
	  if(pair != null){
		  return (Integer)pair.object2;
	  }
	  
	  return 0;
  }
}
