package elements;



import handmadeExceptions.Minus2Exception;
import handmadeExceptions.Minus3Exception;
import handmadeExceptions.Minus4Exception;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Solver {
//lets solve this mother fucker!!!
    public static Solver solver;
    ArrayList<Element> elements;
    ArrayList<Node> nodes;
    ArrayList <Union> unions;
    ArrayList<moshakhassat> moshakhassats;
    double dt,dv,di,t;
    public Solver(ArrayList<Element> elements, ArrayList<Node> nodes, ArrayList<Union> unions, double dt, double dv,double di,double t) {
        this.elements = elements;
        this.nodes = nodes;
        this.unions = unions;
        this.dt = dt;
        this.dv = dv;
        this.di = di;
        this.t = t;
        Solver.solver= this;
    }

    public static Solver getSolver() {
        return solver;
    }

    public  ArrayList<Element> getElements() {
        return elements;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
    public ArrayList<Union> getUnions() {
        return unions;
    }

    //    public  void updaate_elemnts(){
//        for(int i=0 ; i <elements.size() ; i ++){
//            elements.get(i).update_element(dt,dv);
//        }i kari n
//    }
    public void  update_nodes() throws Minus2Exception, Minus3Exception, Minus4Exception {
        int count =0, kk =0 ;
        for (double zaman=0; zaman < t ;zaman += dt) {
            for (Union union:unions){
                union.checkKCL(zaman);
                union.checkKVL(zaman);
            }
            count ++;
            int cnt = 0;
            for (Union union : unions) {
                union.setVisited(false);
            }
            for (Node n : this.nodes) {
                n.I_n = 0;
                n.I_p = 0;
                if (!n.hasVoltageSource()) {
                    if (cnt != 0) {
                        for (Element e : this.elements) {
                            if (e.positiveTerminal.getNameNumber() == cnt && e.type != 'v') {
                                if (e.type == 'i') {
                                    CurrentSource u  = (CurrentSource) e ;
                                    if (u.isAC()){
                                        u.calculateCurrent(zaman);
                                    }
                                    else {
                                        u.calculateCurrent();
                                    }
                                    n.I_n += u.current;
                                    n.I_p += u.current;
                                } else if (e.type == 'r') {
                                    n.I_n += e.calculateCurrentR();
                                    n.I_p += e.calculateCurrentRplus();
                                } else if (e.type == 'l') {
                                    n.I_n += e.calculateCurrentL();
                                    n.I_p += e.calculateCurrentLplus();
                                } else if (e.type == 'c') {
                                    n.I_n += e.calculateCurrentC();
                                    n.I_p += e.calculateCurrentCplus();
                                }
                            } else if (e.negativeTerminal.getNameNumber() == cnt && e.type != 'v') {
                                if (e.type == 'i') {
                                    CurrentSource u  = (CurrentSource) e ;
                                    if (u.isAC()){
                                        u.calculateCurrent(zaman);
                                    }
                                    else {
                                        u.calculateCurrent();
                                    }
                                    n.I_n -= u.current;
                                    n.I_p -= u.current;
                                } else if (e.type == 'r') {
                                    n.I_n -= e.calculateCurrentR();
                                    n.I_p -= e.calculateCurrentRminus();
                                } else if (e.type == 'l') {
                                    n.I_n -= e.calculateCurrentL();
                                    n.I_p -= e.calculateCurrentLminus();
                                } else if (e.type == 'c') {
                                    n.I_n -= e.calculateCurrentC();
                                    n.I_p -= e.calculateCurrentCminus();
                                }
                            }
                        }
                        for (Element element : elements){
                            if (element.type == 'l'){
                                element.setstepcurrent(element.calculateCurrentL());
                            }
                        }
                        n.V_Step = n.V;
                        double ww = ((Math.abs(n.I_n) - Math.abs(n.I_p)) / di) * dv;
//                            while (Math.abs(ww) > 5)
//                                ww /= 2;
                        n.V += ww;

                    }
                    cnt += 1;
                } else {
                    double jaryani;
                    boolean visited = false;
                    boolean is_gnd_included = false;
                    double I_n = 0, I_p = 0;
                    ArrayList<Node> present = new ArrayList<>();
                    Node Main_present = null;
                    int number = n.getUnion();
                    int gereshomare_union=0;
                    for (Union union : unions) {
                        if (union.getNumber() == number) {
                            visited = union.isVisited();
                        }
                    }
                    if (!visited) {
                        for (Union union : unions) {
                            if (union.getNumber() == number) {
                                present = union.getNodes();
                                Main_present = union.getMainNode();
                                gereshomare_union = Main_present.getNameNumber();
                                union.setVisited(true);
                                I_n = 0;
                                I_p = 0;
                            }
                        }
//                        for (Node r : present){
//                            r.V_Step = r.V;
//                        }
                        for (Element e : elements){
                            if (e.type == 'r') {
                                 e.calculateCurrentR();
                            } else if (e.type == 'l') {
                                 e.calculateCurrentL();
                            } else if (e.type == 'c') {
                                 e.calculateCurrentC();
                            }
                        }
                        for (Union union : unions){
                            if (union.getNumber() == number ){
                                union.updateVoltages(zaman);
                                break ;
                            }
                        }
                        if (Main_present.getNameNumber() != 0) {
                            for (Element e : this.elements) {
                                if (e.positiveTerminal.getNameNumber() == cnt && e.type != 'v') {
                                    if (e.type == 'i') {
                                        CurrentSource u  = (CurrentSource) e ;
                                        if (u.isAC()){
                                            u.calculateCurrent(zaman);
                                        }
                                        else {
                                            u.calculateCurrent();
                                        }
                                        I_n += u.current;
                                        I_p += u.current;
                                    } else if (e.type == 'r') {
                                        I_n += e.calculateCurrentR();
                                        I_p += e.calculateCurrentRplus();
                                    } else if (e.type == 'l') {
                                        I_n += e.calculateCurrentL();
                                        I_p += e.calculateCurrentLplus();
                                        e.setstepcurrent(e.calculateCurrentL());
                                    } else if (e.type == 'c') {
                                        I_n += e.calculateCurrentC();
                                        I_p += e.calculateCurrentCplus();
                                    }
                                } else if (e.negativeTerminal.getNameNumber() == cnt && e.type != 'v') {
                                    if (e.type == 'i') {
                                        CurrentSource u  = (CurrentSource) e ;
                                        if (u.isAC()){
                                            u.calculateCurrent(zaman);
                                        }
                                        else {
                                            u.calculateCurrent();
                                        }
                                        I_n -= u.current;
                                        I_p -= u.current;
                                    } else if (e.type == 'r') {
                                        I_n -= e.calculateCurrentR();
                                        I_p -= e.calculateCurrentRminus();
                                    } else if (e.type == 'l') {
                                        I_n -= e.calculateCurrentL();
                                        I_p -= e.calculateCurrentLminus();
                                        e.setstepcurrent(e.calculateCurrentL());
                                    } else if (e.type == 'c') {
                                        I_n -= e.calculateCurrentC();
                                        I_p -= e.calculateCurrentCminus();
                                    }
                                }
                            }
                        } else {
                            is_gnd_included = true;
                        }
                        for (Node r : present) {
                            while (gereshomare_union != r.getNameNumber()){
                                gereshomare_union ++;
                            }
                            if (!is_gnd_included) {
                                for (Element e : this.elements) {
                                    if (e.positiveTerminal.getNameNumber() == gereshomare_union && e.type != 'v') {
                                        if (e.type == 'i') {
                                            CurrentSource u  = (CurrentSource) e ;
                                            if (u.isAC()){
                                                u.calculateCurrent(zaman);
                                            }
                                            else {
                                                u.calculateCurrent();
                                            }
                                            I_n += u.current;
                                            I_p += u.current;
                                        } else if (e.type == 'r') {
                                            I_n += e.calculateCurrentR();
                                            I_p += e.calculateCurrentRplus();
                                        } else if (e.type == 'l') {
                                            I_n += e.calculateCurrentL();
                                            I_p += e.calculateCurrentLplus();
                                            e.setstepcurrent(e.calculateCurrentL());
                                        } else if (e.type == 'c') {
                                            I_n += e.calculateCurrentC();
                                            I_p += e.calculateCurrentCplus();
                                        }
                                    } else if (e.negativeTerminal.getNameNumber() == gereshomare_union && e.type != 'v') {
                                        if (e.type == 'i') {
                                            CurrentSource u  = (CurrentSource) e ;
                                            if (u.isAC()){
                                                u.calculateCurrent(zaman);
                                            }
                                            else {
                                                u.calculateCurrent();
                                            }
                                            I_n -= u.current;
                                            I_p -= u.current;
                                        } else if (e.type == 'r') {
                                            I_n -= e.calculateCurrentR();
                                            I_p -= e.calculateCurrentRminus();
                                        } else if (e.type == 'l') {
                                            I_n -= e.calculateCurrentL();
                                            I_p -= e.calculateCurrentLminus();
                                            e.setstepcurrent(e.calculateCurrentL());
                                        } else if (e.type == 'c') {
                                            I_n -= e.calculateCurrentC();
                                            I_p -= e.calculateCurrentCminus();
                                        }
                                    }
                                }
                            }
                            else {

                            }
                        }
                        for (Union union : unions) {
                            if (union.getNumber() == number) {
                                for (Node r : present){
                                    r.V_Step = r.V;
                                }
                                double ww = ((Math.abs(I_n) - Math.abs(I_p)) / di) * dv;
//                                while (Math.abs(ww) > 5)
//                                    ww /= 2;
                                union.getMainNode().V_Step = union.getMainNode().getV();
                                union.getMainNode().V += ww;
                                union.setI_n(I_n);
                                union.setI_p(I_p);
                            }
                        }
                    }
                    cnt++;
                }
            }
            if (count == 100000){
                kk ++;
            }
            for (Element element : elements){
                double voltage = element.positiveTerminal.getV() - element.negativeTerminal.getV();
                double power =0 ;
                moshakhassat moshakhassat = null;
                if (element.type != 'v') {
                    if (element.type == 'i'){
                        CurrentSource u  = (CurrentSource) element ;
                        if (u.isAC()){
                            u.calculateCurrent(zaman);
                        }
                        else {
                            u.calculateCurrent();
                        }
                        power = (u.current)*(u.positiveTerminal.getV() - u.negativeTerminal.getV());
                        moshakhassat = new moshakhassat(voltage,u.current,power,zaman);
                    }
                    else {
                        if (element.type == 'c'){
                            power = voltage * element.current;
                            moshakhassat = new moshakhassat(voltage,element.current,power,zaman);
                        }
                        else if (element.type == 'l'){
                            power = voltage * element.I_n;
                            moshakhassat = new moshakhassat(voltage,element.I_n,power,zaman);
                        }
                        else if (element.type == 'r'){
                            power = voltage * element.calculateCurrentR();
                            moshakhassat = new moshakhassat(voltage,element.calculateCurrentR(),power,zaman);
                        }
                    }
                }
                else {
                    moshakhassat = get_branch_current(element, zaman);
                }
                element.moshakhassats.add(moshakhassat);
            }
            for (Node node: nodes){
                moshakhassat moshakhassat = new moshakhassat(node.getV(),zaman);
                node.moshakhassats.add(moshakhassat);
            }
//            count ++;
//            if (count % 5000 == 0) {
//                System.out.println("t = " + zaman + "\t");
//                for (Node node : nodes) {
//                    System.out.println(node.getName() + ":" + "\t" + node.getV());
//                }
//            }
        }
    }
    public moshakhassat get_branch_current(Element q,double zaman){
        int cnt = q.positiveTerminal.getNameNumber();
        double voltage = q.positiveTerminal.getV() - q.negativeTerminal.getV();
        double I_n=0,I_p=0;
        double jaryan=0 ;
        for (Element e : this.elements) {
            if (e.positiveTerminal.getNameNumber() == cnt && e.type != 'v') {
                if (e.type == 'i') {
                    CurrentSource u  = (CurrentSource) e ;
                    if (u.isAC()){
                        u.calculateCurrent(zaman);
                    }
                    else {
                        u.calculateCurrent();
                    }
                    I_n += u.current;

                } else if (e.type == 'r') {
                    I_n += e.calculateCurrentR();

                } else if (e.type == 'l') {
                    I_n += e.calculateCurrentL();

                } else if (e.type == 'c') {
                    I_n += e.calculateCurrentC();

                }
            } else if (e.negativeTerminal.getNameNumber() == cnt && e.type != 'v') {
                if (e.type == 'i') {
                    CurrentSource u  = (CurrentSource) e ;
                    if (u.isAC()){
                        u.calculateCurrent(zaman);
                    }
                    else {
                        u.calculateCurrent();
                    }
                    I_n -= u.current;
                } else if (e.type == 'r') {
                    I_n -= e.calculateCurrentR();
                } else if (e.type == 'l') {
                    I_n -= e.calculateCurrentL();
                } else if (e.type == 'c') {
                    I_n -= e.calculateCurrentC();
                }
            }
        }
        return new moshakhassat(voltage,-1*I_n,(-1 * I_n) * voltage,zaman);
    }
    public void print_console() throws IOException {
        File file = new File("test/output.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        int o=-1;
        bw.write("-------------Voltage of nodes in time steps : ----------------");
        bw.newLine();
        for (Node node : nodes){
                bw.write("Node" + node.getNameNumber() + ": ");
                for (int i = 0; i < node.moshakhassats.size(); i++) {
                    o ++;
                    if (o % 1000 == 0 ) {
                        String string = String.format("t =%.6f ,V =  %.6f , ", node.moshakhassats.get(i).t,node.moshakhassats.get(i).voltage);
                        bw.write(string);
                    }
                }
                bw.newLine();
                bw.write("----------------------------------------------");
                bw.newLine();
        }
        o =-1;
        bw.write("-----------------information of elements----------------");
        bw.newLine();
        for (Element element : elements){
                bw.write(element.name + ":  ");
                for (int i = 0; i < element.moshakhassats.size(); i++) {
                    o++;
                    if (o % 1000 == 0) {
                        String string = String.format("t = %.3f  ,V=%.6f , I=%.6f , P=%.6f , ", element.moshakhassats.get(i).t,element.moshakhassats.get(i).voltage, element.moshakhassats.get(i).current, element.moshakhassats.get(i).power);
                        bw.write(string);
                    }
                }
                bw.newLine();
                bw.write("---------------------------------------------");
                bw.newLine();
        }
        bw.close();
    }
}
