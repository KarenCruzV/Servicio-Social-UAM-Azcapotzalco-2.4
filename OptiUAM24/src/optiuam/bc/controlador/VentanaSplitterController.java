
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Splitter;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaSplitterController extends ControladorGeneral implements Initializable {
    
    static int idS = 0;
    ControladorGeneral controlador;
    Stage stage;
    ElementoGrafico elemG;
    VentanaSplitterController splitterControl;
    
    @FXML
    RadioButton rbtn1310, rbtn1550;
    
    @FXML
    ComboBox cboxNumeroSalidas, cboxSalidas, cboxConectarA;
    
    @FXML
    Button btnConectar, btnDesconectar, btnCrear, btnCancelar, btnModificar;
    
    @FXML
    TextField txtPerdidaInsercion;
    
    @FXML
    Label lblSalida, lblConectarA;
    
    @FXML
    Separator separator;
    
    @FXML
    private Pane Pane1;
    
    private final String perdidasValidas[][] = {{"1,0", "2.7", "4.0"},   //2
                                                {"1,1", "5.3", "7.6"},   //4
                                                {"1,2", "7.9", "10.9"},  //8
                                                {"1,3", "10.5", "14.5"}, //16
                                                {"1,4", "12.8", "18.1"}, //32
                                                {"1,5", "15.5", "21.5"}};//64
    
    public void crearSplitter(int longitudOnda, int salidas, double perdida, int id){
        
        String aux = "splitter16"; //guarda el tipo de splitter //< 16 salidas
        //saber que tipo de splitter
        if (salidas == 3) {//32 salidas
            aux = "splitter32";
        }
        if (salidas == 4) {//64 salidas
            aux = "splitter64";
        }
        if (salidas == 5) {//128 salidas
            aux = "splitter128";
        }
        
        Splitter splitter = new Splitter(aux, 0," ",false,salidas, perdida, longitudOnda);
        System.out.println("Splitter creado: " + splitter.toString() + "\n");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("\n¡Splitter creado!");
        alert.showAndWait();
    }
    
    public boolean validarPerdida(double perdida,int salidas) {
        for (String[] perdidasValida : perdidasValidas) {
            if (perdidasValida[0].compareTo(String.valueOf("1") + "," + String.valueOf(salidas)) == 0) {
                return perdida >= Double.parseDouble(perdidasValida[1]) && perdida <= Double.parseDouble(perdidasValida[2]);
            }
        }
        return false;
    }
    
    public String buscarPerdidas(int salidas) {
        for (String[] perdidasValida : perdidasValidas) {
            if (perdidasValida[0].compareTo(String.valueOf("1") + "," + String.valueOf(salidas)) == 0) {
                return "min: " + String.valueOf(perdidasValida[1]) + ", max: " + String.valueOf(perdidasValida[2]);
            }
        }
        return "";
    }
    
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cboxNumeroSalidas.getItems().removeAll(cboxNumeroSalidas.getItems());
        cboxNumeroSalidas.getItems().addAll("2", "4", "8", "16", "32", "64");
        cboxNumeroSalidas.getSelectionModel().select("2");
       
        separator.setVisible(false);
        btnDesconectar.setVisible(false);
        btnConectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        lblSalida.setVisible(false);
        cboxSalidas.setVisible(false);
        btnModificar.setVisible(false);
    }  
    
    public void imprimir(ActionEvent event){
        int salidas=0, longitudOnda=0, id=0;
        double perdida;
        
        if(longitudOnda == 1550){
            longitudOnda = 1550;
            rbtn1550.setSelected(true);
        }
        //cboxNumeroSalidas.setSelectedIndex(salidas);
        perdida = Double.parseDouble(txtPerdidaInsercion.getText());
        txtPerdidaInsercion.setText(String.valueOf(perdida));
        cboxSalidas.getItems().removeAll(cboxSalidas.getItems());
        for(int i = 0; i<((int) Math.pow(2,(salidas+1)));i++){
            cboxSalidas.getItems().add(String.valueOf(i+1));
        }
        //cboxSalidas.setSelectedIndex(0);
        
        if (!validarPerdida(Double.parseDouble(txtPerdidaInsercion.getText()),cboxNumeroSalidas.getSelectionModel().getSelectedIndex())) {
           System.out.println("La pérdida debe ser " + buscarPerdidas(cboxNumeroSalidas.getSelectionModel().getSelectedIndex()));
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nLa pérdida debe ser " +  buscarPerdidas(cboxNumeroSalidas.getSelectionModel().getSelectedIndex()));
            alert.showAndWait();
        }
        else{
            Splitter s= new Splitter();
            s.setConectadoEntrada(false);
            s.setConectadoSalida(false);
            s.setPerdidaInsercion(perdida);
            s.setSalidas(salidas);
            s.setLongitudOnda(longitudOnda);
            s.setNombre("splitter");
            s.setIdS(idS);
            idS++;
            guardarSplitter(s);
            cerrarVentana(event);
        }
        
    }
    
    private void guardarSplitter(Splitter s) {
        s.setId(controlador.getContadorElemento());
        controlador.getElementos().add(s);
        
        ElementoGrafico elem= new ElementoGrafico();
        elem.setComponente(s);
        elem.setId(controlador.getContadorElemento());
        Label dibujo= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter16.png")));
        dibujo.setText(s.getNombre() + "_"+ s.getIdS());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("\n¡Splitter creado!");
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
                    System.out.println("Hola splitter"+elem.getId());
                    try{
                        Stage stage1 = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaSplitter.fxml"));
                        Parent root = loader.load();
                        //Se crea una instancia del controlador del conector
                        VentanaSplitterController splitterController = (VentanaSplitterController) loader.getController();
                        splitterController.init(controlador, stage, Pane1);
                        /*Se necesito usar otro init de forma que el controller sepa cual es el elemento
                            con el que se esta trabajando ademas de que se manda el mismo controller para 
                            iniciar con los valores del elemento mandado.
                        */
                        splitterController.init2(elem,splitterController);
                        splitterController.btnCrear.setVisible(false);
                        splitterController.separator.setVisible(true);
                        splitterController.btnConectar.setVisible(true);
                        splitterController.lblSalida.setVisible(true);
                        splitterController.cboxSalidas.setVisible(true);
                        splitterController.btnDesconectar.setVisible(true);
                        splitterController.lblConectarA.setVisible(true);
                        splitterController.cboxConectarA.setVisible(true);
                        splitterController.btnModificar.setVisible(true);
                        splitterController.init(controlador, this.stage, this.Pane1);
                        Scene scene = new Scene(root);
                        Image ico = new Image("images/acercaDe.png");
                        stage1.getIcons().add(ico);
                        stage1.setTitle("OptiUAM BC Splitter");
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
                            Splitter aux=new Splitter();
                            Splitter aux1=(Splitter)controlador.getElementos().get(elemento);
                            aux.setConectadoEntrada(false);
                            aux.setConectadoSalida(false);
                            aux.setLongitudOnda(aux1.getLongitudOnda());
                            aux.setNombre("splitter");
                            aux.setPerdidaInsercion(aux1.getPerdidaInsercion());
                            aux.setSalidas(aux1.getSalidas());
                            aux.setIdS(idS);
                            guardarSplitter(aux);
                            //System.out.println(aux);
                            idS++;
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
                            Splitter aux= (Splitter)controlador.getElementos().get(elemento);
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
    
    @FXML
    private void modificar(ActionEvent event){
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elemG.getId()==controlador.getElementos().get(elemento).getId()){
                Splitter aux = (Splitter) controlador.getElementos().get(elemento);
                int salidas=0, longitudOnda=0, id=0;
                double perdida;

                if(longitudOnda == 1550){
                    longitudOnda = 1550;
                    rbtn1550.setSelected(true);
                }
                //cboxNumeroSalidas.setSelectedIndex(salidas);
                perdida = Double.parseDouble(txtPerdidaInsercion.getText());
                txtPerdidaInsercion.setText(String.valueOf(perdida));
                cboxSalidas.getItems().removeAll(cboxSalidas.getItems());
                for(int i = 0; i<((int) Math.pow(2,(salidas+1)));i++){
                    cboxSalidas.getItems().add(String.valueOf(i+1));
                }
                //cboxSalidas.setSelectedIndex(0);
                
                if (!validarPerdida(Double.parseDouble(txtPerdidaInsercion.getText()),cboxNumeroSalidas.getSelectionModel().getSelectedIndex())) {
                    System.out.println("La pérdida debe ser " + buscarPerdidas(cboxNumeroSalidas.getSelectionModel().getSelectedIndex()));
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("\nLa pérdida debe ser " +  buscarPerdidas(cboxNumeroSalidas.getSelectionModel().getSelectedIndex()));
                    alert.showAndWait();
                }
                else{
                    aux.setConectadoEntrada(false);
                    aux.setConectadoSalida(false);
                    aux.setPerdidaInsercion(perdida);
                    aux.setSalidas(salidas);
                    aux.setLongitudOnda(longitudOnda);
                    aux.setNombre("splitter");
                    //aux.setIdS(idS);
                    cerrarVentana(event);
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Éxito");
                    alert.setHeaderText(null);
                    alert.setContentText("\n¡Splitter modificado!");
                    alert.showAndWait();

                    System.out.println(aux.toString());
                    for(int h=0; h<controlador.getElementos().size(); h++){
                        System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                        System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
                    }
                }
            }
        }
    }

    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
    }
    
    private void init2(ElementoGrafico elem, VentanaSplitterController conectorController) {
        this.elemG=elem;
        this.splitterControl=conectorController;
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                Splitter spl= (Splitter)controlador.getElementos().get(elemento);
                System.out.println(spl.getSalidas()+"\t"+spl.getLongitudOnda());
                /*No se como poner lo de las salidas xd*/
                if(spl.getLongitudOnda()==1310){
                    splitterControl.rbtn1310.setSelected(true);
                }else if(spl.getLongitudOnda()==1550){
                    splitterControl.rbtn1550.setSelected(true);
                }
                splitterControl.txtPerdidaInsercion.setText(String.valueOf(spl.getPerdidaInsercion()));
            }
        }
    }
    
}
