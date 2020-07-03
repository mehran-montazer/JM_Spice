package elements;



import java.util.ArrayList;
import java.util.HashMap;

public class solver {
//lets solve this mother fucker!!!
    ArrayList<Element> elements = new ArrayList<>();
    ArrayList<Node> nodes = new ArrayList<>();
    ArrayList <Union> unions = new ArrayList<>();
    double dt,dv,di;
    public solver(ArrayList<Element> elements, ArrayList<Node> nodes, ArrayList<Union> unions, double dt, double dv,double di) {
        this.elements = elements;
        this.nodes = nodes;
        this.unions = unions;
        this.dt = dt;
        this.dv = dv;
        this.di = di;
    }
//    public  void updaate_elemnts(){
//        for(int i=0 ; i <elements.size() ; i ++){
//            elements.get(i).update_element(dt,dv);
//        }
//    }
    public void  update_nodes() {
        int cnt = 0;
        for (Union union : unions){
            union.setVisited(false);
        }
        for (Node n: this.nodes) {
            if (!n.hasVoltageSource()) {
                for (Element e : this.elements) {
                    if (e.positiveTerminal.getNameNumber() == cnt && e.type!='v') {
                        n.I_n += e.I;
                        n.I_p += e.I_p;
                    } else if (e.negativeTerminal.getNameNumber() == cnt && e.type!='v') {
                        n.I_n -= e.I;
                        n.I_p -= e.I_p;
                    }
                    n.V_p = n.V;
                    n.V += dv * ((n.I_n - n.I_p) / di) * dv;
                    if (cnt == 0) {
                        n.V = 0;
                        n.V_p = 0;
                    }
                    cnt += 1;
                }
            }
            else {
                boolean visited = false;
                boolean is_gnd_included = false;
                double I_n=0,I_p=0;
                ArrayList<Node> present = new ArrayList<>();
                Node Main_present = null;
                int number = n.getUnion();
                for (Union union : unions){
                    if (union.getNumber()==number){
                        visited = union.isVisited();
                    }
                }
                if (!visited) {
                    for (Union union : unions) {
                        if (union.getNumber() == number) {
                            present = union.getNodes();
                            Main_present = union.getMainNode();
                            union.setVisited(true);
                            I_n = union.I_n;
                            I_p=union.I_p;
                        }
                    }
                    for (Element e : this.elements) {
                        if (Main_present.getNameNumber() != 0) {
                            if (e.positiveTerminal.getNameNumber() == Main_present.getNameNumber() && e.type != 'v') {
                                I_n += e.I;
                                I_p += e.I_p;
                            } else if (e.negativeTerminal.getNameNumber() == Main_present.getNameNumber() && e.type != 'v') {
                                I_n -= e.I;
                                I_p -= e.I_p;
                            }
//                            n.V_p = n.V;
//                            n.V += dv * ((n.I_n - n.I_p) / di) * dv;
                            Main_present.V_p = Main_present.V;
                            Main_present.V += dv * ((Main_present.I_n - Main_present.I_p) / di) * dv;
                        }
                        else {
                            is_gnd_included = true;
                            Main_present.V=0;
                            break;
                        }

                    }
                    for (Node r: present) {
                        if (!is_gnd_included) {
                            for (Element e : this.elements) {
                                if (e.positiveTerminal.getNameNumber() == r.getNameNumber() && e.type != 'v') {
                                    I_n += e.I;
                                    I_p += e.I_p;
                                } else if (e.negativeTerminal.getNameNumber() == r.getNameNumber() && e.type != 'v') {
                                    I_n -= e.I;
                                    I_p -= e.I_p;
                                }
                                r.V_p = r.V;
                                r.updateVoltage();
                            }
                        }
                        else {
                            r.V_p = r.V;
                            r.updateVoltage();
                        }
                    }
                    for (Union union : unions){
                        if (union.getNumber()==number){
                            union.I_n = I_n;
                            union.I_p= I_p;
                        }
                    }
                }
                cnt++;
            }
        }
        while (!is_over()){
            cnt = 0;
            for (Union union : unions){
                union.setVisited(false);
            }
            for (Node n: this.nodes) {
                if (!n.hasVoltageSource()) {
                    for (Element e : this.elements) {
                        if (e.positiveTerminal.getNameNumber() == cnt && e.type!='v') {
                            n.I_n += e.I;
                            n.I_p += e.I_p;
                        } else if (e.negativeTerminal.getNameNumber() == cnt && e.type!='v') {
                            n.I_n -= e.I;
                            n.I_p -= e.I_p;
                        }
                        n.V_p = n.V;
                        n.V += dv * ((n.I_n - n.I_p) / di) * dv;
                        if (cnt == 0) {
                            n.V = 0;
                            n.V_p = 0;
                        }
                        cnt += 1;
                    }
                }
                else {
                    boolean visited = false;
                    boolean is_gnd_included = false;
                    int I_n=0,I_p=0;
                    ArrayList<Node> present = new ArrayList<>();
                    Node Main_present = null;
                    int number = n.getUnion();
                    for (Union union : unions){
                        if (union.getNumber()==number){
                            visited = union.isVisited();
                        }
                    }
                    if (!visited) {
                        for (Union union : unions) {
                            if (union.getNumber() == number) {
                                present = union.getNodes();
                                Main_present = union.getMainNode();
                                union.setVisited(true);
                                I_n = union.I_n;
                                I_p=union.I_p;
                            }
                        }
                        for (Element e : this.elements) {
                            if (Main_present.getNameNumber() != 0) {
                                if (e.positiveTerminal.getNameNumber() == Main_present.getNameNumber() && e.type != 'v') {
                                    I_n += e.I;
                                    I_p += e.I_p;
                                } else if (e.negativeTerminal.getNameNumber() == Main_present.getNameNumber() && e.type != 'v') {
                                    I_n -= e.I;
                                    I_p -= e.I_p;
                                }
//                            n.V_p = n.V;
//                            n.V += dv * ((n.I_n - n.I_p) / di) * dv;
                                Main_present.V_p = Main_present.V;
                                Main_present.V += dv * ((Main_present.I_n - Main_present.I_p) / di) * dv;
                            }
                            else {
                                is_gnd_included = true;
                                Main_present.V=0;
                                break;
                            }

                        }
                        for (Node r: present) {
                            if (!is_gnd_included) {
                                for (Element e : this.elements) {
                                    if (e.positiveTerminal.getNameNumber() == r.getNameNumber() && e.type != 'v') {
                                        I_n += e.I;
                                        I_p += e.I_p;
                                    } else if (e.negativeTerminal.getNameNumber() == r.getNameNumber() && e.type != 'v') {
                                        I_n -= e.I;
                                        I_p -= e.I_p;
                                    }
                                    r.V_p = r.V;
                                    r.updateVoltage();
                                }
                            }
                            else {
                                r.V_p = r.V;
                                r.updateVoltage();
                            }
                        }
                        for (Union union : unions){
                            if (union.getNumber()==number){
                                union.I_n = I_n;
                                union.I_p= I_p;
                            }
                        }
                    }
                    cnt++;
                }
            }
        }
    }
    public boolean is_over(){
        boolean right=true;
        for (Union n:this.unions){
            if (n.I_n > 0.01){
                right = false;
            }
        }
        return right;
    }
}
