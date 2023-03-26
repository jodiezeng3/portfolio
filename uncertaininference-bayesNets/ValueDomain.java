/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Collection;

/**
 *
 * @author jzeng
 */
public class ValueDomain extends ArraySet<Value> implements Domain{
    public static final long serialVersionUID = 1L;
    
    public ValueDomain()
    {
        super();
    }
    
    public ValueDomain(int size)
    {
        super(size);
    }
    
    public ValueDomain(Value... values)
    {
        this();
        for(Value value: values)
            this.add(value);
    }
    
    public ValueDomain(Collection<Value> collection)
    {
        this();
        for(Value value: collection)
            this.add(value);
    }
}
