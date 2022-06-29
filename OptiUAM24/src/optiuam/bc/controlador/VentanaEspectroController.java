
package optiuam.bc.controlador;

import java.awt.Color;
import java.awt.Font;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.FFT;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.modelo.NumeroComplejo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Clase VentanaEspectroController la cual se encarga de proporcionar la
 * funcionalidad al analizador de espectro
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class VentanaEspectroController implements Initializable {
    
    /**Controlador del simulador*/
    ControladorGeneral controlador;
    /**Elemento grafico del medidor de espectro*/
    ElementoGrafico elem;
    /**Escenario en el cual se agregaran los objetos creados*/
    Stage stage;
    
    /**Boton que permite visualizar el pulso a la entrada*/
    @FXML
    Button btnPulsoEntrada;
    /**Boton que permite visualizar el pulso a la salida*/
    @FXML
    Button btnB2;
    /**Panel para agregar objetos*/
    @FXML
    private Pane Pane1;
    /**Espacio en el cual el usuario puede desplazarse*/
    @FXML
    private ScrollPane scroll;

    /**
     * Metodo el cual inicializa la ventana del medidor de espectro
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    /**
     * Metodo para cerrar la ventana del medidor de espectro
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
      
    /**
     * Metodo que realiza los calculos para poder visualizar el pulso a la 
     * entrada correctamente
     * @param A0 Amplitud del pulso de la fuente
     * @param T0 Anchura del pulso de la fuente
     * @param W0 Frecuencia del pulso de la fuente
     * @param C Chirp del pulso de la fuente
     * @param M Pulso de la fuente gausiano o supergausiano
     * @return corrimiento logico a la derecha
     */
    public float[] pulsoEntrada(float A0,float T0,float W0,float C,float M){
        int n = 512;
        if(T0 <=35)
            n=256;
        else if(T0 >35 && T0< 100)
            n= 512;
        else
            n=1024;
     
        NumeroComplejo[] Et= calcularEnvolvente(n,false,A0,T0,W0,C,M);

        //se separa de la envolvente las partes reales y las imaginarias
        //ya que al ser un numero complejo se debe obtener la fft de cada parte tratandola como real
        //y despues crear bien el numero complejo resultante
        float[] valoresReales = new float [n];
        for(int t=-(n/2); t<(n/2);t++){
            valoresReales[(n/2)+t]= Et[(n/2)+t].getRealPart();
        }
        
        float[] valoresImaginarios = new float [n];
        for(int t=-(n/2); t<(n/2);t++){
            valoresImaginarios[(n/2)+t]= Et[(n/2)+t].getImaginaryPart();
        }
        
        //calcular la fft de las partes reales y de las imaginarias
        valoresReales=calcularFFT_real(valoresReales, n);
	valoresImaginarios=calcularFFT_real(valoresImaginarios, n);
        
        //obtner el abs(numero complejo)
        float [] absFFT= new float[n];
              for(int i = -(n/2); i <(n/2) ; i++){
                        absFFT[(n/2)+i]= new NumeroComplejo(valoresReales[(n/2)+i],valoresImaginarios[(n/2)+i]).getAmplitud();
                        //System.out.println((n/2)+i+1+" "+absFFT[(n/2)+i]);
                }
              
        //hacer el corriemiento a la derecha fftshift en Matlab
        return fftShift_float(absFFT, n);
    }
    
    /**
     * Metodo que realiza los calculos para poder visualizar el pulso a la 
     * salida correctamente
     * @param A0 Amplitud del pulso de la fuente
     * @param T0 Anchura del pulso de la fuente
     * @param W0 Frecuencia del pulso de la fuente
     * @param C Chirp del pulso de la fuente
     * @param M Pulso de la fuente gausiano o supergausiano
     * @param lista Componentes conectados antes del medidor de potencia
     * @return corrimiento logico a la derecha
     */
    public float[] pulsoSalidaB2(float A0,float T0,float W0,float C,float M,LinkedList<Componente> lista){
        //primero se encuentra la longitud total de la fibra y su longitud de onda
        float z = 0;
        int wavelength =0;
        for(int i=0; i< lista.size();i++){
            if(lista.get(i).getNombre().contains("fiber")){
                Fibra fibra = (Fibra) lista.get(i);
                z = (float) (z+fibra.getLongitud_km());
                wavelength=fibra.getLongitudOnda();
                //System.out.println(fibra.getLongitudOnda());
            }
        }
        
        //3*10^5 en nm/ps para dividirlo entre la longitud de onda(nm)
        float cluz=300000;
        //longitud de onda en donde la dispersion es 0
        float lambda0=1312;
        // pendiente de la dispersion en lambdao en ps/nm^2*Km 
        float So=0.090F; 
        
        int n = 0;
        
        if((M>=2 && M<4) && (C > 1 && C <4) && (z>100  && z<=200))
            n=32768;
        else if((M <2 && C <= 1 && z<=100))
            n= 16384;
        else
            n=131072;
        //System.out.println(n);
     
        NumeroComplejo[] Et= calcularEnvolvente(n,true,A0,T0,W0,C,M);
        //for(int i = 0 ; i < n ; i++)
        //System.out.println(i+1+"  ;"+Et[i].toString());

        //se separa de la envolvente las partes reales y las imaginarias
        //ya que al ser un numero complejo se debe obtener la la fft de cada parte tratandola como real
        //y despues crear bien el numero complejo resultante
        float[] valoresReales = new float [n];
        for(int i = 0 ; i < n ; i++){
            valoresReales[i]= Et[i].getRealPart();
            //System.out.println(Et[i].getRealPart());
        }
        
        float[] valoresImaginarios = new float [n];
        for(int i = 0 ; i < n ; i++){
            valoresImaginarios[i]= Et[i].getImaginaryPart();
            //System.out.println(Et[i].getImaginaryPart());
        }
        
        //calcular la fft de las partes reales y de las imaginrias
        valoresReales=calcularFFT_real(valoresReales, n);
	valoresImaginarios=calcularFFT_real(valoresImaginarios, n);
        
        //volvemos a ocupar el arreglo Et para guardar los valores de la transformada
        for(int i = 0 ; i < n ; i++)
            Et[i]=new NumeroComplejo(valoresReales[i], valoresImaginarios[i]);
        //System.out.println(i+1+"  ;"+valoresReales[i]+" "+ valoresImaginarios[i]+"i");
        
        //volvemos a ocupar el arreglo Et para guardar los valores del corrimiento de la FFT
         Et= fftShift_complejo(Et, n);
        
         //for(int i = 0 ; i < n ; i++)
            //System.out.println(i+1+"  ;"+Et[i].toString());
        // Calculamos B1 (ps/Km)         
        float B1=0; 
        //Calculamos B2 (ps^2/Km) 
        float Dispersion=(float) ((So/4)*(wavelength-(Math.pow(lambda0,3)/(Math.pow(wavelength,4)))));
        System.out.println(Dispersion+"  "+wavelength);
        float B2=(float) ((-Dispersion*(wavelength*wavelength))/(2*Math.PI*cluz)); 
        //System.out.println(B2);
        float naux=n;
        float w =0;
        float h1=1;
        NumeroComplejo []h2 = new NumeroComplejo[n];
        NumeroComplejo i_negativo = new NumeroComplejo(0, -1); //-i
        for(int i = 0; i <n;i++ ){
            w=(float) ((i*(10/(1*naux)))-5.0);//vector de frecuencias
            h2[i]=i_negativo.multiplicar((float)((w*w)*(4)*(Math.PI*Math.PI)*z*(-B2/2)),true);
            //System.out.println(i+1+"  ;"+h2[i].toString());
        }
        //para calcular exp(h2)
        NumeroComplejo aux2=null;
        for(int i=0; i<n;i++){
             aux2 = new NumeroComplejo(h2[i].getRealPart(),h2[i].getImaginaryPart());
             float x = aux2.getRealPart();
             float y = aux2.getImaginaryPart();
  
             aux2.setRealPart((float) (Math.exp(x)*Math.cos(y)));
             aux2.setImaginaryPart((float) (Math.exp(x)*Math.sin(y))); // lo toma engrados
        
             aux2.multiplicar(h1,false);
             h2[i]=aux2;
             
             //multiplicamos h2 * la tranformada shift;
             Et[i]= Et[i].multiplicar(h2[i],true);
             //System.out.println(i+1+"  ;"+Et[i].toString());
        }
        
        //volvemos a ocupar el arreglo Et para guardar los valores del corrimiento de la FFT * h2;
         Et= fftShift_complejo(Et, n);//respuesta de la gaussiana en  w de la fibra optica
         
        //al igual que con la fft de un numero complejo , se tiene que calcular la ifft de la parte real e imaginria
        //ambas como reales
        NumeroComplejo [] aux_Et_real = new NumeroComplejo[n];
        NumeroComplejo [] aux_Et_imaginario = new NumeroComplejo[n];
        for(int i = 0; i< n; i++){
            aux_Et_real[i]=new NumeroComplejo(Et[i].getRealPart(),0);
            aux_Et_imaginario[i]=new NumeroComplejo(Et[i].getImaginaryPart(),0);
            //System.out.println(aux_Et_real[i]);
            //System.out.println(aux_Et_imaginario[i]);
        }
        
         FFT fft = new FFT(n, 0);
         FFT.NumeroComplejoArray cna = fft.fft(valoresReales); // esto solo es para obtener un Array de complejos
         cna.setNumerosComplejos(Et);
         float ifft_real[] = fft.ifft(cna);//ifft de las partes reales
         
         FFT fft2 = new FFT(n, 0);
         FFT.NumeroComplejoArray cna2 = fft2.fft(valoresReales); // esto solo es para obtener un Array de complejos
         cna2.setNumerosComplejos(aux_Et_imaginario);
         float ifft_imaginario[] = fft2.ifft(cna2);//ifft de las partes reales
         
         //for(int i=0; i<n;i++)
         //System.out.println(i+1+"  ;"+ifft_real[i]+" "+ifft_imaginario[i]+"i");
        
        //obtner el abs(iFFT(real,imaginario))
        float [] absIFFT= new float[n];
              for(int i =0; i <n ; i++){
                        absIFFT[i]= new NumeroComplejo(ifft_real[i],ifft_imaginario[i]).getAmplitud();
                        //System.out.println(i+1+" "+absIFFT[i]);
                }
       
        //hacer el corriemiento a la derecha fftshift en Matlab
        return absIFFT;
    
    }
    
    /**
     * Metodo que calcula la envolvente del pulso
     * @param n Para calcular la envolvente
     * @param salida Precision para el pulso a la salida
     * @param A0 Amplitud del pulso de la fuente
     * @param T0 Anchura del pulso de la fuente
     * @param W0 Frecuencia del pulso de la fuente
     * @param C Chirp del pulso de la fuente
     * @param M Pulso de la fuente gausiano o supergausiano
     * @return arreglo de numeros complejos
     */
    public NumeroComplejo [] calcularEnvolvente(int n,boolean salida,float A0,float T0,float W0,float C,float M){
        //si es para el pulso de salida se necesita mas presicion
        NumeroComplejo[] Et= null;
        if(salida){
                    //n=n/10;
            NumeroComplejo complejo = new NumeroComplejo(0, 1); // i o j
            NumeroComplejo chirpXi= complejo.multiplicar(C, true);// i*C
            chirpXi.sumar(new NumeroComplejo(1, 0), false); //1 + iC
            chirpXi.multiplicar(-.5F, false); //-(1/2)*(1+iC)

            //System.out.println(chirpXi.toString());
            Et= new NumeroComplejo[n];
            NumeroComplejo aux=null;

            double naux = n;
            double t = -((naux/2)/10);

            for(int i=0; i<n;i++){
                t= Math.floor(t * 10) / 10;
                aux = new NumeroComplejo(chirpXi.getRealPart(), chirpXi.getImaginaryPart());
                aux.multiplicar((float) (Math.pow((t*t),M)/Math.pow((T0*T0),M)), false);
                Et[i]=aux;
                t = t + 0.1F;
                //System.out.println((t+1+256)+""+aux.toString());
            }

            for(int i=0; i<n;i++){
                 NumeroComplejo aux2 = new NumeroComplejo(Et[i].getRealPart(),Et[i].getImaginaryPart());
                //System.out.println((t+1+256)+";;;;"+aux2.toString());
                 float x = aux2.getRealPart();
                 float y = aux2.getImaginaryPart();


                 aux2.setRealPart((float) (Math.exp(x)*Math.cos(y)));
                 aux2.setImaginaryPart((float) (Math.exp(x)*Math.sin(y))); // lo toma engrados
                 //System.out.println(aux2.getParteReal()+"\t"+aux2.getParteImaginaria());
                 //System.out.println((t+1+256)+";;;;"+aux2.toString());
                 aux2.multiplicar(A0,false);//A0*U(0,T)
                 Et[i]=aux2;
                 //System.out.println((t+1+256)+";;;;"+Et[256+t].toString());

            }
        }else{
            NumeroComplejo complejo = new NumeroComplejo(0, 1); // i o j
            NumeroComplejo chirpXi= complejo.multiplicar(C, true);// i*C
            chirpXi.sumar(new NumeroComplejo(1, 0), false); //1 + iC
            chirpXi.multiplicar(-.5F, false); //-(1/2)*(1+iC)

            //System.out.println(chirpXi.toString());
            Et= new NumeroComplejo[n];
            NumeroComplejo aux=null;
            for(int t=-(n/2); t<(n/2);t++){
                aux = new NumeroComplejo(chirpXi.getRealPart(), chirpXi.getImaginaryPart());
                aux.multiplicar((float) (Math.pow((t*t),M)/Math.pow((T0*T0),M)), false);
                Et[(n/2)+t]=aux;
                //System.out.println((t+1+256)+""+aux.toString());
            }
            NumeroComplejo aux2=null;
            for(int t=-(n/2); t<(n/2);t++){
                 aux2 = new NumeroComplejo(Et[(n/2)+t].getRealPart(),Et[(n/2)+t].getImaginaryPart());
                //System.out.println((t+1+256)+";;;;"+aux2.toString());
                 float x = aux2.getRealPart();
                 float y = aux2.getImaginaryPart();

                 aux2.setRealPart((float) (Math.exp(x)*Math.cos(y)));
                 aux2.setImaginaryPart((float) (Math.exp(x)*Math.sin(y))); // lo toma engrados
                 //System.out.println((t+1+256)+";;;;"+aux2.toString());
                 aux2.multiplicar(A0,false);//A0*U(0,T)
                 Et[(n/2)+t]=aux2;
                 //System.out.println((t+1+256)+";;;;"+Et[256+t].toString());
            }
        }
        return Et;
    } 
    
    /**
     * Metodo que aplica un corrimiento logico a la derecha desde la mitad 
     * del arreglo de valores flotantes
     * @param valores Arreglo de valores flotantes
     * @param n Para realizar el corrimiento
     * @return corrimiento logico a la derecha
     */
    public float[] fftShift_float(float [] valores,int n){
        float [] fftshift = new float[n];
        for(int i = (n/2); i < n; i++){
            fftshift[i]=valores[i-(n/2)];
        }
        for(int j = 0; j < (n/2); j++){
            fftshift[j]=valores[j+(n/2)];
        }
        return fftshift;        
    }
    
    /**
     * Metodo que aplica un corrimiento logico a la derecha desde la mitad 
     * del arreglo de valores de numeros complejos
     * @param valores Arreglo de valores de numeros complejos
     * @param n Para realizar el corrimiento
     * @return corrimiento logico a la derecha
     */
    public NumeroComplejo[] fftShift_complejo(NumeroComplejo [] valores,int n){
        NumeroComplejo [] fftshift = new NumeroComplejo[n];
        for(int i = (n/2); i < n; i++){
            fftshift[i]=valores[i-(n/2)];
        }
        for(int j = 0; j < (n/2); j++){
            fftshift[j]=valores[j+(n/2)];
        }
        return fftshift;        
    }
    
    /**
     * Metodo que calcula la Transformada Rapida de Fourier y retorna solo la
     * parte real
     * @param datos Arreglo de datos para calcular la FFT
     * @param n Tamaño
     * @return parte real de la Transformada Rapida de Fourier
     */
    public float[] calcularFFT_real(float [] datos, int n){
        FFT fft = new FFT(n,0);	
        FFT.NumeroComplejoArray cna = fft.fft(datos);
        return cna.getPartesReales();
    }
    
    /**
     * Metodo que muestra la grafica del pulso a la entrada
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void btnPulsoEntradaAction(ActionEvent event){
        LinkedList<Componente> ele=verComponentesConectados();
        Fuente f= new Fuente();
        if(ele.getLast().getNombre().contains("source")){
            f= (Fuente)ele.getLast();
            float valores[] = pulsoEntrada(f.getA0(), f.getT0(),
                f.getW0(), f.getC(), f.getM());
        
            if(valores != null){
                XYSeries series = new XYSeries("xy");

                 //Introduccion de datos
                for(int i = -(valores.length/2); i <(valores.length/2) ; i++){
                        series.add(i,valores[i+(valores.length/2)]);
                        //System.out.println((n/2)+i+" "+series.getY((n/2)+i));
                }

                XYSeriesCollection dataset = new XYSeriesCollection();
                dataset.addSeries(series);

                JFreeChart chart = ChartFactory.createXYLineChart(
                        "Input Pulse", // Título
                        "Frequency (w)", // Etiqueta Coordenada X
                        "U(0,w)", // Etiqueta Coordenada Y
                        dataset, // Datos
                        PlotOrientation.VERTICAL,
                        true, // Muestra la leyenda de los productos (Producto A)
                        false,
                        false
                );
                chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
                chart.setBackgroundPaint(new Color(173, 216, 230));
                  //Mostramos la grafica en pantalla
                ChartFrame frame = new ChartFrame("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase(), chart);
                //frame.getChartPanel().hasFocus();
                frame.setAlwaysOnTop(true);
                //frame.setUndecorated(true);
                // Define el icono
                frame.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());
                
                frame.pack();
                frame.setVisible(true);
            }
            cerrarVentana(event);
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
     * Metodo que muestra la grafica del pulso a la salida
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void btnB2Action(ActionEvent event){
        LinkedList<Componente> ele=verComponentesConectados();
        Fuente f= new Fuente();
        if(ele.getLast().getNombre().contains("source")){
            f= (Fuente)ele.getLast();
            float valores[] = pulsoSalidaB2(f.getA0(), f.getT0(),
                f.getW0(), f.getC(), f.getM(),ele);
        
            if(valores != null){
                XYSeries series = new XYSeries("xy");

                 //Introduccion de datos
                double naux = valores.length;
                double t = -((naux/2)/10);
                for(int i=0; i<valores.length;i++){
                    t= Math.floor(t * 10) / 10;
                    series.add(t,valores[i]);
                    //System.out.println(valores[i]);
                    t = t + 0.1F;
                    //System.out.println(valores[i]);
                }

                XYSeriesCollection dataset = new XYSeriesCollection();
                dataset.addSeries(series);

                JFreeChart chart = ChartFactory.createXYLineChart(
                        "Output Pulse with B2", // Título
                        "Time in ms (t)", // Etiqueta Coordenada X
                        "U(z,t)", // Etiqueta Coordenada Y
                        dataset, // Datos
                        PlotOrientation.VERTICAL,
                        true, // Muestra la leyenda de los productos (Producto A)
                        false,
                        false
                );
                
                chart.getTitle().setFont(Font.decode("ARIAL BLACK-18"));
                chart.setBackgroundPaint(new Color(173, 216, 230));
                //Mostramos la grafica en pantalla
                ChartFrame frame = new ChartFrame("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase(), chart);
                //frame.getChartPanel().hasFocus();
                frame.setAlwaysOnTop(true);
                //frame.setUndecorated(true);
                // Define el icono
                frame.setIconImage(new ImageIcon(getClass().getResource("acercaDeGraficas.png")).getImage());
                
                frame.pack();
                frame.setVisible(true);
                
            }
            cerrarVentana(event);
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
     * Metodo que permite ver los componentes conectados antes del medidor de espectro
     * @return Componentes
     */
    public LinkedList verComponentesConectados(){
        LinkedList<Componente> lista=new LinkedList();
        System.out.println(elem.getComponente().getNombre());
        añadirComponentesConectados(lista, elem.getComponente());
        return lista;
    }
    
    /**
     * Metodo que agrega a una lista los componentes conectados antes del medidor de espectro
     * @param lista Lista de componentes
     * @param comp Componentes
     */
    public void añadirComponentesConectados(LinkedList lista, Componente comp){
        lista.add(comp);
        if(comp.isConectadoEntrada()){
            for(int i=0; i<controlador.getElementos().size(); i++){
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
