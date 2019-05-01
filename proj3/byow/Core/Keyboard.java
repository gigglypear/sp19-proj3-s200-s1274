package byow.Core;

import edu.princeton.cs.introcs.StdDraw;


public class Keyboard {

    public static String sollicitInput() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char letter = StdDraw.nextKeyTyped();
                if (letter == 'N' || letter == 'n') {
                    System.out.println("new game ");
                    System.out.println(letter);
                    return getSeed(letter);

                } else if (letter == 'L' || letter == 'l') {
                    System.out.println("load game");
                    return Character.toString(letter);
                    //load method

                } else if (letter == ':') {
//                    System.out.println("gotta figure out how to quit");
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            char checkQ = StdDraw.nextKeyTyped();
                            if (checkQ == 'Q' || checkQ == 'q') {
                                //quit and save method
//                                System.out.println("really quit now");
                                return ":Q";
                            }
                            break;
                        }
                    }

                } else {
                    String input = Character.toString(letter).toLowerCase();
                    if (input.equals("a") || input.equals("w") || input.equals("s")
                            || input.equals("d") || input.equals("u") || input.equals("j")
                            || input.equals("h") || input.equals("k")) {
                        return input;
                    } else {
                        return "";
                    }
                }
            }

        }
    }


    private static String getSeed(char n) {

        StringBuilder allchars = new StringBuilder();
        allchars.append(n);

        boolean noTerminate = true;

        while (noTerminate) {
            if (StdDraw.hasNextKeyTyped()) {
                char number = StdDraw.nextKeyTyped();
                System.out.println(number);
                if (number == 'S' || number == 's') {
                    noTerminate = false;
                }
                allchars.append(number);
            }

        }
        String input = allchars.toString();
//        System.out.println(input);

        return input;
    }


}
