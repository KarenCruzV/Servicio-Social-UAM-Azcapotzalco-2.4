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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaPulsoController implements Initializable {
    
    @FXML
    Button btnAplicar, btnGraficar;
    
    @FXML
    TextField txtA0, txtC, txtT0, txtW0, txtM;
    
    private VentanaFuenteController ventanaFuente;//id de la ventana de la fuente !!
    String tipo="Gaussiano";
    /** Creates new form VentanaPulso */
    
    private ControladorGeneral controlador;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    public void setVentanaFuenteController(VentanaFuenteController ventanaFuente){
        this.ventanaFuente= ventanaFuente;
    }
    
    public void setValores(float C,float A0,float W0,float T0,float M){
        txtC.setText(String.valueOf(C)); //chirp
        txtA0.setText(String.valueOf(A0)); //amplitud
        txtW0.setText(String.valueOf(W0)); //frecuencia
        txtT0.setText(String.valueOf(T0)); //anchura
        txtM.setText(String.valueOf(M));
        if(M > 1){
            tipo="Supergaussiano";
        }
    }
    
     private boolean validarValores(){
        if (txtA0.getText().compareTo("")==0 || !txtA0.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nValor de la amplitud invalido");
            alert.showAndWait();
            return false;
        }
        if (txtT0.getText().compareTo("")==0 || !txtT0.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nValor de la anchura invalido");
            alert.showAndWait();
            return false;
        }
        if (txtC.getText().compareTo("")==0 || !txtC.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nValor del chirp invalido");
            alert.showAndWait();
            return false;
        }
        if (txtW0.getText().compareTo("")==0 || !txtW0.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nValor de la frecuencia invalido");
            alert.showAndWait();
            return false;
        }
         if (txtM.getText().compareTo("")==0 || !txtM.getText().matches("[+-]?\\d*(\\.\\d+)?") 
                 || Float.parseFloat(txtM.getText() ) <1){
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nValor de M invalido");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    
    public void setControlador(ControladorGeneral controlador){
        this.controlador=controlador;
    }
    
    @FXML
    private void btnAplicarAction(ActionEvent event) {
        if(validarValores()){
            float A0 = Float.parseFloat(txtA0.getText());
            float T0 = Float.parseFloat(txtT0.getText());
            float W0 = Float.parseFloat(txtW0.getText());
            float C = Float.parseFloat(txtC.getText());
            float M = Float.parseFloat(txtM.getText());
            //controlador.guardarPulso(A0,T0,W0,C,M,ventanaFuente.getId());
            //ventanaFuente.setPulso(A0, T0, W0, C,M);
            if(M > 1){
            tipo="Supergaussiano";
        }
            System.out.println("C:"+C+" A0:"+A0+" W0:"+W0+ " T0:"+T0);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("\nPulso modificado");
            alert.showAndWait();
        }
    }
      
}
