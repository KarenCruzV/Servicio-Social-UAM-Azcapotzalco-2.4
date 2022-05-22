
package optiuam.bc.controlador;

import java.net.URL;
import java.util.LinkedList;
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
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Empalme;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.modelo.Splitter;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaPotenciaController implements Initializable {
    
    ControladorGeneral controlador;
    LinkedList<Componente> elementos;
    ElementoGrafico elem;
    
    @FXML
    Button btnCalcularPotencia;
    
    @FXML
    TextField txtSensibilidad;
    
    @FXML
    Label lblPotencia;
    
    /**
     * Initializes the controller class.
     * @param event Representa una accion del usuario en la interfaz
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public double calcularPotencia(Double sensibilidad) {
        /*MedidorPotencia aux;
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
        }*/
        double Dt= 0.0; //dispersion cromatica total
        double Pa= 0.0; //perdida por atenuacion de la fibra L*Fa 
        double S = 0.0; // anchura espectral
        double L = 0.0; // longitud de la fibra en km
        double Fa= 0.0; // atenuacion de la fibra
        double Dc= 0.0; // dispersion de la fibra
        double B = 0.0; // taza de bits
        double Pd= 0.0; //perdida por dispersion del enlace
        double Pt= 0.0; //perdida total del enlace 
        double Tp= 0.0; //potencia de la fuente dB
        //int    Tc=0;    //cantidad de conectores en el enlace
        double Pc =0.0; //perdida de los conectores
        LinkedList<Double> conectores = new LinkedList<>();//guarda las perdidas de los conectores en un enlace
        //int    Te=0;    //cantidad de empalmes en el enlace
        double Pe=0.0;  //perdida de los empalmes
        LinkedList<Double> empalmes = new LinkedList<>();//guarda las perdidas de los empalmes en un enlace
        double Ps=0.0;  //perdida del splitter
        int    Se=0;    //salidas del splitter
       // boolean isSplitter=false; //para saber si hubo un splitter en el enlace
     
        elementos = verCaminito();
        for(int o=0; o<elementos.size();o++){
            System.out.println(elementos.get(o).toString());
        }
        System.out.println("\n\n");
        System.out.println(elementos.get(Se));
        for(int i = elementos.size()-1;i>=0;i--){
            if(elementos.get(i).getNombre().contains("splitter")){
                //isSplitter=true;
                Splitter splitter_aux = (Splitter)elementos.get(i);
                Ps= splitter_aux.getPerdidaInsercion();
                Se=splitter_aux.getSalidas();
                Dt = Dc * S *L; // picosegungo x10-12
                Pd = -10 * Math.log10(1-((0.5)*Math.pow((Math.PI*(B*Math.pow(10, 9))),2)* Math.pow((Dt*Math.pow(10, -12)),2)));
                System.out.println("Splitter ---Perdida por dispersion ="+ Pd);
                Pc=perdidaConectores_Empalmes(conectores);
                System.out.println("Splitter ---Perdida conectores="+Pc);
                Pe=perdidaConectores_Empalmes(empalmes);
                System.out.println("Splitter ---Perdida empalmes="+Pe);
                Pa = L*Fa;
                System.out.println("Splitter ---Perdida atenuacion fibra="+Pa);
                // Math.pow(2,(Se+1)) -> puede ser 2,4,8,16,32,64 
                Tp=(Tp-(Pd + Pc + Pe + Pa + Ps))/Math.pow(2,(Se+1)); 
                System.out.println("Splitter ----Potencia ="+Tp);
                //inicializar los valores a 0 para los nuevos enlaces
                L=0.0;
                conectores = new LinkedList<>();
                empalmes = new LinkedList<>();
            } 
            if(elementos.get(i).getNombre().contains("source")){ //fuente
                Fuente fuente_aux = (Fuente)elementos.get(i);
                B=fuente_aux.getVelocidad();
                S=fuente_aux.getAnchura();
                Tp=fuente_aux.getPotencia();
            } 
            if(elementos.get(i).getNombre().contains("connector")){
                Conector conector_aux = (Conector)elementos.get(i);
                conectores.add(conector_aux.getPerdidaInsercion());
            }
            
            if(elementos.get(i).getNombre().contains("fiber")){
                Fibra fibra_aux = (Fibra)elementos.get(i);
                Dc = fibra_aux.getDispersion();
                Fa = fibra_aux.getAtenuacion();
                L = L + fibra_aux.getLongitud_km();
            }
            if(elementos.get(i).getNombre().contains("splice")){ //empalme
                Empalme empalme_aux = (Empalme)elementos.get(i);
                empalmes.add(empalme_aux.getPerdidaInsercion());
            }
        }
        Dt = Dc * S *L; // picosegungo x10-12
        Pd = -10 * Math.log10(1-((0.5)*Math.pow((Math.PI*(B*Math.pow(10, 9))),2)* Math.pow((Dt*Math.pow(10, -12)),2)));
        System.out.println("Perdida por dispersion ="+ Pd);
        Pc=perdidaConectores_Empalmes(conectores);
        System.out.println("Perdida conectores="+Pc);
        Pe=perdidaConectores_Empalmes(empalmes);
        System.out.println("Perdida empalmes="+Pe);
        Pa = L*Fa;
        System.out.println("Perdida atenuacion fibra="+Pa);
        Pt=(Tp-(sensibilidad))-(Pd + Pc + Pe + Pa);
        System.out.println("Potencia ="+Pt);
        System.out.println("Potencia ="+Math.floor(Pt*100)/100);
        return (Math.floor(Pt*100)/100);
    }
    
    public double perdidaConectores_Empalmes(LinkedList<Double> lista){
        Double perdidaTotal = 0.0;
        if(lista.isEmpty())
            return perdidaTotal;
        for(Double perdida:lista)
            perdidaTotal=perdidaTotal+perdida;
        return perdidaTotal;
    }
    
    @FXML
    private void btnCalcularPotenciaAction(){
        if (txtSensibilidad.getText().compareTo("")==0 || !txtSensibilidad.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid sensitivity value");
            alert.showAndWait();
        }
        else{
            Double potencia = calcularPotencia(Double.valueOf(txtSensibilidad.getText()));
            if(potencia !=-1)
                lblPotencia.setText(String.valueOf(potencia + " dB"));
            else if(potencia ==-2){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Link calculation error");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Link error");
                alert.showAndWait();
            }
        }
    }
    
    public LinkedList verCaminito(){
        LinkedList<Componente> poyo=new LinkedList();
        añadirCaminito(poyo, elem.getComponente());
        return poyo;
    }
    public void añadirCaminito(LinkedList poyo, Componente comp){
        poyo.add(comp);
        if(elem.getComponente().isConectadoEntrada()){
            for(int kc=0; kc<controlador.getElementos().size();kc++){
                if(comp.getElementoConectadoEntrada().equals(controlador.getDibujos().get(kc).getDibujo().getText())){
                    Componente aux= controlador.getElementos().get(kc);
                    añadirCaminito(poyo, aux);
                    break;
                }
            }
        }
    }

    void init(ElementoGrafico elem, ControladorGeneral controlador) {
        this.elem=elem;
        this.controlador=controlador;
    }
    
    
}
