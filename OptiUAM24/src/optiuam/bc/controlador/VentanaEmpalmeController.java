    
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
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
import optiuam.bc.modelo.Empalme;

/**
 * Clase VentanaEmpalmeController la cual se encarga de instanciar un empalme
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see ControladorGeneral
 */
public class VentanaEmpalmeController extends ControladorGeneral implements Initializable {
    
    /**Identificador del empalme*/
    static int idEmpalme = 0;
    /**Controlador del simulador*/
    ControladorGeneral controlador;
    /**Escenario en el cual se agregaran los objetos creados*/
    Stage stage;
    /**Elemento grafico del empalme*/
    ElementoGrafico elemG;
    /**Controlador del emaplme*/
    VentanaEmpalmeController empalmeControl;
    /**Posicion del empalme en el eje X*/
    static double posX;
    /**Posicion del empalme en el eje Y*/
    static double posY;
    
    /**RadioButton para la longitud de onda de 1310 nm*/
    @FXML
    RadioButton rbtn1310;
    /**RadioButton para la longitud de onda de 1550 nm*/
    @FXML
    RadioButton rbtn1550;
    /**RadioButton para el tipo Fusion del empalme*/
    @FXML
    RadioButton rbtnfusion;
    /**RadioButton para el tipo Mecanico del empalme*/
    @FXML
    RadioButton rbtnMecanico;
    /**Caja de texto para ingresar la perdida de insercion del empalme*/
    @FXML
    TextField txtPerdida;
    /**Boton para desconectar el empalme*/
    @FXML
    Button btnDesconectar;
    /**Boton para modificar el empalme*/
    @FXML
    Button btnModificar;
    /**Boton para crear una empalme*/
    @FXML
    Button btnCrear;
    /**Etiqueta de la lista desplegable de elementos disponibles para conectar
     el empalme*/
    @FXML
    Label lblConectarA;
    /**Lista desplegable de elementos disponibles para conectar el empalme*/
    @FXML
    ComboBox cboxConectarA;
    /**Panel para agregar objetos*/
    @FXML
    private Pane Pane1;
    /**Espacio en el cual el usuario puede desplazarse*/
    @FXML
    private ScrollPane scroll;
    
    /**
     * Metodo que muestra el identificador del empalme
     * @return idEmpalme
     */
    public static int getIdEmpalme() {
        return idEmpalme;
    }

    /**
     * Metodo que modifica el identificador del empalme
     * @param idEmpalme Identificador del empalme
     */
    public static void setIdEmpalme(int idEmpalme) {
        VentanaEmpalmeController.idEmpalme = idEmpalme;
    }
    
    /**
     * Metodo que muestra la posicion del empalme en el eje X
     * @return posX
     */
    public static double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion del empalme en el eje X
     * @param posX Posicion en el eje X
     */
    public static void setPosX(double posX) {
        VentanaEmpalmeController.posX = posX;
    }

    /**
     * Metodo que muestra la posicion del empalme en el eje Y
     * @return posY
     */
    public static double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion del empalme en el eje Y
     * @param posY Posicion en el eje Y
     */
    public static void setPosY(double posY) {
        VentanaEmpalmeController.posY = posY;
    }
    
    /**
     * Metodo el cual inicializa la ventana del empalme
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip perdida = new Tooltip();
        perdida.setText("The loss must be" + " min: 0" + " max: 0.5");
        txtPerdida.setTooltip(perdida);
        btnCrear.setVisible(true);
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnModificar.setVisible(false);
    }    
    
    /**
     * Metodo el cual captura los datos obtenidos de la ventana del empalme y
     * crea uno
     * @param event Representa cualquier tipo de accion 
     * @throws java.lang.reflect.InvocationTargetException Proporciona diferentes 
     * excepciones lanzadas bajo el paquete java lang
     */
    public void enviarDatos(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException{
        int tipo=0, longitudOnda=0, id = 0;
        double perdidaInsercion, perdidaMax = 0.5;
        if(rbtnMecanico.isSelected()){
            tipo=1;
        }else if(rbtnfusion.isSelected()){
            tipo=0;
        }   
        if(rbtn1310.isSelected()){
            longitudOnda=1310;
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
        }
        if (txtPerdida.getText().isEmpty() || txtPerdida.getText().compareTo("")==0 || !txtPerdida.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid loss value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid loss value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdida.setText("");
        }
        else if(Double.parseDouble(txtPerdida.getText()) > perdidaMax || Double.parseDouble(txtPerdida.getText()) < 0){
            System.out.println("\nThe loss must be" + " min: 0" + " max: " + perdidaMax);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe loss must be" + " min: 0" + " max: " + perdidaMax,
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdida.setText("");
        }
        else{
            perdidaInsercion = Double.parseDouble(txtPerdida.getText());
            Empalme empalme = new Empalme();
            empalme.setConectadoEntrada(false);
            empalme.setConectadoSalida(false);
            empalme.setIdEmpalme(idEmpalme);
            empalme.setLongitudOnda(longitudOnda);
            empalme.setNombre("splice"); 
            empalme.setPerdidaInsercion(perdidaInsercion);
            empalme.setTipo(tipo);
            guardarEmpalme(empalme);
            idEmpalme++;
            cerrarVentana(event);
        }
    }
    
    /**
     * Metodo que guarda el empalme en el panel
     * @param empalme Empalme con valores almacenados
     */
    public void guardarEmpalme(Empalme empalme) {
        empalme.setId(controlador.getContadorElemento());
        controlador.getElementos().add(empalme);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        elem.setComponente(empalme);
        elem.setId(controlador.getContadorElemento());
        
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_empalme.png")));
        dibujo.setText(empalme.getNombre() + "_"+ empalme.getIdEmpalme());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nSplice created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    
    /**
     * Metodo que duplica un empalme
     * @param empalme Empalme a duplicar
     * @param el Elemento grafico del empalme a duplicar
     */
    public void duplicarEmpalme(Empalme empalme, ElementoGrafico el) {
        empalme.setId(controlador.getContadorElemento());
        controlador.getElementos().add(empalme);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        elem.setComponente(empalme);
        elem.setId(controlador.getContadorElemento());
        
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_empalme.png")));
        dibujo.setText(empalme.getNombre() + "_"+ empalme.getIdEmpalme());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        
        dibujo.setLayoutX(el.getDibujo().getLayoutX()+35);
        dibujo.setLayoutY(el.getDibujo().getLayoutY()+20);
        
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nDuplicate splice!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Metodo el cual le proporciona eventos al empalme tales como movimiento, 
     * abrir ventana para modificarlo o mostrar un menu de acciones
     * @param elem Elemento grafico del empalme
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
                    if(elem.getComponente().isConectadoEntrada()){
                        ElementoGrafico aux;
                        for(int it=0; it<controlador.getDibujos().size();it++){
                            if(elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                                aux=controlador.getDibujos().get(it);
                                aux.getComponente().getLinea().setVisible(false);
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
            elem.getDibujo().setOnMouseClicked((MouseEvent event) -> {
                if(event.getButton()==MouseButton.PRIMARY){
                    try{
                        Stage stage1 = new Stage(StageStyle.UTILITY);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaEmpalme.fxml"));
                        Parent root = loader.load();
                        //Se crea una instancia del controlador del empalme.
                        VentanaEmpalmeController empalmeController = (VentanaEmpalmeController) loader.getController();
                        empalmeController.init(controlador,stage,Pane1,scroll);
                        empalmeController.init2(elem,empalmeController);
                        empalmeController.btnCrear.setVisible(false);
                        empalmeController.btnDesconectar.setVisible(true);
                        empalmeController.lblConectarA.setVisible(true);
                        empalmeController.cboxConectarA.setVisible(true);
                        empalmeController.btnModificar.setVisible(true);
                        
                        Scene scene = new Scene(root);
                        Image ico = new Image("images/acercaDe.png");
                        stage1.getIcons().add(ico);
                        stage1.setTitle("OptiUAM BC - "+elem.getDibujo().getText().toUpperCase());
                        stage1.initModality(Modality.APPLICATION_MODAL);
                        stage1.setScene(scene);
                        stage1.setResizable(false);
                        stage1.showAndWait();
                    }
                    catch(IOException ex){
                        Logger.getLogger(VentanaEmpalmeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(event.getButton()==MouseButton.SECONDARY){
                    mostrarMenu(elem);
                }
        });
    }
    
    /**
     * Metodo el cual muestra un menu de acciones para duplicar, eliminar o 
     * ver propiedades del empalme
     * @param dibujo Elemento grafico del empalme
     */
    public void mostrarMenu(ElementoGrafico dibujo){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("-Duplicated");
        MenuItem menuItem3 = new MenuItem("-Delet");
        MenuItem menuItem4 = new MenuItem("-Properties");

        /*Duplicar*/
        menuItem1.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    //System.out.println(dibujo.getId()+"----"+controlador.getElementos().get(elemento).getId());
                    Empalme empalmeAux=new Empalme();
                    Empalme aux1=(Empalme)controlador.getElementos().get(elemento);
                    empalmeAux.setConectadoEntrada(false);
                    empalmeAux.setConectadoSalida(false);
                    empalmeAux.setIdEmpalme(idEmpalme);
                    empalmeAux.setLongitudOnda(aux1.getLongitudOnda());
                    empalmeAux.setPerdidaInsercion(aux1.getPerdidaInsercion());
                    empalmeAux.setTipo(aux1.getTipo());
                    empalmeAux.setNombre("splice");
                    duplicarEmpalme(empalmeAux,dibujo);
                    idEmpalme++;
                    break;
                }
            }
        });

        /*Eliminar*/
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
                    Empalme aux= (Empalme)controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux); 
                }
            }    
            dibujo.getDibujo().setVisible(false);
            
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nRemoved splice!",
                    aceptar);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.showAndWait();

        });
        
        /*Propiedades*/
        menuItem4.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Stage s = new Stage(StageStyle.DECORATED);
                    Image ico = new Image("images/dibujo_empalme.png");
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC - Properties");
                    s.initModality(Modality.APPLICATION_MODAL);
                    Empalme aux= (Empalme)controlador.getElementos().get(elemento);
                    String tip;
                    Label label;
                    if(aux.getTipo() == 0){
                        tip = "Fusion";
                        label = new Label("  Name: "+aux.getNombre()+
                            "\n  Id: "+aux.getIdEmpalme()+
                            "\n  Input: "+aux.getElementoConectadoEntrada()+
                            "\n  Output :"+aux.getElementoConectadoSalida()+
                            "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                            "\n  Type: "+tip+
                            "\n  Insertion Loss: "+aux.getPerdidaInsercion()+" dB");
                        Scene scene = new Scene(label, 190, 130);
                        s.setScene(scene);
                        s.setResizable(false);
                        s.showAndWait();
                    }
                    else if(aux.getTipo()== 1){
                        tip = "Mechanic";
                        label = new Label("  Name: "+aux.getNombre()+
                            "\n  Id: "+aux.getIdEmpalme()+
                            "\n  Input: "+aux.getElementoConectadoEntrada()+
                            "\n  Output :"+aux.getElementoConectadoSalida()+
                            "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                            "\n  Type: "+tip+
                            "\n  Insertion Loss: "+aux.getPerdidaInsercion()+" dB");
                        Scene scene = new Scene(label, 190, 130);
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
     * Metodo para cerrar la ventana del empalme
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    /**
     * Metodo para desconectar el empalme
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void Desconectar(ActionEvent event){
        for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
                if(empalmeControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                    Componente comp= controlador.getElementos().get(elemento2);
                    comp.setConectadoEntrada(false);
                    comp.setElementoConectadoEntrada("");
                    System.out.println(comp.getNombre());
                    break;
                }
        }
        empalmeControl.cboxConectarA.getSelectionModel().select(0);
        if(elemG.getComponente().isConectadoSalida()){
            elemG.getComponente().setConectadoSalida(false);
            elemG.getComponente().setElementoConectadoSalida("");
            elemG.getComponente().getLinea().setVisible(false);
        }
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nDisconnected splice!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
        cerrarVentana(event);
    }
    
    /**
     * Metodo para modificar el empalme
     * @param event Representa cualquier tipo de accion
     * @throws java.lang.reflect.InvocationTargetException Proporciona diferentes 
     * excepciones lanzadas bajo el paquete java lang
     */
    @FXML
    public void modificar(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException{
        Empalme aux = (Empalme) elemG.getComponente();
        int tipo=0, longitudOnda=0, id = 0;
        double perdidaInsercion, perdidaMax = 0.5;
        if(rbtnMecanico.isSelected()){
            tipo=1;
        }else if(rbtnfusion.isSelected()){
            tipo=0;
        }   
        if(rbtn1310.isSelected()){
            longitudOnda=1310;
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
        }
        if((empalmeControl.cboxConectarA.getSelectionModel().getSelectedIndex())==0){
            Desconectar(event);
        }else{
            if(aux.isConectadoSalida()){elemG.getComponente().getLinea().setVisible(false);}
            aux.setConectadoSalida(true);
            for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
                if(empalmeControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
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
        if (txtPerdida.getText().isEmpty() || txtPerdida.getText().compareTo("")==0 || !txtPerdida.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid loss value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid loss value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdida.setText("");
        }
        else if(Double.parseDouble(txtPerdida.getText()) > perdidaMax || Double.parseDouble(txtPerdida.getText()) < 0){
            System.out.println("\nThe loss must be" + " min: 0" + " max: " + perdidaMax);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe loss must be" + " min: 0" + " max: " + perdidaMax,
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdida.setText("");
        }
        else{
            perdidaInsercion = Double.parseDouble(txtPerdida.getText());
            aux.setLongitudOnda(longitudOnda);
            aux.setNombre("splice");
            aux.setPerdidaInsercion(perdidaInsercion);
            aux.setTipo(tipo);
            cerrarVentana(event);

            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nModified splice!",
                    aceptar);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.showAndWait();
            
            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
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
     */
    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1, ScrollPane scroll) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.scroll=scroll;
    }
    
    /**
     * Metodo que recibe el elemento y el controlador y, a partir de estos,
     * puede mostrar los valores inciales del elemento 
     * @param elem Elemento grafico
     * @param empalmeController Controlador del empalme
    */
    public void init2(ElementoGrafico elem, VentanaEmpalmeController empalmeController) {
        this.elemG=elem;
        this.empalmeControl=empalmeController;
        
        if(elemG.getComponente().isConectadoSalida()==true){
            empalmeControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
        }else{
            empalmeControl.cboxConectarA.getItems().add("Desconected");
            empalmeControl.cboxConectarA.getSelectionModel().select(0);
             for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if("fiber".equals(controlador.getElementos().get(elemento).getNombre())){
                    if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                        empalmeControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                    }
                }
             }
        }
        
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                Empalme emp= (Empalme)controlador.getElementos().get(elemento);
                System.out.println(emp.getTipo()+"\t"+emp.getLongitudOnda());
                if(emp.getTipo()==0){
                    empalmeControl.rbtnfusion.setSelected(true);
                }else if(emp.getTipo()==1){
                    empalmeControl.rbtnMecanico.setSelected(true);
                }
                if(emp.getLongitudOnda()==1310){
                    empalmeControl.rbtn1310.setSelected(true);
                }else if(emp.getLongitudOnda()==1550){
                    empalmeControl.rbtn1550.setSelected(true);
                }
                empalmeControl.txtPerdida.setText(String.valueOf(emp.getPerdidaInsercion()));
            }
        }
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia delante del empalme 
     * con otro elemento
     * @param elemG Elemento grafico del empalme
     */
    public void dibujarLinea(ElementoGrafico elemG) {
        Line line= new Line();   
        line.setStartX(elemG.getDibujo().getLayoutX()+55);
        line.setStartY(elemG.getDibujo().getLayoutY()+15);
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);
        line.setEndX(aux.getDibujo().getLayoutX()+3);
        line.setEndY(aux.getDibujo().getLayoutY()+20);
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        elemG.getComponente().setLinea(line);
              
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia atras del empalme 
     * con otro elemento
     * @param elem Elemento grafico del empalme
     */
    public void dibujarLineaAtras(ElementoGrafico elem) {
        Line line= new Line();   
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);
        line.setStartX(aux.getDibujo().getLayoutX()+aux.getDibujo().getWidth()-3);
        line.setStartY(aux.getDibujo().getLayoutY()+20);
        line.setEndX(elem.getDibujo().getLayoutX());
        line.setEndY(elem.getDibujo().getLayoutY()+10);
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        aux.getComponente().setLinea(line);
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
