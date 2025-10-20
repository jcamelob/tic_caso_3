import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

public class cliente_emisor {

    int id;
    int n_correos;
    Queue<mensaje> mensajes = new LinkedList<>();


    public cliente_emisor(int id_cliente_emisor){

        id = id_cliente_emisor;

        Random random = new Random();

        int min = 20;
        int max = 100;
        n_correos = random.nextInt(max - min + 1) + min;

        mensajes.add(new mensaje(true));

        for (int i = 1; i <= n_correos; i++) {
            mensajes.add(new mensaje(id,i));
        }

        mensajes.add(new mensaje(false));

    }

    public void llenar_buzon(buzon buzon_entrada){
        for (int i = 0; i <= n_correos; i++){
            intentar_add_msm(mensajes.poll(),buzon_entrada);
        }
    }

    public void intentar_add_msm(mensaje msm,buzon buzon_entrada){
        boolean aceptado = false;
        while (!aceptado){
            aceptado = buzon_entrada.add_msm(msm);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    
}
