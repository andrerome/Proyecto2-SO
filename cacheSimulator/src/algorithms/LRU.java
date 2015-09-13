package algorithms;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * @author Andres
 */
public class LRU extends LinkedHashMap <String, Object> implements CachePRPolicy <String, Object> {

  private int entries;
  private int lines=0;
  private int hits=0;

  public LRU(int entries){
    super(entries, 0.75f, true);
    this.entries = entries;
  }
  
  @Override
  public Object retrieve(String key){
    lines++;
    if(super.containsKey(key)){
      hits++;
    }
    
    return super.get(key);
  }
  
  @Override
  public void store(String key, Object data) {
      super.put(key, data);
  }

  @Override
  protected boolean removeEldestEntry(Entry eldest){
    return (super.size()>entries);
  }
    
    @Override
    public int getMaxEntries() {
        return entries;
    }
    
    @Override
    public int getMisses(boolean warm) {
        if (warm)
            return 1; // TODO: Implementar warm
        else
            return lines - hits;
    }
    
    @Override
    public int getLookups(boolean warm) {
        if (warm)
            return 1; // TODO: Implementar warm
        else
            return lines;
    }
}