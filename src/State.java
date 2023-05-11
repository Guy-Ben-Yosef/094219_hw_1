public class State {
    private Board board;
    private static final Board goalBoard = new Board("1|2|3|4|5|6|7|8|0"); // set the goal board
    /**
     * Does not get any parameters as input, it is check if the current board's state is the goal state and returns boolean value
     * @return boolean
     */
    public boolean isGoal(){
        if (this.equals(goalBoard)){
            return true;
        }
        return false;
    }

    /**
     * Gets the board as input and sets the board
     * @param board
     */
    public String[] actions{
        String[] actions = new String[4];
        actions[0] = "up";
        actions[1] = "down";
        actions[2] = "left";
        actions[3] = "right";
        return actions;
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
