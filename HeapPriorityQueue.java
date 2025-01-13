

public class HeapPriorityQueue<K extends Comparable,V> implements PriorityQueue<K ,V> {
    
    private Entry[] storage; 
    private int tail;        
    
    public HeapPriorityQueue(){
        this(100);
    }
    

    public HeapPriorityQueue(int size){
        storage = new Entry[size];
        tail = -1;
    }
    
   
    @SuppressWarnings("unchecked")
    public int size(){
        return tail+1;
    }

   
    @SuppressWarnings("unchecked")
    public boolean isEmpty(){
        return tail<0;
    }
    
    
    
    @SuppressWarnings("unchecked")
    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException{
        if(tail == storage.length -1) throw new IllegalArgumentException("Heap Overflow");
        Entry<K,V> e = new Entry<>(key,value);        
        storage[++tail] = e;
        upHeap(tail);
        return e;
    }
    

    
    @SuppressWarnings("unchecked")
    public Entry<K,V> min(){
        if(isEmpty()) return null;
        return storage[0];
    }
    
    
    
    @SuppressWarnings("unchecked")
    public Entry<K,V> removeMin(){
        if(isEmpty()) return null;
        Entry<K,V> min = storage[0];
        
        if(tail == 0)
        {
            tail = -1;
            storage[0] = null;
            return min;
        }
        
        storage[0] = storage[tail];
        storage[tail--] = null;
        downHeap(0);
        return min;
    }  
    
    
    
    @SuppressWarnings("unchecked")
    private void upHeap(int location){
        if(location == 0)
        {
            return;
        } 
            
        int parent = parent(location);
        if(storage[parent].key.compareTo(storage[location].key) > 0)
        {
            swap(location,parent);
            upHeap(parent);
        }               
    }
    

    
    @SuppressWarnings("unchecked")
    private void downHeap(int location){
        int left = (location*2) +1;
        int right = (location*2) +2;
         
        
        if(left > tail)
        {
            return;
        }

      
        if(left == tail)
        {
            if(storage[location].key.compareTo(storage[left].key) > 0)
            {
                swap(location,left);                  
            }
            return;
        }

        int toSwap= (storage[left].key.compareTo(storage[right].key) < 0)? left:right;         
        if(storage[location].key.compareTo(storage[toSwap].key) > 0){
            swap(location,toSwap);
            downHeap(toSwap);
        }                 
    }
    
    @SuppressWarnings("unchecked")
    private int parent(int location){
        return (location-1)/2;
    }
    
    @SuppressWarnings("unchecked")
    private void swap(int location1, int location2){
        Entry<K,V> temp = storage[location1];
        storage[location1] = storage[location2];
        storage[location2] = temp;  
    }
    
}