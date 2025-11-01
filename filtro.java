import java.util.Random;

public class filtro extends Thread{

    private int id;
    private buzon buzon_entrada;
    private buzon buzon_entrega;
    private buzon buzon_spam;
    private Random random = new Random();


    private static int n_clientes = 0;
    private static int n_fin = 0;
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
                    synchronized (filtro.class) {
                    n_clientes++;
                    }
                } else if (msj.isFinEmisor()) {
                    synchronized (filtro.class) {
                    n_fin++;
                    }
                }   
                buzon_entrega.add_NoSpam(this, msj);
            }else {
                int tiempo = random.nextInt(10001) + 10000;
                msj.setTiempoCuarentena(tiempo);
                buzon_spam.add_Spam(this, msj);
            }

            // Verificar si se deben enviar mensajes de fin globales/terminar ejecucion del thread
            synchronized (filtro.class) {
                boolean todos_finalizaron = (n_fin == n_clientes);
                boolean entradas_vacias = buzon_entrada.isEmpty() && buzon_spam.isEmpty();

                if (todos_finalizaron && entradas_vacias && !fin_entrega_enviado && !fin_cuarentena_enviado) {
                    fin_entrega_enviado = true;
                    fin_cuarentena_enviado = true;
                    mensaje fin_entrega = new mensaje();
                    mensaje fin_cuarentena = new mensaje();

                    //Envía mensaje de fin a entrega
                    buzon_entrega.add_NoSpam(this, fin_entrega);
                    System.out.println("[" + getName() + "]: envió " + fin_entrega.getContenido() + " a buzón de entrega ");
                    buzon_spam.add_Spam(this, fin_cuarentena);

                    //Envía mensaje de fin a cuarentena
                    System.out.println("[" + getName() + "]: envió " + fin_entrega.getContenido() + " a buzón de cuarentena ");

                    buzon_entrada.cerrar();
                    //buzon_spam.cerrar();
                }

                // condición de parada del filtro
                if (todos_finalizaron && fin_entrega_enviado && fin_cuarentena_enviado) {
                    break;
                }
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
