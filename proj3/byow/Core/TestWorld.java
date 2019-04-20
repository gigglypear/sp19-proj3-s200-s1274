package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class TestWorld {

    /**Hard-coding, width and height ARE NOT 30
     */
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private static final Pos UPPERCORNER = new Pos(WIDTH - 1, HEIGHT - 1);

    /**Hard-coding, SEED should be a RANDOM INPUT selected by USER
     */
    private static final long SEED = 2099087;
    private static final Random RANDOM = new Random(SEED);


    private static Pos roomStartPos(TETile[][] world) {
        boolean isValid = false;
        Pos result = new Pos(0, 0);
        while (!isValid) {
            result = new Pos(RANDOM.nextInt(WIDTH - 1), RANDOM.nextInt(HEIGHT - 1));
            isValid = result.validatePos(UPPERCORNER, "room", world);
        }
        return result;
    }

    private static Pos roomEndPos(Pos startP, TETile[][] world) {
        boolean isValid = false;
        Pos result = new Pos(0, 0);
        while (!isValid) {
            //the random-generator generate a room of width/height from 2 to 8
            result = new Pos(startP.x + RANDOM.nextInt(6) + 2, startP.y + RANDOM.nextInt(6) + 2);
            isValid = result.validatePos(UPPERCORNER, "room", world);
        }
        return result;
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

        for (int i = 0; i < 4; i++) {

            Pos startP = roomStartPos(world);
            Pos endP = roomEndPos(startP, world);
            world = Room.add(world, startP, endP, Tileset.FLOOR);
        }


        world[29][29] = Tileset.WALL;
        ter.renderFrame(world);
    }

}
