
package optiuam.bc.modelo;

public class Componente {
    
    /*Attribute Declaration*/
    
    //Name and id to identify the component
    protected String nombre;
    protected int id;
    //Attributes to know if it is conectado and what it is conectado to
    protected String elementoConectadoEntrada="";
    protected boolean conectadoEntrada;
    protected String elementoConectadoSalida="" ;
    protected boolean conectadoSalida;
    
    /*Constructor*/

    public Componente() {
    }

    public Componente(String nombre, int id, boolean conectadoEntrada, boolean conectadoSalida) {
        this.nombre = nombre;
        this.id = id;
        this.conectadoEntrada = conectadoEntrada;
        this.conectadoSalida= conectadoSalida;
    }
    

    /*Getter and Setter*/
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getElementoConectadoEntrada() {
        return elementoConectadoEntrada;
    }

    public void setElementoConectadoEntrada(String elementoConectadoEntrada) {
        this.elementoConectadoEntrada = elementoConectadoEntrada;
    }

    public boolean isConectadoEntrada() {
        return conectadoEntrada;
    }

    public void setConectadoEntrada(boolean conectadoEntrada) {
        this.conectadoEntrada = conectadoEntrada;
    }

    public String getElementoConectadoSalida() {
        return elementoConectadoSalida;
    }

    public void setElementoConectadoSalida(String elementoConectadoSalida) {
        this.elementoConectadoSalida = elementoConectadoSalida;
    }

    public boolean isConectadoSalida() {
        return conectadoSalida;
    }

    public void setConectadoSalida(boolean conectadoSalida) {
        this.conectadoSalida = conectadoSalida;
    }
    
    
    /*toString*/

    @Override
    public String toString() {
        /*return "Componente{" + "nombre=" + nombre + ", id=" + id + 
                //"elementoEntrada= "+elementoConectadoEntrada+
                ", conectadoEntrada=" + conectadoEntrada + 
                //"elementoSalida= "+elementoConectadoSalida+
                ", conectadoSalida=" + conectadoSalida + '}';*/
        return nombre+","+id+","+conectadoEntrada+","+conectadoSalida;
    }
    
}