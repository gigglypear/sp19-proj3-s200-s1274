package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class TileSelect {

    public static TETile tileType(TETile location, int x, int y, TETile t){

        TETile type = Tileset.MOUNTAIN; //initialize for now??

        if(type == t){
            type = t;
        } else{
            type = Tileset.WALL;
        }

        return type;
    }

}
