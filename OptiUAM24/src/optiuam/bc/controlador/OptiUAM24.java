
package optiuam.bc.controlador;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author karen
 */
public class OptiUAM24 extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaPrincipal.fxml"));
        Parent root = loader.load();
        //\src\images\ico_aplicar.png
        VentanaPrincipal ventanaPrincipal= loader.getController();
        ventanaPrincipal.setStage(primaryStage);
        Image ico = new Image("/images/acercaDe.png"); 
        primaryStage.getIcons().add(ico);

        primaryStage.setTitle("OptiUAM BC");
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e-> Platform.exit());
        primaryStage.setOnCloseRequest(e-> System.exit(0));
    }
    

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    
}