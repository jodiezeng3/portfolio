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
public class LikelihoodWeightingInferencer {
    
    
    public Distribution query(NamedVariable X, Assignment e, BayesianNetwork bn, int n)
    {
        return likelihoodWeighting(X, e, bn, n);
    }
    
    public Distribution query(NamedVariable X, Assignment e, BayesianNetwork bn)
    {
        return likelihoodWeighting(X, e, bn, 10000);
    }
    
    public Distribution likelihoodWeighting(NamedVariable X, Assignment e, BayesianNetwork bn, int n)
    {
        Distribution dist = new Distribution(X);
        Assignment a;
        List<NamedVariable> variables = bn.getVariablesSortedTopologically();
        
        for(int j = 0; j < n; j++)
        {
            
            Random r = new Random();
            double d;
            NamedVariable v;
            ValueDomain domain;
            double weight = 1.0;
            
            //prior sample
            do
            {
                a = new Assignment();
                
                for(int i = 0; i < variables.size(); i++)
                {
                    d = r.nextDouble();
                    v = variables.get(i);
                    domain = v.getDomain();
                    double sum = 0.0;
                    Iterator domainIterator = domain.iterator();
                    boolean found = false;
                    Value val = null;

                    if(e.containsKey(v))
                    {
                        a.put(v, e.get(v));
                        weight *= bn.getProbability(v, a);
                    }
                    else
                    {
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
                } 
            }
            while(!this.isConsistent(a, e));
            
            Value val = a.get(X);
            
            if(dist.containsKey(val))
                dist.set(val, dist.get(val) + ((1.0/n) * weight));
            else
                dist.set(val, (1.0/n)*weight);
        }
        
        dist.normalize();
        return dist;
    }
    
    public boolean isConsistent(Assignment x, Assignment e)
    {
        return x.containsAll(e);
    }
}
