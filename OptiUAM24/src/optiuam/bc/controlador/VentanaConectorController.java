
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static optiuam.bc.controlador.VentanaFuenteController.idFuente;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;


/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaConectorController extends ControladorGeneral implements Initializable {
    static int idConector=0;
    ControladorGeneral controlador;
    Stage principal;
    @FXML
    private Pane Pane1;
    @FXML
        Label lblTitulo, lblLongitudOnda,lblModo, lblPerdida, lbldB, lblConectarA, lblPropiedades;
    @FXML
        RadioButton rbtn1310, rbtn1550, rbtnMono, rbtnMulti;
    @FXML
        TextField txtPerdida;
    @FXML
        Button btnDesconectar, btnCancelar, btnCrear;
    @FXML
        ComboBox cboxConectarA;
    @FXML
        AnchorPane ConectorVentana;
    /*
    public void modificarConector(int longitudOnda, int modo, double perdida, String id, String componente) {
        Conector conector = (Conector) obtenerElemento(id);
        conector.setLongitudOnda(longitudOnda);
        conector.setModo(modo);
        conector.setPerdidaInsercion(perdida);
        if (componente.compareTo(conector.getElementoConectado()) != 0) { //es otro componente a conectar
            if (conector.getElementoConectado().compareTo("") != 0) { // para desconectar el que tenia antes
                obtenerElemento(conector.getElementoConectado()).setConectado(false);
            }
            conector.setElementoConectado(componente);
            obtenerElemento(componente).setConectado(true);
        }
    }*/
    
    
    //public void getLongitudOnda(ActionEvent ev){
       // if(rbtn1310.isSelected()){
       //     System.out.println(rbtn1310.getText()+"\t"+getModo());
       // }else{
      //      System.out.println(rbtn1550.getText()+"\t"+getModo());
        //}
    //}
    
    //public String getModo(){
      //  String modo="";
        //if(rbtnMono.isSelected()){
          //  modo= rbtnMono.getText();
        //}else{
          //  modo= rbtnMulti.getText();
        //}
        //return modo;
    //}
    
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
            guardarFuente(con);
            idConector++;
            cerrarVentana(event);
            
        }
        
    }
    private void guardarFuente(Conector conector) {
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
                            guardarFuente(aux);
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
                if(!linea.contains("conector")){
                    if(linea.contains("fibra") || linea.contains("splitter") ||
                            linea.contains("potencia") || linea.contains("espectro")){
                        cboxConectarA.getItems().add(linea);
                        cboxConectarA.getSelectionModel().selectFirst();
                    }
                }
                
            } 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VentanaConectorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VentanaConectorController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
                
            } catch (IOException ex) {
                Logger.getLogger(VentanaConectorController.class.getName()).log(Level.SEVERE, null, ex);
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
