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
    private Pos treasureBox;
    private Pos bomb;
//    private String input;

    private Pos mouseCood;
    private boolean initialized;
    private String tiletype;
    private String tileblurb;
    private int health;
    private int health2;

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
                        tileblurb = tileText(mouseCood);
                        health = avatar.getCalories();
                        health2 = avatar2.getCalories();
//                        System.out.println(tiletype);
                        Font fonthud = new Font("Monaco", Font.BOLD, 14);
                        StdDraw.setPenColor(StdDraw.YELLOW);
                        StdDraw.setFont(fonthud);
                        StdDraw.text(2, HEIGHT - 2, tiletype);
                        StdDraw.text(17, HEIGHT - 4, tileblurb);
                        StdDraw.text(65, HEIGHT -2, "Aang's Calories: " + health);
                        StdDraw.text(65, HEIGHT - 4, "Katara's Calories: " + health2);
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

            } else if (input.equals("b")){
                drawBackgroundWindow();
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

                if (validateBombDis() == 1 && !avatar.warned)  {
                    drawWarning(1);
                } else if (validateBombDis() == 2 && !avatar2.warned) {
                    drawWarning(2);
                }

                if (validateBomb() == 1)  {
                    drawBomb(1);
                } else if (validateBomb() == 2) {
                    drawBomb(2);
                }

                if (validatewin() == 1) {
                    drawWinnerWindow(1);
                } else if (validatewin() == 2) {
                    drawWinnerWindow(2);
                }

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
        treasureBox = newworld.treasureBox;
        bomb = newworld.bomb;

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
        } else if (t.equals(Tileset.TREASURE)) {
            return "Treasure Box";
        } else {
            return "???";
        }

    }

    private String tileText(Pos p){
        TETile t = world[p.x][p.y];
        if (t.equals(Tileset.AVATAR)) {
            return "Aang";
        } else if (t.equals(Tileset.AVATAR2)) {
            return "Katara";
        } else if (t.equals(Tileset.WALL)) {
            return "A wall tile is a hash tag and multiple wall tiles are a hash set";
        } else if (t.equals(Tileset.FLOOR)) {
            return "To floor or to ceiling? #rendering problems";
        } else if (t.equals(Tileset.NOTHING)) {
            return "public static void or just public void?";
        } else if (t.equals(Tileset.TREASURE)) {
            return "MY PRECIOUS XD";
        } else {
            return "idk sorry";
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

    private int validatewin() {
        if (avatar.location.x == treasureBox.x && avatar.location.y == treasureBox.y) {
            return 1;
        } else if (avatar2.location.x == treasureBox.x && avatar2.location.y == treasureBox.y) {
            return 2;
        }
        if (avatar.getCalories() <= 0) {
            return 2;
        } else if (avatar2.getCalories() <= 0) {
            return 1;
        }

        return 0;
    }

    private int validateBombDis() {

        if (Math.abs(avatar.location.x - treasureBox.x) <= 2 && Math.abs(avatar.location.y - treasureBox.y) <= 2) {
            return 1;

        } else if (Math.abs(avatar2.location.x - treasureBox.x) == 2 && Math.abs(avatar2.location.y - treasureBox.y) == 2) {
            return 2;
        }
        return 0;
    }

    private int validateBomb() {
        if (avatar.location.x == bomb.x && avatar.location.y == bomb.y) {
            avatar.totalCalories -= 50;
            world[avatar.location.x][avatar.location.y] = Tileset.BOMB;
            return 1;

        } else if (avatar2.location.x == bomb.x && avatar2.location.y == bomb.y) {
            avatar2.totalCalories -= 50;
            world[avatar2.location.x][avatar2.location.y] = Tileset.BOMB;
            return 2;
        }
        return 0;
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
        String replayGame = "Replay Game (R)";
        String quitGame = "Quit Game (Q)";
        String lore = "Lore (B)";


        StdDraw.text(width/ 2, (height/2) + 6, title);
        StdDraw.text(width / 2, (height / 2) + 3, newGame);
        StdDraw.text(width / 2, height / 2, loadGame);
        StdDraw.text(width/2, (height/2) - 3, lore);
        StdDraw.text(width / 2, (height / 2) - 6, replayGame);
        StdDraw.text(width / 2, (height / 2) - 9, quitGame);
        StdDraw.show();
    }

    public void drawBackgroundWindow(){
        this.width = WIDTH;
        this.height = HEIGHT;

        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 16);
        //^^ previously size = 30; not sure if messes up game font, doesn't seem to be
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

//        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(font);

        String header = "~~~THE LORE~~~";
        String line1 = "Once upon a time, two students decided to test the limits of their abilities by taking";
        String line2 = "CS 61B. In the process, this game was created with the motto 'sometimes less is more.'";
        String line3 = "The random worlds are generated using an algorithm iteration haters would despise, but";
        String line4 = "hey at least a proper world should generate. There are two avatars in the game, so";
        String line5 = "two people can play. Journey together through the world, it's not that hard. But just";
        String line6 = "try not to hit the walls. Winning isn't that hard in this game, just move and get the";
        String line7 = "$$$. May the fourth be with you, because on the fifth you can't play anymore :)";
        String line8 = "~~~  ~~~  ~~~";
        String line9 = "New Game (N) | Load Game (L) | Replay (R) | Quit (Q)";


        StdDraw.text(width/2, (height/2) + 12, header);
        StdDraw.text(width/2, (height/2) + 9, line1);
        StdDraw.text(width/ 2, (height/2) + 6, line2);
        StdDraw.text(width / 2, (height / 2) + 3, line3);
        StdDraw.text(width / 2, height / 2, line4);
        StdDraw.text(width/2, (height/2) - 3, line5);
        StdDraw.text(width / 2, (height / 2) - 6, line6);
        StdDraw.text(width / 2, (height / 2) - 9, line7);
        StdDraw.text(width/2, (height/2) - 12, line8);
        StdDraw.text(width/2, (height/2) - 15, line9);
        StdDraw.show();
    }

    public void drawWinnerWindow(int player){
        StdDraw.clear(Color.BLACK);

        this.width = WIDTH;
        this.height = HEIGHT;

        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.setFont(font);

        String line1;
        String line2;

        if (player == 1) {
            line1 = "CONGRATS Aang!";
            line2 = "Way to go, Katara! You tried:)";
        } else {
            line1 = "CONGRATS Katara!";
            line2 = "Way to go, Aang! You tried:)";
        }



        StdDraw.text(width / 2, (height / 2) + 3, line1);
        StdDraw.text(width / 2, height / 2, line2);

        StdDraw.show();
        StdDraw.pause(10000);
        System.exit(0);
    }

    public void drawBomb(int player){
        StdDraw.clear(Color.BLACK);

        this.width = WIDTH;
        this.height = HEIGHT;

        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.setFont(font);

        String line1;
        String line2;

        if (player == 1) {
            line1 = "Aang encountered a bomb!";
            line2 = "Aang loses 50 calories :(";
        } else {
            line1 = "Katara encountered a bomb!";
            line2 = "Katara loses 50 calories :(";
        }



        StdDraw.text(width / 2, (height / 2) + 3, line1);
        StdDraw.text(width / 2, height / 2, line2);

        StdDraw.show();
        StdDraw.pause(2000);
    }

    private void drawWarning (int player) {
        StdDraw.clear(Color.BLACK);

        this.width = WIDTH;
        this.height = HEIGHT;

        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.setFont(font);

        String line1;
        String line2 = "Be careful there is a bomb nearby.";

        if (player == 1) {
            avatar.warned = true;
            line1 = "Aang is approaching the treasure!";

        } else {
            avatar2.warned = true;
            line1 = "Katara is approaching the treasure!";

        }



        StdDraw.text(width / 2, (height / 2) + 3, line1);
        StdDraw.text(width / 2, height / 2, line2);

        StdDraw.show();
        StdDraw.pause(5000);
    }

}

/**
 * @source: https://stackoverflow.com/questions/1625234/how-to-append-
 * text-to-an-existing-file-in-java
 * @source: https://www.journaldev.com/881/java-append-to-file
 * @source: https://stackoverflow.com/questions/16135726/java-how-to-read-from
 * -file-when-i-used-printwriter-bufferedwriter-and-filewri
 **/
