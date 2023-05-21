/**
 * Tile class represents a tile with a specific value. The class provides a constructor to initialize the value of the
 * tile, a get method to retrieve the tile value, and overrides the equals and hashCode methods for comparison and
 * hashing purposes.
 */
public class Tile {
    protected int value;

    /**
     * Constructor for Tile
     * @param value of specific tile
     */
    public Tile(int value){
        this.value = value;
    }

    /**
     * get method
     * @return the tile value
     */
    public int get(){ return this.value; }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Tile)) {
            return false;
        }
        Tile tile = (Tile) other;
        return value == tile.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
