/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachesimulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            readFile(args[0]);
        }else{
            System.out.println("Ejecutar por línea de comando con los siguientes argumentos:");
            System.out.println("<workload_file> <policy> <cache size>");
            System.out.println("Ejemplo: $java cacheSimulator workload.txt LRU 50000");
        }
   
    }
    
    public static void readFile(String filename){
        File file=null;
        FileReader reader=null;
        BufferedReader buffer=null;
        //Se podría usar clase Scanner.java para probar eficiencia en la lectura.
        
        try{
           file=new File(filename);
           reader=new FileReader(file);
           buffer=new BufferedReader(reader);
           
           String line;
           while((line=buffer.readLine())!=null)
              System.out.println(line);
        }
        catch(Exception e){
           System.out.println("Error al leer el archivo: "+e.getMessage());
        }finally{
            try {
                if(reader!=null)   
                   reader.close();
            } catch (Exception ex) {
                System.out.println("Error al cerrar el archivo: "+ex.getMessage());
            }
        }
    }
   
    
}
