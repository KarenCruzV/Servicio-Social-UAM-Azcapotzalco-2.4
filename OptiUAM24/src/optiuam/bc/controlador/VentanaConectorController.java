
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.Conector;
import optiuam.bc.modelo.ElementoGrafico;


/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaConectorController extends ControladorGeneral implements Initializable {
    
    static int idConector = 0;
    ControladorGeneral controlador;
    Stage stage;
    ElementoGrafico elemG;
    VentanaConectorController conectorControl;
    static double posX, posY;
    
    @FXML
    private Pane Pane1;
    @FXML
        Label lblTitulo, lblLongitudOnda,lblModo, lblPerdida, lbldB, lblConectarA, lblPropiedades;
    @FXML
        RadioButton rbtn1310, rbtn1550, rbtnMono, rbtnMulti;
    @FXML
        TextField txtPerdida;
    @FXML
        Button btnDesconectar, btnCancelar, btnCrear, btnModificar;
    @FXML
        ComboBox cboxConectarA;
    @FXML
        AnchorPane ConectorVentana;
    @FXML
    private ScrollPane scroll;

    public static double getPosX() {
        return posX;
    }

    public static void setPosX(double posX) {
        VentanaConectorController.posX = posX;
    }

    public static double getPosY() {
        return posY;
    }

    public static void setPosY(double posY) {
        VentanaConectorController.posY = posY;
    }

    public void imprimir(ActionEvent event){
        int modo=0, longitudOnda=0, id = 0;
        double perdidaInsercion, perdidaMax =0.5;
        
        if(rbtnMono.isSelected()){
            modo=0;
            //System.out.println("Tipo Mono");
        }else if(rbtnMulti.isSelected()){
            perdidaMax=1.0;
            modo=1;
            //System.out.println("Tipo Multi");
        }   
        if(rbtn1310.isSelected()){
            longitudOnda=1310;
            //System.out.println(1310);
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
            //System.out.println(1550);
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
    
    private void guardarConector(Conector conector) {
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
    
    private void guardarConector2(Conector conector,ElementoGrafico el) {
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
        alert.setContentText("\nConnector created!");
        alert.showAndWait();
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
                    //System.out.println("Hola conector"+elem.getId());
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaConector.fxml"));
                        //FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaConector.fxml"));
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
                        //stage1.initModality(Modality.APPLICATION_MODAL);
                        stage1.setScene(scene);
                        stage1.setResizable(false);
 
                        stage1.showAndWait();
                    }
                    catch(IOException ex){
                        Logger.getLogger(VentanaConectorController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(event.getButton()==MouseButton.SECONDARY){
                    mostrarMenuChiquito(elem);
                }
        });
    }
    
    public void mostrarMenuChiquito(ElementoGrafico dibujo){
        // create a menu
        ContextMenu contextMenu = new ContextMenu();

        // create menuitems
        MenuItem menuItem1 = new MenuItem("-Duplicate");
        MenuItem menuItem2 = new MenuItem("-Rotate");
        MenuItem menuItem3 = new MenuItem("-Delete");
        

        menuItem1.setOnAction(e ->{
            //System.out.println("Duplicar");
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
                    guardarConector2(aux,dibujo);
                    System.out.println(aux);
                    idConector++;
                    break;
                }
            }
        });

        menuItem2.setOnAction(e ->{
            System.out.println("Girar conector");
        });

        menuItem3.setOnAction(e ->{
            if(dibujo.getComponente().isConectadoSalida()==true){
                for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getComponente().getElementoConectadoSalida()==controlador.getDibujos().get(elemento).getDibujo().getText()){
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
                if(dibujo.getComponente().getElementoConectadoEntrada()==controlador.getDibujos().get(elemento).getDibujo().getText()){
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
                    Conector aux= (Conector)controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux); 
                }
            }    
            
            dibujo.getDibujo().setVisible(false);

        });
        MenuItem menuItem4 = new MenuItem("-Properties");
        menuItem4.setOnAction(e ->{
            //Tooltip tt= new Tooltip();
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Conector fue= (Conector)controlador.getElementos().get(elemento);
                    
                    String name = "Name: "+fue.getNombre();
                    String id = "Id = "+fue.getIdConector();
                    String conE = "Input: "+fue.getElementoConectadoEntrada();
                    String conS = "Output: "+fue.getElementoConectadoSalida();
                    //tt.setText(name+"\n"+id+"\n"+conE+"\n"+conS);
                    System.out.println(name+"\n"+id+"\n"+conE+"\n"+conS);
                //dibujo.getDibujo().setTooltip(tt);
                }
            }
        });

        // add menu items to menu
        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem2);
        contextMenu.getItems().add(menuItem3);
        contextMenu.getItems().add(menuItem4);
        dibujo.getDibujo().setContextMenu(contextMenu);
    }
    
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCrear.setVisible(true);
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnModificar.setVisible(false);
    } 
    
    @FXML
    private void Desconectar(ActionEvent event){
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
        //elemG.getComponente().setConectadoEntrada(false);
        if(elemG.getComponente().isConectadoSalida()){
        elemG.getComponente().setConectadoSalida(false);
        elemG.getComponente().setElementoConectadoSalida("");
        elemG.getComponente().getLinea().setVisible(false);
        }
        cerrarVentana(event);
    }
    
    @FXML
    private void modificar(ActionEvent event){
        Conector aux = (Conector) elemG.getComponente();
        int modo=0, longitudOnda=0, id = 0;
        double perdidaInsercion, perdidaMax =0.5;
        if(rbtnMono.isSelected()){
            modo=0;
            //System.out.println("Tipo Mono");
        }else if(rbtnMulti.isSelected()){
            perdidaMax=1.0;
            modo=1;
            //System.out.println("Tipo Multi");
        }   
        if(rbtn1310.isSelected()){
            longitudOnda=1310;
            //System.out.println(1310);
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
            //System.out.println(1550);
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
            //System.out.println(conectorControl.cboxConectarA.getSelectionModel().getSelectedItem().toString());
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
            //aux.setConectadoEntrada(false);
            //aux.setConectadoSalida(false);
            //aux.setIdConector(idConector);
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

            //System.out.println(aux.toString());
            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
            }
        }
        
    }

    void init(ControladorGeneral controlador, Stage stage, Pane Pane1, ScrollPane scroll) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.scroll=scroll;
    }

    /*
        Funcion init2: 
        recibe el elemento y el controlador a partir de estos puede mostrar los valores inciales del elemento 
    */
    private void init2(ControladorGeneral controlador, Stage stage, Pane Pane1,ElementoGrafico elem, VentanaConectorController conectorController) {
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
                }else{
                if("fiber".equals(controlador.getElementos().get(elemento).getNombre()) ||
                    "splitter".equals(controlador.getElementos().get(elemento).getNombre()) ||
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
    
    private void dibujarLinea(ElementoGrafico elemG) {
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
        //setLinea(line);
        //System.out.println("Se dibujo una linea");
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        elemG.getComponente().setLinea(line);
              
    }
    
    private void dibujarLineaAtras(ElementoGrafico elem) {
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
        //setLinea(line);
        //System.out.println("Se dibujo una linea");
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
