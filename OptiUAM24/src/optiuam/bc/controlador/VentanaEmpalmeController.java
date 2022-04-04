/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Empalme;
import optiuam.bc.vista.VentanaPrincipal;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaEmpalmeController extends ControladorGeneral implements Initializable {
    
    @FXML
    RadioButton rbtn1310, rbtn1550, rbtnfusion, rbtnMecanico;
    
    @FXML
    TextField txtPerdida;
    
    @FXML
    private Pane Pane1;
    
    ControladorGeneral cont;
    VentanaPrincipal ven;
    
    public void crearEmpalme(int tipo, int longitudOnda, double perdida, int id){
        Empalme empalme = new Empalme("empalme", 0,tipo, perdida, longitudOnda);
        elementos.add(empalme);
        crearArchivoAux(empalme.toString());
        /*
        manejadorElementos = new ElementoGrafico(cont, Pane1, id, "empalme");
        dibujos.add(manejadorElementos);
        manejadorElementos.dibujarEmpalme();
        listaEmpalme(empalme);
        contadorElemento++;
        */
    }
    
    public void imprimir(ActionEvent event){
        int tipo=0, longitudOnda=0, id = 0;
        double perdidaInsercion, perdidaMax = 0.5;
        if(rbtnMecanico.isSelected()){
            tipo=1;
            //System.out.println("Tipo Mono");
        }else if(rbtnfusion.isSelected()){
            tipo=0;
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
            crearEmpalme(tipo, longitudOnda, perdidaInsercion, id);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }
    
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
}
