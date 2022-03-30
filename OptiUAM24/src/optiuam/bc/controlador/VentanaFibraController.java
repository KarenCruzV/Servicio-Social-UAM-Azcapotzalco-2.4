/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.vista.VentanaPrincipal;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaFibraController extends ControladorGeneral implements Initializable {
    @FXML
    private TextField txtAtenue, txtDisp, txtDistancia;
    
    @FXML 
    private RadioButton rbtnMono, rbtnMulti, rbtn1310, rbtn1550, rbtnOtro, rbtn28, rbtn50;
    
    @FXML
    private Pane Pane1;
    
    @FXML
            public Button btnCrear;
    
    ControladorGeneral cont;
    VentanaPrincipal ven;

    public Button getBtnCrear() {
        return btnCrear;
    }

    public void setBtnCrear(Button btnCrear) {
        this.btnCrear = btnCrear;
    }
    
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //cont.setControlador();
        
    }    
    public void crearFibra(int longitudOnda, int modo, int tipo, double longitud_km, double atenuacion, double dispersion, int id) {
        Fibra fibra_aux = new Fibra(longitudOnda, modo, tipo, longitud_km, atenuacion, dispersion, "fibra", contadorElemento);
        System.out.println("\nFibra creada: " + fibra_aux.toString());
        elementos.add(fibra_aux);
        manejadorElementos = new ElementoGrafico(cont,  Pane1, id, "fibra");
        dibujos.add(manejadorElementos);
        manejadorElementos.dibujarFibra();
        listaFibra(fibra_aux);
        contadorElemento++;
        //cont.getElementos().add(fibra_aux);
        //cont.getManejadorElementos() = new ElementoGrafico(ventana_principal.getPnl_trabajo(), "fibra" + String.valueOf(contadorElemento), "fibra", this,"fibra");
        //cont.setManejadorElementos(new ElementoGrafico());
        //dibujos.add(manejadorElementos);
        //cont.getDibujos().add(cont.getManejadorElementos());
        //cont.setContadorElemento(cont.getContadorElemento()+1);
        //System.out.println(cont);
        
            //No sirve poner el menso dibujoooooooooooooooooooooooooooooo xd
        /* Label dibujo= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
        ven.setVentanaTrabajo().getChildren().add(dibujo);
        */
        
    }
    public void imprimir(ActionEvent event){
        int modo=0, longitudOnda=0, tipo=0, id = 0;
        double longitudKm, atenue, dispersion;
        if(rbtnMono.isSelected()){
            modo=0;
            //System.out.println("Tipo Mono");
        }else if(rbtnMulti.isSelected()){
            modo=1;
            //System.out.println("Tipo Multi");
        }   
        if(rbtn1310.isSelected()){
            longitudOnda=1310;
            //System.out.println(1310);
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
            //System.out.println(1550);
        }
        if(rbtn28.isSelected()){
            tipo=0;
            //System.out.println("28");
        }else if(rbtn50.isSelected()){
            tipo=1;
            //System.out.println("50");
        }else if(rbtnOtro.isSelected()){
            tipo=2;
            //System.out.println("Otro");
        }
        longitudKm= Double.parseDouble(txtDistancia.getText());
        atenue= Double.parseDouble(txtAtenue.getText());
        dispersion= Double.parseDouble(txtDisp.getText());
        crearFibra(longitudOnda, modo, tipo, longitudKm,atenue, dispersion, id);
        
        if(event.getSource() == btnCrear){
            //Pane pane = new Pane();
            //Image image = new Image(getClass().getResourceAsStream("/images/dibujo_fibra.png"));
            //ImageView imgView = new ImageView(image);
            //pane.getChildren().add(imgView);
            //pane.setVisible(true);
            //System.out.println("Fibra creada");
        }
        //Stage stage= VenFibra;
    }
   
    /*
    public void modificarFibra(int window, int modo, int tipo, double longitud_km,
                               double atenuacion, double dispersion, String id,String componente) {
        Fibra fibra = (Fibra) obtenerElemento(id);
        fibra.setLongitud_onda(window);
        fibra.setModo(modo);
        fibra.setTipo(tipo);
        fibra.setLongitud_km(longitud_km);
        fibra.setDispersion(dispersion);
        fibra.setAtenuacion(atenuacion);
        if (componente.compareTo(fibra.getElementoConectado()) != 0) { //es otro componente a conectar
            if (fibra.getElementoConectado().compareTo("") != 0) { // para desconectar el que tenia antes
                obtenerElemento(fibra.getElementoConectado()).setConectado(false);
            }
            fibra.setElementoConectado(componente);
            obtenerElemento(componente).setConectado(true);
        }
       // System.out.println(fibra.toString());
    }*/
    
}
