package algorithms;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * @author Andres
 */
public class LRU extends LinkedHashMap{

  private int entries;
  private int lines=0;
  private int hits=0;

  public LRU(int entries){
    super(entries, 0.75f, true);
    this.entries = entries;
  }

  public Object get(Object key){
    lines++;
    if(containsKey(key)){
      hits++;
    }
    Object value=super.get(key);
    return value;
  }

  protected boolean removeEldestEntry(Entry eldest){
    return (size()>entries);
  }

    public int getEntrys() {
        return entries;
    }

    public int getHits() {
        return hits;
    }

    public int getLines() {
        return lines;
    }   
}