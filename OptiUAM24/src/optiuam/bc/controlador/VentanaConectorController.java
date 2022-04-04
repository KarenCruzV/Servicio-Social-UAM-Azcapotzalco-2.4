
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
        protected Label lblTitulo, lblLongitudOnda,lblModo, lblPerdida, lbldB, labelConectarA, lblPropiedades;
    @FXML
        protected RadioButton rbtn1310, rbtn1550, rbtnMono, rbtnMulti;
    @FXML
        protected TextField txtPerdida;
    @FXML
        protected Button btnDesconectar, btnCancelar, btnCrear;
    @FXML
        protected ComboBox cmbConectarA;
    @FXML
        protected AnchorPane ConectorVentana;
    
    @FXML
    private Pane Pane1;
    
    ControladorGeneral cont;
    VentanaPrincipal ven;
    
    public void crearConector(int longitudOnda, int modo, double perdida, int id){
        Conector conector = new Conector("conector",0,longitudOnda, modo, perdida);
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
        Image icoDesconectar = new Image("/images/acercaDe.png"); 
        
        
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
            e.printStackTrace();
        }
        
    }
    
}
