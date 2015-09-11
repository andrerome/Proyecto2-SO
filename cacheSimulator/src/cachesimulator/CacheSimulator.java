/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachesimulator;

/**
 *
 * @author Andres
 */
public class CacheSimulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if(args.length==3){
            System.out.println("File: "+args[0]);
            System.out.println("Policy: "+args[1]);
            System.out.println("Cache size: "+args[2]);
            System.out.println("Evaluando una caché "+args[1]+" con "+args[2]+" entradas.");
            System.out.println("Resultados:");
            System.out.println("\tMiss rate:              x% (W misses out of Q references)");
            System.out.println("\tMiss rate (warm cache): y% (M misses out of Q-(cache size) references)");
        }else{
            System.out.println("Ejecutar por línea de comando con los siguientes argumentos:");
            System.out.println("<workload_file> <policy> <cache size>");
            System.out.println("Ejemplo: $java cacheSimulator workload.txt LRU 50000");
        }
   
    }
   
    
}
