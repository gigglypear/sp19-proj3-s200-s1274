package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class World {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static final long SEED = 12345;//445; 12345 -- not all rooms connected with this seed
    private static final Random RANDOM = new Random(SEED);

    private List<Room> allRooms;
//    private HashMap<Room, Pos> OpenRm;
    private HashMap<Pos, Room> PosToRm;
    private List<Pos> openFringe;

    private int totalRms;

    public World() {
        allRooms = new ArrayList<>();
//        OpenRm = new HashMap<>();
        PosToRm = new HashMap<>();
        openFringe = new ArrayList<>();
        totalRms = 0;
    }

    public void generateWorld(TETile[][] world){


        /**
         * Generalization for method
         */
//        int direction = RandomUtils.uniform(RANDOM, 3);
//
//        Room initRm = Room.roomGenerator(world, RANDOM);
//        initRm.draw(world, Tileset.FLOOR);
//        totalRms += 1;
//
//        Pos open1 = initRm.randomOpeningGenerator(direction, RANDOM, world);
//        OpenRm.put(initRm, open1);
//        openFringe.add(open1);
//
//
////        int maxRms = RandomUtils.uniform(RANDOM, 3, 16);
//        int maxRms = 4; /**hard code for now; CHANGE*/
//        Room currRm = initRm;
//
//        while(maxRms != totalRms + 1){
//            Room RmToConnect = Room.roomGenerator(world, RANDOM);
//            RmToConnect.draw(world, Tileset.FLOOR);
//            totalRms += 1;
//
//            Pos endOpen = RmToConnect.randomOpeningGenerator(direction, RANDOM, world);
//            OpenRm.put(RmToConnect, endOpen);
//            openFringe.add(endOpen);
//
//            Pos startOpen= OpenRm.get(currRm);
//
//
//
//            World.connect(world, startOpen, endOpen);
//
//            currRm = RmToConnect;
//        }

        /**
         * Generate a random world
         */

        Room currRm = Room.roomGenerator(world, RANDOM);
        currRm.draw(world, Tileset.FLOOR);

        totalRms += 1;

//        int maxRms = RandomUtils.uniform(RANDOM, 5, 8); //can add more rooms once it works
        int maxRms = 4; /**hard code for now; CHANGE*/

        while(maxRms != totalRms) {
            Room RmToConnect = Room.roomGenerator(world, RANDOM);
            RmToConnect.draw(world, Tileset.FLOOR);
            totalRms += 1;



            Pos[] openPos = currRm.connectHelper(RmToConnect, RANDOM);

            ///when two rooms overlap, dont connect them, just move on!
            if (openPos[0] != null) {
                Pos startOpen = openPos[0];
                Pos endOpen = openPos[1];
                if (totalRms == 2) {
                    openFringe.add(startOpen);
                }
                openFringe.add(endOpen);
                PosToRm.put(startOpen, currRm);
                PosToRm.put(endOpen, RmToConnect);

                World.connect(world, startOpen, endOpen, currRm);
            }

            currRm = RmToConnect;
        }

        for(Pos opening: openFringe){
            int x = opening.x;
            int y = opening.y;

            world[x][y] = TileSelect.checkNeighbors(world, x, y, Tileset.WALL);
        }




    }

    public static TETile[][] connect(TETile[][] world, Pos start, Pos end, Room r){

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
        if(startY == endY){
            Hallway.drawHor(world, Tileset.FLOOR, hall);
        } else if (startX == endX){
            Hallway.drawVer(world, Tileset.FLOOR, hall);
        } else{
            Pos turningPt = hall.turningPos(start, end, r);
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

        World wholeWorld = new World();

        wholeWorld.generateWorld(world);


        ter.renderFrame(world);

    }
}
