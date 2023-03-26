/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author jzeng
 */
public class Assignment extends ArrayMap<NamedVariable,Value> {
    
    public Assignment()
    {
        super();
    }
    
    public Assignment(NamedVariable X1, Value x1)
    {
        this();
        this.put(X1, x1);
    }
    
    public Assignment(NamedVariable X1, Value x1, NamedVariable X2, Value x2)
    {
        this(X1, x1);
        this.put(X2, x2);
    }
    
    public Assignment(NamedVariable X1, Value x1, NamedVariable X2, Value x2, NamedVariable X3, Value x3)
    {
        this(X1, x1, X2, x2);
        this.put(X3, x3);
    }
    
    public Set<NamedVariable> variableSet() {
        return this.keySet();
    }
    
    //@Override
    public boolean containsAll(Assignment other)
    {
        Set<Map.Entry<NamedVariable,Value>> ourEntries = this.entrySet();
        Set<Map.Entry<NamedVariable,Value>> theirEntries = other.entrySet();
        return ourEntries.containsAll(theirEntries);
    }
    
    
    @Override
    public Assignment copy()
    {
        Assignment result = new Assignment();
        for(Map.Entry<NamedVariable, Value> entry : this.entrySet())
        {
            NamedVariable var = entry.getKey();
            Value val = entry.getValue();
            result.put(var, val);
        }
        
        return result;
    }
}
