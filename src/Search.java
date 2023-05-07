import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Comparator;

public class Search {
    private int expandedNodes;
    private List<Action> result;
    private Status status = Status.UNSOLVED;

    /**
     * Constructs the root node of the game based on an initial board.
     *
     * @param boardString String representing the initial board
     * @return The root node used to search for a solution
     */
    private Node getRoot(String boardString) {
        // TODO: Implement this function.
        // NOTE: This is the only function you need to modify in this class!
    }

    /**
     * Performs a Greedy Best First Search, using node heuristic function.
     *
     * @param boardString String representing the initial board
     * @return List of actions which performing them will lead to the the goal state
     */
    public List<Action> search(String boardString) {
        try {
            Node root = getRoot(boardString);

            Queue<Node> frontier = new PriorityQueue<>(Comparator.comparing(Node::heuristicValue));  // Stores future nodes
            Set<State> enqueued = new HashSet<>();  // Used for duplicate detection
            frontier.add(root);  // Add the root as the first node in the frontier
            enqueued.add(root.getState());  // Mark the root as visited

            while (!frontier.isEmpty()) {
                Node node = frontier.remove();  // Get a node with smallest heuristic value
                if (node.getState().isGoal()) {
                    result = extractSolution(node);  // Extracting the solution
                    status = Status.SOLVED;
                    return result;
                }
                expandedNodes++;
                Node[] children = node.expand();

                for (Node child : children) {  // Iterate over all possible child nodes
                    if (!enqueued.contains(child.getState())) {  // Check for duplication
                        enqueued.add(child.getState());  // Mark the child as visited
                        frontier.add(child);
                    }
                }
            }
            status = Status.UNSOLVABLE;  // Unsolvable board
        } catch (OutOfMemoryError err) {  // Out of memory - probably due to an explosion of the frontier
            status = Status.OUT_OF_MEMORY;
        }
        return null;
    }

    /**
     * Extracts a solution from a given node by iterating backward from the node up to the root.
     * The given node satisfies node.getState().isGoal() == true.
     *
     * @param node Node contains a the goal state
     * @return List of actions to reach the goal state
     */
    private List<Action> extractSolution(Node node) {
        List<Action> actions = new ArrayList<>();
        while (node != null) {  // Iterate backwards until reaching the root
            actions.add(node.getAction());
            node = node.getParent();
        }
        Collections.reverse(actions);  // Reverse the list
        actions.remove(0);
        return actions;
    }

    public Status getStatus() {
        return status;
    }

    public List<Action> getResult() {
        return result;
    }

    public int getExpandedNodes() {
        return expandedNodes;
    }

    public enum Status {
        SOLVED,
        UNSOLVABLE,
        OUT_OF_MEMORY,
        UNSOLVED
    }
}
