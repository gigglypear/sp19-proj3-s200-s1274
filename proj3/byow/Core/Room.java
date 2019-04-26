package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Room implements Spaces {

    private int totalRms;

    private Room Rm;
    private Pos startP;
    private Pos endP;
    private Pos centerP;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean connected;


//    private Pos[] openings; //each room has an array of openings
//    public List<Room> allRooms = new ArrayList<>();

//    private HashMap<Room, Pos> OpenRm;
//    public List<Pos> openFringe = new ArrayList<>();

    /**
     * Room object constructor
     * @param start -- takes a Pos (x,y) value as starting position
     * @param w -- width of OVERALL room (includes walls)
     * @param h -- height of OVERALL room(includes walls)
     */
    public Room(Pos start, int w, int h) {

        width = w;
        height = h;
        x = start.x;
        y = start.y;

        startP = start;
        endP = new Pos(x + width, y + height);
        centerP = new Pos(x + width/2, y + height/2);

        connected = false;

        System.out.println("start = (" + x + " , " + y + ")");
        System.out.println("end = (" + endP.x + " , " + endP.y + ")");
    }

    public Pos getStartP() {
        return startP;
    }

    public Pos getEndP() {
        return endP;
    }


    public static int numRoomsGenerator(TETile[][] world, Random seed){
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

        int startX = RandomUtils.uniform(seed ,3, world.length - 18);
        int startY = RandomUtils.uniform(seed,  3, world[0].length - 18);

        int height = RandomUtils.uniform(seed, 4, 15);
        int width = RandomUtils.uniform(seed, 4, 15);



        System.out.println(startX + ", " +  startY);
        System.out.println(width + ", " + height );

        Pos start = new Pos(startX, startY);
        Room room = new Room(start, width, height);

        return room;

    }

    /**
     * Drawing the room where the room is closed off (no openings)
     * width and height of the rooms include the walls ==> floor space
     * witdth = width - 2 and height = height - 2 to account for walls
     * @param world
     * @param t
     */
    public void draw(TETile[][] world, TETile t) {

        /**
         * Original drawing of room that DOES NOT ACCOUNT FOR OVERLAP
         */
        int width = this.width;
        int height = this.height;
        int startX = this.x;
        int startY = this.y;

        int endX = this.x + width;
        int endY = this.y + height;


        //Create the left and right walls
        for(int i = startY; i < endY + 1; i++) {
            world[startX][i] = Tileset.WALL;
            world[endX][i] = Tileset.WALL;
        }

        for(int j = startX + 1; j < endX; j++) {
            //create the first entry as wall
            world[j][startY] = Tileset.WALL;

            //create 2nd to 1 before last as floors
            for (int k = startY + 1; k < endY; k++) {
                world[j][k] = t;
            }

            //create last entry as wall
            world[j][endY] = Tileset.WALL;
        }


    }

    /**
     * @param direction -- Given a randomly selected direction (0 = north)
     * @param r -- of class Random generated from the seed; used to get
     *          a random x/y value for the tile you want to open on the side of the
     *          given direction
     * @param world
     */


//    public Pos randomOpeningGenerator(int direction, Random r, TETile[][] world) {
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
//        return p;
//
//    }

    /***
     * takes in a Room r and identify which type of hallway needed to construct
     * from current to room R
     * @param r
     * @return a size 2 String[] in which first
     */

    protected Pos[] connectHelper(Room r, Random rand) {
        if (this.overlap(r)) {
            return new Pos[2];
        }
        String[] notes = this.connectDirection(r);
        if (notes[0] == "straight") {
            return randomOpeningGeneratorS(r, notes[1], rand);
        } else {
            return randomOpeningGeneratorL(r, notes[1], rand);
        }
    }

    private boolean overlap(Room r) {
        return (this.startP.x <= r.endP.x && this.endP.x >= r.startP.x
                && this.startP.y <= r.endP.y && this.endP.y >= r.startP.y);

    }

    private String[] connectDirection(Room r) {
        String[] result = new String[2];
        //northeast region
        if (this.centerP.x <= r.centerP.x && this.centerP.y <= r.centerP.y) {
            if (this.endP.x - r.startP.x >= 3) {
                result[1] = "north";
                result[0] = "straight";
            } else if (this.endP.y - r.startP.y >= 3) {
                result[1] = "east";
                result[0] = "straight";
            } else {
                result[1] = "northeast";
                result[0] = "L";
            }
            //northwest region
        } else if (this.centerP.x >= r.centerP.x && this.centerP.y <= r.centerP.y) {
            if (r.endP.x - this.startP.x >= 3) {
                result[1] = "north";
                result[0] = "straight";
            } else if (this.endP.y - r.startP.y >= 3) {
                result[1] = "west";
                result[0] = "straight";
            } else {
                result[1] = "northwest";
                result[0] = "L";
            }
            //southeast region
        } else if (this.centerP.x <= r.centerP.x && this.centerP.y >= r.centerP.y) {
            if (this.endP.x - r.startP.x >= 3) {
                result[1] = "south";
                result[0] = "straight";
            } else if (r.endP.y - this.startP.y >= 3) {
                result[1] = "east";
                result[0] = "straight";
            } else {
                result[1] = "southeast";
                result[0] = "L";
            }
        } else {
            if (r.endP.x - this.startP.x >= 3) {
                result[1] = "south";
                result[0] = "straight";
            } else if (r.endP.y - this.startP.y >= 3) {
                result[1] = "west";
                result[0] = "straight";
            } else {
                result[1] = "southwest";
                result[0] = "L";
            }
        }
        return result;
    }

    protected Pos[] randomOpeningGeneratorS(Room r, String dir, Random rand) {
        int startx;
        int starty;
        int endx;
        int endy;

        if (dir == "north") {
            if (this.endP.x - r.startP.x >= 3) {
                startx = rand.nextInt(this.endP.x - r.startP.x - 2 ) + r.startP.x + 1;
            } else { //(r.endP.x - this.startP.x >= 3)
                startx = rand.nextInt(r.endP.x - this.startP.x - 2) + this.startP.x + 1;
            }
            endx = startx;
            starty = this.endP.y;
            endy = r.startP.y;
        } else if (dir == "south") {
            if (this.endP.x - r.startP.x >= 3) {
                startx = rand.nextInt(this.endP.x - r.startP.x - 2 ) + r.startP.x + 1;
            } else { //(r.endP.x - this.startP.x >= 3)
                startx = rand.nextInt(r.endP.x - this.startP.x - 2) + this.startP.x + 1;
            }
            endx = startx;
            starty = this.startP.y;
            endy = r.endP.y;
        } else if (dir == "east") {
            if ((r.endP.y - this.startP.y >= 3)) {
                starty = rand.nextInt(r.endP.y - this.startP.y - 2) + this.startP.y + 1;
            } else { //(this.endP.y - r.startP.y >= 3)
                starty = rand.nextInt(this.endP.y - r.startP.y - 2) + r.startP.y + 1;
            }
            endy = starty;
            startx = this.endP.x;
            endx = r.startP.x;
        } else {//west
            if ((r.endP.y - this.startP.y >= 3)) {
                starty = rand.nextInt(r.endP.y - this.startP.y - 2) + this.startP.y + 1;
            } else { //(this.endP.y - r.startP.y >= 3)
                starty = rand.nextInt(this.endP.y - r.startP.y - 2) + r.startP.y + 1;
            }
            endy = starty;
            startx = this.startP.x;
            endx = r.endP.x;
        }
       return new Pos[] {new Pos(startx, starty), new Pos(endx, endy)};
    }


    protected Pos[] randomOpeningGeneratorL(Room r, String dir, Random rand) {

        if (dir == "northeast") {
            return northeastHelper(r, rand);
        } else if (dir == "northwest") {
            return northwestHelper(r, rand);
        } else if (dir == "southeast") {
            return southeastHelper(r, rand);
        } else {
            return southwestHelper(r, rand);
        }
    }


    private Pos[] northeastHelper (Room r, Random rand) {

        int startx;
        int starty;
        int endx;
        int endy;
        if (this.endP.x >= r.startP.x) { // create start Pos on north side of curr, end Pos on west of r
            startx = rand.nextInt(r.startP.x - this.startP.x - 2) + this.startP.x + 1;
            starty = this.endP.y;
            endx = r.startP.x;
            endy = rand.nextInt(r.endP.y - r.startP.y - 2) + r.startP.y + 1;
        } else if (this.endP.y >= r.startP.y) { // create start Pos on east side of curr, end Pos on south of r
            startx = this.endP.x;
            starty = rand.nextInt(r.startP.y - this.startP.y - 2) + this.startP.y + 1;
            endx = rand.nextInt(r.endP.x - r.startP.x - 2) + r.startP.x + 1;
            endy = r.startP.y;
        } else { // create start Pos on north side of curr, end Pos on west of r
            startx = rand.nextInt(this.endP.x - this.startP.x - 2) + this.startP.x + 1;
            starty = this.endP.y;
            endx = r.startP.x;
            endy = rand.nextInt(r.endP.y - r.startP.y - 2) + r.startP.y + 1;
        }
        return new Pos[] {new Pos(startx, starty), new Pos(endx, endy)};
    }


    private Pos[] northwestHelper (Room r, Random rand) {

        int startx;
        int starty;
        int endx;
        int endy;
        if (r.endP.x >= this.startP.x) { // create start Pos on north side of curr, end Pos on east of r
            startx = rand.nextInt(this.endP.x - r.endP.x - 2) + r.endP.x + 1;
            starty = this.endP.y;
            endx = r.endP.x;
            endy = rand.nextInt(r.endP.y - r.startP.y - 2) + r.startP.y + 1;
        } else if (this.endP.y >= r.startP.y) { // create start Pos on west side of curr, end Pos on south of r
            startx = this.startP.x;
            starty = rand.nextInt(r.startP.y - this.startP.y - 2) + this.startP.y + 1;
            endx = rand.nextInt(r.endP.x - r.startP.x - 2) + r.startP.x + 1;
            endy = r.startP.y;
        } else { // create start Pos on north side of curr, end Pos on east of r
            startx = rand.nextInt(this.endP.x - this.startP.x - 2) + this.startP.x + 1;
            starty = this.endP.y;
            endx = r.endP.x;
            endy = rand.nextInt(r.endP.y - r.startP.y - 2) + r.startP.y + 1;
        }
        return new Pos[] {new Pos(startx, starty), new Pos(endx, endy)};
    }

    private Pos[] southeastHelper (Room r, Random rand) {

        int startx;
        int starty;
        int endx;
        int endy;

        if (this.endP.x >= r.startP.x) { // create start Pos on south side of curr, end Pos on west of r
            startx = rand.nextInt(r.startP.x - this.startP.x - 2) + this.startP.x + 1;
            starty = this.startP.y;
            endx = r.startP.x;
            endy = rand.nextInt(r.endP.y - r.startP.y - 2) + r.startP.y + 1;
        } else if (r.endP.y >= this.startP.y) { // create start Pos on east side of curr, end Pos on north of r
            startx = endP.x;
            starty = rand.nextInt(this.endP.y - r.endP.y - 2) + r.endP.y + 1;
            endx = rand.nextInt(r.endP.x - r.startP.x - 2) + r.startP.x + 1;
            endy = r.endP.y;
        } else { // create start Pos on south side of curr, end Pos on west of r
            startx = rand.nextInt(this.endP.x - this.startP.x - 2) + this.startP.x + 1;
            starty = this.startP.y;
            endx = r.startP.x;
            endy = rand.nextInt(r.endP.y - r.startP.y - 2) + r.startP.y + 1;
        }
        return new Pos[] {new Pos(startx, starty), new Pos(endx, endy)};
    }

    private Pos[] southwestHelper (Room r, Random rand) {
        int startx;
        int starty;
        int endx;
        int endy;

        if (r.endP.x >= this.startP.x) { // create start Pos on south side of curr, end Pos on east of r
            startx = rand.nextInt(this.endP.x - r.endP.x - 2) + r.endP.x + 1;
            starty = this.startP.y;
            endx = r.endP.x;
            endy = rand.nextInt(r.endP.y - r.startP.y - 2) + r.startP.y + 1;
        } else if (r.endP.y >= this.startP.y) { // create start Pos on west side of curr, end Pos on north of r
            startx = this.startP.x;
            starty = rand.nextInt(this.endP.y - r.endP.y - 2) + r.endP.y + 1;
            endx = rand.nextInt(r.endP.x - r.startP.x - 2) + r.startP.x + 1;
            endy = r.endP.y;
        } else { // create start Pos on south side of curr, end Pos on east of r
            startx = rand.nextInt(this.endP.x - this.startP.x - 2) + this.startP.x + 1;
            starty = this.startP.y;
            endx = r.endP.x;
            endy = rand.nextInt(r.endP.y - r.startP.y - 2) + r.startP.y + 1;
        }
        return new Pos[] {new Pos(startx, starty), new Pos(endx, endy)};

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
    public TETile[][] close(TETile[][] world, String direction, Pos p) {
        world[p.x][p.y] = Tileset.WALL;

        return world;
    }




}
