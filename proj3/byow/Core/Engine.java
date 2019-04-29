package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.FileReader;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    public long seed;
    public String command;


    private World newworld;
    private TETile[][] world;
    private Avatar avatar;


    public Engine(){

        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        drawWelcomeWindow();

    }


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        System.out.println("interact called");

        Engine welcome = new Engine();
        welcome.drawWelcomeWindow();


    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
//        long maxLong = 9223372036854775807;
//        int inputLength = input.length();
//        if ((input.charAt(0) == 'N' || input.charAt(0) == 'n')
//                && (input.charAt(inputLength - 1) == 'S' || input.charAt(inputLength - 1) == 's')) {
//            long seed = Long.parseLong(input.substring(1, input.length() - 1));
//            World newworld = new World(seed);
//
//            TERenderer ter = new TERenderer();
//            ter.initialize(WIDTH, HEIGHT);
//
//            TETile[][] world = new TETile[WIDTH][HEIGHT];
//            for (int x = 0; x < WIDTH; x += 1) {
//                for (int y = 0; y < HEIGHT; y += 1) {
//                    world[x][y] = Tileset.NOTHING;
//                }
//            }
//
//            TETile[][] updateworld = newworld.generateWorld(world);
//            ter.renderFrame(updateworld);
//
//            return updateworld;

        if ((input.charAt(0) == 'N' || input.charAt(0) == 'n')) {
            return newGame(input);

        } else if ((input.charAt(0) == 'L') || input.charAt(0) == 'l') {
            return loadGame(input);

        } else {

            for(int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == 'W' || input.charAt(i) == 'w') {
                    world = avatar.goUp(world);
                } else if (input.charAt(i) == 'S' || input.charAt(i) == 's') {
                    world = avatar.goDown(world);
                } else if (input.charAt(i) == 'A' || input.charAt(i) == 'a') {
                    world = avatar.goLeft(world);
                } else if (input.charAt(i) == 'D' || input.charAt(i) == 'd') {
                    world = avatar.goRight(world);
                } else if (input.charAt(i) == ':') {
                    if (input.charAt(i + 1) == 'q' || input.charAt(i + 1) == 'Q') {
                        saveGame(input);
                        break;
                    }
                }
            }
//            ter.renderFrame(world);
            return world;
        }
    }


    private TETile[][] newGame(String input) {
        command = input;
        int pointer = 0;
        while (pointer < input.length()) {
            pointer++;
            if (input.charAt(pointer) == 's' || input.charAt(pointer) == 'S') {
                break;
            }
        }
        seed = Long.parseLong(input.substring(1, pointer - 1));

        newworld = new World(seed);

//        TERenderer ter = new TERenderer();
//        ter.initialize(WIDTH, HEIGHT);

        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        world = newworld.generateWorld(world);
        avatar = newworld.avatar;

        if (pointer + 1 <= input.length()) {
            String rest = input.substring(pointer + 1);
            interactWithInputString(rest);
        }
//        for(int i = pointer + 1; i < input.length(); i++) {
//            if (input.charAt(i) == 'W' || input.charAt(i) == 'w') {
//                world = avatar.goUp(world);
//            } else if (input.charAt(i) == 'S' || input.charAt(i) == 's') {
//                world = avatar.goDown(world);
//            } else if (input.charAt(i) == 'A' || input.charAt(i) == 'a') {
//                world = avatar.goLeft(world);
//            } else if (input.charAt(i) == 'D' || input.charAt(i) == 'd') {
//                world = avatar.goRight(world);
//            } else if (input.charAt(i) == ':') {
//                if (input.charAt(i) == 'q' || input.charAt(i) == 'Q') {
//                    saveGame(input);
//                    break;
//                }
//            }
//        }


//        ter.renderFrame(world);
        return world;
    }

    private TETile[][] loadGame(String input) {
        //FileReader reader = new FileReader("save.txt");

        return newGame(input);
    }


    private void saveGame(String input) {

    }

    public void drawWelcomeWindow() {

        //Don't know if need to clear?
        //For some reason StdDraw.clear() won't work and returns blank screen
//        StdDraw.clear();


        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setPenColor(edu.princeton.cs.algs4.StdDraw.WHITE);
        StdDraw.setFont(font);

        String newGame = "New Game (N)";
        String loadGame = "Load Game (L)";
        String quitGame = "Quit Game (Q)";

        StdDraw.text(WIDTH/2, (HEIGHT/2) + 3, newGame);
        StdDraw.text(WIDTH/2, HEIGHT/2, loadGame);
        StdDraw.text(WIDTH/2, (HEIGHT/2) - 3, quitGame);
        StdDraw.show();
    }
}
