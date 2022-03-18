/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.model;

public class Component {
    
    /*Attribute Declaration*/
    
    //Name and id to identify the component
    protected String name;
    protected int id;
    //Attributes to know if it is connected and what it is connected to
    protected String connectedElement = "";
    protected boolean connected;

    
    /*Getter and Setter*/
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConnectedElement() {
        return connectedElement;
    }

    public void setConnectedElement(String connectedElement) {
        this.connectedElement = connectedElement;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    
    
    /*toString*/

    @Override
    public String toString() {
        return name + " " + id + ", " + connectedElement + ", " + connected;
    }
    
}
