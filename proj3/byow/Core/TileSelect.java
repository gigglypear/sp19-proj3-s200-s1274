package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class TileSelect {

    /**
     * Returns a tile type that should be placed down in the world after
     * comparing the tile location to see if anything is already there
     * @param world
     * @param x
     * @param y
     * @param t
     * @return
     */
    public static TETile tileType(TETile[][] world, int x, int y, TETile t){

        TETile type;
        TETile wall1 = world[x][y];

        if(wall1 == t){
            type = t;
        } else{
            type = Tileset.WALL;
        }

        return type;
    }

}
