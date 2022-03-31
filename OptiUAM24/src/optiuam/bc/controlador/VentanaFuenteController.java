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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.vista.VentanaPrincipal;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaFuenteController extends ControladorGeneral implements Initializable {

    @FXML
    RadioButton rbtn1310, rbtn1550, rbtnLaser, rbtnLed;
    
    @FXML
    TextField txtPotencia, txtAnchuraEspectro, txtVelocidad;
    
    @FXML
    Button btnPulso, btnCrear, btnDesconectar, btnCancelar;
    
    @FXML
    private Pane Pane1;
    
    ControladorGeneral cont;
    VentanaPrincipal ven;
    
    public void crearFuente(int longitudOnda, int tipo, double potencia, double anchura, double velocidad, int id){
        Fuente fuente = new Fuente(tipo, potencia, anchura, velocidad, longitudOnda, "fuente", contadorElemento);
        System.out.println("Fuente creada: " + fuente.toString());
        elementos.add(fuente);
        manejadorElementos = new ElementoGrafico(cont,  Pane1, id, "fuente");
        dibujos.add(manejadorElementos);
        manejadorElementos.dibujarFuente();
        listaFuente(fuente);
        contadorElemento++;
    }
    
    public void imprimir(ActionEvent event){
        int longitudOnda=0, tipo=0, id = 0;
        double potencia, anchura, velocidad;
        
        if(rbtnLaser.isSelected()){
            tipo = 0;
        }
        else if(rbtnLed.isSelected()){
            tipo = 1;
        }
        else if(rbtn1310.isSelected()){
            longitudOnda=1310;
            //System.out.println(1310);
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
            //System.out.println(1550);
        }
        
        potencia = Double.parseDouble(txtPotencia.getText());
        anchura = Double.parseDouble(txtAnchuraEspectro.getText());
        velocidad = Double.parseDouble(txtVelocidad.getText());
        
        if (txtPotencia.getText().compareTo("")==0 || !txtPotencia.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("Valor de la potencia invalido");
        }
        else if(txtAnchuraEspectro.getText().compareTo("")==0 || !txtAnchuraEspectro.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("Valor de la anchura invalido");
        }
        else if(txtVelocidad.getText().compareTo("")==0 || !txtVelocidad.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("Valor de la velocidad invalido");
        }
        else if((tipo==0 &&Double.parseDouble(txtAnchuraEspectro.getText())<=0) ||
           (tipo==0 &&Double.parseDouble(txtAnchuraEspectro.getText())>1)){
            System.out.println("\nEl valor de la anchura debe ser max 1 nm");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El valor de la anchura debe ser max 1 nm");
            alert.showAndWait();
        }
        else if((tipo == 1 &&Double.parseDouble(txtAnchuraEspectro.getText())< (double)(0.01)) ||
           (tipo==1 &&Double.parseDouble(txtAnchuraEspectro.getText())> 1)){
            System.out.println("\nEl valor de la anchura debe ser min: 0.01 nm  max: 1.0 nm");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El valor de la anchura debe ser min: 0.01 nm  max: 1.0 nm");
            alert.showAndWait();
        } 
        else{
            crearFuente(longitudOnda, tipo, potencia, anchura, velocidad, id);
        }

    }
    
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
