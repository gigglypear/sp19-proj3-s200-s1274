package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Avatar {

    protected int totalCalories = 100;
    protected Pos location;
    private TETile t;
    protected boolean warned;

    public Avatar(Pos loc, TETile tile) {
        location = loc;
        t = tile;
        warned = false;
    }

    protected TETile[][] goUp(TETile[][] world) {
        if (world[location.x][location.y + 1] != Tileset.WALL) {
            world[location.x][location.y] = Tileset.FLOOR;
            world[location.x][location.y + 1] = t;
            location = new Pos(location.x, location.y + 1);
            totalCalories -= 1;
        } else{
            totalCalories -= 5;
        }

        return world;
    }

    protected TETile[][] goDown(TETile[][] world) {
        if (world[location.x][location.y - 1] != Tileset.WALL) {
            world[location.x][location.y] = Tileset.FLOOR;
            world[location.x][location.y - 1] = t;
            location = new Pos(location.x, location.y - 1);
            totalCalories -= 1;
        } else{
            totalCalories -= 5;
        }

        return world;

    }

    protected TETile[][] goLeft(TETile[][] world) {

        if (world[location.x - 1][location.y] != Tileset.WALL) {
            world[location.x][location.y] = Tileset.FLOOR;
            world[location.x - 1][location.y] = t;
            location = new Pos(location.x - 1, location.y);
            totalCalories -= 1;
        } else{
            totalCalories -= 5;
        }

        return world;

    }

    protected TETile[][] goRight(TETile[][] world) {
        if (world[location.x + 1][location.y] != Tileset.WALL) {
            world[location.x][location.y] = Tileset.FLOOR;
            world[location.x + 1][location.y] = t;
            location = new Pos(location.x + 1, location.y);
            totalCalories -= 1;
        } else{
            totalCalories -= 5;
        }

        return world;
    }

    public int getCalories(){
        return totalCalories;
    }
}
