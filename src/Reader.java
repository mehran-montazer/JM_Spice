import elements.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {
    private boolean isEnded = false;
    private ArrayList<Element> elements = new ArrayList<>();
    private HashMap<String, Element> elementHashMap = new HashMap<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList <Union> unions = new ArrayList<>();
    private double dv;
    private double dt;
    private double di;
    private double t;
    private Scanner scanner;
    ///////////////////////////////////constructor/////////////////////////////////
    Reader (File file) throws FileNotFoundException {
        scanner = new Scanner(file);
    }
    /////////////////////////////////////getter////////////////////////////////////
    public ArrayList<Node> getNodes() {
        return nodes;
    }
    public ArrayList<Union> getUnions() {
        return unions;
    }
    public ArrayList<Element> getElements() {
        return elements;
    }
    public double getDi() {
        return di;
    }
    public double getDv() {
        return dv;
    }
    public double getDt() {
        return dt;
    }
    public double getT() {
        return t;
    }
    ///////////////////////////////////////////////////////////////////////////
    public void read() {
        ArrayList <VoltageSource> voltageSources = new ArrayList<>();
        String[] tokens;
        String input;
        ArrayList <String> dependentSources = new ArrayList<>();
        int lineNumber = 0;
        Pattern number = Pattern.compile("\\d+(.\\d+)*");
        Pattern suffix = Pattern.compile("\\D");
        while (scanner.hasNextLine() && !isEnded) {
            lineNumber++;
            input = scanner.nextLine();
            if (!input.startsWith("*")) {
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
                        if (value == -1) {
                            isEnded = true;
                            System.out.println("check line" + lineNumber + "!");
                        }
                        if (tokens[0].startsWith("R") || tokens[0].startsWith("r")) {
                            Resistor resistor;
                            if (!isEnded) {
                                resistor = new Resistor(name, positiveTerminal, negativeTerminal, value);
                                elementHashMap.put(resistor.getName(), resistor);
                                elements.add(resistor);
                            }
                        } else if (tokens[0].startsWith("L") || tokens[0].startsWith("l")) {
                            Inductor inductor;
                            if (!isEnded) {
                                inductor = new Inductor(name, positiveTerminal, negativeTerminal, value);
                                elementHashMap.put(inductor.getName(), inductor);
                                elements.add(inductor);
                            }
                        } else if (tokens[0].startsWith("C") || tokens[0].startsWith("c")) {
                            Capacitor capacitor;
                            if (!isEnded) {
                                capacitor = new Capacitor(name, positiveTerminal, negativeTerminal, value);
                                elementHashMap.put(capacitor.getName(), capacitor);
                                elements.add(capacitor);
                            }
                        }
                        else if ((tokens[0].startsWith("d") || tokens[0].startsWith("D")) && (value == 1)){
                            Diode diode;
                            diode = new Diode(name, negativeTerminal, positiveTerminal);
                            elementHashMap.put(name, diode);
                            elements.add(diode);
                        }
                    } else if (tokens.length == 7) {
                        if (tokens[4].equals("0") && tokens[5].equals("0") && tokens[6].equals("0")) {
                            Matcher numberMatcher = number.matcher(tokens[3]);
                            Matcher suffixMatcher = suffix.matcher(tokens[3]);
                            double value = valueCalculator(numberMatcher, suffixMatcher);
                            if (value == -1) {
                                isEnded = true;
                                System.out.println("check line" + lineNumber + "!");
                            }
                            if ((tokens[0].startsWith("v") || tokens[0].startsWith("V")) && !isEnded) {
                                VoltageSource voltageSource = new VoltageSource(name, positiveTerminal, negativeTerminal, value);
                                elementHashMap.put(voltageSource.getName(), voltageSource);
                                voltageSources.add(voltageSource);
                                elements.add(voltageSource);
                                positiveTerminal.addVoltageSource(voltageSource);
                                negativeTerminal.addVoltageSource(voltageSource);
                            } else if ((tokens[0].startsWith("i") || tokens[0].startsWith("I")) && !isEnded) {
                                CurrentSource currentSource = new CurrentSource(name, positiveTerminal, negativeTerminal, value);
                                elementHashMap.put(currentSource.getName(), currentSource);
                                elements.add(currentSource);
                            } else if (!isEnded) {
                                isEnded = true;
                                System.out.println("Not valid input! Check line" + lineNumber);
                            }
                        }
                        else {
                            Matcher numberMatcher = number.matcher(tokens[3]);
                            Matcher suffixMatcher = suffix.matcher(tokens[3]);
                            double offSet = valueCalculator(numberMatcher, suffixMatcher);
                            if (offSet == -1) {
                                isEnded = true;
                                System.out.println("check line" + lineNumber + "!");
                            }
                            numberMatcher = number.matcher(tokens[4]);
                            suffixMatcher = suffix.matcher(tokens[4]);
                            double amp = valueCalculator(numberMatcher, suffixMatcher);
                            if (amp == -1) {
                                isEnded = true;
                                System.out.println("check line" + lineNumber + "!");
                            }
                            numberMatcher = number.matcher(tokens[5]);
                            suffixMatcher = suffix.matcher(tokens[5]);
                            double freq = valueCalculator(numberMatcher, suffixMatcher);
                            if (freq == -1) {
                                isEnded = true;
                                System.out.println("check line" + lineNumber + "!");
                            }
                            numberMatcher = number.matcher(tokens[6]);
                            suffixMatcher = suffix.matcher(tokens[6]);
                            double phase = valueCalculator(numberMatcher, suffixMatcher);
                            if (phase == -1) {
                                isEnded = true;
                                System.out.println("check line" + lineNumber + "!");
                            }
                            if ((tokens[0].startsWith("v") || tokens[0].startsWith("V")) && !isEnded) {
                                VoltageSource voltageSource = new VoltageSource(name, positiveTerminal, negativeTerminal, offSet, amp, freq, phase);
                                elementHashMap.put(voltageSource.getName(), voltageSource);
                                voltageSources.add(voltageSource);
                                elements.add(voltageSource);
                                positiveTerminal.addVoltageSource(voltageSource);
                                negativeTerminal.addVoltageSource(voltageSource);
                            } else if ((tokens[0].startsWith("i") || tokens[0].startsWith("I")) && !isEnded) {
                                CurrentSource currentSource = new CurrentSource(name, positiveTerminal, negativeTerminal, offSet, amp, freq, phase);
                                elementHashMap.put(currentSource.getName(), currentSource);
                                elements.add(currentSource);
                            } else if (!isEnded) {
                                isEnded = true;
                                System.out.println("Not valid input! Check line" + lineNumber);
                            }
                        }
                    } else if (tokens.length == 6 || tokens.length == 5) {
                        String temp = input + " " + lineNumber;
                        dependentSources.add(temp);
                    }
                } else if (tokens.length == 2) {
                    Matcher numberMatcher = number.matcher(tokens[1]);
                    Matcher suffixMatcher = suffix.matcher(tokens[1]);
                    double value = valueCalculator(numberMatcher, suffixMatcher);
                    if (value == -1) {
                        isEnded = true;
                        System.out.println("check line" + lineNumber + "!");
                    }
                    if (tokens[0].equals("dv") || tokens[0].equals("dV")) {
                        dv = value;
                    } else if (tokens[0].equals("di") || tokens[0].equals("dI")) {
                        di = value;
                    } else if (tokens[0].equals("dt") || tokens[0].equals("dT")) {
                        dt = value;
                    }
                    else if (tokens[0].equals(".tran")){
                        t = value;
                        isEnded = true;
                    }
                    else {
                        isEnded = true;
                        System.out.println("Not valid input! Check line" + lineNumber + "!");
                    }
                } else {
                    System.out.println("Not valid input! Check out line" + lineNumber);
                    isEnded = true;
                }
            }
        }
        if (!dependentSources.isEmpty()) {
            for (int i = 0; i < dependentSources.size(); i++) {
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
                if (tokens.length == 7) {
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
                    if (positiveDependentNode == null || negativeDependentNode == null) {
                        isEnded = true;
                        System.out.println("Dependent nodes problem" + lineNumber);
                    } else {
                        Matcher numberMatcher = number.matcher(tokens[5]);
                        Matcher suffixMatcher = suffix.matcher(tokens[5]);
                        double value = valueCalculator(numberMatcher, suffixMatcher);
                        if (value == -1) {
                            isEnded = true;
                            System.out.println("check line" + lineNumber + "!");
                        }
                        if (name.startsWith("g") || name.startsWith("G")) {
                            CurrentSource currentSource;
                            currentSource = new CurrentSource(name, positiveTerminal, negativeTerminal, positiveDependentNode, negativeDependentNode, value);
                            elementHashMap.put(currentSource.getName(), currentSource);
                            elements.add(currentSource);
                        } else if (name.startsWith("e") || name.startsWith("E")) {
                            VoltageSource voltageSource;
                            voltageSource = new VoltageSource(name, positiveTerminal, negativeTerminal, positiveDependentNode, negativeDependentNode, value);
                            elementHashMap.put(voltageSource.getName(), voltageSource);
                            elements.add(voltageSource);
                            voltageSources.add(voltageSource);
                            positiveTerminal.addVoltageSource(voltageSource);
                            negativeTerminal.addVoltageSource(voltageSource);
                        } else {
                            isEnded = true;
                            System.out.println(lineNumber);
                        }
                    }
                } else if (tokens.length == 6) {
                    String majorElementName = tokens[3];
                    if (elementHashMap.containsKey(majorElementName)) {
                        Element majorElement;
                        majorElement = elementHashMap.get(majorElementName);
                        Matcher numberMatcher = number.matcher(tokens[4]);
                        Matcher suffixMatcher = suffix.matcher(tokens[4]);
                        double value = valueCalculator(numberMatcher, suffixMatcher);
                        if (value == -1) {
                            isEnded = true;
                            System.out.println("check line" + lineNumber + "!");
                        }
                        if (name.startsWith("f") || name.startsWith("F")) {
                            CurrentSource currentSource;
                            currentSource = new CurrentSource(name, positiveTerminal, negativeTerminal, majorElement, value);
                            elementHashMap.put(currentSource.getName(), currentSource);
                            elements.add(currentSource);
                        } else if (name.startsWith("h") || name.startsWith("H")) {
                            VoltageSource voltageSource;
                            voltageSource = new VoltageSource(name, positiveTerminal, negativeTerminal, majorElement, value);
                            elementHashMap.put(voltageSource.getName(), voltageSource);
                            voltageSources.add(voltageSource);
                            positiveTerminal.addVoltageSource(voltageSource);
                            negativeTerminal.addVoltageSource(voltageSource);
                        } else {
                            isEnded = true;
                            System.out.println(lineNumber);
                        }
                    } else {
                        isEnded = true;
                        System.out.println("check line" + lineNumber);
                    }
                }
            }
        }
        Collections.sort(nodes);
        //setting unions
        boolean isSetProperly = false;
        while (!isSetProperly){
            isSetProperly = true;
            for (VoltageSource voltageSource : voltageSources) {
                if (voltageSource.getPositiveTerminal().getUnion() != voltageSource.getNegativeTerminal().getUnion()) {
                    isSetProperly = false;
                    if (voltageSource.getNegativeTerminal().getNameNumber() < voltageSource.getPositiveTerminal().getNameNumber()) {
                        voltageSource.getPositiveTerminal().setUnion(voltageSource.getNegativeTerminal().getUnion());
                        voltageSource.getPositiveTerminal().setParentNode(voltageSource.getNegativeTerminal());
                        voltageSource.getPositiveTerminal().setConnector(voltageSource);
                        voltageSource.getPositiveTerminal().setConnectorNormal(true);
                    } else if (voltageSource.getNegativeTerminal().getNameNumber() > voltageSource.getPositiveTerminal().getNameNumber()) {
                        voltageSource.getNegativeTerminal().setUnion(voltageSource.getPositiveTerminal().getUnion());
                        voltageSource.getNegativeTerminal().setParentNode(voltageSource.getPositiveTerminal());
                        voltageSource.getNegativeTerminal().setConnector(voltageSource);
                        voltageSource.getNegativeTerminal().setConnectorNormal(false);
                    }
                }
            }
        }
        //end of setting unions
        Node mainNode = null;
        HashSet <Integer> unionNumbers = new HashSet();
        for (Node node : nodes){
            node.updateDependency();
            unionNumbers.add(node.getUnion());
        }
        for (int unionTemp : unionNumbers){
            Union union = new Union(unionTemp);
            for (Node node : nodes){
                if (node.getNameNumber() == unionTemp)
                    mainNode = node;
                else if (node.getUnion() == unionTemp)
                    union.addNode(node);
            }
            union.setMainNode(mainNode);
            unions.add(union);
        }
    }
    private double valueCalculator(Matcher numberMatcher, Matcher suffixMatcher){
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

