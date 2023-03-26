/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
/**
 *
 * @author jzeng
 */
public class XMLBIFPrinter {
    
    public String doubleFormatString = "%g";
    protected PrintStream out;
    
    public XMLBIFPrinter(PrintStream out)
    {
        this.out = out;
    }
    
    public void print(BayesianNetwork network)
    {
        print(network, null);
    }
    
    public void print(BayesianNetwork network, String name) 
    {		
        printXMLHeader();
        printDOCTYPE();
        printBIFHeader();
        printNetwork(network, name);
        printBIFTrailer();
    }
    
    protected void printXMLHeader() 
    {
        out.println("<?xml version=\"1.0\"?>");
    }
    
    protected void printDOCTYPE() 
    {
        out.println("<!-- DTD for the XMLBIF 0.3 format -->\n" + 
            "<!DOCTYPE BIF [\n" + 
            "	<!ELEMENT BIF ( NETWORK )*>\n" + 
            "	      <!ATTLIST BIF VERSION CDATA #REQUIRED>\n" + 
            "	<!ELEMENT NETWORK ( NAME, ( PROPERTY | VARIABLE | DEFINITION )* )>\n" + 
            "	<!ELEMENT NAME (#PCDATA)>\n" + 
            "	<!ELEMENT VARIABLE ( NAME, ( OUTCOME |  PROPERTY )* ) >\n" + 
            "	      <!ATTLIST VARIABLE TYPE (nature|decision|utility) \"nature\">\n" + 
            "	<!ELEMENT OUTCOME (#PCDATA)>\n" + 
            "	<!ELEMENT DEFINITION ( FOR | GIVEN | TABLE | PROPERTY )* >\n" + 
            "	<!ELEMENT FOR (#PCDATA)>\n" + 
            "	<!ELEMENT GIVEN (#PCDATA)>\n" + 
            "	<!ELEMENT TABLE (#PCDATA)>\n" + 
            "	<!ELEMENT PROPERTY (#PCDATA)>\n" + 
            "]>");
    }
    
    protected void printBIFHeader() 
    {
        out.println("<BIF VERSION=\"0.3\">");
    }
    
    protected void printBIFTrailer() 
    {
        out.println("</BIF>");
    }
    
    protected void printNetwork(BayesianNetwork network, String name) 
    {
        out.println("<NETWORK>");
        if (name != null) 
            out.println("<NAME>" + name + "</NAME>");
        
        // Variables
        for (NamedVariable var : network.getVariables()) 
        {
            out.println("<VARIABLE TYPE=\"nature\">");
            out.println("  <NAME>" + getNameOrDie(var) + "</NAME>");
            for (Value value : var.getDomain()) 
                out.println("  <OUTCOME>" + value + "</OUTCOME>");
           
            out.println("</VARIABLE>");
        }
        // CPTs
        // This requires access to some of the internals of BayesianNetworks, which
        // I have now made public
        for (NamedVariable var : network.getVariables()) 
        {
            out.println("<DEFINITION>");
            out.println("  <FOR>" + getNameOrDie(var) + "</FOR>");
            // Parents must be ordered and that order is used for printing the TABLE
            List<NamedVariable> givens = new ArrayList<NamedVariable>(network.getParents(var)); 
            for (NamedVariable given : givens) 
                out.println("  <GIVEN>" + getNameOrDie(given) + "</GIVEN>");

            out.println("  <TABLE>");
            recursivelyPrintTable(network, new Assignment(), var, givens);
            out.println("  </TABLE>");
            out.println("</DEFINITION>");
        }
        out.println("</NETWORK>");
    }
    
    protected String getNameOrDie(NamedVariable var) throws IllegalArgumentException 
    {
        if (var instanceof NamedVariable) 
        {
            NamedVariable nvar = (NamedVariable)var;
            return nvar.getName();
        } 
        else 
            throw new IllegalArgumentException("unnamed RandomVariable cannot be expressed using XMBIF: " + var);
        
    }
    
    protected void recursivelyPrintTable(BayesianNetwork network, Assignment a, NamedVariable forVar, List<NamedVariable> givens) 
    {
        if (givens.isEmpty()) 
        {
            // No givens: print the probabilities for the values of forVar with the given assignment
            out.print("    ");
            
            for (Value v : forVar.getDomain()) 
            {
                a.put(forVar, v);
                double p = network.getProbability(forVar, a);
                out.format(doubleFormatString + " ", p);
                a.remove(forVar);
            }
            
            out.println();
        } 
        else 
        {
            // Otherwise: iterate over values of first given and recurse on rest
            NamedVariable firstGiven = givens.get(0);
            List<NamedVariable> restGivens = givens.subList(1, givens.size());
            for (Value v : firstGiven.getDomain()) 
            {
                a.put(firstGiven, v);
                recursivelyPrintTable(network, a, forVar, restGivens);
                a.remove(firstGiven);
            }
        }
    }
}
