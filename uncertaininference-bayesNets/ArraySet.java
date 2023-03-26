/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
/**
 *
 * @author jzeng
 */
public class ArraySet<E> extends AbstractSet<E> {
    
    protected ArrayList<E> elements;
    
    public ArraySet()
    {
        this(0);
    }
    
    public ArraySet(int initialCapacity)
    {
        super();
        elements = new ArrayList<E>(initialCapacity);
    }
    
    public ArraySet(Collection<? extends E> c)
    {
        this(c.size());
        for(E e: c)
            add(e);
    }
    
    public ArraySet(E... elements)
    {
        this(elements.length);
        for(E e: elements)
            add(e);
    }
    
    @Override
    public Iterator<E> iterator()
    {
        return elements.iterator();
    }
    
    @Override
    public int size()
    {
        return elements.size();
    }
    
    @Override
    public boolean add(E e)
    {
        if(contains(e))
            return false;
        else
            return elements.add(e);
    }
    
    public ArraySet<E> copy()
    {
        ArraySet<E> newSet = new ArraySet<E>(this.size());
        for(E e: this)
            newSet.add(e);
        return newSet;
    }
}
