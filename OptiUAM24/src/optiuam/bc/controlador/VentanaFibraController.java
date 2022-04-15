
package optiuam.bc.controlador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.vista.VentanaPrincipal;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaFibraController extends VentanaPrincipal implements Initializable {
    static int idFibra=0;
    ControladorGeneral controlador;
    Stage principal;
    private Fibra fibra;
    @FXML
    private TextField txtAtenue, txtDisp, txtDistancia;
    
    @FXML 
    private RadioButton rbtnMono, rbtnMulti, rbtn1310, rbtn1550, rbtnOtro, rbtn28, rbtn50;
    
    @FXML
    public Button btnCrear, btnDesconectar;
    
    @FXML
    public Label lblConectarA;
    
    @FXML
    public ComboBox cboxConectarA;
    
    @FXML
    public Separator separator;
    
    @Override
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        BufferedReader br = null;
        try {
            separator.setVisible(false);
            btnDesconectar.setVisible(false);
            lblConectarA.setVisible(false);
            cboxConectarA.setVisible(false);
            
            /*----------------------------------------------------------------*/
            
            br = new BufferedReader(new FileReader("elementos.txt"));
            String linea;
            cboxConectarA.getItems().removeAll(cboxConectarA.getItems());
            while ((linea = br.readLine()) != null){
                if(!linea.contains("fibra")){
                    if(linea.contains("conector")||linea.contains("empalme")){
                        cboxConectarA.getItems().add(linea);
                        cboxConectarA.getSelectionModel().selectFirst();
                    }
                }
                
            } 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VentanaFibraController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VentanaFibraController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
                
            } catch (IOException ex) {
                Logger.getLogger(VentanaFibraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }    
    
    @FXML
    private void rbtn1310Action(ActionEvent event){
        if (!rbtnOtro.isSelected()) {
            if (rbtnMono.isSelected()) { // monomodo 1310
                txtAtenue.setText("0.32");
                txtAtenue.setEditable(false);
                txtDisp.setText("0");
                txtDisp.setEditable(false);
            } else { // multimodod 1310
                txtAtenue.setText("0.36");
                txtAtenue.setEditable(false);
                txtDisp.setText("3.5");
                txtDisp.setEditable(false);
            }
        }
    }
    
    @FXML
    private void rbtn1550Action(ActionEvent event){
        if (!rbtnOtro.isSelected()) {
            if (rbtnMono.isSelected()) { // monomodo 1510
                txtAtenue.setText("0.18");
                txtAtenue.setEditable(false);
                txtDisp.setText("18");
                txtDisp.setEditable(false);
            }
        }
    }
    
    @FXML
    private void rbtnMm50(ActionEvent event){
        if (!rbtnOtro.isSelected()) {
            rbtn1310.setSelected(true);
            rbtn1550.setDisable(true);
            rbtnMulti.setSelected(true);
            rbtnMulti.setDisable(false);
            rbtnMono.setDisable(true);
            txtAtenue.setText("0.36");
            txtAtenue.setEditable(false);
            txtDisp.setText("3.5");
            txtDisp.setEditable(false);
        }
    }
    
    @FXML
    private void rbtnSmf28(ActionEvent event){
        if (!rbtnOtro.isSelected()) {
            rbtn1550.setDisable(false);
            rbtnMono.setSelected(true);
            rbtnMono.setDisable(false);
            rbtnMulti.setDisable(true);
            if (rbtn1310.isSelected()) { // monomodo 1310
                txtAtenue.setText("0.32");
                txtAtenue.setEditable(false);
                txtDisp.setText("0");
                txtDisp.setEditable(false);
            } else { // monomodo 1550
                txtAtenue.setText("0.18");
                txtAtenue.setEditable(false);
                txtDisp.setText("18");
                txtDisp.setEditable(false);
            }
        }
    }
    
    @FXML
    private void rbtnOtro(ActionEvent event){
        rbtnMulti.setDisable(false);
        rbtnMono.setDisable(false);
        rbtn1310.setDisable(false);
        rbtn1550.setDisable(false);
        txtDisp.setText("");
        txtDisp.setEditable(true);
        txtAtenue.setText("");
        txtAtenue.setEditable(true);
    }
    
    @FXML
    private void modificar(ActionEvent event){
        separator.setVisible(true);
        btnDesconectar.setVisible(true);
        lblConectarA.setVisible(true);
        cboxConectarA.setVisible(true);
    }
    
    public void enviarDatos(ActionEvent event){
        int modo=0, longitudOnda=0, tipo=0, id = 0;
        double longitudKm, atenue, dispersion;
        
        if (modo == 1) // multimodo
        {
            rbtnMulti.setSelected(true);
        }
        
        if (longitudOnda == 1550) // 1310 nm
        {
            rbtn1550.setSelected(true);
        }
        if (tipo == 1) // mm50
        {
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtn1550.setDisable(true);
            rbtnMono.setDisable(true);
            rbtn50.setSelected(true);
        }
        if(tipo ==0){ // smf28
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtnMulti.setDisable(true);
            rbtn28.setSelected(true);
        }
        if(tipo == 2){} //otro
        
        longitudKm= Double.parseDouble(txtDistancia.getText());
        atenue= Double.parseDouble(txtAtenue.getText());
        dispersion= Double.parseDouble(txtDisp.getText());
        Fibra f= new Fibra();
        f.setAtenuacion(atenue);
        f.setDispersion(dispersion);
        f.setLongitudOnda(longitudOnda);
        f.setMode(modo);
        f.setLongitud_km(longitudKm);
        f.setTipo(tipo);
        f.setIdFibra(idFibra);
        //f.setNombre("fibraEnviada");
        f.setConectado(false);
        //System.out.println(f.toString());
        guardarComponente(f);
        idFibra++;
        
        //this.fibra=f;
        //System.out.println(fibra.toString());
        
        cerrarVentana(event);
    }
    public void guardarComponente(Fibra fibra){
        fibra.setNombre("fibra");
        fibra.setId(controlador.getContadorElemento());
        controlador.getElementos().add(fibra);
        
        ElementoGrafico elem= new ElementoGrafico();
        elem.setComponente(fibra.getNombre());
        elem.setId(controlador.getContadorElemento());
        Label dibujo= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
        dibujo.setText(fibra.getNombre() + "_"+ fibra.getIdFibra());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
    }
    public void eventos(ElementoGrafico elem){
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
                    System.out.println("Hola fibra");
                    for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                        if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                           
                        }
                    }
                }else if(event.getButton()==MouseButton.SECONDARY){
                    mostrarMenuChiquito(elem);
                }
        });
    }

    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1) {
        this.controlador=controlador;
        this.principal=stage;
        this.Pane1=Pane1;
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
                            Fibra aux= new Fibra();
                            Fibra aux1= (Fibra)controlador.getElementos().get(elemento);
                            aux.setAtenuacion(aux1.getAtenuacion());
                            aux.setDispersion(aux1.getDispersion());
                            aux.setLongitudOnda(aux1.getLongitudOnda());
                            aux.setLongitud_km(aux1.getLongitud_km());
                            aux.setMode(aux1.getMode());
                            aux.setTipo(aux1.getTipo());
                            aux.setNombre("fibra");
                            aux.setIdFibra(idFibra);
                            aux.setConectado(false);
                            guardarComponente(aux);
                            idFibra++;
                            System.out.println(aux.toString());
                            for(int h=0; h<controlador.getElementos().size(); h++){
                                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
                            }
                            break;
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
                    for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                        if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                            Fibra aux= (Fibra)controlador.getElementos().get(elemento);
                            controlador.getDibujos().remove(dibujo);
                            controlador.getElementos().remove(aux); 
                        }
                    }   
                    dibujo.getDibujo().setVisible(false);
                    System.out.print(controlador.getContadorElemento());
                    for(int h=0; h<controlador.getElementos().size(); h++){
                        System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                        System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
                    }
                });
                
                
                
                // add menu items to menu
                contextMenu.getItems().add(menuItem1);
                contextMenu.getItems().add(menuItem2);
                contextMenu.getItems().add(menuItem3);
                
                dibujo.getDibujo().setContextMenu(contextMenu);
    }
    
}
