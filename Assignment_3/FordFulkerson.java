import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		boolean visited[] = new boolean[graph.getNbNodes()];

		//using recursive method because it is a cleaner implementation and we care about accuracy for this q
		boolean found = pathDFSHelper(source, destination, graph, visited, path);

		if (!found){
			path.clear();
		}

		return path;
	}

	private static boolean pathDFSHelper(Integer current, Integer destination, WGraph graph, boolean[] visited, ArrayList<Integer> path){
		//we've visited this node so update
		visited[current] = true;
		//add to path
		path.add(current);

		//if we are at the destination, return true
		if (current == destination){
			return true;
		}
		//otherwise, check all neighbours
		for (Edge edge: graph.getEdges()){
			//if this edge is the one with our current node, check the other node
			if (edge.nodes[0] ==current && edge.weight > 0){
				Integer neighbour = edge.nodes[1];
				//if we haven't visited this neighbour yet, check it
				if (!visited[neighbour]){
					//if we find a path, return true
					if (pathDFSHelper(neighbour, destination, graph, visited, path)){
						return true;
					}
				}
			}
		}
		//if we get here, we didn't find a path so remove the current node from the path
		path.remove(path.size()-1);
		return false;
	}



	public static String fordfulkerson( WGraph graph){
		String answer="";
		int maxFlow = 0;
		
		//create a new graph to store the residual graph
		WGraph residualGraph = new WGraph(graph);

		//add our reverse edges with zero capacity for the residual graph
		ArrayList<Edge> edges = graph.getEdges();
		for (Edge edge : edges){
			int u = edge.nodes[0];
			int v = edge.nodes[1];
			//if reverse edge doesn't exist, add it
			if (residualGraph.getEdge(v, u) == null){
				residualGraph.addEdge(new Edge(v, u, 0));
			}
		}

		//find augmenting path 
		ArrayList<Integer> path = pathDFS(graph.getSource(), graph.getDestination(), residualGraph);
		//while we can find a path, keep augmenting
		while (!path.isEmpty()){
			//find our bottleneck value 
			int bottleneck = Integer.MAX_VALUE;

			//traverse graph to find bottleneck value 
			for (int i = 0; i < path.size() - 1; i++){
				int u = path.get(i);
				int v = path.get(i+1);
				//get the edge weight
				Edge edge = residualGraph.getEdge(u, v);
				//if the edge weight is less than the current bottleneck, update it
				if (edge != null && edge.weight < bottleneck){
					bottleneck = edge.weight;
				}
			}

				//update our residual graph
				//subtract the bottleneck from the forward edge
				//add the bottleneck to the reverse edge
			for (int i = 0; i < path.size() - 1; i++){
				int u = path.get(i);
				int v = path.get(i+1);

				Edge forwardEdge = residualGraph.getEdge(u, v);
				Edge reverseEdge = residualGraph.getEdge(v, u);
				residualGraph.setEdge(u,v, forwardEdge.weight - bottleneck);
				residualGraph.setEdge(v,u, reverseEdge.weight + bottleneck);
			}

			//add this flow to our max flow
			maxFlow += bottleneck;
			//find the next augmenting path
			path = pathDFS(graph.getSource(), graph.getDestination(), residualGraph);
		}

		//create a new graph to store the flow graph
		WGraph flowGraph = new WGraph();
		//keep our same source and sink 
		flowGraph.setSource(graph.getSource());
		flowGraph.setDestination(graph.getDestination());
		

		//make our flow graph 
		for (Edge edge : graph.getEdges()){
			int u = edge.nodes[0];
			int v = edge.nodes[1];

			//flow is equal to the original weight minus the residual weight
			int flow = edge.weight - residualGraph.getEdge(u, v).weight;
			//if edge already exists, update it, and if it doesnt, add it 
			if (flowGraph.getEdge(u, v) != null){
				flowGraph.setEdge(u, v, flow);
			}
			else{
				flowGraph.addEdge(new Edge(u, v, flow));
			}
		}



		answer += maxFlow + "\n" + flowGraph.toString();	
		return answer;
	}
	
}
