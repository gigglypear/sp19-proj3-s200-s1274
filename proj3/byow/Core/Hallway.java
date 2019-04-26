package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Hallway implements Spaces {

    private Pos startP;
    private Pos endP;

    public Hallway(Pos start, Pos end){
        startP = start;
        endP = end;
    }

    /**
     * This methods generate a vertical hallway
     * start and end position are openings of the hallways and will be assigned with a floor tile
     */
    public static void drawVer(TETile[][] world, TETile t, Hallway h) {

        Pos start = h.startP;
        Pos end = h.endP;

        int s;
        int e;
        if (start.y < end.y) {
            s = start.y;
            e = end.y;
        } else {
            e = start.y;
            s = end.y;
        }
        for (int i = s; i < e + 1; i++) {
            world[start.x - 1][i] = Tileset.WALL;
            world[start.x][i] = t;
            world[start.x + 1][i] = Tileset.WALL;
        }


        world[end.x][end.y] = Tileset.WALL;

    }

    /**
     * Generate a hallway where the start is an opening and the ending is closed
     * @param world
     * @param t
     * @return
     */
    public static void drawHor(TETile[][] world, TETile t, Hallway h) {

        Pos start = h.startP;
        Pos end = h.endP;

        int s;
        int e;
        if (start.x < end.x) {
            s = start.x;
            e = end.x;
        } else {
            e = start.x;
            s = end.x;
        }

        for (int i = s; i < e + 1; i++) {

            world[i][start.y - 1] = Tileset.WALL;
            world[i][start.y] = t;
            world[i][start.y + 1] = Tileset.WALL;

        }

        world[end.x][end.y] = Tileset.WALL;

    }


    /**
     * Generates L shaped hallways
     * @param world
     * @param t
     * @param turningPt -- Position where hallway makes turn
     * @param h
     */
    public static void drawL(TETile[][] world, TETile t, Pos turningPt, Hallway h){
        Pos start = h.startP;
        Pos end = h.endP;

        //case where we draw a vertical hallway first
        if(start.x == turningPt.x){
            // case where vertical hallway go up
            if (start.y < turningPt.y) {
                drawVer(world, t, new Hallway(start, new Pos (turningPt.x, turningPt.y + 1)));
                world[turningPt.x][turningPt.y+1] = Tileset.WALL;
            } else { // case where vertical hallway go down
                drawVer(world, t, new Hallway(start, new Pos (turningPt.x, turningPt.y - 1)));
                world[turningPt.x][turningPt.y-1] = Tileset.WALL;
            }
            // draw horizontal hallway, case where horizontal go right
            if (turningPt.x < end.x) {
                drawHor(world, t, new Hallway(new Pos(turningPt.x + 1, turningPt.y), end));
            } else {
                drawHor(world, t, new Hallway(new Pos(turningPt.x - 1, turningPt.y), end));
            }
        } else { // case where we draw a horizontal hallway first
            // case where horizontal hallway go right
            if (start.x < turningPt.x) {
                drawHor(world, t, new Hallway(start, new Pos (turningPt.x + 1, turningPt.y)));
                world[turningPt.x + 1][turningPt.y] = Tileset.WALL;
            } else { // case where horizontal hallway go left
                drawHor(world, t, new Hallway(start, new Pos (turningPt.x - 1, turningPt.y)));
                world[turningPt.x - 1][turningPt.y] = Tileset.WALL;
            }
            // draw vertical hallway, case where vertical go up
            if (turningPt.y < end.y) {
                drawVer(world, t, new Hallway(new Pos(turningPt.x, turningPt.y + 1), end));

            } else { // case where vertical hallway go down
                drawVer(world, t, new Hallway(new Pos(turningPt.x, turningPt.y - 1), end));
            }
        }

        world[end.x][end.y] = t;

    }

    /**
     * Calculates the turning point for an L shaped hall
     * @param start -- start Position
     * @param end -- end Position
     * WARNING: Make sure this works for other cases
     * @return
     */
    public static Pos turningPos(Pos start, Pos end, Room r){
        Pos turningPt;
        if(start.y == r.getStartP().y || start.y == r.getEndP().y){
            turningPt = new Pos(start.x, end.y);
        } else{
            turningPt = new Pos(end.x, start.y);
        }
        System.out.println("turning pt " + turningPt.x + ", " + turningPt.y);
        return turningPt;

    }




    public  TETile[][] Open(TETile[][] world, Pos p, TETile t) {
        world[p.x][p.y] = t;
        return world;
    }


    public  TETile[][] Close(TETile[][] world, Pos p) {
        world[p.x][p.y] = Tileset.WALL;
        return world;
    }
}
