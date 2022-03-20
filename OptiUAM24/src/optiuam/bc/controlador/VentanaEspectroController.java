/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaEspectroController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
