
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Empalme;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.modelo.Splitter;

/**
 * Clase VentanaPotenciaController la cual se encarga de proporcionar la
 * funcionalidad al medidor de potencia
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class VentanaPotenciaController implements Initializable {
    
    /**Controlador del simulador*/
    ControladorGeneral controlador;
    /**Componentes conectados antes del medidor de potencia*/
    LinkedList<Componente> elementos;
    /**Elemento grafico del medidor de potencia*/
    ElementoGrafico elem;
    /**Escenario en el cual se agregaran los objetos creados*/
    Stage stage;
    
    /**Boton para calcular la potencia*/
    @FXML
    Button btnCalcularPotencia;
    /**Caja de texto para ingresar la sensibilidad*/
    @FXML
    TextField txtSensibilidad;
    /**Etiqueta que mostrará el resultado del calculo de la potencia*/
    @FXML
    Label lblPotencia;
    /**Panel para agregar objetos*/
    @FXML
    private Pane Pane1;
    /**Espacio en el cual el usuario puede desplazarse*/
    @FXML
    private ScrollPane scroll;
    
    /**
     * Metodo el cual inicializa la ventana del medidor de potencia
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    /**
     * Metodo para cerrar la ventana del medidor de potencia
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    /**
     * Metodo que calcula la potencia
     * @param sensibilidad Sensibilidad utilizada para calcular la potencia
     * @return potencia calculada
     */
    public double calcularPotencia(Double sensibilidad) {
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
     
        elementos = verComponentesConectados();
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
            if(elementos.get(i).getNombre().contains("source")){
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
            if(elementos.get(i).getNombre().contains("splice")){ 
                Empalme empalme_aux = (Empalme)elementos.get(i);
                empalmes.add(empalme_aux.getPerdidaInsercion());
            }
        }
        Dt = Dc * S *L; // picosegungo x10^12
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
    
    /**
     * Metodo que muestra la perdida de conectores y empalmes
     * @param lista Conectores y empalmes
     * @return perdida total
     */
    public double perdidaConectores_Empalmes(LinkedList<Double> lista){
        Double perdidaTotal = 0.0;
        if(lista.isEmpty())
            return perdidaTotal;
        for(Double perdida:lista)
            perdidaTotal=perdidaTotal+perdida;
        return perdidaTotal;
    }
    
    /**
     * Metodo que muestra la potencia calculada
     */
    @FXML
    public void btnCalcularPotenciaAction(){
        LinkedList<Componente> ele=verComponentesConectados();
        if(ele.getLast().getNombre().contains("source")){
            if (txtSensibilidad.getText().compareTo("")==0 || !txtSensibilidad.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
                ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid sensitivity value",
                    aceptar);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
            else{
                Double potencia = calcularPotencia(Double.valueOf(txtSensibilidad.getText()));
                if(potencia !=-1)
                    lblPotencia.setText(String.valueOf(potencia + " dB"));
                else if(potencia ==-2){
                    ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                        "\nLink calculation error",
                        aceptar);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }
        }
        else{
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                "\nLink error",
                aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    
    /**
     * Metodo que permite ver los componentes conectados antes del medidor de 
     * potencia
     * @return Componentes
     */
    public LinkedList verComponentesConectados(){
        LinkedList<Componente> lista=new LinkedList();
        System.out.println(elem.getComponente().getNombre());
        añadirComponentesConectados(lista, elem.getComponente());
        return lista;
    }
    
    /**
     * Metodo que agrega a una lista los componentes conectados antes del medidor de potencia
     * @param lista Lista de componentes
     * @param comp Componentes
     */
    public void añadirComponentesConectados(LinkedList lista, Componente comp){
        lista.add(comp);
        if(comp.isConectadoEntrada()){
            for(int i=0; i<controlador.getElementos().size();i++){
                if(comp.getElementoConectadoEntrada().equals(controlador.getDibujos().get(i).getDibujo().getText())){
                    Componente aux= controlador.getElementos().get(i);
                    añadirComponentesConectados(lista, aux);
                    break;
                }
            }
        }
    }

    /**
     * Metodo que proporciona lo necesario para que la ventana reconozca a 
     * que elemento se refiere
     * @param controlador Controlador del simulador
     * @param stage Escenario en el cual se agregan los objetos creados
     * @param Pane1 Panel para agregar objetos
     * @param scroll Espacio en el cual el usuario puede desplazarse
     * @param elem Elemento grafico
     */
    void init(ControladorGeneral controlador, Stage stage, Pane Pane1, ScrollPane scroll, ElementoGrafico elem) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.scroll=scroll;
        this.elem=elem;
    }
    
}
