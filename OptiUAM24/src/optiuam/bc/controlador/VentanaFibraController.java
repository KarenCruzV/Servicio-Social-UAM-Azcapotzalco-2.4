
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
import javafx.geometry.Bounds;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Fibra;

/**
 * FXML Controller class
 *
 * @author j
 */
public class VentanaFibraController extends VentanaPrincipal implements Initializable {
    
    static int idFibra = 0;
    ControladorGeneral controlador;
    Stage stage;
    ElementoGrafico elemG;
    VentanaFibraController fibraControl;
    static double posX, posY;
    
    @FXML
    private TextField txtAtenue, txtDisp, txtDistancia;
    
    @FXML 
    private RadioButton rbtnMono, rbtnMulti, rbtn1310, rbtn1550, rbtnOtro, rbtn28, rbtn50;
    
    @FXML
    public Button btnCrear, btnDesconectar, btnModificar;
    
    @FXML
    public Label lblConectarA;
    
    @FXML
    public ComboBox cboxConectarA;
    
    @FXML
    Separator separator;
    
    @FXML
    private ScrollPane scroll;

    public static double getPosX() {
        return posX;
    }

    public static int getIdFibra() {
        return idFibra;
    }

    public static void setIdFibra(int idFibra) {
        VentanaFibraController.idFibra = idFibra;
    }

    public static void setPosX(double posX) {
        VentanaFibraController.posX = posX;
    }

    public static double getPosY() {
        return posY;
    }

    public static void setPosY(double posY) {
        VentanaFibraController.posY = posY;
    }
    
    @Override
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnModificar.setVisible(false);
        separator.setVisible(false);
    } 
    
    @FXML
    public void Desconectar(ActionEvent event){
        for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
                if(fibraControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                    Componente comp= controlador.getElementos().get(elemento2);
                    comp.setConectadoEntrada(false);
                    comp.setElementoConectadoEntrada("");
                    System.out.println(comp.getNombre());
                    break;
                }
        }
        fibraControl.cboxConectarA.getSelectionModel().select(0);
        //elemG.getComponente().setConectadoEntrada(false);
        if(elemG.getComponente().isConectadoSalida()){
            elemG.getComponente().setConectadoSalida(false);
            elemG.getComponente().setElementoConectadoSalida("");
            elemG.getComponente().getLinea().setVisible(false);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("\nDisconnected fiber!");
            alert.showAndWait();
        cerrarVentana(event);
    }
    
    @FXML
    public void rbtn1310Action(ActionEvent event){
        rbtn1310.setSelected(true);
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
    public void rbtn1550Action(ActionEvent event){
        rbtn1550.setSelected(true);
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
    public void rbtnMm50(ActionEvent event){
        rbtn50.setSelected(true);
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
    public void rbtnSmf28(ActionEvent event){
        rbtn28.setSelected(true);
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
            } 
            else { // monomodo 1550
                txtAtenue.setText("0.18");
                txtAtenue.setEditable(false);
                txtDisp.setText("18");
                txtDisp.setEditable(false);
            }
        }
    }
    
    @FXML
    public void rbtnOtro(ActionEvent event){
        rbtnOtro.setSelected(true);
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
    public void modificar(ActionEvent event){
        Fibra aux = (Fibra) elemG.getComponente();
        int modo=0, longitudOnda=0, tipo=0, id = 0;
        double longitudKm, atenue, dispersion;

        if(rbtnMulti.isSelected()) // multimodo
        {
            modo = 1;
            rbtnMulti.setSelected(true);
        }
        if (rbtn1550.isSelected()) // 1550 nm
        {
            longitudOnda = 1550;
            rbtn1550.setSelected(true);
        }
        if (rbtn1310.isSelected()) // 1310 nm
        {
            longitudOnda = 1310;
            rbtn1310.setSelected(true);
        }
        if (rbtn50.isSelected()) // mm50
        {
            tipo = 1;
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtn1550.setDisable(true);
            rbtnMono.setDisable(true);
            rbtn50.setSelected(true);
        }
        if(rbtn28.isSelected()){ // smf28
            tipo = 0;
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtnMulti.setDisable(true);
            rbtn28.setSelected(true);
        }
        if(rbtnOtro.isSelected()){tipo=2;} //otro

        if((fibraControl.cboxConectarA.getSelectionModel().getSelectedIndex())==0){
            //aux.setConectadoEntrada(false);
            //aux.setConectadoSalida(false);
            //aux.setElementoConectadoSalida(null);
            Desconectar(event);
        }
        else{
            if(aux.isConectadoSalida()){elemG.getComponente().getLinea().setVisible(false);}
            aux.setConectadoSalida(true);
            
            for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
                if(fibraControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                    ElementoGrafico eg= controlador.getDibujos().get(elemento2);
                    aux.setElementoConectadoSalida(eg.getDibujo().getText());
                    aux.setConectadoSalida(true);
                    System.out.println(controlador.getDibujos().get(elemento2).getComponente().toString());
                    eg.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                    eg.getComponente().setConectadoEntrada(true);
                    break;
                }
            }
            dibujarLinea(elemG);
        }

        longitudKm= Double.parseDouble(txtDistancia.getText());
        atenue= Double.parseDouble(txtAtenue.getText());
        dispersion= Double.parseDouble(txtDisp.getText());
        aux.setAtenuacion(atenue);
        aux.setDispersion(dispersion);
        aux.setLongitudOnda(longitudOnda);
        aux.setModo(modo);
        aux.setLongitud_km(longitudKm);
        aux.setTipo(tipo);
        
        cerrarVentana(event);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\nModified fiber!");
        alert.showAndWait();
        
        Tooltip proFibra = new Tooltip();
        String tip, mod;
        if(aux.getModo() == 0){
            mod = "Monomode";
            switch (aux.getTipo()) {
                case 0:
                    tip = "smf-28";
                    proFibra.setText("Name: "+aux.getNombre()+
                            "\nId = "+aux.getIdFibra()+
                            "\nInput: "+aux.getElementoConectadoEntrada()+
                            "\nOutput :"+aux.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+aux.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+aux.getLongitud_km()+" km"+
                            "\nAttenuation: "+aux.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+aux.getDispersion()+" ps/(nm*km)");
                    break;
                case 1:
                    tip = "mm50";
                    proFibra.setText("Name: "+aux.getNombre()+
                            "\nId = "+aux.getIdFibra()+
                            "\nInput: "+aux.getElementoConectadoEntrada()+
                            "\nOutput :"+aux.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+aux.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+aux.getLongitud_km()+" km"+
                            "\nAttenuation: "+aux.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+aux.getDispersion()+" ps/(nm*km)");
                    break;
                case 2:
                    tip = "Other";
                    proFibra.setText("Name: "+aux.getNombre()+
                            "\nId = "+aux.getIdFibra()+
                            "\nInput: "+aux.getElementoConectadoEntrada()+
                            "\nOutput :"+aux.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+aux.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+aux.getLongitud_km()+" km"+
                            "\nAttenuation: "+aux.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+aux.getDispersion()+" ps/(nm*km)");
                    break;
                default:
                    break;
            }
        }
        else if(aux.getModo() == 1){
            mod = "Multimode";
            switch (aux.getTipo()) {
                case 0:
                    tip = "smf-28";
                    proFibra.setText("Name: "+aux.getNombre()+
                            "\nId = "+aux.getIdFibra()+
                            "\nInput: "+aux.getElementoConectadoEntrada()+
                            "\nOutput :"+aux.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+aux.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+aux.getLongitud_km()+" km"+
                            "\nAttenuation: "+aux.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+aux.getDispersion()+" ps/(nm*km)");
                    break;
                case 1:
                    tip = "mm50";
                    proFibra.setText("Name: "+aux.getNombre()+
                            "\nId = "+aux.getIdFibra()+
                            "\nInput: "+aux.getElementoConectadoEntrada()+
                            "\nOutput :"+aux.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+aux.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+aux.getLongitud_km()+" km"+
                            "\nAttenuation: "+aux.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+aux.getDispersion()+" ps/(nm*km)");
                    break;
                case 2:
                    tip = "Other";
                    proFibra.setText("Name: "+aux.getNombre()+
                            "\nId = "+aux.getIdFibra()+
                            "\nInput: "+aux.getElementoConectadoEntrada()+
                            "\nOutput :"+aux.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+aux.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+aux.getLongitud_km()+" km"+
                            "\nAttenuation: "+aux.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+aux.getDispersion()+" ps/(nm*km)");
                    break;
                default:
                    break;
            }
        }
        elemG.getDibujo().setTooltip(proFibra);
        
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    public void enviarDatos(ActionEvent event){
        int modo=0, longitudOnda=0, tipo=0, id = 0;
        double longitudKm, atenue, dispersion;
        
        if (rbtnMulti.isSelected()) // multimodo
        {
            modo = 1;
            rbtnMulti.setSelected(true);
        }else{
            modo=0;
            rbtnMono.setSelected(true);
        }
        if (rbtn1550.isSelected()) // 1550 nm
        {
            longitudOnda = 1550;
            rbtn1550.setSelected(true);
        }else{
            longitudOnda=1310;
            rbtn1310.setSelected(true);
        }
        if (rbtn50.isSelected()) // mm50
        {
            tipo = 1;
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtn1550.setDisable(true);
            rbtnMono.setDisable(true);
            rbtn50.setSelected(true);
        }
        if(rbtn28.isSelected()){ // smf28
            tipo = 0;
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtnMulti.setDisable(true);
            rbtn28.setSelected(true);
        }
        if(rbtnOtro.isSelected()){tipo=2;} //otro
        
        longitudKm= Double.parseDouble(txtDistancia.getText());
        atenue= Double.parseDouble(txtAtenue.getText());
        dispersion= Double.parseDouble(txtDisp.getText());
        Fibra f= new Fibra();
        f.setAtenuacion(atenue);
        f.setDispersion(dispersion);
        f.setLongitudOnda(longitudOnda);
        f.setModo(modo);
        f.setLongitud_km(longitudKm);
        f.setTipo(tipo);
        f.setIdFibra(idFibra);
        f.setConectadoEntrada(false);
        f.setConectadoSalida(false);
        guardarComponente(f);
        idFibra++;
        cerrarVentana(event);
    }
    
    public void guardarComponente(Fibra fibra){
        fibra.setNombre("fiber"); 
        fibra.setId(controlador.getContadorElemento());
        controlador.getElementos().add(fibra);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        fibra.setPosX(dibujo.getLayoutX());
        fibra.setPosY(dibujo.getLayoutY());
        setPosX(fibra.getPosX());
        setPosY(fibra.getPosY());
        
        elem.setComponente(fibra);
        elem.setId(controlador.getContadorElemento());
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
        dibujo.setText(fibra.getNombre() + "_"+ fibra.getIdFibra());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\nFiber created!");
        alert.showAndWait();
        
        Tooltip proFibra = new Tooltip();
        String tip, mod;
        if(fibra.getModo() == 0){
            mod = "Monomode";
            switch (fibra.getTipo()) {
                case 0:
                    tip = "smf-28";
                    proFibra.setText("Name: "+fibra.getNombre()+
                            "\nId = "+fibra.getIdFibra()+
                            "\nInput: "+fibra.getElementoConectadoEntrada()+
                            "\nOutput :"+fibra.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+fibra.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+fibra.getLongitud_km()+" km"+
                            "\nAttenuation: "+fibra.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+fibra.getDispersion()+" ps/(nm*km)");
                    break;
                case 1:
                    tip = "mm50";
                    proFibra.setText("Name: "+fibra.getNombre()+
                            "\nId = "+fibra.getIdFibra()+
                            "\nInput: "+fibra.getElementoConectadoEntrada()+
                            "\nOutput :"+fibra.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+fibra.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+fibra.getLongitud_km()+" km"+
                            "\nAttenuation: "+fibra.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+fibra.getDispersion()+" ps/(nm*km)");
                    break;
                case 2:
                    tip = "Other";
                    proFibra.setText("Name: "+fibra.getNombre()+
                            "\nId = "+fibra.getIdFibra()+
                            "\nInput: "+fibra.getElementoConectadoEntrada()+
                            "\nOutput :"+fibra.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+fibra.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+fibra.getLongitud_km()+" km"+
                            "\nAttenuation: "+fibra.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+fibra.getDispersion()+" ps/(nm*km)");
                    break;
                default:
                    break;
            }
        }
        else if(fibra.getModo() == 1){
            mod = "Multimode";
            switch (fibra.getTipo()) {
                case 0:
                    tip = "smf-28";
                    proFibra.setText("Name: "+fibra.getNombre()+
                            "\nId = "+fibra.getIdFibra()+
                            "\nInput: "+fibra.getElementoConectadoEntrada()+
                            "\nOutput :"+fibra.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+fibra.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+fibra.getLongitud_km()+" km"+
                            "\nAttenuation: "+fibra.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+fibra.getDispersion()+" ps/(nm*km)");
                    break;
                case 1:
                    tip = "mm50";
                    proFibra.setText("Name: "+fibra.getNombre()+
                            "\nId = "+fibra.getIdFibra()+
                            "\nInput: "+fibra.getElementoConectadoEntrada()+
                            "\nOutput :"+fibra.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+fibra.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+fibra.getLongitud_km()+" km"+
                            "\nAttenuation: "+fibra.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+fibra.getDispersion()+" ps/(nm*km)");
                    break;
                case 2:
                    tip = "Other";
                    proFibra.setText("Name: "+fibra.getNombre()+
                            "\nId = "+fibra.getIdFibra()+
                            "\nInput: "+fibra.getElementoConectadoEntrada()+
                            "\nOutput :"+fibra.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+fibra.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+fibra.getLongitud_km()+" km"+
                            "\nAttenuation: "+fibra.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+fibra.getDispersion()+" ps/(nm*km)");
                    break;
                default:
                    break;
            }
        }
        elem.getDibujo().setTooltip(proFibra);
    }
    
    public void duplicarFibra(Fibra fibra,ElementoGrafico el){
        fibra.setNombre("fiber"); 
        fibra.setId(controlador.getContadorElemento());
        controlador.getElementos().add(fibra);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        fibra.setPosX(dibujo.getLayoutX());
        fibra.setPosY(dibujo.getLayoutY());
        setPosX(fibra.getPosX());
        setPosY(fibra.getPosY());
        
        elem.setComponente(fibra);
        elem.setId(controlador.getContadorElemento());
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
        dibujo.setText(fibra.getNombre() + "_"+ fibra.getIdFibra());
        dibujo.setContentDisplay(ContentDisplay.TOP);
            dibujo.setLayoutX(el.getDibujo().getLayoutX()+35);
            dibujo.setLayoutY(el.getDibujo().getLayoutY()+20);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\nDuplicate fiber!");
        alert.showAndWait();
        
        Tooltip proFibra = new Tooltip();
        String tip, mod;
        if(fibra.getModo() == 0){
            mod = "Monomode";
            switch (fibra.getTipo()) {
                case 0:
                    tip = "smf-28";
                    proFibra.setText("Name: "+fibra.getNombre()+
                            "\nId = "+fibra.getIdFibra()+
                            "\nInput: "+fibra.getElementoConectadoEntrada()+
                            "\nOutput :"+fibra.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+fibra.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+fibra.getLongitud_km()+" km"+
                            "\nAttenuation: "+fibra.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+fibra.getDispersion()+" ps/(nm*km)");
                    break;
                case 1:
                    tip = "mm50";
                    proFibra.setText("Name: "+fibra.getNombre()+
                            "\nId = "+fibra.getIdFibra()+
                            "\nInput: "+fibra.getElementoConectadoEntrada()+
                            "\nOutput :"+fibra.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+fibra.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+fibra.getLongitud_km()+" km"+
                            "\nAttenuation: "+fibra.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+fibra.getDispersion()+" ps/(nm*km)");
                    break;
                case 2:
                    tip = "Other";
                    proFibra.setText("Name: "+fibra.getNombre()+
                            "\nId = "+fibra.getIdFibra()+
                            "\nInput: "+fibra.getElementoConectadoEntrada()+
                            "\nOutput :"+fibra.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+fibra.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+fibra.getLongitud_km()+" km"+
                            "\nAttenuation: "+fibra.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+fibra.getDispersion()+" ps/(nm*km)");
                    break;
                default:
                    break;
            }
        }
        else if(fibra.getModo() == 1){
            mod = "Multimode";
            switch (fibra.getTipo()) {
                case 0:
                    tip = "smf-28";
                    proFibra.setText("Name: "+fibra.getNombre()+
                            "\nId = "+fibra.getIdFibra()+
                            "\nInput: "+fibra.getElementoConectadoEntrada()+
                            "\nOutput :"+fibra.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+fibra.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+fibra.getLongitud_km()+" km"+
                            "\nAttenuation: "+fibra.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+fibra.getDispersion()+" ps/(nm*km)");
                    break;
                case 1:
                    tip = "mm50";
                    proFibra.setText("Name: "+fibra.getNombre()+
                            "\nId = "+fibra.getIdFibra()+
                            "\nInput: "+fibra.getElementoConectadoEntrada()+
                            "\nOutput :"+fibra.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+fibra.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+fibra.getLongitud_km()+" km"+
                            "\nAttenuation: "+fibra.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+fibra.getDispersion()+" ps/(nm*km)");
                    break;
                case 2:
                    tip = "Other";
                    proFibra.setText("Name: "+fibra.getNombre()+
                            "\nId = "+fibra.getIdFibra()+
                            "\nInput: "+fibra.getElementoConectadoEntrada()+
                            "\nOutput :"+fibra.getElementoConectadoSalida()+
                            "\nType: "+tip+ "\nMode: "+mod+
                            "\nWavelenght: "+fibra.getLongitudOnda()+" nm"+
                            "\nFiber lenght: "+fibra.getLongitud_km()+" km"+
                            "\nAttenuation: "+fibra.getAtenuacion() +" dB/km"+
                            "\nDispersion: "+fibra.getDispersion()+" ps/(nm*km)");
                    break;
                default:
                    break;
            }
        }
        elem.getDibujo().setTooltip(proFibra);
    }
    
    public void eventos(ElementoGrafico elem){
        elem.getDibujo().setOnMouseDragged((MouseEvent event) -> {
            if(event.getButton()==MouseButton.PRIMARY){
                double newX=event.getSceneX();
                double newY=event.getSceneY();
                int j=0;
                for(int a=0; a<Pane1.getChildren().size();a++){
                    if(Pane1.getChildren().get(a).toString().contains(elem.getDibujo().getText())){
                        j=a;
                        break;
                    }
                }
                if( outSideParentBoundsX(elem.getDibujo().getLayoutBounds(), newX, newY) ) {    //return; 
                }else{
                    elem.getDibujo().setLayoutX(Pane1.getChildren().get(j).getLayoutX()+event.getX()+1);
                }
                /*
                if(elem.getDibujo().getLayoutX()>=0.0){
                    elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                    elem.getDibujo().setLayoutX((scroll.getHvalue()*200)+event.getSceneX()-20);
                }else{
                    elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                    elem.getDibujo().setLayoutX(0.0);
                }
                if(elem.getDibujo().getLayoutY()>=0.0){
                    elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                    elem.getDibujo().setLayoutY((scroll.getVvalue()*200)+event.getSceneY()-170);
                }else{
                    elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                    elem.getDibujo().setLayoutY(0);
                }
                */
                if(outSideParentBoundsY(elem.getDibujo().getLayoutBounds(), newX, newY) ) {    //return; 
                }else{
                elem.getDibujo().setLayoutY(Pane1.getChildren().get(j).getLayoutY()+event.getY()+1);}

                if(elem.getComponente().isConectadoSalida()==true){
                    borrarLinea(elem.getComponente().getLinea());
                    dibujarLinea(elem);
                }
                if(elem.getComponente().isConectadoEntrada()){
                    ElementoGrafico aux;
                    for(int it=0; it<controlador.getDibujos().size();it++){
                        if(elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                            aux=controlador.getDibujos().get(it);
                            borrarLinea(aux.getComponente().getLinea());
                        }
                    }
                    dibujarLineaAtras(elem);
                }
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
                    try{
                        Stage stage1 = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaFibra.fxml"));
                        Parent root = loader.load();
                        //Se crea una instancia del controlador del conector
                        VentanaFibraController fibraController = (VentanaFibraController) loader.getController();
                        fibraController.init(controlador, stage, Pane1,scroll);
                        /*Se necesito usar otro init de forma que el controller sepa cual es el elemento
                            con el que se esta trabajando ademas de que se manda el mismo controller para 
                            iniciar con los valores del elemento mandado.
                        */
                        
                        fibraController.btnCrear.setVisible(false);
                        fibraController.btnDesconectar.setVisible(true);
                        fibraController.lblConectarA.setVisible(true);
                        fibraController.cboxConectarA.setVisible(true);
                        fibraController.btnModificar.setVisible(true);
                        fibraController.separator.setVisible(true);
                        fibraController.init2(elem,fibraController);
                        Scene scene = new Scene(root);
                        Image ico = new Image("images/acercaDe.png");
                        stage1.getIcons().add(ico);
                        stage1.setTitle("OptiUAM BC "+elem.getDibujo().getText().toUpperCase());
                        stage1.initModality(Modality.APPLICATION_MODAL);
                        stage1.setScene(scene);
                        stage1.setResizable(false);
                        stage1.showAndWait();
                    }
                    catch(IOException ex){
                        Logger.getLogger(VentanaConectorController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }else if(event.getButton()==MouseButton.SECONDARY){
                    mostrarMenuChiquitoFibra(elem);
                }
        });
    }
    
    public void mostrarMenuChiquitoFibra(ElementoGrafico dibujo){
        // create a menu
        ContextMenu contextMenu = new ContextMenu();

        // create menuitems
        MenuItem menuItem1 = new MenuItem("-Duplicate");
        //MenuItem menuItem2 = new MenuItem("-Girar");
        MenuItem menuItem3 = new MenuItem("-Delete");

        menuItem1.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    System.out.println(dibujo.getId()+"----"+controlador.getElementos().get(elemento).getId());
                    Fibra aux= new Fibra();
                    Fibra aux1= (Fibra)controlador.getElementos().get(elemento);
                    aux.setAtenuacion(aux1.getAtenuacion());
                    aux.setDispersion(aux1.getDispersion());
                    aux.setLongitudOnda(aux1.getLongitudOnda());
                    aux.setLongitud_km(aux1.getLongitud_km());
                    aux.setModo(aux1.getModo());
                    aux.setTipo(aux1.getTipo());
                    aux.setNombre("fiber");
                    aux.setIdFibra(idFibra);
                    aux.setConectadoEntrada(false);
                    aux.setConectadoSalida(false);
                    duplicarFibra(aux,dibujo);
                    idFibra++;
                    for(int h=0; h<controlador.getElementos().size(); h++){
                        System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                        System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
                    }
                    break;
                }
            }
        });
        /*
        menuItem2.setOnAction(e ->{
            System.out.println("La fibra no gira");
        });*/

        menuItem3.setOnAction(e ->{
            if(dibujo.getComponente().isConectadoSalida()==true){
                for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                    if(dibujo.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento).getDibujo().getText())){
                        Componente aux= controlador.getElementos().get(elemento);
                        System.out.println(); 
                        aux.setConectadoEntrada(false);
                        aux.setElementoConectadoEntrada("");

                        dibujo.getComponente().getLinea().setVisible(false);
                    }
                }   
            }
            if(dibujo.getComponente().isConectadoEntrada()==true){
                for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                    if(dibujo.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(elemento).getDibujo().getText())){
                        Componente aux= controlador.getElementos().get(elemento);
                        aux.setConectadoSalida(false);
                        aux.setElementoConectadoSalida("");
                         aux.getLinea().setVisible(false);
                    }
                }
            }
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Fibra aux= (Fibra)controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux); 
                }
            }   
            
            dibujo.getDibujo().setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("\nRemoved fiber!");
            alert.showAndWait();
        });
        
        // add menu items to menu
        contextMenu.getItems().add(menuItem1);
        //contextMenu.getItems().add(menuItem2);
        contextMenu.getItems().add(menuItem3);

        dibujo.getDibujo().setContextMenu(contextMenu);
    }
    
    public void init2(ElementoGrafico elem, VentanaFibraController fibraController) {
        this.elemG=elem;
        this.fibraControl=fibraController;
        
        if(elemG.getComponente().isConectadoSalida()==true){
            fibraControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
        }
        else{
            fibraControl.cboxConectarA.getItems().add("Desconected");
            fibraControl.cboxConectarA.getSelectionModel().select(0);
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if("connector".equals(controlador.getElementos().get(elemento).getNombre()) ||
                    "splice".equals(controlador.getElementos().get(elemento).getNombre())){
                if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                    fibraControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                }
            }
            }
        }
        
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                Fibra fib= (Fibra)controlador.getElementos().get(elemento);
                System.out.println(fib.getModo()+"\t"+fib.getLongitudOnda());
                
                switch (fib.getTipo()) {
                    case 0:
                        fibraControl.rbtn28.setSelected(true);
                        break;
                    case 1:
                        fibraControl.rbtn50.setSelected(true);
                        break;
                    case 2:
                        fibraControl.rbtnOtro.setSelected(true);
                        break;
                    default:
                        break;
                }
                if(fib.getModo()==0){
                    fibraControl.rbtnMono.setSelected(true);
                }else if(fib.getModo()==1){
                    fibraControl.rbtnMulti.setSelected(true);
                }
                
                if(fib.getLongitudOnda()==1310){
                    fibraControl.rbtn1310.setSelected(true);
                }else if(fib.getLongitudOnda()==1550){
                    fibraControl.rbtn1550.setSelected(true);
                }
                fibraControl.txtAtenue.setText(String.valueOf(fib.getAtenuacion()));
                fibraControl.txtDisp.setText(String.valueOf(fib.getDispersion()));
                fibraControl.txtDistancia.setText(String.valueOf(fib.getLongitud_km()));
            }
        }
    }
    
    public void dibujarLinea(ElementoGrafico elemG) {
        Line line= new Line();   
        line.setStartX(elemG.getDibujo().getLayoutX()+elemG.getDibujo().getWidth());
        line.setStartY(elemG.getDibujo().getLayoutY()+7);
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);
        line.setEndX(aux.getDibujo().getLayoutX());
        line.setEndY(aux.getDibujo().getLayoutY());
        setLinea(line);
        //System.out.println("Se dibujo una linea");
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        elemG.getComponente().setLinea(line);
              
    }
    
    public void borrarLinea(Line linea){
        linea.setVisible(false);
    }
    
    public void dibujarLineaAtras(ElementoGrafico elem) {
        Line line= new Line();   
        ElementoGrafico aux= new ElementoGrafico();
        
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);
        line.setStartX(aux.getDibujo().getLayoutX()+aux.getDibujo().getWidth());
        line.setStartY(aux.getDibujo().getLayoutY()+10);
        line.setEndX(elem.getDibujo().getLayoutX());
        line.setEndY(elem.getDibujo().getLayoutY()+7);
        setLinea(line);
        //System.out.println("Se dibujo una linea");
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        aux.getComponente().setLinea(line);
            
    }

    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1, ScrollPane scroll) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.scroll=scroll;
    }

    public boolean outSideParentBoundsX( Bounds childBounds, double newX, double newY) {

        Bounds parentBounds = Pane1.getLayoutBounds();

        //check if too left
        if( parentBounds.getMaxX() <= (newX + childBounds.getMaxX()) ) {
            return true ;
        }

        //check if too right
        if( parentBounds.getMinX() >= (newX + childBounds.getMinX()) ) {
            return true ;
        }
        /*
        //check if too down
        if( parentBounds.getMaxY() <= (newY + childBounds.getMaxY()) ) {
            return true ;
        }

        //check if too up
        if( parentBounds.getMinY()+170 >= (newY + childBounds.getMinY()) ) {
            return true ;
        }
        */
        return false;

        /* Alternative implementation 
        Point2D topLeft = new Point2D(newX + childBounds.getMinX(), newY + childBounds.getMinY());
        Point2D topRight = new Point2D(newX + childBounds.getMaxX(), newY + childBounds.getMinY());
        Point2D bottomLeft = new Point2D(newX + childBounds.getMinX(), newY + childBounds.getMaxY());
        Point2D bottomRight = new Point2D(newX + childBounds.getMaxX(), newY + childBounds.getMaxY());
        Bounds newBounds = BoundsUtils.createBoundingBox(topLeft, topRight, bottomLeft, bottomRight);

        return ! parentBounds.contains(newBounds);
         */
    }
    
    public boolean outSideParentBoundsY( Bounds childBounds, double newX, double newY) {

        Bounds parentBounds = Pane1.getLayoutBounds();
        /*
        //check if too left
        if( parentBounds.getMaxX() <= (newX + childBounds.getMaxX()) ) {
            return true ;
        }

        //check if too right
        if( parentBounds.getMinX() >= (newX + childBounds.getMinX()) ) {
            return true ;
        }
        */
        //check if too down
        if( parentBounds.getMaxY() <= (newY + childBounds.getMaxY()) ) {
            return true ;
        }

        //check if too up
        if( parentBounds.getMinY()+179 >= (newY + childBounds.getMinY()) ) {
            return true ;
        }

        return false;

        /* Alternative implementation 
        Point2D topLeft = new Point2D(newX + childBounds.getMinX(), newY + childBounds.getMinY());
        Point2D topRight = new Point2D(newX + childBounds.getMaxX(), newY + childBounds.getMinY());
        Point2D bottomLeft = new Point2D(newX + childBounds.getMinX(), newY + childBounds.getMaxY());
        Point2D bottomRight = new Point2D(newX + childBounds.getMaxX(), newY + childBounds.getMaxY());
        Bounds newBounds = BoundsUtils.createBoundingBox(topLeft, topRight, bottomLeft, bottomRight);

        return ! parentBounds.contains(newBounds);
         */
    }
}
