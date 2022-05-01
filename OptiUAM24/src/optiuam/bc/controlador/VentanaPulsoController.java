
package optiuam.bc.controlador;

import java.awt.Color;
import java.awt.Font;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import optiuam.bc.modelo.NumeroComplejo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaPulsoController implements Initializable {
    
    @FXML
    Button btnAplicar, btnGraficar;
    
    @FXML
    TextField txtA0, txtC, txtT0, txtW0, txtM;
    
    static float A0;
    static float T0;
    static float W0;
    static float C;
    static float M;
    
    private VentanaFuenteController ventanaFuente;//id de la ventana de la fuente !!
    String tipo="Gaussian";
    /** Creates new form VentanaPulso */
    
    private ControladorGeneral controlador;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    public void setVentanaFuenteController(VentanaFuenteController ventanaFuente){
        this.ventanaFuente= ventanaFuente;
    }
    
    public void setValores(float C,float A0,float W0,float T0,float M){
        txtC.setText(String.valueOf(C)); //chirp
        txtA0.setText(String.valueOf(A0)); //amplitud
        txtW0.setText(String.valueOf(W0)); //frecuencia
        txtT0.setText(String.valueOf(T0)); //anchura
        txtM.setText(String.valueOf(M));
        if(M > 1){
            tipo="Supergaussiano";
        }
    }
    
     private boolean validarValores(){
        if (txtA0.getText().compareTo("")==0 || !txtA0.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nValor de la amplitud invalido");
            alert.showAndWait();
            return false;
        }
        if (txtT0.getText().compareTo("")==0 || !txtT0.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nValor de la anchura invalido");
            alert.showAndWait();
            return false;
        }
        if (txtC.getText().compareTo("")==0 || !txtC.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nValor del chirp invalido");
            alert.showAndWait();
            return false;
        }
        if (txtW0.getText().compareTo("")==0 || !txtW0.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nValor de la frecuencia invalido");
            alert.showAndWait();
            return false;
        }
         if (txtM.getText().compareTo("")==0 || !txtM.getText().matches("[+-]?\\d*(\\.\\d+)?") 
                 || Float.parseFloat(txtM.getText() ) <1){
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nValor de M invalido");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    
    public void setControlador(ControladorGeneral controlador){
        this.controlador=controlador;
    }
    
    @FXML
    public void btnAplicarAction(ActionEvent event) {
        if(validarValores()){
            A0 = Float.parseFloat(txtA0.getText());
            T0 = Float.parseFloat(txtT0.getText());
            W0 = Float.parseFloat(txtW0.getText());
            C = Float.parseFloat(txtC.getText());
            M = Float.parseFloat(txtM.getText());
            
            //controlador.guardarPulso(A0,T0,W0,C,M,ventanaFuente.getId());
            //ventanaFuente.setPulso(A0, T0, W0, C,M);
            if(M > 1){
            tipo="Supergaussian";
        }
            System.out.println("C:"+C+" A0:"+A0+" W0:"+W0+ " T0:"+T0);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("\nPulso modificado");
            alert.showAndWait();
        }
    }
    
    public float[] calcularPulso(){
        float A0 = Float.parseFloat(txtA0.getText());
        float T0 = Float.parseFloat(txtT0.getText());
        float W0 = Float.parseFloat(txtW0.getText());
        float C = Float.parseFloat(txtC.getText());
        float M = Float.parseFloat(txtM.getText());
        int n = 512;
        if(T0 <=35)
            n=256;
        else if(T0 >35 && T0< 100)
            n= 512;
        else
            n=1024;
        
        NumeroComplejo complejo = new NumeroComplejo(0, 1); // i o j
        NumeroComplejo chirpXi= complejo.multiplicar(C, true);// i*C
        chirpXi.sumar(new NumeroComplejo(1, 0), false); //1 + iC
        chirpXi.multiplicar(-.5F, false); //-(1/2)*(1+iC)
    
        //System.out.println(chirpXi.toString());
        NumeroComplejo[] Et= new NumeroComplejo[n];
        NumeroComplejo aux=null;
        for(int t=-(n/2); t<(n/2);t++){
            aux = new NumeroComplejo(chirpXi.getParteReal(), chirpXi.getParteImaginaria());
            aux.multiplicar((float) (Math.pow((t*t),M)/Math.pow((T0*T0),M)), false);
            Et[(n/2)+t]=aux;
            //System.out.println((t+1+256)+""+aux.toString());
        }
        NumeroComplejo aux2=null;
        for(int t=-(n/2); t<(n/2);t++){
             aux2 = new NumeroComplejo(Et[(n/2)+t].getParteReal(),Et[(n/2)+t].getParteImaginaria());
            //System.out.println((t+1+256)+";;;;"+aux2.toString());
             float x = aux2.getParteReal();
             float y = aux2.getParteImaginaria();
  
             aux2.setParteReal((float) (Math.exp(x)*Math.cos(y)));
             aux2.setParteImaginaria((float) (Math.exp(x)*Math.sin(y))); // lo toma engrados
             //System.out.println((t+1+256)+";;;;"+aux2.toString());
             aux2.multiplicar(A0,false);
             Et[(n/2)+t]=aux2;
             //System.out.println((t+1+256)+";;;;"+Et[256+t].toString());
           
        }
        NumeroComplejo aux3=null;
        NumeroComplejo[] Ej= new NumeroComplejo[n];
        for(int t=-(n/2); t<(n/2);t++){
            //System.out.println("----------");
            aux3 = new NumeroComplejo(W0*t*0,-1*W0*t);
            //System.out.println(Et[256+t].toString());
            //System.out.println((t+1+256)+";;;;"+aux3.toString());
            float x = aux3.getParteReal();
            float y = aux3.getParteImaginaria();
            //System.out.println(x);
            //System.out.println(y);
            Ej[(n/2)+t]=Et[(n/2)+t].multiplicar(new NumeroComplejo((float) (Math.exp(x)*Math.cos(y)),(float) (Math.exp(x)*Math.sin(y))),true);
            //System.out.println((t+1+256)+";;;;"+Ej[256+t].toString());
           
        }
        
        float[] valoresReales = new float [n];
        for(int t=-(n/2); t<(n/2);t++){
            valoresReales[(n/2)+t]= Ej[(n/2)+t].getParteReal();
        }
        return valoresReales;
    }
    
    @FXML
    private void btnGraficarAction(ActionEvent event){
        float [] valores = calcularPulso();
        int n =valores.length;
        
        XYSeries series = new XYSeries("xy");
        
         //Introduccion de datos
        for(int i = -(n/2); i <(n/2) ; i++){
            series.add(i,valores[(n/2)+i]);
            //System.out.println(i+1+256+",,"+Ej[256+i].getRealPart());
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                tipo + " Pulse", // Título
                "Time (x)", // Etiqueta Coordenada X
                "U(0,t)", // Etiqueta Coordenada Y
                dataset, // Datos
                PlotOrientation.VERTICAL,
                true, // Muestra la leyenda de los productos (Producto A)
                false,
                false
        );
        chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
        chart.setBackgroundPaint(new Color(173, 216, 230));
         //Mostramos la grafica en pantalla
        ChartFrame frame = new ChartFrame("OptiUAM BC Pulse Graph "+tipo, chart);
        frame.pack();
        frame.setVisible(true);
    }
      
}
