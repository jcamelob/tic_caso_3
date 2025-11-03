package estoy_cansado;

import java.util.LinkedList;
import java.util.Queue;

public class Entrega {

    private int capacity;
    private Queue<Item> queue;
    private int cerrado = 0;
    private int n_consumidores;

    public Entrega(int capacity, int n_consumidores) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
        this.n_consumidores = n_consumidores;
    }

    public synchronized void put(Thread thread, Item item) {
        while (queue.size() >= capacity) {
            try {
                System.out.println("[" + thread.getName() + "]: espera porque el buzón de entrega está lleno");
                wait();
                System.out.println("[" + thread.getName() + "]: despertó porque hay espacio en el buzón de entrega");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.add(item);
        System.out.println("[" + thread.getName() + "]: guardó en el buzón de entrega el " + item.getName());
        notifyAll();
    }

    public synchronized Item get(Thread thread) {
        while (queue.isEmpty()) {
        try {
            System.out.println("[" + thread.getName() + "]: espera porque el buzón de entrega está vacío");
            wait();
            System.out.println("[" + thread.getName() + "]: despertó porque hay ítems en el buzón de entrega");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    Item item = queue.poll();
    if (item.getName().equals("FIN")) {
        this.cerrado += 1;
    }
    System.out.println("[" + thread.getName() + "]: agarró del buzón de entrega el " + item.getName());
    notifyAll();
    return item;
    }

    public synchronized void esperar_final(){ //OJO revisar
        while (this.cerrado < this.n_consumidores){
            try {
                Thread.sleep(20);
                notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}