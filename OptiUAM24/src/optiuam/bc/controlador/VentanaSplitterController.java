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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import optiuam.bc.modelo.ElementoGrafico;
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
    private Pane Pane1;
    
    ControladorGeneral cont;
    VentanaPrincipal ven;
    
    private final String perdidasValidas[][] = {{"1,0", "2.7", "4.0"},   //2
                                                {"1,1", "5.3", "7.6"},   //4
                                                {"1,2", "7.9", "10.9"},  //8
                                                {"1,3", "10.5", "14.5"}, //16
                                                {"1,4", "12.8", "18.1"}, //32
                                                {"1,5", "15.5", "21.5"}};//64
    
    public void crearSplitter(int window, int salidas, double perdida, int id){
        Splitter splitter = new Splitter("splitter16", 0,salidas, perdida, window);
        System.out.println(splitter.toString());
        crearArchivoAux(splitter.toString());
        /*elementos.add(splitter);
        String aux = "splitter8"; //guarda el tipo de splitter //< 8 salidas
        //saber que tipo de splitter
        if (salidas == 3) {//16 salidas
            aux = "splitter16";
        }
        if (salidas == 4) {//32 salidas
            aux = "splitter32";
        }
        if (salidas == 5) {//64 salidas
            aux = "splitter64";
        }
        manejadorElementos = new ElementoGrafico(cont,  Pane1, id, "splitter " + aux);
        dibujos.add(manejadorElementos);
        manejadorElementos.dibujarSplitter();
        //listaSplitter(splitter);
        contadorElemento++;
        */
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
    public void imprimir(ActionEvent event){
        crearSplitter(contadorElemento, contadorElemento, contadorElemento, contadorElemento);
        cerrarVentana(event);
    }
    
}
