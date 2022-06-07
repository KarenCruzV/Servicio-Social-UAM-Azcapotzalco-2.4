
package optiuam.bc.modelo;

import java.util.LinkedList;

/**
 * Clase Splitter la cual contiene los atributos principales de un divisor 
 * optico
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see Componente
 */
public class Splitter extends Componente {
    
    /**Numero de salidas del divisor optico*/                    
    private int salidas;
    /**Puertos salida del divisor optico*/
    private StringBuilder cSalidas;
    /**Perdida de insercion del divisor optico*/
    private double perdidaInsercion;
    /**Longitud de onda del divisor optico*/
    private int longitudOnda;
    /**Conexiones del divisor optico*/
    private LinkedList<PuertoSalida> conexiones;
    /**Identificador del divisor optico. Es diferente al identificador del componente*/
    private int idS;
    /**Posicion en el eje X del divisor opticor*/
    private double posX; 
    /**Posicion en el eje Y del divisor optico*/
    private double posY;
    
    /**
    * Metodo constructor sin parametros
    */
    public Splitter() {
        this.conexiones=new LinkedList();
    }
    
    /**
     * Metodo constructor con parametros
     * @param nombre Nombre del componente
     * @param id Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra conectado con el conector
     * @param conectado Indica si el componente esta conectado
     * @param salidas Numero de salidas del divisor optico
     * @param perdidaInsercion Perdida de insercion del divisor optico
     * @param longitudOnda Longitud de onda del divisor optico
     */
    public Splitter(String nombre, int id, String elementoConectado, boolean conectado,
            int salidas, double perdidaInsercion, int longitudOnda) {
        this.salidas = salidas;
        this.perdidaInsercion = perdidaInsercion;
        this.longitudOnda = longitudOnda;
        this.nombre = nombre;
        this.id = id;
    }

    /**
     * Metodo que muestra el numero de salidas del divisor optico
     * @return salidas
     */
    public int getSalidas() {
        return salidas;
    }
    
    /**
     * Metodo que modifica el numero de salidas del divisor optico
     * @param salidas Numero de salidas del divisor optico
     */
    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }

    /**
     * Metodo que muestra la perdida de insercion del divisor optico
     * @return perdidaInsercion
     */
    public double getPerdidaInsercion() {
        return perdidaInsercion;
    }
    
    /**
     * Metodo que modifica la perdida de insercion del divisor optico
     * @param perdidaInsercion Perdida de insercion del divisor optico
     */
    public void setPerdidaInsercion(double perdidaInsercion) {
        this.perdidaInsercion = perdidaInsercion;
    }

    /**
     * Metodo que muestra la longitud de onda del divisor optico
     * @return longitudOnda
     */
    public int getLongitudOnda() {
        return longitudOnda;
    }

     /**
     * Metodo que modifica la longitud de onda del divisor optico
     * @param longitudOnda Longitud de onda del divisor optico
     */
    public void setLongitudOnda(int longitudOnda) {
        this.longitudOnda = longitudOnda;
    }
    
    /**
     * Metodo que muestra el identificador del divisor optico, no el del componente
     * @return idS
     */
    public int getIdS() {
        return idS;
    }

    /**
     * Metodo que modifica el identificador del divisor optico, no el del componente
     * @param idS Identificador del divisor optico
     */
    public void setIdS(int idS) {
        this.idS = idS;
    }
    
    /**
     * Metodo que muestra la posicion en el eje X del divisor optico
     * @return posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion en el eje X del divisor optico
     * @param posX Posicion en el eje X del divisor optico
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Metodo que muestra la posicion en el eje Y del divisor optico
     * @return posY
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion en el eje Y del divisor optico
     * @param posY Posicion en el eje Y del divisor optico
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }

    /**
     * Metodo que muestra las conexiones del divisor optico
     * @return conexiones
     */
    public LinkedList<PuertoSalida> getConexiones() {
        return conexiones;
    }

    /**
     * Metodo que modifica las conexiones del divisor optico
     * @param conexiones Conexiones del divisor optico
     */
    public void setConexiones(LinkedList<PuertoSalida> conexiones) {
        this.conexiones = conexiones;
    }

    /**
     * Metodo que muestra la conversion de los puertos salida del divisor optico
     * a un String
     * @return salidas
     */
    public StringBuilder getcSalidas() {
        return cSalidas;
    }

    /**
     * Metodo que modifica la conversion de los puertos salida del divisor optico
     * a un String
     * @param cSalidas Conversion de puertos salida
     */
    public void setcSalidas(StringBuilder cSalidas) {
        this.cSalidas = cSalidas;
    }
    
    /**
     * Metodo que modifica las salidas del divisor optico
     * @param salidas Salidas del divisor optico
     */
    public void modificarSalidas(int salidas){
        cSalidas=new StringBuilder();
        for(int i=0;i<salidas-1; i++){
            PuertoSalida puerto= new PuertoSalida();
            conexiones.add(puerto);
            cSalidas.append(conexiones.get(i).toString());
        }
    }
    
    /**
     * Metodo que actualiza las salidas del divisor optico
     * @param salidas Salidas del divisor optico
     */
    public void actuaizarSalidas(int salidas){
        cSalidas=new StringBuilder();
        for(int i=0;i<salidas-1; i++){
            cSalidas.append(conexiones.get(i).toString());
        }
    }
    
    /**
     * Metodo toString que retorna los atributos de un divisor optico
     * @return nombre, id, conectadoEntrada, elementoConectadoEntrada, 
     * conectadoSalida, elementoConectadoSalida, salidas, 
     * perdidaInsercion, longitudOnda, idS, cSalidas
     */
    @Override
    public String toString() {
        cSalidas=new StringBuilder();
        for(int i=0;i<salidas-1; i++){
            cSalidas.append(conexiones.get(i).toString());
        }
        return super.toString()+","+salidas+","+perdidaInsercion+","+longitudOnda+","+idS+cSalidas.toString();
                
    }
    
}