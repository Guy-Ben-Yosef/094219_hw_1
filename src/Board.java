import java.util.Arrays;

public class Board {
    String boardString;
    Tile [][] board;
    static int row;
    static int col;

    /**
     * Constructor for board object to get a certain board by a string
     * @param boardString is a string-need to be converted
     */
    public Board(String boardString){
        this.boardString = boardString;
        this.Build();
    }

    /**
     * Constructor for board object in case that the user want to get an EMPTY board
     */
    public Board() {
        this.board = new Tile[row][col];
    }

    /**
     * This function build the tiles board
     * the sign of '_' replaced by 0
     */
    private void Build() {
        String[] splittedString = boardString.split("\\|") ;

        this.row = splittedString.length;
        this.col = (splittedString[0]).split(" ").length;
        this.board = new Tile[row][col];

        this.Insert();
    }

    /**
     * Inserting the board all the tiles
     */
    private void Insert() {
        int count = 0;

        for (int i = 0; i < this.row; i++){
            for (int j = 0; j  < this.col; j++){
                if (this.boardString.charAt(count) == '|'){
                    count ++;
                    continue;
                }

                if(this.boardString.charAt(count) == '_'){
                    this.board[i][j] = new Tile(0);

                }else{
                    this.board[i][j] = new Tile(this.boardString.charAt(count));
                }

                count ++;
            }
        }
    }

    /**
     * Printing the current board
     */
    public void print(){
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j  < this.col; j++) {
                if (this.board[i][j].get() == 0) {
                    System.out.print("_");
                } else {
                    System.out.print(this.board[i][j].get());
                }
            }
        System.out.println();
        }
    }

    /**
     * This method generates a board with Tiles numbered from 1 to (row * col - 1) and a blank Tile represented by 0,
     * that is a board matches the final board of the game (goal board)
     */
    public void getGoalBoard(){
        // Initialize a counter to keep track of the Tile number
        int count = 1;

        // Loop through each row and column of the board
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                // Check if the current Tile should be numbered or blank
                if (count < row * col){
                    this.board[i][j] = new Tile(count);
                } else {
                    this.board[i][j] = new Tile(0);
                }
                count ++;
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Board)) {
            return false;
        }
        Board board = (Board) other;
        return Arrays.deepEquals(tiles, board.tiles);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }
}
