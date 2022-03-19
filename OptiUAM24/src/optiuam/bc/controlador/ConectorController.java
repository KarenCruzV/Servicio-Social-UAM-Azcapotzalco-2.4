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
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author karen
 */
public class ConectorController implements Initializable {

    @FXML
        protected Label lblTitulo, lblLongitudOnda,lblModo, lblPerdida, lbldB, labelConectarA, lblPropiedades;
    @FXML
        protected RadioButton rbtn1310, rbtn1550, rbtnMono, rbtnMulti;
    @FXML
        protected TextField txtPerdida;
    @FXML
        protected Button btnDesconectar, btnCancelar, btnCrear;
    @FXML
        protected ComboBox cmbConectarA;
    @FXML
        protected AnchorPane ConectorVentana;
    
    
    
    public void getLongitudOnda(ActionEvent ev){
        if(rbtn1310.isSelected()){
            lblPropiedades.setText(rbtn1310.getText()+"\t"+getModo());
            System.out.println(rbtn1310.getText()+"\t"+getModo());
        }else{
            lblPropiedades.setText(rbtn1550.getText()+"\t"+getModo());
            System.out.println(rbtn1550.getText()+"\t"+getModo());
        }
    }
    public String getModo(){
        String modo="";
        if(rbtnMono.isSelected()){
            modo= rbtnMono.getText();
        }else{
            modo= rbtnMulti.getText();
        }
        return modo;
    }
    
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    
}
