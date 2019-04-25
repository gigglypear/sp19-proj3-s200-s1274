package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;
import java.util.stream.IntStream;

public class SimpleTest {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private static final long SEED = 99976; //87687;
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
         * Simple test where we initalize a room and then generate random openings on
         * a given orientation
         */
//        //Generate initial room
//        Room room1 = new Room(new Pos(35, 12), new Pos(41, 16));
//        room1.draw(world, Tileset.FLOOR);
//
//        //Randomly decide if a side (direction) needs to be open
//        int[] openPos = new int[4];
//        for (int i = 0; i < 4; i++) {
//            openPos[i] = RANDOM.nextInt(4);
//        }
//
//        //If no sides were opened during random selection then randomly select a room side
//        //to open
//        while (IntStream.of(openPos).sum() == 0) {
//            openPos[RANDOM.nextInt(3)] = 1;
//        }
//
//        //Using method to open a tile at a given side
//        for (int i = 0; i < 4; i++) {
//            if (openPos[i] != 0) {
//                room1.randomOpeningGenerator(i, RANDOM, world);
//            }
//        }


        //Generate initial room

        //Room room1 = Room.roomGenerator(world, RANDOM);

//        Pos start1 = new Pos(5, 6);
//        Pos end1 = new Pos(10, 15);
//        Room room1 = new Room(start1, end1);
//        room1.draw(world, Tileset.FLOOR);
//
//        Pos start2 = new Pos(12, 20);
//        Pos end2 = new Pos(22, 26);
//        Room room2 = new Room(start2, end2);
//        //Room room2 = Room.roomGenerator(world, RANDOM);
//        room2.draw(world, Tileset.FLOOR);
//
//
//
//        Hallway h = new Hallway(new Pos(16, 20), new Pos(10, 11));
//        Hallway.drawL(world, Tileset.FLOOR, new Pos(16, 11), h);

        Pos start1 = new Pos(5, 15);
        Pos end1 = new Pos(10, 21);
        Room room1 = new Room(start1, end1);
        room1.draw(world, Tileset.FLOOR);

        Pos start2 = new Pos(12, 10);
        Pos end2 = new Pos(22, 15);
        Room room2 = new Room(start2, end2);
        //Room room2 = Room.roomGenerator(world, RANDOM);
        room2.draw(world, Tileset.FLOOR);



        Hallway h = new Hallway(new Pos(13, 15), new Pos(10, 16));
        Hallway.drawL(world, Tileset.FLOOR, new Pos(13, 16), h);

        String[] result = room1.connectDirection(room2);
        System.out.print(result[0] + " " + result[1]);

        ter.renderFrame(world);
    }
}
