package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**protected class ????
 *
 */
public class Pos {

    public int x;
    public int y;

    public Pos(int Posx, int Posy) {
        x = Posx;
        y = Posy;
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


    public boolean isOpen() {
        //TODO
        return false;
    }
}
