/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author jzeng
 */
public class Value<T> {
    protected T value;
    
    public T getStringValue()
    {
        return value;
    }
    
    public Value(T value)
    {
        this.value = value;
    }
    
    public String toString()
    {
        return value.toString();
    }
    
    public boolean equals(Object other)
    {
        if(other.getClass() == this.getClass() && other != null)
        {
            Value other_v = (Value) other;
            return other_v.value.equals(this.value);
        }
        
        return false;
    }
    
    public int hashCode()
    {
        return value.hashCode();
    }
}
