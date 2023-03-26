/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author jzeng
 */
public class ArrayMap<K, V> extends AbstractMap<K, V>{
    
    protected ArraySet<Map.Entry<K, V>> entries;
    
    public ArrayMap()
    {
        this(0);
    }
    
    public ArrayMap(int initialCapacity)
    {
        this.entries = new ArraySet<Map.Entry<K, V>>(initialCapacity);
    }
    
    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return this.entries;
    }
    
    @Override
    public V put(K key, V value)
    {
        V oldValue = this.remove(key);
        Map.Entry<K, V> entry = new AbstractMap.SimpleEntry<K,V>(key, value);
        this.entries.add(entry);
        return oldValue;
    }
    
    public ArrayMap<K,V> copy()
    {
        ArrayMap<K,V> newMap = new ArrayMap<K,V>(this.size());
        for(Map.Entry<K,V> entry: this.entrySet())
            newMap.put(entry.getKey(), entry.getValue());
        
        return newMap;
    }
    
}
