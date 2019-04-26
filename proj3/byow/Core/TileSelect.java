package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class TileSelect {

    public static void drawWalls(TETile[][] world, int x, int y) {
        TETile currTile = world[x][y];

        if (currTile != Tileset.FLOOR) {
            for (int i = -1; i < 2; i += 1) {
                for (int j = -1; j < 2; j += 1) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    if (world[x + i][y + j] == Tileset.FLOOR) {
                        world[x][y] = Tileset.WALL;
                    }
                }
            }

        }

    }

}
