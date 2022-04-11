
package optiuam.bc.vista;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import optiuam.bc.controlador.ControladorGeneral;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.*;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.modelo.MedidorEspectro;
import optiuam.bc.modelo.MedidorPotencia;

public class VentanaPrincipal implements Initializable {
    
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
    
    static ControladorGeneral controlador= new ControladorGeneral();
    
    
    @FXML
    private void abrirVentanaFibra(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);
        Parent root = FXMLLoader.load(getClass().getResource("VentanaFibra.fxml"));
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);
        stage.setTitle("OptiUAM BC Fibra");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        leerAuxiliar();
        System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    private void abrirVentanaFuente(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);
        Parent root = FXMLLoader.load(getClass().getResource("VentanaFuente.fxml"));
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);
        stage.setTitle("OptiUAM BC Fuente");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        leerAuxiliar();
        
    }
    
    @FXML
    private void abrirVentanaSplitter(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);
        Parent root = FXMLLoader.load(getClass().getResource("VentanaSplitter.fxml"));
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);
        stage.setTitle("OptiUAM BC Splitter");
        stage.setScene(scene);
        stage.showAndWait();
        stage.setResizable(false);
        leerAuxiliar();
    }
    
    @FXML
    private void abrirVentanaConector(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);
        Parent root = FXMLLoader.load(getClass().getResource("VentanaConector.fxml"));
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);
        stage.setTitle("OptiUAM BC Conector");
        stage.setScene(scene);
        stage.showAndWait();
        stage.setResizable(false);
        leerAuxiliar();
    }
    
    @FXML
    private void abrirVentanaEmpalme(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);
        Parent root = FXMLLoader.load(getClass().getResource("VentanaEmpalme.fxml"));
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);
        stage.setTitle("OptiUAM BC Empalme");
        stage.setScene(scene);
        stage.showAndWait();
        stage.setResizable(false);
        leerAuxiliar();
    }
    
    @FXML
    private void crearPotencia(ActionEvent event){
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
                mostrarMenuChiquito(elem);
            }
        });
            controlador.setContadorElemento(controlador.getContadorElemento()+1);
    
    }
    
    @FXML
    private void crearEspectro(ActionEvent event) {
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
                mostrarMenuChiquito(elem);
                
            }
        });
            controlador.setContadorElemento(controlador.getContadorElemento()+1);
    }
    
    @FXML
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    public void leerAuxiliar() throws FileNotFoundException, IOException{
        File doc =
        new File("auxiliar.txt");
        Scanner obj = new Scanner(doc);
        //Componente componente= new Componente();
        StringTokenizer st = new StringTokenizer(obj.nextLine(),",");
        String nombre = st.nextToken();
        st.nextToken();
        //componente.setNombre(nombre);
        //componente.setId(controlador.getContadorElemento());
        int id = controlador.getContadorElemento();
        //componente.setConectado(Boolean.parseBoolean(st.nextToken()));
        //controlador.getElementos().add(componente);
        
        Label dibujo= new Label();
        switch (nombre) {
            case "fibra":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
                Fibra fibra= new Fibra(nombre,id,st.nextToken(),Boolean.parseBoolean(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Double.parseDouble(st.nextToken()),Double.parseDouble(st.nextToken()),Double.parseDouble(st.nextToken()));
                controlador.getElementos().add(fibra);
                break;
            case "fuente":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_fuenteR.png")));
                Fuente fuente= new Fuente(nombre, id,st.nextToken(),Boolean.parseBoolean(st.nextToken()), Integer.parseInt(st.nextToken()),Double.parseDouble(st.nextToken()),Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()), Integer.parseInt(st.nextToken()));
                controlador.getElementos().add(fuente);
                break;
            case "conector":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_conectorR.png")));
                Conector conector= new Conector(nombre, id, st.nextToken(),Boolean.parseBoolean(st.nextToken()), Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Double.parseDouble(st.nextToken()));
                controlador.getElementos().add(conector);
                break;
            case "empalme":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_empalme.png")));
                Empalme empalne= new Empalme(nombre, id, st.nextToken(),Boolean.parseBoolean(st.nextToken()), Integer.parseInt(st.nextToken()),Double.parseDouble(st.nextToken()), Integer.parseInt(st.nextToken()));
                controlador.getElementos().add(empalne);
                break;
            case "splitter16":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter16.png")));
                Splitter splitter= new Splitter(nombre, id, st.nextToken(),Boolean.parseBoolean(st.nextToken()),Integer.parseInt(st.nextToken()),Double.parseDouble(st.nextToken()), Integer.parseInt(st.nextToken()));
                controlador.getElementos().add(splitter);
                break;
            case "splitter32":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter32.png")));
                break;
            case "splitter64":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter64.png")));
                break;
            case "splitter128":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter128.png")));
                break;
            default:
                break;
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("auxiliar.txt"))) {
            bw.write("");
        }
        
        doc.delete();
        dibujo.setText(nombre + "_"+ controlador.getContadorElemento());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        
        /*--------------------------------------------------------------------*/
        
        File file = new File("elementos.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(dibujo.getText()+"\n");
        }
        
        /*--------------------------------------------------------------------*/
        
        ElementoGrafico elem = new ElementoGrafico();
        elem.setComponente(nombre);
        elem.setDibujo(dibujo);

        elem.setId(controlador.getContadorElemento());
        controlador.getDibujos().add(elem);
        Pane1.getChildren().add(dibujo);
        
            elem.getDibujo().setOnMouseDragged((MouseEvent event) -> {
                if(event.getButton()==MouseButton.PRIMARY){
                    elem.getDibujo().setLayoutX(event.getSceneX()-20);
                    elem.getDibujo().setLayoutY(event.getSceneY()-170);
                    elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                }
        });
            elem.getDibujo().setOnMouseEntered((MouseEvent event) -> {
                elem.getDibujo().setStyle("-fx-border-color: darkblue;");
                elem.getDibujo().setCursor(Cursor.OPEN_HAND);
        });
            elem.getDibujo().setOnMouseExited((MouseEvent event) -> {
                elem.getDibujo().setStyle("");
        });
            elem.getDibujo().setOnMouseClicked((MouseEvent event) -> {
                if(event.getButton()==MouseButton.PRIMARY){
                    if(elem.getDibujo().getText().contains("fibra")){
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("VentanaFibra.fxml"));
                            Scene scene = new Scene(root);
                            Image ico = new Image("images/acercaDe.png");
                            Stage stage = new Stage(StageStyle.UTILITY);
                            stage.getIcons().add(ico);
                            stage.setTitle("OptiUAM BC Fibra");
                            stage.setScene(scene);
                            stage.showAndWait();
                            stage.setResizable(false);
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    else if(elem.getDibujo().getText().contains("fuente")){
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("VentanaFuente.fxml"));
                            Scene scene = new Scene(root);
                            Image ico = new Image("images/acercaDe.png");
                            Stage stage = new Stage(StageStyle.UTILITY);
                            stage.getIcons().add(ico);
                            stage.setTitle("OptiUAM BC Fuente");
                            stage.setScene(scene);
                            stage.showAndWait();
                            stage.setResizable(false);
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    else if(elem.getDibujo().getText().contains("conector")){
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("VentanaConector.fxml"));
                            Scene scene = new Scene(root);
                            Image ico = new Image("images/acercaDe.png");
                            Stage stage = new Stage(StageStyle.UTILITY);
                            stage.getIcons().add(ico);
                            stage.setTitle("OptiUAM BC Conector");
                            stage.setScene(scene);
                            stage.showAndWait();
                            stage.setResizable(false);
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    else if(elem.getDibujo().getText().contains("empalme")){
                        System.out.println("Hola empalme");
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("VentanaEmpalme.fxml"));
                            Scene scene = new Scene(root);
                            Image ico = new Image("images/acercaDe.png");
                            Stage stage = new Stage(StageStyle.UTILITY);
                            stage.getIcons().add(ico);
                            stage.setTitle("OptiUAM BC Empalme");
                            stage.setScene(scene);
                            stage.showAndWait();
                            stage.setResizable(false);
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    else if(elem.getDibujo().getText().contains("splitter")){
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("VentanaSplitter.fxml"));
                            Scene scene = new Scene(root);
                            Image ico = new Image("images/acercaDe.png");
                            Stage stage = new Stage(StageStyle.UTILITY);
                            stage.getIcons().add(ico);
                            stage.setTitle("OptiUAM BC Splitter");
                            stage.setScene(scene);
                            stage.showAndWait();
                            stage.setResizable(false);
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Holi");
                        alert.setHeaderText(null);
                        alert.setContentText("\nAun no esta implementado");
                        alert.showAndWait();
                    }
                }else if(event.getButton()==MouseButton.SECONDARY){
                    mostrarMenuChiquito(elem);
                }
        });
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
    }
    
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
    }
    
    public void duplicar(Componente comp) throws IOException{
        Label dibujo= new Label();
        switch (comp.getNombre()) {
            case "fibra":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
                break;
            case "fuente":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_fuenteR.png")));
                break;
            case "conector":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_conectorR.png")));
                break;
            case "empalme":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_empalme.png")));
                break;
            case "splitter16":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter16.png")));
                break;
            case "splitter32":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter32.png")));
                break;
            case "splitter64":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter64.png")));
                break;
            case "splitter128":
                dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter128.png")));
                break;
            default:
                break;
        }
        
        dibujo.setText(comp.getNombre() + "_"+ controlador.getContadorElemento());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        
        /*--------------------------------------------------------------------*/
        
        File file = new File("elementos.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(dibujo.getText()+"\n");
        }
        
        /*--------------------------------------------------------------------*/
        
        ElementoGrafico elem = new ElementoGrafico();
        elem.setComponente(comp.getNombre());
        elem.setDibujo(dibujo);

        elem.setId(controlador.getContadorElemento());
        controlador.getDibujos().add(elem);
        Pane1.getChildren().add(dibujo);
        
            elem.getDibujo().setOnMouseDragged((MouseEvent event) -> {
                if(event.getButton()==MouseButton.PRIMARY){
                    elem.getDibujo().setLayoutX(event.getSceneX()-20);
                    elem.getDibujo().setLayoutY(event.getSceneY()-170);
                    elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                }
        });
            elem.getDibujo().setOnMouseEntered((MouseEvent event) -> {
                elem.getDibujo().setStyle("-fx-border-color: darkblue;");
                elem.getDibujo().setCursor(Cursor.OPEN_HAND);
        });
            elem.getDibujo().setOnMouseExited((MouseEvent event) -> {
                elem.getDibujo().setStyle("");
        });
            elem.getDibujo().setOnMouseClicked((MouseEvent event) -> {
                if(event.getButton()==MouseButton.PRIMARY){
                    if(elem.getDibujo().getText().contains("fibra")){
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("VentanaFibra.fxml"));
                            Scene scene = new Scene(root);
                            Image ico = new Image("images/acercaDe.png");
                            Stage stage = new Stage(StageStyle.UTILITY);
                            stage.getIcons().add(ico);
                            stage.setTitle("OptiUAM BC Fibra");
                            stage.setScene(scene);
                            stage.showAndWait();
                            stage.setResizable(false);
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    else if(elem.getDibujo().getText().contains("fuente")){
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("VentanaFuente.fxml"));
                            Scene scene = new Scene(root);
                            Image ico = new Image("images/acercaDe.png");
                            Stage stage = new Stage(StageStyle.UTILITY);
                            stage.getIcons().add(ico);
                            stage.setTitle("OptiUAM BC Fuente");
                            stage.setScene(scene);
                            stage.showAndWait();
                            stage.setResizable(false);
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    else if(elem.getDibujo().getText().contains("conector")){
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("VentanaConector.fxml"));
                            Scene scene = new Scene(root);
                            Image ico = new Image("images/acercaDe.png");
                            Stage stage = new Stage(StageStyle.UTILITY);
                            stage.getIcons().add(ico);
                            stage.setTitle("OptiUAM BC Conector");
                            stage.setScene(scene);
                            stage.showAndWait();
                            stage.setResizable(false);
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    else if(elem.getDibujo().getText().contains("empalme")){
                        System.out.println("Hola empalme");
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("VentanaEmpalme.fxml"));
                            Scene scene = new Scene(root);
                            Image ico = new Image("images/acercaDe.png");
                            Stage stage = new Stage(StageStyle.UTILITY);
                            stage.getIcons().add(ico);
                            stage.setTitle("OptiUAM BC Empalme");
                            stage.setScene(scene);
                            stage.showAndWait();
                            stage.setResizable(false);
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    else if(elem.getDibujo().getText().contains("splitter")){
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("VentanaSplitter.fxml"));
                            Scene scene = new Scene(root);
                            Image ico = new Image("images/acercaDe.png");
                            Stage stage = new Stage(StageStyle.UTILITY);
                            stage.getIcons().add(ico);
                            stage.setTitle("OptiUAM BC Splitter");
                            stage.setScene(scene);
                            stage.showAndWait();
                            stage.setResizable(false);
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Holi");
                        alert.setHeaderText(null);
                        alert.setContentText("\nAun no esta implementado");
                        alert.showAndWait();
                    }
                }else if(event.getButton()==MouseButton.SECONDARY){
                    mostrarMenuChiquito(elem);
                }
        });
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        
    }    

}
