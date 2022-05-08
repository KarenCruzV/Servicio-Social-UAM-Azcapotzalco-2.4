
package optiuam.bc.controlador;

import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ObservableList<Componente> componentes;
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

    public static void setLinea(Line linea) {
        VentanaPrincipal.linea = linea;
    }

    @FXML
    private void abrirVentanaFibra(ActionEvent event) throws IOException{
        Fibra fibra= new Fibra();
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
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
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
    private void crearPotencia(ActionEvent event){
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
                    potControl.init(elem,controlador);
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
    private void crearEspectro(ActionEvent event) {
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
                    Parent root = FXMLLoader.load(getClass().getResource("VentanaEspectro.fxml"));
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
        MenuItem menuItem2 = new MenuItem("-Rotate");
        MenuItem menuItem3 = new MenuItem("-Delete");

        menuItem2.setOnAction(e ->{
            System.out.println("Girar");
            if(dibujo.getDibujo().getText().contains("potencia")){
                System.out.println("Girar potencia");
            }
            else{
                System.out.println("Girar espectro");
            }
        });

        menuItem3.setOnAction(e ->{
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
        contextMenu.getItems().add(menuItem2);
        contextMenu.getItems().add(menuItem3);
        contextMenu.getItems().add(menuItem4);
        dibujo.getDibujo().setContextMenu(contextMenu);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        componentes=FXCollections.observableArrayList();
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
        VentanaPrincipal.controlador = controlador;

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
            
            //se vacian los arrays de controlador.elementos
            controlador.elementos = new LinkedList<>();
            controlador.dibujos = new LinkedList<>();
            
            //se limpia el panel de trabajo
            menuItemNewAction();
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
                        controlador.elementos.add(conector);
                        controlador.manejadorElementos = new ElementoGrafico(controlador, Integer.valueOf(partes[1]), conector);
                        //controlador.manejadorElementos.dibujarComponente();
                        Label dibujo = new Label();
                        dibujo.setGraphic(new ImageView(new Image("images/dibujo_conectorR.png")));
                        dibujo.setText(conector.getNombre() + "_"+ conector.getIdConector());
                        dibujo.setContentDisplay(ContentDisplay.TOP);
                        controlador.manejadorElementos.setDibujo(dibujo);
                        //controlador.manejadorElementos.setX(Integer.valueOf(partes[7]));
                        controlador.manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[8]));
                        //controlador.manejadorElementos.setY(Integer.valueOf(partes[8]));
                        controlador.manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[9]));
                        controlador.dibujos.add(controlador.manejadorElementos);
                        //INTENTO DE QUE APAREZCA EN LA VENTANA PRINCIPAL :V
                        ElementoGrafico elem = new ElementoGrafico();
                        elem.setComponente(conector);
                        elem.setDibujo(dibujo);
                        elem.setId(VentanaPrincipal.controlador.getContadorElemento());
                        controlador.getDibujos().add(elem);
                        dibujo.setVisible(true);
                        Pane1.getChildren().add(dibujo);
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
                        controlador.elementos.add(empalme);
                        controlador.manejadorElementos = new ElementoGrafico(controlador, Integer.valueOf(partes[1]), empalme);
                        //controlador.manejadorElementos.dibujarComponente();
                        Label dibujo1 = new Label();
                        dibujo1.setGraphic(new ImageView(new Image("images/dibujo_empalme.png")));
                        dibujo1.setText(empalme.getNombre() + "_"+ empalme.getIdEmpalme());
                        dibujo1.setContentDisplay(ContentDisplay.TOP);
                        controlador.manejadorElementos.setDibujo(dibujo1);
                        //controlador.manejadorElementos.setX(Integer.valueOf(partes[7]));
                        controlador.manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[8]));
                        //controlador.manejadorElementos.setY(Integer.valueOf(partes[8]));
                        controlador.manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[9]));
                        controlador.dibujos.add(controlador.manejadorElementos);
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
                        controlador.manejadorElementos = new ElementoGrafico(controlador, Integer.valueOf(partes[1]), splitter);
                        //controlador.manejadorElementos.dibujarComponente();
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
                        controlador.manejadorElementos.setDibujo(dibujo2);
                        //controlador.manejadorElementos.setX(Integer.valueOf(partes[7]));
                        controlador.manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[8]));
                        //controlador.manejadorElementos.setY(Integer.valueOf(partes[8]));
                        controlador.manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[9]));
                        controlador.dibujos.add(controlador.manejadorElementos);
                        linea=br.readLine();
                        String [] conexiones = linea.split(",");
                        for(int i=0;i<((int) Math.pow(2,(splitter.getSalidas()+1))); i++){
                            if(conexiones[i].compareTo(" ")==0)
                                splitter.cargarConexion(i,"");
                            else    
                                splitter.cargarConexion(i,conexiones[i]);
                        }
                        controlador.elementos.add(splitter);
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
                        controlador.elementos.add(fibra);
                        controlador.manejadorElementos = new ElementoGrafico(controlador, Integer.valueOf(partes[1]), fibra);
                        //controlador.manejadorElementos.dibujarComponente();
                        Label dibujo3 = new Label();
                        dibujo3.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
                        dibujo3.setText(fibra.getNombre() + "_"+ fibra.getIdFibra());
                        dibujo3.setContentDisplay(ContentDisplay.TOP);
                        controlador.manejadorElementos.setDibujo(dibujo3);
                        //controlador.manejadorElementos.setX(Integer.valueOf(partes[7]));
                        controlador.manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[11]));
                        //controlador.manejadorElementos.setY(Integer.valueOf(partes[8]));
                        controlador.manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[12]));
                        controlador.dibujos.add(controlador.manejadorElementos);
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
                        controlador.elementos.add(fuente);
                        controlador.manejadorElementos = new ElementoGrafico(controlador, Integer.valueOf(partes[1]), fuente);
                        //controlador.manejadorElementos.dibujarComponente();
                        Label dibujo4 = new Label();
                        dibujo4.setGraphic(new ImageView(new Image("images/dibujo_fuente.png")));
                        dibujo4.setText(fuente.getNombre() + "_"+ fuente.getIdFuente());
                        dibujo4.setContentDisplay(ContentDisplay.TOP);
                        controlador.manejadorElementos.setDibujo(dibujo4);
                        //controlador.manejadorElementos.setX(Integer.valueOf(partes[7]));
                        controlador.manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[10]));
                        //controlador.manejadorElementos.setY(Integer.valueOf(partes[8]));
                        controlador.manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[11]));
                        controlador.dibujos.add(controlador.manejadorElementos);
                        break;
                        
                    case "power":
                        MedidorPotencia potencia= new MedidorPotencia();
                        potencia.setId(Integer.valueOf(partes[1]));
                        potencia.setNombre(nombre);
                        potencia.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        potencia.setConectadoSalida(Boolean.valueOf(partes[3]));
                        potencia.setIdPotencia(Integer.valueOf(partes[4]));
                        controlador.elementos.add(potencia);
                        controlador.manejadorElementos = new ElementoGrafico(controlador, Integer.valueOf(partes[1]), potencia);
                        //controlador.manejadorElementos.dibujarComponente();
                        Label dibujo5 = new Label();
                        dibujo5.setGraphic(new ImageView(new Image("images/dibujo_potencia.png")));
                        dibujo5.setText(potencia.getNombre() + "_"+ potencia.getIdPotencia());
                        dibujo5.setContentDisplay(ContentDisplay.TOP);
                        controlador.manejadorElementos.setDibujo(dibujo5);
                        //controlador.manejadorElementos.setX(Integer.valueOf(partes[7]));
                        controlador.manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[5]));
                        //controlador.manejadorElementos.setY(Integer.valueOf(partes[8]));
                        controlador.manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[6]));
                        controlador.dibujos.add(controlador.manejadorElementos);
                        break;

                    case "spectrum":
                        MedidorEspectro espectro = new MedidorEspectro();
                        espectro.setId(Integer.valueOf(partes[1]));
                        espectro.setNombre(nombre);
                        espectro.setConectadoEntrada(Boolean.valueOf(partes[2]));
                        espectro.setConectadoSalida(Boolean.valueOf(partes[3]));
                        espectro.setIdEspectro(Integer.valueOf(partes[4]));
                        controlador.elementos.add(espectro);
                        controlador.manejadorElementos = new ElementoGrafico(controlador, Integer.valueOf(partes[1]), espectro);
                        //controlador.manejadorElementos.dibujarComponente();
                        Label dibujo6 = new Label();
                        dibujo6.setGraphic(new ImageView(new Image("images/dibujo_espectro.png")));
                        dibujo6.setText(espectro.getNombre() + "_"+ espectro.getIdEspectro());
                        dibujo6.setContentDisplay(ContentDisplay.TOP);
                        controlador.manejadorElementos.setDibujo(dibujo6);
                        //controlador.manejadorElementos.setX(Integer.valueOf(partes[7]));
                        controlador.manejadorElementos.getDibujo().setLayoutX(Integer.valueOf(partes[5]));
                        //controlador.manejadorElementos.setY(Integer.valueOf(partes[8]));
                        controlador.manejadorElementos.getDibujo().setLayoutY(Integer.valueOf(partes[6]));
                        controlador.dibujos.add(controlador.manejadorElementos);
                        break;
                        
                    default:
                        controlador.contadorElemento=Integer.valueOf(partes[1]);
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
    
    private void eventos(ElementoGrafico elem) {
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
    
    private void dibujarLinea(ElementoGrafico elemG) {
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
    
    private void borrarLinea(Line linea){
        linea.setVisible(false);
    }
    
    private void dibujarLineaAtras(ElementoGrafico elem) {
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
    
    private boolean outSideParentBoundsX( Bounds childBounds, double newX, double newY) {

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
    private boolean outSideParentBoundsY( Bounds childBounds, double newX, double newY) {

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

}
