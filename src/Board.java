import java.util.Arrays;

public class Board {
    String boardString;
    Tile[][] board;
    int row;
    int col;

    /**
     * Constructor for board object to get a certain board by a string
     * @param boardString is a string - need to be converted
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
        String[] splittedString = "\\|".split(boardString) ;

        row = splittedString.length;
        col = " ".split(splittedString[0]).length;
        this.board = new Tile[row][col];

        this.Insert();
    }

    /**
     * Inserting the board all the tiles
     */
    private void Insert() {
        int count = 0;

        for (int i = 0; i < row; i++){
            for (int j = 0; j  < col; j++){
                if (this.boardString.charAt(count) == '|'){
                    count ++;
                    continue;

                } else if(this.boardString.charAt(count) == '_'){
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
