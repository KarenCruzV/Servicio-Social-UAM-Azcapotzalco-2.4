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
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static optiuam.bc.controlador.VentanaFuenteController.idFuente;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Empalme;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.vista.VentanaPrincipal;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaEmpalmeController extends ControladorGeneral implements Initializable {
    static int idEmpalme=0;
    ControladorGeneral controlador;
    Stage principal;
    @FXML
    RadioButton rbtn1310, rbtn1550, rbtnfusion, rbtnMecanico;
    
    @FXML
    TextField txtPerdida;
    
    @FXML
    Button btnDesconectar;
    
    @FXML
    Label lblConectarA;
    
    @FXML
    ComboBox cboxConectarA;
    
    @FXML
    private Pane Pane1;
   
    
    public void imprimir(ActionEvent event){
        int tipo=0, longitudOnda=0, id = 0;
        double perdidaInsercion, perdidaMax = 0.5;
        if(rbtnMecanico.isSelected()){
            tipo=1;
            //System.out.println("Tipo Mono");
        }else if(rbtnfusion.isSelected()){
            tipo=0;
            //System.out.println("Tipo Multi");
        }   
        if(rbtn1310.isSelected()){
            longitudOnda=1310;
            //System.out.println(1310);
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
            //System.out.println(1550);
        }
        
        perdidaInsercion= Double.parseDouble(txtPerdida.getText());
        
        if (txtPerdida.getText().compareTo("")==0 || !txtPerdida.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("\nValor de la pérdida invalido");
        }
        else if(Double.parseDouble(txtPerdida.getText()) > perdidaMax || Double.parseDouble(txtPerdida.getText()) < 0){
            System.out.println("\nLa pérdida debe ser" + " min: 0" + " max: " + perdidaMax);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nLa pérdida debe ser" + " min: 0" + " max: " + perdidaMax);
            alert.showAndWait();
        }
        else{
            Empalme empalme= new Empalme();
            empalme.setConectado(false);
            empalme.setIdEmpalme(idEmpalme);
            empalme.setLongitudOnda(longitudOnda);
            empalme.setNombre("empalme");
            empalme.setPerdidaInsercion(perdidaInsercion);
            empalme.setTipo(tipo);
            guardarFuente(empalme);
            idEmpalme++;
            cerrarVentana(event);
        }
    }
    private void guardarFuente(Empalme empalme) {
        empalme.setId(controlador.getContadorElemento());
        controlador.getElementos().add(empalme);
        
        ElementoGrafico elem= new ElementoGrafico();
        elem.setComponente(empalme.getNombre());
        elem.setId(controlador.getContadorElemento());
        Label dibujo= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_empalme.png")));
        dibujo.setText(empalme.getNombre() + "_"+ empalme.getIdEmpalme());
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
                    System.out.println("Hola empalme"+elem.getId());
                    
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
                            Empalme empalmeAux=new Empalme();
                            Empalme aux1=(Empalme)controlador.getElementos().get(elemento);
                            empalmeAux.setConectado(false);
                            empalmeAux.setIdEmpalme(idEmpalme);
                            empalmeAux.setLongitudOnda(aux1.getLongitudOnda());
                            empalmeAux.setPerdidaInsercion(aux1.getPerdidaInsercion());
                            empalmeAux.setTipo(aux1.getTipo());
                            empalmeAux.setNombre("empalme");
                            guardarFuente(empalmeAux);
                            //System.out.println(empalmeAux);
                            idEmpalme++;
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
                            Empalme aux= (Empalme)controlador.getElementos().get(elemento);
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
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BufferedReader br = null;
        try {
            btnDesconectar.setVisible(false);
            lblConectarA.setVisible(false);
            cboxConectarA.setVisible(false);
            
            /*----------------------------------------------------------------*/
            
            br = new BufferedReader(new FileReader("elementos.txt"));
            String linea;
            cboxConectarA.getItems().removeAll(cboxConectarA.getItems());
            while ((linea = br.readLine()) != null){
                if(!linea.contains("empalme")){
                    if(linea.contains("fibra")){
                        cboxConectarA.getItems().add(linea);
                        cboxConectarA.getSelectionModel().selectFirst();
                    }
                }
                
            } 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VentanaEmpalmeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VentanaEmpalmeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
                
            } catch (IOException ex) {
                Logger.getLogger(VentanaEmpalmeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
    
    @FXML
    private void modificar(ActionEvent event){
        btnDesconectar.setVisible(true);
        lblConectarA.setVisible(true);
        cboxConectarA.setVisible(true);
    }

    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1) {
        this.controlador=controlador;
        this.principal=stage;
        this.Pane1=Pane1;
    }
    
}
