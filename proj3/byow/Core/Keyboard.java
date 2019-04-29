package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class Keyboard {

    public static void sollicitInput() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char letter = StdDraw.nextKeyTyped();
                if(letter == 'N' || letter == 'n'){
                    System.out.println("new game ");

                } else if (letter == 'L' || letter == 'l'){
                    System.out.println("load game");
                    //load method

                } else if (letter == ':') {
                    System.out.println("gotta figure out how to quit");
                    while (true){
                        if (StdDraw.hasNextKeyTyped()) {
                            char checkQ = StdDraw.nextKeyTyped();
                            if (checkQ == 'Q' || checkQ == 'q') {
                                //quit and save method
                                System.out.println("really quit now");
                            }
                        }
                    }

                }
            }

        }
    }


//    public String getSeed() {
//        //TODO: Read n letters of player input
//
//        StringBuilder allchars = new StringBuilder();
//
//
////        while(){
////            if(StdDraw.hasNextKeyTyped() == true){
////                char letter = StdDraw.nextKeyTyped();
////                if(letter == 'N' || letter == 'n'){
////
////                }
////
////                //allchars.append(letter);
////            }
////        }
////        String input = allchars.toString();
//
//        return input;
//    }



}
