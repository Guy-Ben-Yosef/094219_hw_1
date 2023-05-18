import java.util.Arrays;

public class Board {
    Tile[][] tiles;
    Tile[][] goalTiles;
    int row;
    int col;

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

        this.goalTiles = getGoalTiles();
    }

    /**
     * Constructor for board object in case that the user want to get an EMPTY board
     */
    public Board() {
        this.tiles = new Tile[row][col];
    }

    /**
     * Inserting the board all the tiles
     */
    private void Insert(String boardString) {
        int count = -1;
        int i = 0;
        while ((i < row) && (count < boardString.length() - 1)) {
            int j = 0;
            while ((j  < col) && (count < boardString.length() - 1)) {
                count ++;
                if (boardString.charAt(count) == '|') {
                    i ++;
                } else if (boardString.charAt(count) == ' ') {
                    j ++;
                } else if(boardString.charAt(count) == '_'){
                    this.tiles[i][j] = new Tile(0);
                }else{
                    // Get the relevant Char from boardString
                    char relevantChar = boardString.charAt(count);
                    // Convert this Char to int
                    int charAsInt = Character.getNumericValue(relevantChar);
                    this.tiles[i][j] = new Tile(charAsInt);
                }
            }
        }
    }

    /**
     * Printing the current board
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
     * This method generates a Board with Tiles numbered from 1 to (row * col - 1) and a blank Tile represented by 0,
     * that is a Board matches the final board of the game (goal board).
     */
    public Tile[][] getGoalTiles(){
        goalTiles = new Tile[row][col];
        // Initialize a counter to keep track of the Tile number
        int count = 1;

        // Loop through each row and column of the board
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.col; j++){
                // Check if the current Tile should be numbered or blank
                if (count < this.row * this.col){
                    goalTiles[i][j] = new Tile(count);
                } else {
                    goalTiles[i][j] = new Tile(0);
                }
                count ++;
            }
        }
        return goalTiles;
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
