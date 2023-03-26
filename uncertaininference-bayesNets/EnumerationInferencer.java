/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;

/**
 *
 * @author jzeng
 */
public class EnumerationInferencer {
    
    public Distribution query(NamedVariable X, Assignment e, BayesianNetwork bn)
    {
        return this.enumerationAsk(X, e, bn);
    }
    
    public Distribution enumerationAsk(NamedVariable X, Assignment e, BayesianNetwork bn)
    {
        /*
            Q(x) <- distribution over x, initially empty
            for each value xi of X do
                Q(xi) <- enumerateAll(bn.vars, exi)
                    where exi is e extended w/X = xi
            return normalized Q(X)
        */
        
        Distribution q = new Distribution(X);
        ValueDomain X_domain = X.getDomain();
        Iterator<Value> dIterate = X_domain.iterator();
        
        
        while(dIterate.hasNext())
        {
            Value xi = dIterate.next();
            e.put(X, xi);
            q.set(xi, enumerateAll(bn.getVariablesSortedTopologically(), e, bn));
            e.remove(X);
        }
        
        q.normalize();
        return q;
    }
    
    public double enumerateAll(List<NamedVariable> vars, Assignment e, BayesianNetwork bn)
    {
        if(vars.isEmpty()) return 1.0;
        NamedVariable Y = vars.get(0);
        
        if(e.containsKey(Y))
        {
            double posterior = bn.getProbability(Y, e);            
            double d =  posterior * enumerateAll(rest(vars, Y), e, bn);
            return d;
        }
             
        ValueDomain Y_domain = Y.getDomain();
        Iterator<Value> dIterate = Y_domain.iterator();
        double sum = 0.0;
        
        while(dIterate.hasNext())
        {
            Value yi = dIterate.next();
            e.put(Y, yi);
            sum += bn.getProbability(Y, e) * enumerateAll(rest(vars, Y), e, bn);
            e.remove(Y);
        }
        
        return sum;
    }
    
    private List<NamedVariable> rest(List<NamedVariable> vars, NamedVariable remove)
    {
        List<NamedVariable> rest = new ArrayList<NamedVariable>();
        
        for(NamedVariable v: vars)
        {
            if(!remove.getName().equals(v.getName()))
                rest.add(v);
        }
        
        return rest;
    }
    
}
