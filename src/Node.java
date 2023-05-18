/**
 * This class represents a node in the search tree.
 * It contains the state of the puzzle, the parent node, and the action that was performed to reach this state.
 * 1. expand() - This method expands the current node to generate all possible child nodes.
 * 2. heuristicValue(...) - TODO.
 */
public class Node {
    State nodeState;
    Node parent;
    Action actionToThisState;
    int heuristicValue;

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
     * Heuristic value based on  Manhattan geometry 
     * @return the heuristic grade
     */
    public int heuristicValue() {
        heuristicValue = 0;

//        for (int i = 0; i < this.nodeState.stateBoard.row; i++){  // looping all tile's board.
//            for (int j = 0; j  < this.nodeState.stateBoard.col; j++) {
//                heuristicValue += distance(i, j, this.nodeState.stateBoard.board[i][j].get());
//            }
//        }
        return heuristicValue;
    }

    /**
     * Calculating the distance of current tile from the original
     * @param i row parameter
     * @param j column parameter
     * @param value at i,j board
     * @return the distance
     */
    public int distance(int i, int j, int value){
        for (int l = 0; l < this.nodeState.board.row; l++){  // looping all tile's board.
            for (int m = 0; m  < this.nodeState.board.col; m++) {
                if(value == this.nodeState.board.goalTiles[l][m].get()){
                    return 0;  // TO BE DELETED
//                    return (abs(i - l) + abs(j - m));
                }
             }
         } 

         return 0;
    }
}
