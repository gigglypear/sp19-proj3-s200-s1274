package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class TileSelect {

//    /**
//     * Returns a tile type that should be placed down in the world after
//     * comparing the tile location to see if anything is already there
//     * @param world
//     * @param x
//     * @param y
//     * @param t
//     * @return
//     */
//    public static TETile tileType(TETile[][] world, int x, int y, TETile t){
//
//        TETile type;
//        TETile wall1 = world[x][y];
//
//        if(wall1 == t){
//            type = t;
//        } else{
//            type = Tileset.WALL;
//        }
//
//        return type;
//    }
//
//    public static TETile checkNeighbors(TETile[][] world, int x, int y, TETile t){
//
//        TETile type;
//        TETile currTile = world[x][y];
//
//        TETile northN = world[x+1][y];
//        TETile southN = world[x-1][y];
//        TETile eastN = world[x][y+1];
//        TETile westN = world[x][y-1];
//
//        if(northN != Tileset.NOTHING && southN != Tileset.NOTHING &&
//                eastN != Tileset.NOTHING && westN != Tileset.NOTHING){
//            type = t;
//        }else if(northN != Tileset.NOTHING && southN != Tileset.NOTHING ||
//                eastN != Tileset.NOTHING && westN != Tileset.NOTHING){
//            type = Tileset.WALL;
//        } else{
//            type = t;
//        }
//
//        return type;
//
//    }

    public static void drawWalls(TETile[][] world, int x, int y){
        TETile currTile = world[x][y];

        if(currTile != Tileset.FLOOR){
            for(int i = -1; i < 2; i+=1) {
                for (int j = -1; j < 2; j += 1) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    if (world[x + i][y + j] == Tileset.FLOOR) {
                        world[x][y] = Tileset.WALL;
                    }
                }
            }

        }

    }

}
