import java.util.Arrays;

public class Board {
    Tile[][] tiles;
    static int row;
    static int col;
    static Board goalBoard;

    /**
     * Constructor for board object to get a certain board by a string
     * @param boardString is a string - need to be converted
     */
    public Board(String boardString){
        String[] splittedString = boardString.split("\\|") ;

        row = splittedString.length;
        col = splittedString[0].split(" ").length;
        this.tiles = new Tile[row][col];

        this.Insert(boardString);

        setGoalBoard();
    }

    /**
     * Constructor for board object in case that the user want to get an EMPTY board
     */
    public Board(int row, int col) {
        this.tiles = new Tile[row][col];
    }

    /**
     * constructor for board object
     */
    public Board(){
        this.tiles = new Tile[0][0];
    }

    /**
     * Inserting the board all the tiles
     */
    private void Insert(String boardString) {
        String[] rowsStrings = boardString.split("\\|");  // Splitting the board to rows
        for (int i = 0; i < rowsStrings.length; i++) {
            String[] colsStrings = rowsStrings[i].split(" ");  // Splitting the board to columns
            for (int j = 0; j < colsStrings.length; j++) {
                if (colsStrings[j].equals("_")) {
                    this.tiles[i][j] = new Tile(0);  // Inserting the blank tile
                } else {
                    this.tiles[i][j] = new Tile(Integer.parseInt(colsStrings[j]));  // Inserting the tile
                }
            }
        }
    }

    /**
     * Printing the current board
     * TODO: need to be deleted
     */
    public void print(){
        for (int i = 0; i < row; i++){
            for (int j = 0; j  < col; j++) {
                if (this.tiles[i][j].get() == 0) {
                    System.out.print("_");
                } else {
                    System.out.print(this.tiles[i][j].get());
                }
            }
        System.out.println();
        }
    }

    /**
     * This method generates a static Board with Tiles numbered from 1 to (row * col - 1) and a blank Tile represented
     * by 0, that is a Board matches the final board of the game (goal board).
     */
    public void setGoalBoard(){
         goalBoard = new Board(row, col);
        // Initialize a counter to keep track of the Tile number
        int count = 1;

        // Loop through each row and column of the board
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                // Check if the current Tile should be numbered or blank
                if (count < row * col){
                    Board.goalBoard.tiles[i][j] = new Tile(count);
                } else {
                    Board.goalBoard.tiles[i][j] = new Tile(0);
                }
                count ++;
            }
        }
    }

    /**
     * This method make a copy of the current board
     * @return (Board) a copy of the current board
     */
    public Board copy() {
        Board newBoard = new Board();
        newBoard.tiles = new Tile[row][col];

        // Loop through each row and column of the board
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                newBoard.tiles[i][j] = new Tile(this.tiles[i][j].get());  // Copy the Tile
            }
        }
        return newBoard;
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
