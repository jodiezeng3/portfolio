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
public class AIMAAlarmNetwork extends BayesianNetwork{
    
    public AIMAAlarmNetwork()
    {
        super();
        
        NamedVariable B = new BooleanVariable("B");
        NamedVariable E = new BooleanVariable("E");
        NamedVariable A = new BooleanVariable("A");
        NamedVariable J = new BooleanVariable("J");
        NamedVariable M = new BooleanVariable("M");
        this.add(B);
        this.add(E);
        this.add(A);
        this.add(J);
        this.add(M);
        
        BooleanValue TRUE = BooleanValue.TRUE;
        BooleanValue FALSE = BooleanValue.FALSE;
        Assignment a;
        
        //B
        CPT Bprior = new CPT(B);
        a = new Assignment();
        Bprior.set(TRUE, a, 0.001);
        Bprior.set(FALSE, a, 1-0.001);
        this.connect(B, new ArraySet<NamedVariable>(), Bprior);
        
        //E
        CPT Eprior = new CPT(E);
        a = new Assignment();
        Eprior.set(TRUE, a, 0.002);
        Eprior.set(FALSE, a, 1-0.002);
        this.connect(E, new ArraySet<NamedVariable>(), Eprior);
        
        //B,E -> A
        Set<NamedVariable> BE = new ArraySet<NamedVariable>();
        BE.add(B);
        BE.add(E);
        CPT AgivenBE = new CPT(A);
        a = new Assignment();
        a.put(B, TRUE);
        a.put(E, TRUE);
        AgivenBE.set(TRUE, a, 0.95);
        AgivenBE.set(FALSE, a, 1-0.95);
        a = new Assignment();
        a.put(B, TRUE);
        a.put(E, FALSE);
        AgivenBE.set(TRUE, a, 0.94);
        AgivenBE.set(FALSE, a, 1-0.94);
        a = new Assignment();
        a.put(B, FALSE);
        a.put(E, TRUE);
        AgivenBE.set(TRUE, a, 0.29);
        AgivenBE.set(FALSE, a, 1-0.29);
        a = new Assignment();
        a.put(B, FALSE);
        a.put(E, FALSE);
        AgivenBE.set(TRUE, a, 0.001);
        AgivenBE.set(FALSE, a, 1-0.001);
        this.connect(A, BE, AgivenBE);
        
        //A -> J
        Set<NamedVariable> justA = new ArraySet<NamedVariable>();
        justA.add(A);
        CPT JgivenA = new CPT(J);
        a = new Assignment();
        a.put(A, TRUE);
        JgivenA.set(TRUE, a, 0.9);
        JgivenA.set(FALSE, a, 1-0.9);
        a = new Assignment();
        a.put(A, FALSE);
        JgivenA.set(TRUE, a, 0.05);
        JgivenA.set(FALSE, a, 1-0.05);
        this.connect(J, justA, JgivenA);
        
        //A -> M
        CPT MgivenA = new CPT(M);
        a = new Assignment();
        a.put(A, TRUE);
        MgivenA.set(TRUE, a, 0.7);
        MgivenA.set(FALSE, a, 1-0.7);
        a = new Assignment();
        a.put(A, FALSE);
        MgivenA.set(TRUE, a, 0.01);
        MgivenA.set(FALSE, a, 1-0.01);
        this.connect(M, justA, MgivenA);
    }
    
    public static void main(String[] args) {
        AIMAAlarmNetwork bn = new AIMAAlarmNetwork();
        System.out.println(bn);
        
        System.out.println("P(B|j,m) = \\alpha <0.00059224,0.0014919> ~= <0.284,0.716>");
        EnumerationInferencer exact = new EnumerationInferencer();
        Assignment e = new Assignment();
        NamedVariable B = bn.getVariableByName("B");
        NamedVariable J = bn.getVariableByName("J");
        NamedVariable M = bn.getVariableByName("M");
        e.put(J, BooleanValue.TRUE);
        e.put(M, BooleanValue.TRUE);
        Distribution dist = exact.query(B, e, bn);
        System.out.println(dist);
        
        RejectionSamplingInferencer rs = new RejectionSamplingInferencer();
        Distribution dist2 = rs.query(B, e, bn, 20000);
        System.out.println("*** rejection sampling");
        System.out.println(dist2);
        
        LikelihoodWeightingInferencer lw = new LikelihoodWeightingInferencer();
        Distribution dist3 = lw.query(B, e, bn, 30000);
        System.out.println("*** likelihood weighting");
        System.out.println(dist3);
	}
}
