/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author jzeng
 */
public class BooleanVariable extends NamedVariable{
    
    static ValueDomain domain = new BooleanDomain();
    
    public BooleanVariable(String name)
    {
        super(name, domain);
    }
}
