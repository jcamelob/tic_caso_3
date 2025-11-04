package estoy_cansado;

import java.util.Random;

class Filter extends Thread {

    private static int totalToConsume;
    private static int finCount = 0;
    private static int finFiltroCount = 0;
    private Buffer buffer;
    private Entrega entrega;
    private Cuarentena cuarentena;
    private static int n_mensajes_fin;
    private static int n_filtros;
    private Random random = new Random();

    public Filter(String name, Buffer buffer, Entrega entrega, Cuarentena cuarentena, int n_mensajes_fin, int n_filtros) {
        super(name);
        this.buffer = buffer;
        this.entrega = entrega;
        this.cuarentena = cuarentena;
        Filter.n_mensajes_fin = n_mensajes_fin;
        Filter.n_filtros = n_filtros;
        
    }

    @Override
    public void run() {
        while (true) {
            synchronized (Filter.class) {
                if (finCount >= n_mensajes_fin) break;
            }
            Item item = buffer.get(this);
            if (item != null){
            if (item.getName().equals("FIN")) {
                //entrega.put(this, item); // reenvía el fin a los consumidores
                synchronized (Filter.class) {
                    finCount ++; //aumentamos el conteo de fin en 1
                }
            }
            if (item.isSpam()){
                item.setTiempoCuarentena(random.nextInt(11)+10);
                cuarentena.put(this, item);
            } else {
                entrega.put(this, item);
            }
        }
        }
        System.out.println("[" + this.getName() + "]: finalizado");
        //buffer.esperar_final();
        synchronized (Filter.class) {
            finFiltroCount ++;
            buffer.despertar();
            if (finFiltroCount >= n_filtros){
                cuarentena.putFin(this, new Item("FINFILTRO"));
                entrega.put(this, new Item("FINFILTRO"));
            }
        }
        System.out.println("["+this.getName()+"]: Ha finalizado");
    }

    public static void setTotalToConsume(int total) {
        totalToConsume = total;
    }
}