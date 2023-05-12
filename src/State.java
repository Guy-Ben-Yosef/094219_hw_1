public class State {
    Board[][] board;
    static Board[][] goalBoard;

    public State(Board[][] board){
        this.board = board;
        this.goalBoard = Board.getGoalBoard();

    }

    // TODO: `getGoalBoard` function should be implemented in `Board` class
    /*
    public Board getGoalBoard(){
        int count = 0;
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                count ++;
                if (count < row * col){
                    goalBoard[i][j] = new Tile(count);
                    break;
                } else {
                    goalBoard[i][j] = new Tile(0);
                }
            }
        }
        return goalBoard;
    }
    */


    /**
     * Does not get any parameters as input, it is checking if the current board's state is the goal state and returns boolean value
     * @return boolean
     */
    public boolean isGoal(){
        if (this.equals(goalBoard)){
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
     * Finds the indexes of the empty tile
     * @return int[]
     */
    private int[] findEmptyTileIndexes(){
        for (int i = 0; i < Board.row; i++){
            for (int j = 0; j < Board.col; j++){
                if (this.board[i][j].get() == 0){
                    return new int[]{i, j};
                }
            }
        }
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
