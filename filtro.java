public class filtro {

    buzon buzon_entrada;
    int n_clientes = 0;
    int n_fin = 0;
    boolean terminado = false;

    public filtro(buzon buzon_e){
        buzon_entrada = buzon_e;
    }

    public void filtrar(buzon buzon_cuarentena, buzon buzon_entrega){
        mensaje msm = buzon_entrada.get_msm();

        if (msm.contenido == "inicio"){
            n_clientes += 1;
        } else if (msm.contenido == "fin") {
            n_fin += 1;
        } else {
            if (msm.spam == true){
                buzon_cuarentena.add_msm(msm);
            } else {
                intentar_add_msm(msm, buzon_entrega);
            }
        }
        //aca toca mirar la sincronizacion
        if (n_clientes == n_fin){
            buzon_cuarentena.add_msm(msm);
            intentar_add_msm(msm, buzon_entrega);
            terminado = true;
        }
    }

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
}
