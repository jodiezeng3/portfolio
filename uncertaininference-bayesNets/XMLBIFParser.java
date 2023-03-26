/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
/**
 *
 * @author jzeng
 */
public class XMLBIFParser {
    
    public BayesianNetwork readNetworkFromFile(String filename) throws IOException, ParserConfigurationException, SAXException
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(filename));
        return processDocument(doc);
    }
    
    protected BayesianNetwork processDocument(Document doc)
    {
        final BayesianNetwork network = new BayesianNetwork();
        //variables
        doForEachElement(doc, "VARIABLE", new ElementTaker()
        {
           public void element(Element e)
           {
               processVariableElement(e, network);
           }
        });
        //defintions(links + cpts)
        doForEachElement(doc, "DEFINITION", new ElementTaker() {
           public void element(Element e)
           {
               processDefinitionElement(e, network);
           }
        });
        
        return network;
    }
    
    protected void doForEachElement(Document doc, String tagname, ElementTaker taker)
    {
        NodeList nodes = doc.getElementsByTagName(tagname);
        if(nodes != null && nodes.getLength() > 0)
        {
            for(int i = 0; i < nodes.getLength(); i++)
            {
                Node node = nodes.item(i);
                taker.element((Element) node);
            }
        }
    }
    
    protected void processVariableElement(Element e, BayesianNetwork network)
    {
        Element nameElt = getChildWithTagName(e, "NAME");
        String name = getChildText(nameElt);
        
        final ValueDomain domain = new ValueDomain();
        doForEachChild(e, "OUTCOME", new ElementTaker() {
           public void element(Element e) 
           {
               String value = getChildText(e);
               domain.add(new StringValue(value));
           } 
        });
        NamedVariable var = new NamedVariable(name, domain);
        network.add(var);
    }
    
    protected void processDefinitionElement(Element e, final BayesianNetwork network)
    {
        Element forElt = getChildWithTagName(e, "FOR");
        String forName = getChildText(forElt);
        
        NamedVariable forVar = network.getVariableByName(forName);
        
        final List<NamedVariable> givens = new ArrayList<NamedVariable>();
        doForEachChild(e, "GIVEN", new ElementTaker() {
           public void element(Element e) {
               String value = getChildText(e);
               givens.add(network.getVariableByName(value));
           } 
        });
        CPT cpt = new CPT(forVar);
        Element tableElt = getChildWithTagName(e, "TABLE");
        String tableStr = getChildText(tableElt);
        initCPTFromString(cpt, givens, tableStr);
        Set<NamedVariable> parents = new ArraySet<NamedVariable>(givens);
        network.connect(forVar, parents, cpt);
    }
    
    public void initCPTFromString(CPT cpt, List<NamedVariable> givens, String str) throws NumberFormatException, CPTFormatException 
    {
        StringTokenizer tokens = new StringTokenizer(str);
        recursivelyInitCPT(cpt, new Assignment(), givens, tokens);
    }
    
    protected void recursivelyInitCPT(CPT cpt, Assignment a, List<NamedVariable> givens, StringTokenizer tokens)
    {
        if(givens.isEmpty())
        {
            for(Value v: cpt.getVariable().getDomain())
            {
                String token = tokens.nextToken();
                double p = Double.parseDouble(token);
                Assignment aa = a.copy();
                cpt.set(v, aa, p);
            }
        }
        else
        {
            NamedVariable firstGiven = givens.get(0);
            List<NamedVariable> restGivens = givens.subList(1, givens.size());
            for(Value v: firstGiven.getDomain())
            {
                a.put(firstGiven, v);
                recursivelyInitCPT(cpt, a, restGivens, tokens);
                a.remove(firstGiven);
            }
        }
    }
    
    protected Element getChildWithTagName(Element elt, String tagname)
    {
        NodeList children = elt.getChildNodes();
        if(children != null && children.getLength() > 0)
        {
            for(int i = 0; i < children.getLength(); i++)
            {
                Node node = children.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element childElt = (Element) node;
                    if(childElt.getTagName().equals(tagname))
                        return childElt;
                }
            }
        }
        
        throw new NoSuchElementException(tagname);
    }
    
    protected void doForEachChild(Element elt, String tagname, ElementTaker taker) 
    {
        NodeList children = elt.getChildNodes();
        if (children != null && children.getLength() > 0) 
        {
            for (int i=0; i < children.getLength(); i++) 
            {
                Node node = children.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element childElt = (Element)node;
                    if (childElt.getTagName().equals(tagname)) 
                        taker.element(childElt);                    
                }
            }
        }
    }
    
    public String getChildText(Node node) 
    {
        if (node == null) 
            return null;
        
        StringBuilder buf = new StringBuilder();
        Node child = node.getFirstChild();
        while (child != null) 
        {
            short type = child.getNodeType();
            if (type == Node.TEXT_NODE) 
                buf.append(child.getNodeValue());
            
            else if (type == Node.CDATA_SECTION_NODE) 
                buf.append(getChildText(child));
            
            child = child.getNextSibling();
        }
        
        return buf.toString();
    }
    
    protected void trace(String msg) 
    {
        System.err.println(msg);
    }
    
    interface ElementTaker {
	public void element(Element e);
}
}
