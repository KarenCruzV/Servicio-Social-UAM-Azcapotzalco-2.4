
package optiuam.bc.controlador;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.ElementoGrafico;

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
    
    public void nuevoTrabajo(ActionEvent event) {
        //ventana_principal.dispose(); //se oculta y elimina la ventana principal
        //ControladorGeneral controlador = new ControladorGeneral();
        //ventana_principal= new VentanaPrincipal();
        
        //controlador.setVentana_principal(ventana_principal);
        //ventana_principal.setControlador(controlador);
        
        //ventana_principal.setVisible(true);
        //lo de arriba es como lo tiene el shavo
        
        //ventana_principal.cerrarVentana(event); si lo pongo, marca error de
        // java.lang.NullPointerException
        //si no lo pongo, no se cierra la ventana anterior :V
        try {
            Parent root = FXMLLoader.load(getClass().getResource("VentanaPrincipal.fxml"));
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.getIcons().add(ico);
            stage.setTitle("OptiUAM BC");
            stage.setScene(scene);
            stage.showAndWait();
            stage.setOnCloseRequest(e-> Platform.exit());
            stage.setOnCloseRequest(e-> System.exit(0));
            //stage.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(ControladorGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
