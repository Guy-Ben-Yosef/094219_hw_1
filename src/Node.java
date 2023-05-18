/**
 * This class represents a node in the search tree.
 * It contains the state of the puzzle, the parent node, and the action that was performed to reach this state.
 * 1. expand() - This method expands the current node to generate all possible child nodes.
 * 2. heuristicValue(...) - TODO.
 */
public class Node {
    State state;
    Node father;
    Action actionToThisState;
    int heuristicValue;

    /**
     * Constructor
     * @param state the current state
     * @param father the parent node
     * @param actionToThisState the action that was performed to reach this state
     */
    public Node(State state, Node father, Action actionToThisState) {
        this.state = state;
        this.father = father;
        this.actionToThisState = actionToThisState;
    }

    /**
     * Expands the current node to generate all possible child nodes.
     *
     * @return An array of Node objects representing the child nodes of the current node.
     */
    public Node[] expand() {
        // Get all possible actions for the current state
        PossibleDirection[] possibleActions = this.state.actions();

        // Create an array of child nodes with the same length as the possible actions
        Node[] children = new Node[possibleActions.length];

        // For each possible action, create a new child node with the resulting state and add it to the array
        for (int i = 0; i < children.length; i++) {
            // Calculate the new position of the tile based on the action
            int[] targetTileIndexes = Action.convertEmptyToTarget(this.state.emptyTileIndexes, possibleActions[i]);
            // Create a new Action object with the target tile indexes and direction
            Action actionToChild = new Action(targetTileIndexes, possibleActions[i]);
            // Calculate the resulting state after performing the action
            State childState = state.result(actionToChild);
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

        for (int i = 0; i < state.row; i++){  // looping all tile's board.
            for (int j = 0; j  < state.col; j++) {
                heuristicValue += distance(i, j, state[i][j].get());
            }
        } 
        return heuristicValue;
    }

    /**
     * Calaulating the distance of current tile from the original
     * @param i row parameter
     * @param j column parameter
     * @param value at i,j board
     * @return the distance
     */
    public int distance(int i, int j, int value){
        for (int l = 0; l < state.GOAL_BOARD.row; l++){  // looping all tile's board.
            for (int m = 0; m  < state.GOAL_BOARD.col; m++) {
                if(value == state.goalBoard[l][m]){
                    return (abs(i -l) + abs(j-m));
                }
             }
         } 

         return 0;
    }
}
