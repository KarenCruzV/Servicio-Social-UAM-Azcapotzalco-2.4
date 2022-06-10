/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Arturo Borja
 */
public class InicioController implements Initializable {

    @FXML
    private StackPane inicioPane;
    @FXML
    private ImageView ImageInicio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new InicioSplash().start();
    }    
    class InicioSplash extends Thread{
        @Override
        public void run() { 
                try {
                    Thread.sleep(2400);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Stage stage= new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaPrincipal.fxml"));
                Parent root=null;
                            try {
                                root = loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                VentanaPrincipal ventanaPrincipal= loader.getController();
                VentanaPrincipal.setStage(stage);
                Image ico = new Image("/images/acercaDe.png"); 
                stage.getIcons().add(ico);

                stage.setTitle("OptiUAM BC");
                Scene scene = new Scene(root);
        
                stage.setScene(scene);
                stage.show();
                stage.setOnCloseRequest(e-> Platform.exit());
                stage.setOnCloseRequest(e-> System.exit(0));
                inicioPane.getScene().getWindow().hide();
                        }
                    });
                
            } catch (InterruptedException ex) {
                Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    }
    
}
