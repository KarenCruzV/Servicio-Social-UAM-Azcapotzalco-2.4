
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;


/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaConectorController extends ControladorGeneral implements Initializable {
    static int idConector=0;
    ControladorGeneral controlador;
    Stage stage;
    ElementoGrafico elemxd;
    VentanaConectorController conectorControl;
    @FXML
    private Pane Pane1;
    @FXML
        Label lblTitulo, lblLongitudOnda,lblModo, lblPerdida, lbldB, lblConectarA, lblPropiedades;
    @FXML
        RadioButton rbtn1310, rbtn1550, rbtnMono, rbtnMulti;
    @FXML
        TextField txtPerdida;
    @FXML
        Button btnDesconectar, btnCancelar, btnCrear, btnModificar;
    @FXML
        ComboBox cboxConectarA;
    @FXML
        AnchorPane ConectorVentana;

    public void imprimir(ActionEvent event){
        int modo=0, longitudOnda=0, id = 0;
        double perdidaInsercion, perdidaMax =0.5;
        if(rbtnMono.isSelected()){
            modo=0;
            //System.out.println("Tipo Mono");
        }else if(rbtnMulti.isSelected()){
            perdidaMax=1.0;
            modo=1;
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
            Conector con= new Conector();
            con.setConectado(false);
            con.setIdConector(idConector);
            con.setLongitudOnda(longitudOnda);
            con.setNombre("conector");
            con.setPerdidaInsercion(perdidaInsercion);
            con.setModo(modo);
            guardarConector(con);
            idConector++;
            cerrarVentana(event);
        }
    }
    
    private void guardarConector(Conector conector) {
        conector.setId(controlador.getContadorElemento());
        controlador.getElementos().add(conector);
        
        ElementoGrafico elem= new ElementoGrafico();
        elem.setComponente(conector.getNombre());
        elem.setId(controlador.getContadorElemento());
        Label dibujo= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_conectorR.png")));
        dibujo.setText(conector.getNombre() + "_"+ conector.getIdConector());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("\n¡Conector creado!");
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
                    System.out.println("Hola conector"+elem.getId());
                    try{
                        Stage stage1 = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaConector.fxml"));
                        Parent root = loader.load();
                        //Se crea una instancia del controlador del conector
                        VentanaConectorController conectorController = (VentanaConectorController) loader.getController();
                        conectorController.init(controlador, stage, Pane1);
                        /*Se necesito usar otro init de forma que el controller sepa cual es el elemento
                            con el que se esta trabajando ademas de que se manda el mismo controller para 
                            iniciar con los valores del elemento mandado.
                        */
                        conectorController.init2(elem,conectorController);
                        conectorController.btnCrear.setVisible(false);
                        conectorController.btnDesconectar.setVisible(true);
                        conectorController.lblConectarA.setVisible(true);
                        conectorController.cboxConectarA.setVisible(true);
                        conectorController.btnModificar.setVisible(true);
                        conectorController.init(controlador, this.stage, this.Pane1);
                        Scene scene = new Scene(root);
                        Image ico = new Image("images/acercaDe.png");
                        stage1.getIcons().add(ico);
                        stage1.setTitle("OptiUAM BC Conector");
                        stage1.initModality(Modality.APPLICATION_MODAL);
                        stage1.setScene(scene);
                        stage1.setResizable(false);
                        stage1.showAndWait();
                    }
                    catch(IOException ex){
                        Logger.getLogger(VentanaConectorController.class.getName()).log(Level.SEVERE, null, ex);
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
                            Conector aux=new Conector();
                            Conector aux1=(Conector)controlador.getElementos().get(elemento);
                            aux.setConectado(false);
                            aux.setIdConector(idConector);
                            aux.setLongitudOnda(aux1.getLongitudOnda());
                            aux.setModo(aux1.getModo());
                            aux.setNombre("conector");
                            aux.setPerdidaInsercion(aux1.getPerdidaInsercion());
                            guardarConector(aux);
                            System.out.println(aux);
                            idConector++;
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
                            Conector aux= (Conector)controlador.getElementos().get(elemento);
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
            Conector aux = (Conector) controlador.getElementos().get(elemento);
            int modo=0, longitudOnda=0, id = 0;
            double perdidaInsercion, perdidaMax =0.5;
            if(rbtnMono.isSelected()){
                modo=0;
                //System.out.println("Tipo Mono");
            }else if(rbtnMulti.isSelected()){
                perdidaMax=1.0;
                modo=1;
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
                //aux.setIdConector(idConector);
                aux.setLongitudOnda(longitudOnda);
                aux.setNombre("conector");
                aux.setPerdidaInsercion(perdidaInsercion);
                aux.setModo(modo);
                cerrarVentana(event);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("\n¡Conector modificado!");
                alert.showAndWait();
                
                System.out.println(aux.toString());
                for(int h=0; h<controlador.getElementos().size(); h++){
                    System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                    System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
                }
                break;
            }
        }}
    }

    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
    }

    /*
        Funcion init2: 
        recibe el elemento y el controlador a partir de estos puede mostrar los valores inciales del elemento 
        OJO!!!! AUN NOS FALTA LO DE CONECTADO CON----
    */
    private void init2(ElementoGrafico elem, VentanaConectorController conectorController) {
        this.elemxd=elem;
        this.conectorControl=conectorController;
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                Conector con= (Conector)controlador.getElementos().get(elemento);
                System.out.println(con.getModo()+"\t"+con.getLongitudOnda());
                if(con.getModo()==0){
                    conectorControl.rbtnMono.setSelected(true);
                }else if(con.getModo()==1){
                    conectorControl.rbtnMulti.setSelected(true);
                }
                if(con.getLongitudOnda()==1310){
                    conectorControl.rbtn1310.setSelected(true);
                }else if(con.getLongitudOnda()==1550){
                    conectorControl.rbtn1550.setSelected(true);
                }
                conectorControl.txtPerdida.setText(String.valueOf(con.getPerdidaInsercion()));
            }
        }
    }
    
}
