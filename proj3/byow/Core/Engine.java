package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.BufferedReader;


public class Engine {

    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    protected long seed;
    protected String command;

    private int width;
    private int height;

    private TERenderer ter;
    private World newworld;
    private TETile[][] world;
    private Avatar avatar;
//    private String input;

    private Pos mouseCood;
    private boolean initialized;
    private String tiletype;

    private StringBuilder allStrokes = new StringBuilder();


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        System.out.println("interact called");
        drawWelcomeWindow();

        ter = new TERenderer();


        boolean quit = false;
        while (!quit) {
            if (initialized) {
                while (!StdDraw.hasNextKeyTyped()) {
                    StdDraw.clear(Color.black);
                    ter.renderFrameNoShow(world);
                    mouseCood = new Pos((int) Math.floor(StdDraw.mouseX()),
                            (int) Math.floor(StdDraw.mouseY()));
//                    System.out.println(mouseCood.x + " , " + mouseCood.y);
                    if (validateMouseCoor(mouseCood)) {
                        tiletype = mouseOver(mouseCood);
//                        System.out.println(tiletype);
                        Font fonthud = new Font("Monaco", Font.BOLD, 14);
                        StdDraw.setPenColor(StdDraw.YELLOW);
                        StdDraw.setFont(fonthud);
                        StdDraw.text(2, HEIGHT - 2, tiletype);
                        StdDraw.show();

                    }
                }
            }

            String input = Keyboard.sollicitInput();

            System.out.println(input);
            if (input.equals(":Q") || input.equals(":q")) {

                String saveStr = allStrokes.toString();

                saveGame(saveStr);
                quit = true;
                System.exit(0);


            } else if (input.length() != 0) {
//                allStrokes.append(input);
                System.out.println("curr string: " + allStrokes);

                world = interactWithInputString(input);

                if (!initialized) {
                    System.exit(0);
                    return;
                } else {
                    ter.renderFrame(world);
                }

//                ter.renderFrame(world);

            }


        }


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

//        world = new TETile[1][1];
//        world[0][0] = Tileset.NOTHING;

//        TETile[][] toLoad;
        if (input.length() == 0) {
            world = new TETile[WIDTH][HEIGHT];
            for (int x = 0; x < WIDTH; x += 1) {
                for (int y = 0; y < HEIGHT; y += 1) {
                    world[x][y] = Tileset.NOTHING;
                }
            }
            return world;
        } else if ((input.charAt(0) == 'N' || input.charAt(0) == 'n')) {
//            allStrokes.append(input);
            return newGame(input);

        } else if ((input.charAt(0) == 'L') || input.charAt(0) == 'l') {

//            if (loadGame(input) == null) {
//                System.exit(0);
//            }
////            System.out.println("input passed into loadGame: " + input);
//            System.out.println("went to load game");
//            return loadGame(input);

            String newinput = loadGame(input);
            return interactWithInputString(newinput);


        } else {

            for (int i = 0; i < input.length(); i++) {
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
                        saveGame(allStrokes.toString());
//                        System.out.println("this saved: " + allStrokes.toString());
                        break;
                    }
                }
                allStrokes.append(input.charAt(i));
            }
//            ter.renderFrame(world);
            return world;
        }
    }


    private TETile[][] newGame(String input) {

        allStrokes = new StringBuilder();
        int pointer = 0;
        while (pointer < input.length()) {
            pointer++;
            if (input.charAt(pointer) == 's' || input.charAt(pointer) == 'S') {
                break;
            }
        }
        seed = Long.parseLong(input.substring(1, pointer));

        newworld = new World(seed);

        allStrokes.append("n");
        allStrokes.append(seed);
        allStrokes.append("s");

        initialized = true;

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

        //if there are more commands after "N#S",
        if (pointer + 1 < input.length()) {
            String rest = input.substring(pointer + 1);
            interactWithInputString(rest);
        }

//        ter.renderFrame(world);
        return world;
    }

    private String loadGame(String input) {
        StringBuilder loading = new StringBuilder();

        try {
            BufferedReader fr = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File("./byow/core/save.txt"))));
            String line = "";
            while ((line = fr.readLine()) != null) {
                System.out.println("from txt file: " + line);
                loading.append(line);
            }
        } catch (IOException e) {
            System.out.println("exception case triggered for load");
        }


//        allStrokes = loading;
//        System.out.println("loading: " + loading);


//        try {
//            PrintWriter out = new PrintWriter(new BufferedWriter(
//                    new FileWriter("./byow/core/save.txt", false)));
//            out.println("");
//            out.close();
//        } catch (IOException e) {
//            //exception handling left as an exercise for the reader
//            System.out.println("exception case triggered");
//        }

        if (!initialized && loading.length() == 0) {
//            System.exit(0);
            return "";
        }


        String newinput = loading + input.substring(1);

        return newinput;
    }


    private void saveGame(String string) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new FileWriter("./byow/core/save.txt", false)));
            out.println(string);
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            System.out.println("exception case triggered");
        }

    }

    public void drawWelcomeWindow() {

//        edu.princeton.cs.algs4.StdDraw.clear();

        this.width = WIDTH;
        this.height = HEIGHT;

        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

//        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(font);

        String newGame = "New Game (N)";
        String loadGame = "Load Game (L)";
        String quitGame = "Quit Game (Q)";

        StdDraw.text(width / 2, (height / 2) + 3, newGame);
        StdDraw.text(width / 2, height / 2, loadGame);
        StdDraw.text(width / 2, (height / 2) - 3, quitGame);
        StdDraw.show();
    }


    private String mouseOver(Pos p) {
        TETile t = world[p.x][p.y];
        if (t.equals(Tileset.AVATAR)) {
            return "Avatar";
        } else if (t.equals(Tileset.WALL)) {
            return "WALL";
        } else if (t.equals(Tileset.FLOOR)) {
            return "FLOOR";
        } else if (t.equals(Tileset.NOTHING)) {
            return "VOID";
        } else {
            return "???";
        }

    }

    private boolean validateMouseCoor(Pos p) {
        return (p.x < WIDTH && p.x >= 0 && p.y < HEIGHT && p.y >= 0);
    }

}

/**
 * @source: https://stackoverflow.com/questions/1625234/how-to-append-
 * text-to-an-existing-file-in-java
 * @source: https://www.journaldev.com/881/java-append-to-file
 * @source: https://stackoverflow.com/questions/16135726/java-how-to-read-from
 * -file-when-i-used-printwriter-bufferedwriter-and-filewri
 **/
