
package optiuam.bc.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Conector;
import optiuam.bc.vista.VentanaPrincipal;


/**
 * FXML Controller class
 *
 * @author karen
 */
public class ConectorController extends VentanaPrincipal implements Initializable {
    
    
    private ArrayList<Componente> elementos; // contiene los elementos creados en la simulacion
    private int contadorElemento; // contador para asignar id a un elemento
    private VentanaPrincipal ventana_principal;//para tener la comunicacion vista-controlador
    private String componente;
    private Label dibujo;
    public Button b;

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
    public void crearConector2(int longitud_onda, int modo, double perdida){
        Conector conector = new Conector(longitud_onda, modo, perdida, "conector ", contadorElemento);
        elementos.add(conector);
        dibujarConector();
        contadorElemento++;
    }
    
    @FXML
    public void crearConector(){
        //System.out.println("xd");
        EventHandler<ActionEvent> event = (ActionEvent event1) -> {
            System.out.println("HOLA");
        };
        btnCrear.setOnAction(event);
    }
    
    public void dibujarConector(){
        if(componente.compareTo("conector")==0){
            //Image ico = new Image("iconos/dibujo_conectorR.png");
            //dibujo.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("iconos/dibujo_conectorR.png"))));
            Pane1.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream("iconos/dibujo_conectorR.png"))));
        }
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
    
    public void getLongitudOnda(ActionEvent ev){
        if(rbtn1310.isSelected()){
            System.out.println(rbtn1310.getText()+"\t"+getModo());
        }else{
            System.out.println(rbtn1550.getText()+"\t"+getModo());
        }
    }
    public String getModo(){
        String modo="";
        if(rbtnMono.isSelected()){
            modo= rbtnMono.getText();
        }else{
            modo= rbtnMulti.getText();
        }
        return modo;
    }
    
    @Override
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image icoDesconectar = new Image("/images/acercaDe.png"); 
        
        
    }    
    
}
