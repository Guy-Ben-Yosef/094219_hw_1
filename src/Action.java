/* This is the Action class. It is used to represent a single action in the game. */
public class Action {
    int i;
    int j;
    PossibleDirection direction;
    public Action(int[] targetTileIndexes, PossibleDirection direction) {
        this.i = targetTileIndexes[0];
        this.j = targetTileIndexes[1];
        this.direction = direction;
    }

    /**
     * This method returns the (new) indexes of the tile after the action is committed.
     * @return State
     */
    public int[] actionAsNewIndexes() {
        if (direction == PossibleDirection.UP) {
            return new int[]{i+1, j};
        } else if (direction == PossibleDirection.DOWN) {
            return new int[]{i-1, j};
        } else if (direction == PossibleDirection.RIGHT) {
            return new int[]{i, j+1};
        } else if (direction == PossibleDirection.LEFT) {
            return new int[]{i, j-1};
        }
        return null;
    }

    /**
     * This method convert between the indexes of the empty tile and the tile need to be moved (based on an input
     * direction).
     * @return State
     */
    public static int[] convertEmptyToTarget(int[] emptyIndexes, PossibleDirection direction) {
        if (direction == PossibleDirection.UP) {
            return new int[]{emptyIndexes[0]-1, emptyIndexes[1]};
        } else if (direction == PossibleDirection.DOWN) {
            return new int[]{emptyIndexes[0]+1, emptyIndexes[1]};
        } else if (direction == PossibleDirection.RIGHT) {
            return new int[]{emptyIndexes[0], emptyIndexes[1]-1};
        } else if (direction == PossibleDirection.LEFT) {
            return new int[]{emptyIndexes[0], emptyIndexes[1]+1};
        }
        return null;
    }

}
