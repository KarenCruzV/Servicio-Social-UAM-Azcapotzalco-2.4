
package optiuam.bc.controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Clase principal la cual se encarga de ejecutar pantalla de inicio del
 * simulador
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see Application
 */
public class OptiUAM24 extends Application {
    
    /**
    * Metodo start, encargado de mostrar la pantalla de inicio del simulador
    * @throws java.lang.Exception Proporciona diferentes excepciones lanzadas bajo el paquete java lang
    */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
        Parent root = loader.load();
        
        Image ico = new Image("/images/acercaDe.png"); 
        primaryStage.getIcons().add(ico);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
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
