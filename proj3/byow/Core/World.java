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

    protected Avatar avatar;

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
         */
        int direction = RandomUtils.uniform(RANDOM, 3);

        Room initRm = Room.roomGenerator(world, RANDOM);
        initRm.draw(world, Tileset.FLOOR);
        allRooms.add(initRm);
        totalRms += 1;

        //Put down Avatar in the first room


        Pos open1 = initRm.randomOpeningGenerator(direction, RANDOM, world);
        openRm.put(initRm, open1);
        openFringe.add(open1);


        int maxRms = RandomUtils.uniform(RANDOM, 5, 10);
//        int maxRms = 6; /**hard code for now; CHANGE*/
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


        for (int x = 1; x < WIDTH - 1; x += 1) {
            for (int y = 1; y < HEIGHT - 1; y += 1) {
                TileSelect.drawWalls(world, x, y);
            }
        }

        Room firstRm = allRooms.get(0);
        this.putAvatar(firstRm, world, RANDOM);

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

    /***
     * this method put an Avatar randomly in the room
     * @param r
     * @param world
     * @param rand
     */
    protected void putAvatar(Room r, TETile[][] world, Random rand) {
        Pos rStart = r.getStartP();
        Pos rEnd = r.getEndP();

        int x = rand.nextInt(rEnd.x - rStart.x - 1) + rStart.x + 1;
        int y = rand.nextInt(rEnd.y - rStart.y - 1) + rStart.y + 1;
        Pos avatarLoc = new Pos(x, y);
        this.avatar = new Avatar(avatarLoc);
        world[x][y] = Tileset.AVATAR;
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


        World wholeWorld = new World(928374); //7752


        wholeWorld.generateWorld(world);


        ter.renderFrame(world);

    }
}
