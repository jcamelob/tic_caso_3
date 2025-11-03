import java.util.LinkedList;
import java.util.Queue;

public class buzon {

    private int len_max = -1;
    private Queue<mensaje> queue;
    private boolean cerrado = false;
    
    
    public buzon(int max){
        this.len_max = max;
        this.queue = new LinkedList<>();

    }

    public buzon(){
        this.queue = new LinkedList<>();
    }

    //Interacciones con el Buzón de entrada: Espera activa

    public synchronized void add_msj(Thread thread, mensaje msj) {
        
        while (queue.size() >= len_max) {
            try {
                System.out.println("[" + thread.getName() + "]: espera porque el buzón de entrada está lleno");
                wait();
                System.out.println("[" + thread.getName() + "]: despertó porque hay espacio en el buzón de entrada");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        queue.add(msj);
        notifyAll();
        System.out.println("[" + thread.getName() + "]: guardó en el búfer de entrada el " + msj.getContenido());
    }

    public synchronized mensaje get_msj(Thread thread) {

        while (queue.isEmpty() && !cerrado) {
            try {

                System.out.println("[" + thread.getName() + "]: espera porque el buzón de entrada está vacío");
                wait();
                if (cerrado){
                    System.out.println("[" + thread.getName() + "]: despertó porque el buzón de entrada fue cerrado");
                } else {
                    System.out.println("[" + thread.getName() + "]: despertó porque hay mensajes en el buzón de entrada");
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (queue.isEmpty() && cerrado) {
            return null; // indica que el buzón se cerró
        }

        mensaje msj = queue.poll();
        if (msj != null) {
            System.out.println("[" + thread.getName() + "]: agarró del buzón de entrada el " + msj.getContenido());
            notifyAll();
        }
        return msj;
    }

    //Interacciones con el Buzón de cuarentena: Espera semi-activa
    //Como tiene capacidad ilimitada, nunca es necesario esperar que haya espacio
    public synchronized void add_Spam(Thread thread, mensaje msj) {
        /*        
        queue.add(msj);
        System.out.println("[" + thread.getName() + "]: guardó en buzón de spam el " + msj.getContenido());
        notifyAll();
         */

    }
    
    public synchronized mensaje get_Spam(Thread thread){
        /*
        mensaje msj = queue.poll();
        if (msj != null) {
            System.out.println("[" + thread.getName() + "]: agarró del buzón de cuarentena el " + msj.getContenido());
            notifyAll();
        }
        return msj;
         */
        return null;
    }

    //Interacciones con el buzón de entrega: Espera semi-activa
    public synchronized void add_NoSpam(Thread thread, mensaje msj) {
        while (queue.size() >= len_max) {
            //System.out.println("[" + thread.getName() + "]: cede su turno porque el búzon de entrega está lleno");
            Thread.yield();
        }

        queue.add(msj);
        System.out.println("[" + thread.getName() + "]: guardó en buzón de entrega el " + msj.getContenido());
        notifyAll();
    }

    public synchronized mensaje get_NoSpam(Thread thread){
        mensaje msj = queue.poll();
        if (msj != null) {
            System.out.println("[" + thread.getName() + "]: agarró del buzón de entrega el " + msj.getContenido());
            notify();
        }
        return msj;
    }

    //metodos utiles

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized boolean isCerrado() {
        return cerrado;
    }

    public synchronized void cerrar() {
        cerrado = true;
        notifyAll(); // despierta a todos los hilos bloqueados
    }

    public synchronized boolean queue_is_empty(){
        return this.queue.isEmpty();
    }


}
