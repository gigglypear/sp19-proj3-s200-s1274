package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 * protected class ????
 */
public class Pos {

    protected int x;
    protected int y;

    public Pos(int posx, int posy) {
        x = posx;
        y = posy;
    }

    public boolean validatePos(Pos bound, String type, TETile[][] world) {
        if (this.x > bound.x || this.y > bound.y) {
            return false;
        }
        if (world[this.x][this.y] != Tileset.NOTHING) {
            return false;
        }
        return true;
    }

}
