import java.util.LinkedList;
import java.util.ArrayList;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    private Move solution;
    
    public Solver(Board initial)  {
        //take board and search through neighbors, pick one with lowest manhattan distance. Ignore same neighbors
        //stop searching until isGoal
        //detect unsolvable by seeing if the twin is solved 
        //whats the difference in speed between a priority queue based search and a simple loop based one?
        //expand the priority queues until the minimum has a zero manhattan distance
//        solution = new ArrayList<Board>();
        
        if (initial == null) throw new IllegalArgumentException();
        
        MinPQ<Move> pq = new MinPQ<Move>();
        pq.insert(new Move(initial));
        
        MinPQ<Move> twinpq = new MinPQ<Move>(); 
        twinpq.insert(new Move(initial.twin()));
        
        while(true) {
            //keep inserting into both priority queue until the minimum is the game board
            Move originalPathMin = pq.delMin();
            if (originalPathMin.board.isGoal()) {
                solution = originalPathMin;
                return;
            }
//            StdOut.println(originalPathMin.board);
            
            for (Board b: originalPathMin.board.neighbors()) {
                if (originalPathMin.previous == null || !originalPathMin.previous.equals(b)) {
                    pq.insert(new Move(b, originalPathMin));
                }
                
            }
            
            
            Move twinPathMin = twinpq.delMin();
            if (twinPathMin.board.isGoal()) {
                solution = null;
                return;
            }
            for (Board b: twinPathMin.board.neighbors()) {
                if (twinPathMin.previous == null || !twinPathMin.previous.equals(b)) {
                    twinpq.insert(new Move(b, twinPathMin));  
                }
            }
        }
        
    }         
    // is the initial board solvable?
    public boolean isSolvable()  {
        return solution != null;        
    }          
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!this.isSolvable()) return -1;
        return solution.moves;
    }                    
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!this.isSolvable()) return null;
        
        Stack<Board> moves = new Stack<Board>();
        Move solutionIterator = solution;
        while (solutionIterator != null) {
            moves.push(solutionIterator.board);
            solutionIterator = solutionIterator.previous;
        }
        return moves;
    }
    
    public static void main(String[] args) { 
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
         
        Board initial = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    
    private class Move implements Comparable<Move> {
        private Board board;
        private int moves;
        private Move previous;
        
        public Move(Board currentBoard) {
            this.board = currentBoard;
            this.moves = 0; 
        }
        

        public Move(Board currentBoard, Move previousMove) {
            this.board = currentBoard;
            this.moves = previousMove.moves+1;
            this.previous = previousMove;
        }
        
        public int compareTo(Move otherMove) {
            return this.board.manhattan()-otherMove.board.manhattan() + this.moves-otherMove.moves;
        }
    }
}