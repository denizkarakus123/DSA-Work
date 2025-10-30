import java.util.*;

/****************************
*
* COMP251 template file
*
* Assignment 1, Question 3
*
*****************************/

public class A1_Q3 {
	
	public static ArrayList<String> Discussion_Board(String[] posts){
		Map<String, Integer> wordCount = new HashMap<String, Integer>();
		Map<String, Set<String>> userWordMap = new HashMap<String, Set<String>>();
		ArrayList<String> result = new ArrayList<String>();
		Set<String> uniqueUsers = new HashSet<>();

		for (String post : posts){
			//split up our post int user and message
			String[] postSplit = post.split(" ",2);
			//if empty, skip
			if (postSplit.length < 2){
				continue;
			}
			String user = postSplit[0];
			String[] message = postSplit[1].split(" ");
			//add words to our wordCount map, and userWordMap
			for (String word : message){
				//add user to our userWordMap if they don't exist
				userWordMap.putIfAbsent(word, new HashSet<String>());
				wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
				userWordMap.get(word).add(user);
				uniqueUsers.add(user);
			}
		}
		//we want to now only add the words that are said by every user to our result
		int numUsers = uniqueUsers.size();
		for (String word : wordCount.keySet()){
			if (userWordMap.get(word).size() == numUsers){
				result.add(word);
			}
		}
		result.sort((a, b) -> {
			int freqA = wordCount.get(a);
			int freqB = wordCount.get(b);
			if (freqA == freqB) {
				return a.compareTo(b); // Lexicographic order for ties
			}
			return Integer.compare(freqB, freqA); // Higher frequency first
		});

		return result;
	}

}
