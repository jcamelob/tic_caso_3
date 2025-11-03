import java.util.Random;

public class servidor_entrega extends Thread {
    
    private final int id; 
    private final buzon buzonEntrega;
    private final Random random = new Random();


    public servidor_entrega(buzon buzon_entrega, int id_servidor_entrega) {
        super("Servidor de entrega "+ id_servidor_entrega);
        this.id = id_servidor_entrega;
        this.buzonEntrega = buzon_entrega;
    }

    @Override
    public void run(){
        System.out.println("[" + this.getName()+"]: Inicializado");
        while (!buzonEntrega.isCerrado()) { //aca
            while (buzonEntrega.queue_is_empty()){
                try {
                    this.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mensaje msj = buzonEntrega.get_NoSpam(this);
            if (msj != null){
                process(msj);
            }
        }
        System.out.println("[" + this.getName()+"]: Finalizado");
    }

    private void process(mensaje msj){
        try {
            Thread.sleep(random.nextInt(2000)+1000);
            System.out.println("[" + this.getName() + "]: procesó el " + msj.getContenido());
        } catch (InterruptedException  e) {
            Thread.currentThread().interrupt();
        }

        //Validar si era mensaje de FIN 
        if (msj.isFinFiltro()){
            buzonEntrega.cerrar();
        }
    }
    
}
