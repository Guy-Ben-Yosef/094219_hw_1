/**
 * This class represents the state of the board at any given time. It has the following methods:
 * 1. getGoalBoard() - This method generates a Board with Tiles numbered from 1 to (row * col - 1) and a blank Tile
 *                     represented by 0, that is a Board matches the final board of the game (goal board).
 * 2. isGoal()       - This method checks if the current board's state is the goal state and returns a boolean value.
 * 3. actions()      - This method returns an array of all possible actions that can be taken from the current state.
 * 4. findEmptyTileIndexes() - This method returns the indexes of the empty Tile in the current state.
 * 5. result()       - This method returns the state after an action is committed.
 */
public class State {
    Tile[][] state;
    int[] emptyTileIndexes;
    Board GOAL_BOARD = getGoalBoard();;

    /**
     * Constructor for State
     * @param board the current board
     */
    public State(Tile[][] board){
        this.state = board;
        this.emptyTileIndexes = findEmptyTileIndexes();
    }

    /**
     * This method generates a Board with Tiles numbered from 1 to (row * col - 1) and a blank Tile represented by 0,
     * that is a Board matches the final board of the game (goal board).
     */
    public static Board getGoalBoard(){
        Board goalBoard = new Board();
        // Initialize a counter to keep track of the Tile number
        int count = 1;

        // Loop through each row and column of the board
        for (int i = 0; i < Board.row; i++){
            for (int j = 0; j < Board.col; j++){
                // Check if the current Tile should be numbered or blank
                if (count < Board.row * Board.col){
                    goalBoard.board[i][j] = new Tile(count);
                } else {
                    goalBoard.board[i][j] = new Tile(0);
                }
                count ++;
            }
        }
        return goalBoard;
    }

    /**
     * Does not get any parameters as input, it is checking if the current board's state is the goal state and returns
     * boolean value
     * @return boolean
     */
    public boolean isGoal(){
        if (this.equals(GOAL_BOARD)){
            return true;
        }
        return false;
    }

    /**
     * This method does not get any parameters as input, it is returning all the possible actions from the present state
     * of the board as an array of `PossibleDirection` enum.
     * @return PossibleDirection[]
     */
    public PossibleDirection[] actions() {
        // Find the indexes of the empty tile
        int[] emptyTileIndexes = findEmptyTileIndexes();
        int emptyTileI = emptyTileIndexes[0];
        int emptyTileJ = emptyTileIndexes[1];

        // Initialize int array with the size of 1x4 of zeros
        int[] possibleActions = new int[4];  // {up, down, right, left}

        // Find the possible actions
        if (emptyTileI > 0) {
            possibleActions[0] = 1;
        } else if (emptyTileI < Board.row - 1) {
            possibleActions[1] = 1;
        } else if (emptyTileJ > 0) {
            possibleActions[2] = 1;
        } else if (emptyTileJ < Board.col - 1) {
            possibleActions[3] = 1;
        }

        // Calculate the sum of `possibleActions` array
        int sum = 0;
        for (int i = 0; i < possibleActions.length; i++){
            sum += possibleActions[i];
        }

        // Initialize an array of `PossibleDirection` enum with the size of `sum`
        PossibleDirection[] actions = new PossibleDirection[sum];

        // Fill the `actions` array with the possible actions
        int counter = 0;
        for (int i = 0; i < possibleActions.length; i++) {
            if (possibleActions[i] == 1) {
                actions[counter] = PossibleDirection.values()[i];
                counter++;
            }
        }
        return actions;
    }

    /**
     * Finds the indexes of the empty tile of a certain state. If this state does not have an empty tile, it returns
     * null.
     * @return int[]
     */
    private int[] findEmptyTileIndexes(){
        for (int i = 0; i < Board.row; i++){
            for (int j = 0; j < Board.col; j++){
                if (this.state[i][j].get() == 0){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /**
     * Represents the updated state of a game after performing an action on a tile.
     *
     * @param action The action to be performed on the tile.
     * @return The updated state of the game as a State object.
     */
    public State result(Action action) {
        // Get the current position of the tile that will be moved
        int i = action.i;
        int j = action.j;

        // Get the tile that will be moved
        Tile movingTile = this.state[i][j];

        // Calculate the new position of the tile based on the action
        int[] newIndexes = action.actionAsNewIndexes();

        // Move the tile to the new position and replace the old position with an empty tile
        this.state[newIndexes[0]][newIndexes[1]] = movingTile;
        this.state[i][j] = new Tile(0);

        // Update the empty tile indexes
        this.emptyTileIndexes = newIndexes;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof State)) {
            return false;
        }
        State otherState = (State) other;
        return board.equals(otherState.board);
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }
}
