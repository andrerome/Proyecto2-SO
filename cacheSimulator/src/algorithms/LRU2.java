package algorithms;

import java.util.HashMap;

/**
 * @author Andres
 */
public class LRU2 implements CachePRPolicy <String, Object> {

    private boolean DEBUG = false;

    private HashMap <String, LRUPage> map = new HashMap <String, LRUPage> ();

    private HashMap <String, LRUPage> map2 = new HashMap <String, LRUPage> ();

    private int max_entries = 1;
    private int curr_entries = 0;
    private boolean warming = true;

    private int lookups = 0;
    private int warm_lookups = 0;
    private int misses = 0;
    private int warm_misses = 0;

    public LRU2(int entries){
      this.max_entries = entries;
    }

    @Override
    public Object retrieve(String key) {
      lookups++;

      if (!warming)
          warm_lookups++;

      LRUPage page = map.get(key);
      //LRUPage page2 = map2.get(key);

      if (page == null) {
          misses++;

          if (!warming)
              warm_misses++;

          return null;
      }

      //page.referenced = true;
      LRUPage page2 = map2.get(key);
      
      if(page2 != null) {
          page2.penultimate = page2.ultimate;
          page2.ultimate = lookups;
          page2.referencedTwice = true;
      }
      
      
      return page.getData();
    }

    @Override
    public void store(String key, Object data) {
        LRUPage nuevo = new LRUPage(key, data);
        nuevo.ultimate = this.lookups;
        
        if(this.curr_entries < this.max_entries) {
            this.map.put(key,nuevo);
            this.map2.put(key,nuevo);
        } else {
            LRUPage toDelete = null;
            
            for (String col : map.keySet()) {
                LRUPage item = map2.get(col);
                
                if(item!=null) {
                    if(toDelete == null) {
                        toDelete = item;
                    } else {
                        if(item.penultimate!=0?item.penultimate < toDelete.penultimate:item.ultimate < toDelete.ultimate) {
                            toDelete = item;
                        }
                    }
                }                               
            }
            
             map.remove(toDelete.key);
             map.put(key, nuevo);
             
             LRUPage toEdit = map2.get(key);
             
             if(toEdit!= null) {
                 toEdit.penultimate = toEdit.ultimate;
                 toEdit.ultimate = lookups;
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

class LRUPage {
    public String key;
    private Object data; 
    public int penultimate = 0;
    public int ultimate = 0;
    boolean referencedTwice;
    
    public LRUPage(String key, Object data) {
        this.key = key;
        this.data = data;
        this.referencedTwice = false;
        this.penultimate = 0;
        this.ultimate = 0;
    }
    
    public Object getData() {
        return data;
    }
}