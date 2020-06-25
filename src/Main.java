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
        ArrayList <String> dependentSources = new ArrayList<>();
        ArrayList <VoltageSource> voltageSources = new ArrayList<>();
        ArrayList <Union> unions = new ArrayList<>();
        File file = new File("input.txt");
        double dv;
        double dt;
        double di;
        String[] tokens;
        try {
            Scanner scanner = new Scanner(file);
            ArrayList<String> nodeName = new ArrayList<>();
            int lineNumber = 0;
            Pattern number = Pattern.compile("\\d+");
            Pattern suffix = Pattern.compile("\\D");
            while (scanner.hasNextLine() && !isEnded){
                lineNumber ++;
                input = scanner.nextLine();
                if (!input.startsWith("*")){
                    int i;
                    tokens = input.split("\\s+");
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
                        //neighbors
                        if (!positiveTerminal.getSaf().contains(negativeTerminal))
                            positiveTerminal.adder(negativeTerminal);
                        if (!negativeTerminal.getSaf().contains(positiveTerminal))
                            negativeTerminal.adder(positiveTerminal);
                        //end of the node part
                        if (tokens.length == 4) {
                            Matcher numberMatcher = number.matcher(tokens[3]);
                            Matcher suffixMatcher = suffix.matcher(tokens[3]);
                            double value = valueCalculator(numberMatcher, suffixMatcher);
                            if (value == -1){
                                isEnded = true;
                                System.out.println("check line" + lineNumber + "!");
                            }
                            System.out.println(value);
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
                            if (tokens[4].equals("0") && tokens[5].equals("0") && tokens[6].equals("0")){
                                Matcher numberMatcher = number.matcher(tokens[3]);
                                Matcher suffixMatcher = suffix.matcher(tokens[3]);
                                double value = valueCalculator(numberMatcher, suffixMatcher);
                                if (value == -1){
                                    isEnded = true;
                                    System.out.println("check line" + lineNumber + "!");
                                }
                                if ( ( tokens[0].startsWith("v") || tokens[0].startsWith("V") ) && !isEnded){
                                    VoltageSource voltageSource = new VoltageSource(name, positiveTerminal, negativeTerminal, value);
                                    elements.put(voltageSource.getName(), voltageSource);
                                    voltageSources.add(voltageSource);
                                }
                                else if ( ( tokens[0].startsWith("i") || tokens[0].startsWith("I") ) && !isEnded){
                                    CurrentSource currentSource = new CurrentSource(name, positiveTerminal, negativeTerminal, value);
                                    elements.put(currentSource.getName(), currentSource);
                                }
                                else if (!isEnded){
                                    isEnded = true;
                                    System.out.println("Not valid input! Check line" + lineNumber);
                                }
                            }
                        }
                        else if (tokens.length == 6 || tokens.length == 5){
                            String temp = input + " " + lineNumber;
                            dependentSources.add(temp);
                        }
                    }
                    else if (tokens.length == 2){
                        Matcher numberMatcher = number.matcher(tokens[1]);
                        Matcher suffixMatcher = suffix.matcher(tokens[1]);
                        double value = valueCalculator(numberMatcher, suffixMatcher);
                        if (value == -1){
                            isEnded = true;
                            System.out.println("check line" + lineNumber + "!");
                        }
                        if (tokens[0].equals("dv") || tokens[0].equals("dV")){
                            dv = value;
                        }
                        else if (tokens[0].equals("di") || tokens[0].equals("dI")){
                            di = value;
                        }
                        else if (tokens[0].equals("dt") || tokens[0].equals("dT")){
                            dt = value;
                        }
                        else {
                            isEnded = true;
                            System.out.println("Not valid input! Check line" + lineNumber + "!");
                        }
                    }
                    else{
                        System.out.println("Not valid input! Check out line" + lineNumber);
                        isEnded = true;
                    }
                }
            }
            if (!dependentSources.isEmpty()){
                for (int i = 0; i < dependentSources.size(); i++){
                    input = dependentSources.get(i);
                    tokens = input.split("\\s+");
                    lineNumber = Integer.parseInt(tokens[tokens.length - 1]);
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
                    //neighbors
                    if (!positiveTerminal.getSaf().contains(negativeTerminal))
                        positiveTerminal.adder(negativeTerminal);
                    if (!negativeTerminal.getSaf().contains(positiveTerminal))
                        negativeTerminal.adder(positiveTerminal);
                    if (tokens.length == 7){
                        Node positiveDependentNode = null;
                        Node negativeDependentNode = null;
                        String positiveDependentTerminalName = tokens[3];
                        String negativeDependentTerminalName = tokens[4];
                        for (i = 0; i < nodes.size(); i++) {
                            if (nodes.get(i).getName().equals(positiveDependentTerminalName))
                                positiveDependentNode = nodes.get(i);
                            else if (nodes.get(i).getName().equals(negativeDependentTerminalName))
                                negativeDependentNode = nodes.get(i);
                        }
                        if (positiveDependentNode == null || negativeDependentNode == null){
                            isEnded = true;
                            System.out.println("Dependent nodes problem" + lineNumber);
                        }
                        else {
                            Matcher numberMatcher = number.matcher(tokens[5]);
                            Matcher suffixMatcher = suffix.matcher(tokens[5]);
                            double value = valueCalculator(numberMatcher, suffixMatcher);
                            if (value == -1){
                                isEnded = true;
                                System.out.println("check line" + lineNumber + "!");
                            }
                            if (name.startsWith("g") || name.startsWith("G")){
                                CurrentSource currentSource;
                                currentSource = new CurrentSource(name, positiveTerminal, negativeTerminal, positiveDependentNode, negativeDependentNode, value);
                                elements.put(currentSource.getName(), currentSource);
                            }
                            else if (name.startsWith("e") || name.startsWith("E")){
                                VoltageSource voltageSource;
                                voltageSource = new VoltageSource(name, positiveTerminal, negativeTerminal, positiveDependentNode, negativeDependentNode, value);
                                elements.put(voltageSource.getName(), voltageSource);
                                voltageSources.add(voltageSource);
                            }
                            else{
                                isEnded = true;
                                System.out.println(lineNumber);
                            }
                        }
                    }
                    else if (tokens.length == 6){
                        String majorElementName = tokens[3];
                        if (elements.containsKey(majorElementName)){
                            Element majorElement;
                            majorElement = elements.get(majorElementName);
                            Matcher numberMatcher = number.matcher(tokens[4]);
                            Matcher suffixMatcher = suffix.matcher(tokens[4]);
                            double value = valueCalculator(numberMatcher, suffixMatcher);
                            if (value == -1){
                                isEnded = true;
                                System.out.println("check line" + lineNumber + "!");
                            }
                            if (name.startsWith("f") || name.startsWith("F")){
                                CurrentSource currentSource;
                                currentSource = new CurrentSource(name, positiveTerminal, negativeTerminal, majorElement, value);
                                elements.put(currentSource.getName(), currentSource);
                            }
                            else if (name.startsWith("h") || name.startsWith("H")){
                                VoltageSource voltageSource;
                                voltageSource = new VoltageSource(name, positiveTerminal, negativeTerminal, majorElement, value);
                                elements.put(voltageSource.getName(), voltageSource);
                                voltageSources.add(voltageSource);
                            }
                            else {
                                isEnded = true;
                                System.out.println(lineNumber);
                            }
                        }
                        else {
                            isEnded = true;
                            System.out.println("check line" + lineNumber);
                        }
                    }
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        //setting unions
        for (Node node : nodes) {
            if (node.getName().equals("gnd") || node.getName().equals("0")) {
                node.setUnion(0);
            }
        }
        int tempU = 1;
        for (VoltageSource voltageSource : voltageSources) {
            if (voltageSource.getPositiveTerminal().getUnion() == -1 && voltageSource.getNegativeTerminal().getUnion() == -1) {
                voltageSource.getPositiveTerminal().setUnion(tempU);
                voltageSource.getNegativeTerminal().setUnion(tempU);
                tempU++;
            } else if (voltageSource.getPositiveTerminal().getUnion() != -1 && voltageSource.getNegativeTerminal().getUnion() == -1)
                voltageSource.getNegativeTerminal().setUnion(voltageSource.getPositiveTerminal().getUnion());
            else if (voltageSource.getPositiveTerminal().getUnion() == -1 && voltageSource.getNegativeTerminal().getUnion() != -1)
                voltageSource.getPositiveTerminal().setUnion(voltageSource.getNegativeTerminal().getUnion());
        }
        for (Node node : nodes) {
            if (node.getUnion() == -1) {
                node.setUnion(tempU);
                tempU++;
            }
        }
        //end of setting unions
        for (int i = 0; i < tempU; i++){
            Union union = new Union(i);
            for (Node node : nodes) {
                if (node.getUnion() == i)
                    union.addNode(node);
            }
            unions.add(union);
        }
    }
    static double valueCalculator(Matcher numberMatcher, Matcher suffixMatcher){
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
                return -1;
            }
        }
        else if (value <= 0) {
            return -1;
        }
        return value;
    }
}
