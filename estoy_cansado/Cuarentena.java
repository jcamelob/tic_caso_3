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
        if (item.getName().equals("FINCUARENTENA")){
        notifyAll();
        }
    }

    public synchronized void putFin(Thread thread, Item item) {
        queue.add(item);
        System.out.println("[" + thread.getName() + "]: notificó a la cuarentena del fin de los items del filtro " + item.getName());
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized Item get(Thread thread) {
        Item item = queue.poll();
        if (item != null){
        System.out.println("[" + thread.getName() + "]: agarró del buzón de entrega el " + item.getName());
        //notifyAll();
        }
        return item;
    }

    public int get_len(){
        return this.queue.size();
    }
}