package elements;

import java.util.ArrayList;
import java.util.HashMap;

public class solver {
//lets solve this mother fucker!!!
    HashMap<String, Element> elements = new HashMap<>();
    ArrayList<Node> nodes = new ArrayList<>();
    ArrayList <Union> unions = new ArrayList<>();
    double dt,dv;
    public solver(HashMap<String, Element> elements, ArrayList<Node> nodes, ArrayList<Union> unions, double dt, double dv) {
        this.elements = elements;
        this.nodes = nodes;
        this.unions = unions;
        this.dt = dt;
        this.dv = dv;
    }
    public  void updaate_elemnts(){
        for(int i=0 ; i <elements.size() ; i ++){
            elements.get(i).update_element(dt,dv);
        }
    }
    public void  update_nodes() {
        int cnt = 0;
        for (Node n: this.nodes) {
            n.I_n = 0;
            n.I_p = 0;
            for (Element e : this.elements) {
                if (e.positiveTerminal == cnt) {
                    n.I_n += e.I;
                    n.I_p += e.I_p;
                }
                else if (e.negativeTerminal == cnt) {
                    n.I_n -= e.I
                    n.I_p -= e.I_p
                }
                n.V_p = n.V;
                n.I = (n.I_n + n.I_p) / 2;
            #if abs(n.I) < 1:
            #pass
#elif abs (n.I_p) < abs(n.I_n):
            #n.V += self.dv
#elif abs (n.I_p) > abs(n.I_n):
            #n.V -= self.dv
                n.V -= self.dv * n.I_n
                if cnt == 0:
                n.V = 0
                n.V_p = 0
                cnt += 1


            }
            n.I_n = 0
            n.I_p = 0
            for e in self.elements:
            if e.In == cnt:
            n.I_n += e.I
            n.I_p += e.I_p
            elif e.Out == cnt:
            n.I_n -= e.I
            n.I_p -= e.I_p

            n.V_p = n.V
            n.I = (n.I_n + n.I_p) / 2
            #if abs(n.I) < 1:
            #pass
#elif abs (n.I_p) < abs(n.I_n):
            #n.V += self.dv
#elif abs (n.I_p) > abs(n.I_n):
            #n.V -= self.dv
            n.V -= self.dv * n.I_n
            if cnt == 0:
            n.V = 0
            n.V_p = 0
            cnt += 1

        }
    }
}
