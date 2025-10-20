import java.util.LinkedList;

public class Main {

  static LinkedList<cliente_emisor> lista_emisores;
  static LinkedList<filtro> lista_filtros;
  static LinkedList<servidor_entrega> lista_servidores_entrega;
  static buzon buzon_entrada;
  static buzon buzon_cuarentena;
  static buzon buzon_entrega;
  static cuarentena cuarent = new cuarentena(buzon_cuarentena);
  static servidor_entrega entrega = new servidor_entrega(buzon_entrega);

    public static void main(String[] args) {
      run();
    }

  //todo run se tiene que paralelizar
  public static void run(){

    creacion_instancias();
    //emisores deben correr en paralelo
    for (cliente_emisor emisores : lista_emisores){
      emisores.llenar_buzon(buzon_entrada);
    }
    //filtros deben correr en paralelo
    for (filtro esteFiltro : lista_filtros){
      esteFiltro.filtrar(buzon_cuarentena, buzon_entrega);
    }
    //siguientes 2 corren al tiempo
    cuarent.consumir_buzon();
    entrega.consumir_buzon();
  }

  public static void creacion_instancias() {

    int n_clientes_emisores = 4;
      int n_mensajes = 20;
      int n_filtros = 2;
      int n_servidores_entrega = 2;
      int capacidad_buzon_entrada = 8;
      int capacidad_buzon_entrega = 10;

      buzon_entrada = new buzon(capacidad_buzon_entrada);
      buzon_cuarentena = new buzon();
      buzon_entrega = new buzon(capacidad_buzon_entrega);

      for (int i=0;i<n_clientes_emisores;i++){
        cliente_emisor cliente = new cliente_emisor(i);
        lista_emisores.add(cliente);
      }

      for (int i=0;i<n_filtros;i++){
        filtro filt = new filtro(buzon_entrada);
        lista_filtros.add(filt);
      }

      for (int i=0;i<n_servidores_entrega;i++){
        servidor_entrega servidor = new servidor_entrega(buzon_entrega);
        lista_servidores_entrega.add(servidor);
      }

  }
    
}
