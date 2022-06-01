
package optiuam.bc.modelo;

/**
 * Clase MedidorPotencia la cual contiene los atributos principales de un medidor de potencia
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see Componente
 */
public class MedidorPotencia extends Componente {
    
    /**Sensibilidad del medidor de potencia*/
    private Double sensibilidad = 0.0;
    /**Identificador del medidor de potencia. Es diferente al identificador del componente*/
    private int idPotencia;

    /**
    * Metodo constructor sin parametros
    */
    public MedidorPotencia() {
    }
    
    /**
    * Metodo constructor con parametros
     * @param nombre Nombre del componente
     * @param id Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra conectado con el medidor de potencia
     * @param conectado Indica si el componente esta conectado
    */
    public MedidorPotencia(String nombre, int id,String elementoConectado, boolean conectado) {
        this.nombre = nombre;
        this.id = id;
    }
    
    /**
     * Metodo que muestra la sensibilidad del medidor de potencia
     * @return sensibilidad
     */
    public Double getSensibilidad() {
        return sensibilidad;
    }

    /**
     * Metodo que modifica la sensibilidad del medidor de potencia
     * @param sensibilidad Sensibilidad del medidor de potencia
     */
    public void setSensibilidad(Double sensibilidad) {
        this.sensibilidad = sensibilidad;
    }
    
    /**
     * Metodo que muestra el identificador del medidor de potencia, no el del componente
     * @return idPotencia
     */
    public int getIdPotencia() {
        return idPotencia;
    }

    /**
     * Metodo que modifica el identificador del medidor de potencia, no el del componente
     * @param idPotencia Identificador del medidor de potencia
     */
    public void setIdPotencia(int idPotencia) {
        this.idPotencia = idPotencia;
    }
    
    /**
     * Metodo toString que retorna los atributos del medidor de potencia
     * @return nombre, id, conectadoEntrada, conectadoSalida, idPotencia
     */
    @Override
    public String toString() {
        return super.toString()+","+idPotencia;
    }
    
}