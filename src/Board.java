import java.util.Arrays;

public class Board {
    String boardString;
    Tile [][] board;
    int row;
    int col;

    /**
     * Constractor for board object
     * @param boardString is a string-need to be converted
     */
    public Board(String boardString){
        this.boardString = boardString;
        this.Build();
    }

    /**
     * This function build the tiles board
     * the sign of '_' replaced by 0
     */
    private void Build() {
        String[] splittedString = boardString.split("|") ;

        this.row = splittedString.length;
        this.col = (splittedString[0]).split("").length;
        this.board = new Tile[row][col];

        this.Insert(row, col);
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
