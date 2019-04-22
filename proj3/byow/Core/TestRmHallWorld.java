package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class TestRmHallWorld {


    /**
     * tentative approach:
     * a) create a room at the bottom left region
     *      - need to be within certain distance of the bottom left corner (0,0),
     *          dont want to be too far out
     * b) create a hallway
     */


    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;


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
//
//
//        Room room1 = new Room(new Pos(35, 12), new Pos(41, 16));
//        room1.draw(world, Tileset.FLOOR);
//
//
//
//        room1.open(world, new Pos(35,16), Tileset.FLOOR);
//
//        ter.renderFrame(world);

    }
}
