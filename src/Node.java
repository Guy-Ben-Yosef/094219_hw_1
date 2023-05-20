/**
 * This class represents a node in the search tree.
 * It contains the state of the puzzle, the parent node, and the action that was performed to reach this state.
 * 1. expand() - This method expands the current node to generate all possible child nodes.
 * 2. heuristicValue() - This method calculates the heuristic value of the current node.
 */
public class Node {
    State nodeState;
    Node parent;
    Action actionToThisState;
//    int heuristicValue;

    /**
     * Constructor
     * @param nodeState the current state
     * @param parent the parent node
     * @param actionToThisState the action that was performed to reach this state
     */
    public Node(State nodeState, Node parent, Action actionToThisState) {
        this.nodeState = nodeState;
        this.parent = parent;
        this.actionToThisState = actionToThisState;
    }

    /**
     * Constructor for first node of the game (the first node has no parent and no action)
     * @param nodeState the current state
     */
    public Node(State nodeState) {
        this.nodeState = nodeState;
    }


    /**
     * @return the current state
     */
    public State getState(){
        return this.nodeState;
    }

    /**
     * @return this node's action
     */
    public Action getAction() {
        return this.actionToThisState;
    }

    /**
     * @return the parent node
     */
    public Node getParent() {
        return this.parent;
    }

    /**
     * Expands the current node to generate all possible child nodes.
     *
     * @return An array of Node objects representing the child nodes of the current node.
     */
    public Node[] expand() {
        // Get all possible actions for the current state
        PossibleDirection[] possibleDirections = this.nodeState.actions();

        // Create an array of child nodes with the same length as the possible actions
        Node[] children = new Node[possibleDirections.length];

        // For each possible action, create a new child node with the resulting state and add it to the array
        for (int i = 0; i < children.length; i++) {
            PossibleDirection direction = possibleDirections[i];
            // Calculate the new position of the tile based on the action
            int[] targetTileIndexes = Action.convertEmptyToTarget(this.nodeState.emptyTileIndexes, direction);
            int targetI = targetTileIndexes[0];
            int targetJ = targetTileIndexes[1];
            // Create a new Action object with the target tile indexes and direction
            Action actionToChild = new Action(targetI, targetJ, this.nodeState.board.tiles[targetI][targetJ], direction);
            // Calculate the resulting state after performing the action
            State childState = this.nodeState.result(actionToChild);
            // Create a new Node object with the child state, current node, and action to child
            children[i] = new Node(childState, this, actionToChild);
        }

        return children;
    }


    /**
     * Heuristic value based on Manhattan geometry
     * @return the heuristic grade
     */
    public int heuristicValue() {
        int heuristicValue = 0;
        // Loop through all tiles in the board and calculate the distance of each tile from its original position
        for (int i = 0; i < Board.row; i++){  // looping all tile's board.
            for (int j = 0; j  < Board.col; j++) {
                Tile thisTile = this.nodeState.board.tiles[i][j];
                int[] goalIndexes = value2GoalIndexes(thisTile.get());
                heuristicValue += manhattanDistance(i, j, goalIndexes[0], goalIndexes[1]);
            }
        }

        int linearConflicts = 0;
        for (int i = 0; i < Board.row; i++) {
            // Initialize int array with the size of the board's col
            int[] rowValues = new int[Board.col];

            for (int j = 0; j < Board.col; j++) {
                int value = this.nodeState.board.tiles[i][j].get();
                // Get the indexes of the tile's goal position
                int[] goalIndexes = value2GoalIndexes(value);
                // If the tile's row index is the same as its goal row index, add it to rowValues, else add -1
                if (i == goalIndexes[0]) {
                    // If the value is 0, insert the board's row times board's col (the maximal value) as the value,
                    // else insert the value
                    if (value == 0) {
                        rowValues[j] = (Board.row * Board.col);
                    } else {
                        rowValues[j] = value;
                    }
                } else {
                    rowValues[j] = -1;
                }
            }
            // Check linear conflicts in the row
            linearConflicts += countLinearConflicts(rowValues);
        }

        for (int j = 0; j < Board.col; j++) {
            // Initialize int array with the size of the board's row
            int[] colValues = new int[Board.row];

            for (int i = 0; i < Board.row; i++) {
                int value = this.nodeState.board.tiles[i][j].get();
                // Get the indexes of the tile's goal position
                int[] goalIndexes = value2GoalIndexes(value);
                // if the tile's col index is the same as its goal col index, add it to colValues, else add -1
                if (j == goalIndexes[1]) {
                    // If the value is 0, insert the board's row times board's col (the maximal value) as the value,
                    // else insert the value
                    if (value == 0) {
                        colValues[i] = (Board.row * Board.col);
                    } else {
                        colValues[i] = value;
                    }
                } else {
                    colValues[i] = -1;
                }
            }
            // Check linear conflicts in the column
            linearConflicts += countLinearConflicts(colValues);
        }

        heuristicValue += (2 * linearConflicts);
        return heuristicValue;
    }

    /**
     * This method counts the number of linear conflicts in a row or column
     * @param values the values of the tiles in a row or column
     * @return the number of linear conflicts
     */
    private int countLinearConflicts(int[] values) {
        int linearConflicts = 0;  // Initialize the number of linear conflicts

        // Skip -1 values
        for (int i = 0; i < (values.length-1); i++) {
            if (values[i] == -1) {
                continue;
            }
            int value = values[i];
            for (int j = i + 1; j < values.length; j++) {
                // Skip -1 values
                if (values[j] == -1) {
                    continue;
                }
                int nextValue = values[j];
                if (value > nextValue) {
                    linearConflicts++;
                    }
                }
            }
        return linearConflicts;
    }



    /**
     * Calculating the distance of a tile from its goal position in the sense of Manhattan geometry
     * @param iCurrent current row index of a tile
     * @param jCurrent current column index of a tile
     * @param iGoal goal row index of a tile
     * @param jGoal goal column index of a tile
     * @return the distance of a tile from its goal position
     */
    private int manhattanDistance(int iCurrent, int jCurrent, int iGoal, int jGoal){
        float horizontalDistance = abs(iCurrent - iGoal);
        float verticalDistance = abs(jCurrent - jGoal);

        return (int) (horizontalDistance + verticalDistance);

    }

    /**
     * This method convert between the value of a tile and its goal position
     *
     * @param value the value of a tile
     * @return the row and column indexes of the tile's goal position
     */
    private int[] value2GoalIndexes(int value) {
        int l, m; // Declaring the row and column of the tile's goal position
        if (value >= 1) {
            l = (value - 1) / Board.col;  // Integer division
            m = value - (l * Board.col + 1);
        } else {
            l = (Board.row - 1);
            m = (Board.col - 1);
        }
        int[] indexes = {l, m};
        return indexes;
    }

    /**
     * Calculating the absolute value of a number
     * @param x the number
     * @return absolute value of x
     */
    private static float abs(float x) {
        if (x >= 0) {
            return x;
        } else {
            return -x;
        }
    }
}
