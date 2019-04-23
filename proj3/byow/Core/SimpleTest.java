package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;
import java.util.stream.IntStream;

public class SimpleTest {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private static final long SEED = 809977; //87687;
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

        Room room1 = Room.roomGenerator(world, RANDOM);
        Room.draw(world, room1, Tileset.FLOOR);

        Room room2 = Room.roomGenerator(world, RANDOM);
        Room.draw(world, room2, Tileset.FLOOR);







        ter.renderFrame(world);
    }
}
