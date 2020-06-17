import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import elements.*;
//har gooneh copy bardari as in code shar'an haram ast :)
public class Main {
    public static void main (String[] arg){
        //Reading File Section
        boolean isEnded = false;
        String input;
        HashMap<String, Element> elements = new HashMap<>();
        ArrayList <Node> nodes = new ArrayList<>();
        File file = new File("input.txt");
        double dv;
        double dt;
        double di;
        try {
            Scanner scanner = new Scanner(file);
            ArrayList<String> nodeName = new ArrayList<>();
            int lineNumber = 0;
            Pattern number = Pattern.compile("\\d+");
            Pattern suffix = Pattern.compile("\\w");
            while (scanner.hasNextLine() && !isEnded){
                lineNumber ++;
                input = scanner.next();
                if (!input.startsWith("*")){
                    int i;
                    String[] tokens = input.split("\\s+");
                    //managing whether nodes are created before or not
                    if (tokens.length > 2) {
                        Node positiveTerminal = null;
                        Node negativeTerminal = null;
                        String name = tokens[0];
                        String positiveTerminalName = tokens[1];
                        String negativeTerminalName = tokens[2];
                        for (i = 0; i < nodes.size(); i++) {
                            if (nodes.get(i).getName().equals(positiveTerminalName))
                                positiveTerminal = nodes.get(i);
                            else if (nodes.get(i).getName().equals(negativeTerminalName))
                                negativeTerminal = nodes.get(i);
                        }
                        if (positiveTerminal == null) {
                            positiveTerminal = new Node(positiveTerminalName);
                            nodes.add(positiveTerminal);
                        }
                        if (negativeTerminal == null) {
                            negativeTerminal = new Node(negativeTerminalName);
                            nodes.add(negativeTerminal);
                        }
                        //end of the node part
                        if (tokens.length == 4) {
                            Matcher numberMatcher = number.matcher(tokens[3]);
                            Matcher suffixMatcher = suffix.matcher(tokens[3]);
                            double value = 0;
                            if (numberMatcher.find()) {
                                value = Double.parseDouble(numberMatcher.group());
                            }
                            String suffixTemp = null;
                            if (suffixMatcher.find()) {
                                suffixTemp = suffixMatcher.group();
                            }
                            if (value > 0 && suffixTemp != null) {
                                if (suffixTemp.equals("p")) {
                                    value *= Math.pow(10, -12);
                                } else if (suffixTemp.equals("n")) {
                                    value *= Math.pow(10, -9);
                                } else if (suffixTemp.equals("u")) {
                                    value *= Math.pow(10, -6);
                                } else if (suffixTemp.equals("m")) {
                                    value *= Math.pow(10, -3);
                                } else if (suffixTemp.equals("k")) {
                                    value *= Math.pow(10, +3);
                                } else if (suffixTemp.equals("M")) {
                                    value *= Math.pow(10, +6);
                                } else if (suffixTemp.equals("G")) {
                                    value *= Math.pow(10, +9);
                                } else {
                                    isEnded = true;
                                    System.out.println("Not valid suffix! check line" + lineNumber + "!");
                                }
                            } else if (value <= 0) {
                                isEnded = true;
                                System.out.println("Not valid value! check line" + lineNumber + "!");

                            }
                            if (tokens[0].startsWith("R") || tokens[0].startsWith("r")) {
                                Resistor resistor;
                                if (!isEnded) {
                                    resistor = new Resistor(name, positiveTerminal, negativeTerminal, value);
                                    elements.put(resistor.getName(), resistor);
                                }
                            } else if (tokens[0].startsWith("L") || tokens[0].startsWith("l")) {
                                Inductor inductor;
                                if (!isEnded) {
                                    inductor = new Inductor(name, positiveTerminal, negativeTerminal, value);
                                    elements.put(inductor.getName(), inductor);
                                }
                            } else if (tokens[0].startsWith("C") || tokens[0].startsWith("c")) {
                                Capacitor capacitor;
                                if (!isEnded) {
                                    capacitor = new Capacitor(name, positiveTerminal, negativeTerminal, value);
                                    elements.put(capacitor.getName(), capacitor);
                                }
                            }
                        } else if (tokens.length == 7) {

                        }
                        else if (tokens.length == 6){

                        }
                        else if (tokens.length == 5){

                        }
                    }
                    else if (tokens.length == 2){

                    }
                    else{
                        System.out.println("Not valid input! Check out line" + lineNumber);
                        isEnded = true;
                    }
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
