package estoy_cansado;
public class Producer extends Thread {

private int totalToProduce;
private int producedCount = 0;
private Buffer buffer;

public Producer(String name, Buffer buffer, int totalToProduce) {
    super(name);
    this.buffer = buffer;
    this.totalToProduce = totalToProduce;
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

public void setTotalToProduce(int total) {
    this.totalToProduce = total;
}
}
