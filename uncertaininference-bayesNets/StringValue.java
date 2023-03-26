/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author jzeng
 */
public class StringValue extends Value<String>{
    
    public StringValue(String s)
    {
        super(s);
    }
    
    public String stringValue()
    {
        return this.value;
    }
}
