/* Node class for the linked list */
public class Node {
    State state;
    Node father;
    Action actionToThisState;

    /**
     * Constructor
     * @param state
     * @param father
     * @param actionToThisState
     */
    public Node(State state, Node father, Action actionToThisState) {
        this.state = state;
        this.father = father;
        this.actionToThisState = actionToThisState;
    }

    /** This `expand` method does not get any parameter, but returns an array of all the Nodes coming out from this
     * method using the method `actions` of `State` class.
     */
    public Node[] expand() {
        PossibleDirection[] possibleActions = this.state.actions();
        Node[] children = new Node[possibleActions.length];

        for (int i = 0; i < children.length; i++) {
            Action actionToChild = new Action(int[] targetTileIndexes, PossibleDirection direction);
            State childState = actionToChild.commitAction(this.state);
            children[i] = new Node(childState, this, actionToChild);
        }


        return children;
    }
}
