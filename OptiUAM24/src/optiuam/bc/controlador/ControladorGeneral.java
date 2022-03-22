/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.controlador;

import java.util.ArrayList;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.vista.VentanaPrincipal;

/**
 *
 * @author Arturo Borja
 */
public class ControladorGeneral {
    private ArrayList<Componente> elementos; // contiene los elementos creados en la simulacion
    private ArrayList<ElementoGrafico> dibujos; //Contiene los elementos mostrados en el panel
    private int contadorElemento; // contador para asignar id a un elemento
    private VentanaPrincipal ventana_principal;//para tener la comunicacion vista-controlador
    private ElementoGrafico manejadorElementos; // para crear los elementos graficos

    public ControladorGeneral() {
        contadorElemento = 0;
        elementos = new ArrayList();
        dibujos = new ArrayList();
    }
    
    public ControladorGeneral setControlador(){
        return this;
    }

    public ArrayList<Componente> getElementos() {
        return elementos;
    }

    public void setElementos(ArrayList<Componente> elementos) {
        this.elementos = elementos;
    }

    public ArrayList<ElementoGrafico> getDibujos() {
        return dibujos;
    }

    public void setDibujos(ArrayList<ElementoGrafico> dibujos) {
        this.dibujos = dibujos;
    }

    public int getContadorElemento() {
        return contadorElemento;
    }

    public void setContadorElemento(int contadorElemento) {
        this.contadorElemento = contadorElemento;
    }

    public VentanaPrincipal getVentana_principal() {
        return ventana_principal;
    }

    public void setVentana_principal(VentanaPrincipal ventana_principal) {
        this.ventana_principal = ventana_principal;
    }

    public ElementoGrafico getManejadorElementos() {
        return manejadorElementos;
    }

    public void setManejadorElementos(ElementoGrafico manejadorElementos) {
        this.manejadorElementos = manejadorElementos;
    }

    public ControladorGeneral(ArrayList<Componente> elementos, ArrayList<ElementoGrafico> dibujos, int contadorElemento, VentanaPrincipal ventana_principal, ElementoGrafico manejadorElementos) {
        this.elementos = elementos;
        this.dibujos = dibujos;
        this.contadorElemento = contadorElemento;
        this.ventana_principal = ventana_principal;
        this.manejadorElementos = manejadorElementos;
    }
    
    
}
