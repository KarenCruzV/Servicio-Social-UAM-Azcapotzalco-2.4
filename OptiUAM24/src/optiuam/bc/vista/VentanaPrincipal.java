
package optiuam.bc.vista;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import optiuam.bc.controlador.ControladorGeneral;
import optiuam.bc.controlador.VentanaConectorController;
import optiuam.bc.controlador.VentanaEmpalmeController;
import optiuam.bc.controlador.VentanaFibraController;
import optiuam.bc.controlador.VentanaFuenteController;
import optiuam.bc.controlador.VentanaSplitterController;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.*;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.modelo.MedidorEspectro;
import optiuam.bc.modelo.MedidorPotencia;

public class VentanaPrincipal implements Initializable {

    Stage stage;
    private ObservableList<Componente> componentes;
    static ControladorGeneral controlador= new ControladorGeneral();
    
    @FXML
        private ImageView viewFibra,viewFuente, viewConector,viewSplitter,viewEmpalme, viewPotencia,viewEspectro;
    @FXML
        private Button btnFibra, btnFuente, btnConector, btnSplitter, btnEmpalme, btnPotencia, btnEspectro;
    
    private Image fibraI, fuenteI, conectorI, splitterI, empalmeI, potenciaI, espectroI, fondo;
    
    @FXML
    public Pane Pane1;
    @FXML
    public ScrollPane scroll;
    @FXML
    static public TitledPane componentMenu;
    
    
    
    
    @FXML
    private void abrirVentanaFibra(ActionEvent event) throws IOException{
        Fibra fibra= new Fibra();
        Stage stage = new Stage(StageStyle.UTILITY);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaFibra.fxml"));
        Parent root = loader.load();
        //Se crea una instancia del controlador de fibra.
        VentanaFibraController fibraController= (VentanaFibraController) loader.getController();
        fibraController.init(controlador,this.stage,this.Pane1);
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);
        stage.setTitle("OptiUAM BC Fibra");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
 
        System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    private void abrirVentanaFuente(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaFuente.fxml"));
        Parent root =loader.load();
        VentanaFuenteController fuenteControl=loader.getController();
        fuenteControl.init(controlador,this.stage,this.Pane1);
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);
        stage.setTitle("OptiUAM BC Fuente");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        //System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    private void abrirVentanaSplitter(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaSplitter.fxml"));
        Parent root =loader.load();
        VentanaSplitterController splitterControl=loader.getController();
        splitterControl.init(controlador,this.stage,this.Pane1);
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);
        stage.setTitle("OptiUAM BC Conector");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        //System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    private void abrirVentanaConector(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaConector.fxml"));
        Parent root =loader.load();
        VentanaConectorController conectorControl=loader.getController();
        conectorControl.init(controlador,this.stage,this.Pane1);
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);
        stage.setTitle("OptiUAM BC Conector");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        //System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    private void abrirVentanaEmpalme(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaEmpalme.fxml"));
        Parent root =loader.load();
        VentanaEmpalmeController empalmeControl=loader.getController();
        empalmeControl.init(controlador,this.stage,this.Pane1);
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);
        stage.setTitle("OptiUAM BC Fuente");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        //System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    private void crearPotencia(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("\n¡Medidor de potencia creado!");
        alert.showAndWait();
        
        Componente componente = new Componente();
        componente.setId(controlador.getContadorElemento());
        controlador.getElementos().add(componente);
        MedidorPotencia potencia = new MedidorPotencia("potencia", controlador.getContadorElemento()," ",false);
        Label dibujo= new Label();
        Label title= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_potenciaR.png")));
        title.setText("potencia" + controlador.getContadorElemento());
        dibujo.setText("potencia" + "_"+ controlador.getContadorElemento());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        title.setLayoutX(0);
        title.setLayoutY(-20);
        
        ElementoGrafico elem = new ElementoGrafico();
        elem.setComponente("potencia");
        elem.setDibujo(dibujo);
        elem.setTitle(title);
        elem.setId(controlador.getContadorElemento());
        controlador.getDibujos().add(elem);
        Pane1.getChildren().add(dibujo);
        
            dibujo.setOnMouseDragged((MouseEvent event1) -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                dibujo.setLayoutX(event1.getSceneX() - 20);
                dibujo.setLayoutY(event1.getSceneY() - 170);
                dibujo.setCursor(Cursor.CLOSED_HAND);
            }
        });
            
            dibujo.setOnMouseEntered((MouseEvent event1) -> {
                dibujo.setStyle("-fx-border-color: darkblue;");
                dibujo.setCursor(Cursor.OPEN_HAND);
        });
            
            dibujo.setOnMouseExited((MouseEvent event1) -> {
                dibujo.setStyle("");
        });
            
            dibujo.setOnMouseClicked((MouseEvent event1) -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("VentanaPotencia.fxml"));
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage stage = new Stage(StageStyle.UTILITY);
                    stage.getIcons().add(ico);
                    stage.setTitle("OptiUAM BC Medidor Potencia");
                    stage.setScene(scene);
                    stage.showAndWait();
                    stage.setResizable(false);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event1.getButton() == MouseButton.SECONDARY) {
                
            }
        });
            controlador.setContadorElemento(controlador.getContadorElemento()+1);
        System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    private void crearEspectro(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("\n¡Analizador de espectros creado!");
        alert.showAndWait();
        
        Componente componente= new Componente();
        componente.setId(controlador.getContadorElemento());
        controlador.getElementos().add(componente);
        MedidorEspectro espectro = new MedidorEspectro("espectro", controlador.getContadorElemento()," ",false);
        Label dibujo= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_espectroR.png")));
        dibujo.setText("espectro" + "_"+ controlador.getContadorElemento());
        dibujo.setContentDisplay(ContentDisplay.TOP);

        ElementoGrafico elem = new ElementoGrafico();
        elem.setComponente("espectro");
        elem.setDibujo(dibujo);
        elem.setId(controlador.getContadorElemento());
        controlador.getDibujos().add(elem);
        Pane1.getChildren().add(dibujo);
        
            elem.getDibujo().setOnMouseDragged((MouseEvent event1) -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                elem.getDibujo().setLayoutX(event1.getSceneX() - 20);
                elem.getDibujo().setLayoutY(event1.getSceneY() - 170);
                elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
            }
        });
            
            elem.getDibujo().setOnMouseEntered((MouseEvent event1) -> {
                elem.getDibujo().setStyle("-fx-border-color: darkblue;");
                elem.getDibujo().setCursor(Cursor.OPEN_HAND);
        });
            
            elem.getDibujo().setOnMouseExited((MouseEvent event1) -> {
                elem.getDibujo().setStyle("");
        });
            
            elem.getDibujo().setOnMouseClicked((MouseEvent event1) -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("VentanaEspectro.fxml"));
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage stage = new Stage(StageStyle.UTILITY);
                    stage.getIcons().add(ico);
                    stage.setTitle("OptiUAM BC Medidor Espectro");
                    stage.setScene(scene);
                    stage.showAndWait();
                    stage.setResizable(false);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event1.getButton() == MouseButton.SECONDARY) {
                
            }
        });
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    /*
    public void mostrarMenuChiquito(ElementoGrafico dibujo){
        // create a menu
                ContextMenu contextMenu = new ContextMenu();
                
                // create menuitems
                MenuItem menuItem1 = new MenuItem("-Duplicar");
                MenuItem menuItem2 = new MenuItem("-Girar");
                MenuItem menuItem3 = new MenuItem("-Eliminar");
                
                menuItem1.setOnAction(e ->{
                    System.out.println("Duplicar");
                    for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                        if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                            if(controlador.getElementos().get(elemento).getNombre()=="fibra"){
                                
                            }
                        }
                    }
                });
                
                menuItem2.setOnAction(e ->{
                    System.out.println("Girar");
                    if(dibujo.getDibujo().getText().contains("potencia")){
                        System.out.println("Girar potencia");
                    }
                    else{
                        System.out.println("Girar elemento");
                    }
                });
                
                menuItem3.setOnAction(e ->{
                    System.out.println("Elemento "+dibujo.getDibujo().getText()+" eliminado");
                    controlador.getDibujos().remove(dibujo.getId());
                    controlador.getElementos().remove(dibujo.getId());    
                    dibujo.getDibujo().setVisible(false);
                });
                
                // add menu items to menu
                contextMenu.getItems().add(menuItem1);
                contextMenu.getItems().add(menuItem2);
                contextMenu.getItems().add(menuItem3);
                dibujo.getDibujo().setContextMenu(contextMenu);
    }*/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        componentes=FXCollections.observableArrayList();
        fibraI=new Image("images/ico_fibra.png"); 
        fuenteI=new Image("images/ico_fuente.png"); 
        conectorI=new Image("images/ico_conector.png"); 
        potenciaI=new Image("images/ico_potencia.png"); 
        espectroI=new Image("images/ico_espectro.png"); 
        empalmeI=new Image("images/ico_empalme.png"); 
        splitterI=new Image("images/ico_splitter.png"); 
        //fondo= new Image("/images/fondo.png"); 
        
        viewFibra.setImage(fibraI);
        viewFuente.setImage(fuenteI);
        viewConector.setImage(conectorI);
        viewPotencia.setImage(potenciaI);
        viewEspectro.setImage(espectroI);
        viewEmpalme.setImage(empalmeI);
        viewSplitter.setImage(splitterI);
        
        //ventanaPrincipal=this;
        
    }    

    public void setStage(Stage primaryStage) {
        stage=primaryStage;
    }

    public Pane getPane1() {
        return Pane1;
    }
    

}
