import java.rmi.activation.UnknownObjectException;

/**
 * Abstract class, which is a template for Solver classes.
 **/
public abstract class PuzzleSolver {

    // The goal state, when non-null, puzzle is in goal state.
    protected PuzzleState goal; // stores goal state
    protected long time;    // stores time needed to solve
    protected long memory;  // stores memory used to solve
    private static final long MEGABYTE = 1024L * 1024L; // used to translate Bytes to MegaBytes

    public  String solve(Puzzle puzzle, int heuristicID){return "Method \"Solve\" from PuzzleSolver not override";};   // solves the puzzle and returns sequence of moves
    public  String solve(Puzzle puzzle, int heuristicID,char[] order){return "Method \"Solve\" from PuzzleSolver not override";};
    // Function used to change bytes to Megabytes
    private static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    public String getSequence() {
        /**
         * Gets sequence of moves from PuzzleStates and prints all information to the console.
         * It iterates through PuzzleStates and gets their level in a tree, move that was performed, and their parent
         * state. It outputs steps in a reverse order - from goal state to the initial state.
         * It also provides information about time taken, memory used and the final solution sequence.
         */
        // Current state is set to goal state; parent state for printing history.
        try {
            PuzzleState current = goal, parent;
            StringBuilder builder = new StringBuilder();
            // Prints main output
            if (current != null) {
                System.out.println("This is the state sequence (reverse order): ");
                System.out.println("Goal State: ");
                while (true) {
                    // If at top of the tree == initial state
                    if (current.getLevel() == 0) {
                        System.out.println("Program reached the goal state of the puzzle!\n");
                    } else {
                        // Prints current level in a tree
                        System.out.println("Level in tree: " + current.getLevel()); // prints current state
                        System.out.println("Move: " + current.getMove());   // prints movement direction

                    }
                    System.out.println(current.toString()); // prints current state
                    parent = current.getPrev(); // sets parent to current state's previous state

                    // No parent
                    if (parent == null)
                        break;

                    builder.append(current.getMove());  // appends move to solution
                    current = parent;   // sets current state to parent
                }

                System.out.println("This solving technique took: " + time + " ms"); //  Time taken
                System.out.println("Memory used: " + memory + " B\t\t| (" + bytesToMegabytes(memory) + "MB)");
                System.out.println("Solution length: " + goal.getLevel());
                goal = null;
                // Writes sequence of moves in reverse order (correct one)
                return "This is the sequence of moves from initial state to the goal state: " + builder.reverse().toString();
            } else {
                // No solution
                throw new UnknownObjectException("");
            }
        }
        catch (UnknownObjectException uoe)
        {
            System.err.println("No solution found...");
        }
        return "";
    }
}
