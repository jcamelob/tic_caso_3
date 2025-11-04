package estoy_cansado;

import java.util.LinkedList;
import java.util.Queue;

public class Cuarentena {

    private Queue<Item> queue;
    private int cerrado = 0;

    public Cuarentena() {
        this.queue = new LinkedList<>();
        
    }

    public synchronized void put(Thread thread, Item item) {
        queue.add(item);
        System.out.println("[" + thread.getName() + "]: guardó en la cuarentena el " + item.getName());
        notifyAll();
    }

    public synchronized void putFin(Thread thread, Item item) {
        queue.add(item);
        notifyAll();
        System.out.println("[" + thread.getName() + "]: notificó a la cuarentena del fin de los items del filtro " + item.getName());
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized Item get(Thread thread) {
        while (queue.isEmpty()) {
        try {
            System.out.println("[" + thread.getName() + "]: espera porque el buzón de cuarentena está vacío");
            wait();
            System.out.println("[" + thread.getName() + "]: despertó porque hay ítems en el buzón de cuarentena");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    Item item = queue.poll();
    System.out.println("[" + thread.getName() + "]: agarró del buzón de entrega el " + item.getName());
    notifyAll();
    return item;
    }

    public int get_len(){
        return this.queue.size();
    }
}