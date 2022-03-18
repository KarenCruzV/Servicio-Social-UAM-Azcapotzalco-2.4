/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.modelo;

public class Conector extends Componente {
    
    /*Attribute Declaration*/
    
    private double perdidaInsercion;// 0.5 max na min single fiber| 1.0 max na min multi fiber
    private int longitudOnda; // 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window
    private int modo;
    private char orientacion ='R';

    
    /*Constructor*/
    
    public Conector() {
    }

    public Conector(int wavelength, int mode, double insertion_loss, String name, int id) {
        this.perdidaInsercion = insertion_loss;
        this.longitudOnda = wavelength;
        this.modo = mode;
        this.nombre = name;
        this.id = id;
    }
    
    
    /*Getter and Setter*/

    public double getPerdidaInsercion() {
        return perdidaInsercion;
    }

    public void setPerdidaInsercion(double perdidaInsercion) {
        this.perdidaInsercion = perdidaInsercion;
    }

    public int getLongitudOnda() {
        return longitudOnda;
    }

    public void setLongitudOnda(int longitudOnda) {
        this.longitudOnda = longitudOnda;
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public char getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(char orientacion) {
        this.orientacion = orientacion;
    }
    
    
    /*toString*/

    @Override
    public String toString() {
        return super.toString() + ", " + perdidaInsercion + ", " + longitudOnda + 
                ", " + modo + ", " + orientacion;
    }
    
}