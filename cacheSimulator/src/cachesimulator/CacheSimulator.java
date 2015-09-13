package cachesimulator;

import algorithms.LRU;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;

public class CacheSimulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length==3){
            System.out.println("File: "+args[0]);
            System.out.println("Policy: "+args[1]);
            System.out.println("Cache size: "+args[2]);
            System.out.println("Evaluando una caché "+args[1]+" con "+args[2]+" entradas...");
            
            switch (args[1]) {
                case "LRU":
                    LRUcache(args[0],Integer.parseInt(args[2]));
                    break;
                case "OPTIMO":
                    break;
                case "CLOCK":
                    break;
                case "VARIANTE":
                    break;
                default:
                    System.out.println(args[1]+" no es una política de desalojo de caché válida!");
                    break;
            }
        } else {
            System.out.println("Ejecutar por línea de comando con los siguientes argumentos:");
            System.out.println("<workload_file> <policy> <cache size>");
            System.out.println("Ejemplo: $java cacheSimulator workload.txt LRU 50000");
        }
    }
    
    public static void LRUcache(String filename, int entries) {
        File file = null;
        FileReader reader = null;
        BufferedReader buffer = null;
        //Se podría usar clase Scanner.java para probar eficiencia en la lectura.
        
        LRU lru = new LRU(entries);
        DecimalFormat df = new DecimalFormat("0.00");
        float hitrate = 0;
        
        try {
            file = new File(filename);
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line;
            while ((line=buffer.readLine()) != null) {
                String filedata = (String) lru.get(line);
                if (filedata == null) {
                    lru.put(line, filedata);
                }
            }
            
            hitrate=(float)lru.getHits()*100/lru.getLines();
            System.out.println("Resultados:");
            System.out.println("\tMiss rate:              "+df.format(100-hitrate)+"% ( "+(lru.getLines()-lru.getHits())+" misses out of Q references)");
            System.out.println("\tHit rate (warm cache):  "+df.format(hitrate)+"% ( "+lru.getHits()+" hits out of Q-(cache size) references)");
        } catch(Exception e) {
            System.out.println("Error al leer el archivo: "+e.getMessage());
        } finally {
            try {
                if(reader!=null)
                    reader.close();
            } catch (Exception ex) {
                System.out.println("Error al cerrar el archivo: "+ex.getMessage());
            }
        }
    }
}