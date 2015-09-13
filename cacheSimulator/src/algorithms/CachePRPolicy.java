package algorithms;

public interface CachePRPolicy <K, V> {
    public V retrieve(K key);
    public void store(K key, V value);
    
    public int getLookups(boolean warm); // warm: si es true, se devuelven sólo los lookups hechos con todos los frames llenos
    public int getMisses(boolean warm); // warm: si es true, se devuelven sólo los misses con todos los frames llenos
    
    public int getMaxEntries();
}