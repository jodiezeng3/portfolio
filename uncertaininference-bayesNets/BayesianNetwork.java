/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
/**
 *
 * @author jzeng
 */
public class BayesianNetwork implements Cloneable{
    
    public class Node {
        NamedVariable variable;
        Set<Node> parents;
        Set<Node> children = new ArraySet<Node>();
        CPT cpt;
        
        Node(NamedVariable variable)
        {
            this.variable = variable;
        }
    }
    
    protected Set<Node> nodes;
    
    public BayesianNetwork(int size)
    {
        this.nodes = new ArraySet<Node>(size);
    }
    
    public BayesianNetwork()
    {
        this(0);
    }
    
    public BayesianNetwork(Collection<NamedVariable> variables)
    {
        this(variables.size());
        for(NamedVariable var: variables)
            this.add(var);
    }
    
    public int size()
    {
        return nodes.size();
    }
    
    public void add(NamedVariable var)
    {
        nodes.add(new Node(var));
    }
    
    public Node getNodeForVariable(NamedVariable var)
    {
        for(Node node: nodes)
        {
            if(node.variable == var)
                return node;
        }
        
        throw new NoSuchElementException();
    }
    
    public NamedVariable getVariableByName(String name)
    {
        for(Node node: nodes)
        {
            NamedVariable var = node.variable;
            if(var.getName().equals(name))
                return var;
        }
        
        throw new NoSuchElementException();
    }
    
    public Set<NamedVariable> getVariables()
    {
        Set<NamedVariable> vars = new ArraySet<NamedVariable>(nodes.size());
        for(Node node: nodes)
            vars.add(node.variable);
        
        return vars;
    }
    
    /*
        connect node for given random var to nodes in set of parent random var w/ given cpt
    */
    public void connect(NamedVariable var, Set<NamedVariable> parents, CPT cpt)
    {
        Node node = getNodeForVariable(var);
        node.parents = new ArraySet<Node>(parents.size());
        for(NamedVariable pvar: parents)
        {
            Node pnode = getNodeForVariable(pvar);
            node.parents.add(pnode);
            pnode.children.add(node);
        }
        
        node.cpt = cpt;
    }
    
    public Set<NamedVariable> getChildren(NamedVariable X)
    {
        Set<NamedVariable> children = new ArraySet<NamedVariable>();
        Node node = getNodeForVariable(X);
        for(Node childNode: node.children)
            children.add(childNode.variable);
        
        return children;
    }
    
    public Set<NamedVariable> getParents(NamedVariable X)
    {
        Set<NamedVariable> parents = new ArraySet<NamedVariable>();
        Node node = getNodeForVariable(X);
        for(Node parentNode: node.parents)        
            parents.add(parentNode.variable);
        
        return parents;
    }
    
    /*
        return prob stored in CPT for given random var, given parents in given
        assignment
    */
    public double getProbability(NamedVariable X, Assignment e)
    {
        Node node = getNodeForVariable(X);
        Value value = e.get(X);
        double result = node.cpt.get(value, e);
        /* Print for testing ->>
        System.out.println("Posterior for " +X.getName() + " " + value.toString());
        Iterator pi = this.getParents(X).iterator();
        while(pi.hasNext())
        {
            NamedVariable pn =(NamedVariable) pi.next();
            System.out.println(pn.getName() + "  " + e.get(pn).toString());
        }
        
        System.out.println("Prob: " + result);*/
        return result;
    }
    
    public void setProbability(NamedVariable X, Assignment e, double p)
    {
        Node node = getNodeForVariable(X);
        Value value = e.get(X);
        node.cpt.set(value, e, p);
    }
    
    public List<NamedVariable> getVariablesSortedTopologically()
    {
        //L <- empty list that will contain sorted nodes
        List<NamedVariable> L = new ArrayList<NamedVariable>(nodes.size());
        //S <- set of all nodes w/ no outgoing connections
        Set<Node> S = new ArraySet<Node>(nodes.size());
        for(Node node: nodes)
        {
            if(node.children.isEmpty())
                S.add(node);
        }
        
        Set<Node> visited = new ArraySet<Node>(nodes.size());
        
        for(Node n: S)
            visit(n, L, visited);
        
        return L;
    }
    
    protected void visit(Node n, List<NamedVariable> L, Set<Node> visited)
    {
        if(!visited.contains(n))
        {
            visited.add(n);
            
            for(Node m: nodes)
            {
                if(m.children.contains(n))
                    visit(m, L, visited);
            }
            
            L.add(n.variable);
        }
    }
    
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for(NamedVariable var: getVariablesSortedTopologically())
        {
            builder.append(var.toString());
            builder.append("<-");
            Node node = getNodeForVariable(var);
            if(node.parents != null)
            {
                for(Node pnode: node.parents)
                {
                    builder.append(pnode.variable.toString());
                }
            }
            builder.append("\n");
            if(node.cpt != null)
            {
                builder.append(node.cpt.toString());
                builder.append("\n");
            }
        }
        
        return builder.toString();
    }
    
    public BayesianNetwork copy()
    {
        Set<NamedVariable> variables = this.getVariables();
        BayesianNetwork newNetwork = new BayesianNetwork(variables);
        for(NamedVariable var: variables)
        {
            Node node = this.getNodeForVariable(var);
            Set<Node> parents = node.parents;
            Set<NamedVariable> newParents = new ArraySet<NamedVariable>(parents.size());
            for(Node parentNode: parents)
            {
                NamedVariable parentVar = parentNode.variable;
                newParents.add(parentVar);
            }
            CPT newCPT = node.cpt.copy();
            newNetwork.connect(var, newParents, newCPT);
        }
        
        return newNetwork;
    }
    
}
