package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    //This class create holds two integer x and y that signify the x and y value of the coordinate.
    private static class Pos {
        public int x;
        public int y;

        public Pos(int Posx, int Posy) {
            x = Posx;
            y = Posy;
        }
    }


    public static TETile[][] hexGenerator(int size, Pos coord, TETile[][] world, TETile t) {
        world = hexUpper(size, coord, world, t);
        Pos lowerStart = new Pos(coord.x, coord.y + 2 * size - 1);
        world = hexLower(size, lowerStart, world, t);
        return world;
    }

    private static TETile[][] hexUpper(int size, Pos coord, TETile[][] world, TETile t) {
        int y = coord.y;
        int xinit = coord.x;
        int xend = xinit + size;
        for (int i = y; i < y + size; i += 1) {
            for (int j = xinit; j < xend; j += 1) {
                world[j][i] = t;
            }
            xinit--;
            xend++;

        }
        return world;
    }

    private static TETile[][] hexLower(int size, Pos coord, TETile[][] world, TETile t) {
        int y = coord.y;
        int xinit = coord.x;
        int xend = xinit + size;
        for (int i = y; i > y - size; i -= 1) {
            for (int j = xinit; j < xend; j += 1) {
                world[j][i] = t;
            }
            xinit--;
            xend++;

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
                world[x][y] = Tileset.WALL;
            }
        }

        world[0][0] = Tileset.WALL;

        world = hexGenerator(2, new Pos(3,3), world, Tileset.WATER);
        world = hexGenerator(3, new Pos(9,3), world, Tileset.FLOWER);
        world = hexGenerator(4, new Pos(18,3), world, Tileset.MOUNTAIN);
        // draws the world to the screen
        ter.renderFrame(world);
    }



}
