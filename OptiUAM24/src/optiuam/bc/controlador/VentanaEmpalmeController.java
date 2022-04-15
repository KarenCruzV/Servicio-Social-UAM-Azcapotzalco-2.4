
package optiuam.bc.controlador;

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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Empalme;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaEmpalmeController extends ControladorGeneral implements Initializable {
    static int idEmpalme=0;
    ControladorGeneral controlador;
    Stage stage;
    ElementoGrafico elemxd;
    VentanaEmpalmeController empalmeControl;
    
    @FXML
    RadioButton rbtn1310, rbtn1550, rbtnfusion, rbtnMecanico;
    
    @FXML
    TextField txtPerdida;
    
    @FXML
    Button btnDesconectar, btnModificar, btnCrear;
    
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
            guardarEmpalme(empalme);
            idEmpalme++;
            cerrarVentana(event);
        }
    }
    private void guardarEmpalme(Empalme empalme) {
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
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("\n¡Empalme creado!");
        alert.showAndWait();
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
                    try{
                        Stage stage1 = new Stage(StageStyle.UTILITY);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaEmpalme.fxml"));
                        Parent root = loader.load();
                        //Se crea una instancia del controlador del empalme.
                        VentanaEmpalmeController empalmeController = (VentanaEmpalmeController) loader.getController();
                        empalmeController.init(controlador,stage,Pane1);
                        empalmeController.init2(elem,empalmeController);
                        empalmeController.btnCrear.setVisible(false);
                        empalmeController.btnDesconectar.setVisible(true);
                        empalmeController.lblConectarA.setVisible(true);
                        empalmeController.cboxConectarA.setVisible(true);
                        empalmeController.btnModificar.setVisible(true);
                        empalmeController.init(controlador, this.stage, this.Pane1);
                        Scene scene = new Scene(root);
                        Image ico = new Image("images/acercaDe.png");
                        stage1.getIcons().add(ico);
                        stage1.setTitle("OptiUAM BC Empalme");
                        stage1.initModality(Modality.APPLICATION_MODAL);
                        stage1.setScene(scene);
                        stage1.setResizable(false);
                        stage1.showAndWait();
                    }
                    catch(IOException ex){
                        Logger.getLogger(VentanaEmpalmeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
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
                            guardarEmpalme(empalmeAux);
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
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCrear.setVisible(true);
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnModificar.setVisible(false);
    }    
    
    @FXML
    private void modificar(ActionEvent event){
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elemxd.getId()==controlador.getElementos().get(elemento).getId()){
            Empalme aux = (Empalme) controlador.getElementos().get(elemento);
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
                aux.setConectado(false);
                //aux.setIdEmpalme(idEmpalme);
                aux.setLongitudOnda(longitudOnda);
                aux.setNombre("empalme");
                aux.setPerdidaInsercion(perdidaInsercion);
                aux.setTipo(tipo);
                cerrarVentana(event);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("\n¡Empalme modificado!");
                alert.showAndWait();
                
                System.out.println(aux.toString());
                for(int h=0; h<controlador.getElementos().size(); h++){
                    System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                    System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
                }
                break;
            }
            }
        }
    }

    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
    }
    
    private void init2(ElementoGrafico elem, VentanaEmpalmeController empalmeController) {
        this.elemxd=elem;
        this.empalmeControl=empalmeController;
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                Empalme emp= (Empalme)controlador.getElementos().get(elemento);
                System.out.println(emp.getTipo()+"\t"+emp.getLongitudOnda());
                if(emp.getTipo()==0){
                    empalmeControl.rbtnfusion.setSelected(true);
                }else if(emp.getTipo()==1){
                    empalmeControl.rbtnMecanico.setSelected(true);
                }
                if(emp.getLongitudOnda()==1310){
                    empalmeControl.rbtn1310.setSelected(true);
                }else if(emp.getLongitudOnda()==1550){
                    empalmeControl.rbtn1550.setSelected(true);
                }
                empalmeControl.txtPerdida.setText(String.valueOf(emp.getPerdidaInsercion()));
            }
        }
    }
    
}
