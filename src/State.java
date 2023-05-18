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
    Board board;
    int[] emptyTileIndexes;

    /**
     * Constructor for State
     * @param board the current board
     */
    public State(Board board, int[] emptyTileIndexes){
        this.board = board;
        this.emptyTileIndexes = emptyTileIndexes;
    }

    public State(Board board){
        this.board = board;
        this.emptyTileIndexes = findEmptyTileIndexes();
    }

    /**
     * Does not get any parameters as input, it is checking if the current board's state is the goal state and returns
     * boolean value
     * @return boolean
     */
    public boolean isGoal(){
        Board thisBoard = this.board;
        if (thisBoard.equals(Board.goalBoard)){
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
        if (emptyTileI < this.board.row - 1) {
            possibleActions[0] = 1;  // UP is possible
        }
        if (emptyTileI > 0) {
            possibleActions[1] = 1;  // DOWN is possible
        }
        if (emptyTileJ > 0) {
            possibleActions[2] = 1;  // RIGHT is possible
        }
        if (emptyTileJ < this.board.col - 1) {
            possibleActions[3] = 1;  // LEFT is possible
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
        for (int i = 0; i < this.board.row; i++){
            for (int j = 0; j < this.board.col; j++){
                if (this.board.tiles[i][j].get() == 0){
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
        int[] copiedEmptyTileIndexes = {this.emptyTileIndexes[0], this.emptyTileIndexes[1]};
        State newState = new State(this.board.copy(), copiedEmptyTileIndexes);

        // Get the current position of the tile that will be moved
        int i = action.i;
        int j = action.j;

        // Get the tile that will be moved
        Tile targetTile = action.tile;

        // Move the tile to the new position and replace the old position with an empty tile
        newState.board.tiles[emptyTileIndexes[0]][emptyTileIndexes[1]].set(targetTile.get());
        newState.board.tiles[i][j].set(0);

        // Update the empty tile indexes
        newState.emptyTileIndexes[0] = i;
        newState.emptyTileIndexes[1] = j;
        return newState;
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
