
package optiuam.bc.controlador;

import java.util.LinkedList;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Empalme;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.modelo.Splitter;

/**
 *
 * @author Arturo Borja
 */
public class ControladorGeneral {
    
    public LinkedList<Componente> elementos; // contiene los elementos creados en la simulacion
    public LinkedList<ElementoGrafico> dibujos; //Contiene los elementos mostrados en el panel
    public int contadorElemento; // contador para asignar id a un elemento
    public VentanaPrincipal ventana_principal;//para tener la comunicacion vista-controlador
    public ElementoGrafico manejadorElementos; // para crear los elementos graficos

    public ControladorGeneral() {
        elementos = new LinkedList();
        dibujos = new LinkedList();
        contadorElemento=0;
    }
    
    public ControladorGeneral setControlador(){
        return this;
    }

    public LinkedList<Componente> getElementos() {
        return elementos;
    }

    public void setElementos(LinkedList<Componente> elementos) {
        this.elementos = elementos;
    }
    
    public LinkedList<ElementoGrafico> getDibujos() {
        return dibujos;
    }

    public void setDibujos(LinkedList<ElementoGrafico> dibujos) {
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

    public ControladorGeneral(LinkedList<Componente> elementos, 
            LinkedList<ElementoGrafico> dibujos, int contadorElemento, 
            VentanaPrincipal ventana_principal, ElementoGrafico manejadorElementos) {
        this.elementos = elementos;
        this.dibujos = dibujos;
        this.contadorElemento = contadorElemento;
        this.ventana_principal = ventana_principal;
        this.manejadorElementos = manejadorElementos;
    }
    
    /*public Componente obtenerElemento(int id) {
        for (int i = 0; i < elementos.size(); i++) {
            if (elementos.get(i).getId() == id) {
                return elementos.get(i);
            }
        }
        return null;
    }*/
    
    /*public void guardarPulso(float A0,float T0,float W0,float C,float M,int id){
        Fuente fuente = (Fuente) obtenerElemento(id);
        //System.out.println("C:"+C+" A0:"+A0+" W0:"+W0+ " T0:"+T0);
        fuente.setPulso(A0, T0, W0, C,M);
    }*/
    
    
}
