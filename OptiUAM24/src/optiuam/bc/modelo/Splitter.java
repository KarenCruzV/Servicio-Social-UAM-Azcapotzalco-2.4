
package optiuam.bc.modelo;

import java.util.ArrayList;

public class Splitter extends Componente {
    
    /*Attribute Declaration*/
                          
    private int salidas;//numero de salidas del splitter optico
                        //0=2;1=4;2=8;3=16;4=32;5=64
    private double perdidaInsercion;
    private int longitudOnda;
    private ArrayList<String> conexiones;

    
    /*Constructor*/
    
    public Splitter(int outputs, double insertion_loss, int wavelength, String name, int id) {
        this.salidas = outputs;
        this.perdidaInsercion = insertion_loss;
        this.longitudOnda = wavelength;
        this.nombre = name;
        this.id = id;
        modificarSalidas(outputs); 
    }

    public Splitter() {
    }

    
    /*Getter and Setter*/
    
    public int getSalidas() {
        return salidas;
    }

    public double getPerdidaInsercion() {
        return perdidaInsercion;
    }

    public int getLongitudOnda() {
        return longitudOnda;
    }

    public void setSalidas(int salidas) {
        if(this.salidas!=salidas)
            modificarSalidas(salidas);
        this.salidas = salidas;
    }

    public void setPerdidaInsercion(double perdidaInsercion) {
        this.perdidaInsercion = perdidaInsercion;
    }

    public void setLongitudOnda(int longitudOnda) {
        this.longitudOnda = longitudOnda;
    }
    
    public String getConexion(int salida){
        return conexiones.get(salida);
    }
    //asigna el elemento conectado en la salida indicada 
    public void setConexion(int salida,String componente){
        conexiones.remove(salida);
        conexiones.add(salida, componente);
    }
    //este metodo es exclusivo para cuando se carga un trabajo y contiene un splitter
    //asigna el elemento conectado en la salida indicada
    // es necesario ya que por alguna razon no inicializa el arreglo de conexiones
    public void cargarConexion(int salida,String componente){
       if(conexiones == null){
           modificarSalidas(salidas);
       }
        conexiones.remove(salida);
        conexiones.add(salida, componente);
       
    }
    
    private void modificarSalidas(int salidas){
        this.conexiones = new ArrayList();
        for(int i=0;i<((int) Math.pow(2,(salidas+1))); i++)
            conexiones.add("");
    }

    public ArrayList<String> getConexiones() {
        return conexiones;
    }
    //este metodo encuentra la salida a la que esta conectada el componente   
    public int buscarSalida(String idComponente){
        for(int i = 0;i<conexiones.size();i++)
            if(conexiones.get(i).compareTo(idComponente)==0)
                return i;
            
        return -1;    
    }
    @Override
    public String toString() {
        return super.toString()+","+salidas+","+perdidaInsercion+","+longitudOnda;
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
}