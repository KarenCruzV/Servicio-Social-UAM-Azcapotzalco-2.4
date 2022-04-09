
package optiuam.bc.controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import optiuam.bc.modelo.Splitter;
import optiuam.bc.vista.VentanaPrincipal;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaSplitterController extends ControladorGeneral implements Initializable {

    @FXML
    RadioButton rbtn1310, rbtn1550;
    
    @FXML
    ComboBox cboxNumeroSalidas, cboxSalidas, cboxConectarA;
    
    @FXML
    Button btnConectar, btnDesconectar, btnCrear, btnCancelar;
    
    @FXML
    TextField txtPerdidaInsercion;
    
    @FXML
    private Pane Pane1;
    
    ControladorGeneral cont;
    VentanaPrincipal ven;
    
    private int id;
    
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
        
        Splitter splitter = new Splitter(aux, 0,salidas, perdida, longitudOnda);
        System.out.println("Splitter creado: " + splitter.toString() + "\n");
        crearArchivoAux(splitter.toString());
        
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
    }   
    public void crearArchivoAux(String elemento){
        try {
            String ruta = "auxiliar.txt";
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(elemento);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
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
        this.id=id;
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
            crearSplitter(longitudOnda, salidas, perdida, id);
            cerrarVentana(event);
        }
        
    }
    
}
