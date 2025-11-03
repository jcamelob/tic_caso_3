package estoy_cansado;

import java.util.Random;

class Consumer extends Thread {

    private static int totalToConsume;
    private static int consumedCount = 0;
    private Entrega buffer;
    private Random random;

    public Consumer(String name, Entrega buffer) {
        super(name);
        this.buffer = buffer;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            Item item = buffer.get(this);

            if (item.getName().equals("FIN")) {
                System.out.println("[" + this.getName() + "]: recibió FIN y termina.");
                // Importante: reenviar FIN para que otros consumidores también paren
                buffer.put(this, item);
                break;
            }

            process(item);
        }
    System.out.println("[" + this.getName() + "]: finalizado");
    }


    private void process(Item item) {
        try {
            Thread.sleep(random.nextInt(2000) + 1000);
            System.out.println("[" + this.getName() + "]: procesó el " + item.getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        synchronized (Consumer.class) {
            consumedCount++;
        }
    }

    public static void setTotalToConsume(int total) {
        totalToConsume = total;
    }
}