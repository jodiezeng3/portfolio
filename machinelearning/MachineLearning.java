/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author jzeng
 */
public class MachineLearning {

    /**
     * args[0] - file name
     * args[1] - classifier type
     * args[2] - alpha type
     * args[3] - starting weights
     * args[4] - step #
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Example> examples = new ArrayList<Example>();
        FileReader r;
        BufferedReader reader;
        ArrayList<String> lines = new ArrayList();
        Scanner s;
        try
        {
            r = new FileReader(args[0]);
            reader = new BufferedReader(r);
            String line;
            boolean done = false;
            
            while(!done)
            {
                line = reader.readLine();
                
                if(line == null)
                    done = true;
                else
                    lines.add(line);
            }           
            
            for(int i = 0; i < lines.size(); i++)
            {
                s = new Scanner(lines.get(i)).useDelimiter(",");
                ArrayList d = new ArrayList<Double>();
                
                while(s.hasNext())
                {
                    d.add(Double.parseDouble(s.next()));
                }
                
                double[] inputs = new double[d.size() - 1];
                
                for(int k = 0; k < d.size() - 1; k++)
                    inputs[k] = (double) d.get(k);
                
                double output = (double) d.get(d.size() - 1);
                
                examples.add(new Example(inputs, output));
            }
            
            
            double[] weights = new double[examples.get(0).inputs.length + 1];
            Arrays.fill(weights, Double.parseDouble(args[3]));
            LinearClassifier lc;
            
            if(args[1].equals("perceptron"))
                lc = new PerceptronClassifier(weights);
            else if(args[1].equals("logistic"))
                lc = new LogisticClassifier(weights);
            else
                lc = new PerceptronClassifier(weights);
            
            LearningRateSchedule rate;
            
            if(args[2].equals("decay"))
                rate = new DecayingLearningRateSchedule();
            else
            {
                double alpha = Double.parseDouble(args[2]);
                rate = new LearningRateSchedule(){
                  public double alpha(int t)
                  {
                      return alpha;
                  }
                };
            }

            lc.train(examples, Integer.parseInt(args[4]), rate);
            for(int i = 0; i < weights.length; i++)
                System.out.println("w_" + i + ": " + weights[i]);
        }
        catch(IOException e)
        {
            System.out.println("Error Processing File: " + e);
        }
    }
    
}
