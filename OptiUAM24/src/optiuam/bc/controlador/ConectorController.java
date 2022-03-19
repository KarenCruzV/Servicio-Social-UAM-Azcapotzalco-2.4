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
import javafx.scene.control.*;


/**
 * FXML Controller class
 *
 * @author karen
 */
public class ConectorController implements Initializable {

    @FXML
        private Label lblTitulo, lblLongitudOnda,lblModo, lblPerdida, lbldB, labelConectarA;
    @FXML
        private RadioButton rbtn1310, rbtn1550, rbtnMono, rbtnMulti;
    @FXML
        private TextField txtPerdida;
    @FXML
        private Button btnDesconectar, btnCancelar, btnCrear;
    @FXML
        private ComboBox cmbConectarA;
    
    
    public void getLongitudOnda(ActionEvent ev){
        if(rbtn1310.isSelected()){
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    
}
