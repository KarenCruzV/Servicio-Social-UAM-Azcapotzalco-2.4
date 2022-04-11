
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.vista.VentanaPrincipal;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaFibraController extends VentanaPrincipal implements Initializable {
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

    public Button getBtnCrear() {
        return btnCrear;
    }

    public void setBtnCrear(Button btnCrear) {
        this.btnCrear = btnCrear;
    }
    
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
    
    public void crearFibra(int longitudOnda, int modo, int tipo, double longitud_km, double atenuacion, double dispersion) {
        Fibra fibra_aux = new Fibra("fibra", 0," ",false,longitudOnda, modo, tipo, longitud_km, atenuacion, dispersion);
        System.out.println("Fibra creada: " + fibra_aux.toString()+"\n");
        System.out.println(fibra_aux);
        crearArchivoAux(fibra_aux.toString());
    }
    
    public void imprimir(ActionEvent event){
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
        crearFibra(longitudOnda, modo, tipo, longitudKm,atenue, dispersion);
        cerrarVentana(event);
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
            //e.printStackTrace();
        }
    }
    /*
    public void modificarFibra(int window, int modo, int tipo, double longitud_km,
                               double atenuacion, double dispersion, String id,String componente) {
        Fibra fibra = (Fibra) obtenerElemento(id);
        fibra.setLongitud_onda(window);
        fibra.setModo(modo);
        fibra.setTipo(tipo);
        fibra.setLongitud_km(longitud_km);
        fibra.setDispersion(dispersion);
        fibra.setAtenuacion(atenuacion);
        if (componente.compareTo(fibra.getElementoConectado()) != 0) { //es otro componente a conectar
            if (fibra.getElementoConectado().compareTo("") != 0) { // para desconectar el que tenia antes
                obtenerElemento(fibra.getElementoConectado()).setConectado(false);
            }
            fibra.setElementoConectado(componente);
            obtenerElemento(componente).setConectado(true);
        }
       // System.out.println(fibra.toString());
    }*/
    
}
