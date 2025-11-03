package estoy_cansado;

class Filter extends Thread {

    private static int totalToConsume;
    private static int consumedCount = 0;
    private Buffer buffer;
    private Buffer entrega;

    public Filter(String name, Buffer buffer, Buffer entrega) {
        super(name);
        this.buffer = buffer;
        this.entrega = entrega;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (Filter.class) {
                if (consumedCount >= totalToConsume) break;
            }
            Item item = buffer.get(this);
        if (item.getName().equals("FIN")) {
            entrega.put(this, item); // reenvía el fin a los consumidores
            break;
        }
        entrega.put(this, item);
    }
    System.out.println("[" + this.getName() + "]: finalizado");
    }

    public static void setTotalToConsume(int total) {
        totalToConsume = total;
    }
}