/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import java.util.Random;
/**
 *
 * @author jzeng
 */
abstract public class LinearClassifier {
    
    public double[] weights;
    
    public LinearClassifier(double[] weights) 
    {
        this.weights = weights;
    }
    
    public LinearClassifier(int ninputs)
    {
        this.weights = new double[ninputs];
    }
    
    /**
     * update the weights of this linear classifier using the given
     * inputs/output example and learning rate alpha
     */
    abstract public void update(double[] x, double y, double alpha);
    
    /**
     * threshold the given value using this linear classifier's
     * threshold function
     */
    abstract public double threshold(double z);
    
    /**
     * Evaluate given input vector using this linear classifier
     * and return the output value
     * value is: threshold(w \cdot x)
     */
    public double eval(double[] x)
    {
        return threshold(VectorOps.dot1(this.weights, x));
    }
    
    /**
     * train this linear classifier on the given examples for the
     * given number of steps, using given learning rate schedule
     * "typically the learning rule is applied one example at a time,
     * choosing examples at random (as in stochastic gradient descent)."
     */
    public void train(List<Example> examples, int nsteps, LearningRateSchedule schedule)
    {
        Random r = new Random();
        int n = examples.size();
        for(int i = 1; i <= nsteps; i++)
        {
            int j = r.nextInt(n);
            Example ex = examples.get(j);
            this.update(ex.inputs, ex.output, schedule.alpha(i));
            this.trainingReport(examples, i, nsteps);
        }
    }
    
    /**
     * train this linear classifier on the given examples for the
     * given number of steps, using given constant learning rate
     */
    public void train(List<Example> examples, int nsteps, double constant_alpha)
    {
        train(examples, nsteps, new LearningRateSchedule() {
           public double alpha(int t) { return constant_alpha; } 
        });
    }
    
    /**
     * this method is called after each weight update during training
     * subclasses can override it to gather statistics or update displays
     */
    protected void trainingReport(List<Example> examples, int stepnum, int nsteps)
    {
        System.out.println(stepnum + "\t " + accuracy(examples));
    }
    
    /**
     * return the squared error per example (mean squared error) for this
     * linear classifier on the given examples
     * the mean squared error is the total L_2 loss divided by num of samples
     */
    public double squaredErrorPerSample(List<Example> examples)
    {
        double sum = 0.0;
        for(Example ex: examples)
        {
            double result = eval(ex.inputs);
            double error = ex.output - result;
            sum += error*error;
        }
        return sum / examples.size();
    }
    
    /**
     * return the proprtion of the given examples that are classified correctly
     * by this linear classifier
     * this is probably only meaningful for classifiers that use hard threshold
     * use w care
     */
    public double accuracy(List<Example> examples)
    {
        int ncorrect = 0;
        for(Example ex: examples)
        {
            double result = eval(ex.inputs);
            //System.out.println("result: " + result);
            //System.out.println("output: " + ex.output);
            if(result == ex.output)
            {
                ncorrect++;
            }
        }
        
        return (double) (ncorrect) / (double) (examples.size());
    }
}
