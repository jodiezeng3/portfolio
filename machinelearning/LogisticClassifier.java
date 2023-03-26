/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;

/**
 *
 * @author jzeng
 */
public class LogisticClassifier extends LinearClassifier{
    
    public LogisticClassifier(double[] weights)
    {
        super(weights);
    }
    
    public LogisticClassifier(int ninputs)
    {
        super(ninputs);
    }
    
    /**
     * a logistic classifier uses the logistic update rule
     * (AIMA eq 18.8): w_i \leftarrow w_i+\alpha(y-h_w(x)) \times h_w(x)(1-h_w(x)) \times x_i
     */
    public void update(double[] x, double y, double alpha)
    {
        //FIXME
        double h_w = eval(x);
        //System.out.println("*** h_w: " + h_w);
        //System.out.println("    y: " + y);
        //System.out.println("    alpha: " + alpha);
        
        for(int i = 0; i < weights.length; i++)
        {
            double w_i = weights[i];
            double x_i = (i==0) ? 1 : x[i-1];
            
            //System.out.println("    x: " + x_i);
            //System.out.println("    w_" + i + ": " + weights[i]);
            weights[i] = w_i + (alpha * (y - h_w)) * (h_w * (1 - h_w)) * x_i;
            //System.out.println("    w2: " + weights[i]);
        }
    }
    
    /**
     * a logistic classifier uses a 0/1 sigmoid threshold at z = 0
     */
    public double threshold(double z)
    {
        //System.out.println("z: " + z);
        double logisitic = 1 / (1 + (Math.pow(Math.E, -z)));
        //System.out.println("log: " + logisitic);
        return logisitic;
    }
    
    @Override
    public double accuracy(List<Example> examples)
    {
        int ncorrect = 0;
        for(Example ex: examples)
        {
            double result = (eval(ex.inputs) > 0.5) ? 1.0 : 0.0;
            
            if(result == ex.output)
                ncorrect++;
        }
        
        return (double) (ncorrect) / (double) (examples.size());
    }
    
}
