package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestQuaWorld {
    /**Hard-coding, width and height ARE NOT 30
     */
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    private static final Pos UPPERCORNER = new Pos(WIDTH - 1, HEIGHT - 1);

    /**Hard-coding, SEED should be a RANDOM INPUT selected by USER
     */
    private static final long SEED = 87687;
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
         * tentative approach: (parts surrounded by * are subject to change
         * 1) partition the world into a *4x4* grid
         * 2) for each grid box, generate 0 or 1 or 2 rooms
         *      2a) if generate 2 rooms, connect them
         * 3) once rooms are generated in each grid box, connect rooms in each grid box with hallways
         *      3a) start with bottom left corner and move top-right ward,
         *          a.k.a only connect upward and rightward
         *      3b) when connect a grid box with 1 room to a grid box with 2 rooms, connect to the closest room
         *      3c) when connect a grid box with 2 rooms to a grid box with one room, connect the closest
         * 4) if no hallways can be constructed between rooms, give up/ break out of current loop
         */

        ter.renderFrame(world);
    }
}
