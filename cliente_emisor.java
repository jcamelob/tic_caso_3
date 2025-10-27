
public class cliente_emisor extends Thread {

    private int id;
    private int n_correos;
    private int correos_enviados = -1;
    private buzon buzonEntrada;

    public cliente_emisor(buzon buzon_entrada, int id_cliente_emisor, int n_correos){

        super("Emisor " + id_cliente_emisor);
        this.id = id_cliente_emisor;
        this.buzonEntrada = buzon_entrada;
        this.n_correos = n_correos;
    } 


    @Override
    public void run(){
        while (true) {
            mensaje msj = produce();
            if (msj == null){
                break;
            }
            buzonEntrada.add_msj(this, msj);
        }
        System.out.println("[" + this.getName()+"]: Finalizado");
    }

    private mensaje produce(){
        int msjId = 0;
        synchronized (cliente_emisor.class) {
            //El primer correo es el de inicio
            if (correos_enviados ==-1){
                correos_enviados++;
                mensaje msjInicio = new mensaje(true, id);
                System.out.println("[" + this.getName() + "]: envió el " + msjInicio.getContenido());
                return msjInicio;
            }
            //El ultimo correo es el de fin 
            else if(correos_enviados == n_correos){
                correos_enviados++;
                mensaje msjFin = new mensaje(false, id);
                System.out.println("[" + this.getName() + "]: envió el " + msjFin.getContenido());
                return msjFin;
            } 
            // configurar id de un correo normal
            else if (correos_enviados < n_correos){
                correos_enviados++;
                msjId =  correos_enviados;
            }
        }
        //enviar un correo  normal
        if (msjId != 0){
            
            mensaje msj = new mensaje(id, msjId);
            System.out.println("[" + this.getName() + "]: envió el " + msj.getContenido());
            return msj;
        }
        return null;
    }

    /*

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

     */
    
}
