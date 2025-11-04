package estoy_cansado;

import java.util.Random;

public class Item {
    private int tiempoCuarentena = 0;
    private boolean spam;
    private Random random = new Random();

    private String name;

    public Item(String name) {
        this.name = name;

        int random_n = random.nextInt(3)+1; //Cambiar probabilidades

        if (random_n==3){
            if (!name.equals("FIN") && !name.equals("INICIO")){
                this.spam = true;

            }
            
        }
    }

    public String getName() {
        return name;
    }

    public void setTiempoCuarentena(int tiempo) {
        this.tiempoCuarentena = tiempo;
    }

    public void disminuir_contador(){
        this.tiempoCuarentena -= 1;
    }

    public int getTiempoCuarentena(){
        return tiempoCuarentena;
    }

    public boolean isSpam() {
        return spam;
    }
}