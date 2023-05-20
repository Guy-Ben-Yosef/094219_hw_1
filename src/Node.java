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
     * @return true if the node has a parent, false otherwise
     */
    private boolean hasParent() {
        return (parent != null);
    }

    /**
     * Expands the current node to generate all possible child nodes.
     *
     * @return An array of Node objects representing the child nodes of the current node.
     */
    public Node[] expand() {
        // Get all possible actions for the current state
        PossibleDirection[] possibleDirections = this.nodeState.actions();

        int childrenNumber;
        if ( this.hasParent() ) {
            // The number of children is the number of possible actions minus the opposite action to the parent
            childrenNumber = possibleDirections.length - 1;
        } else {
            childrenNumber = possibleDirections.length;
        }
        // Create an array of child nodes
        Node[] children = new Node[childrenNumber];

        int childIndex = 0;
        // For each possible action, create a new child node with the resulting state and add it to the array
        for (PossibleDirection direction : possibleDirections) {
            if (this.hasParent()) {
                // If the parent node exists, check if the current action is the opposite of the parent's action
                PossibleDirection parentDirection = this.actionToThisState.direction;
                if (direction == findOppositeDirection(parentDirection)) {
                    // If the current action is the opposite of the parent's action, skip this action
                    continue;
                }
            }
            // Calculate the new position of the tile based on the action
            int[] targetTileIndexes = Action.convertEmptyToTarget(this.nodeState.emptyTileIndexes, direction);
            int targetI = targetTileIndexes[0];
            int targetJ = targetTileIndexes[1];
            // Create a new Action object with the target tile indexes and direction
            Action actionToChild = new Action(targetI, targetJ, this.nodeState.board.tiles[targetI][targetJ], direction);
            // Calculate the resulting state after performing the action
            State childState = this.nodeState.result(actionToChild);
            // Create a new Node object with the child state, current node, and action to child
            children[childIndex] = new Node(childState, this, actionToChild);

            childIndex++;
        }

        return children;
    }

    /**
     * Simple method to get the opposite direction of a given direction
     * @param direction the direction to find the opposite of
     * @return the opposite direction
     */
    private PossibleDirection findOppositeDirection(PossibleDirection direction) {
        switch (direction) {
            case UP:
                return PossibleDirection.DOWN;
            case DOWN:
                return PossibleDirection.UP;
            case RIGHT:
                return PossibleDirection.LEFT;
            case LEFT:
                return PossibleDirection.RIGHT;
        }
        return null;
    }


    /**
     * Heuristic value based on Manhattan geometry
     * @return the heuristic grade
     */
    public int heuristicValue() {
        int manhattanDistances = calcManhattanDistances() * 1;
//        int linearConflicts = calcLinearConflicts() * 2;
//        int inversions = countInversions(flattenBoard()) * 2;

        int heuristicValue = manhattanDistances;
        return heuristicValue;
    }


    /**
     * calculates the number of inversions in the array. An inversion occurs when two numbers are in reversed order
     * relative to their desired sorted positions. In other words, an inversion happens when a number precedes another
     * lower number.
     * @param flattenedBoard the board as 1D flattened array
     * @return the number of inversions in the board
     */
    private int countInversions(int[] flattenedBoard) {
        // Initialize the number of inversions to 0
        int inversions = 0;

        // Iterate over the array
        for (int i = 0; i < (flattenedBoard.length-1); i++) {
            // Iterate over the array starting from the next index
            for (int j = i + 1; j < flattenedBoard.length; j++) {
                // If the value of the first index is greater than the value of the second index, increment inversions
                if (flattenedBoard[i] > flattenedBoard[j]) {
                    inversions++;
                }
            }
        }

        return inversions;

    }

    /**
     * @return The board as 1D flattened array
     */
    private int[] flattenBoard() {
        int[] flattenedBoard = new int[Board.row * Board.col];
        int index = 0;
        for (int i = 0; i < Board.row; i++) {
            for (int j = 0; j < Board.col; j++) {
                int value = this.nodeState.board.tiles[i][j].get();
                // value equals zero assign the maximal value to the tile that is row*col
                if (value == 0) {
                    value = Board.row * Board.col;
                }
                flattenedBoard[index] = value;
                index++;
            }
        }
        return flattenedBoard;
    }

    /**
     * This method does not get any parameter as an input, but counts how much linear conflicts there are in the board.
     *
     * @return the number of linear conflicts in the board
     */
    private int calcLinearConflicts() {
        int linearConflicts = 0;

        // Count linear conflicts in each row and add it to linearConflicts
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
            linearConflicts += linearConflicts(rowValues);
        }

        // Count linear conflicts in each column and add it to linearConflicts
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
            linearConflicts += linearConflicts(colValues);
        }
        return linearConflicts;
    }

    /**
     * This method counts the number of linear conflicts in a row or column
     * @param values the values of the tiles in a row or column
     * @return the number of linear conflicts
     */
    private int linearConflicts(int[] values) {
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
     * This method does not get any parameter as an input, but calculate the sum of the Manhattan distances of all tiles
     * in the board.
     *
     * @return the sum of the Manhattan distances of all tiles in the board
     */
    private int calcManhattanDistances() {
        int manhattedDistancts = 0;
        // Loop through all tiles in the board and calculate the distance of each tile from its original position
        for (int i = 0; i < Board.row; i++){  // looping all tile's board.
            for (int j = 0; j  < Board.col; j++) {
                Tile thisTile = this.nodeState.board.tiles[i][j];
                int[] goalIndexes = value2GoalIndexes(thisTile.get());
                manhattedDistancts += manhattanDistance(i, j, goalIndexes[0], goalIndexes[1]);
            }
        }
        return manhattedDistancts;
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
