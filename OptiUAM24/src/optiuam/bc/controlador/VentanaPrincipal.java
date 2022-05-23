
package optiuam.bc.controlador;

import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Empalme;
import optiuam.bc.modelo.Fibra;
import optiuam.bc.modelo.Fuente;
import optiuam.bc.modelo.MedidorEspectro;
import optiuam.bc.modelo.MedidorPotencia;
import optiuam.bc.modelo.Splitter;

public class VentanaPrincipal implements Initializable {

    static Stage stage;
    static ControladorGeneral controlador= new ControladorGeneral();
    static int idEspectro = 0;
    static int idPotencia = 0;
    
    @FXML
        private ImageView viewFibra,viewFuente, viewConector,viewSplitter,viewEmpalme, viewPotencia,viewEspectro;
    @FXML
        private Button btnFibra, btnFuente, btnConector, btnSplitter, btnEmpalme, btnPotencia, btnEspectro;
    
    private Image fibraI, fuenteI, conectorI, splitterI, empalmeI, potenciaI, espectroI, fondo;
    
    @FXML
    public Pane Pane1;
    
    @FXML
    public ScrollPane scroll;
    
    @FXML
    static public TitledPane componentMenu;
    
    @FXML
    MenuItem menuItemNew, menuItemSave, menuItemOpen;
    
    @FXML
    Menu menuHelp, menuAbout;
    
    static Line linea;
    
    public static Line getLinea() {
        return linea;
    }

    public static ControladorGeneral getControlador() {
        return controlador;
    }

    public static void setLinea(Line linea) {
        VentanaPrincipal.linea = linea;
    }

    @FXML
    private void abrirVentanaFibra(ActionEvent event) throws IOException{
        Stage s = new Stage(StageStyle.UTILITY);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaFibra.fxml"));
        Parent root = loader.load();
        //Se crea una instancia del controlador de fibra.
        VentanaFibraController fibraController= (VentanaFibraController) loader.getController();
        fibraController.init(controlador,VentanaPrincipal.stage,Pane1,this.scroll);
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        s.getIcons().add(ico);
        s.setTitle("OptiUAM BC New Fiber");
        s.initModality(Modality.APPLICATION_MODAL);
        s.setScene(scene);
        s.setResizable(false);
        s.showAndWait();
        //System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    private void abrirVentanaFuente(ActionEvent event) throws IOException{
        Stage s = new Stage(StageStyle.UTILITY);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaFuente.fxml"));
        Parent root =loader.load();
        VentanaFuenteController fuenteControl=loader.getController();
        fuenteControl.init(controlador,VentanaPrincipal.stage,Pane1,this.scroll);
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        s.getIcons().add(ico);
        s.setTitle("OptiUAM BC New Source");
        s.initModality(Modality.APPLICATION_MODAL);
        s.setScene(scene);
        s.setResizable(false);
        s.showAndWait();
        //System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).toString());
        }
    }
    
    @FXML
    private void abrirVentanaSplitter(ActionEvent event) throws IOException{
        Stage s = new Stage(StageStyle.UTILITY);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaSplitter.fxml"));
        Parent root =loader.load();
        VentanaSplitterController splitterControl=loader.getController();
        splitterControl.init(controlador,VentanaPrincipal.stage,Pane1,this.scroll);
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        s.getIcons().add(ico);
        s.setTitle("OptiUAM BC New Splitter");
        s.initModality(Modality.APPLICATION_MODAL);
        s.setScene(scene);
        s.setResizable(false);
        s.showAndWait();
        //System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    private void abrirVentanaConector(ActionEvent event) throws IOException{
        Stage s = new Stage(StageStyle.UTILITY);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaConector.fxml"));
        Parent root =loader.load();
        VentanaConectorController conectorControl=loader.getController();
        conectorControl.init(controlador,VentanaPrincipal.stage,Pane1,this.scroll);
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        s.getIcons().add(ico);
        s.setTitle("OptiUAM BC New Connector");
        s.initModality(Modality.APPLICATION_MODAL);
        s.setScene(scene);
        s.setResizable(false);
        s.show();
        //System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    private void abrirVentanaEmpalme(ActionEvent event) throws IOException{
        Stage s = new Stage(StageStyle.UTILITY);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaEmpalme.fxml"));
        Parent root =loader.load();
        VentanaEmpalmeController empalmeControl=loader.getController();
        empalmeControl.init(controlador,VentanaPrincipal.stage,Pane1,this.scroll);
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        s.getIcons().add(ico);
        s.setTitle("OptiUAM BC New Splice");
        s.initModality(Modality.APPLICATION_MODAL);
        s.setScene(scene);
        s.setResizable(false);
        s.showAndWait();
        //System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    public void crearPotencia(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\nPower Meter created!");
        alert.showAndWait();
        
        MedidorPotencia potencia = new MedidorPotencia();
        potencia.setNombre("power"); //potencia
        potencia.setConectadoEntrada(false);
        potencia.setConectadoSalida(false);
        potencia.setIdPotencia(idPotencia);
        idPotencia++;
        potencia.setId(controlador.getContadorElemento());
        controlador.getElementos().add(potencia);
        
        Label dibujo= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_potencia.png")));
        dibujo.setText("power" + "_"+ potencia.getIdPotencia());
        dibujo.setContentDisplay(ContentDisplay.TOP);

        ElementoGrafico elem = new ElementoGrafico();
        elem.setComponente(potencia);
        elem.setDibujo(dibujo);
        elem.setId(controlador.getContadorElemento());
        controlador.getDibujos().add(elem);
        Pane1.getChildren().add(dibujo);
        
           eventos(elem);
            
            dibujo.setOnMouseClicked((MouseEvent event1) -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                try {
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaPotencia.fxml"));
                    Parent root= loader.load();
                    VentanaPotenciaController potControl= loader.getController();
                    potControl.init(controlador, stage, Pane1, scroll);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage s = new Stage(StageStyle.UTILITY);
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC "+elem.getDibujo().getText().toUpperCase());
                    s.setScene(scene);
                    s.showAndWait();
                    s.setResizable(false);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event1.getButton() == MouseButton.SECONDARY) {
                mostrarMenuChiquito(elem);
            }
        });
            controlador.setContadorElemento(controlador.getContadorElemento()+1);
        //System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    public void crearEspectro(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\nSpectrum Meter created!");
        alert.showAndWait();
        
        MedidorEspectro espectro = new MedidorEspectro();
        espectro.setNombre("spectrum"); //espectro
        espectro.setConectadoEntrada(false);
        espectro.setConectadoSalida(false);
        espectro.setIdEspectro(idEspectro);
        idEspectro++;
        espectro.setId(controlador.getContadorElemento());
        controlador.getElementos().add(espectro);
        
        Label dibujo= new Label();
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_espectro.png")));
        dibujo.setText("spectrum" + "_"+ espectro.getIdEspectro());
        dibujo.setContentDisplay(ContentDisplay.TOP);

        ElementoGrafico elem = new ElementoGrafico();
        elem.setComponente(espectro);
        elem.setDibujo(dibujo);
        elem.setId(controlador.getContadorElemento());
        controlador.getDibujos().add(elem);
        Pane1.getChildren().add(dibujo);
        
            eventos(elem);
            
            elem.getDibujo().setOnMouseClicked((MouseEvent event1) -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaEspectro.fxml"));
                    Parent root= loader.load();
                    VentanaEspectroController espcControl= loader.getController();
                    espcControl.init(controlador, stage, Pane1, scroll);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage s = new Stage(StageStyle.UTILITY);
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC "+elem.getDibujo().getText().toUpperCase());
                    s.setScene(scene);
                    s.showAndWait();
                    s.setResizable(false);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event1.getButton() == MouseButton.SECONDARY) {
                mostrarMenuChiquito(elem);
            }
        });
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        //System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }
    }
    
    @FXML
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    public void mostrarMenuChiquito(ElementoGrafico dibujo){
        // create a menu
        ContextMenu contextMenu = new ContextMenu();

        // create menuitems
        MenuItem menuItem3 = new MenuItem("-Delete");

        menuItem3.setOnAction(e ->{
            if(dibujo.getComponente().isConectadoSalida()==true){
                for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento).getDibujo().getText())){
                    Componente aux= controlador.getElementos().get(elemento);
                    System.out.println();
                    //controlador.getDibujos().remove(dibujo);
                    //controlador.getElementos().remove(aux); 
                    aux.setConectadoEntrada(false);
                    aux.setElementoConectadoEntrada("");
                   
                    dibujo.getComponente().getLinea().setVisible(false);
                }
            }   
            }
            if(dibujo.getComponente().isConectadoEntrada()==true){
                for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(elemento).getDibujo().getText())){
                    Componente aux= controlador.getElementos().get(elemento);
                    //controlador.getDibujos().remove(dibujo);
                    //controlador.getElementos().remove(aux); 
                    aux.setConectadoSalida(false);
                    aux.setElementoConectadoSalida("");
                     aux.getLinea().setVisible(false);
                }
            }
            }
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    if(dibujo.getDibujo().getText().contains("power")){ //potencia
                        MedidorPotencia aux= (MedidorPotencia)controlador.getElementos().get(elemento);
                        controlador.getDibujos().remove(dibujo);
                        controlador.getElementos().remove(aux); 
                    }
                    else{
                        MedidorEspectro aux= (MedidorEspectro)controlador.getElementos().get(elemento);
                        controlador.getDibujos().remove(dibujo);
                        controlador.getElementos().remove(aux); 
                    }
                    
                }
                
            }    
            dibujo.getDibujo().setVisible(false);
        });
        MenuItem menuItem4 = new MenuItem("-Properties");
        menuItem4.setOnAction(e ->{
            //Tooltip tt= new Tooltip();
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    
                    if(dibujo.getDibujo().getText().contains("power")){ //potencia
                        MedidorPotencia aux= (MedidorPotencia)controlador.getElementos().get(elemento);
                        String name = "Name: "+aux.getNombre();
                        String id = "Id = "+aux.getIdPotencia();
                        String conE = "Input: "+aux.getElementoConectadoEntrada();
                        String conS = "Output :"+aux.getElementoConectadoSalida();
                        //tt.setText(name+"\n"+id+"\n"+conE+"\n"+conS);
                        System.out.println(name+"\n"+id+"\n"+conE+"\n"+conS);
                    }
                    else{
                        MedidorEspectro aux= (MedidorEspectro)controlador.getElementos().get(elemento);
                        String name = "Name: "+aux.getNombre();
                        String id = "Id= "+aux.getIdEspectro();
                        String conE = "Input: "+aux.getElementoConectadoEntrada();
                        String conS = "Output: "+aux.getElementoConectadoSalida();
                        //tt.setText(name+"\n"+id+"\n"+conE+"\n"+conS);
                        System.out.println(name+"\n"+id+"\n"+conE+"\n"+conS);
                    }
                //dibujo.getDibujo().setTooltip(tt);
                }
            }
                
        });

        // add menu items to menu
        
        contextMenu.getItems().add(menuItem3);
        contextMenu.getItems().add(menuItem4);
        dibujo.getDibujo().setContextMenu(contextMenu);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //componentes=FXCollections.observableArrayList();
        fibraI=new Image("images/ico_fibra.png"); 
        fuenteI=new Image("images/ico_fuente.png"); 
        conectorI=new Image("images/ico_conector.png"); 
        potenciaI=new Image("images/ico_potencia.png"); 
        espectroI=new Image("images/ico_espectro.png"); 
        empalmeI=new Image("images/ico_empalme.png"); 
        splitterI=new Image("images/ico_splitter.png"); 
        //fondo= new Image("/images/fondo.png"); 
        
        viewFibra.setImage(fibraI);
        viewFuente.setImage(fuenteI);
        viewConector.setImage(conectorI);
        viewPotencia.setImage(potenciaI);
        viewEspectro.setImage(espectroI);
        viewEmpalme.setImage(empalmeI);
        viewSplitter.setImage(splitterI);
        
    }    

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        VentanaPrincipal.stage = stage;
    }

    public Pane getPane1() {
        return Pane1;
    }
    
    public void setControlador(ControladorGeneral controlador) {
        this.controlador = controlador;

    }
    
    @FXML
    private void menuAboutAction(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("VentanaAcercaDe.fxml"));
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            Stage st = new Stage(StageStyle.UTILITY);
            st.getIcons().add(ico);
            st.setTitle("OptiUAM BC About");
            st.setScene(scene);
            st.showAndWait();
            st.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void menuHelpAction(){
        try {
            File objetofile = new File ("ayuda.pdf");
            Desktop.getDesktop().open(objetofile);
        }catch (IOException ex) {
               System.out.println(ex);
        }
    }
    
    @FXML
    private void menuItemNewAction() {                                           
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelar = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Do you want to create a new job?",
                aceptar,
                cancelar);

        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.orElse(cancelar) == aceptar) {
            controlador.nuevoTrabajo();
        }
        else{}
    }    
    
    @FXML
    private void menuItemSaveAction(ActionEvent event) {                                             
        JFileChooser manejador = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("opt Files (*.opt)", "*.opt");
        manejador.setDialogTitle("Save");
        manejador.setBackground(Color.CYAN);
        manejador.setFileFilter(filtro);
        manejador.showSaveDialog(null);
        String ruta_archivo="";
        try {
        ruta_archivo = manejador.getSelectedFile().getPath();
        //System.out.println(ruta_archivo);
        controlador.guardarTrabajo(ruta_archivo);
        } catch (Exception e) {
            //no se hace nada ya que esta excepcion se activa cuando se da click 
            //en cancelar o se cierra la ventana para cargar/guardad trabajo
        }
        /*Mi forma xd se ve mas bonito jsjsjs
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("opt Files", "*.opt"));
        fileChooser.showSaveDialog(null);
        try {
        ruta_archivo = fileChooser...->ya no aplica.getSelectedFile().getPath();
        //System.out.println(ruta_archivo);
        controlador.guardarTrabajo(ruta_archivo);
        } catch (Exception e) {
            //no se hace nada ya que esta excepcion se activa cuando se da click 
            //en cancelar o se cierra la ventana para cargar/guardad trabajo
        }
        */
    } 
    
    public void abrirTrabajo(String ruta) throws InstantiationException, IllegalAccessException{
        
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
             // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File (ruta);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);
            
            ControladorGeneral con = new ControladorGeneral();
                        
           Node node= Pane1.getChildren().get(0);
           Pane1.getChildren().clear();
           Pane1.getChildren().add(node);
           controlador.getElementos().clear();
           controlador.getDibujos().clear();
           // Lectura del fichero
            String linea="";
            
            while((linea=br.readLine())!=null){
                String [] partes = linea.split(",");
                String nombre = partes[0];
                switch (nombre) {
                    case "connector":
                        Conector conector = new Conector();
                        conector.setId(Integer.valueOf(partes[1]));
                        conector.setNombre(nombre);
                        conector.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        conector.setElementoConectadoEntrada(partes[3]);
                        conector.setConectadoSalida(Boolean.valueOf(partes[4]));
                        conector.setElementoConectadoSalida(partes[5]);
                        conector.setLongitudOnda(Integer.valueOf(partes[6]));
                        conector.setModo(Integer.valueOf(partes[7]));
                        conector.setPerdidaInsercion(Double.valueOf(partes[8]));
                        conector.setIdConector(Integer.valueOf(partes[9]));
                        System.out.println(conector.getIdConector());
                        con.getElementos().add(conector);
                        
                        Label dibujo = new Label();
                        dibujo.setGraphic(new ImageView(new Image("images/dibujo_conectorR.png")));
                        dibujo.setText(conector.getNombre() + "_"+ conector.getIdConector());
                        dibujo.setLayoutX(Double.parseDouble(partes[10]));
                        dibujo.setLayoutY(Double.parseDouble(partes[11]));
                        dibujo.setContentDisplay(ContentDisplay.TOP);
                        
                        ElementoGrafico elem = new ElementoGrafico();
                        elem.setComponente(conector);
                        elem.setDibujo(dibujo);
                        elem.setId(Integer.valueOf(partes[1]));
                        VentanaConectorController aux= new VentanaConectorController();
                        aux.init(con, stage, Pane1, scroll);
                        con.getDibujos().add(elem);
                        dibujo.setVisible(true);
                        
                        Pane1.getChildren().add(dibujo);
                        aux.eventos(elem);
                        break;
                        
                    case "splice":
                        Empalme empalme = new Empalme();
                        empalme.setId(Integer.valueOf(partes[1]));
                        empalme.setNombre(nombre);
                        empalme.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        empalme.setElementoConectadoEntrada(partes[3]);
                        empalme.setConectadoSalida(Boolean.valueOf(partes[4]));
                        empalme.setElementoConectadoSalida(partes[5]);
                        empalme.setTipo(Integer.valueOf(partes[6]));
                        empalme.setPerdidaInsercion(Double.valueOf(partes[7]));
                        empalme.setLongitudOnda(Integer.valueOf(partes[8]));
                        empalme.setIdEmpalme(Integer.valueOf(partes[9]));
                        System.out.println(empalme.getIdEmpalme());
                        con.getElementos().add(empalme);
                        
                        Label dibujo1 = new Label();
                        dibujo1.setGraphic(new ImageView(new Image("images/dibujo_empalme.png")));
                        dibujo1.setText(empalme.getNombre() + "_"+ empalme.getIdEmpalme());
                        dibujo1.setLayoutX(Double.parseDouble(partes[10]));
                        dibujo1.setLayoutY(Double.parseDouble(partes[11]));
                        dibujo1.setContentDisplay(ContentDisplay.TOP);
                        
                        ElementoGrafico elem1 = new ElementoGrafico();
                        elem1.setComponente(empalme);
                        elem1.setDibujo(dibujo1);
                        elem1.setId(Integer.valueOf(partes[1]));
                        VentanaEmpalmeController aux1= new VentanaEmpalmeController();
                        aux1.init(con, stage, Pane1, scroll);
                        con.getDibujos().add(elem1);
                        dibujo1.setVisible(true);
                        
                        Pane1.getChildren().add(dibujo1);
                        aux1.eventos(elem1);
                        break;
                        
                    case "fiber":
                        Fibra fibra = new Fibra();
                        fibra.setId(Integer.valueOf(partes[1]));
                        fibra.setNombre(nombre);
                        fibra.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        fibra.setElementoConectadoEntrada(partes[3]);
                        fibra.setConectadoSalida(Boolean.valueOf(partes[4]));
                        fibra.setElementoConectadoSalida(partes[5]);
                        fibra.setLongitudOnda(Integer.valueOf(partes[6]));
                        fibra.setModo(Integer.valueOf(partes[7]));
                        fibra.setTipo(Integer.valueOf(partes[8]));
                        fibra.setLongitud_km(Double.valueOf(partes[9]));
                        fibra.setDispersion(Double.valueOf(partes[10]));
                        fibra.setAtenuacion(Double.valueOf(partes[11]));
                        fibra.setIdFibra(Integer.valueOf(partes[12]));
                        System.out.println(fibra.getIdFibra());
                        con.getElementos().add(fibra);
                        
                        Label dibujo2 = new Label();
                        dibujo2.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
                        dibujo2.setText(fibra.getNombre() + "_"+ fibra.getIdFibra());
                        dibujo2.setLayoutX(Double.parseDouble(partes[13]));
                        dibujo2.setLayoutY(Double.parseDouble(partes[14]));
                        dibujo2.setContentDisplay(ContentDisplay.TOP);
                        
                        ElementoGrafico elem2 = new ElementoGrafico();
                        elem2.setComponente(fibra);
                        elem2.setDibujo(dibujo2);
                        elem2.setId(Integer.valueOf(partes[1]));
                        VentanaFibraController aux2= new VentanaFibraController();
                        aux2.init(con, stage, Pane1, scroll);
                        con.getDibujos().add(elem2);
                        dibujo2.setVisible(true);
                        
                        Pane1.getChildren().add(dibujo2);
                        aux2.eventos(elem2);
                        break;
                        
                    case "splitter":
                        Splitter splitter = new Splitter();
                        splitter.setId(Integer.valueOf(partes[1]));
                        splitter.setNombre(nombre);
                        splitter.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        splitter.setElementoConectadoEntrada(partes[3]);
                        splitter.setConectadoSalida(Boolean.valueOf(partes[4]));
                        splitter.setElementoConectadoSalida(partes[5]);
                        splitter.setSalidas(Integer.valueOf(partes[6]));
                        splitter.setPerdidaInsercion(Double.valueOf(partes[7]));
                        splitter.setLongitudOnda(Integer.valueOf(partes[8]));
                        splitter.setIdS(Integer.valueOf(partes[9]));
                        System.out.println(splitter.getIdS());
                        con.getElementos().add(splitter);
                        
                        Label dibujo3 = new Label();
                        
                        switch (splitter.getSalidas()) {
                            case 2:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter2.png")));
                                break;
                            case 4:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter4.png")));
                                break;
                            case 8:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter8.png")));
                                break;
                            case 16:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter16.png")));
                                break;
                            case 32:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter32.png")));
                                break;
                            case 64:
                                dibujo3.setGraphic(new ImageView(new Image("images/dibujo_splitter64.png")));
                                break;
                            default:
                                break;
                        }
                        
                        dibujo3.setText(splitter.getNombre() + "_"+ splitter.getIdS());
                        dibujo3.setLayoutX(Double.parseDouble(partes[10]));
                        dibujo3.setLayoutY(Double.parseDouble(partes[11]));
                        dibujo3.setContentDisplay(ContentDisplay.TOP);
                        
                        String [] conexiones = linea.split(",");
                        for(int i=0;i<splitter.getSalidas()+1; i++){
                            if(conexiones[i].compareTo(" ")==0)
                                splitter.cargarConexion(i,"");
                            else    
                                splitter.cargarConexion(i,conexiones[i]);
                        }
                        ElementoGrafico elem3 = new ElementoGrafico();
                        elem3.setComponente(splitter);
                        elem3.setDibujo(dibujo3);
                        elem3.setId(Integer.valueOf(partes[1]));
                        VentanaSplitterController aux3= new VentanaSplitterController();
                        aux3.init(con, stage, Pane1, scroll);
                        
                        con.getDibujos().add(elem3);
                        dibujo3.setVisible(true);
                        
                        Pane1.getChildren().add(dibujo3);
                        aux3.eventos(elem3);
                        break;
                        
                    case "source":
                        Fuente fuente = new Fuente();
                        fuente.setId(Integer.valueOf(partes[1]));
                        fuente.setNombre(nombre);
                        fuente.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        fuente.setElementoConectadoEntrada(partes[3]);
                        fuente.setConectadoSalida(Boolean.valueOf(partes[4]));
                        fuente.setElementoConectadoSalida(partes[5]);
                        fuente.setTipo(Integer.parseInt(partes[6]));
                        fuente.setPotencia(Double.parseDouble(partes[7]));
                        fuente.setAnchura(Double.parseDouble(partes[8]));
                        fuente.setVelocidad(Double.parseDouble(partes[9]));
                        fuente.setLongitudOnda(Integer.parseInt(partes[10]));
                        fuente.setPulso(Float.parseFloat(partes[11]), Float.parseFloat(partes[12]), Float.parseFloat(partes[13]), Float.parseFloat(partes[14]), Float.parseFloat(partes[15]));
                        fuente.setIdFuente(Integer.parseInt(partes[16]));
                        con.getElementos().add(fuente);
                        
                        Label dibujo4 = new Label();
                        dibujo4.setGraphic(new ImageView(new Image("images/dibujo_fuente.png")));
                        dibujo4.setText(fuente.getNombre() + "_"+ fuente.getIdFuente());
                        dibujo4.setLayoutX(Double.parseDouble(partes[17]));
                        dibujo4.setLayoutY(Double.parseDouble(partes[18]));
                        dibujo4.setContentDisplay(ContentDisplay.TOP);

                        ElementoGrafico elem4 = new ElementoGrafico();
                        elem4.setComponente(fuente);
                        elem4.setDibujo(dibujo4);
                        elem4.setId(Integer.valueOf(partes[1]));
                        VentanaFuenteController aux4= new VentanaFuenteController();
                        aux4.init(con, stage, Pane1, scroll);

                        con.getDibujos().add(elem4);
                        dibujo4.setVisible(true);
                        Pane1.getChildren().add(dibujo4);
                        aux4.eventos(elem4);
                        break;
                        
                    case "power":
                        MedidorPotencia potencia= new MedidorPotencia();
                        potencia.setId(Integer.valueOf(partes[1]));
                        potencia.setNombre(nombre);
                        potencia.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        potencia.setElementoConectadoEntrada(partes[3]);
                        potencia.setConectadoSalida(Boolean.valueOf(partes[4]));
                        potencia.setElementoConectadoSalida(partes[5]);
                        potencia.setIdPotencia(Integer.valueOf(partes[6]));
                        System.out.println(potencia.getIdPotencia());
                        con.getElementos().add(potencia);
                        
                        Label dibujo5 = new Label();
                        dibujo5.setGraphic(new ImageView(new Image("images/dibujo_potencia.png")));
                        dibujo5.setText(potencia.getNombre() + "_"+ potencia.getIdPotencia());
                        dibujo5.setLayoutX(Double.parseDouble(partes[7]));
                        dibujo5.setLayoutY(Double.parseDouble(partes[8]));
                        dibujo5.setContentDisplay(ContentDisplay.TOP);
                        
                        ElementoGrafico elem5 = new ElementoGrafico();
                        elem5.setComponente(potencia);
                        elem5.setDibujo(dibujo5);
                        elem5.setId(Integer.valueOf(partes[1]));
                        VentanaPotenciaController aux5= new VentanaPotenciaController();
                        aux5.init(con, stage, Pane1, scroll);
                        con.getDibujos().add(elem5);
                        dibujo5.setVisible(true);
                        
                        Pane1.getChildren().add(dibujo5);
                        eventosPotencia(dibujo5, elem5);
                        break;    
                      
                    case "spectrum":
                        MedidorEspectro espectro = new MedidorEspectro();
                        espectro.setId(Integer.valueOf(partes[1]));
                        espectro.setNombre(nombre);
                        espectro.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        espectro.setElementoConectadoEntrada(partes[3]);
                        espectro.setConectadoSalida(Boolean.valueOf(partes[4]));
                        espectro.setElementoConectadoSalida(partes[5]);
                        espectro.setIdEspectro(Integer.valueOf(partes[6]));
                        System.out.println(espectro.getIdEspectro());
                        con.getElementos().add(espectro);
                        
                        Label dibujo6 = new Label();
                        dibujo6.setGraphic(new ImageView(new Image("images/dibujo_espectro.png")));
                        dibujo6.setText(espectro.getNombre() + "_"+ espectro.getIdEspectro());
                        dibujo6.setLayoutX(Double.parseDouble(partes[7]));
                        dibujo6.setLayoutY(Double.parseDouble(partes[8]));
                        dibujo6.setContentDisplay(ContentDisplay.TOP);
                        
                        ElementoGrafico elem6 = new ElementoGrafico();
                        elem6.setComponente(espectro);
                        elem6.setDibujo(dibujo6);
                        elem6.setId(Integer.valueOf(partes[1]));
                        VentanaEspectroController aux6= new VentanaEspectroController();
                        aux6.init(con, stage, Pane1, scroll);
                        con.getDibujos().add(elem6);
                        dibujo6.setVisible(true);
                        
                        Pane1.getChildren().add(dibujo6);
                        eventosEspectro(dibujo6, elem6);
                        break;
                        
                    default:
                        con.setContadorElemento(Integer.valueOf(partes[0]));
                }
            }
            controlador=con;
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
    
    @FXML
    private void menuItemOpenAction(ActionEvent event) {                                            
        JFileChooser manejador = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("opt files", "opt");
        manejador.setFileFilter(filtro);
        manejador.showOpenDialog(null);
        String ruta_archivo="";
        try {
            ruta_archivo = manejador.getSelectedFile().getPath();
            System.out.println(ruta_archivo);
            abrirTrabajo(ruta_archivo);
        } catch (Exception e) {
            //no se hace nada ya que esta excepcion se activa cuando se da click 
            //en cancelar o se cierra la ventana para cargar/guardad trabajo
        }
    }   
    
    public void eventos(ElementoGrafico elem) {
        elem.getDibujo().setOnMouseDragged((MouseEvent event) -> {
                if(event.getButton()==MouseButton.PRIMARY){
                    double newX=event.getSceneX();
                    double newY=event.getSceneY();
                    int karen=0;
                    for(int a=0; a<Pane1.getChildren().size();a++){
                        if(Pane1.getChildren().get(a).toString().contains(elem.getDibujo().getText())){
                            karen=a;
                            break;
                        }
                    }
                    if( outSideParentBoundsX(elem.getDibujo().getLayoutBounds(), newX, newY) ) {    //return; 
                    }else{
                        elem.getDibujo().setLayoutX(Pane1.getChildren().get(karen).getLayoutX()+event.getX()+1);
                    }
                    /*
                    if(elem.getDibujo().getLayoutX()>=0.0){
                        elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                        elem.getDibujo().setLayoutX((scroll.getHvalue()*200)+event.getSceneX()-20);
                    }else{
                        elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                        elem.getDibujo().setLayoutX(0.0);
                    }
                    if(elem.getDibujo().getLayoutY()>=0.0){
                        elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                        elem.getDibujo().setLayoutY((scroll.getVvalue()*200)+event.getSceneY()-170);
                    }else{
                        elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                        elem.getDibujo().setLayoutY(0);
                    }
                    */
                    if(outSideParentBoundsY(elem.getDibujo().getLayoutBounds(), newX, newY) ) {    //return; 
                    }else{
                    elem.getDibujo().setLayoutY(Pane1.getChildren().get(karen).getLayoutY()+event.getY()+1);}
                    if(elem.getComponente().isConectadoSalida()==true){
                        borrarLinea(elem.getComponente().getLinea());
                        dibujarLinea(elem);
                    }
                    if(elem.getComponente().isConectadoEntrada()){
                        ElementoGrafico aux;
                        for(int it=0; it<controlador.getDibujos().size();it++){
                            if(elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                                aux=controlador.getDibujos().get(it);
                                borrarLinea(aux.getComponente().getLinea());
                            }
                        }
                        dibujarLineaAtras(elem);
                    }
                }
        });
            elem.getDibujo().setOnMouseEntered((MouseEvent event) -> {
                elem.getDibujo().setStyle("-fx-border-color: darkblue;");
                elem.getDibujo().setCursor(Cursor.OPEN_HAND);
        });
            elem.getDibujo().setOnMouseExited((MouseEvent event) -> {
                elem.getDibujo().setStyle("");
        });
    }
    
    public void dibujarLinea(ElementoGrafico elemG) {
        Line line= new Line();   
        line.setStartX(elemG.getDibujo().getLayoutX()+elemG.getDibujo().getWidth());
        line.setStartY(elemG.getDibujo().getLayoutY()+7);
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(javafx.scene.paint.Color.BLACK);
        line.setEndX(aux.getDibujo().getLayoutX());
        line.setEndY(aux.getDibujo().getLayoutY());
        setLinea(line);
        //System.out.println("Se dibujo una linea");
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        elemG.getComponente().setLinea(line);
              
    }
    
    public void borrarLinea(Line linea){
        linea.setVisible(false);
    }
    
    public void dibujarLineaAtras(ElementoGrafico elem) {
        Line line= new Line();   
        ElementoGrafico aux= new ElementoGrafico();
        
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(javafx.scene.paint.Color.BLACK);
        line.setStartX(aux.getDibujo().getLayoutX()+aux.getDibujo().getWidth());
        line.setStartY(aux.getDibujo().getLayoutY()+10);
        line.setEndX(elem.getDibujo().getLayoutX());
        line.setEndY(elem.getDibujo().getLayoutY()+7);
        setLinea(line);
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        aux.getComponente().setLinea(line);
            
    }
    
    public boolean outSideParentBoundsX( Bounds childBounds, double newX, double newY) {

        Bounds parentBounds = Pane1.getLayoutBounds();

        //check if too left
        if( parentBounds.getMaxX() <= (newX + childBounds.getMaxX()) ) {
            return true ;
        }

        //check if too right
        if( parentBounds.getMinX() >= (newX + childBounds.getMinX()) ) {
            return true ;
        }
        /*
        //check if too down
        if( parentBounds.getMaxY() <= (newY + childBounds.getMaxY()) ) {
            return true ;
        }

        //check if too up
        if( parentBounds.getMinY()+170 >= (newY + childBounds.getMinY()) ) {
            return true ;
        }
        */
        return false;

        /* Alternative implementation 
        Point2D topLeft = new Point2D(newX + childBounds.getMinX(), newY + childBounds.getMinY());
        Point2D topRight = new Point2D(newX + childBounds.getMaxX(), newY + childBounds.getMinY());
        Point2D bottomLeft = new Point2D(newX + childBounds.getMinX(), newY + childBounds.getMaxY());
        Point2D bottomRight = new Point2D(newX + childBounds.getMaxX(), newY + childBounds.getMaxY());
        Bounds newBounds = BoundsUtils.createBoundingBox(topLeft, topRight, bottomLeft, bottomRight);

        return ! parentBounds.contains(newBounds);
         */
    }
    
    public boolean outSideParentBoundsY( Bounds childBounds, double newX, double newY) {

        Bounds parentBounds = Pane1.getLayoutBounds();
        /*
        //check if too left
        if( parentBounds.getMaxX() <= (newX + childBounds.getMaxX()) ) {
            return true ;
        }

        //check if too right
        if( parentBounds.getMinX() >= (newX + childBounds.getMinX()) ) {
            return true ;
        }
        */
        //check if too down
        if( parentBounds.getMaxY() <= (newY + childBounds.getMaxY()) ) {
            return true ;
        }

        //check if too up
        if( parentBounds.getMinY()+179 >= (newY + childBounds.getMinY()) ) {
            return true ;
        }

        return false;

        /* Alternative implementation 
        Point2D topLeft = new Point2D(newX + childBounds.getMinX(), newY + childBounds.getMinY());
        Point2D topRight = new Point2D(newX + childBounds.getMaxX(), newY + childBounds.getMinY());
        Point2D bottomLeft = new Point2D(newX + childBounds.getMinX(), newY + childBounds.getMaxY());
        Point2D bottomRight = new Point2D(newX + childBounds.getMaxX(), newY + childBounds.getMaxY());
        Bounds newBounds = BoundsUtils.createBoundingBox(topLeft, topRight, bottomLeft, bottomRight);

        return ! parentBounds.contains(newBounds);
         */
    }
    
    public void eventosPotencia(Label dibujo, ElementoGrafico elem){
        eventos(elem);
        dibujo.setOnMouseClicked((MouseEvent event1) -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                try {
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("VentanaPotencia.fxml"));
                    Parent root= loader.load();
                    VentanaPotenciaController potControl= loader.getController();
                    potControl.init(controlador, stage, Pane1, scroll);
                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    Stage s = new Stage(StageStyle.UTILITY);
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC "+elem.getDibujo().getText().toUpperCase());
                    s.setScene(scene);
                    s.showAndWait();
                    s.setResizable(false);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (event1.getButton() == MouseButton.SECONDARY) {
                mostrarMenuChiquito(elem);
            }
        });
    }
    
    public void eventosEspectro(Label dibujo, ElementoGrafico elem){
        eventos(elem);
            
        elem.getDibujo().setOnMouseClicked((MouseEvent event1) -> {
        if (event1.getButton() == MouseButton.PRIMARY) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaEspectro.fxml"));
                Parent root= loader.load();
                VentanaEspectroController espcControl= loader.getController();
                espcControl.init(controlador, stage, Pane1, scroll);
                Scene scene = new Scene(root);
                Image ico = new Image("images/acercaDe.png");
                Stage s = new Stage(StageStyle.UTILITY);
                s.getIcons().add(ico);
                s.setTitle("OptiUAM BC "+elem.getDibujo().getText().toUpperCase());
                s.setScene(scene);
                s.showAndWait();
                s.setResizable(false);
            } catch (IOException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event1.getButton() == MouseButton.SECONDARY) {
            mostrarMenuChiquito(elem);
        }
        });
    }

}
