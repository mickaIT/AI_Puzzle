public class PuzzleState {    // implements Comparable due to A*

    private int size; // size of an array
    private int[][] puzzleArray; // array of puzzle numbers
    private int zeroColumn; // index of 0
    private int zeroRow;    // index of 0
    private int level;  // this state's level in a tree
    private boolean isGoalState;    // true if it is a goal state
    private char move;  // move to get to this puzzle state
    private PuzzleState prev;   // previous state
    private int value;      // stores the heuristic value of the state

    /**
     * CONSTRUCTORS
     **/
    // Makes new puzzle state from puzzle instance, used during moves/ changing parents
    PuzzleState() {
        this(Puzzle.getInstance());
    }

    // Constructor to make a new puzzle state from a puzzle
    public PuzzleState(Puzzle puzzle) {

        this.size = puzzle.getSize();   // sets the same size
        puzzleArray = new int[size][size];  // makes new puzzle state array
        this.level = puzzle.getLevel(); // sets level in a  tree

        // Copies values
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                puzzleArray[i][j] = puzzle.getNumber(i, j);
            }
        }
        this.zeroRow = puzzle.getZeroRow();
        this.zeroColumn = puzzle.getZeroColumn();
        this.isGoalState = puzzle.isGoalState();
    }

    /**
     * FUNCTIONS
     **/
    // Copies state
    private void copy(PuzzleState state) {

        this.size = state.getSize();
        this.zeroRow = state.getZeroRow();
        this.zeroColumn = state.getZeroColumn();
        this.isGoalState = state.getGoalState();
        puzzleArray = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                puzzleArray[i][j] = state.getNumber(i, j);
            }
        }
    }

    // Checks if a goal state & sets the flag
    public boolean isGoalState() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                // If value is not the same as in the goal state
                if (puzzleArray[i][j] != (i * getSize() + j)) {

                    isGoalState = false;
                    return false;
                }
            }
        }

        // All numbers in correct positions so it is a goal state
        isGoalState = true;
        return true;
    }

    /**
     * HEURISTICS
     **/
    // Methods which allow to compare value of states in A* & Best-First Search
    // Calculates the cost based on manhattan distance to a goal state
    public int manhattan(){

        int manhattanDistanceSum = 0;
        int[][] sArray = this.getPuzzleArray();

        for (int x = 0; x < this.getSize(); x++){
            for (int y = 0; y < this.getSize(); y++){
                int value = sArray[x][y];
                if (value != 0){    // omits 0 tile
                    int goalX = (value - 1) / this.getSize();
                    int goalY = (value - 1) % this.getSize();
                    int dx = x - goalX;
                    int dy = y - goalY;
                    manhattanDistanceSum += Math.abs(dx) + Math.abs(dy);
                }
            }
        }
        return manhattanDistanceSum;
    }
    // Calculates the cost based on number of misplaced tiles
    public int heuristic(){

        int pathLength = 0;
        int[][] sArray = this.getPuzzleArray();

        for (int i = 0; i < this.getSize(); i++){
            for (int j = 0; j < this.getSize(); j++){
                if (sArray[i][j] != (i * this.getSize() + j)){
                    pathLength += 1;
                }
            }
        }
        return pathLength;
        //return 0;
    }

    /**
     * MOVEMENTS
     **/
    public static PuzzleState moveLeft(PuzzleState s) {

        // Checks if the edge
        if (s.getZeroColumn() <= 0)
            return null;

        // Clears the next state
        PuzzleState nextState = null;

        // Attempt to make a new instance of the input puzzle state
        try {
            // Set the next state to the new instance of the input state.
            nextState = s.getClass().newInstance();
        } catch (InstantiationException e) {
            System.err.println("Error creating a new down-shifted puzzle state.");
        } catch (IllegalAccessException e) {
            System.err.println("Error accessing new puzzle instance.");
        }

        nextState.copy(s);  // copy contents of state

        int temp = s.getNumber(s.getZeroRow(), s.getZeroColumn() - 1);  // stores number on the left
        // Swaps numbers
        nextState.setNumber(s.getZeroRow(), s.getZeroColumn() - 1, 0);
        nextState.setNumber(s.getZeroRow(), s.getZeroColumn(), temp);
        nextState.setZeroColumn(s.getZeroColumn() - 1); // changes 0's index

        nextState.setMove('L'); // sets move to Left
        // Changes previous state to input state
        nextState.setPrev(s);
        nextState.setLevel(s.getLevel() + 1);

        return nextState;
    }

    public static PuzzleState moveRight(PuzzleState s) {

        // Checks if the edge
        if (s.getZeroColumn() >= s.getSize() - 1)
            return null;

        // Clears the next state
        PuzzleState nextState = null;

        // Attempt to make a new instance of the input puzzle state
        try {
            // Set the next state to the new instance of the input state.
            nextState = s.getClass().newInstance();
        } catch (InstantiationException e) {
            System.err.println("Error creating a new down-shifted puzzle state.");
        } catch (IllegalAccessException e) {
            System.err.println("Error accessing new puzzle instance.");
        }

        nextState.copy(s);  // copy contents of state

        int temp = s.getNumber(s.getZeroRow(), s.getZeroColumn() + 1);  // stores number on the right
        // Swaps numbers
        nextState.setNumber(s.getZeroRow(), s.getZeroColumn() + 1, 0);
        nextState.setNumber(s.getZeroRow(), s.getZeroColumn(), temp);
        nextState.setZeroColumn(s.getZeroColumn() + 1); // changes 0's index
        nextState.setMove('R'); // sets move to Right

        // Changes previous state to input state
        nextState.setPrev(s);
        nextState.setLevel(s.getLevel() + 1);

        return nextState;
    }

    public static PuzzleState moveUp(PuzzleState s) {

        // Checks if the edge
        if (s.getZeroRow() <= 0)
            return null;

        // Clears the next state
        PuzzleState nextState = null;

        // Attempt to make a new instance of the input puzzle state
        try {
            // Set the next state to the new instance of the input state.
            nextState = s.getClass().newInstance();
        } catch (InstantiationException e) {
            System.err.println("Error creating a new down-shifted puzzle state.");
        } catch (IllegalAccessException e) {
            System.err.println("Error accessing new puzzle instance.");
        }

        nextState.copy(s);  // copy contents of state

        int temp = s.getNumber(s.getZeroRow() - 1, s.getZeroColumn());  // stores number above

        // Swaps numbers
        nextState.setNumber(s.getZeroRow() - 1, s.getZeroColumn(), 0);
        nextState.setNumber(s.getZeroRow(), s.getZeroColumn(), temp);
        nextState.setZeroRow(s.getZeroRow() - 1);   // changes 0's index

        nextState.setMove('U'); // sets move to Up

        // Changes previous state to input state
        nextState.setPrev(s);
        nextState.setLevel(s.getLevel() + 1);

        return nextState;
    }

    public static PuzzleState moveDown(PuzzleState s) {

        // Checks if the edge
        if (s.getZeroRow() >= s.getSize() - 1)
            return null;

        // Clears the next state
        PuzzleState nextState = null;

        // Attempt to make a new instance of the input puzzle state
        try {
            // Set the next state to the new instance of the input state.
            nextState = s.getClass().newInstance();
        } catch (InstantiationException e) {
            System.err.println("Error creating a new down-shifted puzzle state.");
        } catch (IllegalAccessException e) {
            System.err.println("Error accessing new puzzle instance.");
        }

        nextState.copy(s);  // copy contents of state

        int temp = s.getNumber(s.getZeroRow() + 1, s.getZeroColumn());  // stores number below

        // Swaps numbers
        nextState.setNumber(s.getZeroRow() + 1, s.getZeroColumn(), 0);
        nextState.setNumber(s.getZeroRow(), s.getZeroColumn(), temp);
        nextState.setZeroRow(s.getZeroRow() + 1);   // changes 0's index
        nextState.setMove('D'); // sets move to Down

        // Changes previous state to input state
        nextState.setPrev(s);
        nextState.setLevel(s.getLevel() + 1);

        return nextState;
    }

    /**
     * GETTERS
     **/
    public int getLevel() {
        return this.level;
    }

    public int getSize() {

        return this.size;
    }

    public int[][] getPuzzleArray() {

        return this.puzzleArray;
    }

    public int getZeroColumn() {
        return this.zeroColumn;
    }

    public int getZeroRow() {
        return this.zeroRow;
    }

    public PuzzleState getPrev() {
        return prev;
    }

    public boolean getGoalState() {
        return this.isGoalState;
    }

    // Needed for assigning manhattan distance for comparison in A*
    public int getValue() {
        return value;
    }

    // Get the direction of the move to get to this state.
    public char getMove() {
        return this.move;
    }

    public int getNumber(int row, int column) {
        return this.puzzleArray[row][column];
    }

    /**
     * SETTERS
     **/
    public void setLevel(int n) {
        this.level = n;
    }

    private void setMove(char c) {
        this.move = c;
    }

    private void setPrev(PuzzleState s) {
        this.prev = s;
    }

    public void setZeroColumn(int column) {
        this.zeroColumn = column;
    }

    public void setZeroRow(int row) {
        this.zeroRow = row;
    }

    public void setNumber(int row, int column, int number) {
        /* Sets the value at the specified index of the puzzle to the input number. */
        this.puzzleArray[row][column] = number;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * OVERRIDES
     **/
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        for (int n = 0; n < puzzleArray.length; n++) {
            for (int j = 0; j < puzzleArray[n].length; j++) {
                builder.append(puzzleArray[n][j] + " ");
            }
            builder.append(newLine); // new line
        }
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();  // generates hash code based on a string
    }

    @Override
    public boolean equals(Object o) {

        // Checks if it is PuzzleState
        if (o instanceof PuzzleState) {
            PuzzleState state = (PuzzleState) o;

            // Compares sizes
            if (state.getSize() != this.getSize())
                return false;

            // Checks if numbers at indexes are the same
            for (int i = 0; i < getSize(); i++) {
                for (int j = 0; j < getSize(); j++) {
                    if (state.getNumber(i, j) != puzzleArray[i][j])
                        return false;
                }
            }
            return true;
        }
        return false; // not a PuzzleState
    }
}