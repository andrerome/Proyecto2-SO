package algorithms;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * @author Andres
 */
public class LRU extends LinkedHashMap <String, Object> implements CachePRPolicy <String, Object> {

  private final int max_entries;
  private int curr_entries = 0;
  private boolean warming = true;
  private int lookups = 0;
  private int misses = 0;
  private int warm_lookups = 0;
  private int warm_misses = 0;

  public LRU(int entries){
    super(entries, 0.75f, true);
    this.max_entries = entries;
  }
  
  @Override
  public Object retrieve(String key){
    lookups++;
    if (!warming)
        warm_lookups++;
    
    Object data = super.get(key);
        if (data == null) {
            misses++;
            if (!warming)
                warm_misses++;

            return null;
        }

        return data;
        
  }
  
  @Override
  public void store(String key, Object data) {
      
            boolean existe = super.containsKey(key);
            if (!existe)
                super.put(key,data);

            if (curr_entries < max_entries) { // warming
               
                curr_entries++;
                if (curr_entries == max_entries) {
                    // System.out.println("end warming");
                    warming = false;
                }
            }
  }

  @Override
  protected boolean removeEldestEntry(Entry eldest){
    return (super.size()>max_entries);
  }
    
    @Override
    public int getMaxEntries() {
        return max_entries;
    }
    
    @Override
    public int getMisses(boolean warm) {
        return warm?warm_misses:misses;
    }
    
    @Override
    public int getLookups(boolean warm) {
        return warm?warm_lookups:lookups;
    }
}