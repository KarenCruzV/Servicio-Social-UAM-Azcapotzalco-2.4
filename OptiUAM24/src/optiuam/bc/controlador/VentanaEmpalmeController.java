    
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
    static Line linea;
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

    public static Line getLinea() {
        return linea;
    }

    public static void setLinea(Line linea) {
        VentanaEmpalmeController.linea = linea;
    }

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
            System.out.println("\nValor de la pérdida invalido");
        }
        else if(Double.parseDouble(txtPerdida.getText()) > perdidaMax || Double.parseDouble(txtPerdida.getText()) < 0){
            System.out.println("\nLa pérdida debe ser" + " min: 0" + " max: " + perdidaMax);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nLa pérdida debe ser" + " min: 0" + " max: " + perdidaMax);
            alert.showAndWait();
        }
        else{
            Empalme empalme= new Empalme();
            empalme.setConectadoEntrada(false);
            empalme.setConectadoSalida(false);
            empalme.setIdEmpalme(idEmpalme);
            empalme.setLongitudOnda(longitudOnda);
            empalme.setNombre("empalme");
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
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("\n¡Empalme creado!");
        alert.showAndWait();
    }

    private void eventos(ElementoGrafico elem) {
        elem.getDibujo().setOnMouseDragged((MouseEvent event) -> {
                if(event.getButton()==MouseButton.PRIMARY){
                    elem.getDibujo().setLayoutX(event.getSceneX()-20);
                    elem.getDibujo().setLayoutY(event.getSceneY()-170);
                    elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                    
                    if(elem.getComponente().isConectadoSalida()==true){
                        borrarLinea(linea);
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
                    //System.out.println("Hola empalme"+elem.getId());
                    try{
                        Stage stage1 = new Stage(StageStyle.UTILITY);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaEmpalme.fxml"));
                        Parent root = loader.load();
                        //Se crea una instancia del controlador del empalme.
                        VentanaEmpalmeController empalmeController = (VentanaEmpalmeController) loader.getController();
                        empalmeController.init(controlador,stage,Pane1);
                        empalmeController.init2(elem,empalmeController);
                        empalmeController.btnCrear.setVisible(false);
                        empalmeController.btnDesconectar.setVisible(true);
                        empalmeController.lblConectarA.setVisible(true);
                        empalmeController.cboxConectarA.setVisible(true);
                        empalmeController.btnModificar.setVisible(true);
                        empalmeController.init(controlador, this.stage, this.Pane1);
                        Scene scene = new Scene(root);
                        Image ico = new Image("images/acercaDe.png");
                        stage1.getIcons().add(ico);
                        stage1.setTitle("OptiUAM BC Empalme");
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
        MenuItem menuItem1 = new MenuItem("-Duplicar");
        //MenuItem menuItem2 = new MenuItem("-Girar");
        MenuItem menuItem3 = new MenuItem("-Eliminar");

        menuItem1.setOnAction(e ->{
            System.out.println("Duplicar");
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
                    empalmeAux.setNombre("empalme");
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
                borrarLinea(linea);
            }
            dibujo.getDibujo().setVisible(false);

        });
        MenuItem menuItem4 = new MenuItem("-Propiedades");
        menuItem4.setOnAction(e ->{
            //Tooltip tt= new Tooltip();
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Empalme fue= (Empalme)controlador.getElementos().get(elemento);
                    
                    String name= "NOMBRE: "+fue.getNombre();
                    String id= "ID= "+fue.getIdEmpalme();
                    String conE= "Entrada:"+fue.getElementoConectadoEntrada();
                    String conS= "Salida:"+fue.getElementoConectadoSalida();
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
                    ElementoGrafico poyo= controlador.getDibujos().get(elemento2);
                    aux.setElementoConectadoSalida(poyo.getDibujo().getText());
                    aux.setConectadoSalida(true);
                    //controlador.getDibujos().get(elemento2).getComponente().setElementoConectadoEntrada(this.elemG);
                    controlador.getDibujos().get(elemento2).getComponente().setConectadoEntrada(true);
                    //System.out.println(poyo.getComponente().getElementoConectadoEntrada().getDibujo().getText());
                    //System.out.println(fuenteControl.cboxConectarA.getSelectionModel().getSelectedItem().toString());
                    //System.out.println(controlador.getDibujos().get(elemento2).getDibujo().getText());
                    break;
                }
            }
            dibujarLinea(elemG);
            //System.out.println(empalmeControl.cboxConectarA.getSelectionModel().getSelectedItem().toString());
        }
        perdidaInsercion= Double.parseDouble(txtPerdida.getText());

        if (txtPerdida.getText().compareTo("")==0 || !txtPerdida.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("\nValor de la pérdida invalido");
        }
        else if(Double.parseDouble(txtPerdida.getText()) > perdidaMax || Double.parseDouble(txtPerdida.getText()) < 0){
            System.out.println("\nLa pérdida debe ser" + " min: 0" + " max: " + perdidaMax);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nLa pérdida debe ser" + " min: 0" + " max: " + perdidaMax);
            alert.showAndWait();
        }
        else{
            //aux.setIdEmpalme(idEmpalme);
            aux.setLongitudOnda(longitudOnda);
            aux.setNombre("empalme");
            aux.setPerdidaInsercion(perdidaInsercion);
            aux.setTipo(tipo);
            cerrarVentana(event);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("\n¡Empalme modificado!");
            alert.showAndWait();

            System.out.println(aux.toString());
            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
            }
        }
            
    }

    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
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
            if("fibra".equals(controlador.getElementos().get(elemento).getNombre())){
                empalmeControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
            }
        }
    }
    
    @FXML
    private void Desconectar(ActionEvent event){
        empalmeControl.cboxConectarA.getSelectionModel().select(0);
        elemG.getComponente().setConectadoEntrada(false);
        elemG.getComponente().setConectadoSalida(false);
        elemG.getComponente().setElementoConectadoSalida(null);
        getLinea().setVisible(false);
        cerrarVentana(event);
    }
    
    private Line dibujarLinea(ElementoGrafico elemG) {
        linea = new Line();
        linea.setStartX(elemG.getDibujo().getLayoutX()+45);
        linea.setStartY(elemG.getDibujo().getLayoutY()+7);
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elemG.getComponente().getElementoConectadoSalida()==controlador.getDibujos().get(it).getDibujo().getText()){
                aux=controlador.getDibujos().get(it);
            }
        }
        linea.setEndX(aux.getDibujo().getLayoutX());
        linea.setEndY(aux.getDibujo().getLayoutY());
        linea.setStroke(Color.GREY);
        linea.setStrokeWidth(2);
        setLinea(linea);
        //System.out.println("Se dibujo una linea");
        linea.setVisible(true);
        Pane1.getChildren().add(getLinea());
        return linea;        
    }
    
    private void borrarLinea(Line linea){
        linea.setVisible(false);
    }
    
}
