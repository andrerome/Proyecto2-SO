package algorithms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Carlos
 */
public class OPT implements CachePRPolicy <String, Object> {
    private HashMap <String, LinkedList <Integer>> crystalball = new HashMap <String, LinkedList <Integer>> ();
    private HashMap <String, Object> map = new HashMap <String, Object> (); // caché
    
    private int max_entries;
    private int curr_entries = 0;
    private boolean warming = true;
    
    private int lookups = 0;
    private int warm_lookups = 0;
    private int misses = 0;
    private int warm_misses = 0;
    
    public OPT(int entries, String filename) {
        this.max_entries = entries;
        
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(filename));
            String line;
            int index = 0;

            while ((line = in.readLine()) != null) {
                LinkedList <Integer> positions = crystalball.get(line);
                if (positions == null) {
                    positions = new LinkedList <Integer> ();
                    crystalball.put(line, positions);
                }
                
                positions.add(new Integer(index));
                index++;
            }
            
            // System.out.println(crystalball);
            
            in.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error: Archivo \"" + filename + "\" no encontrado");
        } catch (IOException ex) {
            System.out.println("Error: No se pudo leer del archivo \"" + filename + "\"");
        } finally {
            if (in == null) {
                System.out.println("Error: El archivo \"" + filename + "\" no pudo ser abierto");
            }
        }
    }

    @Override
    public Object retrieve(String key) {
        lookups++;
        if (!warming)
            warm_lookups++;

        Object data = map.get(key);
        if (data == null) {
            misses++;
            if (!warming)
                warm_misses++;

            return null;
        }

        return data;
    }

    @Override
    public void store(String key, Object value) {
        if (curr_entries < max_entries) { // warming
            boolean existe = map.containsKey(key);
            if (!existe) {
                map.put(key, value);
                // System.out.println("warming: "+map);
                
                curr_entries++;
                if (curr_entries == max_entries) {
                    // System.out.println("end warming");
                    warming = false;
                }
            }
        } else { // reemplazo
            // System.out.println("cache before: "+map);
            int index = lookups - 1;
            // System.out.println("try to put: "+index+": "+key);
            
            String key_toremove = null;
            int max = index;
            for (String existent_key : map.keySet()) {
                LinkedList <Integer> positions = crystalball.get(existent_key);
                int pos = -1;
                
                while (!positions.isEmpty() && (pos = positions.peekFirst()) < index) {
                    positions.pop();
                }
                
                if (positions.isEmpty()) {
                    key_toremove = existent_key;
                    break;
                } else if (pos > max) {
                    key_toremove = existent_key;
                    max = pos;
                }
            }
            
            // System.out.println("toremove: "+key_toremove);
            // System.out.println("crystalball: "+crystalball);
            
            map.remove(key_toremove);
            map.put(key, value);
            
            // System.out.println("cache after: "+map);
            // System.out.println("");
        }
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
    public int getMaxEntries() {
        return max_entries;
    }
}