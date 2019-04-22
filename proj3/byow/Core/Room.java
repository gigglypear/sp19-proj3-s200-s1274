package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;

public class Room implements Spaces {

    /**
    hard-coding: may delete/add some of these variables later
     */
//    private int width;
//    private int height;
    private Pos startP;
    private Pos endP;

    private boolean connected;

    public Room(Pos start, Pos end) {
        startP = start;
        endP = end;
        connected = false;
    }

    public static TETile[][] draw(TETile[][] world, Pos start, Pos end, TETile t) {

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



    public boolean overlap(List<Room> rooms) {
        for(Room r: rooms) {
            if (this.startP.x <= r.endP.x && this.endP.x >= r.startP.x
                    && this.startP.y <= r.endP.y && this.endP.y >=r.startP.y) {
                return true;
            }
        }
        return false;
    }
//
//    protected static Pos[] roomGenerator(List<Room> rooms, TETile[][] world) {
//        Pos startP = roomStartPos(world);
//        Pos endP = roomEndPos(startP, world);
//
//        Room potentialRm = new Room(startP, endP);
//
//        potentialRm.overlap(rooms);
//
//    }



    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public TETile[][] size() {
        return new TETile[0][];
    }

    @Override
    public TETile[][] Open(TETile[][] world, Pos p, TETile t) {
        world[p.x][p.y] = t;
        return world;
    }

    @Override
    public TETile[][] Close(TETile[][] world, Pos p) {
        world[p.x][p.y] = Tileset.WALL;
        return world;
    }
}
