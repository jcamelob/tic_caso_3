import java.util.Random;

public class mensaje {

    int id;
    boolean spam = false;
    String contenido;
    int contador;

    public mensaje(int id_cliente_emisor,int n_mensaje) {

        Random random = new Random();
        if (random.nextInt(3)==3){
            spam = true;
        }

        id = Integer.parseInt(Integer.toString(id_cliente_emisor)+Integer.toString(n_mensaje));

        contenido = "texto";

    }

    public mensaje(boolean inicio) {

        if (inicio){
            contenido = "inicio";
        } else {
            contenido = "fin";
        }

        contenido = "texto";

    }

    public void set_contador(int tiempo) {
        contador = tiempo;
    }

    public void disminuir_contador(){
        contador -= 1;
    }
    
}
