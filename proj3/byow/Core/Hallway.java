package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Hallway implements Spaces {


    /**
     * This methods generate a vertical hallway
     * start and end position are openings of the hallways and will be assigned with a floor tile
     */

    public static TETile[][] drawVer(TETile[][] world, Pos start, Pos end, TETile t) {

        for (int i = start.y; i < end.y + 1; i++) {
            world[start.x - 1][i] = Tileset.WALL;
            world[start.x][i] = t;
            world[start.x + 1][i] = Tileset.WALL;
        }

        return world;
    }

    public static TETile[][] drawHor(TETile[][] world, Pos start, Pos end, TETile t) {

        for (int i = start.x; i < end.x + 1; i++) {
            world[i][start.y - 1] = Tileset.WALL;
            world[i][start.y] = t;
            world[i][start.y + 1] = Tileset.WALL;
        }

        return world;
    }

    public static TETile[][] drawCorner(TETile[][] world, Pos start, Pos end, TETile t) {


        return world;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public TETile[][] size() {
        return new TETile[0][];
    }


    public  TETile[][] Open(TETile[][] world, Pos p, TETile t) {
        world[p.x][p.y] = t;
        return world;
    }


    public  TETile[][] Close(TETile[][] world, Pos p) {
        world[p.x][p.y] = Tileset.WALL;
        return world;
    }
}
