
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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Clase InicioController la cual se encarga de mostrar la pantalla de inicio 
 * del simulador y ejecutar la ventana principal
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class InicioController implements Initializable {

    /**Panel de inicio del simulador*/
    @FXML
    private StackPane inicioPane;

    /**
     * Metodo el cual inicializa el simulador
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new InicioSplash().start();
    }    
    
    /**
     * Clase encargada de iniciar el simulador
     */
    class InicioSplash extends Thread{
        @Override
        public void run() { 
            try {
                Thread.sleep(2400);
                Platform.runLater(() -> {
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
                });
            } catch (InterruptedException ex) {
                Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
