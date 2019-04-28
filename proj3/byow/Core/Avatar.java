package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Avatar {

    private Pos location;

    public Avatar(Pos loc){
        location = loc;
    }

    protected TETile[][] goUp(TETile[][] world){
        if (world[location.x][location.y + 1] != Tileset.WALL) {
            world[location.x][location.y] = Tileset.FLOOR;
            world[location.x][location.y + 1] = Tileset.AVATAR;
            location = new Pos(location.x, location.y + 1);
        }
        return world;
    }

    protected TETile[][] goDown(TETile[][] world){
        if (world[location.x][location.y - 1] != Tileset.WALL) {
            world[location.x][location.y] = Tileset.FLOOR;
            world[location.x][location.y - 1] = Tileset.AVATAR;
            location = new Pos(location.x, location.y - 1);
        }
        return world;

    }

    protected TETile[][] goLeft(TETile[][] world){

        if (world[location.x - 1][location.y] != Tileset.WALL) {
            world[location.x][location.y] = Tileset.FLOOR;
            world[location.x - 1][location.y] = Tileset.AVATAR;
            location = new Pos(location.x - 1, location.y);
        }
        return world;

    }

    protected TETile[][] goRight(TETile[][] world){
        if (world[location.x + 1][location.y] != Tileset.WALL) {
            world[location.x][location.y] = Tileset.FLOOR;
            world[location.x + 1][location.y] = Tileset.AVATAR;
            location = new Pos(location.x + 1, location.y);
        }
        return world;
    }
}
