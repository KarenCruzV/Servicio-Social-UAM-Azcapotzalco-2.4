
package optiuam.bc.modelo;

import java.util.ArrayList;

/**
 * Clase Splitter la cual contiene los atributos principales de un splitter
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see Componente
 */
public class Splitter extends Componente {
    
    /**Numero de salidas del divisor optico. 0=2; 1=4; 2=8; 3=16; 4=32; 5=64*/                    
    private int salidas;
    /**Perdida de insercion del divisor optico*/
    private double perdidaInsercion;
    /**Longitud de onda del divisor optico*/
    private int longitudOnda;
    /**Conexiones del divisor optico*/
    private ArrayList<String> conexiones;
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
        modificarSalidas(salidas); 
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
        if(this.salidas!=salidas)
            modificarSalidas(salidas);
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
     * Metodo que muestra el elemento conectado en la salida indicada del divisor optico
     * @param salida Salida indicada/seleccionada del divisor optico
     * @return conexiones;
     */
    public String getConexion(int salida){
        return conexiones.get(salida);
    }
    
    /**
     * Metodo que modifica el elemento conectado en la salida indicada del divisor optico
     * @param salida Salida indicada/seleccionada del divisor optico
     * @param componente Nuevo componente que sera conectado a la salida indicada
     */
    public void setConexion(int salida,String componente){
        conexiones.remove(salida);
        conexiones.add(salida, componente);
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
     * Metodo que se usa solo cuando se carga un trabajo y contiene un splitter. 
     * Asigna el elemento conectado en la salida indicada.
     * Es necesario ya que por alguna razon no inicializa el arreglo de conexiones
     * @param salida Salida indicada/seleccionada del divisor optico
     * @param componente Nuevo componente que sera conectado a la salida indicada
     */
    public void cargarConexion(int salida,String componente){
       if(conexiones == null){
           modificarSalidas(salidas);
       }
        conexiones.remove(salida);
        conexiones.add(salida, componente);
       
    }
    
    /**
     * Metodo que modifica las salidas del divisor optico
     */
    private void modificarSalidas(int salidas){
        this.conexiones = new ArrayList();
        for(int i=0;i<((int) Math.pow(2,(salidas+1))); i++)
            conexiones.add("");
    }

    /**
     * Metodo que muestra las conexiones del divisor optico
     * @return conexiones
     */
    public ArrayList<String> getConexiones() {
        return conexiones;
    }
    
    
    public int buscarSalida(String idComponente){
        for(int i = 0;i<conexiones.size();i++)
            if(conexiones.get(i).compareTo(idComponente)==0)
                return i;
        return -1;    
    }
    
    public String Conexiones(){
        String aux="";// variable para guardar las conexiones creadas separadas con una coma;
        for(int i =0;i < conexiones.size();i++){
            if(i+1 == conexiones.size()){
                if(conexiones.get(i).compareTo("")==0)
                  aux=aux+" ";  
                else
                aux=aux + conexiones.get(i);
            }
            else{
                if(conexiones.get(i).compareTo("")==0)
                    aux=aux+" "+",";  
                else
                    aux=aux + conexiones.get(i)+",";
            }
        }
        return aux;
    } 
    
    /**
     * Metodo toString que retorna los atributos de un divisor optico
     * @return nombre, id, conectadoEntrada, conectadoSalida, salidas, 
     * perdidaInsercio, longitudOnda, idS;
     */
    @Override
    public String toString() {
        return super.toString()+","+salidas+","+perdidaInsercion+","+longitudOnda+","+idS;
    }
    
}