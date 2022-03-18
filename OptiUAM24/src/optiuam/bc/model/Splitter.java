/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.model;

import java.util.ArrayList;

public class Splitter extends Component {
    
    /*Attribute Declaration*/
                          
    private int outputs;// number of optical splitter outputs
                        //0=2;1=4;2=8;3=16;4=32;5=64
    private double insertion_loss;
    private int wavelength;
    private ArrayList<String> connections;

    
    /*Constructor*/
    
    public Splitter(int outputs, double insertion_loss, int wavelength, String name, int id) {
        this.outputs = outputs;
        this.insertion_loss = insertion_loss;
        this.wavelength = wavelength;
        this.name = name;
        this.id = id;
        modificarSalidas(outputs); 
    }

    public Splitter() {
    }

    
    /*Getter and Setter*/
    
    public int getOutputs() {
        return outputs;
    }

    public double getInsertion_loss() {
        return insertion_loss;
    }

    public int getWavelength() {
        return wavelength;
    }

    public void setOutputs(int outputs) {
        if(this.outputs!=outputs)
            modificarSalidas(outputs);
        this.outputs = outputs;
    }

    public void setInsertion_loss(double insertion_loss) {
        this.insertion_loss = insertion_loss;
    }

    public void setWavelength(int wavelength) {
        this.wavelength = wavelength;
    }
    
    public String getConexion(int salida){
        return connections.get(salida);
    }
    //asigna el elemento conectado en la salida indicada 
    public void setConexion(int salida,String componente){
        connections.remove(salida);
        connections.add(salida, componente);
    }
    //este metodo es exclusivo para cuando se carga un trabajo y contiene un splitter
    //asigna el elemento conectado en la salida indicada
    // es necesario ya que por alguna razon no inicializa el arreglo de connections
    public void cargarConexion(int salida,String componente){
       if(connections == null){
           modificarSalidas(outputs);
       }
        connections.remove(salida);
        connections.add(salida, componente);
       
    }
    
    private void modificarSalidas(int salidas){
        this.connections = new ArrayList();
        for(int i=0;i<((int) Math.pow(2,(salidas+1))); i++)
            connections.add("");
    }

    public ArrayList<String> getConnections() {
        return connections;
    }
    //este metodo encuentra la salida a la que esta conectada el componente   
    public int buscarSalida(String idComponente){
        for(int i = 0;i<connections.size();i++)
            if(connections.get(i).compareTo(idComponente)==0)
                return i;
            
        return -1;    
    }
    @Override
    public String toString() {
        return super.toString()+","+outputs+","+insertion_loss+","+wavelength;
    }
    
    public String Conexiones(){
        String aux="";// variable para guardar las connections creadas separadas con una coma;
        for(int i =0;i < connections.size();i++){
            if(i+1 == connections.size()){
                if(connections.get(i).compareTo("")==0)
                  aux=aux+" ";  
                else
                aux=aux + connections.get(i);
            }
            else{
                if(connections.get(i).compareTo("")==0)
                    aux=aux+" "+",";  
                else
                    aux=aux + connections.get(i)+",";
            }
        }
        
        return aux;
    } 
}
