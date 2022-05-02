
package optiuam.bc.modelo;

import javafx.scene.shape.Line;

/**
 * Clase Componente la cual contiene los atributos principales de un elemento
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class Componente {
    
    /**Nombre del componente*/
    protected String nombre; 
    /**Identificador del componente*/
    protected int id;
    /**Nombre del componente el cual se encuentra conectado por la entrada*/
    protected String elementoConectadoEntrada="";
    /**Indica si el componente esta conectado por la entrada*/
    protected boolean conectadoEntrada;
    /**Nombre del componente el cual se encuentra conectado por la salida*/
    protected String elementoConectadoSalida="" ;
    /**Indica si el componente esta conectado por la salida*/
    protected boolean conectadoSalida;
    /**Conexion (linea) del componente*/
    protected Line linea;
    
    /**
    * Metodo constructor sin parametros
    */
    public Componente() {
    }

    /**
     * Metodo constructor con parametros
     * @param nombre Nombre del componente
     * @param id Identificador del componente
     * @param conectadoEntrada Nombre del componente el cual se encuentra conectado por la entrada
     * @param conectadoSalida Nombre del componente el cual se encuentra conectado por la salida
     */
    public Componente(String nombre, int id, boolean conectadoEntrada, boolean conectadoSalida) {
        this.nombre = nombre;
        this.id = id;
        this.conectadoEntrada = conectadoEntrada;
        this.conectadoSalida= conectadoSalida;
    }

    /**
     * Metodo que muestra el nombre del componente
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que modifica el nombre del componente
     * @param nombre Nombre del componente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que muestra el identificador del componente
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo que modifica el nombre del componente
     * @param id Identificador del componente
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metodo que muestra el nombre del componente el cual se encuentra 
     * conectado por la entrada
     * @return elementoConectadoEntrada
     */
    public String getElementoConectadoEntrada() {
        return elementoConectadoEntrada;
    }

    /**
     * Metodo que modifica el nombre del componente el cual se encuentra 
     * conectado por la entrada
     * @param elementoConectadoEntrada Nombre del componente el cual se encuentra conectado por la entrada
     */
    public void setElementoConectadoEntrada(String elementoConectadoEntrada) {
        this.elementoConectadoEntrada = elementoConectadoEntrada;
    }

    /**
     * Metodo que muestra si el componente esta conectado por la entrada
     * @return conectadoEntrada
     */
    public boolean isConectadoEntrada() {
        return conectadoEntrada;
    }

    /**
     * Metodo que modifica si el componente esta conectado por la entrada
     * @param conectadoEntrada Indica si el componente esta conectado por la entrada
     */
    public void setConectadoEntrada(boolean conectadoEntrada) {
        this.conectadoEntrada = conectadoEntrada;
    }

    /**
     * Metodo que muestra el nombre del componente el cual se encuentra 
     * conectado por la salida
     * @return elementoConectadoSalida
     */
    public String getElementoConectadoSalida() {
        return elementoConectadoSalida;
    }

    /**
     * Metodo que modifica el nombre del componente el cual se encuentra 
     * conectado por la salida
     * @param elementoConectadoSalida Indica si el componente esta conectado por la salida
     */
    public void setElementoConectadoSalida(String elementoConectadoSalida) {
        this.elementoConectadoSalida = elementoConectadoSalida;
    }

    /**
     * Metodo que muestra si el componente esta conectado por la salida
     * @return conectadoSalida
     */
    public boolean isConectadoSalida() {
        return conectadoSalida;
    }

    /**
     * Metodo que modifica si el componente esta conectado por la salida
     * @param conectadoSalida Indica si el componente esta conectado por la salida
     */
    public void setConectadoSalida(boolean conectadoSalida) {
        this.conectadoSalida = conectadoSalida;
    }

    /**
     * Metodo que muestra la conexion (linea) del componente
     * @return linea
     */
    public Line getLinea() {
        return linea;
    }

    /**
     * Metodo que modifica la conexion (linea) del componente
     * @param linea Conexion (linea) del componente
     */
    public void setLinea(Line linea) {
        this.linea = linea;
    }
    
    /**
     * Metodo toString que retorna los atributos de un componente
     * @return nombre, id, conectadoEntrada, conectadoSalida
     */
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