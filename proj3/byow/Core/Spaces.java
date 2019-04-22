package byow.Core;

import byow.TileEngine.TETile;

public interface Spaces {

    /**
     * this method assign the given position to a given TETile
     */
//    TETile[][] Open(TETile[][] world, Pos p, TETile t);

    /**
     * this method assign the given position to a given TETile
     */
//    TETile[][] Close(TETile[][] world, Pos p);


    boolean isConnected();

    ///do we need it??????
    TETile[][] size();

}
