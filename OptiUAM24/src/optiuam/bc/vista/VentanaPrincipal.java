
package optiuam.bc.vista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class VentanaPrincipal implements Initializable {
 
    @FXML
    private void abrirVentanaFibra(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("VentanaFibra.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage(StageStyle.DECORATED);
        Image ico = new Image("/images/acercaDe.png"); 
        stage.getIcons().add(ico);

        stage.setTitle("OptiUAM BC Fibra");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
    
    @FXML
    private void abrirVentanaFuente(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("VentanaFuente.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage(StageStyle.DECORATED);
        Image ico = new Image("/images/acercaDe.png"); 
        stage.getIcons().add(ico);

        stage.setTitle("OptiUAM BC Fuente");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void abrirVentanaSplitter(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("VentanaSplitter.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage(StageStyle.DECORATED);
        Image ico = new Image("/images/acercaDe.png"); 
        stage.getIcons().add(ico);

        stage.setTitle("OptiUAM BC Splitter");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void abrirVentanaConector(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("VentanaConector.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage(StageStyle.DECORATED);
        Image ico = new Image("/images/acercaDe.png"); 
        stage.getIcons().add(ico);

        stage.setTitle("OptiUAM BC Conector");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void abrirVentanaEmpalme(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("VentanaEmpalme.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage(StageStyle.DECORATED);
        Image ico = new Image("/images/acercaDe.png"); 
        stage.getIcons().add(ico);

        stage.setTitle("OptiUAM BC Empalme");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void abrirVentanaPotencia(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("VentanaPotencia.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage(StageStyle.DECORATED);
        Image ico = new Image("/images/acercaDe.png"); 
        stage.getIcons().add(ico);

        stage.setTitle("OptiUAM BC Medidor Potencia");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void abrirVentanaEspectro(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        
        Parent root = FXMLLoader.load(getClass().getResource("VentanaEspectro.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage(StageStyle.DECORATED);
        Image ico = new Image("/images/acercaDe.png"); 
        stage.getIcons().add(ico);

        stage.setTitle("OptiUAM BC Medidor Espectro");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
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
