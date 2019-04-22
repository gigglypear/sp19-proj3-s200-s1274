package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestWorld {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private static final Pos UPPERCORNER = new Pos(WIDTH - 1, HEIGHT - 1);

    /**Hard-coding, SEED should be a RANDOM INPUT selected by USER
     */

//    private static final long SEED = 2099087;
//    private static final Random RANDOM = new Random(SEED);
//
//
//    protected static Pos roomStartPos(TETile[][] world) {
//        boolean isValid = false;
//        Pos result = new Pos(0, 0);
//        while (!isValid) {
//            result = new Pos(RANDOM.nextInt(WIDTH - 1), RANDOM.nextInt(HEIGHT - 1));
//            isValid = result.validatePos(UPPERCORNER, "room", world);
//        }
//        return result;
//    }
//
//    protected static Pos roomEndPos(Pos startP, TETile[][] world) {
//        boolean isValid = false;
//        Pos result = new Pos(0, 0);
//        while (!isValid) {
//            //the random-generator generate a room of width/height from 2 to 8
//            result = new Pos(startP.x + RANDOM.nextInt(6) + 2, startP.y + RANDOM.nextInt(6) + 2);
//            isValid = result.validatePos(UPPERCORNER, "room", world);
//        }
//        return result;
//    }
//
//    protected static Pos[] roomValidator(List<Room> rooms, TETile[][] world) {
//
//        boolean isValid = true;
//        Pos startP = new Pos(0, 0);
//        Pos endP = new Pos(0, 0);
//        while (isValid) {
//            startP = roomStartPos(world);
//            endP = roomEndPos(startP, world);
//            Room potentialRm = new Room(startP, endP);
//            isValid = potentialRm.overlap(rooms);
//        }
//        return new Pos[] {startP, endP};
//    }

    private static final long SEED = 87687;
    private static final Random RANDOM = new Random(SEED);


    protected static Pos roomStartPos(TETile[][] world) {
        boolean isValid = false;
        Pos result = new Pos(0, 0);
        while (!isValid) {
            result = new Pos(RANDOM.nextInt(WIDTH - 1), RANDOM.nextInt(HEIGHT - 1));
            isValid = result.validatePos(UPPERCORNER, "room", world);
        }
        return result;
    }

    protected static Pos roomEndPos(Pos startP, TETile[][] world) {
        boolean isValid = false;
        int counter = 0;
        Pos result = new Pos(0, 0);
        while (!isValid) {
            //the random-generator generate a room of width/height from 2 to 8
            result = new Pos(startP.x + RANDOM.nextInt(6) + 2, startP.y + RANDOM.nextInt(6) + 2);
            isValid = result.validatePos(UPPERCORNER, "room", world);
            counter++;
            if (counter > 5) {
                break;
            }
        }
        return result;
    }

    protected static Pos[] roomValidator(List<Room> rooms, TETile[][] world) {

        boolean isValid = true;
        Pos startP = new Pos(0, 0);
        Pos endP = new Pos(0, 0);
        while (isValid) {
            startP = roomStartPos(world);
            endP = roomEndPos(startP, world);
            Room potentialRm = new Room(startP, endP);
            isValid = potentialRm.overlap(rooms);
        }
        return new Pos[] {startP, endP};
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

        //List of rooms that are added to the world
        List<Room> rooms = new ArrayList();


//        //Draws a one room
//        Pos r1Start = new Pos(5, 5);
//        Pos r1End = new Pos(12, 12);
//
//        Room placedRoom = new Room(r1Start, r1End);
//        rooms.add(placedRoom);
//
//        Room.draw(world, r1Start, r1End, Tileset.FLOOR);
//
//        //Draws overlapping room
//        Pos r2Start = new Pos(7,3);
//        Pos r2End = new Pos(9, 14);
//
//        Room potentialRm = new Room(r2Start, r2End);
//        boolean isValid = potentialRm.overlap(rooms);
//        System.out.println(isValid);
//
//        //Room.draw(world, r2Start, r2End, Tileset.FLOOR);
//
//
//        //Draws non-overlapping room
//        Pos r3Start = new Pos(1,1);
//        Pos r3End = new Pos(3, 3);
//
//        Room potentialRm2 = new Room(r3Start, r3End);
//        boolean isValid2 = potentialRm2.overlap(rooms);
//        System.out.println(isValid2);
//
//        Room.draw(world, r3Start, r3End, Tileset.FLOOR);
//
//



        rooms = new ArrayList();

        for (int i = 0; i < 10; i++) {

//            Pos startP = roomStartPos(world);
//            Pos endP = roomEndPos(startP, world);
            Pos[] pos = roomValidator(rooms, world);
            Room room1 = new Room(pos[0], pos[1]);
            rooms.add(room1);
//            world = room1.draw(world, Tileset.FLOOR);

//        for (int i = 0; i < 10; i++) {
//
////            Pos startP = roomStartPos(world);
////            Pos endP = roomEndPos(startP, world);
//            Pos[] pos = roomValidator(rooms, world);
//            rooms.add(new Room(pos[0], pos[1]));
//            world = Room.draw(world, pos[0], pos[1], Tileset.FLOOR);
//
//
//        }

        }

        ter.renderFrame(world);
    }

}
