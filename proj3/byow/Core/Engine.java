package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
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

    private int width;
    private int height;

    private String commandToProcess;
    private TERenderer ter;
    private World newworld;
    private TETile[][] world;
    private Avatar avatar;
    private Avatar avatar2;
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
            if (input.equals(":q")) {

                String saveStr = allStrokes.toString();

                saveGame(saveStr);
                quit = true;
                System.exit(0);


            } else if (input.equals("r")) {
                replay();

            } else if (input.length() != 0) {
//                allStrokes.append(input);
//                System.out.println("curr string: " + allStrokes);

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

        commandToProcess = input.toLowerCase();
        boolean terminate = false;
        while (!terminate) {
            if (commandToProcess.charAt(0) == 'n') {
//            allStrokes.append(input);
                newGame(commandToProcess);

            } else if (commandToProcess.charAt(0) == 'l') {

//            if (loadGame(input) == null) {
//                System.exit(0);
//            }
////            System.out.println("input passed into loadGame: " + input);
//            System.out.println("went to load game");
//            return loadGame(input);

                commandToProcess = loadGame(commandToProcess);
//                allStrokes.append(input);
            } else if (commandToProcess.charAt(0) == ':') {
                if (commandToProcess.charAt(1) == 'q') {
                    saveGame(allStrokes.toString());
                    commandToProcess = commandToProcess.substring(2);
//                       System.out.println("this saved: " + allStrokes.toString());
//                       break;
                }
            } else {
                //move
                if (commandToProcess.charAt(0) == 'w') {
                    world = avatar.goUp(world);
                } else if (commandToProcess.charAt(0) == 's') {
                    world = avatar.goDown(world);
                } else if (commandToProcess.charAt(0) == 'a') {
                    world = avatar.goLeft(world);
                } else if (commandToProcess.charAt(0) == 'd') {
                    world = avatar.goRight(world);
                } else if (commandToProcess.charAt(0) == 'u') {
                    world = avatar2.goUp(world);
                } else if (commandToProcess.charAt(0) == 'j') {
                    world = avatar2.goDown(world);
                } else if (commandToProcess.charAt(0) == 'h') {
                    world = avatar2.goLeft(world);
                } else if (commandToProcess.charAt(0) == 'k') {
                    world = avatar2.goRight(world);
                }
                allStrokes.append(commandToProcess.charAt(0));
                commandToProcess = commandToProcess.substring(1);

            }
            if (commandToProcess.length() == 0) {
                terminate = true;
            }
        }
        return world;
    }


    private void newGame(String input) {

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

        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        world = newworld.generateWorld(world);
        avatar = newworld.avatar;
        avatar2 = newworld.avatar2;

        //if there are more commands after "N#S",
        if (pointer + 1 < input.length()) {
            commandToProcess = input.substring(pointer + 1);
        } else {
            commandToProcess = "";
        }
    }

    private String loadGame(String input) {
        StringBuilder loading = new StringBuilder();

        try {
            BufferedReader fr = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File("./save.txt"))));
            String line = "";
            while ((line = fr.readLine()) != null) {
//                System.out.println("from txt file: " + line);
                loading.append(line);
            }
        } catch (IOException e) {
            System.out.println("exception case triggered for load");
        }


//        allStrokes = loading;
//        System.out.println("loading: " + loading);


        if (!initialized && loading.length() == 0) {
            world = new TETile[WIDTH][HEIGHT];
            for (int x = 0; x < WIDTH; x += 1) {
                for (int y = 0; y < HEIGHT; y += 1) {
                    world[x][y] = Tileset.NOTHING;
                }
            }
            return "";
        }

        String newinput = loading.toString() + input.substring(1);

        return newinput;
    }


    private void saveGame(String string) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new FileWriter("./save.txt", false)));
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

        String title = "CS61B: BYWOW";
        String newGame = "New Game (N)";
        String loadGame = "Load Game (L)";
        String quitGame = "Quit Game (Q)";
        String replayGame = "Replay Game (R)";

        StdDraw.text(width/ 2, (height/2) + 6, title);
        StdDraw.text(width / 2, (height / 2) + 3, newGame);
        StdDraw.text(width / 2, height / 2, loadGame);
        StdDraw.text(width / 2, (height / 2) - 3, replayGame);
        StdDraw.text(width / 2, (height / 2) - 6, quitGame);
        StdDraw.show();
    }


    private String mouseOver(Pos p) {
        TETile t = world[p.x][p.y];
        if (t.equals(Tileset.AVATAR)) {
            return "PLAYER1";
        } else if (t.equals(Tileset.AVATAR2)) {
            return "PLAYER2";
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


    private void replay() {

        String loadinput = loadGame("l");

        int pointer = 0;
        while (pointer < loadinput.length()) {
            pointer++;
            if (loadinput.charAt(pointer) == 's' || loadinput.charAt(pointer) == 'S') {
                break;
            }
        }

        String seedCopy = loadinput.substring(0, pointer + 1);

        newGame(seedCopy);

        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);

        String strokeToPlay = loadinput.substring(pointer + 1);

        while (strokeToPlay.length() != 0) {
            StdDraw.pause(150);
            String currStroke = strokeToPlay.substring(0, 1);
            strokeToPlay = strokeToPlay.substring(1);
            world = interactWithInputString(currStroke);
            ter.renderFrame(world);
        }

//        System.out.println(pointer);

//if there are more commands after "N#S",
//        for(int i = pointer + 1; pointer < loadinput.length() - 1; i++){
//            if(i + 1 != loadinput.length()){
////                String next = loadinput.substring(i, (i + 1));
//                System.out.println("next index: " + i);
//                System.out.println(i);
//            }
////            interactWithInputString(next);
//        }
    }

    private boolean validateMouseCoor(Pos p) {
        return (p.x < WIDTH && p.x >= 0 && p.y < HEIGHT && p.y >= 0);
    }

//    public static void main(String[] args) {
//        Engine engine = new Engine();
//        engine.replay();
//    }

}

/**
 * @source: https://stackoverflow.com/questions/1625234/how-to-append-
 * text-to-an-existing-file-in-java
 * @source: https://www.journaldev.com/881/java-append-to-file
 * @source: https://stackoverflow.com/questions/16135726/java-how-to-read-from
 * -file-when-i-used-printwriter-bufferedwriter-and-filewri
 **/
