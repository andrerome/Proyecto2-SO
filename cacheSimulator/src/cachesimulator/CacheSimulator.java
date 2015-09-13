package cachesimulator;

import algorithms.CachePRPolicy;
import algorithms.LRU;
import algorithms.OPT;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
// import java.util.HashMap;

public class CacheSimulator {
    // static HashMap <String, Integer> map = new HashMap <String, Integer> (); // Tests para implementar OPT

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length==3){
            String file = args[0];
            String policyType = args[1];
            int cacheSize = Integer.parseInt(args[2]);
            
            CachePRPolicy policy = null;
            
            /*System.out.println("File: "+file);
            System.out.println("Policy: "+policy);
            System.out.println("Cache size: "+cacheSize);
            System.out.println("Evaluando una caché "+policy+" con "+cacheSize+" entradas...");*/
            
            switch (policyType) {
                case "LRU":
                    policy = new LRU(cacheSize);
                    break;
                case "OPTIMO":
                    policy = new OPT(cacheSize, file);
                    break;
                case "CLOCK":
                    break;
                case "VARIANTE":
                    break;
                default:
                    System.out.println(policyType+" no es una política de desalojo de caché válida!");
                    break;
            }
            
            readFile(policy, file);
            showResults(policy);
        } else {
            System.out.println("Ejecutar por línea de comando con los siguientes argumentos:");
            System.out.println("<workload_file> <policy> <cache size>");
            System.out.println("Ejemplo: java -jar cacheSimulator.jar workload.txt LRU 50000");
        }
    }
    
    public static void showResults(CachePRPolicy policy) {
        DecimalFormat df = new DecimalFormat("0.00");
        
        int lookups = policy.getLookups(false);
        int misses = policy.getMisses(false);

        int warm_lookups = policy.getLookups(true);
        int warm_misses = policy.getMisses(true);

        float missrate = (float) misses * 100 / lookups;
        float warm_missrate = warm_misses == 0 ? 0 : (float) warm_misses * 100 / warm_lookups;

        System.out.println("Resultados:");
        System.out.println("\tMiss rate:              " + df.format(missrate) + "% ( " + misses + " misses out of " + lookups + " references)");
        System.out.println("\tMiss rate (warm cache):  " + df.format(warm_missrate) + "% ( " + warm_misses + " misses out of " + lookups + "-" + policy.getMaxEntries() + " references)");
    }
    
    public static void readFile(CachePRPolicy policy, String filename) {
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(filename));
            String line;
            // int max = 1;
            
            while ((line = in.readLine()) != null) {
                /*Integer count = map.get(line);
                if (count == null)
                    map.put(line, 1);
                else {
                    count++;
                    map.put(line, count);
                    max = Math.max(max, count);
                }*/
                
                Object data = policy.retrieve(line);
                if (data == null)
                    policy.store(line, true);
            }
            
            /*System.out.println(map.keySet().size()+" different references");
            System.out.println(max+" max positions");*/
            
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
}