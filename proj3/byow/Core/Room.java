package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.Random;

public class Room {

    private int totalRms;

    private Pos startP;
    private Pos endP;
    private int x;
    private int y;
    private int width;
    private int height;

    private boolean connected;

    private Pos[] openings; //each room has an array of openings
//    public List<Room> allRooms = new ArrayList<>();

//    private HashMap<Room, Pos> OpenRm;
//    public List<Pos> openFringe = new ArrayList<>();

    /**
     * Room object constructor
     *
     * @param start -- takes a Pos (x,y) value as starting position
     * @param w     -- width of OVERALL room (includes walls)
     * @param h     -- height of OVERALL room(includes walls)
     */
    public Room(Pos start, int w, int h) {

        width = w;
        height = h;
        x = start.x;
        y = start.y;

        startP = start;
        endP = new Pos(x + width, y + height);

        connected = false;
        openings = new Pos[4];
    }


    public static int numRoomsGenerator(TETile[][] world, Random seed) {
        int totalRms = RandomUtils.uniform(seed, 3, 20);
//        for(int i = 0; i <= totalRms; i+= 1){
//            return roomGenerator(world, seed);
//        }
        return totalRms;
    }


    /**
     * STATIC ROOM GENERATOR
     * Generates a random room object with x and y values selected between bounds
     * Selected bounds of start x and y values: 0 to world - max height/width of
     * room
     * e.g. current max room height and width is 15 overall
     * Checks that starting and ending x and y values are valid values
     *
     * @param world
     * @param seed
     * @return
     */
    public static Room roomGenerator(TETile[][] world, Random seed) {

        int startX = RandomUtils.uniform(seed, 3, world.length - 18);
        int startY = RandomUtils.uniform(seed, 3, world[0].length - 18);

        int height = RandomUtils.uniform(seed, 4, 15);
        int width = RandomUtils.uniform(seed, 4, 15);


//
//        System.out.println(startX + ", " +  startY);
//        System.out.println(width + ", " + height );

        Pos start = new Pos(startX, startY);
        Room room = new Room(start, width, height);

        return room;

    }

    /**
     * Drawing the room where the room is closed off (no openings)
     * width and height of the rooms include the walls ==> floor space
     * witdth = width - 2 and height = height - 2 to account for walls
     *
     * @param world
     * @param t
     */
    public void draw(TETile[][] world, TETile t) {

        /**
         * Original way DOES NOT account for overlapping
         */
        int w = this.width;
        int h = this.height;
        int startX = this.x;
        int startY = this.y;

        int endX = this.x + w;
        int endY = this.y + h;


//        //Create the left and right walls
//        for (int i = startY; i < endY + 1; i++) {
////            world[startX][i] = Tileset.WALL;
////            world[endX][i] = Tileset.WALL;
//        }

        for (int j = startX + 1; j < endX; j++) {
            //create the first entry as wall
//            world[j][startY] = Tileset.WALL;

            //create 2nd to 1 before last as floors
            for (int k = startY + 1; k < endY; k++) {
                world[j][k] = t;
            }

            //create last entry as wall
//            world[j][endY] = Tileset.WALL;
        }

        /**
         * Working on this method to address overlap...some tiles still showing up
         */
//        int height = this.height;
//        int startX = this.x;
//        int startY = this.y;
//
//        int endX = this.x + width;
//        int endY = this.y + height;
//
//
//        //Create the left and right walls
//        for(int i = startY; i < endY + 1; i++) {
////            world[startX][i] = TileSelect.tileType(world, startX, i, t);
////            world[endX][i] = TileSelect.tileType(world, endX, i, t);;
//        }
//
//        for(int j = startX + 1; j < endX; j++) {
//            //create the first entry as wall
////            world[j][startY] = TileSelect.tileType(world, j, startY, t);
//
//            //create 2nd to 1 before last as floors
//            for (int k = startY + 1; k < endY; k++) {
//                world[j][k] = t;
//            }
//
//            //create last entry as wall
////            world[j][endY] = TileSelect.tileType(world, j, endY, t);
//        }

    }

    /**
     * @param direction -- Given a randomly selected direction (0 = north)
     * @param r         -- of class Random generated from the seed; used to get
     *                  a random x/y value for the tile you want to open on the side of the
     *                  given direction
     * @param world
     */
//    public void randomOpeningGenerator(int direction, Random r, TETile[][] world) {
//        int x;
//        int y;
//        int north = 0;
//        int east = 1;
//        int south = 2;
//        int west = 3;
//
//        //Randomly selecting an x/y value on a given side and making sure it's not
//        //the corners of the walls
//        if (direction == north) { // 0 = north
//            x = r.nextInt(endP.x - startP.x - 2 ) + this.startP.x + 1;
//            y = endP.y;
//        } else if(direction == east) { // 1 = east
//            x = endP.x;
//            y = r.nextInt(endP.y - startP.y - 2) + this.startP.y + 1;
//        } else if (direction == south) { // 2 = south
//            x = r.nextInt(endP.x - startP.x - 2 ) + this.startP.x + 1;
//            y = startP.y;
//        } else { // 3 = west
//            x = startP.x;
//            y = r.nextInt(endP.y - startP.y - 2) + this.startP.y + 1;
//        }
//
//        Pos p = new Pos(x, y);
//        openings[direction] = p;
//        this.open(world, p, Tileset.FLOOR);
//
//    }
    public Pos randomOpeningGenerator(int direction, Random r, TETile[][] world) {
        int posx;
        int posy;
        int north = 0;
        int east = 1;
        int south = 2;
        int west = 3;

        //Randomly selecting an x/y value on a given side and making sure it's not
        //the corners of the walls
        if (direction == north) { // 0 = north
            posx = r.nextInt(endP.x - startP.x - 2) + this.startP.x + 1;
            posy = endP.y;
        } else if (direction == east) { // 1 = east
            posx = endP.x;
            posy = r.nextInt(endP.y - startP.y - 2) + this.startP.y + 1;
        } else if (direction == south) { // 2 = south
            posx = r.nextInt(endP.x - startP.x - 2) + this.startP.x + 1;
            posy = startP.y;
        } else { // 3 = west
            posx = startP.x;
            posy = r.nextInt(endP.y - startP.y - 2) + this.startP.y + 1;
        }

        Pos p = new Pos(posx, posy);
        openings[direction] = p;
        this.open(world, p, Tileset.FLOOR);
        return p;

    }

    /**
     * Opens tiles
     *
     * @param world
     * @param p     -- position of tile you want to open
     * @param t
     */
    public void open(TETile[][] world, Pos p, TETile t) {
        world[p.x][p.y] = t;

    }


//    /**
//     * Closes tile at a given direction (side)
//     * @param world
//     * @param direction
//     * @param p
//     * @return
//     */
//    public TETile[][] close(TETile[][] world, String direction, Pos p) {
//        world[p.x][p.y] = Tileset.WALL;
//
//        return world;
//    }

//    private boolean overlap(Room r) {
//        return (this.startP.x <= r.endP.x && this.endP.x >= r.startP.x
//                && this.startP.y <= r.endP.y && this.endP.y >= r.startP.y);
//
//    }

    public boolean overlap(List<Room> allRooms) {
        for (Room r : allRooms) {
            if (this.startP.x <= r.endP.x && this.endP.x >= r.startP.x
                    && this.startP.y <= r.endP.y && this.endP.y >= r.startP.y) {
                return true;
            }
        }
        return false;
    }

    public Pos getStartP() {
        return startP;
    }

    public Pos getEndP() {
        return endP;
    }


}
