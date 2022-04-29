
package optiuam.bc.controlador;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.MedidorPotencia;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaPotenciaController implements Initializable {
    
    ControladorGeneral controlador;
    
    @FXML
    Button btnCalcularPotencia;
    
    @FXML
    TextField txtSensibilidad;
    
    @FXML
    Label lblPotencia;
    
    ElementoGrafico elem;
    
    private int id;

    /**
     * Initializes the controller class.
     * @param event
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    /*public double calcularPotencia(int id,Double sensibilidad) {
        MedidorPotencia aux;
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                aux = (MedidorPotencia) controlador.manejadorElementos.getComponente();
                if(!Objects.equals(sensibilidad, aux.getSensibilidad())){//si aun un cambio en el valor de la sensibilidad
                    aux.setSensibilidad(sensibilidad);//lo guarda
                }
                if(aux.isConectadoEntrada() == false){
                    return -1;
                }
                else{
                   return aux.calcularPotencia();
                }
            }
        }
    }
    
    @FXML
    private void btnCalcularPotenciaAction(){
        if (txtSensibilidad.getText().compareTo("")==0 || !txtSensibilidad.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Valor de la sensibilidad invalido");
            alert.showAndWait();
        }
        else{
            Double potencia = calcularPotencia(id,Double.valueOf(txtSensibilidad.getText()));
            if(potencia !=-1)
                lblPotencia.setText(String.valueOf(potencia + " dB"));
            else if(potencia ==-2){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error en el calculo del enlace");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error en el enlace");
                alert.showAndWait();
            }
        }
    }*/
    
    
}
