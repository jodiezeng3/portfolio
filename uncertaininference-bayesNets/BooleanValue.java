/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author jzeng
 */
public class BooleanValue extends Value<Boolean>{
    public static final BooleanValue TRUE = new BooleanValue(Boolean.TRUE);
    public static final BooleanValue FALSE = new BooleanValue(Boolean.FALSE);
    
    public BooleanValue(Boolean b)
    {
        super(b);
    }
    
    protected Boolean booleanValue()
    {
        return this.value;
    }
    
    public static BooleanValue valueOf(boolean b)
    {
        if(b)
            return TRUE;
        else
            return FALSE;
    }
    
    public static BooleanValue valueOf(String s) throws IllegalArgumentException 
    {
        if(s.equalsIgnoreCase("true"))
            return TRUE;
        else if (s.equalsIgnoreCase("false"))
            return FALSE;
        else
            throw new IllegalArgumentException(s);
    }
}
