package algorithms;

import java.util.HashMap;

/**
 * @author Kenny
 */
public class CLOCK implements CachePRPolicy <String, Object> {
    private HashMap <String, ClockPage> map = new HashMap <String, ClockPage> ();
    
    private int max_entries = 1;
    private int curr_entries = 0;
    private boolean warming = true;
    
    private int lookups = 0;
    private int warm_lookups = 0;
    private int misses = 0;
    private int warm_misses = 0;
    
    private ClockPage head = null; // Siempre apunta al primer elemento que se insertó
    private ClockPage tail = null;
    private ClockPage hand = null; // Siempre apunta al último elemento insertado
    
    public CLOCK(int entries) {
        this.max_entries = entries;
    }
    
    @Override
    public int getMaxEntries() {
        return max_entries;
    }
    
    @Override
    public int getLookups(boolean warm) {
        return warm?warm_lookups:lookups;
    }
    
    @Override
    public int getMisses(boolean warm) {
        return warm?warm_misses:misses;
    }
    
    @Override
    public Object retrieve(String key) {
        lookups++;
        if (!warming)
            warm_lookups++;
        
        ClockPage page = map.get(key);
        if (page == null) {
            misses++;
            
            if (!warming)
                warm_misses++;
            
            return null;
        }
        
        page.referenced = true;
        return page.getData();
    }
    
    @Override
    public void store(String key, Object data) {
        ClockPage page = new ClockPage(key, data);
        map.put(key, page);
        
        if (head == null) { // Primer elemento a insertar
            head = tail = hand = page;            
            curr_entries++;
        } else if (curr_entries < max_entries) { // Aun no se llenan los frames designados
            tail = page;
            hand.next = page;
            hand = hand.next;
            
            curr_entries++;
            
            if (curr_entries == max_entries) { // Frames llenos
                tail.next = head;
                warming = false;
            }
        } else { // Buscar página a desalojar
            ClockPage toDelete;
            ClockPage lastVisited = hand;
            
            while ((toDelete = hand.next).referenced) {
                toDelete.referenced = false;
                lastVisited = toDelete;
                hand = hand.next;
            }
            
            page.next = toDelete.next;
            lastVisited.next = page;
            hand = page;
            
            map.remove(toDelete.key);
            
            /*if (toDelete == head)
                head = page;
            
            ClockPage pointer = head;
            System.out.print("Cache: " + head.key + " -> ");
            while (pointer.next != null) {
                System.out.print(pointer.next.key + " -> ");
                pointer = pointer.next;

                if (pointer == head) {
                    break;
                }
            }
            System.out.println("");
            System.out.println("");*/
        }
    }
}

class ClockPage {
    public String key;
    public ClockPage next;
    private Object data;
    boolean referenced;
    
    public ClockPage(String key, Object data) {
        this.key = key;
        this.data = data;
        this.referenced = true;
    }
    
    public Object getData() {
        return data;
    }
}