package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Room implements Spaces {




    public static TETile[][] add(TETile[][] world, Pos start, Pos end, TETile t) {

        //Create the left and right walls
        for(int i = start.y; i < end.y + 1; i++) {
            world[start.x][i] = Tileset.WALL;
            world[end.x][i] = Tileset.WALL;
        }


        for(int j = start.x + 1; j < end.x; j++) {
            //create the first entry as wall
            world[j][start.y] = Tileset.WALL;
            //create 2nd to 1 before last as floors
            for (int k = start.y + 1; k < end.y; k++) {
                world[j][k] = t;
            }
            //create last entry as wall
            world[j][end.y] = Tileset.WALL;
        }

        return world;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public TETile[][] size() {
        return new TETile[0][];
    }





}
