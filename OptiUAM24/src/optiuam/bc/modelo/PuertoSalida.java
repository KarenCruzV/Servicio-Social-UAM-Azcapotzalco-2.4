
package optiuam.bc.modelo;

import javafx.scene.shape.Line;

/**
 * Clase PuertoSalida la cual contiene los atributos principales del puerto
 * salida para un divisor optico
 * @author Arturo Borja
 * @author Karen Cruz
 */

public class PuertoSalida {
    
    /**Indica si el componente esta conectado por la salida*/
    private boolean conectadoSalida;
    /**Nombre del componente el cual se encuentra conectado por la salida*/
    private String elementoConectadoSalida;
    /**Conexion (linea) del componente*/
    private Line linea;

    /**Metodo constructor sin parametros*/
    public PuertoSalida() {
        this.conectadoSalida=false;
        this.elementoConectadoSalida="";
        
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
     * Metodo toString que retorna los atributos del puerto salida
     * @return conectadoSalida, elementoConectadoSalida
     */
    @Override
    public String toString() {
        return ","+conectadoSalida + "," + elementoConectadoSalida;
    }
    
}
