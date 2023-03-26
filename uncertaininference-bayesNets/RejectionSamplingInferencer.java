/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jzeng
 */
public class RejectionSamplingInferencer {
    
    public Distribution query(NamedVariable X, Assignment e, BayesianNetwork bn)
    {
        return rejectionSampling(X, e, bn, 10000);
    }
    
    public Distribution query(NamedVariable X, Assignment e, BayesianNetwork bn, int n)
    {
        return rejectionSampling(X, e, bn, n);
    }
    
    private Distribution rejectionSampling(NamedVariable X, Assignment e, BayesianNetwork bn, int n)
    {
        /*
        for(j = 1 to n) do
         x = prior-sample(bn)
         if x is consistent with e then
            
        */
        Assignment x;
        Distribution d = new Distribution(X);
        
        for(int i = 0; i < n; i++)
        {
            x = priorSample(bn);
            if(this.isConsistent(x, e))
            {
                Value v = x.get(X);

                if(d.containsKey(v))
                    d.set(v, d.get(v) + (1.0/n));
                else
                    d.set(v, (1.0/n));
            }
        }
        
        d.normalize();
        return d;
    }
    
    public Assignment priorSample(BayesianNetwork bn)
    {
        /*
            sample each variable in topological order
            choose value for that var conditioned on the values already chosen for its parents
        */
        
        List<NamedVariable> variables = bn.getVariablesSortedTopologically();
        Assignment a = new Assignment();
        Random r = new Random();
        double d;
        NamedVariable v;
        ValueDomain domain;
        
        for(int i = 0; i < variables.size(); i++)
        {
            d = r.nextDouble();
            v = variables.get(i);
            domain = v.getDomain();
            double sum = 0.0;
            Iterator domainIterator = domain.iterator();
            boolean found = false;
            Value val = null;
            
            while(domainIterator.hasNext() && !found)
            {
                val = (Value) domainIterator.next();
                a.put(v, val);
                double prob = bn.getProbability(v, a);
                sum += prob;
                
                if(sum >= d)
                {
                    found = true;
                    break;
                }
                
                a.remove(v);
            }  
            
            if(!found)
                a.put(v, val);
        }
        
        return a;
    }
    
    public boolean isConsistent(Assignment x, Assignment e)
    {
        return x.containsAll(e);
    }
}
