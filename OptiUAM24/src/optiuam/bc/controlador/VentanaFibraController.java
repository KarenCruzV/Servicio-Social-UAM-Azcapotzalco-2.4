
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
import optiuam.bc.modelo.Fibra;

/**
 * Clase VentanaFibraController la cual se encarga de instanciar una fibra
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see ControladorGeneral
 */
public class VentanaFibraController extends ControladorGeneral implements Initializable {
    
    /**Identificador de la fibra*/
    static int idFibra = 0;
    /**Controlador del simulador*/
    ControladorGeneral controlador;
    /**Escenario en el cual se agregaran los objetos creados*/
    Stage stage;
    /**Elemento grafico de la fibra*/
    ElementoGrafico elemG;
    /**Controlador de la fibra*/
    VentanaFibraController fibraControl;
    /**Posicion del conector en el eje X*/
    static double posX;
    /**Posicion del conector en el eje Y*/
    static double posY;
    
    /**Caja de texto para ingresar la atenuacion de la fibra*/
    @FXML
    TextField txtAtenue;
    /**Caja de texto para ingresar la dispersion de la fibra*/
    @FXML
    TextField txtDisp;
    /**Caja de texto para ingresar la longitud del cable de la fibra*/
    @FXML
    TextField txtDistancia;
    /**RadioButton para el modo Monomodo de la fibra*/
    @FXML 
    RadioButton rbtnMono;
    /**RadioButton para el modo Multimodo de la fibra*/
    @FXML 
    RadioButton rbtnMulti;
    /**RadioButton para la longitud de onda de 1310 nm*/
    @FXML 
    RadioButton rbtn1310;
    /**RadioButton para la longitud de onda de 1550 nm*/
    @FXML 
    RadioButton rbtn1550;
    /**RadioButton para el tipo Otro de la fibra*/
    @FXML 
    RadioButton rbtnOtro;
    /**RadioButton para el tipo smf-28 de la fibra*/
    @FXML 
    RadioButton rbtn28;
    /**RadioButton para el tipo mm50 de la fibra*/
    @FXML 
    RadioButton rbtn50;
    /**Boton para crear una fibra*/
    @FXML
    Button btnCrear;
    /**Boton para desconectar la fibra*/
    @FXML
    Button btnDesconectar;
    /**Boton para modificar la fibra*/
    @FXML
    Button btnModificar;
    /**Etiqueta de la lista desplegable de elementos disponibles para conectar
     la fibra*/
    @FXML
    Label lblConectarA;
    /**Lista desplegable de elementos disponibles para conectar la fibra*/
    @FXML
    ComboBox cboxConectarA;
    /**Separador de la ventana fibra*/
    @FXML
    Separator separator;
    /**Panel para agregar objetos*/
    @FXML
    private Pane Pane1;
    /**Espacio en el cual el usuario puede desplazarse*/
    @FXML
    private ScrollPane scroll;

    /**
     * Metodo que muestra el identificador de la fibra
     * @return idFibra
     */
    public static int getIdFibra() {
        return idFibra;
    }

    /**
     * Metodo que modifica el identificador de la fibra
     * @param idFibra Identificador de la fibra
     */
    public static void setIdFibra(int idFibra) {
        VentanaFibraController.idFibra = idFibra;
    }
    
    /**
     * Metodo que muestra la posicion de la fibra en el eje X
     * @return posX
     */
    public static double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion de la fibra en el eje X
     * @param posX Posicion en el eje X
     */
    public static void setPosX(double posX) {
        VentanaFibraController.posX = posX;
    }

    /**
     * Metodo que muestra la posicion de la fibra en el eje Y
     * @return posY
     */
    public static double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion de la fibra en el eje Y
     * @param posY Posicion en el eje Y
     */
    public static void setPosY(double posY) {
        VentanaFibraController.posY = posY;
    }
    
    /**
     * Metodo el cual inicializa la ventana de la fibra
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnModificar.setVisible(false);
        separator.setVisible(false);
    } 
    
    /**
     * Metodo que proporciona automaticamente los valores de la atenuacion y
     * dispersion de la fibra al seleccionar la longitud de onda de 1310 nm y
     * dependiendo del modo
     */
    @FXML
    public void rbtn1310Action(){
        rbtn1310.setSelected(true);
        if (!rbtnOtro.isSelected()) {
            if (rbtnMono.isSelected()) { 
                txtAtenue.setText("0.32");
                txtAtenue.setEditable(false);
                txtDisp.setText("0");
                txtDisp.setEditable(false);
            } else { 
                txtAtenue.setText("0.36");
                txtAtenue.setEditable(false);
                txtDisp.setText("3.5");
                txtDisp.setEditable(false);
            }
        }
    }
    
    /**
     * Metodo que proporciona automaticamente los valores de la atenuacion y
     * dispersion de la fibra al seleccionar la longitud de onda de 1550 nm y
     * dependiendo del modo
     */
    @FXML
    public void rbtn1550Action(){
        rbtn1550.setSelected(true);
        if (!rbtnOtro.isSelected()) {
            if (rbtnMono.isSelected()) { 
                txtAtenue.setText("0.18");
                txtAtenue.setEditable(false);
                txtDisp.setText("18");
                txtDisp.setEditable(false);
            }
        }
    }
    
    /**
     * Metodo que corresponde al tipo de fibra mm50
     */
    @FXML
    public void rbtnMm50(){
        rbtn50.setSelected(true);
        if (!rbtnOtro.isSelected()) {
            rbtn1310.setSelected(true);
            rbtn1550.setDisable(true);
            rbtnMulti.setSelected(true);
            rbtnMulti.setDisable(false);
            rbtnMono.setDisable(true);
            txtAtenue.setText("0.36");
            txtAtenue.setEditable(false);
            txtDisp.setText("3.5");
            txtDisp.setEditable(false);
        }
    }
    
    /**
     * Metodo que corresponde al tipo de fibra smf-28
     */
    @FXML
    public void rbtnSmf28(){
        rbtn28.setSelected(true);
        if (!rbtnOtro.isSelected()) {
            rbtn1550.setDisable(false);
            rbtnMono.setSelected(true);
            rbtnMono.setDisable(false);
            rbtnMulti.setDisable(true);
            if (rbtn1310.isSelected()) { 
                txtAtenue.setText("0.32");
                txtAtenue.setEditable(false);
                txtDisp.setText("0");
                txtDisp.setEditable(false);
            } 
            else { 
                txtAtenue.setText("0.18");
                txtAtenue.setEditable(false);
                txtDisp.setText("18");
                txtDisp.setEditable(false);
            }
        }
    }
    
    /**
     * Metodo que corresponde al tipo Otro de fibra en el cual se puede ingresar
     * cualquier valor en atenuacion y dispersion
     */
    @FXML
    public void rbtnOtro(){
        rbtnOtro.setSelected(true);
        rbtnMulti.setDisable(false);
        rbtnMono.setDisable(false);
        rbtn1310.setDisable(false);
        rbtn1550.setDisable(false);
        txtDisp.setText("");
        txtDisp.setEditable(true);
        txtAtenue.setText("");
        txtAtenue.setEditable(true);
    }
    
    /**
     * Metodo el cual captura los datos obtenidos de la ventana del conector y
     * crea uno
     * @param event Representa cualquier tipo de accion 
     * @throws java.lang.reflect.InvocationTargetException 
     */
    public void enviarDatos(ActionEvent event) throws RuntimeException, InvocationTargetException{
        int modo=0, longitudOnda=0, tipo=0, id = 0;
        double longitudKm, atenue, dispersion;
        
        if (rbtnMulti.isSelected()){
            modo = 1;
            rbtnMulti.setSelected(true);
        }else{
            modo=0;
            rbtnMono.setSelected(true);
        }
        if (rbtn1550.isSelected()){
            longitudOnda = 1550;
            rbtn1550.setSelected(true);
        }else{
            longitudOnda=1310;
            rbtn1310.setSelected(true);
        }
        if (rbtn50.isSelected()){
            tipo = 1;
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtn1550.setDisable(true);
            rbtnMono.setDisable(true);
            rbtn50.setSelected(true);
        }
        if(rbtn28.isSelected()){ 
            tipo = 0;
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtnMulti.setDisable(true);
            rbtn28.setSelected(true);
        }
        if(rbtnOtro.isSelected()){
            tipo=2;
        }
        
        if (txtDistancia.getText().isEmpty() || txtDistancia.getText().compareTo("")==0 || !txtDistancia.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid value");
            alert.showAndWait();
            txtDistancia.setText("");
        }
        else if (txtAtenue.getText().isEmpty() || txtAtenue.getText().compareTo("")==0 || !txtAtenue.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid value");
            alert.showAndWait();
            txtAtenue.setText("");
        }
        else if (txtDisp.getText().isEmpty() || txtDisp.getText().compareTo("")==0 || !txtDisp.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid value");
            alert.showAndWait();
            txtDisp.setText("");
        }
        else{
            longitudKm= Double.parseDouble(txtDistancia.getText());
            atenue= Double.parseDouble(txtAtenue.getText());
            dispersion= Double.parseDouble(txtDisp.getText());
            Fibra f= new Fibra();
            f.setAtenuacion(atenue);
            f.setDispersion(dispersion);
            f.setLongitudOnda(longitudOnda);
            f.setModo(modo);
            f.setLongitud_km(longitudKm);
            f.setTipo(tipo);
            f.setIdFibra(idFibra);
            f.setConectadoEntrada(false);
            f.setConectadoSalida(false);
            guardarComponente(f);
            idFibra++;
            cerrarVentana(event);
        }
    }
    
    /**
     * Metodo que guarda la fibra en el panel
     * @param fibra Fibra con valores almacenados
     */
    public void guardarComponente(Fibra fibra){
        fibra.setNombre("fiber"); 
        fibra.setId(controlador.getContadorElemento());
        controlador.getElementos().add(fibra);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        fibra.setPosX(dibujo.getLayoutX());
        fibra.setPosY(dibujo.getLayoutY());
        setPosX(fibra.getPosX());
        setPosY(fibra.getPosY());
        
        elem.setComponente(fibra);
        elem.setId(controlador.getContadorElemento());
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
        dibujo.setText(fibra.getNombre() + "_"+ fibra.getIdFibra());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\nFiber created!");
        alert.showAndWait();
    }
    
    /**
     * Metodo que duplica una fibra
     * @param fibra Fibra a duplicar
     * @param el Elemento grafico de la fibra a duplicar
     */
    public void duplicarFibra(Fibra fibra,ElementoGrafico el){
        fibra.setNombre("fiber"); 
        fibra.setId(controlador.getContadorElemento());
        controlador.getElementos().add(fibra);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        fibra.setPosX(dibujo.getLayoutX());
        fibra.setPosY(dibujo.getLayoutY());
        setPosX(fibra.getPosX());
        setPosY(fibra.getPosY());
        
        elem.setComponente(fibra);
        elem.setId(controlador.getContadorElemento());
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
        dibujo.setText(fibra.getNombre() + "_"+ fibra.getIdFibra());
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
        alert.setContentText("\nDuplicate fiber!");
        alert.showAndWait();
    }
    
    /**
     * Metodo el cual le proporciona eventos a la fibra tales como movimiento, 
     * abrir ventana para modificarlo o mostrar un menu de acciones
     * @param elem Elemento grafico de la fibra
     */
    public void eventos(ElementoGrafico elem){
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
                        Stage stage1 = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaFibra.fxml"));
                        Parent root = loader.load();
                        //Se crea una instancia del controlador del conector
                        VentanaFibraController fibraController = (VentanaFibraController) loader.getController();
                        fibraController.init(controlador, stage, Pane1,scroll);
                        /*Se necesito usar otro init de forma que el controller sepa cual es el elemento
                            con el que se esta trabajando ademas de que se manda el mismo controller para 
                            iniciar con los valores del elemento mandado.
                        */
                        
                        fibraController.btnCrear.setVisible(false);
                        fibraController.btnDesconectar.setVisible(true);
                        fibraController.lblConectarA.setVisible(true);
                        fibraController.cboxConectarA.setVisible(true);
                        fibraController.btnModificar.setVisible(true);
                        fibraController.separator.setVisible(true);
                        fibraController.init2(controlador, stage, Pane1,elem,fibraController);
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
     * ver propiedades de la fibra
     * @param dibujo Elemento grafico de la fibra
     */
    public void mostrarMenu(ElementoGrafico dibujo){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("-Duplicate");
        MenuItem menuItem3 = new MenuItem("-Delete");
        MenuItem menuItem4 = new MenuItem("-Properties");

        /*Duplicar*/
        menuItem1.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    //System.out.println(dibujo.getId()+"----"+controlador.getElementos().get(elemento).getId());
                    Fibra aux= new Fibra();
                    Fibra aux1= (Fibra)controlador.getElementos().get(elemento);
                    aux.setAtenuacion(aux1.getAtenuacion());
                    aux.setDispersion(aux1.getDispersion());
                    aux.setLongitudOnda(aux1.getLongitudOnda());
                    aux.setLongitud_km(aux1.getLongitud_km());
                    aux.setModo(aux1.getModo());
                    aux.setTipo(aux1.getTipo());
                    aux.setNombre("fiber");
                    aux.setIdFibra(idFibra);
                    aux.setConectadoEntrada(false);
                    aux.setConectadoSalida(false);
                    duplicarFibra(aux,dibujo);
                    idFibra++;
                    for(int h=0; h<controlador.getElementos().size(); h++){
                        System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                        System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
                    }
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
                    Fibra aux= (Fibra)controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux); 
                }
            }   
            dibujo.getDibujo().setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("\nRemoved fiber!");
            alert.showAndWait();
        });
        
        /*Propiedades*/
        menuItem4.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Stage s = new Stage(StageStyle.DECORATED);
                    Image ico = new Image("images/dibujo_fibra.png");
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC Properties");
                    s.initModality(Modality.APPLICATION_MODAL);
                    Fibra aux= (Fibra)controlador.getElementos().get(elemento);
                    Label label;
                    String tip, mod;
                    if(aux.getModo() == 0){
                        mod = "Monomode";
                        switch (aux.getTipo()) {
                            case 0:
                                tip = "smf-28";
                                label = new Label("  Name: "+aux.getNombre()+
                                        "\n  Id: "+aux.getIdFibra()+
                                        "\n  Input: "+aux.getElementoConectadoEntrada()+
                                        "\n  Output :"+aux.getElementoConectadoSalida()+
                                        "\n  Type: "+tip+ "\n  Mode: "+mod+
                                        "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                                        "\n  Fiber lenght: "+aux.getLongitud_km()+" km"+
                                        "\n  Attenuation: "+aux.getAtenuacion() +" dB/km"+
                                        "\n  Dispersion: "+aux.getDispersion()+" ps/(nm*km)");
                                Scene scene = new Scene(label, 190, 165);
                                s.setScene(scene);
                                s.setResizable(false);
                                s.showAndWait();
                                break;
                            case 1:
                                tip = "mm50";
                                label = new Label("  Name: "+aux.getNombre()+
                                        "\n  Id: "+aux.getIdFibra()+
                                        "\n  Input: "+aux.getElementoConectadoEntrada()+
                                        "\n  Output :"+aux.getElementoConectadoSalida()+
                                        "\n  Type: "+tip+ "\n  Mode: "+mod+
                                        "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                                        "\n  Fiber lenght: "+aux.getLongitud_km()+" km"+
                                        "\n  Attenuation: "+aux.getAtenuacion() +" dB/km"+
                                        "\n  Dispersion: "+aux.getDispersion()+" ps/(nm*km)");
                                Scene scene1 = new Scene(label, 190, 165);
                                s.setScene(scene1);
                                s.setResizable(false);
                                s.showAndWait();
                                break;
                            case 2:
                                tip = "Other";
                                label = new Label("  Name: "+aux.getNombre()+
                                        "\n  Id: "+aux.getIdFibra()+
                                        "\n  Input: "+aux.getElementoConectadoEntrada()+
                                        "\n  Output :"+aux.getElementoConectadoSalida()+
                                        "\n  Type: "+tip+ "\n  Mode: "+mod+
                                        "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                                        "\n  Fiber lenght: "+aux.getLongitud_km()+" km"+
                                        "\n  Attenuation: "+aux.getAtenuacion() +" dB/km"+
                                        "\n  Dispersion: "+aux.getDispersion()+" ps/(nm*km)");
                                Scene scene2 = new Scene(label, 190, 165);
                                s.setScene(scene2);
                                s.setResizable(false);
                                s.showAndWait();
                                break;
                            default:
                                break;
                        }
                    }
                    else if(aux.getModo() == 1){
                        mod = "Multimode";
                        switch (aux.getTipo()) {
                            case 0:
                                tip = "smf-28";
                                label = new Label("  Name: "+aux.getNombre()+
                                        "\n  Id: "+aux.getIdFibra()+
                                        "\n  Input: "+aux.getElementoConectadoEntrada()+
                                        "\n  Output :"+aux.getElementoConectadoSalida()+
                                        "\n  Type: "+tip+ "\n  Mode: "+mod+
                                        "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                                        "\n  Fiber lenght: "+aux.getLongitud_km()+" km"+
                                        "\n  Attenuation: "+aux.getAtenuacion() +" dB/km"+
                                        "\n  Dispersion: "+aux.getDispersion()+" ps/(nm*km)");
                                Scene scene3 = new Scene(label, 190, 165);
                                s.setScene(scene3);
                                s.setResizable(false);
                                s.showAndWait();
                                break;
                            case 1:
                                tip = "mm50";
                                label = new Label("  Name: "+aux.getNombre()+
                                        "\n  Id: "+aux.getIdFibra()+
                                        "\n  Input: "+aux.getElementoConectadoEntrada()+
                                        "\n  Output :"+aux.getElementoConectadoSalida()+
                                        "\n  Type: "+tip+ "\n  Mode: "+mod+
                                        "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                                        "\n  Fiber lenght: "+aux.getLongitud_km()+" km"+
                                        "\n  Attenuation: "+aux.getAtenuacion() +" dB/km"+
                                        "\n  Dispersion: "+aux.getDispersion()+" ps/(nm*km)");
                                Scene scene4 = new Scene(label, 190, 165);
                                s.setScene(scene4);
                                s.setResizable(false);
                                s.showAndWait();
                                break;
                            case 2:
                                tip = "Other";
                                label = new Label("  Name: "+aux.getNombre()+
                                        "\n  Id: "+aux.getIdFibra()+
                                        "\n  Input: "+aux.getElementoConectadoEntrada()+
                                        "\n  Output :"+aux.getElementoConectadoSalida()+
                                        "\n  Type: "+tip+ "\n  Mode: "+mod+
                                        "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                                        "\n  Fiber lenght: "+aux.getLongitud_km()+" km"+
                                        "\n  Attenuation: "+aux.getAtenuacion() +" dB/km"+
                                        "\n  Dispersion: "+aux.getDispersion()+" ps/(nm*km)");
                                Scene scene5 = new Scene(label, 190, 165);
                                s.setScene(scene5);
                                s.setResizable(false);
                                s.showAndWait();
                                break;
                            default:
                                break;
                        }
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
     * Metodo para cerrar la ventana de la fibra
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    /**
     * Metodo para desconectar la fibra
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void Desconectar(ActionEvent event){
        for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
            if(fibraControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                Componente comp= controlador.getElementos().get(elemento2);
                comp.setConectadoEntrada(false);
                comp.setElementoConectadoEntrada("");
                System.out.println(comp.getNombre());
                break;
            }
        }
        fibraControl.cboxConectarA.getSelectionModel().select(0);
        if(elemG.getComponente().isConectadoSalida()){
            elemG.getComponente().setConectadoSalida(false);
            elemG.getComponente().setElementoConectadoSalida("");
            elemG.getComponente().getLinea().setVisible(false);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("\nDisconnected fiber!");
            alert.showAndWait();
        cerrarVentana(event);
    }
    
    /**
     * Metodo para modificar la fibra
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void modificar(ActionEvent event){
        Fibra aux = (Fibra) elemG.getComponente();
        int modo=0, longitudOnda=0, tipo=0, id = 0;
        double longitudKm, atenue, dispersion;

        if(rbtnMulti.isSelected()){
            modo = 1;
            rbtnMulti.setSelected(true);
        }
        if (rbtn1550.isSelected()){
            longitudOnda = 1550;
            rbtn1550.setSelected(true);
        }
        if (rbtn1310.isSelected()){
            longitudOnda = 1310;
            rbtn1310.setSelected(true);
        }
        if (rbtn50.isSelected()){
            tipo = 1;
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtn1550.setDisable(true);
            rbtnMono.setDisable(true);
            rbtn50.setSelected(true);
        }
        if(rbtn28.isSelected()){
            tipo = 0;
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtnMulti.setDisable(true);
            rbtn28.setSelected(true);
        }
        if(rbtnOtro.isSelected()){
            tipo=2;
        } 

        if((fibraControl.cboxConectarA.getSelectionModel().getSelectedIndex())==0){
            Desconectar(event);
        }
        else{
            if(aux.isConectadoSalida()){elemG.getComponente().getLinea().setVisible(false);}
            aux.setConectadoSalida(true);
            
            for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
                if(fibraControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
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
        if (txtDistancia.getText().isEmpty() || txtDistancia.getText().isEmpty() || txtDistancia.getText().compareTo("")==0 || !txtDistancia.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid value");
            alert.showAndWait();
            txtDistancia.setText("");
        }
        else if (txtAtenue.getText().isEmpty() || txtAtenue.getText().isEmpty() || txtAtenue.getText().compareTo("")==0 || !txtAtenue.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid value");
            alert.showAndWait();
            txtAtenue.setText("");
        }
        else if (txtDisp.getText().isEmpty() || txtDisp.getText().isEmpty() || txtDisp.getText().compareTo("")==0 || !txtDisp.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid value");
            alert.showAndWait();
            txtDisp.setText("");
        }
        else{
            longitudKm= Double.parseDouble(txtDistancia.getText());
            atenue= Double.parseDouble(txtAtenue.getText());
            dispersion= Double.parseDouble(txtDisp.getText());
            aux.setAtenuacion(atenue);
            aux.setDispersion(dispersion);
            aux.setLongitudOnda(longitudOnda);
            aux.setModo(modo);
            aux.setLongitud_km(longitudKm);
            aux.setTipo(tipo);
            cerrarVentana(event);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("\nModified fiber!");
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
     * @param controlador Controlador del simulador
     * @param stage Escenario en el cual se agregan los objetos creados
     * @param Pane1 Panel para agregar objetos
     * @param elem Elemento grafico
     * @param fibraController Controlador del conector
    */
    public void init2(ControladorGeneral controlador, Stage stage, Pane Pane1,ElementoGrafico elem, VentanaFibraController fibraController) {
        this.elemG=elem;
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.fibraControl=fibraController;
        
        if(elemG.getComponente().isConectadoSalida()==true){
            fibraControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
        }
        else{
            fibraControl.cboxConectarA.getItems().add("Desconected");
            fibraControl.cboxConectarA.getSelectionModel().select(0);
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if("connector".equals(controlador.getElementos().get(elemento).getNombre()) ||
                        "splice".equals(controlador.getElementos().get(elemento).getNombre())){
                    if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                        fibraControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                    }
                }
            }
        }
        
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                Fibra fib= (Fibra)controlador.getElementos().get(elemento);
                System.out.println(fib.getModo()+"\t"+fib.getLongitudOnda());
                
                switch (fib.getTipo()) {
                    case 0:
                        fibraControl.rbtn28.setSelected(true);
                        break;
                    case 1:
                        fibraControl.rbtn50.setSelected(true);
                        break;
                    case 2:
                        fibraControl.rbtnOtro.setSelected(true);
                        break;
                    default:
                        break;
                }
                if(fib.getModo()==0){
                    fibraControl.rbtnMono.setSelected(true);
                }else if(fib.getModo()==1){
                    fibraControl.rbtnMulti.setSelected(true);
                }
                
                if(fib.getLongitudOnda()==1310){
                    fibraControl.rbtn1310.setSelected(true);
                }else if(fib.getLongitudOnda()==1550){
                    fibraControl.rbtn1550.setSelected(true);
                }
                fibraControl.txtAtenue.setText(String.valueOf(fib.getAtenuacion()));
                fibraControl.txtDisp.setText(String.valueOf(fib.getDispersion()));
                fibraControl.txtDistancia.setText(String.valueOf(fib.getLongitud_km()));
            }
        }
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia delante de la fibra
     * con otro elemento
     * @param elemG Elemento grafico de la fibra
     */
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
        line.setStroke(Color.BLACK);
        line.setEndX(aux.getDibujo().getLayoutX());
        line.setEndY(aux.getDibujo().getLayoutY());
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        elemG.getComponente().setLinea(line);
              
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia atras de la fibra
     * con otro elemento
     * @param elem Elemento grafico de la fibra
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
        line.setStartX(aux.getDibujo().getLayoutX()+aux.getDibujo().getWidth());
        line.setStartY(aux.getDibujo().getLayoutY()+10);
        line.setEndX(elem.getDibujo().getLayoutX());
        line.setEndY(elem.getDibujo().getLayoutY()+7);
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
