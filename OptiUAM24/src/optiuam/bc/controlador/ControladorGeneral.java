
package optiuam.bc.controlador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Empalme;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.modelo.MedidorEspectro;
import optiuam.bc.modelo.MedidorPotencia;
import optiuam.bc.modelo.Splitter;

/**
 * Clase ControladorGeneral la cual contiene los metodos y atributos necesarios 
 * para hacer funcionar el simulador
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class ControladorGeneral {
    
    /**Elementos creados en la simulacion*/
    public LinkedList<Componente> elementos; 
    /**Elementos mostrados en el area de trabajo*/
    public LinkedList<ElementoGrafico> dibujos;
    /**Contador para asignar nombre a un elemento*/
    public int contadorElemento; 
    /**Utilizado para tener la comunicacion vista-controlador*/
    public VentanaPrincipal ventana_principal;
    /**Utilizado para crear los elementos graficos*/
    public ElementoGrafico manejadorElementos; 

    /**
     * Metodo constructor sin parametros encargado de inicializar los atributos
     de elementos, dibujos y el contador del elemento
     */
    public ControladorGeneral() {
        elementos = new LinkedList();
        dibujos = new LinkedList();
        contadorElemento=0;
    }
    
    /**
     * Metodo constructor con parametros
     * @param elementos Elementos creados en la simulacion
     * @param dibujos Elementos mostrados en el area de trabajo
     * @param contadorElemento Contador para asignar nombre a un elemento
     * @param ventana_principal Utilizado para tener la comunicacion vista-controlador
     * @param manejadorElementos Utilizado para crear los elementos graficos
     */
    public ControladorGeneral(LinkedList<Componente> elementos, 
            LinkedList<ElementoGrafico> dibujos, int contadorElemento, 
            VentanaPrincipal ventana_principal, ElementoGrafico manejadorElementos) {
        this.elementos = elementos;
        this.dibujos = dibujos;
        this.contadorElemento = contadorElemento;
        this.ventana_principal = ventana_principal;
        this.manejadorElementos = manejadorElementos;
    }
    
    /**
     * Metodo que modifica el controlador
     * @return controlador
     */
    public ControladorGeneral setControlador(){
        return this;
    }

    /**
     * Metodo que muestra los elementos creados en la simulacion
     * @return elementos creados
     */
    public LinkedList<Componente> getElementos() {
        return elementos;
    }

    /**
     * Metodo que modifica los elementos creados en la simulacion
     * @param elementos Elementos creados en la simulacion
     */
    public void setElementos(LinkedList<Componente> elementos) {
        this.elementos = elementos;
    }
    
    /**
     * Metodo que obtiene los elementos mostrados en el area de trabajo
     * @return elementos mostrados en el area de trabajo
     */
    public LinkedList<ElementoGrafico> getDibujos() {
        return dibujos;
    }

    /**
     * Metodo que modifica los elementos mostrados en el area de trabajo
     * @param dibujos Elementos mostrados en el area de trabajo
     */
    public void setDibujos(LinkedList<ElementoGrafico> dibujos) {
        this.dibujos = dibujos;
    }

    /**
     * Metodo que muestra el contador para asignar nombre a un elemento
     * @return contador del elemento
     */
    public int getContadorElemento() {
        return contadorElemento;
    }

    /**
     * Metodo que modifica el contador para asignar nombre a un elemento
     * @param contadorElemento Contador del elemento
     */
    public void setContadorElemento(int contadorElemento) {
        this.contadorElemento = contadorElemento;
    }

    /**
     * Metodo que muestra la ventana principal para tener la comunicacion 
     * vista-controlador
     * @return ventana principal
     */
    public VentanaPrincipal getVentana_principal() {
        return ventana_principal;
    }

    /**
     * Metodo que modifica la ventana principal para tener la comunicacion 
     * vista-controlador
     * @param ventana_principal  ventana principal
     */
    public void setVentana_principal(VentanaPrincipal ventana_principal) {
        this.ventana_principal = ventana_principal;
    }

    /**
     * Metodo que muestra la creacion de los elementos graficos
     * @return manejador de elementos graficos
     */
    public ElementoGrafico getManejadorElementos() {
        return manejadorElementos;
    }

    /**
     * Metodo que modifica la creacion de los elementos graficos
     * @param manejadorElementos Manejador de elementos graficos
     */
    public void setManejadorElementos(ElementoGrafico manejadorElementos) {
        this.manejadorElementos = manejadorElementos;
    }

    /**
     * Metodo utilizado para reiniciar los identificadores de los elementos y el 
     * controlador general al iniciar un nuevo trabajo
     */
    public void reset(){
        VentanaPrincipal.controlador = new ControladorGeneral();
        VentanaConectorController.idConector=0;
        VentanaEmpalmeController.idEmpalme=0;
        VentanaFibraController.idFibra=0;
        VentanaFuenteController.idFuente=0;
        VentanaSplitterController.idS=0;
        VentanaPrincipal.idEspectro=0;
        VentanaPrincipal.idPotencia=0;
    }
    
    /**
     * Metodo que obtiene el elemento grafico de un componente
     * @param id Identificador del elemento grafico
     * @return elemento grafico
     */
    public ElementoGrafico obtenerDibujo(int id){
        for(int i = 0 ; i < dibujos.size();i++)
            if(dibujos.get(i).getId()==id)
                return dibujos.get(i);
        return null;
    }
    
    /**
     * Metodo utilizado para guardar un trabajo realizado en el simulador
     * @param ruta_archivo Nombre elegido para guardar el archivo
     */
    public void guardarTrabajo(String ruta_archivo){
        //se comprueba que el usuario no le ponga extension al archivo
        if(ruta_archivo.contains(".opt")){
           ruta_archivo= ruta_archivo.split(".opt")[0];//se le quita el .opt
        }
             
        try {
            File archivo = new File(ruta_archivo+".opt");
            if(!archivo.exists())
                archivo.createNewFile();
            FileWriter fichero = null;
            PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(fichero);
            pw.println(contadorElemento);
            for(int i =0;i < elementos.size();i++){
                String aux = elementos.get(i).getNombre();
                int aux1 = elementos.get(i).getId();
                if(aux.contains("connector")){
                    Conector conector = (Conector) elementos.get(i);
                    pw.println(conector.toString()+","+obtenerDibujo(aux1).getDibujo().getLayoutX()+
                            ","+obtenerDibujo(aux1).getDibujo().getLayoutY());
                }
                else if(aux.contains("splice")){
                    Empalme empalme= (Empalme) elementos.get(i);
                    pw.println(empalme.toString()+","+obtenerDibujo(aux1).getDibujo().getLayoutX()+
                            ","+obtenerDibujo(aux1).getDibujo().getLayoutY());
                }
                else if(aux.contains("fiber")){
                    Fibra fibra = (Fibra) elementos.get(i);
                    pw.println(fibra.toString()+","+obtenerDibujo(aux1).getDibujo().getLayoutX()+
                            ","+obtenerDibujo(aux1).getDibujo().getLayoutY());
                }
                else if(aux.contains("splitter")){
                    Splitter splitter = (Splitter) elementos.get(i);
                    pw.println(splitter.toString()+","+obtenerDibujo(aux1).getDibujo().getLayoutX()+
                            ","+obtenerDibujo(aux1).getDibujo().getLayoutY());
                    //pw.println(splitter.Conexiones());
                }
                else if(aux.contains("source")){
                    Fuente fuente = (Fuente) elementos.get(i);
                    pw.println(fuente.toString()+","+obtenerDibujo(aux1).getDibujo().getLayoutX()+
                            ","+obtenerDibujo(aux1).getDibujo().getLayoutY());
                }
                else if(aux.contains("power")){
                    MedidorPotencia potencia= (MedidorPotencia) elementos.get(i);
                    pw.println(potencia.toString()+","+obtenerDibujo(aux1).getDibujo().getLayoutX()+
                            ","+obtenerDibujo(aux1).getDibujo().getLayoutY());
                }
                else{
                    MedidorEspectro espectro = (MedidorEspectro)elementos.get(i);
                    pw.println(espectro.toString()+","+obtenerDibujo(aux1).getDibujo().getLayoutX()+
                            ","+obtenerDibujo(aux1).getDibujo().getLayoutY());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // finally asegura que se cierra el fichero
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        } catch (IOException ex) {
            Logger.getLogger(ControladorGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
