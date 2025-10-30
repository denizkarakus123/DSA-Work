import java.util.*;

public class A3Q1 {
    public static int[] saving_frogs(String[][] board) {
        int[] ans = new int[2];
        int rows = board.length;
        int cols = board[0].length;
        
        //build list of entrances and count total food
        List<int[]> entrances = new ArrayList<>();
        int totalFood = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if(board[i][j].equals(".")){
                    //count food cells
                    totalFood++;
                }
                if(board[i][j].length() == 1 && board[i][j].charAt(0) >= 'A' && board[i][j].charAt(0) <= 'W'){
                    //add entrance if on border
                    if(i == 0 || i == rows-1 || j == 0 || j == cols-1){
                        entrances.add(new int[]{i, j});
                    }
                }
            }
        }
        
        //if no food, return zeros
        if(totalFood == 0){
            ans[0] = 0;
            ans[1] = 0;
            return ans;
        }
        
        //if no entrance, then all food is unreachable
        if(entrances.isEmpty()){
            ans[0] = 0;
            ans[1] = totalFood;
            return ans;
        }
        
        //fast path: if only one entrance, run bfs from it
        if(entrances.size() == 1){
            boolean[][] visited = new boolean[rows][cols];
            bfs(board, entrances.get(0)[0], entrances.get(0)[1], visited, rows, cols);
            int reachableFood = 0;
            for (int i = 0; i < rows; i++){
                for (int j = 0; j < cols; j++){
                    if(board[i][j].equals(".") && visited[i][j]){
                        reachableFood++;
                    }
                }
            }
            ans[0] = reachableFood > 0 ? 1 : 0;
            ans[1] = totalFood - reachableFood;
            return ans;
        }
        
        //multiple entrances: use connected component approach
        boolean[][] visited = new boolean[rows][cols];
        int frogsNeeded = 0;
        int unreachableFood = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                //only start a bfs if cell is traversable (food or space) and not visited
                if(!visited[i][j] && (board[i][j].equals(".") || board[i][j].equals(" "))){
                    int[] result = bfs(board, i, j, visited, rows, cols);
                    int foodCount = result[0];
                    boolean touchesEntrance = result[1] == 1;
                    //only consider components that contain food
                    if(foodCount > 0){
                        if(touchesEntrance){
                            frogsNeeded++; //one frog covers this component
                        }
                        else{
                            unreachableFood += foodCount; //food in this component is unreachable
                        }
                    }
                }
            }
        }
        
        ans[0] = frogsNeeded;
        ans[1] = unreachableFood;
        return ans;
    }
    
    private static int[] bfs(String[][] board, int startRow, int startCol, boolean[][] visited, int rows, int cols) {
        //bfs like in class but tweak it to problem statements
        int foodCount = 0;
        boolean touchesEntrance = false;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;
        
        //up, down, left, right
        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        
        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            
            //count food if cell is "."
            if(board[r][c].equals(".")){
                foodCount++;
            }
            
            //check all four neighbors
            for (int[] dir : directions){
                int newRow = r + dir[0];
                int newCol = c + dir[1];
                if(newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols){
                    //if neighbor is traversable (food or space) and not visited, add to queue
                    if(!visited[newRow][newCol] && (board[newRow][newCol].equals(".") || board[newRow][newCol].equals(" "))){
                        visited[newRow][newCol] = true;
                        queue.offer(new int[]{newRow, newCol});
                    }
                    //if neighbor is an entrance, mark that this component touches an entrance
                    else if(board[newRow][newCol].length() == 1 &&
                            board[newRow][newCol].charAt(0) >= 'A' &&
                            board[newRow][newCol].charAt(0) <= 'W'){
                        touchesEntrance = true;
                    }
                }
            }
        }
        
        return new int[]{foodCount, touchesEntrance ? 1 : 0};
    }
}
