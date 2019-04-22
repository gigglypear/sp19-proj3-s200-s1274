package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.Random;

public class Room implements Spaces {

    /**
    hard-coding: may delete/add some of these variables later
     */
//    private int width;
//    private int height;
    private Pos startP;
    private Pos endP;

    private boolean connected;

    private Pos[] openings;

    public Room(Pos start, Pos end) {
        startP = start;
        endP = end;
        connected = false;
        openings = new Pos[4];
    }

//    public static TETile[][] draw(TETile[][] world, Pos start, Pos end, TETile t) {
//
//        //Create the left and right walls
//        for(int i = start.y; i < end.y + 1; i++) {
//            world[start.x][i] = Tileset.WALL;
//            world[end.x][i] = Tileset.WALL;
//        }
//
//        for(int j = start.x + 1; j < end.x; j++) {
//            //create the first entry as wall
//            world[j][start.y] = Tileset.WALL;
//
//            //create 2nd to 1 before last as floors
//            for (int k = start.y + 1; k < end.y; k++) {
//                world[j][k] = t;
//            }
//
//            //create last entry as wall
//            world[j][end.y] = Tileset.WALL;
//        }
//
//        return world;
//    }

    /**
     * Drawing the room where the room is closed off (no openings)
     * @param world
     * @param t
     */
    public void draw(TETile[][] world, TETile t) {

        Pos start = this.startP;
        Pos end = this.endP;

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

    }

    /**
     * @param direction -- Given a randomly selected direction (0 = north)
     * @param r -- of class Random generated from the seed; used to get
     *          a random x/y value for the tile you want to open on the side of the
     *          given direction
     * @param world
     */
    public void randomOpeningGenerator(int direction, Random r, TETile[][] world) {
        int x;
        int y;

        if (direction == 0) { // 0 = north
            x = r.nextInt(endP.x - startP.x - 2 ) + this.startP.x + 1;
            y = endP.y;
        } else if(direction == 1) { // 1 = east
            x = endP.x;
            y = r.nextInt(endP.y - startP.y - 2) + this.startP.y + 1;
        } else if (direction == 2) { // 2 = south
            x = r.nextInt(endP.x - startP.x - 2 ) + this.startP.x + 1;
            y = startP.y;
        } else { // 3 = west
            x = startP.x;
            y = r.nextInt(endP.y - startP.y - 2) + this.startP.y + 1;
        }

        Pos p = new Pos(x, y);
        openings[direction] = p;
        this.open(world, p, Tileset.FLOOR);
//        System.out.println(p.x +" , " + p.y);
    }


    /**
     * Currently works for approach where generate all rooms first
     * @param rooms -- list of rooms
     * @return
     */
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


    /**
     * Opens tiles
     * @param world
     * @param p -- position of tile you want to open
     * @param t
     */
    public void open(TETile[][] world, Pos p, TETile t) {
        world[p.x][p.y] = t;
    }

    /**
     * Closes tile at a given direction (side)
     * @param world
     * @param direction
     * @param p
     * @return
     */
    public TETile[][] Close(TETile[][] world, String direction, Pos p) {
        world[p.x][p.y] = Tileset.WALL;

        return world;
    }
}
