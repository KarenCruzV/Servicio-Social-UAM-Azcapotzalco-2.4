/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.modelo;

/**
 *
 * @author Arturo Borja
 */
public class PuertoEntrada {
    private boolean conectadoEntrada;
    private String elementoConectadoEntrada;

    public boolean isConectadoEntrada() {
        return conectadoEntrada;
    }

    public void setConectadoEntrada(boolean conectadoEntrada) {
        this.conectadoEntrada = conectadoEntrada;
    }

    public String getElementoConectadoEntrada() {
        return elementoConectadoEntrada;
    }

    public void setElementoConectadoEntrada(String elementoConectadoEntrada) {
        this.elementoConectadoEntrada = elementoConectadoEntrada;
    }

    public PuertoEntrada() {
        this.conectadoEntrada=false;
        this.elementoConectadoEntrada="";
    }

    @Override
    public String toString() {
        return ","+conectadoEntrada + "," + elementoConectadoEntrada;
    }
    
    
    
    
}
