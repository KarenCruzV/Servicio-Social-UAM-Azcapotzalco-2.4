/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.vista.VentanaPrincipal;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaFuenteController extends ControladorGeneral implements Initializable {
    static int idFuente=0;
    ControladorGeneral controlador;
    Stage principal;
    @FXML
    RadioButton rbtn1310, rbtn1550, rbtnLaser, rbtnLed;
    
    @FXML
    TextField txtPotencia, txtAnchuraEspectro, txtVelocidad;
    
    @FXML
    Button btnPulso, btnCrear, btnDesconectar, btnCancelar;
    
    @FXML
    Label lblConectarA;
    
    @FXML
    ComboBox cboxConectarA;
    
    @FXML
    Separator separator;
    
    @FXML
    private Pane Pane1;
    
    public void imprimir(ActionEvent event){
        int longitudOnda=0, tipo=0, id = 0;
        double potencia, anchura, velocidad;
        
        if(rbtnLaser.isSelected()){
            tipo = 0;
        }
        else if(rbtnLed.isSelected()){
            tipo = 1;
        }
        else if(rbtn1310.isSelected()){
            longitudOnda=1310;
            //System.out.println(1310);
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
            //System.out.println(1550);
        }
        
        potencia = Double.parseDouble(txtPotencia.getText());
        anchura = Double.parseDouble(txtAnchuraEspectro.getText());
        velocidad = Double.parseDouble(txtVelocidad.getText());
        
        if (txtPotencia.getText().compareTo("")==0 || !txtPotencia.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("Valor de la potencia invalido");
        }
        else if(txtAnchuraEspectro.getText().compareTo("")==0 || !txtAnchuraEspectro.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("Valor de la anchura invalido");
        }
        else if(txtVelocidad.getText().compareTo("")==0 || !txtVelocidad.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("Valor de la velocidad invalido");
        }
        else if((tipo==0 &&Double.parseDouble(txtAnchuraEspectro.getText())<=0) ||
           (tipo==0 &&Double.parseDouble(txtAnchuraEspectro.getText())>1)){
            System.out.println("\nEl valor de la anchura debe ser max 1 nm");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El valor de la anchura debe ser max 1 nm");
            alert.showAndWait();
        }
        else if((tipo == 1 &&Double.parseDouble(txtAnchuraEspectro.getText())< (double)(0.01)) ||
           (tipo==1 &&Double.parseDouble(txtAnchuraEspectro.getText())> 1)){
            System.out.println("\nEl valor de la anchura debe ser min: 0.01 nm  max: 1.0 nm");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El valor de la anchura debe ser min: 0.01 nm  max: 1.0 nm");
            alert.showAndWait();
        } 
        else{
            Fuente fuente= new Fuente();
            fuente.setAnchura(anchura);
            fuente.setIdFuente(idFuente);
            fuente.setLongitudOnda(longitudOnda);
            fuente.setNombre("fuente");
            fuente.setPotencia(potencia);
            fuente.setTipo(tipo);
            fuente.setVelocidad(velocidad);
            fuente.setConectado(false);
            guardarFuente(fuente);
            //System.out.println(fuente);
            idFuente++;
            cerrarVentana(event);
        }

    }
    
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BufferedReader br = null;
        try {
            btnPulso.setVisible(false);
            separator.setVisible(false);
            btnDesconectar.setVisible(false);
            lblConectarA.setVisible(false);
            cboxConectarA.setVisible(false);
            
            /*----------------------------------------------------------------*/
            
            br = new BufferedReader(new FileReader("elementos.txt"));
            String linea;
            cboxConectarA.getItems().removeAll(cboxConectarA.getItems());
            while ((linea = br.readLine()) != null){
                if(!linea.contains("fuente")){
                    if(linea.contains("conector")){
                        cboxConectarA.getItems().add(linea);
                        cboxConectarA.getSelectionModel().selectFirst();
                    }
                }
                
            } 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VentanaFuenteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VentanaFuenteController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
                
            } catch (IOException ex) {
                Logger.getLogger(VentanaFuenteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
    
    @FXML
    private void modificar(ActionEvent event){
        btnPulso.setVisible(true);
        separator.setVisible(true);
        btnDesconectar.setVisible(true);
        lblConectarA.setVisible(true);
        cboxConectarA.setVisible(true);
    }
    
    @FXML
    private void configurarPulso(ActionEvent event) throws IOException {
        System.out.println("Pulso");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("VentanaPulso.fxml"));
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.getIcons().add(ico);
            stage.setTitle("OptiUAM BC Pulse Configuation");
            stage.setScene(scene);
            stage.showAndWait();
            stage.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(VentanaFuenteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1) {
        this.controlador=controlador;
        this.principal=stage;
        this.Pane1=Pane1;
    }

    private void guardarFuente(Fuente fuente) {
        fuente.setId(controlador.getContadorElemento());
        controlador.getElementos().add(fuente);
        
        ElementoGrafico elem= new ElementoGrafico();
        elem.setComponente(fuente.getNombre());
        elem.setId(controlador.getContadorElemento());
        Label dibujo= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fuente.png")));
        dibujo.setText(fuente.getNombre() + "_"+ fuente.getIdFuente());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
                
    }

    private void eventos(ElementoGrafico elem) {
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
                    System.out.println("Hola fuente"+elem.getId());
                    
                }else if(event.getButton()==MouseButton.SECONDARY){
                    mostrarMenuChiquito(elem);
                }
        });
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
                            System.out.println(dibujo.getId()+"----"+controlador.getElementos().get(elemento).getId());
                            Fuente fuenteAux=new Fuente();
                            Fuente fuenteAux1=(Fuente)controlador.getElementos().get(elemento);
                            fuenteAux.setAnchura(fuenteAux1.getAnchura());
                            fuenteAux.setConectado(false);
                            fuenteAux.setLongitudOnda(fuenteAux1.getLongitudOnda());
                            fuenteAux.setPotencia(fuenteAux1.getPotencia());
                            fuenteAux.setTipo(fuenteAux1.getTipo());
                            fuenteAux.setNombre("fuente");
                            fuenteAux.setPulso(fuenteAux1.getA0(),fuenteAux1.getT0(),fuenteAux1.getW0(),fuenteAux1.getC(),fuenteAux1.getM());
                            fuenteAux.setVelocidad(fuenteAux1.getVelocidad());
                            fuenteAux.setIdFuente(idFuente);
                            guardarFuente(fuenteAux);
                            System.out.println(fuenteAux);
                            idFuente++;
                            break;
                        }
                    }
                });
                
                menuItem2.setOnAction(e ->{
                    System.out.println("Girar");
                    System.out.println("Girar fibra");
                });
                
                menuItem3.setOnAction(e ->{
                    for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                        if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                            Fibra aux= (Fibra)controlador.getElementos().get(elemento);
                            controlador.getDibujos().remove(dibujo);
                            controlador.getElementos().remove(aux); 
                        }
                    }    
                    dibujo.getDibujo().setVisible(false);
                            
                });
                
                // add menu items to menu
                contextMenu.getItems().add(menuItem1);
                contextMenu.getItems().add(menuItem2);
                contextMenu.getItems().add(menuItem3);
                dibujo.getDibujo().setContextMenu(contextMenu);
    }
    
}
