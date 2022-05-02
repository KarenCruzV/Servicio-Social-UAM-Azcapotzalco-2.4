
package optiuam.bc.controlador;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase principal la cual se encarga de ejecutar la Ventana Principal del
 * simulador
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see Application
 */
public class OptiUAM24 extends Application {
    
    /**
    *
    * Metodo start, encargado de mostrar la Ventana Principal
    * @throws java.lang.Exception Proporciona diferentes excepciones lanzadas bajo el paquete java lang
    */
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
     * Metodo main
     * @param args the command line arguments
     * @throws java.lang.Exception Proporciona diferentes excepciones lanzadas bajo el paquete java lang
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    
}
