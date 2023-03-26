/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author jzeng
 */
public class BooleanDomain extends ValueDomain{
    
    public BooleanDomain()
    {
        super(2);
        this.add(BooleanValue.TRUE);
        this.add(BooleanValue.FALSE);
    }
}
