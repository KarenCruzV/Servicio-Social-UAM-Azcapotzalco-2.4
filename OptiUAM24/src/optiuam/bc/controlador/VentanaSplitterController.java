
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
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static optiuam.bc.controlador.VentanaConectorController.idConector;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Splitter;
import optiuam.bc.vista.VentanaPrincipal;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaSplitterController extends ControladorGeneral implements Initializable {
    static int idS=0;
    ControladorGeneral controlador;
    Stage principal;
    
    @FXML
    RadioButton rbtn1310, rbtn1550;
    
    @FXML
    ComboBox cboxNumeroSalidas, cboxSalidas, cboxConectarA;
    
    @FXML
    Button btnConectar, btnDesconectar, btnCrear, btnCancelar;
    
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
        for (int i = 0; i < perdidasValidas.length; i++) {
            if (perdidasValidas[i][0].compareTo(String.valueOf("1") + "," + String.valueOf(salidas)) == 0) {
                if (perdida >= Double.parseDouble(perdidasValidas[i][1]) && perdida <= Double.parseDouble(perdidasValidas[i][2])) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
    
    public String buscarPerdidas(int salidas) {
        for (int i = 0; i < perdidasValidas.length; i++) {
            if (perdidasValidas[i][0].compareTo(String.valueOf("1") + "," + String.valueOf(salidas)) == 0) {
                return "min: " + String.valueOf(perdidasValidas[i][1]) + ", max: " + String.valueOf(perdidasValidas[i][2]);
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
        
        BufferedReader br = null;
        try {
            separator.setVisible(false);
            btnDesconectar.setVisible(false);
            btnConectar.setVisible(false);
            lblConectarA.setVisible(false);
            cboxConectarA.setVisible(false);
            lblSalida.setVisible(false);
            cboxSalidas.setVisible(false);
            
            /*----------------------------------------------------------------*/
            
            br = new BufferedReader(new FileReader("elementos.txt"));
            String linea;
            cboxConectarA.getItems().removeAll(cboxConectarA.getItems());
            while ((linea = br.readLine()) != null){
                if(!linea.contains("splitter")){
                    if(linea.contains("conector") || linea.contains("potencia")){
                        cboxConectarA.getItems().add(linea);
                        cboxConectarA.getSelectionModel().selectFirst();
                    }
                }
                
            } 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VentanaSplitterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VentanaSplitterController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
                
            } catch (IOException ex) {
                Logger.getLogger(VentanaSplitterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
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
            s.setConectado(false);
            s.setPerdidaInsercion(perdida);
            s.setSalidas(salidas);
            s.setLongitudOnda(longitudOnda);
            s.setNombre("splitter");
            s.setIdS(idS);
            idS++;
            guardarFuente(s);
            cerrarVentana(event);
        }
        
    }
    
    private void guardarFuente(Splitter s) {
        s.setId(controlador.getContadorElemento());
        controlador.getElementos().add(s);
        
        ElementoGrafico elem= new ElementoGrafico();
        elem.setComponente(s.getNombre());
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
                            aux.setConectado(false);
                            aux.setLongitudOnda(aux1.getLongitudOnda());
                            aux.setNombre("splitter");
                            aux.setPerdidaInsercion(aux1.getPerdidaInsercion());
                            aux.setSalidas(aux1.getSalidas());
                            aux.setIdS(idS);
                            guardarFuente(aux);
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
        separator.setVisible(true);
        btnDesconectar.setVisible(true);
        btnConectar.setVisible(true);
        lblConectarA.setVisible(true);
        cboxConectarA.setVisible(true);
        lblSalida.setVisible(true);
        cboxSalidas.setVisible(true);
    }

    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1) {
        this.controlador=controlador;
        this.principal=stage;
        this.Pane1=Pane1;
    }
    
}
