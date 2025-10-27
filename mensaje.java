import java.util.Random;

public class mensaje {

    private int id;
    private boolean spam = false;
    private boolean startEmisor = false;
    private boolean finEmisor = false;
    private boolean finFiltro = false;
    private String contenido;
    private int tiempoCuarentena = 0;

    //mensajes normales
    public mensaje(int id_cliente_emisor,int n_mensaje) {

        Random random = new Random();
        if (random.nextInt(4)==3){
            spam = true;
        }

        this.id = Integer.parseInt(Integer.toString(id_cliente_emisor)+Integer.toString(n_mensaje));

        this.contenido = "Mensaje "+ Integer.toString(id);

    }

    //Mensajes de inicio y fin usados por los clientes emisores
    public mensaje(boolean inicio, int id_cliente_emisor) {

        if (inicio){
            this.startEmisor = true;
            this.contenido = "Mensaje de inicio del emisor " + Integer.toString(id_cliente_emisor);
        } else {
            this.finEmisor = true;
            this.contenido = "Mensaje de fin del emisor " + Integer.toString(id_cliente_emisor);
        }
    }

    //Mensajes de fin usados por los filtros
    public mensaje() {
        this.finFiltro = true;
        this.contenido = "Mensaje de fin de filtros";
    }


    public String getContenido() {
        return contenido;
    }

    public boolean isSpam() {
        return spam;
    }

    public boolean isInicioEmisor() {
        return startEmisor;
    }

    public boolean isFinEmisor() {
        return finEmisor;
    }

    public boolean isFinFiltro() {
        return finFiltro;
    }

    public void setTiempoCuarentena(int tiempo) {
        this.tiempoCuarentena = tiempo;
    }


    /*

    

    public void disminuir_contador(){
        contador -= 1;
    }

     */
    
    
}
