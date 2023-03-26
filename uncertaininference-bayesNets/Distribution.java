/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Map;
/**
 *
 * @author jzeng
 */
public class Distribution extends ArrayMap<Value,Double>{
    
    protected NamedVariable variable;
    
    public Distribution(NamedVariable X)
    {
        super(X.getDomain().size());
        this.variable = X;
    }
    
    public NamedVariable getVariable()
    {
        return this.variable;
    }
    
    public void set(Value value, double probability)
    {
        put(value, probability);
    }
    
    public double get(Value value)
    {
        Double p = super.get(value);
        if(p == null)
            throw new IllegalArgumentException(value.toString());
        else
            return p.doubleValue();
    }
    
    public void normalize()
    {
        double sum = 0.0;
        for(Double value: values())
            sum += value.doubleValue();
        
        for(Map.Entry<Value,Double> entry: this.entrySet())
            entry.setValue(entry.getValue()/sum);
    }
    
    public Distribution copy()
    {
        Distribution newDist = new Distribution(this.variable);
        for (Map.Entry<Value,Double> entry: this.entrySet())
        {
            Double oldProb = entry.getValue();
            Double newProb = new Double(oldProb);
            newDist.put(entry.getKey(), newProb);
        }
        
        return newDist;
    }
    
    
}
