import java.util.*;

public class A2_Q1 {
    
    //result class, makes it easier in my opinion instead of an array
    private static class Result {
        int minBalls = Integer.MAX_VALUE;
        int minMoves = Integer.MAX_VALUE;
    }
    
    public static int[] game(String[][] board) {
        Result result = new Result();
        
        //make our recursive array 
        Set<String> visited = new HashSet<>();
        exploreMoves(board, 0, result, visited);
        //return result as an array
        return new int[]{result.minBalls, result.minMoves};
    }


    private static List<int[]> findMoves(String[][] board) {
        List<int[]> moves = new ArrayList<>();
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals("o")) {//we found a ball!!
                    //check in all four directions
                    
                    //check up
                    if (i >= 2 && board[i-1][j].equals("o") && board[i-2][j].equals(".")) {
                        moves.add(new int[]{i, j, i-2, j});
                    }
                    
                    //check down
                    if (i + 2 < board.length && board[i+1][j].equals("o") && board[i+2][j].equals(".")) {
                        moves.add(new int[]{i, j, i+2, j});
                    }
                    
                    //check left
                    if (j >= 2 && board[i][j-1].equals("o") && board[i][j-2].equals(".")) {
                        moves.add(new int[]{i, j, i, j-2});
                    }
                    
                    //check right
                    if (j + 2 < board[i].length && board[i][j+1].equals("o") && board[i][j+2].equals(".")) {
                        moves.add(new int[]{i, j, i, j+2});
                    }
                }
            }
        }
        
        return moves;
    }

    
    private static void exploreMoves(String[][] board, int moves, Result result, Set<String> visited) {
        //we want to convert the board to a string so that its easier to check if we have visited this state before
        String board_string = boardToString(board);
        
        //we dont want to visit the same state again 
        if (visited.contains(board_string)) {
            return;
        }
        
        //add the whole board configuration to the visited set, so we can say "hey, we have visited this state before"
        visited.add(board_string);
        
        //find all possible moves on our current board configuration
        List<int[]> possibleMoves = findMoves(board);
        
        //if no moves are possible, we've reached the endpoint of this trial of the game. 
        if (possibleMoves.isEmpty()) {
            int ballsRemaining = countBalls(board);
            
            //we iterate through the code to see if this trial is better than our other trials 
            if (ballsRemaining < result.minBalls) {
                result.minBalls = ballsRemaining;
                result.minMoves = moves;
            } else if (ballsRemaining == result.minBalls && moves < result.minMoves) {
                result.minMoves = moves;
            }
            
            return;
        }
        
        //now we want to try each possible move and see where it takes us
        for (int[] move : possibleMoves) {
            //here we get the starting and ending coordinates of our move
            int fromRow = move[0];
            int fromCol = move[1];
            int toRow = move[2];
            int toCol = move[3];
            
            
            int jumpRow = (fromRow + toRow) / 2;
            int jumpCol = (fromCol + toCol) / 2;
            
            //save original state for backtracking
            String fromOriginal = board[fromRow][fromCol];
            String jumpOriginal = board[jumpRow][jumpCol];
            String toOriginal = board[toRow][toCol];
            
            //do the move!!
            board[fromRow][fromCol] = ".";
            board[jumpRow][jumpCol] = ".";
            board[toRow][toCol] = "o";
            
            //now we want to recursively explore the next move 
            exploreMoves(board, moves + 1, result, visited);
            
            //backtrack, or undo the move, as we only have one board so this is where the backtracking happens
            board[fromRow][fromCol] = fromOriginal;
            board[jumpRow][jumpCol] = jumpOriginal;
            board[toRow][toCol] = toOriginal;
        }
    }
    



 
    
    
    private static int countBalls(String[][] board) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals("o")) {
                    count++;
                }
            }
        }
        return count;
    }
    

    private static String boardToString(String[][] board) {
        StringBuilder sb = new StringBuilder();
        for (String[] row : board) {
            for (String cell : row) {
                sb.append(cell);
            }
        }
        return sb.toString();
    }
}
