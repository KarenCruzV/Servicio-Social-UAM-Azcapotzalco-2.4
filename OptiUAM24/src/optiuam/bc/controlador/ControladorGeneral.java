
package optiuam.bc.controlador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
 *
 * @author Arturo Borja
 */
public class ControladorGeneral {
    
    public LinkedList<Componente> elementos; // contiene los elementos creados en la simulacion
    public LinkedList<ElementoGrafico> dibujos; //Contiene los elementos mostrados en el panel
    public int contadorElemento; // contador para asignar id a un elemento
    public VentanaPrincipal ventana_principal;//para tener la comunicacion vista-controlador
    public ElementoGrafico manejadorElementos; // para crear los elementos graficos

    public ControladorGeneral() {
        elementos = new LinkedList();
        dibujos = new LinkedList();
        contadorElemento=0;
    }
    
    public ControladorGeneral setControlador(){
        return this;
    }

    public LinkedList<Componente> getElementos() {
        return elementos;
    }

    public void setElementos(LinkedList<Componente> elementos) {
        this.elementos = elementos;
    }
    
    public LinkedList<ElementoGrafico> getDibujos() {
        return dibujos;
    }

    public void setDibujos(LinkedList<ElementoGrafico> dibujos) {
        this.dibujos = dibujos;
    }

    public int getContadorElemento() {
        return contadorElemento;
    }

    public void setContadorElemento(int contadorElemento) {
        this.contadorElemento = contadorElemento;
    }

    public VentanaPrincipal getVentana_principal() {
        return ventana_principal;
    }

    public void setVentana_principal(VentanaPrincipal ventana_principal) {
        this.ventana_principal = ventana_principal;
    }

    public ElementoGrafico getManejadorElementos() {
        return manejadorElementos;
    }

    public void setManejadorElementos(ElementoGrafico manejadorElementos) {
        this.manejadorElementos = manejadorElementos;
    }

    public ControladorGeneral(LinkedList<Componente> elementos, 
            LinkedList<ElementoGrafico> dibujos, int contadorElemento, 
            VentanaPrincipal ventana_principal, ElementoGrafico manejadorElementos) {
        this.elementos = elementos;
        this.dibujos = dibujos;
        this.contadorElemento = contadorElemento;
        this.ventana_principal = ventana_principal;
        this.manejadorElementos = manejadorElementos;
    }
    
    public void nuevoTrabajo(ActionEvent event) {
        //ventana_principal.dispose(); //se oculta y elimina la ventana principal
        //ControladorGeneral controlador = new ControladorGeneral();
        //ventana_principal= new VentanaPrincipal();
        
        //controlador.setVentana_principal(ventana_principal);
        //ventana_principal.setControlador(controlador);
        
        //ventana_principal.setVisible(true);
        //lo de arriba es como lo tiene el shavo
        
        //ventana_principal.cerrarVentana(event); si lo pongo, marca error de
        // java.lang.NullPointerException
        //si no lo pongo, no se cierra la ventana anterior :V
        try {
            Parent root = FXMLLoader.load(getClass().getResource("VentanaPrincipal.fxml"));
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.getIcons().add(ico);
            stage.setTitle("OptiUAM BC");
            stage.setScene(scene);
            stage.showAndWait();
            stage.setOnCloseRequest(e-> Platform.exit());
            stage.setOnCloseRequest(e-> System.exit(0));
            //stage.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(ControladorGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ElementoGrafico obtenerDibujo(int id){
        for(int i = 0 ; i < dibujos.size();i++)
            if(dibujos.get(i).getId()==id)
                return dibujos.get(i);
        return null;
    }
    
    public void guardarTrabajo(String ruta_archivo){
        //se comprueba que el usuario no le ponga extension al archivo
        if(ruta_archivo.contains(".opt")){
           ruta_archivo= ruta_archivo.split(".opt")[0];//se le quita el .opt
            //System.out.println(ruta_archivo);
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
                if(aux.contains("conector")){
                    Conector conector = (Conector) elementos.get(i);
                    pw.println(conector.toString()+","+obtenerDibujo(aux1).getDibujo().getLayoutX()+
                            ","+obtenerDibujo(aux1).getDibujo().getLayoutY());
                }
                else if(aux.contains("empalme")){
                    Empalme empalme= (Empalme) elementos.get(i);
                    pw.println(empalme.toString()+","+obtenerDibujo(aux1).getDibujo().getLayoutX()+
                            ","+obtenerDibujo(aux1).getDibujo().getLayoutY());
                }
                else if(aux.contains("fibra")){
                    Fibra fibra = (Fibra) elementos.get(i);
                    pw.println(fibra.toString()+","+obtenerDibujo(aux1).getDibujo().getLayoutX()+
                            ","+obtenerDibujo(aux1).getDibujo().getLayoutY());
                }
                else if(aux.contains("splitter")){
                    Splitter splitter = (Splitter) elementos.get(i);
                    pw.println(splitter.toString()+","+obtenerDibujo(aux1).getDibujo().getLayoutX()+
                            ","+obtenerDibujo(aux1).getDibujo().getLayoutY());
                    pw.println(splitter.Conexiones());
                }
                else if(aux.contains("fuente")){
                    Fuente fuente = (Fuente) elementos.get(i);
                    pw.println(fuente.toString()+","+obtenerDibujo(aux1).getDibujo().getLayoutX()+
                            ","+obtenerDibujo(aux1).getDibujo().getLayoutY());
                }
                else if(aux.contains("potencia")){
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
           // aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
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
