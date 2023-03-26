/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Set;
/**
 *
 * @author jzeng
 */
public class AIMAWetGrassNetwork extends BayesianNetwork{
    
    public AIMAWetGrassNetwork() {
        super();
        
        NamedVariable C = new NamedVariable("C", new BooleanDomain());
        NamedVariable S = new NamedVariable("S", new BooleanDomain());
        NamedVariable R = new NamedVariable("R", new BooleanDomain());
        NamedVariable W = new NamedVariable("W", new BooleanDomain());
        this.add(C);
        this.add(S);
        this.add(R);
        this.add(W);
        // Shorthands
        BooleanValue TRUE = BooleanValue.TRUE;
        BooleanValue FALSE = BooleanValue.FALSE;
        Assignment a;

        // C (no parents)
        CPT Bprior = new CPT(C);
        a = new Assignment();
        Bprior.set(TRUE, a, 0.5);
        Bprior.set(FALSE, a, 1-0.5);
        this.connect(C, new ArraySet<NamedVariable>() , Bprior);

        // C -> S
        Set<NamedVariable> justC = new ArraySet<NamedVariable>();
        justC.add(C);
        CPT SgivenC = new CPT(S);
        a = new Assignment();
        a.put(C, TRUE);
        SgivenC.set(TRUE, a, 0.1);
        SgivenC.set(FALSE, a, 1-0.1);
        a = new Assignment();
        a.put(C, FALSE);
        SgivenC.set(TRUE, a, 0.5);
        SgivenC.set(FALSE, a, 1-0.5);
        this.connect(S, justC, SgivenC);

        // C -> R
        justC.add(C);
        CPT RgivenC = new CPT(R);
        a = new Assignment();
        a.put(C, TRUE);
        RgivenC.set(TRUE, a, 0.8);
        RgivenC.set(FALSE, a, 1-0.8);
        a = new Assignment();
        a.put(C, FALSE);
        RgivenC.set(TRUE, a, 0.2);
        RgivenC.set(FALSE, a, 1-0.2);
        this.connect(R, justC, RgivenC);

        // S,R -> W
        Set<NamedVariable> SR = new ArraySet<NamedVariable>();
        SR.add(S);
        SR.add(R);
        CPT WgivenSR = new CPT(W);
        a = new Assignment();
        a.put(S, TRUE);
        a.put(R, TRUE);
        WgivenSR.set(TRUE, a, 0.99);
        WgivenSR.set(FALSE, a, 1-0.99);
        a = new Assignment();
        a.put(S, TRUE);
        a.put(R, FALSE);
        WgivenSR.set(TRUE, a, 0.90);
        WgivenSR.set(FALSE, a, 1-0.90);
        a = new Assignment();
        a.put(S, FALSE);
        a.put(R, TRUE);
        WgivenSR.set(TRUE, a, 0.90);
        WgivenSR.set(FALSE, a, 1-0.90);
        a = new Assignment();
        a.put(S, FALSE);
        a.put(R, FALSE);
        WgivenSR.set(TRUE, a, 0.0);
        WgivenSR.set(FALSE, a, 1-0.0);
        this.connect(W, SR, WgivenSR); 
    }
    
    public static void main(String[] args) {
        AIMAWetGrassNetwork bn = new AIMAWetGrassNetwork();
        System.out.println(bn);

        System.out.println("P(Rain|Sprinkler=true) = <0.3,0.7>");
        EnumerationInferencer exact = new EnumerationInferencer();
        Assignment e = new Assignment();
        NamedVariable R = bn.getVariableByName("R");
        NamedVariable S = bn.getVariableByName("S");
        e.put(S, BooleanValue.TRUE);
        Distribution dist = exact.query(R, e, bn);
        System.out.println(dist);
        
        LikelihoodWeightingInferencer lw = new LikelihoodWeightingInferencer();
        Distribution dist3 = lw.query(R, e, bn, 30000);
        System.out.println("*** likelihood weighting");
        System.out.println(dist3);
	}
}
