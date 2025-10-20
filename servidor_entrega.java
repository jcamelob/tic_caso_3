import java.util.LinkedList;
import java.util.Random;

public class servidor_entrega {

    buzon buzon_entrada;
    LinkedList<mensaje> lista_msm;

    public servidor_entrega(buzon buzon_ent) {
        buzon_entrada = buzon_ent;
    }

    public void consumir_buzon(){
        boolean continuar = true;
        while (continuar){
            mensaje msm = buzon_entrada.queue.poll();
            continuar = consumir(msm);
        }
    }

    public boolean consumir(mensaje msm) {
        if (msm.contenido != "fin") {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(990) + 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lista_msm.add(msm);
        return true;
        }
        return false; //retorna falso si debe acabar
    }

    
}
