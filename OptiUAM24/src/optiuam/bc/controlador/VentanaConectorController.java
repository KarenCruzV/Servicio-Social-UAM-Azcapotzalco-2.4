
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.vista.VentanaPrincipal;


/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaConectorController extends ControladorGeneral implements Initializable {

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
    
    @FXML
    private Pane Pane1;
    
    public void crearConector(int longitudOnda, int modo, double perdida, int id){
        Conector conector = new Conector("conector",0," ",false,longitudOnda, modo, perdida);
        System.out.println("Conector creado: " + conector.toString());
        crearArchivoAux(conector.toString());
        /*
        elementos.add(conector);
        manejadorElementos = new ElementoGrafico(cont, Pane1, id, "conector");
        dibujos.add(manejadorElementos);
        manejadorElementos.dibujarConector();
        listaConector(conector);
        contadorElemento++;
        */
    }
    
    
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
    }
    
    public Componente obtenerElemento(String id) {
        for (int i = 0; i < elementos.size(); i++) {
            if (elementos.get(i).getId_nombre().compareTo(id) == 0) {
                return elementos.get(i);
            }
        }
        return null;
    }
    
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
            crearConector(longitudOnda, modo, perdidaInsercion, id);
            cerrarVentana(event);
        }
        
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
            fw.close();
        } catch (Exception e) {
            
        }
        
    }
    
    @FXML
    private void modificar(ActionEvent event){
        btnDesconectar.setVisible(true);
        lblConectarA.setVisible(true);
        cboxConectarA.setVisible(true);
    }
    
}
