/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author jzeng
 */
public class NamedVariable implements RandomVariable{
    protected String name;
    protected ValueDomain domain;
    
    public NamedVariable(String name, ValueDomain domain)
    {
        this.name = name;
        this.domain = domain;
    }
    
    public String getName()
    {
        return name;
    }
    
    public ValueDomain getDomain()
    {
        return domain;
    }
    
    public String toString()
    {
        return this.name;
    }
    

}
