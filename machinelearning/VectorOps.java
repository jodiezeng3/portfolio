/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jzeng
 */
public class VectorOps {
    
    /**
     * x \cdot y = sum \sum_i x[i] * y[i]
     * @param x vector x
     * @param y vector y
     * @return dot-product of x and y
     */
    static public double dot(double[] x, double[] y)
    {
        double sum = 0.0;
        for(int i = 0; i < x.length; i++)
            sum += x[i] * y[i];
        return sum;
    }
    
    /**
     * For vector w of length n and vector x of length n-1.
     * Easier to do this in code than to constantly allocate
     * and copy new vectors for the "extended" x vector.
     * @param w vector of weights
     * @param x vector x
     * @return w \cdot [1,x]
     */
    static public double dot1(double[] w, double[] x)
    {
        double sum = w[0];
        for(int i = 1; i < w.length; i++)
            sum += w[i] * x[i-1];
        return sum;
    }
}
