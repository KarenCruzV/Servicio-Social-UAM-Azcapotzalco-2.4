
package optiuam.bc.modelo;

/**
 * Clase Empalme la cual contiene los atributos principales de un empalme
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see Componente
 */
public class Empalme extends Componente {
    
    /**Perdida de insercion maxima*/
    private final double perdidaMaxima = .5;
    /**Tipo de empalme. 0 mechanic | 1 fusion*/
    private int tipo; 
    /**Perdida de insercion. 50 max na min mecanico | 50 max na min fusion*/
    private double perdidaInsercion;
    /**Longitud de onda. 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window*/
    private int longitudOnda;
    /**Identificador del empalme. Es diferente al identificador del componente*/
    private int idEmpalme;
    /**Posicion en el eje X del empalme*/
    private double posX; 
    /**Posicion en el eje Y del empalme*/
    private double posY;

    /**
    * Metodo constructor sin parametros
    */
    public Empalme() {
    }

    /**
     * Metodo constructor con parametros
     * @param nombre Nombre del componente
     * @param id Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra conectado con el empalme
     * @param conectado Indica si el componente esta conectado
     * @param tipo Tipo del empalme
     * @param perdidaInsercion Perdida de insercion del empalme
     * @param longitudOnda Longitud de onda del empalme
    */
    public Empalme(String nombre, int id,String elementoConectado, boolean conectado, 
            int tipo, double perdidaInsercion, int longitudOnda) {
        this.tipo = tipo;
        this.perdidaInsercion = perdidaInsercion;
        this.longitudOnda = longitudOnda;
        this.nombre = nombre;
        this.id = id;
    }
    
    /**
     * Metodo que muestra el tipo del empalme
     * @return tipo
     */
    public int getTipo() {
        return tipo;
    }
    

    /**
     * Metodo que modifica el tipo del empalme
     * @param tipo Tipo de empalme. 0 mechanic | 1 fusion
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Metodo que muestra la perdida de insercion del empalme
     * @return perdidaInsercion
     */
    public double getPerdidaInsercion() {
        return perdidaInsercion;
    }

    /**
     * Metodo que modifica la perdida de insercion del empalme
     * @param perdidaInsercion Perdida de insercion. 50 max na min mecanico | 50 max na min fusion
     */
    public void setPerdidaInsercion(double perdidaInsercion) {
        this.perdidaInsercion = perdidaInsercion;
    }

    /**
     * Metodo que muestra la longitud de onda del empalme
     * @return longitudOnda
     */
    public int getLongitudOnda() {
        return longitudOnda;
    }

    /**
     * Metodo que modifica la longitud de onda del empalme
     * @param longitudOnda Longitud de onda. 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window
     */
    public void setLongitudOnda(int longitudOnda) {
        this.longitudOnda = longitudOnda;
    }

    /**
     * Metodo que muestra la perdida de insercion maxima del empalme
     * @return perdidaMaxima
     */
    public double getPerdidaMaxima() {
        return perdidaMaxima;
    }

    /**
     * Metodo que muestra el identificador del empalme, no el del componente
     * @return idEmpalme
     */
    public int getIdEmpalme() {
        return idEmpalme;
    }

    /**
     * Metodo que modifica el identificador del empalme, no el del componente
     * @param idEmpalme Identificador del empalme
     */
    public void setIdEmpalme(int idEmpalme) {
        this.idEmpalme = idEmpalme;
    }
    
    /**
     * Metodo que muestra la posicion en el eje X del empalme
     * @return posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion en el eje X del empalme
     * @param posX Posicion en el eje X del empalme
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Metodo que muestra la posicion en el eje Y del empalme
     * @return posY
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion en el eje Y del empalme
     * @param posY Posicion en el eje Y del empalme
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }
    
    /**
     * Metodo toString que retorna los atributos de un empalme
     * @return nombre, id, conectadoEntrada, conectadoSalida, tipo, 
     * perdidaInsercion, longitudOnda, idEmpalme
     */
    @Override
    public String toString() {
        return super.toString() + "," + tipo + "," + perdidaInsercion + 
                "," + longitudOnda+","+idEmpalme;
    }
    
}