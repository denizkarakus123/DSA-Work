import java.util.*;
public class A3Q2 {
    
    public static String[] time_pass(String[][] itinerary, String[] cities) {
    String[] answer = new String[cities.length];
    
    //lets build our graph from our itinerary
    Map<String, List<String>> graph = new HashMap<>();
    //graph building 
    for (String[] flight : itinerary) {
        String from = flight[0];
        String to = flight[1];
        graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        graph.computeIfAbsent(to, k -> new ArrayList<>());
    }
    
    //identify cities from which we can fly indefinitely (safe cities, basically cities that are within SCCs of our graph)
    Set<String> safeStartCities = findSafeCities(graph);
    
    //check each city in the input list
    for (int i = 0; i < cities.length; i++) {
        answer[i] = safeStartCities.contains(cities[i]) ? "succeed" : "failed";
    }
    
    return answer;
}

private static Set<String> findSafeCities(Map<String, List<String>> graph) {
    //lets build a reverse graph so that we can "propgate" the unsafe cities
    Map<String, List<String>> reverseGraph = new HashMap<>();
    //also we know that if a city has an out degree of 0, its unsafe 
    Map<String, Integer> outDegree = new HashMap<>();
    //also we need to keep track of the unsafe cities
    Set<String> unsafeCities = new HashSet<>();
    //initialize!
    for (String city : graph.keySet()){
        reverseGraph.put(city, new ArrayList<>());
        outDegree.put(city, 0);
    }
    //build the reverse graph and outdegree map
    for (String city : graph.keySet()){
        List<String> neighbours = graph.get(city);
        outDegree.put(city, neighbours.size());
        for (String neighbour : neighbours){
            reverseGraph.computeIfAbsent(neighbour, k -> new ArrayList<>()).add(city);
        }
    }

    //now we make a queue that initially contains all the unsafe cities (outdegree of 0)
    Queue<String> queue = new LinkedList<>();
    for (String city : outDegree.keySet()){
        if (outDegree.get(city) == 0){
            queue.offer(city);
            unsafeCities.add(city);
        }
    }

    //now is where we use our reverse graph to propogate the unsafe cities 
    while (!queue.isEmpty()){
        String city = queue.poll();
        List<String> neighbours = reverseGraph.get(city);
        for (String neighbour : neighbours){
            //decrease the outdegree of the neighbour
            outDegree.put(neighbour, outDegree.get(neighbour) - 1);
            //if the outdegree is now 0, we can add it to the queue
            if (outDegree.get(neighbour) == 0){
                queue.offer(neighbour);
                unsafeCities.add(neighbour);
            }
        }
    }


    //now we can differentiate between the safe and non safe cities!!
    Set<String> safeCities = new HashSet<>(graph.keySet());
    safeCities.removeAll(unsafeCities);
    
    return safeCities;
}
}
