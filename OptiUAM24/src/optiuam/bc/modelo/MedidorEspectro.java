
package optiuam.bc.modelo;

/**
 * Clase MedidorEspectro la cual contiene los atributos principales de un medidor de espectros
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see Componente
 */
public class MedidorEspectro extends Componente {
    
    /**Identificador del medidor de espectros. Es diferente al identificador del componente*/
    private int idEspectro;

    /**
    * Metodo constructor sin parametros
    */
    public MedidorEspectro() {
    }
    
    /**
    * Metodo constructor con parametros
     * @param nombre Nombre del componente
     * @param id Identificador del componente
     * @param elementoConectado Nombre del componente el cual se encuentra conectado con el medidor de espectros
     * @param conectado Indica si el componente esta conectado
    */
    public MedidorEspectro(String nombre, int id, String elementoConectado, 
            boolean conectado) {
        this.nombre = nombre;
        this.id = id;
    }
    
    /**
     * Metodo que muestra el identificador del medidor de espectros, no el del componente
     * @return idEspectro
     */
    public int getIdEspectro() {
        return idEspectro;
    }

    /**
     * Metodo que modifica el identificador del medidor de espectros, no el del componente
     * @param idEspectro Identificador del medidor de espectros
     */
    public void setIdEspectro(int idEspectro) {
        this.idEspectro = idEspectro;
    }

    /**
     * Metodo toString que retorna los atributos del medidor de espectros
     * @return nombre, id, conectadoEntrada, conectadoSalida, idEspectro
     */
    @Override
    public String toString() {
        return super.toString()+","+idEspectro; 
    }
    
}