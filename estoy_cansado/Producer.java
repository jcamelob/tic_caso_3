package estoy_cansado;
public class Producer extends Thread {

private static int totalToProduce;
private static int producedCount = 0;
private Buffer buffer;

public Producer(String name, Buffer buffer) {
    super(name);
    this.buffer = buffer;
}

@Override
public void run() {
    buffer.put(this, new Item("INICIO"));
    while (true) {
        Item item = produce();
        if (item == null) {
            break;
        }
        buffer.put(this, item);
    }
    System.out.println("[" + this.getName() + "]: finalizado");
    buffer.put(this, new Item("FIN"));
}

private Item produce() {
    String itemName = null;
    synchronized(Producer.class) {
        if (producedCount < totalToProduce) {
            producedCount++;
            itemName = "ítem " + producedCount;
        }
    }
    if (itemName != null) {
        Item item = new Item(itemName);
        System.out.println("[" + this.getName() + "]: produjo el " + item.getName());
        return item;
    }
    return null;
}

public static void setTotalToProduce(int total) {
    totalToProduce = total;
}
}
