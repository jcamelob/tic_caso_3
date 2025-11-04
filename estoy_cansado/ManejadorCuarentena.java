package estoy_cansado;

import java.util.Random;

class ManejadorCuarentena extends Thread {

    private static int totalToConsume;
    private static int finCount = 0;
    private static int finFiltroCount = 0;
    private Cuarentena cuarentena;
    private Entrega entrega;
    private boolean destruir = false;
    private Random random = new Random();

    public ManejadorCuarentena(String name, Cuarentena cuarentena, Entrega entrega) {
        super(name);
        this.cuarentena = cuarentena;
        this.entrega = entrega;
    }

    @Override
    public void run() {
        while (true) {

            destruir = false;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            Item item = cuarentena.get(this);
            
            if (item.getName().equals("FINFILTRO")) {
                if (cuarentena.get_len() == 0){
                    cuarentena.put(this, new Item("FINCUARENTENA"));
                    System.out.println("[" + this.getName() + "]: recibió mensaje de fin del filtro y termina.");
                    break;
                } else {
                    cuarentena.put(this,item);
                    continue;
                }
                
            }
            
            procesar(item);

            if (!destruir){

                if (item.getTiempoCuarentena() == 0){
                entrega.put(this, item);
                System.out.println("[" + getName() + "]: se mueve al buzón de entrega el " + item.getName());
                
                }
                else {
                    cuarentena.put(this, item);
                    System.out.println("[" + getName() + "]: se mueve al buzón de spam el " + item.getName() + ". Tiempo restante de cuarentena: " + item.getTiempoCuarentena());
                }
            }else{
                System.out.println("[" + getName() + "]: se descarta el " + item.getName() + " pues se detectó que era malicioso");

            }
            
            
        }
        System.out.println("[" + this.getName() + "]: finalizado");
    }

    private void procesar(Item item){
        item.disminuir_contador();
        if (random.nextInt(22)%7 == 0){
            this.destruir = true;
        }
    }
}