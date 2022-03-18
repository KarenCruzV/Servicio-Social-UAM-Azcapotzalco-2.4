/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.modelo;

public class Componente {
    
    /*Attribute Declaration*/
    
    //Name and id to identify the component
    protected String nombre;
    protected int id;
    //Attributes to know if it is conectado and what it is conectado to
    protected String elementoConectado = "";
    protected boolean conectado;

    public Componente() {
    }
    
    
    
    /*Getter and Setter*/
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getElementoConectado() {
        return elementoConectado;
    }

    public void setElementoConectado(String elementoConectado) {
        this.elementoConectado = elementoConectado;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }
    
    public void crearNuevoComponente(){
        System.out.println("hola");
    }
    
    /*toString*/

    @Override
    public String toString() {
        return nombre + " " + id + ", " + elementoConectado + ", " + conectado;
    }
    
}
