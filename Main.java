
public class Main {
  static buzon buzon_entrada;
  static buzon buzon_cuarentena;
  static buzon buzon_entrega;

    public static void main(String[] args) {
      
      System.out.println("=== INICIO DEL SISTEMA DE MENSAJERÍA ===\n");
      run();
      System.out.println("=== FIN DEL SISTEMA DE MENSAJERÍA ===\n");

    }

  //todo run se tiene que paralelizar
  public static void run(){

    creacion_instancias();

  }

  public static void creacion_instancias() {

    int n_clientes_emisores = 2;
    int n_mensajes = 3;
    int n_filtros = 2;
    int n_servidores_entrega = 2;
    int capacidad_buzon_entrada = 4;
    int capacidad_buzon_entrega = 10;

    buzon_entrada = new buzon(capacidad_buzon_entrada);
    buzon_cuarentena = new buzon();
    buzon_entrega = new buzon(capacidad_buzon_entrega);

    cliente_emisor[] emisores = new cliente_emisor[n_clientes_emisores];
    for (int i=0;i<n_clientes_emisores;i++){
      emisores[i] = new cliente_emisor(buzon_entrada, i+1, n_mensajes);
      emisores[i].start();
    }

    filtro[] filtros = new filtro[n_filtros];
    for (int i=0;i<n_filtros;i++){
      filtros[i] = new filtro(buzon_entrada, buzon_cuarentena, buzon_entrega, i+1);
      filtros[i].start();
    }
    
    cuarentena spam = new cuarentena(buzon_cuarentena, buzon_entrega);
    spam.start();

    //TODO servidores de entrega

    
    servidor_entrega[] servidores = new servidor_entrega[n_servidores_entrega];
    for (int i=0;i<n_servidores_entrega;i++){
      servidores[i] = new servidor_entrega(buzon_entrega, i+1);
      servidores[i].start();
    }
    


     //TODO fin de la ejecucion 
     try { 
            for (filtro f: filtros){
                f.join();
            }

            for (servidor_entrega s: servidores){
                s.join();
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    

  }
    
}
