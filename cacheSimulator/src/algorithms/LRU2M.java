package algorithms;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Andres
 */
public class LRU2M implements CachePRPolicy <String, Object> {

    private boolean DEBUG = false;

    private LinkedHashMap <String, LRUPage> map = new LinkedHashMap <String, LRUPage> ();

    private HashMap <String, LRUPage> map2 = new HashMap <String, LRUPage> ();

    private int max_entries = 1;
    private int curr_entries = 0;
    private boolean warming = true;

    private int lookups = 0;
    private int warm_lookups = 0;
    private int misses = 0;
    private int warm_misses = 0;

    public LRU2M(int entries){
      this.max_entries = entries;
    }

    @Override
    public Object retrieve(String key) {
      lookups++;

      if (!warming)
          warm_lookups++;

      LRUPage page = map.get(key);

      if (page == null) {
          misses++;

          if (!warming)
              warm_misses++;

          return null;
      }
            
      return page.getData();
    }

    @Override
    public void store(String key, Object data) {
        LRUPage nuevo = new LRUPage(key, data);
        nuevo.ultimate = this.lookups;
        
        if(this.curr_entries < this.max_entries) {
            this.map.put(key,nuevo);
            this.curr_entries++;
        } else {            
            if (curr_entries == max_entries) { // Frames llenos
                warming = false;
            }
              
            int count = 0;
            for (String item : map.keySet()) {
                count++;
                if(count == 2){
                    map.remove(item);
                    map.put(key, nuevo);
                    break;                    
                }
            }
        }                    
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
}
