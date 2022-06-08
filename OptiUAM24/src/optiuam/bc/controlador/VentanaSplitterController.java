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
import optiuam.bc.modelo.PuertoSalida;
import optiuam.bc.modelo.Splitter;

/**
 * Clase VentanaSplitterController la cual se encarga de instanciar un 
 * divisor optico (splitter)
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see ControladorGeneral
 */
public class VentanaSplitterController extends ControladorGeneral implements Initializable {
    
    /**Identificador del divisor optico*/
    static int idS = 0;
    /**Controlador del simulador*/
    ControladorGeneral controlador;
    /**Escenario en el cual se agregaran los objetos creados*/
    Stage stage;
    /**Elemento grafico del divisor optico*/
    ElementoGrafico elemG;
    /**Controlador del divisor optico*/
    VentanaSplitterController splitterControl;
    /**Posicion del divisor optico en el eje X*/
    static double posX;
    /**Posicion del divisor optico en el eje Y*/
    static double posY;
    /**Perdidas validas del divisor optico*/
    private final String perdidasValidas[][] = {{"1,0", "2.7", "4.0"},   //2
                                                {"1,1", "5.3", "7.6"},   //4
                                                {"1,2", "7.9", "10.9"},  //8
                                                {"1,3", "10.5", "14.5"}, //16
                                                {"1,4", "12.8", "18.1"}, //32
                                                {"1,5", "15.5", "21.5"}};//64
    
    /**RadioButton para la longitud de onda de 1310 nm*/
    @FXML
    RadioButton rbtn1310;
    /**RadioButton para la longitud de onda de 1550 nm*/
    @FXML
    RadioButton rbtn1550;
    /**Lista desplegable del numero de salidas que tiene el divisor optico*/
    @FXML
    ComboBox cboxNumeroSalidas;
    /**Lista desplegable de cada salida que tiene el divisor optico*/
    @FXML
    ComboBox cboxSalidas;
    /**Lista desplegable de elementos disponibles para conectar el 
     divisor optico*/
    @FXML
    ComboBox cboxConectarA;
    /**Boton para desconectar el divisor optico*/
    @FXML
    Button btnDesconectar;
    /**Boton para crear un divisor optico*/
    @FXML
    Button btnCrear;
    /**Boton para modificar el divisor optico*/
    @FXML
    Button btnModificar;
    /**Caja de texto para ingresar la perdida de insercion del divisor 
     optico*/
    @FXML
    TextField txtPerdidaInsercion;
    /**Etiqueta de la lista desplegable de cada salida del divisor optico*/
    @FXML
    Label lblSalida;
    /**Etiqueta de la lista desplegable de elementos disponibles para conectar
     el divisor optico*/
    @FXML
    Label lblConectarA;
    /**Separador de la ventana del divisor optico*/
    @FXML
    Separator separator;
    /**Panel para agregar objetos*/
    @FXML
    private Pane Pane1;
    /**Espacio en el cual el usuario puede desplazarse*/
    @FXML
    private ScrollPane scroll;
    
    /**
     * Metodo que muestra el identificador del divisor optico
     * @return idS
     */
    public static int getIdS() {
        return idS;
    }

    /**
     * Metodo que modifica el identificador del divisor optico
     * @param idS Identificador del divisor optico
     */
    public static void setIdS(int idS) {
        VentanaSplitterController.idS = idS;
    }
    
    /**
     * Metodo que muestra la posicion del divisor optico en el eje X
     * @return posX
     */
    public static double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion del divisor optico en el eje X
     * @param posX Posicion en el eje X
     */
    public static void setPosX(double posX) {
        VentanaSplitterController.posX = posX;
    }

    /**
     * Metodo que muestra la posicion del divisor optico en el eje Y
     * @return posY
     */
    public static double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion del divisor optico en el eje Y
     * @param posY Posicion en el eje Y
     */
    public static void setPosY(double posY) {
        VentanaSplitterController.posY = posY;
    }
    
    /**
     * Metodo el cual inicializa la ventana del divisor optico
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cboxNumeroSalidas.getItems().removeAll(cboxNumeroSalidas.getItems());
        cboxNumeroSalidas.getItems().addAll("2", "4", "8", "16", "32", "64");
        cboxNumeroSalidas.getSelectionModel().select("2");
        Tooltip perdidaI = new Tooltip();
        perdidaI.setText("2: The loss must be min: 2.7  max: 4.0"
                + "\n4: The loss must be min: 5.3  max: 7.6"
                + "\n8: The loss must be min: 7.9  max: 10.9"
                + "\n16: The loss must be min: 10.5  max: 14.5"
                + "\n32: The loss must be min: 12.8  max: 18.1"
                + "\n64: The loss must be min: 15.5  max: 21.5");
        txtPerdidaInsercion.setTooltip(perdidaI);
        
        separator.setVisible(false);
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        lblSalida.setVisible(false);
        cboxSalidas.setVisible(false);
        btnModificar.setVisible(false);
    }
    
    /**
     * Metodo que valida las perdidas del divisor optico de acuerdo al numero de 
     * salidas que tiene
     * @param perdida Perdida de insercion del divisor optico
     * @param salidas Numero de salidas del divisor optico
     * @return Perdida de insercion validada
     */
    public boolean validarPerdida(double perdida,int salidas) {
        for (String[] perdidasValida : perdidasValidas) {
            if (perdidasValida[0].compareTo(String.valueOf("1") + "," + String.valueOf(salidas)) == 0) {
                return perdida >= Double.parseDouble(perdidasValida[1]) && perdida <= Double.parseDouble(perdidasValida[2]);
            }
        }
        return false;
    }
    
     /**
     * Metodo que busca las perdidas del divisor optico de acuerdo al numero de 
     * salidas que tiene
     * @param salidas Numero de salidas del divisor optico
     * @return Perdidas
     */
    public String buscarPerdidas(int salidas) {
        for (String[] perdidasValida : perdidasValidas) {
            if (perdidasValida[0].compareTo(String.valueOf("1") + "," + String.valueOf(salidas)) == 0) {
                return "min: " + String.valueOf(perdidasValida[1]) + ", max: " + String.valueOf(perdidasValida[2]);
            }
        }
        return "";
    }
    
    /**
     * Metodo el cual captura los datos obtenidos de la ventana del divisor 
     * optico y crea uno
     * @param event Representa cualquier tipo de accion 
     * @throws java.lang.reflect.InvocationTargetException 
     */
    public void enviarDatos(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException{
        int salidas=0, longitudOnda=0, id=0;
        double perdida;
        
        if(rbtn1550.isSelected()){
            longitudOnda = 1550;
            rbtn1550.setSelected(true);
        }
        else if(rbtn1310.isSelected()){
            longitudOnda = 1310;
            rbtn1310.setSelected(true);
        }
        if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("2")){
            salidas = 2;
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("4")){
            salidas = 4;
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("8")){
            salidas = 8;
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("16")){
            salidas = 16;
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("32")){
            salidas = 32;
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("64")){
            salidas = 64;
        }
        
        cboxSalidas.getItems().removeAll(cboxSalidas.getItems());
        for(int i = 0; i<salidas;i++){
            cboxSalidas.getItems().addAll(String.valueOf(i+1));
        }
        if (txtPerdidaInsercion.getText().isEmpty() || txtPerdidaInsercion.getText().compareTo("")==0 || !txtPerdidaInsercion.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid loss value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid loss value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdidaInsercion.setText("");
        }
        else if (!validarPerdida(Double.parseDouble(txtPerdidaInsercion.getText()),cboxNumeroSalidas.getSelectionModel().getSelectedIndex())) {
           System.out.println("The loss must be " + buscarPerdidas(cboxNumeroSalidas.getSelectionModel().getSelectedIndex()));
           ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe loss must be " +  buscarPerdidas(cboxNumeroSalidas.getSelectionModel().getSelectedIndex()),
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else{
            perdida = Double.parseDouble(txtPerdidaInsercion.getText());
            txtPerdidaInsercion.setText(String.valueOf(perdida));
            Splitter s= new Splitter();
            s.setConectadoEntrada(false);
            s.setConectadoSalida(false);
            s.setPerdidaInsercion(perdida);
            s.setSalidas(salidas);
            s.setLongitudOnda(longitudOnda);
            s.setNombre("splitter");
            s.setIdS(idS);
            s.modificarSalidas(salidas);
            idS++;
            guardarSplitter(s);
            cerrarVentana(event);
        }
    }
    
    /**
     * Metodo que guarda el divisor optico en el panel
     * @param s Divisor optico con valores almacenados
     */
    public void guardarSplitter(Splitter s) {
        s.setId(controlador.getContadorElemento());
        controlador.getElementos().add(s);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        elem.setComponente(s);
        elem.setId(controlador.getContadorElemento());
        
        if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("2")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter2.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("4")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter4.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("8")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter8.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("16")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter16.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("32")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter32.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("64")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter64.png")));
        }
        
        dibujo.setText(s.getNombre() + "_"+ s.getIdS());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nSplitter created!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Metodo que duplica un divisor optico
     * @param s Divisor optico a duplicar
     * @param el Elemento grafico del divisor optico a duplicar
     */
    public void duplicarSplitter(Splitter s, ElementoGrafico el) {
        s.setId(controlador.getContadorElemento());
        s.setNombre("splitter");
        controlador.getElementos().add(s);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        elem.setComponente(s);
        elem.setId(controlador.getContadorElemento());
        
        if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("2")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter2.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("4")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter4.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("8")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter8.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("16")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter16.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("32")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter32.png")));
        }
        else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("64")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter64.png")));
        }
        
        dibujo.setText(s.getNombre() + "_"+ s.getIdS());
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
                "\nDuplicate splitter!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    
    /**
     * Metodo el cual le proporciona eventos al divisor optico tales como movimiento, 
     * abrir ventana para modificarlo o mostrar un menu de acciones
     * @param elem Elemento grafico del divisor optico
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
                    
                        borrarLineaSplitter(elem);
                        dibujarLinea(elem);
                    
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
            elem.getDibujo().setOnMouseClicked((MouseEvent event) -> {
                if(event.getButton()==MouseButton.PRIMARY){
                    try{
                        Stage stage1 = new Stage(StageStyle.UTILITY);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaSplitter.fxml"));
                        Parent root = loader.load();
                        
                        //Se crea una instancia del controlador del divisor optico
                        VentanaSplitterController splitterController = (VentanaSplitterController) loader.getController();
                        splitterController.init(controlador, stage, Pane1, scroll);
                        //splitterController.init(controlador, this.stage, this.Pane1);
                        /*Se necesito usar otro init de forma que el controller sepa cual es el elemento
                            con el que se esta trabajando ademas de que se manda el mismo controller para 
                            iniciar con los valores del elemento mandado.
                        */
                        splitterController.init2(elem,splitterController);
                        Splitter spli= (Splitter) elem.getComponente();
                        splitterController.btnCrear.setVisible(false);
                        splitterController.separator.setVisible(true);
                        splitterController.lblSalida.setVisible(true);
                        splitterController.cboxSalidas.setVisible(true);
                        splitterController.btnDesconectar.setVisible(true);
                        splitterController.lblConectarA.setVisible(true);
                        splitterController.cboxConectarA.setVisible(true);
                        splitterController.btnModificar.setVisible(true);
                        
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
     * ver propiedades del divisor optico
     * @param dibujo Elemento grafico del divisor optico
     */
    public void mostrarMenu(ElementoGrafico dibujo){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("-Duplicated");
        MenuItem menuItem3 = new MenuItem("-Deleted");
        MenuItem menuItem4 = new MenuItem("-Properties");

        /*Duplicar*/
        menuItem1.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    //System.out.println(dibujo.getId()+"----"+controlador.getElementos().get(elemento).getId());
                    Splitter aux=new Splitter();
                    Splitter aux1=(Splitter)controlador.getElementos().get(elemento);
                    aux.setConectadoEntrada(false);
                    aux.setConectadoSalida(false);
                    aux.setElementoConectadoEntrada("");
                    aux.setElementoConectadoSalida("");
                    aux.setLongitudOnda(aux1.getLongitudOnda());
                    aux.setNombre(aux1.getNombre());
                    aux.setPerdidaInsercion(aux1.getPerdidaInsercion());
                    aux.setSalidas(aux1.getSalidas());
                    aux.setIdS(idS);
                    //LinkedList conex= new LinkedList();
                    for(int cz=0; cz<aux1.getSalidas();cz++){
                        PuertoSalida p=new PuertoSalida();
                        aux.getConexiones().add(p);        
                    }
                    
                    duplicarSplitter(aux,dibujo);
                    //System.out.println(aux);
                    idS++;
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
                        //System.out.println();
                        aux.setConectadoEntrada(false);
                        aux.setElementoConectadoEntrada("-");
                        dibujo.getComponente().getLinea().setVisible(false);
                    }
                }   
            }
            if(dibujo.getComponente().isConectadoEntrada()==true){
                for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                    if(dibujo.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(elemento).getDibujo().getText())){
                        Componente aux= controlador.getElementos().get(elemento);
                        aux.setConectadoSalida(false);
                        aux.setElementoConectadoSalida("-");
                        aux.getLinea().setVisible(false);
                    }
                }
            }
            Splitter sp=(Splitter)dibujo.getComponente();
            for(int cz=0; cz<sp.getConexiones().size(); cz++){
                if(sp.getConexiones().get(cz).isConectadoSalida()){
                    for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                        if(sp.getConexiones().get(cz).getElementoConectadoSalida().equals(controlador.getDibujos().get(elemento).getDibujo().getText())){
                            Componente aux= controlador.getElementos().get(elemento);
                            //System.out.println();
                            aux.setConectadoEntrada(false);
                            aux.setElementoConectadoEntrada("-");
                            sp.getConexiones().get(cz).getLinea().setVisible(false);
                        }
                    }
                }
                
            }
            
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Splitter aux= (Splitter)controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux); 
                }
            }    
            dibujo.getDibujo().setVisible(false);
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nRemoved splitter!",
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
                    Image ico = new Image("images/ico_splitter.png");
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC Properties");
                    s.initModality(Modality.APPLICATION_MODAL);
                    Splitter aux= (Splitter)controlador.getElementos().get(elemento);
                    Label label;
                    label = new Label("  Name: "+aux.getNombre()+
                        "\n  Id: "+aux.getIdS()+
                        "\n  Input: "+aux.getElementoConectadoEntrada()+
                        "\n  Output :"+aux.getElementoConectadoSalida()+
                        "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                        "\n  Outputs: "+aux.getSalidas()+
                        "\n  Insertion Loss: "+aux.getPerdidaInsercion()+" dB");
                    Scene scene = new Scene(label, 190, 130);
                    s.setScene(scene);
                    s.setResizable(false);
                    s.showAndWait();
                }
            }
        });

        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem3);
        contextMenu.getItems().add(menuItem4);
        dibujo.getDibujo().setContextMenu(contextMenu);
    }
    
    /**
     * Metodo para cerrar la ventana del divisor optico
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    /**
     * Metodo para desconectar el divisor optico
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void Desconectar(ActionEvent event){
        for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
            if(splitterControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                Componente comp= controlador.getElementos().get(elemento2);
                comp.setConectadoEntrada(false);
                comp.setElementoConectadoEntrada("");
                //System.out.println(comp.getNombre());
                break;
            }
        }
        splitterControl.cboxConectarA.getSelectionModel().select(0);
        if(splitterControl.cboxSalidas.getSelectionModel().getSelectedItem().equals("1")){
            if(elemG.getComponente().isConectadoSalida()){
                elemG.getComponente().setConectadoSalida(false);
                elemG.getComponente().setElementoConectadoSalida("-");
                elemG.getComponente().getLinea().setVisible(false);
            }
        }else{
            int salida= Integer.parseInt(splitterControl.cboxSalidas.getSelectionModel().getSelectedItem().toString())-2;
            Splitter aux=(Splitter) elemG.getComponente();
            if(aux.getConexiones().get(salida).isConectadoSalida()){
                aux.getConexiones().get(salida).setConectadoSalida(false);
                aux.getConexiones().get(salida).setElementoConectadoSalida("-");
                aux.getConexiones().get(salida).getLinea().setVisible(false);
            }
            
        }
        ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "\nDisconnected splitter!",
                aceptar);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.showAndWait();
        cerrarVentana(event);
    }
    
    /**
     * Metodo para modificar el divisor optico
     * @param event Representa cualquier tipo de accion
     * @throws java.lang.reflect.InvocationTargetException
     */
    @FXML
    public void modificar(ActionEvent event) throws RuntimeException, InvocationTargetException, NumberFormatException{
        Splitter aux = (Splitter) elemG.getComponente();
        int salidas=0, longitudOnda=0, id=0;
        double perdida;
        
        if(rbtn1550.isSelected()){
            longitudOnda = 1550;
            rbtn1550.setSelected(true);
        }
        else if(rbtn1310.isSelected()){
            longitudOnda = 1310;
            rbtn1310.setSelected(true);
        }
        
        if((splitterControl.cboxConectarA.getSelectionModel().getSelectedIndex())==0){
            Desconectar(event);
        }
        else{
            conectar();
        }
        
        for(int i = 0; i<salidas;i++){
            cboxSalidas.getItems().addAll(String.valueOf(i+1));
            cboxSalidas.getSelectionModel().selectFirst();
        }
        
        if (txtPerdidaInsercion.getText().isEmpty() || txtPerdidaInsercion.getText().compareTo("")==0 || !txtPerdidaInsercion.getText().matches("[0-9]*?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid loss value");
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nInvalid loss value",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdidaInsercion.setText("");
        }
        else if (!validarPerdida(Double.parseDouble(txtPerdidaInsercion.getText()),cboxNumeroSalidas.getSelectionModel().getSelectedIndex())) {
           System.out.println("The loss must be " + buscarPerdidas(cboxNumeroSalidas.getSelectionModel().getSelectedIndex()));
           ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe loss must be " +  buscarPerdidas(cboxNumeroSalidas.getSelectionModel().getSelectedIndex()),
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
            txtPerdidaInsercion.setText("");
        }
        else{
            if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("2")){
                salidas = 2;
                elemG.getDibujo().setGraphic(new ImageView(new Image("images/dibujo_splitter2.png")));
            }
            else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("4")){
                salidas = 4;
                elemG.getDibujo().setGraphic(new ImageView(new Image("images/dibujo_splitter4.png")));
            }
            else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("8")){
                salidas = 8;
                elemG.getDibujo().setGraphic(new ImageView(new Image("images/dibujo_splitter8.png")));
            }
            else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("16")){
                salidas = 16;
                elemG.getDibujo().setGraphic(new ImageView(new Image("images/dibujo_splitter16.png")));
            }
            else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("32")){
                salidas = 32;
                elemG.getDibujo().setGraphic(new ImageView(new Image("images/dibujo_splitter32.png")));
            }
            else if(cboxNumeroSalidas.getSelectionModel().getSelectedItem().equals("64")){
                salidas = 64;
                elemG.getDibujo().setGraphic(new ImageView(new Image("images/dibujo_splitter64.png")));
            }
            perdida = Double.parseDouble(txtPerdidaInsercion.getText());
            txtPerdidaInsercion.setText(String.valueOf(perdida));
            aux.setPerdidaInsercion(perdida);
            aux.setSalidas(salidas);
            aux.setLongitudOnda(longitudOnda);
            aux.setNombre("splitter");
            cerrarVentana(event);

            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "\nModified splitter!",
                    aceptar);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.showAndWait();
            
        }
    }
    
    /**
     * Metodo que indica si una salida del divisor optico esta conectado a un componente
     * @param salida Salida n del divisor optico
     * @param componente Componente a conectar
     * @return Divisor optico conectado
     */
    public boolean conectarSplitter(int salida,String componente){
        Splitter splitter = (Splitter) elemG.getComponente();
        
        if (salida==0) { 
            if(splitter.isConectadoSalida()==false){
                splitter.setConectadoSalida(true);
                splitter.setElementoConectadoSalida(componente);
                Line line= new Line();
                line.setStartX(elemG.getDibujo().getLayoutX()+elemG.getDibujo().getWidth());
                line.setStartY(elemG.getDibujo().getLayoutY()+10);
                ElementoGrafico aux= new ElementoGrafico();
                for(int it=0; it<controlador.getDibujos().size();it++){
                    if(elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                        aux=controlador.getDibujos().get(it);
                        aux.getComponente().setConectadoEntrada(true);
                        aux.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                        line.setStrokeWidth(2);
                        line.setStroke(Color.BLACK);
                        line.setEndX(aux.getDibujo().getLayoutX()+3);
                        line.setEndY(aux.getDibujo().getLayoutY()+20);
                        splitter.setLinea(line);
                        line.setVisible(true);
                        Pane1.getChildren().add(line);
                        return true;
                    }
                }
            }
            else{
                System.out.println("ya esta conectado");
            }
        }
        else if(salida>0&&salida<=splitter.getSalidas()){
            if(splitter.getConexiones().get(salida-1).isConectadoSalida()==false){
                splitter.getConexiones().get(salida-1).setConectadoSalida(true);
                splitter.getConexiones().get(salida-1).setElementoConectadoSalida(componente);
                Line line= new Line();
                line.setStartX(elemG.getDibujo().getLayoutX()+elemG.getDibujo().getWidth());
                line.setStartY(elemG.getDibujo().getLayoutY()+(10+(10*(salida))));
                ElementoGrafico aux= new ElementoGrafico();
                
                for(int it=0; it<controlador.getDibujos().size();it++){
                    if(componente.equals(controlador.getDibujos().get(it).getDibujo().getText())){
                        aux=controlador.getDibujos().get(it);
                        aux.getComponente().setConectadoEntrada(true);
                    aux.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                    line.setStrokeWidth(2);
                    line.setStroke(Color.BLACK);
                    line.setEndX(aux.getDibujo().getLayoutX()+3);
                    line.setEndY(aux.getDibujo().getLayoutY()+20);
                    splitter.getConexiones().get(salida-1).setLinea(line);
                    splitter.actuaizarSalidas(salida);
                    line.setVisible(true);
                    Pane1.getChildren().add(line);
                    return true;
                    }
                }
            }
            else{
                System.out.println("ya esta conectado");
            }
        }
        return false;
    }
    
    /**
     * Metodo que conecta la salida n del divisor optico a un componente
     */
    public void conectar(){
        int salida = cboxSalidas.getSelectionModel().getSelectedIndex();
        
        String componente = cboxConectarA.getSelectionModel().getSelectedItem().toString();
        if(cboxConectarA.getItems().size() <= 1){
            ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "\nThe splitter cannot be connected to anything",
                    aceptar);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else{
            if(conectarSplitter(salida,componente)){
                ButtonType aceptar = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "\nSplitter output "+(salida+1)+ " was connected to component: "+componente,
                        aceptar);
                alert.setTitle("Succes");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }
    
    /**
     * Metodo que muestra la conexion de la salida n del divisor optico a un componente 
     */
    @FXML
    public void actCbox(){
        if(!splitterControl.btnCrear.isVisible()){
            splitterControl.cboxConectarA.getItems().clear();
            if(splitterControl.cboxSalidas.getSelectionModel().getSelectedIndex()==0){
                if(elemG.getComponente().isConectadoSalida()==true){
                    splitterControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
                }
                else{

                    splitterControl.cboxConectarA.getItems().add("Desconected");
                    splitterControl.cboxConectarA.getSelectionModel().select(0);
                    for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                        if("connector".equals(controlador.getElementos().get(elemento).getNombre()) ||
                            "power".equals(controlador.getElementos().get(elemento).getNombre())){
                            if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                                splitterControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                            }
                        }
                    }
                }
            }
            else{
                Splitter aux=(Splitter)elemG.getComponente();
                if(aux.getConexiones().get(splitterControl.cboxSalidas.getSelectionModel().getSelectedIndex()-1).isConectadoSalida()==true){
                    splitterControl.cboxConectarA.getSelectionModel().select(aux.getConexiones().get(splitterControl.cboxSalidas.getSelectionModel().getSelectedIndex()-1).getElementoConectadoSalida());
                }
                else{
                    splitterControl.cboxConectarA.getItems().add("Desconected");
                    splitterControl.cboxConectarA.getSelectionModel().select(0);
                    for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                        if("connector".equals(controlador.getElementos().get(elemento).getNombre()) ||
                            "power".equals(controlador.getElementos().get(elemento).getNombre())){
                            if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                                splitterControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                            }
                        }
                    }
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
     * @param splitterController Controlador del divisor optico
     */
    public void init2(ElementoGrafico elem, VentanaSplitterController splitterController) {
        this.elemG=elem;
        this.splitterControl=splitterController;
        
        if(elemG.getComponente().isConectadoSalida()==true){
            splitterControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
        }
        else{
            splitterControl.cboxConectarA.getItems().add("Desconected");
            splitterControl.cboxConectarA.getSelectionModel().select(0);
             for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if("connector".equals(controlador.getElementos().get(elemento).getNombre()) ||
                    "power".equals(controlador.getElementos().get(elemento).getNombre())){
                     if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                        splitterControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                    }
                }
             }
        }
        
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                Splitter spl= (Splitter)controlador.getElementos().get(elemento);
                
                cboxSalidas.getItems().removeAll(cboxSalidas.getItems());
                for(int i = 0; i<spl.getSalidas();i++){
                    cboxSalidas.getItems().addAll(String.valueOf(i+1));
                    cboxSalidas.getSelectionModel().selectFirst();
                }
                if(spl.getLongitudOnda()==1310){
                    splitterControl.rbtn1310.setSelected(true);
                }else if(spl.getLongitudOnda()==1550){
                    splitterControl.rbtn1550.setSelected(true);
                }
                splitterControl.txtPerdidaInsercion.setText(String.valueOf(spl.getPerdidaInsercion()));
                int salix=0;
                switch (spl.getSalidas()) {
                    case 2:
                        salix=0;
                        break;
                    case 4:
                        salix=1;
                        break;
                    case 8:
                        salix=2;
                        break;
                    case 16:
                        salix=3;
                        break;
                    case 32:
                        salix=4;
                        break;
                    case 64:
                        salix=5;
                        break;
                    default:
                        break;
                }
                splitterControl.cboxNumeroSalidas.getSelectionModel().select(salix);
            }
        }
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia delante del divisor optico 
     * con otro elemento
     * @param elemG Elemento grafico del divisor optico
     */
    public void dibujarLinea(ElementoGrafico elemG) {
        Splitter splitter=(Splitter)elemG.getComponente();
        if(splitter.isConectadoSalida()){
            Line line= new Line();   
            switch (splitter.getSalidas()) {
                case 2:
                    line.setStartX(elemG.getDibujo().getLayoutX()+50);
                    line.setStartY(elemG.getDibujo().getLayoutY()+10);
                    break;
                case 4:
                    line.setStartX(elemG.getDibujo().getLayoutX()+50);
                    line.setStartY(elemG.getDibujo().getLayoutY()+10);
                    break;
                case 8:
                    line.setStartX(elemG.getDibujo().getLayoutX()+80);
                    line.setStartY(elemG.getDibujo().getLayoutY()+10);
                    break;
                case 16:
                    line.setStartX(elemG.getDibujo().getLayoutX()+94);
                    line.setStartY(elemG.getDibujo().getLayoutY()+10);
                    break;
                case 32:
                    line.setStartX(elemG.getDibujo().getLayoutX()+110);
                    line.setStartY(elemG.getDibujo().getLayoutY()+10);
                    break;
                case 64:
                    line.setStartX(elemG.getDibujo().getLayoutX()+120);
                    line.setStartY(elemG.getDibujo().getLayoutY()+10);
                    break;
                default:
                    break;
            }

            ElementoGrafico aux= new ElementoGrafico();
            for(int it=0; it<controlador.getDibujos().size();it++){
                if(elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                    aux=controlador.getDibujos().get(it);
                    line.setStrokeWidth(2);
                    line.setStroke(Color.BLACK);
                    line.setEndX(aux.getDibujo().getLayoutX());
                    line.setEndY(aux.getDibujo().getLayoutY()+20);
                    line.setVisible(true);
                    Pane1.getChildren().add(line); 
                    elemG.getComponente().setLinea(line);  
                }
            }
        }
        for(int lap=0; lap<splitter.getSalidas()-1;lap++){
            if(splitter.getConexiones().get(lap).isConectadoSalida()){
                Line line= new Line();

                switch (splitter.getSalidas()) {
                    case 2:
                        line.setStartX(elemG.getDibujo().getLayoutX()+50);
                        line.setStartY(elemG.getDibujo().getLayoutY()+(10+(10*(lap+1))));
                        break;
                    case 4:
                        line.setStartX(elemG.getDibujo().getLayoutX()+50);
                        line.setStartY(elemG.getDibujo().getLayoutY()+(10+(10*(lap+1))));
                        break;
                    case 8:
                        line.setStartX(elemG.getDibujo().getLayoutX()+80);
                        line.setStartY(elemG.getDibujo().getLayoutY()+(10+(10*(lap+1))));
                        break;
                    case 16:
                        line.setStartX(elemG.getDibujo().getLayoutX()+94);
                        line.setStartY(elemG.getDibujo().getLayoutY()+(10+(5.1*(lap+1))));
                        break;
                    case 32:
                        line.setStartX(elemG.getDibujo().getLayoutX()+110);
                        line.setStartY(elemG.getDibujo().getLayoutY()+(10+(3.2*(lap+1))));
                        break;
                    case 64:
                        line.setStartX(elemG.getDibujo().getLayoutX()+120);
                        line.setStartY(elemG.getDibujo().getLayoutY()+(10+(2*(lap+1))));
                        break;
                    default:
                        break;
                }
                ElementoGrafico aux= new ElementoGrafico();
                //System.out.println("primer");
                for(int ir=0; ir<controlador.getDibujos().size();ir++){
                    if(splitter.getConexiones().get(lap).getElementoConectadoSalida().equals(controlador.getDibujos().get(ir).getDibujo().getText())){
                        aux=controlador.getDibujos().get(ir);
                        aux.getComponente().setConectadoEntrada(true);
                        aux.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                    //System.out.println("cd");
                    line.setStrokeWidth(2);
                    line.setStroke(Color.BLACK);
                    line.setEndX(aux.getDibujo().getLayoutX()+3);
                    line.setEndY(aux.getDibujo().getLayoutY()+20);
                    //splitter.actuaizarSalidas(salida);
                    line.setVisible(true);
                    Pane1.getChildren().add(line);
                    splitter.getConexiones().get(lap).setLinea(line);
                    }

                }
            }
        }
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia atras del divisor optico  
     * con otro elemento
     * @param elem Elemento grafico del divisor optico
     */
    public void dibujarLineaAtras(ElementoGrafico elem) {
        Line line= new Line();   
        Splitter splitter= (Splitter) elem.getComponente();
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
        switch (splitter.getSalidas()) {
            case 2:
            case 4:
                line.setEndX(elem.getDibujo().getLayoutX());
                line.setEndY(elem.getDibujo().getLayoutY()+26);
                break;
            case 8:
                line.setEndX(elem.getDibujo().getLayoutX());
                line.setEndY(elem.getDibujo().getLayoutY()+41);
                break;
            case 16:
                line.setEndX(elem.getDibujo().getLayoutX());
                line.setEndY(elem.getDibujo().getLayoutY()+50);
                break;
            case 32:
                line.setEndX(elem.getDibujo().getLayoutX());
                line.setEndY(elem.getDibujo().getLayoutY()+60);
                break;
            case 64:
                line.setEndX(elem.getDibujo().getLayoutX());
                line.setEndY(elem.getDibujo().getLayoutY()+70);
                break;
            default:
                break;
        }
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        aux.getComponente().setLinea(line);
    }
   
    /**
     * Metodo que elimina la conexion del divisor optico
     * @param elem Elemento grafico del divisor optico
     */
    public void borrarLineaSplitter(ElementoGrafico elem){
        Splitter splitter=(Splitter)elem.getComponente();
            if(splitter.isConectadoSalida()){
                splitter.getLinea().setVisible(false);
            }
            for(int po=0; po<splitter.getSalidas()-1;po++){
                if(splitter.getConexiones().get(po).isConectadoSalida()){
                    splitter.getConexiones().get(po).getLinea().setVisible(false);
                }
            }
    }
    
    /**
     * Metodo que elimina la conexion
     * @param linea Conexion (linea)
     */
    public void borrarLinea(Line linea){
        linea.setVisible(false);
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
