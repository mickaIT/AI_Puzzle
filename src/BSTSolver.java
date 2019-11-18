import java.util.*;
/**
 * Best-First Search takes into account only the heuristic value of a state.
 * It is a greedy algorithm.
 * F(n) = h(n)
 * h(n) - heuristic value of the state
 * It takes ID of an heuristic function to use.
 **/
public class BSTSolver extends PuzzleSolver {

    // For comparing manhattan distance
    private Comparator<PuzzleState> statePriorityComparator = Comparator.comparingInt(PuzzleState::getValue);

    private static BSTSolver instance = new BSTSolver();
    private PriorityQueue<PuzzleState> stateQueue = new PriorityQueue<>(statePriorityComparator);
    private Set<PuzzleState> stateSet = new HashSet<>();

    public BSTSolver() {
    }

    public static BSTSolver getInstance() {
        return instance;
    }


    protected int calculateValue (int heuristicID, PuzzleState newState){
        int value;
        switch (heuristicID) {
            case 1:
                value = newState.heuristic();
                break;
            case 2:
                value = newState.manhattan();
                break;
            default:
                value = 0;
        }
        return value;
    }

    private void bst(PuzzleState state, int heuristicID){

        // Clears memory
        stateQueue.clear();
        stateSet.clear();

        // Adds input state
        stateQueue.add(state);
        stateSet.add(state);

        PuzzleState newState;   // new puzzle state for moves

        while (!stateQueue.isEmpty()){
            state = stateQueue.poll();
            // If goal state
            if(state.isGoalState()){
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
                newState.setValue(calculateValue(heuristicID, newState));
                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move down
            newState = PuzzleState.moveDown(state);
            if (newState != null && !stateSet.contains(newState)) {
                newState.setValue(calculateValue(heuristicID, newState));
                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move left
            newState = PuzzleState.moveLeft(state);
            if (newState != null && !stateSet.contains(newState)) {
                newState.setValue(calculateValue(heuristicID, newState));
                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move right
            newState = PuzzleState.moveRight(state);
            if (newState != null && !stateSet.contains(newState)) {
                newState.setValue(calculateValue(heuristicID, newState));
                stateSet.add(newState);
                stateQueue.add(newState);
            }
        }
    }

    public String solve(Puzzle puzzle, int heuristicID) {

        long startTime = System.currentTimeMillis();    // starts timer
        goal = null;    // goal state is not found at the beginning (null)

        PuzzleState state = new PuzzleState(puzzle);    // creates state to begin with
        bst(state, heuristicID); // starts bst search

        time = System.currentTimeMillis() - startTime;  // calculates time
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        memory = runtime.totalMemory() - runtime.freeMemory();
        return getSequence();   // returns sequence of moves
    }
}
