import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import elements.*;
//har gooneh copy bardari as in code shar'an haram ast :)
public class Main {
    public static void main (String[] arg){
        //Reading File Section
        String input;
        HashMap<String, Element> elements = new HashMap<>();
        ArrayList <Node> nodes = new ArrayList<>();
        File file = new File("input.txt");
        try {
            Scanner scanner = new Scanner(file);
            ArrayList<String> nodeName = new ArrayList<>();
            while (scanner.hasNextLine()){
                input = scanner.next();
                if (input.startsWith("*")){

                }
                else {
                    String[] tokens = input.split("\\s+");
                    if (tokens.length == 4){

                    }
                    else if (tokens.length == 7){

                    }
                    else if (tokens.length == 2){

                    }
                    else
                        System.out.println("Not valid input! Check out the parameters");
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
