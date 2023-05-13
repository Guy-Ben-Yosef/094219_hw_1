public class State {
    Tile[][] state;
    static Board GOAL_BOARD = getGoalBoard();;

    public State(Tile[][] board){
        this.state = board;

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
     * Does not get any parameters as input, it is checking if the current board's state is the goal state and returns boolean value
     * @return boolean
     */
    public boolean isGoal(){
        if (this.equals(GOAL_BOARD)){
            return true;
        }
        return false;
    }

    /**
     * Does not get any parameters as input, it is returning all the possible actions from the present state of the
     * board as an array of strings
     * @return String[]
     */
    public String[] actions() {
        // Find the indexes of the empty tile
        int[] emptyTileIndexes = findEmptyTileIndexes();
        int emptyTileI = emptyTileIndexes[0];
        int emptyTileJ = emptyTileIndexes[1];

        boolean up = false;
        boolean down = false;
        boolean right = false;
        boolean left = false;

        // Find the possible actions
        if (emptyTileI > 0) {
            down = true;
        } else if (emptyTileI < Board.row - 1) {
            up = true;
        } else if (emptyTileJ > 0) {
            right = true;
        } else if (emptyTileJ < Board.col - 1) {
            down = true;
        }

        // TODO: `actions` function should return an array of strings
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
     * Gets the action as input and returns the result state
     * @param action
     * @return State
     */
    public State result(Action action){
        State resultState = new State();
        resultState.board = this.board;
        int row = 0;
        int col = 0;
        for (int i = 0; i < this.board.row; i++){
            for (int j = 0; j < this.board.col; j++){
                if (this.board.board[i][j].get() == 0){
                    row = i;
                    col = j;
                }
            }
        }
        if (action == "up"){
            if (row == 0){
                return null;
            }
            resultState.board.board[row][col].set(this.board.board[row-1][col].get());
            resultState.board.board[row-1][col].set(0);
        }else if (action == "down"){
            if (row == this.board.row-1){
                return null;
            }
            resultState.board.board[row][col].set(this.board.board[row+1][col].get());
            resultState.board.board[row+1][col].set(0);
        }else if (action == "left"){
            if (col == 0){
                return null;
            }
            resultState.board.board[row][col].set(this.board.board[row][col-1].get());
            resultState.board.board[row][col-1].set(0);
        }else if (action == "right"){
            if (col == this.board.col-1){
                return null;
            }
            resultState.board.board[row][col].set(this.board.board[row][col+1].get());
            resultState.board.board[row][col+1].set(0);
        }
        return resultState;
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
