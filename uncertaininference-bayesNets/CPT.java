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
public class CPT {
    
    protected NamedVariable variable;
    
    public CPT(NamedVariable variable)
    {
        this.variable = variable;
    }
    
    public NamedVariable getVariable()
    {
        return variable;
    }
    
    protected class AssignmentMap extends ArrayMap<Assignment,Distribution>{} 
    protected AssignmentMap table = new AssignmentMap();
    
    protected Distribution getRowForAssignment(Assignment a)
    {
        for(Map.Entry<Assignment,Distribution> entry: table.entrySet())
        {
            Assignment thisAssignment = entry.getKey();
            if(a.containsAll(thisAssignment))
                return entry.getValue();
        }
        
        return null;
    }
    
    protected Distribution addRowForAssignment(Assignment a) 
    {
        Distribution row = new Distribution(this.variable);
        this.table.put(a, row);
        return row;
    }
    
    public void set(Value value, Assignment assignment, double p)
    {
        Distribution row = getRowForAssignment(assignment);
        if(row == null)
            row = addRowForAssignment(assignment);
        row.put(value, p);
    }
    
    public double get(Value value, Assignment assignment) throws IllegalArgumentException
    {
        Distribution row = getRowForAssignment(assignment);
        if(row == null)
            throw new IllegalArgumentException(assignment.toString());
        else
            return row.get(value);
    }
    
    public String toString()
    {
        return this.table.toString();
    }
    
    public CPT copy()
    {
        CPT newCPT = new CPT(this.variable);
        for(Map.Entry<Assignment,Distribution> entry: this.table.entrySet())
        {
            Distribution oldDist = entry.getValue();
            Distribution newDist = oldDist.copy();
            newCPT.table.put(entry.getKey(), newDist);
        }
        
        return newCPT;
    }
}
