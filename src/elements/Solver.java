package elements;



import java.util.ArrayList;
import java.util.HashMap;

public class Solver {
//lets solve this mother fucker!!!
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
                                while (Math.abs(ww) > 5)
                                    ww /= 2;
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
                        power = (-1*u.current)*(u.positiveTerminal.getV() - u.negativeTerminal.getV());
                        moshakhassat = new moshakhassat(voltage,u.current,power);
                    }
                    else {
                        power = voltage * element.current;
                        moshakhassat = new moshakhassat(voltage,element.current,power);
                    }
                }
                else {
                    moshakhassat = get_branch_current(element, zaman);
                }
                element.moshakhassats.add(moshakhassat);
            }
            for (Node node: nodes){
                moshakhassat moshakhassat = new moshakhassat(node.getV());
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
        return new moshakhassat(voltage,-1*I_n,(-1 * I_n) * voltage);
    }
    public void print_console(){
        int o=0;
        System.out.println("-------------Voltage of nodes in time steps : ----------------");
        for (Node node : nodes){
                System.out.print("Node" + node.getNameNumber() + ": ");
                for (int i = 0; i < node.moshakhassats.size(); i++) {
                    o ++;
                    if (o % 10000 == 0 ) {
                        System.out.printf(" ,%.6f", node.moshakhassats.get(i).voltage);
                    }
                }
                System.out.println(" ");
                System.out.println("----------------------------------------------");
        }
        o =0;
        System.out.println("-----------------information of elements----------------");
        for (Element element : elements){
                System.out.printf(element.name + ":  ");
                for (int i = 0; i < element.moshakhassats.size(); i++) {
                    o++;
                    if (o % 10000 == 0) {
                        System.out.printf("V=%.6f , I=%.6f , P=%.6f  ", element.moshakhassats.get(i).voltage, element.moshakhassats.get(i).current, element.moshakhassats.get(i).power);
                    }
                }
                System.out.println(" ");
                System.out.println("---------------------------------------------");
        }
    }
}
