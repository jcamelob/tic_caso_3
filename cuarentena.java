import java.util.Random;

public class cuarentena extends Thread {

    private buzon buzon_spam;
    private buzon buzon_entrega;
    private Random random = new Random();

    public cuarentena(buzon buzon_spam, buzon buzon_entrega) {
        super("Manjeador de cuarentena");
        this.buzon_spam = buzon_spam;
        this.buzon_entrega = buzon_entrega;
    }

    @Override
    public void run() {
        /* 
        while (true) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            mensaje msj = buzon_spam.get_Spam(this);
            
            if (msj == null) {
                System.out.println("[" + this.getName() + "]: cede su turno porque el buzón de spam está vacío");
                Thread.yield();
                continue;
            }

            System.out.println("[" + getName() + "]: se comienza a procesar el " + msj.getContenido());

            if (msj.isFinFiltro()) {
                System.out.println("[" + getName() + "]: mensaje de fin de filtro recibido. ");
                Thread.yield();
                break;
            }

            int descartar = random.nextInt(4);
            if (descartar % 7 == 0) {
                System.out.println("[" + getName() + "]: se descarta el " + msj.getContenido() + " pues se detectó que era malicioso");
                Thread.yield();
                continue;
            }
            
            // Decrementa contador de tiempo
            msj.disminuir_contador();

            if (msj.getTiempoCuarentena() <= 0) {
                // Si el contador llega a 0 se mueve al buzón de entrega 
                buzon_entrega.add_NoSpam(this, msj);
                System.out.println("[" + getName() + "]: se mueve al buzón de entrega el " + msj.getContenido());
            } else {
                // Si aún tiene tiempo se reinserta al final del buzón de spam
                buzon_spam.add_Spam(this, msj);
                System.out.println("[" + getName() + "]: se mueve al buzón de spam el " + msj.getContenido() + ". Tiempo restante de cuarentena: " + msj.getTiempoCuarentena());
            }

            Thread.yield();

        }
        */
        System.out.println("[" + this.getName() + "]: finalizado");
    }

    /* 
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

    */

    
}
