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
                heuristicValue += distance(i, j, thisTile.get());
            }
        }
        return heuristicValue;
    }

    /**
     * Calculating the distance of a tile from its goal position
     * @param i row parameter
     * @param j column parameter
     * @param value at i,j board
     * @return the distance
     */
    public int distance(int i, int j, int value){
        int l, m; // Declaring the row and column of the tile's goal position

        if (value >= 1) {
            l = (value - 1) / Board.col;  // Integer division
            m = value - (l * Board.col + 1);
        } else {
            l = (Board.row - 1);
            m = (Board.col - 1);
        }
        float horizontalDistance = Node.abs(i - l);
        float verticalDistance = Node.abs(j - m);

        return (int) (horizontalDistance + verticalDistance);

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
