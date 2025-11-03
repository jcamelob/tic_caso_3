import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class filtro extends Thread{

    private int id;
    private buzon buzon_entrada;
    private buzon buzon_entrega;
    private buzon buzon_spam;
    private Random random = new Random();


    private static final AtomicInteger n_clientes = new AtomicInteger(0);
    private static final AtomicInteger n_fin = new AtomicInteger(0);
    private static boolean fin_entrega_enviado = false;
    private static boolean fin_cuarentena_enviado = false;

    public filtro(buzon buzon_entrada, buzon buzon_spam, buzon buzon_entrega, int id_filtro_spam){

        super("Filtro " + id_filtro_spam);
        this.id = id_filtro_spam;
        this.buzon_entrada = buzon_entrada;
        this.buzon_spam = buzon_spam;
        this.buzon_entrega = buzon_entrega;

    }

    @Override
    public void run(){

        while (true) { 
            //Obtiene un mensaje
            mensaje msj = buzon_entrada.get_msj(this);

            // buzón cerrado
            if (msj == null) { 
                break;
            }       

            //Verificamos si es spam
            if(!msj.isSpam()){

                if(msj.isInicioEmisor()){
                    n_clientes.incrementAndGet();
                } else if (msj.isFinEmisor()) {
                    n_fin.incrementAndGet();
                }   
                buzon_entrega.add_NoSpam(this, msj);
            }else {
                int tiempo = random.nextInt(10001) + 10000;
                msj.setTiempoCuarentena(tiempo);
                buzon_spam.add_Spam(this, msj);
            }

            boolean todos_finalizaron = (n_fin.get() == n_clientes.get());
            
            boolean cerrar = false;
            synchronized (filtro.class) {
                if (todos_finalizaron && !fin_entrega_enviado) {
                    fin_entrega_enviado = true;
                    fin_cuarentena_enviado = true;

                    mensaje fin_entrega = new mensaje();
                    mensaje fin_cuarentena = new mensaje();

                    buzon_entrega.add_NoSpam(this, fin_entrega);
                    buzon_spam.add_Spam(this, fin_cuarentena);
                    System.out.println("[" + getName() + "]: envió FIN a entrega y cuarentena");
                    cerrar = true;
                }
            }
            if (cerrar) {
                buzon_entrada.cerrar();
}

            
        }
        System.out.println("[" + getName() + "]: Finalizado.");
 
    }
    

    /*

    public void intentar_add_msm(mensaje msm,buzon buzon_entrega){
        boolean aceptado = false;
        while (!aceptado){
            aceptado = buzon_entrega.add_msm(msm);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
     */
    
}
