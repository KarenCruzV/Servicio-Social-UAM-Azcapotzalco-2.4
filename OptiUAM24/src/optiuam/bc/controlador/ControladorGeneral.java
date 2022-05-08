
package optiuam.bc.controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public int contadorElemento; // contador para asignar nombre a un elemento
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
        try {
            VentanaPrincipal.stage.close();
            ControladorGeneral con = new ControladorGeneral();
            ventana_principal = VentanaPrincipal.class.newInstance();
            con.setVentana_principal(ventana_principal);
            ventana_principal.setControlador(con);
            OptiUAM24 op = new OptiUAM24();
            op.start(VentanaPrincipal.stage);
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ControladorGeneral.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
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
                    pw.println(splitter.Conexiones());
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
    
    
    public void abrirTrabajo(String ruta){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
             // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File (ruta);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);
            
            //se vacian los arrays de elementos
            elementos = new LinkedList<>();
            dibujos = new LinkedList<>();
            
            //se limpia el panel de trabajo
           //ventana_principal.getPane1().getChildren().clear();

           // Lectura del fichero
            String linea="";
            
            while((linea=br.readLine())!=null){
                //System.out.println(linea);
                String [] partes = linea.split(",");
                String nombre = partes[0];
                switch (nombre) {
                    case "connector":
                        Conector conector = new Conector();
                        conector.setId(Integer.valueOf(partes[1]));
                        conector.setNombre(nombre);
                        conector.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        conector.setConectadoSalida(Boolean.valueOf(partes[3]));
                        conector.setLongitudOnda(Integer.valueOf(partes[4]));
                        conector.setModo(Integer.valueOf(partes[5]));
                        conector.setPerdidaInsercion(Double.valueOf(partes[6]));
                        conector.setIdConector(Integer.valueOf(partes[7]));
                        elementos.add(conector);
                        manejadorElementos = new ElementoGrafico(this, Integer.valueOf(partes[1]), conector);
                        //manejadorElementos.dibujarComponente();
                        Label dibujo = new Label();
                        dibujo.setGraphic(new ImageView(new Image("images/dibujo_conectorR.png")));
                        dibujo.setText(conector.getNombre() + "_"+ conector.getIdConector());
                        dibujo.setContentDisplay(ContentDisplay.TOP);
                        manejadorElementos.setDibujo(dibujo);
                        //manejadorElementos.setX(Integer.valueOf(partes[7]));
                        manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[8]));
                        //manejadorElementos.setY(Integer.valueOf(partes[8]));
                        manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[9]));
                        dibujos.add(manejadorElementos);
                        //INTENTO DE QUE APAREZCA EN LA VENTANA PRINCIPAL :V
                        ElementoGrafico elem = new ElementoGrafico();
                        elem.setComponente(conector);
                        elem.setDibujo(dibujo);
                        elem.setId(VentanaPrincipal.controlador.getContadorElemento());
                        VentanaPrincipal.controlador.getDibujos().add(elem);
                        ventana_principal.Pane1.getChildren().add(dibujo);
                        //------------------------------------------------------
                        break;
                        
                    case "splice":
                        Empalme empalme = new Empalme();
                        empalme.setId(Integer.valueOf(partes[1]));
                        empalme.setNombre(nombre);
                        empalme.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        empalme.setConectadoSalida(Boolean.valueOf(partes[3]));
                        empalme.setTipo(Integer.valueOf(partes[4]));
                        empalme.setPerdidaInsercion(Double.valueOf(partes[5]));
                        empalme.setLongitudOnda(Integer.valueOf(partes[6]));
                        empalme.setIdEmpalme(Integer.valueOf(partes[7]));
                        elementos.add(empalme);
                        manejadorElementos = new ElementoGrafico(this, Integer.valueOf(partes[1]), empalme);
                        //manejadorElementos.dibujarComponente();
                        Label dibujo1 = new Label();
                        dibujo1.setGraphic(new ImageView(new Image("images/dibujo_empalme.png")));
                        dibujo1.setText(empalme.getNombre() + "_"+ empalme.getIdEmpalme());
                        dibujo1.setContentDisplay(ContentDisplay.TOP);
                        manejadorElementos.setDibujo(dibujo1);
                        //manejadorElementos.setX(Integer.valueOf(partes[7]));
                        manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[8]));
                        //manejadorElementos.setY(Integer.valueOf(partes[8]));
                        manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[9]));
                        dibujos.add(manejadorElementos);
                        break;
                        
                    case "splitter":
                        Splitter splitter = new Splitter();
                        splitter.setId(Integer.valueOf(partes[1]));
                        splitter.setNombre(nombre);
                        splitter.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        splitter.setConectadoSalida(Boolean.valueOf(partes[3]));
                        splitter.setSalidas(Integer.valueOf(partes[4]));
                        splitter.setPerdidaInsercion(Double.valueOf(partes[5]));
                        splitter.setLongitudOnda(Integer.valueOf(partes[6]));
                        splitter.setIdS(Integer.valueOf(partes[7]));
                        manejadorElementos = new ElementoGrafico(this, Integer.valueOf(partes[1]), splitter);
                        //manejadorElementos.dibujarComponente();
                        Label dibujo2 = new Label();
                        switch (splitter.getSalidas()) {
                            case 2:
                                dibujo2.setGraphic(new ImageView(new Image("images/dibujo_splitter.png")));
                                break;
                            case 4:
                            case 8:
                            case 16:
                            case 32:
                            case 64:
                                dibujo2.setGraphic(new ImageView(new Image("images/dibujo_splitter+.png")));
                                break;
                            default:
                                break;
                        }
                        dibujo2.setText(splitter.getNombre() + "_"+ splitter.getIdS());
                        dibujo2.setContentDisplay(ContentDisplay.TOP);
                        manejadorElementos.setDibujo(dibujo2);
                        //manejadorElementos.setX(Integer.valueOf(partes[7]));
                        manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[8]));
                        //manejadorElementos.setY(Integer.valueOf(partes[8]));
                        manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[9]));
                        dibujos.add(manejadorElementos);
                        linea=br.readLine();
                        String [] conexiones = linea.split(",");
                        for(int i=0;i<((int) Math.pow(2,(splitter.getSalidas()+1))); i++){
                            if(conexiones[i].compareTo(" ")==0)
                                splitter.cargarConexion(i,"");
                            else    
                                splitter.cargarConexion(i,conexiones[i]);
                        }
                        elementos.add(splitter);
                        break;
                        
                    case "fiber":
                        Fibra fibra = new Fibra();
                        fibra.setId(Integer.valueOf(partes[1]));
                        fibra.setNombre(nombre);
                        fibra.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        fibra.setConectadoSalida(Boolean.valueOf(partes[3]));
                        fibra.setLongitudOnda(Integer.valueOf(partes[4]));
                        fibra.setModo(Integer.valueOf(partes[5]));
                        fibra.setTipo(Integer.valueOf(partes[6]));
                        fibra.setLongitud_km(Double.valueOf(partes[7]));
                        fibra.setDispersion(Double.valueOf(partes[8]));
                        fibra.setAtenuacion(Double.valueOf(partes[9]));
                        fibra.setIdFibra(Integer.valueOf(partes[10]));
                        elementos.add(fibra);
                        manejadorElementos = new ElementoGrafico(this, Integer.valueOf(partes[1]), fibra);
                        //manejadorElementos.dibujarComponente();
                        Label dibujo3 = new Label();
                        dibujo3.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
                        dibujo3.setText(fibra.getNombre() + "_"+ fibra.getIdFibra());
                        dibujo3.setContentDisplay(ContentDisplay.TOP);
                        manejadorElementos.setDibujo(dibujo3);
                        //manejadorElementos.setX(Integer.valueOf(partes[7]));
                        manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[11]));
                        //manejadorElementos.setY(Integer.valueOf(partes[8]));
                        manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[12]));
                        dibujos.add(manejadorElementos);
                        break;
                        
                    case "source":
                        Fuente fuente = new Fuente();
                        fuente.setId(Integer.valueOf(partes[1]));
                        fuente.setNombre(nombre);
                        fuente.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        fuente.setConectadoSalida(Boolean.valueOf(partes[3]));
                        fuente.setTipo(Integer.valueOf(partes[4]));
                        fuente.setPotencia(Double.valueOf(partes[5]));
                        fuente.setAnchura(Double.valueOf(partes[6]));
                        fuente.setVelocidad(Double.valueOf(partes[7]));
                        fuente.setLongitudOnda(Integer.valueOf(partes[8]));
                        fuente.setIdFuente(Integer.valueOf(partes[9]));
                        elementos.add(fuente);
                        manejadorElementos = new ElementoGrafico(this, Integer.valueOf(partes[1]), fuente);
                        //manejadorElementos.dibujarComponente();
                        Label dibujo4 = new Label();
                        dibujo4.setGraphic(new ImageView(new Image("images/dibujo_fuente.png")));
                        dibujo4.setText(fuente.getNombre() + "_"+ fuente.getIdFuente());
                        dibujo4.setContentDisplay(ContentDisplay.TOP);
                        manejadorElementos.setDibujo(dibujo4);
                        //manejadorElementos.setX(Integer.valueOf(partes[7]));
                        manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[10]));
                        //manejadorElementos.setY(Integer.valueOf(partes[8]));
                        manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[11]));
                        dibujos.add(manejadorElementos);
                        break;
                        
                    case "power":
                        MedidorPotencia potencia= new MedidorPotencia();
                        potencia.setId(Integer.valueOf(partes[1]));
                        potencia.setNombre(nombre);
                        potencia.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        potencia.setConectadoSalida(Boolean.valueOf(partes[3]));
                        potencia.setIdPotencia(Integer.valueOf(partes[4]));
                        elementos.add(potencia);
                        manejadorElementos = new ElementoGrafico(this, Integer.valueOf(partes[1]), potencia);
                        //manejadorElementos.dibujarComponente();
                        Label dibujo5 = new Label();
                        dibujo5.setGraphic(new ImageView(new Image("images/dibujo_potencia.png")));
                        dibujo5.setText(potencia.getNombre() + "_"+ potencia.getIdPotencia());
                        dibujo5.setContentDisplay(ContentDisplay.TOP);
                        manejadorElementos.setDibujo(dibujo5);
                        //manejadorElementos.setX(Integer.valueOf(partes[7]));
                        manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[5]));
                        //manejadorElementos.setY(Integer.valueOf(partes[8]));
                        manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[6]));
                        dibujos.add(manejadorElementos);
                        break;

                    case "spectrum":
                        MedidorEspectro espectro = new MedidorEspectro();
                        espectro.setId(Integer.valueOf(partes[1]));
                        espectro.setNombre(nombre);
                        espectro.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        espectro.setConectadoSalida(Boolean.valueOf(partes[3]));
                        espectro.setIdEspectro(Integer.valueOf(partes[4]));
                        elementos.add(espectro);
                        manejadorElementos = new ElementoGrafico(this, Integer.valueOf(partes[1]), espectro);
                        //manejadorElementos.dibujarComponente();
                        Label dibujo6 = new Label();
                        dibujo6.setGraphic(new ImageView(new Image("images/dibujo_espectro.png")));
                        dibujo6.setText(espectro.getNombre() + "_"+ espectro.getIdEspectro());
                        dibujo6.setContentDisplay(ContentDisplay.TOP);
                        manejadorElementos.setDibujo(dibujo6);
                        //manejadorElementos.setX(Integer.valueOf(partes[7]));
                        manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[5]));
                        //manejadorElementos.setY(Integer.valueOf(partes[8]));
                        manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[6]));
                        dibujos.add(manejadorElementos);
                        break;
                        
                    default:
                        contadorElemento=Integer.valueOf(partes[1]);
                }
            }
        }
        catch(IOException | NumberFormatException e){
            e.printStackTrace();
        }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
            try{                    
                if( null != fr ){   
                fr.close();     
            }                  
            }catch (Exception e2){ 
            e2.printStackTrace();
            }
        }
    }
}
