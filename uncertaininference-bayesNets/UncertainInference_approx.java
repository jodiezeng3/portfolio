/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author jzeng
 */
public class UncertainInference_approx {
    
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException{
        String filename;
        NamedVariable X;
        int n;
        ArrayList<NamedVariable> evidence_vars = new ArrayList<NamedVariable>();
        ArrayList<Value> evidence_vals = new ArrayList<Value>();
        
        XMLBIFParser parser = new XMLBIFParser();
        
        filename = args[1];
        n = Integer.parseInt(args[0]);

        BayesianNetwork bn = parser.readNetworkFromFile(filename);
        X = bn.getVariableByName(args[2]);

        for(int i = 3; i < args.length; i += 2)
        {
            evidence_vars.add(bn.getVariableByName(args[i]));
            evidence_vals.add(new StringValue(args[i+1]));
        }
        
        Assignment e = new Assignment();
        
        for(int i = 0; i < evidence_vars.size(); i++)
        {
            e.put(evidence_vars.get(i), evidence_vals.get(i));
        }
        
        RejectionSamplingInferencer rs = new RejectionSamplingInferencer();
        Distribution dist1 = rs.query(X, e, bn, n);
        System.out.println("*** Rejection Sampling ***");
        System.out.println(dist1);
        
        LikelihoodWeightingInferencer lw = new LikelihoodWeightingInferencer();
        Distribution dist2 = lw.query(X, e, bn, n);
        System.out.println("*** Likelihood Weighting ***");
        System.out.println(dist2);
    }
}
