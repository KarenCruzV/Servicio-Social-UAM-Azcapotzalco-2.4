
package optiuam.bc.controlador;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Fuente;

/**
 * Clase VentanaFuenteController la cual se encarga de instanciar una fuente
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see ControladorGeneral
 */
public class VentanaFuenteController extends ControladorGeneral implements Initializable {
    
    /**Identificador de la fuente*/
    static int idFuente = 0;
    /**Controlador del simulador*/
    ControladorGeneral controlador;
    /**Escenario en el cual se agregaran los objetos creados*/
    Stage stage;
    /**Elemento grafico de la fuente*/
    ElementoGrafico elemG;
    /**Controlador de la fuente*/
    VentanaFuenteController fuenteControl;
    /**Posicion de la fuente en el eje X*/
    static double posX;
    /**Posicion de la fuente en el eje Y*/
    static double posY;
    
    /**RadioButton para la longitud de onda de 1310 nm*/
    @FXML
    RadioButton rbtn1310;
    /**RadioButton para la longitud de onda de 1550 nm*/
    @FXML
    RadioButton rbtn1550;
    /**RadioButton para el tipo Laser de la fuente*/
    @FXML
    RadioButton rbtnLaser;
    /**RadioButton para el tipo LED de la fuente*/
    @FXML
    RadioButton rbtnLed;
    /**Caja de texto para ingresar la potencia de la fuente*/
    @FXML
    TextField txtPotencia;
    /**Caja de texto para ingresar la anchura espectral de la fuente*/
    @FXML
    TextField txtAnchuraEspectro;
    /**Caja de texto para ingresar la velocidad de transmision de la fuente*/
    @FXML
    TextField txtVelocidad;
    /**Boton para configurar el pulso de la fuente*/
    @FXML
    Button btnPulso;
    /**Boton para crear una fuente*/
    @FXML
    Button btnCrear;
    /**Boton para desconectar la fuente*/
    @FXML
    Button btnDesconectar;
    /**Boton para modificar la fuente*/
    @FXML
    Button btnModificar;
    /**Etiqueta de la lista desplegable de elementos disponibles para conectar
     la fuente*/
    @FXML
    Label lblConectarA;
    /**Lista desplegable de elementos disponibles para conectar la fuente*/
    @FXML
    ComboBox cboxConectarA;
    /**Separador de la ventana fuente*/
    @FXML
    Separator separator;
    /**Panel para agregar objetos*/
    @FXML
    private Pane Pane1;
    /**Espacio en el cual el usuario puede desplazarse*/
    @FXML
    private ScrollPane scroll;
    
    /**
     * Metodo que muestra el identificador de la fuente
     * @return idFuente
     */
    public static int getIdFuente() {
        return idFuente;
    }

    /**
     * Metodo que modifica el identificador de la fuente
     * @param idFuente Identificador de la fuente
     */
    public static void setIdFuente(int idFuente) {
        VentanaFuenteController.idFuente = idFuente;
    }
    
    /**
     * Metodo que muestra la posicion de la fuente en el eje X
     * @return posX
     */
    public static double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion de la fuente en el eje X
     * @param posX Posicion en el eje X
     */
    public static void setPosX(double posX) {
        VentanaFuenteController.posX = posX;
    }

    /**
     * Metodo que muestra la posicion de la fuente en el eje Y
     * @return posY
     */
    public static double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion de la fuente en el eje Y
     * @param posY Posicion en el eje Y
     */
    public static void setPosY(double posY) {
        VentanaFuenteController.posY = posY;
    }
    
    /**
     * Metodo el cual inicializa la ventana de la fuente
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip perdida = new Tooltip();
        perdida.setText("Laser: The width value must be max 1.0 nm"
                + "\nLed: The width value must be min: 0.01 nm  max: 1.0 nm");
        txtAnchuraEspectro.setTooltip(perdida);
        
        btnPulso.setVisible(false);
        separator.setVisible(false);
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnModificar.setVisible(false);
    }    
    
    /**
     * Metodo el cual captura los datos obtenidos de la ventana de la fuente y
     * crea una
     * @param event Representa cualquier tipo de accion 
     * @throws java.lang.reflect.InvocationTargetException 
     */
    public void enviarDatos(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException{
        int longitudOnda=0, tipo=0, id = 0;
        double potencia, anchura, velocidad;
        
        if(rbtnLaser.isSelected()){
            tipo = 0;
        }
        else if(rbtnLed.isSelected()){
            tipo = 1;
        }
        if(rbtn1310.isSelected()){
            longitudOnda=1310;
        }
        else if(rbtn1550.isSelected()){
            longitudOnda=1550;
        }
        if (txtPotencia.getText().isEmpty() || txtPotencia.getText().compareTo("")==0 || !txtPotencia.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("Invalid power value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid power value");
            alert.showAndWait();
            txtPotencia.setText("");
        }
        else if(txtAnchuraEspectro.getText().isEmpty() || txtAnchuraEspectro.getText().compareTo("")==0 || !txtAnchuraEspectro.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("Invalid width value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid width value");
            alert.showAndWait();
            txtAnchuraEspectro.setText("");
        }
        else if(txtVelocidad.getText().isEmpty() || txtVelocidad.getText().compareTo("")==0 || !txtVelocidad.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("Invalid speed value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid speed value");
            alert.showAndWait();
            txtVelocidad.setText("");
        }
        else if((tipo==0 && Double.parseDouble(txtAnchuraEspectro.getText())<=0) ||
           (tipo==0 &&Double.parseDouble(txtAnchuraEspectro.getText())>1)){
            System.out.println("\nThe width value must be max 1 nm");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The width value must be max 1 nm");
            alert.showAndWait();
            txtAnchuraEspectro.setText("");
        }
        else if((tipo == 1 && Double.parseDouble(txtAnchuraEspectro.getText())< (double)(0.01)) ||
           (tipo==1 &&Double.parseDouble(txtAnchuraEspectro.getText())> 1)){
            System.out.println("\nThe width value must be min: 0.01 nm  max: 1.0 nm");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The width value must be min: 0.01 nm  max: 1.0 nm");
            alert.showAndWait();
            txtAnchuraEspectro.setText("");
        } 
        else{
            potencia = Double.parseDouble(txtPotencia.getText());
            anchura = Double.parseDouble(txtAnchuraEspectro.getText());
            velocidad = Double.parseDouble(txtVelocidad.getText());
            Fuente fuente= new Fuente();
            fuente.setAnchura(anchura);
            fuente.setIdFuente(idFuente);
            fuente.setLongitudOnda(longitudOnda);
            fuente.setNombre("source"); 
            fuente.setPotencia(potencia);
            fuente.setTipo(tipo);
            fuente.setVelocidad(velocidad);
            fuente.setConectadoEntrada(false);
            fuente.setConectadoSalida(false);
            guardarFuente(fuente);
            idFuente++;
            cerrarVentana(event);
        }
    }
    
    /**
     * Metodo que guarda la fuente en el panel
     * @param fuente Fuente con valores almacenados
     */
    public void guardarFuente(Fuente fuente) {
        fuente.setId(controlador.getContadorElemento());
        controlador.getElementos().add(fuente);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        fuente.setPosX(dibujo.getLayoutX());
        fuente.setPosY(dibujo.getLayoutY());
        setPosX(fuente.getPosX());
        setPosY(fuente.getPosY());
        
        elem.setComponente(fuente);
        elem.setId(controlador.getContadorElemento());
        
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fuente.png")));
        dibujo.setText(fuente.getNombre() + "_"+ fuente.getIdFuente());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\nSource created!");
        alert.showAndWait();
    }
    
    /**
     * Metodo que duplica una fuente
     * @param fuente Fuente a duplicar
     * @param el Elemento grafico de la fuente a duplicar
     */
    public void duplicarFuente(Fuente fuente,ElementoGrafico el) {
        fuente.setId(controlador.getContadorElemento());
        controlador.getElementos().add(fuente);
        Label dibujo = new Label();
        ElementoGrafico elem = new ElementoGrafico();
        
        fuente.setPosX(dibujo.getLayoutX());
        fuente.setPosY(dibujo.getLayoutY());
        setPosX(fuente.getPosX());
        setPosY(fuente.getPosY());
        
        elem.setComponente(fuente);
        elem.setId(controlador.getContadorElemento());
        
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fuente.png")));
        dibujo.setText(fuente.getNombre() + "_"+ fuente.getIdFuente());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        dibujo.setLayoutX(el.getDibujo().getLayoutX()+35);
        dibujo.setLayoutY(el.getDibujo().getLayoutY()+20);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\nDuplicate source!");
        alert.showAndWait();
    }
    
    /**
     * Metodo el cual le proporciona eventos a la fuente tales como movimiento, 
     * abrir ventana para modificarlo o mostrar un menu de acciones
     * @param elem Elemento grafico de la fuente
     */
    public void eventos(ElementoGrafico elem) {
        elem.getDibujo().setOnMouseDragged((MouseEvent event) -> {
            if(event.getButton()==MouseButton.PRIMARY){
                double newX=event.getSceneX();
                double newY=event.getSceneY();
                int j=0;
                for(int a=0; a<Pane1.getChildren().size();a++){
                    if(Pane1.getChildren().get(a).toString().contains(elem.getDibujo().getText())){
                        j=a;
                        break;
                    }
                }
                if( outSideParentBoundsX(elem.getDibujo().getLayoutBounds(), newX, newY) ) {    //return; 
                }else{
                    elem.getDibujo().setLayoutX(Pane1.getChildren().get(j).getLayoutX()+event.getX()+1);
                }
                
                if(outSideParentBoundsY(elem.getDibujo().getLayoutBounds(), newX, newY) ) {    //return; 
                }else{
                elem.getDibujo().setLayoutY(Pane1.getChildren().get(j).getLayoutY()+event.getY()+1);}

                if(elem.getComponente().isConectadoSalida()==true){
                    elem.getComponente().getLinea().setVisible(false);
                    dibujarLinea(elem);
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
        
        elem.getDibujo().setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton()==MouseButton.PRIMARY){
                try{
                    Stage stage1 = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaFuente.fxml"));
                    Parent root = loader.load();
                    //Se crea una instancia del controlador de la fuente
                    VentanaFuenteController fuenteController = (VentanaFuenteController) loader.getController();
                    //fuenteController.init(controlador, stage, Pane1);
                    /*Se necesito usar otro init de forma que el controller sepa cual es el elemento
                        con el que se esta trabajando ademas de que se manda el mismo controller para 
                        iniciar con los valores del elemento mandado.
                    */
                    fuenteController.init(controlador, this.stage, this.Pane1,this.scroll);
                    fuenteController.init2(elem,fuenteController);
                    fuenteController.btnCrear.setVisible(false);
                    fuenteController.btnPulso.setVisible(true);
                    fuenteController.separator.setVisible(true);
                    fuenteController.btnDesconectar.setVisible(true);
                    fuenteController.lblConectarA.setVisible(true);
                    fuenteController.cboxConectarA.setVisible(true);
                    fuenteController.btnModificar.setVisible(true);

                    Scene scene = new Scene(root);
                    Image ico = new Image("images/acercaDe.png");
                    stage1.getIcons().add(ico);
                    stage1.setTitle("OptiUAM BC "+elem.getDibujo().getText().toUpperCase());
                    stage1.initModality(Modality.APPLICATION_MODAL);
                    stage1.setScene(scene);
                    stage1.setResizable(false);
                    stage1.showAndWait();
                }
                catch(IOException ex){
                    Logger.getLogger(VentanaConectorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if(event.getButton()==MouseButton.SECONDARY){
                mostrarMenu(elem);
            }
        });
    }
    
    /**
     * Metodo el cual muestra un menu de acciones para duplicar, eliminar o 
     * ver propiedades de la fuente
     * @param dibujo Elemento grafico de la fuente
     */
    public void mostrarMenu(ElementoGrafico dibujo){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("-Duplicated");
        MenuItem menuItem3 = new MenuItem("-Delete");
        MenuItem menuItem4 = new MenuItem("-Properties");

        /*Duplicar*/
        menuItem1.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    System.out.println(dibujo.getId()+"----"+controlador.getElementos().get(elemento).getId());
                    Fuente fuenteAux=new Fuente();
                    Fuente fuenteAux1=(Fuente)controlador.getElementos().get(elemento);
                    fuenteAux.setAnchura(fuenteAux1.getAnchura());
                    fuenteAux.setConectadoEntrada(false);
                    fuenteAux.setConectadoSalida(false);
                    fuenteAux.setLongitudOnda(fuenteAux1.getLongitudOnda());
                    fuenteAux.setPotencia(fuenteAux1.getPotencia());
                    fuenteAux.setTipo(fuenteAux1.getTipo());
                    fuenteAux.setNombre("source");
                    fuenteAux.setPulso(fuenteAux1.getA0(),fuenteAux1.getT0(),fuenteAux1.getW0(),fuenteAux1.getC(),fuenteAux1.getM());
                    fuenteAux.setVelocidad(fuenteAux1.getVelocidad());
                    fuenteAux.setIdFuente(idFuente);
                    duplicarFuente(fuenteAux,dibujo);
                    System.out.println(fuenteAux);
                    idFuente++;
                    break;
                }
            }
        });

        /**Eliminar*/
        menuItem3.setOnAction(e ->{
            if(dibujo.getComponente().isConectadoSalida()==true){
                for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                    if(dibujo.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento).getDibujo().getText())){
                        Componente aux= controlador.getElementos().get(elemento);
                        System.out.println();
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
                        aux.setConectadoSalida(false);
                        aux.setElementoConectadoSalida("");
                        aux.getLinea().setVisible(false);
                    }
                }
            }
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Fuente aux= (Fuente)controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux); 
                }
            }    
            dibujo.getDibujo().setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("\nRemoved source!");
            alert.showAndWait();
        });
        
        /*Propiedades*/
        menuItem4.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Stage s = new Stage(StageStyle.DECORATED);
                    Image ico = new Image("images/dibujo_fuente.png");
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC Properties");
                    s.initModality(Modality.APPLICATION_MODAL);
                    Fuente aux= (Fuente)controlador.getElementos().get(elemento);
                    String tip;
                    Label label;
                    if(aux.getTipo() == 0){
                        tip = "Laser";
                        label = new Label("  Name: "+aux.getNombre()+
                            "\n  Id: "+aux.getIdFuente()+
                            "\n  Input: "+aux.getElementoConectadoEntrada()+
                            "\n  Output :"+aux.getElementoConectadoSalida()+
                            "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                            "\n  Type: "+tip+
                            "\n  Potency: "+aux.getPotencia()+" dBm"+
                            "\n  Spectral Width: "+aux.getAnchura()+" nm"+
                            "\n  Transmission Speed: "+aux.getVelocidad()+" Gbits/seg"+
                            "\n  ----------------Pulse----------------"+
                            "\n  A0: "+aux.getA0() + "\n  C: "+aux.getC()+
                            "\n  T0: "+aux.getT0() + "\n  W0: "+aux.getW0()+
                            "\n  M: "+aux.getM());
                        Scene scene = new Scene(label, 190, 250);
                        s.setScene(scene);
                        s.setResizable(false);
                        s.showAndWait();
                    }
                    else if(aux.getTipo()== 1){
                        tip = "LED";
                        label = new Label("  Name: "+aux.getNombre()+
                            "\n  Id: "+aux.getIdFuente()+
                            "\n  Input: "+aux.getElementoConectadoEntrada()+
                            "\n  Output :"+aux.getElementoConectadoSalida()+
                            "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                            "\n  Type: "+tip+
                            "\n  Potency: "+aux.getPotencia()+" dBm"+
                            "\n  Spectral Width: "+aux.getAnchura()+" nm"+
                            "\n  Transmission Speed: "+aux.getVelocidad()+" Gbits/seg"+
                            "\n  ----------------Pulse----------------"+
                            "\n  A0: "+aux.getA0() + "\n  C: "+aux.getC()+
                            "\n  T0: "+aux.getT0() + "\n  W0: "+aux.getW0()+
                            "\n  M: "+aux.getM());
                        Scene scene = new Scene(label, 190, 250);
                        s.setScene(scene);
                        s.setResizable(false);
                        s.showAndWait();
                    }
                }
            }
        });

        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem3);
        contextMenu.getItems().add(menuItem4);
        dibujo.getDibujo().setContextMenu(contextMenu);
    }
      
    /**
     * Metodo para cerrar la ventana de la fuente
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    /**
     * Metodo para desconectar la fuente
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    private void Desconectar(ActionEvent event){
        for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
            if(fuenteControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                Componente comp= controlador.getElementos().get(elemento2);
                comp.setConectadoEntrada(false);
                comp.setElementoConectadoEntrada("");
                System.out.println(comp.getNombre());
                break;
            }
        }
        fuenteControl.cboxConectarA.getSelectionModel().select(0);
        if(elemG.getComponente().isConectadoSalida()){
            elemG.getComponente().setConectadoSalida(false);
            elemG.getComponente().setElementoConectadoSalida("");
            elemG.getComponente().getLinea().setVisible(false);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("\nDisconnected source!");
            alert.showAndWait();
        cerrarVentana(event);
    }
    
    /**
     * Metodo para modificar la fuente
     * @param event Representa cualquier tipo de accion
     * @throws java.lang.reflect.InvocationTargetException
     */
    @FXML
    private void modificar(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException{
        Fuente aux = (Fuente) elemG.getComponente();
        int longitudOnda=0, tipo=0, id = 0;
        double potencia, anchura, velocidad;

        if(rbtnLaser.isSelected()){
            tipo = 0;
        }
        else if(rbtnLed.isSelected()){
            tipo = 1;
        }
        if(rbtn1310.isSelected()){
            longitudOnda=1310;
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
        }
        if((fuenteControl.cboxConectarA.getSelectionModel().getSelectedIndex())==0){
            Desconectar(event);
        }else{
            if(aux.isConectadoSalida()){
                elemG.getComponente().getLinea().setVisible(false);
            }
            aux.setConectadoSalida(true);

            for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
                if(fuenteControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                    ElementoGrafico eg= controlador.getDibujos().get(elemento2);
                    aux.setElementoConectadoSalida(eg.getDibujo().getText());
                    aux.setConectadoSalida(true);
                    System.out.println(controlador.getDibujos().get(elemento2).getComponente().toString());
                    eg.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                    eg.getComponente().setConectadoEntrada(true);
                    break;
                }
            }
            dibujarLinea(elemG);
        }
        if (txtPotencia.getText().isEmpty() || txtPotencia.getText().compareTo("")==0 || !txtPotencia.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("Invalid power value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid power value");
            alert.showAndWait();
            txtPotencia.setText("");
        }
        else if(txtAnchuraEspectro.getText().isEmpty() || txtAnchuraEspectro.getText().compareTo("")==0 || !txtAnchuraEspectro.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("Invalid width value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid width value");
            alert.showAndWait();
            txtAnchuraEspectro.setText("");
        }
        else if(txtVelocidad.getText().isEmpty() || txtVelocidad.getText().compareTo("")==0 || !txtVelocidad.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("Invalid speed value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid speed value");
            alert.showAndWait();
            txtVelocidad.setText("");
        }
        else if((tipo==0 &&Double.parseDouble(txtAnchuraEspectro.getText())<=0) ||
           (tipo==0 &&Double.parseDouble(txtAnchuraEspectro.getText())>1)){
            System.out.println("\nThe width value must be max 1 nm");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The width value must be max 1 nm");
            alert.showAndWait();
            txtAnchuraEspectro.setText("");
        }
        else if((tipo == 1 &&Double.parseDouble(txtAnchuraEspectro.getText())< (double)(0.01)) ||
           (tipo==1 &&Double.parseDouble(txtAnchuraEspectro.getText())> 1)){
            System.out.println("\nThe width value must be min: 0.01 nm  max: 1.0 nm");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The width value must be min: 0.01 nm  max: 1.0 nm");
            alert.showAndWait();
            txtAnchuraEspectro.setText("");
        } 
        else{
            potencia = Double.parseDouble(txtPotencia.getText());
            anchura = Double.parseDouble(txtAnchuraEspectro.getText());
            velocidad = Double.parseDouble(txtVelocidad.getText());
            aux.setAnchura(anchura);
            aux.setLongitudOnda(longitudOnda);
            aux.setNombre("source");
            aux.setPotencia(potencia);
            aux.setTipo(tipo);
            aux.setVelocidad(velocidad);
            cerrarVentana(event);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("\nModified source!");
            alert.showAndWait();
            
            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
            }
        }
    }
    
    /**
     * Metodo para configurar el pulso de la fuente
     * @throws java.io.IOException
     */
    @FXML
    public void configurarPulso() throws IOException {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("VentanaPulso.fxml"));
            Parent root = loader.load();
            VentanaPulsoController pulControl= loader.getController();
            pulControl.init(elemG);
            
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            Stage st = new Stage(StageStyle.UTILITY);
            st.getIcons().add(ico);
            st.setTitle("OptiUAM BC Pulse Configuration");
            st.setScene(scene);
            st.showAndWait();
            st.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(VentanaFuenteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que proporciona lo necesario para que la ventana reconozca a 
     * que elemento se refiere
     * @param controlador Controlador del simulador
     * @param stage Escenario en el cual se agregan los objetos creados
     * @param Pane1 Panel para agregar objetos
     * @param scroll Espacio en el cual el usuario puede desplazarse
     */
    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1,ScrollPane scroll) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.scroll=scroll;
    }

    /**
     * Metodo que recibe el elemento y el controlador y, a partir de estos,
     * puede mostrar los valores inciales del elemento 
     * @param elem Elemento grafico
     * @param fuenteController Controlador de ls fuente
    */
    public void init2(ElementoGrafico elem, VentanaFuenteController fuenteController) {
        this.elemG = elem;
        this.fuenteControl=fuenteController;
        
        if(elemG.getComponente().isConectadoSalida()==true){
            fuenteControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
        }else{
            fuenteControl.cboxConectarA.getItems().add("Desconected");
            fuenteControl.cboxConectarA.getSelectionModel().select(0);
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if("connector".equals(controlador.getElementos().get(elemento).getNombre())){
                    if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                        fuenteControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                    }
                }
            }
        }
        
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                Fuente fue = (Fuente)controlador.getElementos().get(elemento);
                System.out.println(fue.getTipo()+"\t"+fue.getLongitudOnda());
                if(fue.getTipo()==0){
                    fuenteControl.rbtnLaser.setSelected(true);
                }else if(fue.getTipo()==1){
                    fuenteControl.rbtnLed.setSelected(true);
                }
                if(fue.getLongitudOnda()==1310){
                    fuenteControl.rbtn1310.setSelected(true);
                }else if(fue.getLongitudOnda()==1550){
                    fuenteControl.rbtn1550.setSelected(true);
                }
                fuenteControl.txtAnchuraEspectro.setText(String.valueOf(fue.getAnchura()));
                fuenteControl.txtPotencia.setText(String.valueOf(fue.getPotencia()));
                fuenteControl.txtVelocidad.setText(String.valueOf(fue.getVelocidad()));
            }
            
        }
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia delante de la fuente  
     * con otro elemento
     * @param elemG Elemento grafico de la fuente
     */
    public void dibujarLinea(ElementoGrafico elemG) {
        Line line= new Line();   
        line.setStartX(elemG.getDibujo().getLayoutX()+45);
        line.setStartY(elemG.getDibujo().getLayoutY()+7);
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);
        line.setEndX(aux.getDibujo().getLayoutX());
        line.setEndY(aux.getDibujo().getLayoutY()+8);
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        elemG.getComponente().setLinea(line);
              
    }
    
    /**
     * Metodo que delimita el movimiento en el eje X en el panel para que el 
     * elemento grafico no salga del area de trabajo
     */
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
        
        return false;
    }
    
    /**
     * Metodo que delimita el movimiento en el eje Y en el panel para que el 
     * elemento grafico no salga del area de trabajo
     */
    private boolean outSideParentBoundsY( Bounds childBounds, double newX, double newY) {
        Bounds parentBounds = Pane1.getLayoutBounds();
        
        //check if too down
        if( parentBounds.getMaxY() <= (newY + childBounds.getMaxY()) ) {
            return true ;
        }
        //check if too up
        if( parentBounds.getMinY()+179 >= (newY + childBounds.getMinY()) ) {
            return true ;
        }

        return false;
    }
    
}
