import java.util.Arrays;
import java.util.LinkedList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
    
    
public class Board {
    // construct a board from an n-by-n array of blocks
    private int[][] board;
    private int dim;
    private int blankX;
    private int blankY;
    
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)  {
        dim = blocks.length;
        board = copy(blocks);
        
    }
    
    private int[][] copy(int[][] o) {
        int[][] copy = new int[o.length][o.length];
        for (int r=0; r < o.length; r++) {
            for (int c=0; c < o.length; c++) {
                copy[r][c] = o[r][c];
            }
        }
        return copy;
    }
                                      
    // board dimension n
    public int dimension()
    {
        return dim;
    }
    
    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        for (int i=0; i < dim; i++) {
            for (int j=0; j < dim; j++) {
                if (board[i][j] != dim*i+j+1 && board[i][j] != 0) {
                    hamming += 1;
                }
            }
        }
        return hamming;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i=0; i < dim; i++) {
            for (int j=0; j < dim; j++) {
                if (board[i][j] != dim*i+j+1 && board[i][j] != 0) {
                    int correctRow = (int)((board[i][j] - 1) / dim);
                    int correctCol = (int)((board[i][j] - 1) % dim);
//                    StdOut.println("Number: "+board[i][j]+" should be in: ("+correctRow+","+correctCol+") instead it is in: ("+i+","+j+")");
                    int manhattanDistance = Math.abs(i - correctRow) + Math.abs(j - correctCol);
                    manhattan += manhattanDistance;
                }
            }
        }
        return manhattan;
    } 
    
    // is this board the goal board?
    public boolean isGoal() {
        if (this.manhattan() == 0) return true;
        return false;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
 
        int[][] newBlocks = copy(board);
        for (int i=0; i < dim; i++) {
            for (int j=0; j < dim; j++) {
                //check whether the element and the next one do not have the space
                if (j != dim - 1) {
                    if (newBlocks[i][j] != 0 && newBlocks[i][j+1] != 0) {
                        int t = newBlocks[i][j];
                        newBlocks[i][j] = newBlocks[i][j+1];
                        newBlocks[i][j+1] = t;
                        return new Board(newBlocks);
                    }
                }
            }
        }
        return new Board(newBlocks);
    }
    
    //return a copy of the board with position 1 and 2 swapped. A position is a 2D array with positions embedded
    private int[][] swap(int[] p1, int[] p2) {
        int[][] newBlocks = copy(board);
        int t = newBlocks[p1[0]][p1[1]];
        newBlocks[p1[0]][p1[1]] = newBlocks[p2[0]][p2[1]];
        newBlocks[p2[0]][p2[1]] = t;
        return newBlocks;
    }
    
    private int[] findSpace() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (this.board[i][j] == 0) return new int[] {i,j};
            }
        }
        return new int[] {-1,-1};

    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        //check if board is the same dimension
        if (y == this) return true;
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        if (((Board)y).dimension() != dim) return false;
        
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (((Board)y).board[i][j] != this.board[i][j]) return false; 
            }
        }
    
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<Board>();
        int[] spacePos = this.findSpace();
        //check if space is valid across all four movements, then go
        int[][] newPosition = new int[4][2];
        newPosition[0] = new int[] {spacePos[0]-1, spacePos[1]};
        newPosition[1] = new int[] {spacePos[0]+1, spacePos[1]};
        newPosition[2] = new int[] {spacePos[0], spacePos[1]-1};
        newPosition[3] = new int[] {spacePos[0], spacePos[1]+1};
        
        if (spacePos[0] > 0)               neighbors.add(new Board(swap(spacePos, newPosition[0])));
        if (spacePos[0] < dim - 1)         neighbors.add(new Board(swap(spacePos, newPosition[1])));
        if (spacePos[1] > 0)               neighbors.add(new Board(swap(spacePos, newPosition[2])));
        if (spacePos[1] < dim - 1)         neighbors.add(new Board(swap(spacePos, newPosition[3])));
        
        return neighbors;
    }
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(dim+"\n");
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++)
                str.append(String.format("%2d ", board[row][col]));
            str.append("\n");
        }
        return str.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
         
        Board initial = new Board(blocks);
        StdOut.println(initial);
        StdOut.println(initial.twin());
    } // unit tests (not graded)
}
