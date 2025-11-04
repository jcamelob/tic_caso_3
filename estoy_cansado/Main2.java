package estoy_cansado;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main2 {

    public static void main(String[] args) {
        System.out.println("=== INICIO DEL PROGRAMA PRODUCTOR-CONSUMIDOR ===\n");

        int n_emisores = 0;
        int n_servidores_entrega = 0;
        int n_filtros = 0;
        int bufferCapacity = 0;
        int totalItems = 0;

        // === LECTURA DE PARÁMETROS DESDE ARCHIVO ===
        String filename = "estoy_cansado\\config.txt"; // nombre del archivo con los parámetros

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            n_emisores = Integer.parseInt(br.readLine().trim());
            n_servidores_entrega = Integer.parseInt(br.readLine().trim());
            n_filtros = Integer.parseInt(br.readLine().trim());
            bufferCapacity = Integer.parseInt(br.readLine().trim());
            totalItems = Integer.parseInt(br.readLine().trim());
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer el archivo de configuración: " + e.getMessage());
            return; // termina el programa si hay un error
        }

        // === MUESTRA DE CONFIGURACIÓN ===
        System.out.println("Configuración:");
        System.out.println("- Productores: " + n_emisores);
        System.out.println("- Consumidores: " + n_servidores_entrega);
        System.out.println("- Filtros: " + n_filtros);
        System.out.println("- Capacidad del búfer: " + bufferCapacity);
        System.out.println("- Total de ítems a producir y consumir: " + totalItems);
        System.out.println();

        // === CREACIÓN DE OBJETOS Y HILOS ===
        Buffer buffer = new Buffer(bufferCapacity, n_filtros);
        Entrega entrega = new Entrega(bufferCapacity, n_servidores_entrega);
        Cuarentena cuarentena = new Cuarentena();

        Producer[] producers = new Producer[n_emisores];
        for (int i = 0; i < n_emisores; i++) {
            producers[i] = new Producer("Productor " + (i + 1), buffer, totalItems);
            producers[i].start();
        }

        Filter.setTotalToConsume(totalItems + 1);
        Filter[] filters = new Filter[n_filtros];
        for (int i = 0; i < n_filtros; i++) {
            filters[i] = new Filter("Filtro " + (i + 1), buffer, entrega, cuarentena, n_emisores, n_filtros);
            filters[i].start();
        }

        Consumer.setTotalToConsume(totalItems + 1);
        Consumer[] consumers = new Consumer[n_servidores_entrega];
        for (int i = 0; i < n_servidores_entrega; i++) {
            consumers[i] = new Consumer("Consumidor " + (i + 1), entrega);
            consumers[i].start();
        }

        // Espera a los productores
        for (Producer producer : producers) {
            try {
                producer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Manejador de cuarentena
        ManejadorCuarentena manejador = new ManejadorCuarentena("Manejador Cuarentena", cuarentena, entrega);
        manejador.start();

        // Espera a los filtros
        for (Filter filter : filters) {
            try {
                filter.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Espera al manejador
        try {
            manejador.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Espera a los consumidores
        for (Consumer consumer : consumers) {
            try {
                consumer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n=== PROGRAMA FINALIZADO ===");
    }
}
