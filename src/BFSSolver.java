import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
/**
 * Breadth-First Search (bfs).
 * FIFO Queue is used.
 * It starts at the tree root and explores all of the neighbor nodes at the present depth prior to moving on to
 * the nodes at the next depth level.
 **/
public class BFSSolver extends PuzzleSolver{

    private static BFSSolver instance = new BFSSolver();
    private Set<PuzzleState> stateSet = new HashSet<>(); // HashSet for puzzle states, eliminates duplicates
    private Queue<PuzzleState> stateQueue = new LinkedList<>();  // FIFO Queue for puzzle states

    private BFSSolver() {

    }

    public static BFSSolver getInstance() {
        return instance;
    }

    private void bfs(PuzzleState state) {

        // Clears memory
        stateSet.clear();
        stateQueue.clear();

        // Adds input state
        stateSet.add(state);
        stateQueue.add(state);

        PuzzleState newState;   // new puzzle state for moves

        while (!stateQueue.isEmpty()){

            state = stateQueue.poll();  // takes first element & removes it

            // If goal state
            if (state.isGoalState()) {
                goal = state;
                break;
            }

            // Memory limit check
            if (Runtime.getRuntime().freeMemory() < (.0001) * Runtime.getRuntime().totalMemory()){
                break;
            }

            /** CASES */
            // Move up
            newState = PuzzleState.moveUp(state);
            // Checks if exist and not already visited
            if (newState != null && !stateSet.contains(newState)) {
                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move down
            newState = PuzzleState.moveDown(state);
            if (newState != null && !stateSet.contains(newState)) {
                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move left
            newState = PuzzleState.moveLeft(state);
            if (newState != null && !stateSet.contains(newState)) {
                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move right
            newState = PuzzleState.moveRight(state);
            if (newState != null && !stateSet.contains(newState)) {
                stateSet.add(newState);
                stateQueue.add(newState);
            }
        }
    }

    public String solve(Puzzle puzzle, int h) {

        long startTime = System.currentTimeMillis();    // starts timer
        goal = null;    // goal state is not found at the beginning (null)

        PuzzleState state = new PuzzleState(puzzle);    // creates state to begin with
        bfs(state); // starts bfs search

        time = System.currentTimeMillis() - startTime;  // calculates time
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        memory = runtime.totalMemory() - runtime.freeMemory();
        return getSequence();   // returns sequence of moves
    }
}