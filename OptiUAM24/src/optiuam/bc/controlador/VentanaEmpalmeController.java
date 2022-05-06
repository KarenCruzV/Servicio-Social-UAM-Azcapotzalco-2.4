    
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
    
    private void guardarEmpalme(Empalme empalme) {
        empalme.setId(controlador.getContadorElemento());
        controlador.getElementos().add(empalme);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        empalme.setPosX(dibujo.getLayoutX());
        empalme.setPosY(dibujo.getLayoutY());
        setPosX(empalme.getPosX());
        setPosY(empalme.getPosY());
        
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

    private void eventos(ElementoGrafico elem) {
        elem.getDibujo().setOnMouseDragged((MouseEvent event) -> {
                if(event.getButton()==MouseButton.PRIMARY){
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
                        stage1.setTitle("OptiUAM BC Splice");
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
                    guardarEmpalme(empalmeAux);
                    //System.out.println(empalmeAux);
                    idEmpalme++;
                    break;
                }
            }
        });

        menuItem3.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Empalme aux= (Empalme)controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux); 
                }
            }    
            if(dibujo.getComponente().isConectadoSalida()==true){
                dibujo.getComponente().getLinea().setVisible(false);
            }
            dibujo.getDibujo().setVisible(false);

        });
        MenuItem menuItem4 = new MenuItem("-Properties");
        menuItem4.setOnAction(e ->{
            //Tooltip tt= new Tooltip();
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Empalme fue= (Empalme)controlador.getElementos().get(elemento);
                    
                    String name = "Name: "+fue.getNombre();
                    String id = "Id = "+fue.getIdEmpalme();
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
        btnCrear.setVisible(true);
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnModificar.setVisible(false);
    }    
    
    @FXML
    private void modificar(ActionEvent event){
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
            if(aux.isConectadoSalida()){}
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

    void init(ControladorGeneral controlador, Stage stage, Pane Pane1, ScrollPane scroll) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.scroll=scroll;
    }
    
    private void init2(ElementoGrafico elem, VentanaEmpalmeController empalmeController) {
        this.elemG=elem;
        this.empalmeControl=empalmeController;
        empalmeControl.cboxConectarA.getItems().add("Desconected");
        if(elemG.getComponente().isConectadoSalida()==true){
            empalmeControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
        }else{
            empalmeControl.cboxConectarA.getSelectionModel().select(0);
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
            if("fiber".equals(controlador.getElementos().get(elemento).getNombre())){
                if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                    empalmeControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                }
            }
        }
    }
    
    @FXML
    private void Desconectar(ActionEvent event){
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
        line.setStroke(Color.BLACK);
        line.setEndX(aux.getDibujo().getLayoutX());
        line.setEndY(aux.getDibujo().getLayoutY());
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
