/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.controlador;

import java.util.ArrayList;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Empalme;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.modelo.Splitter;
import optiuam.bc.vista.VentanaPrincipal;

/**
 *
 * @author Arturo Borja
 */
public class ControladorGeneral {
    
    public ArrayList<Componente> elementos; // contiene los elementos creados en la simulacion
    public ArrayList<ElementoGrafico> dibujos; //Contiene los elementos mostrados en el panel
    public int contadorElemento; // contador para asignar id a un elemento
    public VentanaPrincipal ventana_principal;//para tener la comunicacion vista-controlador
    public ElementoGrafico manejadorElementos; // para crear los elementos graficos

    public ControladorGeneral() {
        elementos = new ArrayList();
        dibujos = new ArrayList();
        contadorElemento=0;
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
    
    public void listaFibra(){
        System.out.println("\nLista de fibras creadas: ");
        for(int i = 0; i < elementos.size(); i++){
            System.out.println(elementos.get(i).toString());
        }
    }
    
    public void listaConector(Conector conector){
        System.out.println("\nLista de conectores creados: ");
        for(int i = 0; i < getElementos().size(); i++){
            System.out.println(getElementos().get(i));
        }
    }
    
    public void listaEmpalme(Empalme empalme){
        System.out.println("\nLista de empalmes creados: ");
        for(int i = 0; i < getElementos().size(); i++){
            System.out.println(getElementos().get(i));
        }
    }
    
    public void listaFuente(Fuente fuente){
        System.out.println("\nLista de fuentes creadas: ");
        for(int i = 0; i < getElementos().size(); i++){
            System.out.println(getElementos().get(i));
        }
    }
    
    public void listaSplitter(Splitter splitter){
        System.out.println("\nLista de fuentes creadas: ");
        for(int i = 0; i < getElementos().size(); i++){
            System.out.println(getElementos().get(i));
        }
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