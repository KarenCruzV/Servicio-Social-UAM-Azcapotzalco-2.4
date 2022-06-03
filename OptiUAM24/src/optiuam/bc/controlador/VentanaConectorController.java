
package optiuam.bc.controlador;

import java.io.IOException;
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
import javafx.scene.control.*;
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
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;


/**
 * Clase VentanaConectorController la cual se encarga de instanciar un conector
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 * @see ControladorGeneral
 */
public class VentanaConectorController extends ControladorGeneral implements Initializable {
    
    /**Identificador del conector*/
    static int idConector = 0;
    /**Controlador del simulador*/
    ControladorGeneral controlador;
    /**Escenario en el cual se agregaran los objetos creados*/
    Stage stage;
    /**Elemento grafico del conector*/
    ElementoGrafico elemG;
    /**Controlador del conector*/
    VentanaConectorController conectorControl;
    /**Posicion del conector en el eje X*/
    static double posX;
    /**Posicion del conector en el eje Y*/
    static double posY;
    /**Panel para agregar objetos*/
    @FXML
    private Pane Pane1;
    /**Etiqueta de la lista desplegable de elementos disponibles para conectar
     el conector*/
    @FXML
    Label lblConectarA;
    /**RadioButton para la longitud de onda de 1310 nm*/
    @FXML
    RadioButton rbtn1310;
    /**RadioButton para la longitud de onda de 510 nm*/
    @FXML
    RadioButton rbtn1550;
    /**RadioButton para el modo Monomodo del conector*/
    @FXML
    RadioButton rbtnMono;
    /**RadioButton para el modo Multimodo del conector*/
    @FXML
    RadioButton rbtnMulti;
    /**Caja de texto para ingresar la perdida de insercion del conector*/
    @FXML
    TextField txtPerdida;
    /**Boton para desconectar el conector*/
    @FXML
    Button btnDesconectar;
    /**Boton para cancelar la operacion*/
    @FXML
    Button btnCancelar;
    /**Boton para crear un conector*/
    @FXML
    Button btnCrear;
    /**Boton para modificar el conector*/
    @FXML
    Button btnModificar;
    /**Lista desplegable de elementos disponibles para conectar el conector*/
    @FXML
    ComboBox cboxConectarA;
    /**Espacio en el cual el usuario puede desplazarse*/
    @FXML
    private ScrollPane scroll;

    /**
     * Metodo que muestra el identificador del conector
     * @return idConector
     */
    public static int getIdConector() {
        return idConector;
    }

    /**
     * Metodo que modifica el identificador del conector
     * @param idConector Identificador del conector
     */
    public static void setIdConector(int idConector) {
        VentanaConectorController.idConector = idConector;
    }

    /**
     * Metodo que muestra la posicion del conector en el eje X
     * @return posX
     */
    public static double getPosX() {
        return posX;
    }

    /**
     * Metodo que modifica la posicion del conector en el eje X
     * @param posX Posicion en el eje X
     */
    public static void setPosX(double posX) {
        VentanaConectorController.posX = posX;
    }

    /**
     * Metodo que muestra la posicion del conector en el eje Y
     * @return posY
     */
    public static double getPosY() {
        return posY;
    }

    /**
     * Metodo que modifica la posicion del conector en el eje X
     * @param posY Posicion en el eje Y
     */
    public static void setPosY(double posY) {
        VentanaConectorController.posY = posY;
    }
    
    /**
     * Metodo el cual inicializa la ventana del conector
     * @param url La ubicacion utilizada para resolver rutas relativas para 
     * el objeto raiz, o nula si no se conoce la ubicacion
     * @param rb Los recursos utilizados para localizar el objeto raiz, o nulo 
     * si el objeto raiz no se localizo.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip perdida = new Tooltip();
        perdida.setText("Monomode: The loss must be" + " min: 0" + " max: 0.5"
                + "\nMultimode: The loss must be" + " min: 0" + " max: 1.0");
        txtPerdida.setTooltip(perdida);
        
        btnCrear.setVisible(true);
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnModificar.setVisible(false);
        
    } 

    /**
     * Metodo el cual captura los datos obtenidos de la ventana del conector y
     * crea uno
     * @param event Representa cualquier tipo de accion 
     */
    public void enviarDatos(ActionEvent event){
        int modo=0, longitudOnda=0, id = 0;
        double perdidaInsercion, perdidaMax =0.5;
        
        if(rbtnMono.isSelected()){
            modo=0;
        }else if(rbtnMulti.isSelected()){
            perdidaMax=1.0;
            modo=1;
        }   
        if(rbtn1310.isSelected()){
            longitudOnda=1310;
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
        }
        perdidaInsercion= Double.parseDouble(txtPerdida.getText());
        if (txtPerdida.getText().compareTo("")==0 || !txtPerdida.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid loss value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid loss value");
            alert.showAndWait();
        }
        else if(Double.parseDouble(txtPerdida.getText()) > perdidaMax || Double.parseDouble(txtPerdida.getText()) < 0){
            System.out.println("\nThe loss must be" + " min: 0" + " max: " + perdidaMax);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nThe loss must be" + " min: 0" + " max: " + perdidaMax);
            alert.showAndWait();
        }
        else{
            Conector con= new Conector();
            con.setConectadoEntrada(false);
            con.setConectadoSalida(false);
            con.setIdConector(idConector);
            con.setLongitudOnda(longitudOnda);
            con.setNombre("connector");
            con.setPerdidaInsercion(perdidaInsercion);
            con.setModo(modo);
            guardarConector(con);
            idConector++;
            cerrarVentana(event);
        }
    }
    
    /**
     * Metodo que guarda el conector en el panel
     * @param conector Conector con valores almacenados
     */
    public void guardarConector(Conector conector) {
        conector.setId(controlador.getContadorElemento());
        controlador.getElementos().add(conector);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        conector.setPosX(dibujo.getLayoutX());
        conector.setPosY(dibujo.getLayoutY());
        setPosX(conector.getPosX());
        setPosY(conector.getPosY());
        
        elem.setComponente(conector);
        elem.setId(controlador.getContadorElemento());
        
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_conectorR.png")));
        dibujo.setText(conector.getNombre() + "_"+ conector.getIdConector());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\nConnector created!");
        alert.showAndWait();
    }
    
    /**
     * Metodo que duplica un conector
     * @param conector Conector a duplicar
     * @param elemGraf Elemento grafico del conector a duplicar
     */
    public void duplicarConector(Conector conector,ElementoGrafico elemGraf) {
        conector.setId(controlador.getContadorElemento());
        controlador.getElementos().add(conector);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        conector.setPosX(dibujo.getLayoutX());
        conector.setPosY(dibujo.getLayoutY());
        setPosX(conector.getPosX());
        setPosY(conector.getPosY());
        
        elem.setComponente(conector);
        elem.setId(controlador.getContadorElemento());
        
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_conectorR.png")));
        dibujo.setText(conector.getNombre() + "_"+ conector.getIdConector());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        dibujo.setLayoutX(elemGraf.getDibujo().getLayoutX()+35);
        dibujo.setLayoutY(elemGraf.getDibujo().getLayoutY()+20);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\nDuplicate connector!");
        alert.showAndWait();
    }
    
    /**
     * Metodo el cual le proporciona eventos al conector tales como movimiento, 
     * abrir ventana para modificarlo o mostrar un menu de acciones
     * @param elem Elemento grafico del conector
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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaConector.fxml"));
                        Parent root = loader.load();
                        //Se crea una instancia del controlador del conector
                        VentanaConectorController conectorController = (VentanaConectorController) loader.getController();
                        
                        /*Se necesito usar otro init de forma que el controller sepa cual es el elemento
                            con el que se esta trabajando ademas de que se manda el mismo controller para 
                            iniciar con los valores del elemento mandado.
                        */
                        conectorController.init(controlador, stage, Pane1, scroll);
                        conectorController.init2(controlador, stage, Pane1,elem,conectorController);
                        Scene scene = new Scene(root);
                        Image ico = new Image("images/acercaDe.png");
                        Stage stage1 = new Stage();
                        stage1.getIcons().add(ico);
                        stage1.setTitle("OptiUAM BC "+elem.getDibujo().getText().toUpperCase());
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
     * ver propiedades del conector
     * @param dibujo Elemento grafico del conector
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
                    System.out.println(dibujo.getId()+"----"+controlador.getElementos().get(elemento).getId());
                    Conector aux=new Conector();
                    Conector aux1=(Conector)controlador.getElementos().get(elemento);
                    aux.setConectadoEntrada(false);
                    aux.setConectadoSalida(false);
                    aux.setIdConector(idConector);
                    aux.setLongitudOnda(aux1.getLongitudOnda());
                    aux.setModo(aux1.getModo());
                    aux.setNombre("connector");
                    aux.setPerdidaInsercion(aux1.getPerdidaInsercion());
                    duplicarConector(aux,dibujo);
                    System.out.println(aux);
                    idConector++;
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
                    Conector aux= (Conector)controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux); 
                }
            }    
            dibujo.getDibujo().setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setHeaderText(null);
                alert.setContentText("\nRemoved connector!");
                alert.showAndWait();

        });
        
        /*Propiedades*/
        menuItem4.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Stage s = new Stage(StageStyle.DECORATED);
                    Image ico = new Image("images/dibujo_conectorR.png");
                    s.getIcons().add(ico);
                    s.setTitle("OptiUAM BC Properties");
                    s.initModality(Modality.APPLICATION_MODAL);
                    Conector aux= (Conector)controlador.getElementos().get(elemento);
                    String mode;
                    Label label;
                    if(aux.getModo() == 0){
                        mode = "Monomode";
                        label = new Label("  Name: "+aux.getNombre()+
                            "\n  Id: "+aux.getIdConector()+
                            "\n  Input: "+aux.getElementoConectadoEntrada()+
                            "\n  Output :"+aux.getElementoConectadoSalida()+
                            "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                            "\n  Mode: "+mode+
                            "\n  Insertion Loss: "+aux.getPerdidaInsercion()+" dB");
                        Scene scene = new Scene(label, 190, 130);
                        s.setScene(scene);
                        s.setResizable(false);
                        s.showAndWait();
                    }
                    else if(aux.getModo() == 1){
                        mode = "Multimode";
                        label = new Label("  Name: "+aux.getNombre()+
                            "\n  Id: "+aux.getIdConector()+
                            "\n  Input: "+aux.getElementoConectadoEntrada()+
                            "\n  Output :"+aux.getElementoConectadoSalida()+
                            "\n  Wavelenght: "+aux.getLongitudOnda()+" nm"+
                            "\n  Mode: "+mode+
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
     * Metodo para cerrar la ventana del conector
     * @param event Representa cualquier tipo de accion
     */
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    /**
     * Metodo para desconectar el conector
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void Desconectar(ActionEvent event){
        for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
            if(conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                Componente comp= controlador.getElementos().get(elemento2);
                comp.setConectadoEntrada(false);
                comp.setElementoConectadoEntrada("");
                System.out.println(comp.getNombre());
                break;
            }
        }
        conectorControl.cboxConectarA.getSelectionModel().select(0);
        if(elemG.getComponente().isConectadoSalida()){
            elemG.getComponente().setConectadoSalida(false);
            elemG.getComponente().setElementoConectadoSalida("");
            elemG.getComponente().getLinea().setVisible(false);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("\nDisconnected connector!");
            alert.showAndWait();
        cerrarVentana(event);
    }
    
    /**
     * Metodo para modificar el conector
     * @param event Representa cualquier tipo de accion
     */
    @FXML
    public void modificar(ActionEvent event){
        Conector aux = (Conector) elemG.getComponente();
        int modo=0, longitudOnda=0;
        double perdidaInsercion, perdidaMax =0.5;
        if(rbtnMono.isSelected()){
            modo=0;
        }else if(rbtnMulti.isSelected()){
            perdidaMax=1.0;
            modo=1;
        }   
        if(rbtn1310.isSelected()){
            longitudOnda=1310;
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
        }
        if((conectorControl.cboxConectarA.getSelectionModel().getSelectedIndex())==0){
            if(elemG.getComponente().isConectadoSalida()){
                Desconectar(event);}
        }else{
            if(aux.isConectadoSalida()){elemG.getComponente().getLinea().setVisible(false);}
            aux.setConectadoSalida(true);

            for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
                if(conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
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

        perdidaInsercion= Double.parseDouble(txtPerdida.getText());

        if (txtPerdida.getText().compareTo("")==0 || !txtPerdida.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid loss value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid loss value");
            alert.showAndWait();
        }
        else if(Double.parseDouble(txtPerdida.getText()) > perdidaMax || Double.parseDouble(txtPerdida.getText()) < 0){
            System.out.println("\nThe loss must be" + " min: 0" + " max: " + perdidaMax);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nThe loss must be" + " min: 0" + " max: " + perdidaMax);
            alert.showAndWait();
        }
        else{
            aux.setLongitudOnda(longitudOnda);
            aux.setNombre("connector");
            aux.setPerdidaInsercion(perdidaInsercion);
            aux.setModo(modo);
            cerrarVentana(event);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("\nModified connector!");
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
     * @param conectorController Controlador del conector
    */
    public void init2(ControladorGeneral controlador, Stage stage, Pane Pane1,ElementoGrafico elem, VentanaConectorController conectorController) {
        this.elemG=elem;
        this.conectorControl=conectorController;
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        conectorController.btnCrear.setVisible(false);
        conectorController.btnDesconectar.setVisible(true);
        conectorController.lblConectarA.setVisible(true);
        conectorController.cboxConectarA.setVisible(true);
        conectorController.btnModificar.setVisible(true);
        
        if(elemG.getComponente().isConectadoSalida()==true){
            conectorControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
        }else{
            conectorControl.cboxConectarA.getItems().add("Desconected");
            conectorControl.cboxConectarA.getSelectionModel().select(0);
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(elem.getComponente().getElementoConectadoEntrada().contains("source")){
                    if("fiber".equals(controlador.getElementos().get(elemento).getNombre())){
                        if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                        conectorControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                        }
                    }
                }
                else{
                    if("fiber".equals(controlador.getElementos().get(elemento).getNombre()) ||
                        "splitter".contains(controlador.getElementos().get(elemento).getNombre()) ||
                        "power".equals(controlador.getElementos().get(elemento).getNombre()) ||
                        "spectrum".equals(controlador.getElementos().get(elemento).getNombre())){
                        if(!controlador.getElementos().get(elemento).isConectadoEntrada()){

                            conectorControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                        }
                    }
                }
            }
        }
        
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                Conector con= (Conector)controlador.getElementos().get(elemento);
                System.out.println(con.getModo()+"\t"+con.getLongitudOnda());
                if(con.getModo()==0){
                    conectorControl.rbtnMono.setSelected(true);
                }else if(con.getModo()==1){
                    conectorControl.rbtnMulti.setSelected(true);
                }
                if(con.getLongitudOnda()==1310){
                    conectorControl.rbtn1310.setSelected(true);
                }else if(con.getLongitudOnda()==1550){
                    conectorControl.rbtn1550.setSelected(true);
                }
                conectorControl.txtPerdida.setText(String.valueOf(con.getPerdidaInsercion()));
            }
        }
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia delante del conector 
     * con otro elemento
     * @param elemG Elemento grafico del conector
     */
    public void dibujarLinea(ElementoGrafico elemG) {
        Line line= new Line();   
        line.setStartX(elemG.getDibujo().getLayoutX()+elemG.getDibujo().getWidth());
        line.setStartY(elemG.getDibujo().getLayoutY()+8);
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elemG.getComponente().getElementoConectadoSalida().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);
        
        if(aux.getDibujo().getText().contains("fiber")){
            line.setEndX(aux.getDibujo().getLayoutX()+3);
            line.setEndY(aux.getDibujo().getLayoutY()+20);
        }else{
            line.setEndX(aux.getDibujo().getLayoutX());
            line.setEndY(aux.getDibujo().getLayoutY()+8);
        }
        
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        elemG.getComponente().setLinea(line);
              
    }
    
    /**
     * Metodo que permite visualizar la conexion hacia atras del conector 
     * con otro elemento
     * @param elem Elemento grafico del conector
     */
    public void dibujarLineaAtras(ElementoGrafico elem) {
        Line line= new Line();   
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elem.getComponente().getElementoConectadoEntrada().equals(controlador.getDibujos().get(it).getDibujo().getText())){
                aux=controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2.5);
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
    public void imprimir(ActionEvent event){
        int modo=0, longitudOnda=0, id = 0;
        double perdidaInsercion, perdidaMax =0.5;
        
        if(rbtnMono.isSelected()){
            modo=0;
        }else if(rbtnMulti.isSelected()){
            perdidaMax=1.0;
            modo=1;
        }   
        if(rbtn1310.isSelected()){
            longitudOnda=1310;
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
        }
        perdidaInsercion= Double.parseDouble(txtPerdida.getText());
        if (txtPerdida.getText().compareTo("")==0 || !txtPerdida.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("\nInvalid loss value");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid loss value");
            alert.showAndWait();
        }
        else if(Double.parseDouble(txtPerdida.getText()) > perdidaMax || Double.parseDouble(txtPerdida.getText()) < 0){
            System.out.println("\nThe loss must be" + " min: 0" + " max: " + perdidaMax);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nThe loss must be" + " min: 0" + " max: " + perdidaMax);
            alert.showAndWait();
        }
        else{
            Conector con= new Conector();
            con.setConectadoEntrada(false);
            con.setConectadoSalida(false);
            con.setIdConector(idConector);
            con.setLongitudOnda(longitudOnda);
            con.setNombre("connector");
            con.setPerdidaInsercion(perdidaInsercion);
            con.setModo(modo);
            guardarConector(con);
            idConector++;
            cerrarVentana(event);
        }
    }
    
}
