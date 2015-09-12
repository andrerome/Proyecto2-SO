/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
/**
 *
 * @author Andres
 */
public class LRU extends LinkedHashMap{

  private int entrys;
  private int lines=0;
  private int hits=0;

  public LRU(int entrys){
    super(entrys, 0.75f, true);
    this.entrys = entrys;
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
    return (size()>entrys);
  }

    public int getEntrys() {
        return entrys;
    }

    public int getHits() {
        return hits;
    }

    public int getLines() {
        return lines;
    }
   
}
