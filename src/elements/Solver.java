package elements;



import java.util.ArrayList;
import java.util.HashMap;

public class Solver {
//lets solve this mother fucker!!!
    ArrayList<Element> elements;
    ArrayList<Node> nodes;
    ArrayList <Union> unions;
    double dt,dv,di,t;
    public Solver(ArrayList<Element> elements, ArrayList<Node> nodes, ArrayList<Union> unions, double dt, double dv,double di,double t) {
        this.elements = elements;
        this.nodes = nodes;
        this.unions = unions;
        this.dt = dt;
        this.dv = dv;
        this.di = di;
        this.t = t;
    }
//    public  void updaate_elemnts(){
//        for(int i=0 ; i <elements.size() ; i ++){
//            elements.get(i).update_element(dt,dv);
//        }
//    }
    public void  update_nodes() {
        int count =0 ;
        for (double zaman=0; zaman < t ;zaman += dt) {
            int cnt = 0;
            for (Union union : unions) {
                union.setVisited(false);
            }
//        for (Element element:elements){
//            element.update_element(dt,dv);
//        }
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
                                    e.setstepcurrent(e.calculateCurrentL());
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
                                    e.setstepcurrent(e.calculateCurrentL());
                                } else if (e.type == 'c') {
                                    n.I_n -= e.calculateCurrentC();
                                    n.I_p -= e.calculateCurrentCminus();
                                }
                            }
                        }
                        n.V_Step = n.V;
                        double ww = ((Math.abs(n.I_n) - Math.abs(n.I_p)) / di) * dv;
                            while (Math.abs(ww) > 5)
                                ww /= 2;
                        n.V += ww;

                    }
                    cnt += 1;
                } else {
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
                            Main_present.V_Step = Main_present.V;
                            double ww = ((Math.abs(n.I_n) - Math.abs(n.I_p)) / di) * dv;
                            while (Math.abs(ww) > 5)
                                ww /= 2;
                            Main_present.V += ww;
                        } else {
                            is_gnd_included = true;
                        }
                        for (Node r : present) {
                            while (gereshomare_union != r.getNameNumber()){
                                gereshomare_union ++;
                            }
                            r.V_Step = r.V;
                            r.updateVoltage(zaman);
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
                            } else {
                                r.V_Step = r.V;
                                r.updateVoltage(zaman);
                            }
                        }
                        for (Union union : unions) {
                            if (union.getNumber() == number) {
                                union.setI_n(I_n);
                                union.setI_p(I_p);
                            }
                        }
                    }
                    cnt++;
                }
            }
//            while (!is_over()) {
//                cnt = 0;
//                for (Union union : unions) {
//                    union.setVisited(false);
//                }
////            for (Element element:elements){
////                element.update_element(dt,dv);
////            }
//                for (Node n : this.nodes) {
//                    n.I_n = 0;
//                    n.I_p = 0;
//                    if (!n.hasVoltageSource()) {
//                        if (cnt != 0) {
//                            for (Element e : this.elements) {
//                                if (e.positiveTerminal.getNameNumber() == cnt && e.type != 'v') {
//                                    if (e.type == 'i') {
//                                        n.I_n += e.current;
//                                        n.I_p += e.current;
//                                    } else if (e.type == 'r') {
//                                        n.I_n += e.calculateCurrentR();
//                                        n.I_p += e.calculateCurrentRplus();
//                                    } else if (e.type == 'l') {
//                                        n.I_n += e.calculateCurrentL();
//                                        n.I_p += e.calculateCurrentLplus();
//                                    } else if (e.type == 'c') {
//                                        n.I_n += e.calculateCurrentC();
//                                        n.I_p += e.calculateCurrentCplus();
//                                    }
//                                } else if (e.negativeTerminal.getNameNumber() == cnt && e.type != 'v') {
//                                    if (e.type == 'i') {
//                                        n.I_n -= e.current;
//                                        n.I_p -= e.current;
//                                    } else if (e.type == 'r') {
//                                        n.I_n -= e.calculateCurrentR();
//                                        n.I_p -= e.calculateCurrentRminus();
//                                    } else if (e.type == 'l') {
//                                        n.I_n -= e.calculateCurrentL();
//                                        n.I_p -= e.calculateCurrentLminus();
//                                    } else if (e.type == 'c') {
//                                        n.I_n -= e.calculateCurrentC();
//                                        n.I_p -= e.calculateCurrentCminus();
//                                    }
//                                }
//                            }
//                            n.V_p = n.V;
//                            n.V += ((Math.abs(n.I_n) - Math.abs(n.I_p)) / di) * dv;
//                        }
//                        cnt += 1;
//                    } else {
//                        boolean visited = false;
//                        boolean is_gnd_included = false;
//                        double I_n = 0, I_p = 0;
//                        ArrayList<Node> present = new ArrayList<>();
//                        Node Main_present = null;
//                        int number = n.getUnion();
//                        for (Union union : unions) {
//                            if (union.getNumber() == number) {
//                                visited = union.isVisited();
//                            }
//                        }
//                        if (!visited) {
//                            for (Union union : unions) {
//                                if (union.getNumber() == number) {
//                                    present = union.getNodes();
//                                    Main_present = union.getMainNode();
//                                    union.setVisited(true);
//                                    I_n = 0;
//                                    I_p = 0;
//                                }
//                            }
//                            if (Main_present.getNameNumber() != 0) {
//                                for (Element e : this.elements) {
//                                    if (e.positiveTerminal.getNameNumber() == cnt && e.type != 'v') {
//                                        if (e.type == 'i') {
//                                            n.I_n += e.current;
//                                            n.I_p += e.current;
//                                        } else if (e.type == 'r') {
//                                            n.I_n += e.calculateCurrentR();
//                                            n.I_p += e.calculateCurrentRplus();
//                                        } else if (e.type == 'l') {
//                                            n.I_n += e.calculateCurrentL();
//                                            n.I_p += e.calculateCurrentLplus();
//                                        } else if (e.type == 'c') {
//                                            n.I_n += e.calculateCurrentC();
//                                            n.I_p += e.calculateCurrentCplus();
//                                        }
//                                    } else if (e.negativeTerminal.getNameNumber() == cnt && e.type != 'v') {
//                                        if (e.type == 'i') {
//                                            n.I_n -= e.current;
//                                            n.I_p -= e.current;
//                                        } else if (e.type == 'r') {
//                                            n.I_n -= e.calculateCurrentR();
//                                            n.I_p -= e.calculateCurrentRminus();
//                                        } else if (e.type == 'l') {
//                                            n.I_n -= e.calculateCurrentL();
//                                            n.I_p -= e.calculateCurrentLminus();
//                                        } else if (e.type == 'c') {
//                                            n.I_n -= e.calculateCurrentC();
//                                            n.I_p -= e.calculateCurrentCminus();
//                                        }
//                                    }
//                                }
//                                Main_present.V_p = Main_present.V;
//                                Main_present.V += ((Math.abs(Main_present.I_n) - Math.abs(Main_present.I_p)) / di) * dv;
//                            } else {
//                                is_gnd_included = true;
//                            }
//                            for (Node r : present) {
//                                if (!is_gnd_included) {
//                                    for (Element e : this.elements) {
//                                        if (e.positiveTerminal.getNameNumber() == cnt && e.type != 'v') {
//                                            if (e.type == 'i') {
//                                                n.I_n += e.current;
//                                                n.I_p += e.current;
//                                            } else if (e.type == 'r') {
//                                                n.I_n += e.calculateCurrentR();
//                                                n.I_p += e.calculateCurrentRplus();
//                                            } else if (e.type == 'l') {
//                                                n.I_n += e.calculateCurrentL();
//                                                n.I_p += e.calculateCurrentLplus();
//                                            } else if (e.type == 'c') {
//                                                n.I_n += e.calculateCurrentC();
//                                                n.I_p += e.calculateCurrentCplus();
//                                            }
//                                        } else if (e.negativeTerminal.getNameNumber() == cnt && e.type != 'v') {
//                                            if (e.type == 'i') {
//                                                n.I_n -= e.current;
//                                                n.I_p -= e.current;
//                                            } else if (e.type == 'r') {
//                                                n.I_n -= e.calculateCurrentR();
//                                                n.I_p -= e.calculateCurrentRminus();
//                                            } else if (e.type == 'l') {
//                                                n.I_n -= e.calculateCurrentL();
//                                                n.I_p -= e.calculateCurrentLminus();
//                                            } else if (e.type == 'c') {
//                                                n.I_n -= e.calculateCurrentC();
//                                                n.I_p -= e.calculateCurrentCminus();
//                                            }
//                                        }
//                                    }
//                                    r.V_p = r.V;
//                                    r.updateVoltage(zaman);
//                                } else {
//                                    r.V_p = r.V;
//                                    r.updateVoltage(zaman);
//                                }
//                            }
//                            for (Union union : unions) {
//                                if (union.getNumber() == number) {
//                                    union.setI_n(I_n);
//                                    union.setI_p(I_p);
//                                }
//                            }
//                        }
//                        cnt++;
//                    }
//                }
//            }
//            for (Node node : nodes){
//                node.V_Step = node.V;
//            }
            count ++;
            if (count % 5000 == 0) {
                System.out.println("t = " + zaman + "\t");
                for (Node node : nodes) {
                    System.out.println(node.getName() + ":" + "\t" + node.getV());
                }
            }
        }
    }
    public boolean is_over(){
        boolean right=true;
//        for (Union n : this.unions){
//            double i =0;
//            ArrayList<Node> node = n.getNodes();
//            Node main = n.getMainNode();
//            for (Node e:node){
//                i += e.I_n;
//            }
//            i += main.I_n;
//            n.setI_n(i);
//        }
        for (Union n:this.unions){
            if (n.getMainNode().getNameNumber() != 0) {
                if (!n.getMainNode().hasVoltageSource()) {
                    if (Math.abs(n.getMainNode().I_n) >= 0.05) {
                        right = false;
                    }
                }
                else {
                    if (Math.abs(n.getI_n()) >= 0.05) {
                        right = false;
                    }
                }
            }
        }
        return right;
    }
}
