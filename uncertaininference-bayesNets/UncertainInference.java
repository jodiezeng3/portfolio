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
public class UncertainInference {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException{
        String filename;
        NamedVariable X;
        ArrayList<NamedVariable> evidence_vars = new ArrayList<NamedVariable>();
        ArrayList<Value> evidence_vals = new ArrayList<Value>();
        
        XMLBIFParser parser = new XMLBIFParser();
        
        filename = args[0];

        BayesianNetwork bn = parser.readNetworkFromFile(filename);
        X = bn.getVariableByName(args[1]);

        for(int i = 2; i < args.length; i += 2)
        {
            evidence_vars.add(bn.getVariableByName(args[i]));
            evidence_vals.add(new StringValue(args[i+1]));
        }
        
        Assignment e = new Assignment();
        
        for(int i = 0; i < evidence_vars.size(); i++)
        {
            e.put(evidence_vars.get(i), evidence_vals.get(i));
        }
        
        EnumerationInferencer exact = new EnumerationInferencer();
        Distribution dist = exact.query(X, e, bn);
        System.out.println(dist);
    }
    
}
