
package optiuam.bc.modelo;

/**
 * Clase Fibra la cual contiene los atributos principales de una fibra
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see Componente
 */
public class Fibra extends Componente {
    
    /**Longitud de onda. 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window*/
    private int longitudOnda;
    /*Modo de la fibra. 0 monomodo | 1 multimodo*/
    private int modo;
    /**Tipo de fibra. 0 smf28 | 1 mm50 | 2 otro*/
    private int tipo;
    /**Longitud del cable de fibra optica*/
    private double longitud_km;
    /**Dispersion. 
     * smf28 1310 = 0 1550 = 18;
     * mm50 1310 = 3.5;
     * otro cualquier valor para ambas ondas*/
    private double dispersion; 
    /**Atenuacion. 
     * for smf28 0.32 1310 nm window | 0.18 1550 nm window db/km;
     * for mm50 1550 menor o igual a 0.36 dB/KM */            
    private double atenuacion; 
    /**Identificador de la fibra. Es diferente al identificador del componente*/                           
    private int idFibra;
    /**Posicion en el eje X de la fibra*/
    private double posX; 
    /**Posicion en el eje Y de la fibra*/
    private double posY;

    /**
    * Metodo constructor sin parametros
    */
    public Fibra() {
    }

    /**
    * Metodo constructor con parametros
     * @param nombre Nombre del componente
     * @param id Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra conectado con la fibra
     * @param conectado Indica si el componente esta conectado
     * @param longitudOnda Longitud de onda de la fibra
     * @param modo Modo de la fibra
     * @param tipo Tipo de la fibra
     * @param longitud_km Longitud del cable de fibra optica
     * @param atenuacion Atenuacion de la fibra
     * @param dispersion Dispersion de la fibra
    */
    public Fibra(String nombre,int id, String elementoConectado, boolean conectado, 
            int longitudOnda, int modo, int tipo, double longitud_km, double atenuacion, 
            double dispersion) {
        this.longitudOnda = longitudOnda;
        this.modo = modo;
        this.tipo = tipo;
        this.longitud_km = longitud_km;
        this.dispersion = dispersion;
        this.atenuacion = atenuacion;
        this.nombre = nombre;
        this.id = id;
    }

    /**
     * Metodo que muestra el identificador de la fibra, no el del componente
     * @return idFibra
     */
    public int getIdFibra() {
        return idFibra;
    }

    /**
     * Metodo que modifica el identificador de la fibra, no el del componente
     * @param idFibra Identificador de la fibra
     */
    public void setIdFibra(int idFibra) {
        this.idFibra = idFibra;
    }

    /**
     * Metodo que muestra la longitud de onda de la fibra
     * @return longitudOnda
     */
    public int getLongitudOnda() {
        return longitudOnda;
    }

    /**
     * Metodo que modifica la longitud de onda de la fibra
     * @param longitudOnda Longitud de onda. 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window
     */
    public void setLongitudOnda(int longitudOnda) {
        this.longitudOnda = longitudOnda;
    }

    /**
     * Metodo que muestra el modo de la fibra
     * @return modo
     */
    public int getModo() {
        return modo;
    }

    /**
     * Metodo que modifica el modo de la fibra
     * @param modo Modo de la fibra. 0 monomodo | 1 multimodo
     */
    public void setModo(int modo) {
        this.modo = modo;
    }

    /**
     * Metodo que muestra el tipo de la fibra
     * @return tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Metodo que modifica el tipo de la fibra
     * @param tipo Tipo de fibra. 0 smf28 | 1 mm50 | 2 otro
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Metodo que muestra la longitud del cable de fibra optica
     * @return longitud_km
     */
    public double getLongitud_km() {
        return longitud_km;
    }

    /**
     * Metodo que modifica la longitud del cable de fibra optica
     * @param longitud_km Longitud del cable de fibra optica
     */
    public void setLongitud_km(double longitud_km) {
        this.longitud_km = longitud_km;
    }

    /**
     * Metodo que muestra la dispersion de la fibra
     * @return dispersion
     */
    public double getDispersion() {
        return dispersion;
    }

    /**
     * Metodo que modifica la dispersion de la fibra
     * @param dispersion Dispersion. smf28 1310 = 0 1550 = 18; mm50 1310 = 3.5; otro cualquier valor para ambas ondas
     */
    public void setDispersion(double dispersion) {
        this.dispersion = dispersion;
    }

    /**
     * Metodo que muestra la atenuacion de la fibra
     * @return atenuacion
     */
    public double getAtenuacion() {
        return atenuacion;
    }

    /**
     * Metodo que modifica la atenuacion de la fibra
     * @param atenuacion Atenuacion. for smf28 0.32 1310 nm window | 0.18 1550 nm window db/km; for mm50 1550 menor o igual a 0.36 dB/KM
     */
    public void setAtenuacion(double atenuacion) {
        this.atenuacion = atenuacion;
    }
    
    /**
     * Metodo que muestra la posicion en el eje X de la fibra
     * @return posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion en el eje X de la fibra
     * @param posX Posicion en el eje X de la fibra
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Metodo que muestra la posicion en el eje Y de la fibra
     * @return posY
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion en el eje Y de la fibra
     * @param posY Posicion en el eje Y de la fibra
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }
    
    /**
     * Metodo toString que retorna los atributos de una fibra
     * @return nombre, id, conectadoEntrada, elementoConectadoEntrada, 
     * conectadoSalida, elementoConectadoSalida, longitudOnda, modo,
     * tipo, longitud_km, dispersion, atenuacion, idFibra
     */
    @Override
    public String toString() {
        return super.toString() +  "," + longitudOnda + "," + modo + "," 
                + tipo + "," + longitud_km + "," + dispersion + ","+ atenuacion+","+idFibra;
    }
    
}