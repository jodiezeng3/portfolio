/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jzeng
 */
public class PerceptronClassifier extends LinearClassifier {
    
    public PerceptronClassifier(double[] weights)
    {
        super(weights);
    }
    
    public PerceptronClassifier(int ninputs)
    {
        super(ninputs);
    }
    
    /**
     * a perceptron classifier uses the perceptron learning rule
     * (AIMA eq 18.7): w_i \leftarrow w_i+\alpha(y-h_w(x)) \times x_i
     */
    public void update(double[] x, double y, double alpha)
    {
        double h_w = eval(x);    
        
            for(int i = 0; i < weights.length; i++)
            {
                double w_i = weights[i];
                double x_i = (i==0) ? 1 : x[i-1];

                weights[i] = w_i + (alpha * (y - h_w) * x_i); //update rule
            }
            
    }
    
    /**
     * a perceptron classifier uses a hard 0/1 threshold
     */
    public double threshold(double z)
    {
        //System.out.println("z: " + z);
        
        if(z >= 0)
            return 1;
        
        return 0.0;
    }
    
}
