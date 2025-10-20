import java.util.LinkedList;
import java.util.Random;

public class cuarentena {

    buzon buzon_entrada;
    buzon buzon_salida;
    LinkedList<mensaje> lista_msm;
    LinkedList<mensaje> lista_retorno;

    public cuarentena(buzon buzon_ent) {
        buzon_entrada = buzon_ent;
    }

    public void consumir_buzon(){
        boolean continuar = true;
        while (continuar){
            mensaje msm = buzon_entrada.queue.poll();
            continuar = consumir(msm);
        }
    }

    public boolean consumir(mensaje msm) { //esto debe ser cada segundo cuando se llama 
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (msm.contenido != "fin") {
        Random random = new Random();
        msm.set_contador(random.nextInt(5) + 5);
        lista_msm.add(msm);
        bajar_contador();
        return true;
        }
        return false; //retorna falso si debe acabar
    }

    public void bajar_contador() {
        lista_retorno = new LinkedList<>();
        for (int i = 1; i <= lista_msm.size(); i++) {
            mensaje msm = lista_msm.get(i);
            msm.disminuir_contador();
            Random random = new Random();
            int malicioso = random.nextInt(21) + 1;
            if (malicioso % 7 == 0){
                lista_msm.remove();
            } else if (msm.contador == 0) {
                lista_retorno.add(lista_msm.remove());
            }
        }
        llenar_buzon();
    }

    public void llenar_buzon(){
        for (int i = 0; i <= lista_retorno.size(); i++){
            intentar_add_msm(lista_retorno.poll());
        }
    }

    public void intentar_add_msm(mensaje msm){
        boolean aceptado = false;
        while (!aceptado){
            aceptado = buzon_salida.add_msm(msm);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();    
            }
        }
    }

    
}
