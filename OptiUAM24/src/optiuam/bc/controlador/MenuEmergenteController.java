/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class MenuEmergenteController implements Initializable {
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
    private void duplicar(){
        System.out.println("Duplicar");
    }
    
    @FXML
    private void girar(){
         System.out.println("Girar");
    }
    
    @FXML
    private void eliminar(){
        System.out.println("Eliminar");
    }
    
    
}
