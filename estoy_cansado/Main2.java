package estoy_cansado;

public class Main2 {

    public static void main(String[] args) {
        System.out.println("=== INICIO DEL PROGRAMA PRODUCTOR-CONSUMIDOR ===\n");
        
        int N = 2;
        int n_servidores_entrega = 2;
        int F = 2;
        int bufferCapacity = 2;
        int totalItems = 5;
        
        System.out.println("Configuración:");
        System.out.println("- Productores: " + N);
        System.out.println("- Consumidores: " + n_servidores_entrega);
        System.out.println("- Capacidad del búfer: " + bufferCapacity);
        System.out.println("- Total de ítems a producir y consumir: " + totalItems);
        System.out.println();
        
        Buffer buffer = new Buffer(bufferCapacity,F);
        Entrega entrega = new Entrega(bufferCapacity,n_servidores_entrega);
        
        Producer.setTotalToProduce(totalItems);
        Producer[] producers = new Producer[N];
        for (int i = 0; i < N; i++) {
            producers[i] = new Producer("Productor " + (i+1), buffer);
            producers[i].start();
        }

        Filter.setTotalToConsume(totalItems+1);
        Filter[] filters = new Filter[F];
        for (int i = 0; i < F; i++) {
            filters[i] = new Filter("Filtro " + (i+1), buffer, entrega);
            filters[i].start();
        }
        
        Consumer.setTotalToConsume(totalItems+1);
        Consumer[] consumers = new Consumer[n_servidores_entrega];
        for (int i = 0; i < n_servidores_entrega; i++) {
            consumers[i] = new Consumer("Consumidor " + (i+1), entrega);
            consumers[i].start();
        }
        
        for (Producer producer : producers) {
            try {
                producer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        
        for (int i = 0; i < F; i++) {
            buffer.put(Thread.currentThread(), new Item("FIN"));
        }
        

        for (Filter filter : filters) {
            try {
                filter.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
        for (int i = 0; i < n_servidores_entrega; i++) {
            entrega.put(Thread.currentThread(), new Item("FIN"));
        }
        */

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