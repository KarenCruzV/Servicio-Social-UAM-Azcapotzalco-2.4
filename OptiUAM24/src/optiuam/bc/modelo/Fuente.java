
package optiuam.bc.modelo;

/**
 * Clase Fuente la cual contiene los atributos principales de una fuente
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see Componente
 */
public class Fuente extends Componente {
    
    /**Tipo de fuente. 0 laser | 1 led*/
    private int tipo; 
    /**Potencia de la fuente. 50 max na min mecanico | 50 max na min fusion*/
    private double potencia;
    /**Anchura espectral de la fuente*/
    private double anchura;
    /**Velocidad de transmision de la fuente*/
    private double velocidad;
    /**Longitud de onda de la fuente. 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window*/
    private int longitudOnda;
    /**Posicion en el eje X de la fuente*/
    private double posX; 
    /**Posicion en el eje Y de la fuente*/
    private double posY;
    /**Identificador de la fuente. Es diferente al identificador del componente*/
    private int idFuente;

    /**Para el pulso de la fuente*/
    /**Chirp*/
    private float C = 0;
    /**Amplitud*/
    private float A0 = 0;
    /**Frecuencia*/
    private float W0 = 0;
    /**Anchura*/
    private float T0 = 0;
    /**Pulso gausiano o supergausiano*/
    private float M = 0;
    
    /**
    * Metodo constructor sin parametros
    */
    public Fuente() {
    }

    /**
    * Metodo constructor con parametros
     * @param nombre Nombre del componente
     * @param id Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra conectado la fuente
     * @param conectado Indica si el componente esta conectado
     * @param tipo Tipo de fuente
     * @param potencia Potencia de la fuente
     * @param anchura Anchura espectral de la fuente
     * @param velocidad Velocidad de transmision de la fuente
     * @param longitudOnda Longitud de onda de la fuente
    */
    public Fuente(String nombre, int id, String elementoConectado, boolean conectado,
            int tipo, double potencia, double anchura, double velocidad, int longitudOnda) {
        this.tipo = tipo;
        this.potencia = potencia;
        this.anchura = anchura;
        this.velocidad = velocidad;
        this.longitudOnda = longitudOnda;
        this.nombre = nombre;
        this.id = id;
    }
    
    /**
     * Metodo que muestra el tipo de la fuente
     * @return tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Metodo que modifica el tipo de la fuente
     * @param tipo Tipo de fuente. 0 laser | 1 led
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Metodo que muestra la potencia de la fuente
     * @return potencia
     */
    public double getPotencia() {
        return potencia;
    }

    /**
     * Metodo que modifica la potencia de la fuente
     * @param potencia 50 max na min mecanico | 50 max na min fusion
     */
    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }

    /**
     * Metodo que muestra la anchura espectral de la fuente
     * @return anchura
     */
    public double getAnchura() {
        return anchura;
    }

    /**
     * Metodo que modifica la anchura espectral de la fuente
     * @param anchura Anchura espectral de la fuente
     */
    public void setAnchura(double anchura) {
        this.anchura = anchura;
    }

    /**
     * Metodo que muestra la velocidad de transmision de la fuente
     * @return velocidad
     */
    public double getVelocidad() {
        return velocidad;
    }

    /**
     * Metodo que modifica la velocidad de transmision de la fuente
     * @param velocidad Velocidad de transmision de la fuente
     */
    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Metodo que muestra la longitud de onda de la fuente
     * @return longitudOnda
     */
    public int getLongitudOnda() {
        return longitudOnda;
    }

    /**
     * Metodo que modifica la longitud de onda de la fuente
     * @param longitudOnda Longitud de onda de la fuente. 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window
     */
    public void setLongitudOnda(int longitudOnda) {
        this.longitudOnda = longitudOnda;
    }

    /**
     * Metodo que muestra el chirp del pulso de la fuente
     * @return C
     */
    public float getC() {
        return C;
    }

    /**
     * Metodo que muestra la amplitud del pulso de la fuente
     * @return A0
     */
    public float getA0() {
        return A0;
    }

    /**
     * Metodo que muestra la frecuencia del pulso de la fuente
     * @return W0
     */
    public float getW0() {
        return W0;
    }

    /**
     * Metodo que muestra la anchura del pulso de la fuente
     * @return T0
     */
    public float getT0() {
        return T0;
    }

    /**
     * Metodo que muestra si el pulso de la fuente es gausiano o supergausiano
     * @return M
     */
    public float getM() {
        return M;
    }
    
    /**
     * Metodo que modifica los parametros del pulso de la fuente
     * @param A0 Amplitud
     * @param T0 Frecuencia
     * @param W0 Anchura
     * @param C Chirp
     * @param M Pulso gausiano o supergausiano
     */
    public void setPulso(float A0, float T0, float W0, float C, float M){
        this.C = C; 
        this.A0 = A0;
        this.W0 = W0;
        this.T0 = T0;
        this.M = M;
    }

    /**
     * Metodo que muestra el identificador de la fuente, no el del componente
     * @return idFuente
     */
    public int getIdFuente() {
        return idFuente;
    }

    /**
     * Metodo que modifica el identificador de la fuente, no el del componente
     * @param idFuente Identificador de la fuente
     */
    public void setIdFuente(int idFuente) {
        this.idFuente = idFuente;
    }
    
    /**
     * Metodo que muestra la posicion en el eje X de la fuente
     * @return posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion en el eje X de la fuente
     * @param posX Posicion en el eje X de la fuente
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Metodo que muestra la posicion en el eje Y de la fuente
     * @return posY
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion en el eje Y de la fuente
     * @param posY posicion en el eje Y de la fuente
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }
    
    /**
     * Metodo toString que retorna los atributos de una fuente
     * @return nombre, id, conectadoEntrada, conectadoSalida, tipo, potencia, 
     * anchura, velocidad, longitudOnda, idFuente
     */
    @Override
    public String toString() {
        return super.toString() +","+ tipo + "," + potencia + "," + anchura + "," 
                + velocidad + ",longitud de onda_" + longitudOnda+","/* + ", a0= " + A0 + ",t0= " + T0 + ",w0=" 
                + W0 + ",c= " + C + ",M= " + M+","*/+idFuente;
    }
    
}