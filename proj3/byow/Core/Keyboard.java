package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class Keyboard {

    public String solicitInput(){
        if(StdDraw.hasNextKeyTyped() == true){
            char letter = StdDraw.nextKeyTyped();
            if(letter == 'N' || letter == 'n'){
                getSeed();
            } else if (letter == 'L' || letter == 'l'){
                //load method

            } else if (letter == ':'){
                char checkQ = StdDraw.nextKeyTyped();
                if( checkQ == 'Q' || checkQ == 'q'){
                    //quit and save method
                }

            }

        }

    }


    public String getSeed() {
        //TODO: Read n letters of player input

        StringBuilder allchars = new StringBuilder();


//        while(){
//            if(StdDraw.hasNextKeyTyped() == true){
//                char letter = StdDraw.nextKeyTyped();
//                if(letter == 'N' || letter == 'n'){
//
//                }
//
//                //allchars.append(letter);
//            }
//        }
//        String input = allchars.toString();

        return input;
    }



}
