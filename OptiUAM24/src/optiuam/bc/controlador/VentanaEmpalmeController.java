    
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaEmpalmeController extends ControladorGeneral implements Initializable {
    
    static int idEmpalme = 0;
    ControladorGeneral controlador;
    Stage stage;
    ElementoGrafico elemG;
    VentanaEmpalmeController empalmeControl;
    static double posX, posY;
    
    @FXML
    RadioButton rbtn1310, rbtn1550, rbtnfusion, rbtnMecanico;
    
    @FXML
    TextField txtPerdida;
    
    @FXML
    Button btnDesconectar, btnModificar, btnCrear;
    
    @FXML
    Label lblConectarA;
    
    @FXML
    ComboBox cboxConectarA;
    
    @FXML
    private Pane Pane1;
    
    @FXML
    private ScrollPane scroll;

    public static double getPosX() {
        return posX;
    }

    public static void setPosX(double posX) {
        VentanaEmpalmeController.posX = posX;
    }

    public static double getPosY() {
        return posY;
    }

    public static void setPosY(double posY) {
        VentanaEmpalmeController.posY = posY;
    }
    
    public void imprimir(ActionEvent event){
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
            Empalme empalme= new Empalme();
            empalme.setConectadoEntrada(false);
            empalme.setConectadoSalida(false);
            empalme.setIdEmpalme(idEmpalme);
            empalme.setLongitudOnda(longitudOnda);
            empalme.setNombre("splice"); //empalme
            empalme.setPerdidaInsercion(perdidaInsercion);
            empalme.setTipo(tipo);
            guardarEmpalme(empalme);
            idEmpalme++;
            cerrarVentana(event);
        }
    }

    public static int getIdEmpalme() {
        return idEmpalme;
    }

    public static void setIdEmpalme(int idEmpalme) {
        VentanaEmpalmeController.idEmpalme = idEmpalme;
    }
    
    public void guardarEmpalme(Empalme empalme) {
        empalme.setId(controlador.getContadorElemento());
        controlador.getElementos().add(empalme);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        //empalme.setPosX(dibujo.getLayoutX());
        //empalme.setPosY(dibujo.getLayoutY());
        //setPosX(empalme.getPosX());
        //setPosY(empalme.getPosY());
        
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
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\nSplice created!");
        alert.showAndWait();
    }
    
    public void guardarEmpalme2(Empalme empalme, ElementoGrafico el) {
        empalme.setId(controlador.getContadorElemento());
        controlador.getElementos().add(empalme);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        //empalme.setPosX(dibujo.getLayoutX());
        //empalme.setPosY(dibujo.getLayoutY());
        //setPosX(empalme.getPosX());
        //setPosY(empalme.getPosY());
        
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
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\nSplice created!");
        alert.showAndWait();
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
            elem.getDibujo().setOnMouseClicked((MouseEvent event) -> {
                if(event.getButton()==MouseButton.PRIMARY){
                    //System.out.println("Hola empalme"+elem.getId());
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
                        
                        //empalmeController.init(controlador, this.stage, this.Pane1);
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
                        Logger.getLogger(VentanaEmpalmeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(event.getButton()==MouseButton.SECONDARY){
                    mostrarMenuChiquito(elem);
                }
        });
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
    public void mostrarMenuChiquito(ElementoGrafico dibujo){
        // create a menu
        ContextMenu contextMenu = new ContextMenu();

        // create menuitems
        MenuItem menuItem1 = new MenuItem("-Duplicated");
        //MenuItem menuItem2 = new MenuItem("-Girar");
        MenuItem menuItem3 = new MenuItem("-Delet");

        menuItem1.setOnAction(e ->{
            //System.out.println("Duplicar");
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    System.out.println(dibujo.getId()+"----"+controlador.getElementos().get(elemento).getId());
                    Empalme empalmeAux=new Empalme();
                    Empalme aux1=(Empalme)controlador.getElementos().get(elemento);
                    empalmeAux.setConectadoEntrada(false);
                    empalmeAux.setConectadoSalida(false);
                    empalmeAux.setIdEmpalme(idEmpalme);
                    empalmeAux.setLongitudOnda(aux1.getLongitudOnda());
                    empalmeAux.setPerdidaInsercion(aux1.getPerdidaInsercion());
                    empalmeAux.setTipo(aux1.getTipo());
                    empalmeAux.setNombre("splice");
                    guardarEmpalme2(empalmeAux,dibujo);
                    //System.out.println(empalmeAux);
                    idEmpalme++;
                    break;
                }
            }
        });

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
                    Empalme aux= (Empalme)controlador.getElementos().get(elemento);
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
                    Empalme empalme = (Empalme)controlador.getElementos().get(elemento);
                    String name = "Name: "+empalme.getNombre();
                    String id = "Id = "+empalme.getIdEmpalme();
                    String conE = "Input: "+empalme.getElementoConectadoEntrada();
                    String conS = "Output: "+empalme.getElementoConectadoSalida();
                    System.out.println(name+"\n"+id+"\n"+conE+"\n"+conS);
                    Tooltip proEmpalme = new Tooltip();
                    String tipo;
                    if(empalme.getTipo() == 0){
                        tipo = "Fusion";
                        proEmpalme.setText("Name: "+empalme.getNombre()+
                            "\nId = "+empalme.getIdEmpalme()+
                            "\nInput: "+empalme.getElementoConectadoEntrada()+
                            "\nOutput :"+empalme.getElementoConectadoSalida()+
                            "\nWavelenght: "+empalme.getLongitudOnda()+" nm"+
                            "\nType: "+tipo+
                            "\nInsertion Loss: "+empalme.getPerdidaInsercion()+" dB");
                    }
                    else if(empalme.getTipo()== 1){
                        tipo = "Mechanic";
                        proEmpalme.setText("Name: "+empalme.getNombre()+
                            "\nId = "+empalme.getIdEmpalme()+
                            "\nInput: "+empalme.getElementoConectadoEntrada()+
                            "\nOutput :"+empalme.getElementoConectadoSalida()+
                            "\nWavelenght: "+empalme.getLongitudOnda()+" nm"+
                            "\nType: "+tipo+
                            "\nInsertion Loss: "+empalme.getPerdidaInsercion()+" dB");
                    }
                    dibujo.getDibujo().setTooltip(proEmpalme);
                }
            }
                
        });

        // add menu items to menu
        contextMenu.getItems().add(menuItem1);
        //contextMenu.getItems().add(menuItem2);
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
        Tooltip perdida = new Tooltip();
        perdida.setText("The loss must be" + " min: 0" + " max: 0.5");
        txtPerdida.setTooltip(perdida);
        btnCrear.setVisible(true);
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnModificar.setVisible(false);
    }    
    
    @FXML
    public void modificar(ActionEvent event){
        Empalme aux = (Empalme) elemG.getComponente();
        int tipo=0, longitudOnda=0, id = 0;
        double perdidaInsercion, perdidaMax = 0.5;
        if(rbtnMecanico.isSelected()){
            tipo=1;
            //System.out.println("Tipo Mono");
        }else if(rbtnfusion.isSelected()){
            tipo=0;
            //System.out.println("Tipo Multi");
        }   
        if(rbtn1310.isSelected()){
            longitudOnda=1310;
            //System.out.println(1310);
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
            //System.out.println(1550);
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
            //System.out.println(empalmeControl.cboxConectarA.getSelectionModel().getSelectedItem().toString());
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
            //aux.setIdEmpalme(idEmpalme);
            aux.setLongitudOnda(longitudOnda);
            aux.setNombre("splice");
            aux.setPerdidaInsercion(perdidaInsercion);
            aux.setTipo(tipo);
            cerrarVentana(event);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("\nModified splice!");
            alert.showAndWait();

            //System.out.println(aux.toString());
            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
            }
        }
    }

    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1, ScrollPane scroll) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.scroll=scroll;
    }
    
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
        //elemG.getComponente().setConectadoEntrada(false);
        if(elemG.getComponente().isConectadoSalida()){
        elemG.getComponente().setConectadoSalida(false);
        elemG.getComponente().setElementoConectadoSalida("");
        elemG.getComponente().getLinea().setVisible(false);
        }
        cerrarVentana(event);
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
        line.setStroke(Color.BLACK);
        line.setEndX(aux.getDibujo().getLayoutX());
        line.setEndY(aux.getDibujo().getLayoutY());
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
        line.setStroke(Color.BLACK);
        line.setStartX(aux.getDibujo().getLayoutX()+aux.getDibujo().getWidth());
        line.setStartY(aux.getDibujo().getLayoutY()+10);
        line.setEndX(elem.getDibujo().getLayoutX());
        line.setEndY(elem.getDibujo().getLayoutY()+7);
        //System.out.println("Se dibujo una linea");
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        aux.getComponente().setLinea(line);
            
    }
    
}
