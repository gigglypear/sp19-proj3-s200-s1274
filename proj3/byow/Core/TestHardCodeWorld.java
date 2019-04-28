package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;
import java.util.stream.IntStream;

public class TestHardCodeWorld {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static final long SEED = 9298734;//77
    private static final Random RANDOM = new Random(SEED);

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


        /**
         * Generates 1 room with start at (1,1) and w: 5 h: 10
         */
//        Pos start = new Pos(1, 1);
//        Room room1 = new Room(start, 5, 10 );
//        room1.draw(world, Tileset.FLOOR);


        /**
         * Generates 1 room with start (20,20) and w: 8 h: 4
         */

//        Pos start2 = new Pos(20, 20);
//        Room room2 = new Room(start2, 8, 4);
//        room2.draw(world, Tileset.FLOOR);
//
//        Pos start3 = new Pos(30, 1);
//        Room room3 = new Room(start3, 10, 10);
//        room3.draw(world, Tileset.FLOOR);
//
//
//
//        Hallway hall13 = new Hallway(new Pos(6, 2), new Pos(30, 2));
//        hall13.drawHor(world, Tileset.FLOOR, hall13);

        /**
         * Tests connection of 2 rooms with VERTICAL hallways
         */
//        Pos start2 = new Pos(0, 0);
//        Room room2 = new Room(start2, 5, 5);
//        room2.draw(world, Tileset.FLOOR);
//
//        Pos start3 = new Pos(0, 10);
//        Room room3 = new Room(start3, 5, 5);
//        room3.draw(world, Tileset.FLOOR);
//
//        Hallway hall13 = new Hallway(new Pos(1, 5), new Pos(1, 10));
//        Hallway.drawVer(world, Tileset.FLOOR, hall13);

        /**
         * Tests connection of two rooms with TURNING hallway
         */
//        Pos start2 = new Pos(0, 0);
//        Room room2 = new Room(start2, 5, 5);
//        room2.draw(world, Tileset.FLOOR);
//
//        Pos start3 = new Pos(10, 10);
//        Room room3 = new Room(start3, 6, 5);
//        room3.draw(world, Tileset.FLOOR);
//
//        Hallway hall13 = new Hallway(new Pos(1, 5), new Pos(10, 11));
//
//        Pos turningPt = Hallway.turningPos(new Pos(1, 5), new Pos(10, 11));
//        //Pos turningPt = Hallway.turningPos(new Pos(10, 11), new Pos(1, 5));
//        //should get new Pos(1, 11)
//
//        Hallway.drawL(world, Tileset.FLOOR, turningPt,hall13);

        /**
         * Tests 1 room and random room opening on NORTH side
         */
//        Pos start = new Pos(1, 1);
//        Room room = new Room(start, 5, 5);
//        room.draw(world, Tileset.FLOOR);
//        room.randomOpeningGenerator(0, RANDOM, world);

        /**
         * Tests connection two roms with HORIZONTAL hallways
         * with CONNECT ROOMS method
         * with OVERLAP
         */

//        Pos start2 = new Pos(1, 1);
//        Room room2 = new Room(start2, 8, 4);
//        room2.draw(world, Tileset.FLOOR);
//        room2.open(world, new Pos(9, 2), Tileset.FLOOR);
//        Pos open2 = new Pos(9,2);
//
//        Pos start3 = new Pos(20, 1);
//        Room room3 = new Room(start3, 10, 10);
//        room3.draw(world, Tileset.FLOOR);
//        room3.open(world, new Pos(20, 2), Tileset.FLOOR);
//        Pos open3 = new Pos(20,2);
//
//        Pos start4 = new Pos(35, 1);
//        Room room4 = new Room(start4, 3, 6);
//        room4.draw(world, Tileset.FLOOR);
//        room4.open(world, new Pos(35, 2), Tileset.FLOOR);
//        Pos open4 = new Pos(35, 2);
//
//        World.connect(world, open2, open4);
//
//        Pos stRm = new Pos(38, 2);
//        Pos noRm = new Pos(40, 2);
//        World.connect(world, stRm, noRm);


//        //Expected hall
//        Hallway hall13 = new Hallway(new Pos(6, 2), new Pos(30, 2));
//        hall13.drawHor(world, Tileset.FLOOR, hall13);


        /**
         * Tests connection of 2 rooms with VERTICAL hallways
         * with OVERLAP
         //         */
//        Pos start2 = new Pos(0, 0);
//        Room room2 = new Room(start2, 5, 5);
//        room2.draw(world, Tileset.FLOOR);
//
//        Pos open2 = new Pos(1, 5);
//        room2.open(world, open2, Tileset.FLOOR);
//
//        Pos start3 = new Pos(0, 10);
//        Room room3 = new Room(start3, 5, 5);
//        room3.draw(world, Tileset.FLOOR);
//
//        Pos start4 = new Pos(0, 20);
//        Room room4 = new Room(start4, 5, 5);
//        room4.draw(world, Tileset.FLOOR);
//
//        Pos open4 = new Pos(1, 20);
//        room4.open(world, open4, Tileset.FLOOR);
//
//        World.connect(world, open2, open4);

//        //Expected hall
//        Hallway hall13 = new Hallway(new Pos(1, 5), new Pos(1, 10));
//        Hallway.drawVer(world, Tileset.FLOOR, hall13);

        /**
         * Tests connection of two rooms with TURNING hallway
         */
//        Pos start2 = new Pos(0, 0);
//        Room room2 = new Room(start2, 5, 5);
//        room2.draw(world, Tileset.FLOOR);
//
//        Pos start3 = new Pos(10, 10);
//        Room room3 = new Room(start3, 6, 5);
//        room3.draw(world, Tileset.FLOOR);
//
//        //Room that OVERLAPS where the TURNING POINT falls in the room
//        Pos start4 = new Pos(9, 0);
//        Room room4 = new Room(start4, 10, 6);
//        room4.draw(world, Tileset.FLOOR);
//
//        World.connect(world, new Pos(1, 5), new Pos(10, 11), room3);
//
//        Hallway hall13 = new Hallway(new Pos(1, 5), new Pos(10, 11));
//
//        Pos turningPt = Hallway.turningPos(new Pos(1, 5), new Pos(10, 11));
//        //Pos turningPt = Hallway.turningPos(new Pos(10, 11), new Pos(1, 5));
//        //should get new Pos(1, 11)
//
//        Hallway.drawL(world, Tileset.FLOOR, turningPt,hall13);

        /**
         * Two rooms that OVERLAP
         */
//        Pos start2 = new Pos(5, 5);
//        Room room2 = new Room(start2, 10, 10);
//        room2.draw(world, Tileset.FLOOR);
//
//        Pos start3 = new Pos(7, 7);
//        Room room3 = new Room(start3, 10, 12);
//        room3.draw(world, Tileset.FLOOR);


//        Room room = new Room(new Pos(5,5), 8, 8);
//        room.draw(world, Tileset.FLOOR);
//        this.putAvatar(room, world, RANDOM);



        /**
         * Generates the world
         */
        ter.renderFrame(world);
    }
}
