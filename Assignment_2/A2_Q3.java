import java.util.*;

public class A2_Q3 {
    public static String directions(int[] distances){
        int max_stairs = 1000;
        int num_steps = distances.length;

        //now we want to create our dynamic programming table
        //this is an array of hashmaps where each index represents a step number (0 to n)
        Map<Integer, Integer>[] dp = new HashMap[num_steps + 1];
        
        //at each state, ie, step and height, the path table records which direction we took 
        //to get there with the minimum maximum heeight
        Map<Integer, Character>[] path = new HashMap[num_steps + 1];

        for (int i = 0; i <= num_steps; i++) {
            dp[i] = new HashMap<>();
            path[i] = new HashMap<>();
        }

        //this is our base case, at step 0, we are at height 0 with max height 0
        dp[0].put(0,0);


        for (int i = 0; i < num_steps; i++){
            for (Map.Entry<Integer, Integer> entry : dp[i].entrySet()){
                int currentHeight = entry.getKey();
                int currentMaxHeight = entry.getValue();

                //now we want to try going up
                int upwardsHeight = currentHeight + distances[i];
                if (upwardsHeight <= max_stairs){
                    int newMaxHeight = Math.max(currentMaxHeight, upwardsHeight);
                    //check against our existing solution and update if better
                    if (!dp[i + 1].containsKey(upwardsHeight) || dp[i + 1].get(upwardsHeight) > newMaxHeight){
                        dp[i + 1].put(upwardsHeight, newMaxHeight);
                        path[i + 1].put(upwardsHeight, 'U');
                    }

                }
                //now we want to try going down
                int downwardsHeight = currentHeight - distances[i];
                if (downwardsHeight >= 0){
                    int newMaxHeight = Math.max(currentMaxHeight, downwardsHeight);
                    //check against our existing solution and update if better
                    if (!dp[i + 1].containsKey(downwardsHeight) || dp[i + 1].get(downwardsHeight) > newMaxHeight){
                        dp[i + 1].put(downwardsHeight, newMaxHeight);
                        path[i + 1].put(downwardsHeight, 'D');
                    }
                }
            }
        }
        if (!dp[num_steps].containsKey(0)){
            return "IMPOSSIBLE";
        }

        //reconstruct our path
        StringBuilder result = new StringBuilder();
        int height = 0;
        
        //start from the last step and go backwards
        for (int i = num_steps; i > 0; i--) {
            char direction = path[i].get(height);
            result.insert(0, direction);
            
            //now we update our heights based on the direction we came from 
            if (direction == 'U') {
                height -= distances[i-1];
            } else {
                height += distances[i-1];
            }
        }
        
        return result.toString();
    }
}
