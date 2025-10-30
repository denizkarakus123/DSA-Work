import java.util.*;
import java.lang.*;

public class A3Q3 {
    public static double arduino(double[][] locations) {
        double wire = 0.0;
        int n = locations.length;
        //my approach is to make a completely connected graph with weights representing distances 
        //this will be represented by an adjacency matrix 

        //iteration - lets try not precomputing these 
        /*
        double[][] distances = new double[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                distances[i][j] = Math.sqrt(Math.pow(locations[i][0] - locations[j][0], 2) + Math.pow(locations[i][1] - locations[j][1], 2));
            }
        }
        */

        //now we want to create a minimum spanning tree and gradually build by starting with a vertex and adding 
        //the vertex with the smallest length edge connecting it to our current tree
        //we will use Prim's algorithm to do this
        boolean[] visited = new boolean[n];
        double[] minEdge = new double[n];
        Arrays.fill(minEdge, Double.MAX_VALUE);
        minEdge[0] = 0; //start with the first vertex
        for (int i = 0; i < n; i++){
            //find the vertex with the smallest edge that is not visited
            double min = Double.MAX_VALUE;
            int minIndex = -1;

            for (int j = 0; j < n; j++){
                if (!visited[j] && minEdge[j] < min){
                    min = minEdge[j];
                    minIndex = j;
                }
            }
            //add this vertex to our tree
            visited[minIndex] = true;
            wire += Math.sqrt(min); //add the actual length of the edge to our total wire length
            //update the minimum edges for all vertices connected to this vertex
            for (int j = 0; j < n; j++) {
                if (!visited[j]) {
                    double dx = locations[minIndex][0] - locations[j][0];
                    double dy = locations[minIndex][1] - locations[j][1];
                    double distSq = dx * dx + dy * dy; //squared distance
                    if (distSq < minEdge[j]) {
                        minEdge[j] = distSq;
                    }
                }
            }
        }
        //return the total length of wire needed rounded to two decimal places
        wire = Math.round(wire * 100.0) / 100.0;
        return wire;
    }
}
