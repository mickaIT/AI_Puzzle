import java.util.Random;
/**
 * This class holds initial board loaded from file and is used to invoke all the solving methods.
 * It is also used in version of the program with randomized starting board. (function disabled)
 * */

public class Puzzle {

    private static Puzzle instance = new Puzzle();  // A static instance of Puzzle for use throughout package.
    private int size;   // The puzzle length
    private int[][] puzzleArray;    // Array of puzzle numbers
    private static int[][] goalState;   // Goal state with 0 at [0][0]
    private int zeroColumn; // 0 puzzle column index
    private int zeroRow;    // 0 puzzle row index
    private int level;  // The level of this puzzle state in the tree
    private boolean isGoalState;    // Flag telling if the state is goal

    /** CONSTRUCTORS */
    public Puzzle(){
        this(4);    // same as the maximum row length of a board
    }

    public Puzzle(int sizeInput){
        initialize(sizeInput);
    }

    // Initializes goal state (0 is at position [0][0])
    public void initialize(int sizeInput){

        this.size = sizeInput;  // sets size
        puzzleArray = new int[size][size];  // makes array
        goalState = new int[size][size];    // makes goal state array

        for (int i = 0; i < size; i++) { //wypełnia prawidłowo goal state i puzzle array
            for (int j = 0; j < size; j++){
                puzzleArray[i][j] = (i * size) + j;
                goalState[i][j] = (i * size) + j;
            }
        }
        // Sets 0's indexes
        setZeroRow(0);
        setZeroColumn(0);
        isGoalState = true; // initialize as goal state
    }

    // Initializes initial board / custom state
    public void initialize(int[][] puzzle, int sizeInput){
        this.size = sizeInput;  // sets size
        puzzleArray = new int[size][size];  // makes array
        // Copies custom numbers
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                puzzleArray[i][j] = puzzle[i][j];   // fills array
                // Looks for 0
                if(puzzle[i][j] == 0){
                    setZeroRow(i);
                    setZeroColumn(j);
                }
            }
        }
        this.isGoalState = isGoalState();   // sets goal state
    }

    /** FUNCTIONS */
    public boolean isGoalState(){

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                // If value is not the same as in the goalState1
                if (puzzleArray[i][j] != goalState[i][j]){

                    isGoalState = false;
                    return false;
                }
            }
        }
        // All numbers in correct positions so it is a goal state
        return isGoalState;
    }

    /** RANDOM START */
    public void randomize(){

        Random random = new Random();

        int N = 20; // Number of shuffles from goal state
        for (int i = 0; i < size * N; i++) {
            // Random int from 0 to 4
            switch (random.nextInt(4)) {
                // Move left
                case (0):
                    // Move if possible
                    if (moveLeft()) {
                    }
                    else {
                        i--;    // wasn't moved so shuffle counter back
                    }
                    break;

                // Move right
                case (1):
                    // Move if possible
                    if (moveRight()) {
                    }
                    else {
                        i--;    // wasn't moved so shuffle counter back
                    }
                    break;

                // Move up
                case (2):
                    // Move if possible
                    if (moveUp()) {
                    }
                    else {
                        i--;    // wasn't moved so shuffle counter back
                    }
                    break;

                // Move down
                case (3):
                    // Move if possible
                    if(moveDown()){
                    }
                    else {
                        i--;    // wasn't moved so shuffle counter back
                    }
                    break;
            }
        }
        // If still in goal state
        if (isGoalState())
            randomize();
    }

    public boolean moveLeft(){

        // Checks if can move
        if (getZeroColumn() <= 0)
            return false;

        int temp = puzzleArray[zeroRow][zeroColumn - 1];
        puzzleArray[zeroRow][zeroColumn - 1] = 0;
        puzzleArray[zeroRow][zeroColumn] = temp;
        setZeroColumn(getZeroColumn() - 1);
        return true;
    }

    public boolean moveRight(){

        // Checks if can move
        if (getZeroColumn() >= getSize() - 1)
            return false;


        int temp = puzzleArray[zeroRow][zeroColumn + 1];
        puzzleArray[zeroRow][zeroColumn + 1] = 0;
        puzzleArray[zeroRow][zeroColumn] = temp;
        setZeroColumn(getZeroColumn() + 1);
        return true;
    }

    public boolean moveUp(){

        // Checks if can move
        if (getZeroRow() <= 0)
            return false;

        int temp = puzzleArray[zeroRow - 1][zeroColumn];
        puzzleArray[zeroRow - 1][zeroColumn] = 0;
        puzzleArray[zeroRow][zeroColumn] = temp;
        setZeroRow(getZeroRow() - 1);
        return true;
    }

    public boolean moveDown(){

        // Checks if can move
        if(getZeroRow() >= getSize() - 1)
            return false;

        int temp = puzzleArray[zeroRow + 1][zeroColumn];
        puzzleArray[zeroRow + 1][zeroColumn] = 0;
        puzzleArray[zeroRow][zeroColumn] = temp;
        setZeroRow(getZeroRow() + 1);
        return true;
    }

    /** GETTERS */
    public static Puzzle getInstance(){
        return instance;
    }

    public int getLevel(){
        return this.level;
    }

    public int getSize(){

        return this.size;
    }

    public int[][] getPuzzleArray(){

        return this.puzzleArray;
    }

    public int getZeroRow(){
        return this.zeroRow;
    }

    public int getZeroColumn(){
        return this.zeroColumn;
    }

    public int getNumber(int row, int column){
        return puzzleArray[row][column];
    }

    /** SETTERS */
    public void setLevel(int n){
        this.level = n;
    }

    public void setZeroColumn(int column){
        this.zeroColumn = column;
    }

    public void setZeroRow(int row){
        this.zeroRow = row;
    }

    /** OVERRIDES  */
    @Override
    public String toString(){

        StringBuilder builder = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        for(int n = 0; n < puzzleArray.length; n++){
            for (int j = 0; j < puzzleArray[n].length; j++){
                builder.append(puzzleArray[n][j] + " ");
            }
            builder.append(newLine);    // new line
        }
        return builder.toString();
    }

    @Override
    public int hashCode(){
        return this.toString().hashCode();  // hashcode based on string
    }

    @Override
    public boolean equals(Object o) {

        // Checks if a Puzzle
        if (o instanceof Puzzle) {
            Puzzle puzzle = (Puzzle) o;

            // Compares sizes
            if (puzzle.getSize() != this.getSize())
                return false;

            // Checks if arrays are the same
            int[][] tempArray = puzzle.getPuzzleArray();

            for (int i = 0; i < getSize(); i++){
                for (int j = 0; j < getSize(); j++){
                    if (tempArray[i][j] != puzzleArray[i][j])
                        return false;
                }
            }
            return true;
        }
        return false;   // not a Puzzle
    }
}