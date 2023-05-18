import java.util.Arrays;

public class Board {
    Tile[][] board;
    int row;
    int col;

    /**
     * Constructor for board object to get a certain board by a string
     * @param boardString is a string - need to be converted
     */
    public Board(String boardString){
        String[] splittedString = "\\|".split(boardString) ;

        row = splittedString.length;
        col = " ".split(splittedString[0]).length;
        this.board = new Tile[row][col];

        this.Insert(boardString);
    }

    /**
     * Constructor for board object in case that the user want to get an EMPTY board
     */
    public Board() {
        this.board = new Tile[row][col];
    }

    /**
     * Inserting the board all the tiles
     */
    private void Insert(String boardString) {
        int count = 0;

        for (int i = 0; i < row; i++){
            for (int j = 0; j  < col; j++){
                if (boardString.charAt(count) == '|'){
                    count ++;
                    continue;

                } else if(boardString.charAt(count) == '_'){
                    this.board[i][j] = new Tile(0);

                }else{
                    this.board[i][j] = new Tile(boardString.charAt(count));
                }

                count ++;
            }
        }
    }

    /**
     * Printing the current board
     */
    public void print(){
        for (int i = 0; i < row; i++){
            for (int j = 0; j  < col; j++) {
                if (this.board[i][j].get() == 0) {
                    System.out.print("_");
                } else {
                    System.out.print(this.board[i][j].get());
                }
            }
        System.out.println();
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
