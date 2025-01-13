

public class Entry<K extends Comparable,V> {
    K key;
    V value;
    
    K getKey(){
        return key;
    }

    V getValue(){
        return value;
    }
    
    public Entry(K k, V v){
        key = k;
        value = v;
    }   
}