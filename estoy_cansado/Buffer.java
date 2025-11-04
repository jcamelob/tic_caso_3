package estoy_cansado;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {

    private int capacity;
    private Queue<Item> queue;
    private int cerrado = 0;
    private int n_consumidores;
    private boolean notify = false;

    public Buffer(int capacity, int n_consumidores) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
        this.n_consumidores = n_consumidores;
    }

    public synchronized void put(Thread thread, Item item) {
        while (queue.size() >= capacity) {
            try {
                System.out.println("[" + thread.getName() + "]: espera porque el búfer está lleno");
                wait();
                System.out.println("[" + thread.getName() + "]: despertó porque hay espacio en el búfer");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.add(item);
        System.out.println("[" + thread.getName() + "]: guardó en el búfer el " + item.getName());
        notifyAll();
    }

    public synchronized Item get(Thread thread) {
        while (queue.isEmpty() && !notify) {
        try {
            System.out.println("[" + thread.getName() + "]: espera porque el búfer está vacío");
            wait();
            System.out.println("[" + thread.getName() + "]: despertó porque hay ítems en el búfer");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    Item item = queue.poll();
    if (item != null){
        if (item.getName().equals("FIN")) {
            this.cerrado += 1;
        }
        System.out.println("[" + thread.getName() + "]: agarró del búfer el " + item.getName());
        notifyAll();
        return item;
        }
        return null;
    }

        public synchronized void despertar(){
            this.notify = true;
            notifyAll();
        }

}