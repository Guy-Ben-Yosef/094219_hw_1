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

    /**
     * set method
     * @param new_value the new value of the tile
     */
    public void set(int new_value){ this.value = new_value; }

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