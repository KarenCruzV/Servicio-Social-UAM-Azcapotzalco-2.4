/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.modelo;

import javafx.scene.shape.Line;

/**
 *
 * @author Arturo Borja
 */
public class PuertoSalida {
    private boolean conectadoSalida;
    private String elementoConectadoSalida;
    private Line linea;

    public boolean isConectadoSalida() {
        return conectadoSalida;
    }

    public void setConectadoSalida(boolean conectadoSalida) {
        this.conectadoSalida = conectadoSalida;
    }

    public String getElementoConectadoSalida() {
        return elementoConectadoSalida;
    }

    public void setElementoConectadoSalida(String elementoConectadoSalida) {
        this.elementoConectadoSalida = elementoConectadoSalida;
    }

    public Line getLinea() {
        return linea;
    }

    public void setLinea(Line linea) {
        this.linea = linea;
    }

    public PuertoSalida() {
        this.conectadoSalida=false;
        this.elementoConectadoSalida="";
        
    }

    @Override
    public String toString() {
        return ","+conectadoSalida + "," + elementoConectadoSalida;
    }
    
    
}
