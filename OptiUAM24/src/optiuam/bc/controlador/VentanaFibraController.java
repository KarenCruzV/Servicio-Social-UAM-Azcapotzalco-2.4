
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
import optiuam.bc.modelo.ElementoGrafico;
import optiuam.bc.modelo.Fibra;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaFibraController extends VentanaPrincipal implements Initializable {
    
    static int idFibra = 0;
    ControladorGeneral controlador;
    Stage stage;
    ElementoGrafico elemG;
    VentanaFibraController fibraControl;
    static Line linea;
    static double posX, posY;
    
    @FXML
    private TextField txtAtenue, txtDisp, txtDistancia;
    
    @FXML 
    private RadioButton rbtnMono, rbtnMulti, rbtn1310, rbtn1550, rbtnOtro, rbtn28, rbtn50;
    
    @FXML
    public Button btnCrear, btnDesconectar, btnModificar;
    
    @FXML
    public Label lblConectarA;
    
    @FXML
    public ComboBox cboxConectarA;
    
    @FXML
    public Separator separator;

    public static Line getLinea() {
        return linea;
    }

    public static void setLinea(Line linea) {
        VentanaFibraController.linea = linea;
    }

    public static double getPosX() {
        return posX;
    }

    public static void setPosX(double posX) {
        VentanaFibraController.posX = posX;
    }

    public static double getPosY() {
        return posY;
    }

    public static void setPosY(double posY) {
        VentanaFibraController.posY = posY;
    }
    
    @Override
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        separator.setVisible(false);
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnModificar.setVisible(false);
    } 
    
    @FXML
    private void Desconectar(ActionEvent event){
        fibraControl.cboxConectarA.getSelectionModel().select(0);
        elemG.getComponente().setConectadoEntrada(false);
        elemG.getComponente().setConectadoSalida(false);
        elemG.getComponente().setElementoConectadoSalida(null);
        getLinea().setVisible(false);
        cerrarVentana(event);
    }
    
    @FXML
    private void rbtn1310Action(ActionEvent event){
        if (!rbtnOtro.isSelected()) {
            if (rbtnMono.isSelected()) { // monomodo 1310
                txtAtenue.setText("0.32");
                txtAtenue.setEditable(false);
                txtDisp.setText("0");
                txtDisp.setEditable(false);
            } else { // multimodod 1310
                txtAtenue.setText("0.36");
                txtAtenue.setEditable(false);
                txtDisp.setText("3.5");
                txtDisp.setEditable(false);
            }
        }
    }
    
    @FXML
    private void rbtn1550Action(ActionEvent event){
        if (!rbtnOtro.isSelected()) {
            if (rbtnMono.isSelected()) { // monomodo 1510
                txtAtenue.setText("0.18");
                txtAtenue.setEditable(false);
                txtDisp.setText("18");
                txtDisp.setEditable(false);
            }
        }
    }
    
    @FXML
    private void rbtnMm50(ActionEvent event){
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
    
    @FXML
    private void rbtnSmf28(ActionEvent event){
        if (!rbtnOtro.isSelected()) {
            rbtn1550.setDisable(false);
            rbtnMono.setSelected(true);
            rbtnMono.setDisable(false);
            rbtnMulti.setDisable(true);
            if (rbtn1310.isSelected()) { // monomodo 1310
                txtAtenue.setText("0.32");
                txtAtenue.setEditable(false);
                txtDisp.setText("0");
                txtDisp.setEditable(false);
            } else { // monomodo 1550
                txtAtenue.setText("0.18");
                txtAtenue.setEditable(false);
                txtDisp.setText("18");
                txtDisp.setEditable(false);
            }
        }
    }
    
    @FXML
    private void rbtnOtro(ActionEvent event){
        rbtnMulti.setDisable(false);
        rbtnMono.setDisable(false);
        rbtn1310.setDisable(false);
        rbtn1550.setDisable(false);
        txtDisp.setText("");
        txtDisp.setEditable(true);
        txtAtenue.setText("");
        txtAtenue.setEditable(true);
    }
    
    @FXML
    private void modificar(ActionEvent event){
        Fibra aux = (Fibra) elemG.getComponente();
        int modo=0, longitudOnda=0, tipo=0, id = 0;
        double longitudKm, atenue, dispersion;

        if (modo == 1) // multimodo
        {
            rbtnMulti.setSelected(true);
        }

        if (longitudOnda == 1550) // 1310 nm
        {
            rbtn1550.setSelected(true);
        }
        if (tipo == 1) // mm50
        {
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtn1550.setDisable(true);
            rbtnMono.setDisable(true);
            rbtn50.setSelected(true);
        }
        if(tipo ==0){ // smf28
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtnMulti.setDisable(true);
            rbtn28.setSelected(true);
        }
        if(tipo == 2){} //otro

        if((fibraControl.cboxConectarA.getSelectionModel().getSelectedIndex())==0){
            //aux.setConectadoEntrada(false);
            //aux.setConectadoSalida(false);
            //aux.setElementoConectadoSalida(null);
            Desconectar(event);
        }
        else{
            if(aux.isConectadoSalida()){}
            aux.setConectadoSalida(true);
            
            for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
                if(fibraControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                    ElementoGrafico poyo= controlador.getDibujos().get(elemento2);
                    aux.setElementoConectadoSalida(poyo);
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
            //System.out.println(fibraControl.cboxConectarA.getSelectionModel().getSelectedItem().toString());
        }

        longitudKm= Double.parseDouble(txtDistancia.getText());
        atenue= Double.parseDouble(txtAtenue.getText());
        dispersion= Double.parseDouble(txtDisp.getText());
        aux.setAtenuacion(atenue);
        aux.setDispersion(dispersion);
        aux.setLongitudOnda(longitudOnda);
        aux.setModo(modo);
        aux.setLongitud_km(longitudKm);
        aux.setTipo(tipo);
        //aux.setIdFibra(idFibra);
        //f.setNombre("fibraEnviada");
        //aux.setConectadoEntrada(false);
        //aux.setConectadoSalida(false);
        //System.out.println(f.toString());

        //this.fibra=f;
        //System.out.println(fibra.toString());

        cerrarVentana(event);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("\n¡Fibra modificada!");
        alert.showAndWait();

        System.out.println(aux.toString());
        for(int h=0; h<controlador.getElementos().size(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
        }

    }
    
    public void enviarDatos(ActionEvent event){
        int modo=0, longitudOnda=0, tipo=0, id = 0;
        double longitudKm, atenue, dispersion;
        
        if (modo == 1) // multimodo
        {
            rbtnMulti.setSelected(true);
        }
        
        if (longitudOnda == 1550) // 1310 nm
        {
            rbtn1550.setSelected(true);
        }
        if (tipo == 1) // mm50
        {
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtn1550.setDisable(true);
            rbtnMono.setDisable(true);
            rbtn50.setSelected(true);
        }
        if(tipo ==0){ // smf28
            txtDisp.setEditable(false);
            txtAtenue.setEditable(false);
            rbtnMulti.setDisable(true);
            rbtn28.setSelected(true);
        }
        if(tipo == 2){} //otro
        
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
        //f.setNombre("fibraEnviada");
        f.setConectadoEntrada(false);
        f.setConectadoSalida(false);
        //System.out.println(f.toString());
        guardarComponente(f);
        idFibra++;
        
        //this.fibra=f;
        //System.out.println(fibra.toString());
        
        cerrarVentana(event);
    }
    
    public void guardarComponente(Fibra fibra){
        fibra.setNombre("fibra");
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
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("\n¡Fibra creada!");
        alert.showAndWait();
    }
    
    public void eventos(ElementoGrafico elem){
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
                    //System.out.println("Hola fibra");
                    try{
                        Stage stage1 = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaFibra.fxml"));
                        Parent root = loader.load();
                        //Se crea una instancia del controlador del conector
                        VentanaFibraController fibraController = (VentanaFibraController) loader.getController();
                        fibraController.init(controlador, stage, Pane1);
                        /*Se necesito usar otro init de forma que el controller sepa cual es el elemento
                            con el que se esta trabajando ademas de que se manda el mismo controller para 
                            iniciar con los valores del elemento mandado.
                        */
                        fibraController.init2(elem,fibraController);
                        fibraController.separator.setVisible(true);
                        fibraController.btnCrear.setVisible(false);
                        fibraController.btnDesconectar.setVisible(true);
                        fibraController.lblConectarA.setVisible(true);
                        fibraController.cboxConectarA.setVisible(true);
                        fibraController.btnModificar.setVisible(true);
                        fibraController.init(controlador, this.stage, this.Pane1);
                        Scene scene = new Scene(root);
                        Image ico = new Image("images/acercaDe.png");
                        stage1.getIcons().add(ico);
                        stage1.setTitle("OptiUAM BC Fibra");
                        stage1.initModality(Modality.APPLICATION_MODAL);
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

    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
    }
    
    public void mostrarMenuChiquito(ElementoGrafico dibujo){
        // create a menu
        ContextMenu contextMenu = new ContextMenu();

        // create menuitems
        MenuItem menuItem1 = new MenuItem("-Duplicar");
        MenuItem menuItem2 = new MenuItem("-Girar");
        MenuItem menuItem3 = new MenuItem("-Eliminar");

        menuItem1.setOnAction(e ->{
            System.out.println("Duplicar");
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    System.out.println(dibujo.getId()+"----"+controlador.getElementos().get(elemento).getId());
                    Fibra aux= new Fibra();
                    Fibra aux1= (Fibra)controlador.getElementos().get(elemento);
                    aux.setAtenuacion(aux1.getAtenuacion());
                    aux.setDispersion(aux1.getDispersion());
                    aux.setLongitudOnda(aux1.getLongitudOnda());
                    aux.setLongitud_km(aux1.getLongitud_km());
                    aux.setModo(aux1.getModo());
                    aux.setTipo(aux1.getTipo());
                    aux.setNombre("fibra");
                    aux.setIdFibra(idFibra);
                    aux.setConectadoEntrada(false);
                    aux.setConectadoSalida(false);
                    guardarComponente(aux);
                    idFibra++;
                    System.out.println(aux.toString());
                    for(int h=0; h<controlador.getElementos().size(); h++){
                        System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                        System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
                    }
                    break;
                }
            }
        });

        menuItem2.setOnAction(e ->{
            System.out.println("La fibra no gira");
        });

        menuItem3.setOnAction(e ->{
            System.out.println("Elemento "+dibujo.getDibujo().getText()+" eliminado");
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Fibra aux= (Fibra)controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux); 
                }
            }   
            borrarLinea(linea);
            dibujo.getDibujo().setVisible(false);
            System.out.print(controlador.getContadorElemento());
            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
            }
        });

        // add menu items to menu
        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem2);
        contextMenu.getItems().add(menuItem3);

        dibujo.getDibujo().setContextMenu(contextMenu);
    }
    
    private void init2(ElementoGrafico elem, VentanaFibraController fibraController) {
        this.elemG=elem;
        this.fibraControl=fibraController;
        fibraControl.cboxConectarA.getItems().add("Desconected");
        if(elemG.getComponente().isConectadoSalida()==true){
            fibraControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
        }
        else{
            fibraControl.cboxConectarA.getSelectionModel().select(0);
        }
        
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
            if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                Fibra fib= (Fibra)controlador.getElementos().get(elemento);
                System.out.println(fib.getModo()+"\t"+fib.getLongitudOnda());
                
                if(fib.getTipo()==0){
                    fibraControl.rbtn28.setSelected(true);
                }else if(fib.getTipo()==1){
                    fibraControl.rbtn50.setSelected(true);
                }else if(fib.getTipo()==2){
                    fibraControl.rbtnOtro.setSelected(true);
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
            if("conector".equals(controlador.getElementos().get(elemento).getNombre()) ||
                    "empalme".equals(controlador.getElementos().get(elemento).getNombre())){
                fibraControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
            }
        }
    }
    
    private Line dibujarLinea(ElementoGrafico elemG) {
        linea = new Line();
        linea.setStartX(elemG.getDibujo().getLayoutX()+45);
        linea.setStartY(elemG.getDibujo().getLayoutY()+7);
        linea.setEndX(elemG.getComponente().getElementoConectadoSalida().getDibujo().getLayoutX());
        linea.setEndY(elemG.getComponente().getElementoConectadoSalida().getDibujo().getLayoutY());
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
