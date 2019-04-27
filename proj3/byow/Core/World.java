package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class World {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
//    private static final long SEED;
//    private static final Random RANDOM;

    private long SEED;
    private Random RANDOM;

    private List<Room> allRooms;
    private HashMap<Room, Pos> openRm;
    private List<Pos> openFringe;

    private int totalRms;
    private int overlapTries;

    public World(long seed) {
        allRooms = new ArrayList<>();
        openRm = new HashMap<>();
        openFringe = new ArrayList<>();
        totalRms = 0;
        overlapTries = 0;
        SEED = seed;
        RANDOM = new Random(SEED);
    }

    public TETile[][] generateWorld(TETile[][] world) {


        /**
         * Generalization for method
         * Makes 1 initial room and an opening in that room -->
         * Adds room to list of all rooms -->
         * Adds open space to the fringe and maps intial room to its opening
         * (will get overridden for other rooms) -->
         * Pick a random number of rooms to open and sets inital rm to current
         * room
         * Makes another room and checks that there is not overlap -->
         *    If there's overlap generates a new room until no longer overlaps or
         *    when number of tries = 6 and then just lets overlap occur
         * Adds second room to list of all rooms -->
         * Makes random opening in second room -->
         * Conenects two rooms -->
         * Sets room that was just connected to current room
         * Repeats process until max number of rooms achieved
         */
        int direction = RandomUtils.uniform(RANDOM, 3);

        Room initRm = Room.roomGenerator(world, RANDOM);
        initRm.draw(world, Tileset.FLOOR);
        allRooms.add(initRm);
        totalRms += 1;

        Pos open1 = initRm.randomOpeningGenerator(direction, RANDOM, world);
        openRm.put(initRm, open1);
        openFringe.add(open1);


        int maxRms = RandomUtils.uniform(RANDOM, 5, 10);
        Room currRm = initRm;

        while (maxRms != totalRms + 1) {
            Room rmToConnect = Room.roomGenerator(world, RANDOM);

            while (rmToConnect.overlap(allRooms) && overlapTries < 6) {
                rmToConnect = Room.roomGenerator(world, RANDOM);
                overlapTries += 1;
            }


            rmToConnect.draw(world, Tileset.FLOOR);
            allRooms.add(rmToConnect);
            totalRms += 1;

            Pos endOpen = rmToConnect.randomOpeningGenerator(direction, RANDOM, world);
            openRm.put(rmToConnect, endOpen);
            openFringe.add(endOpen);

            Pos startOpen = openRm.get(currRm);


            World.connect(world, startOpen, endOpen, currRm);

            currRm = rmToConnect;

        }


        for (int x = 3; x < WIDTH - 1; x += 1) {
            for (int y = 3; y < HEIGHT - 1; y += 1) {
                TileSelect.drawWalls(world, x, y);
            }
        }

        return world;


    }

    public static TETile[][] connect(TETile[][] world, Pos start, Pos end, Room room) {

        int startX = start.x;
        int startY = start.y;
        int endX = end.x;
        int endY = end.y;

        Hallway hall = new Hallway(start, end);

        /**
         * if starting y and ending y same --> draw VERTICAL hall
         * if starting x and ending x same --> draw HORIZONTAL hall
         * else draw L hallway
         */
        if (startY == endY) {
            Hallway.drawHor(world, Tileset.FLOOR, hall);
        } else if (startX == endX) {
            Hallway.drawVer(world, Tileset.FLOOR, hall);
        } else {
            Pos turningPt = hall.turningPos(start, end, room);
            Hallway.drawL(world, Tileset.FLOOR, turningPt, hall);
        }

        return world;


    }


    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        World wholeWorld = new World(44959);

        wholeWorld.generateWorld(world);


        ter.renderFrame(world);

    }
}
