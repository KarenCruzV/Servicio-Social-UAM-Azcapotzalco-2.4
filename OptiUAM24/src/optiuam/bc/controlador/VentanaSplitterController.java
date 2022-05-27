
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
import optiuam.bc.modelo.Splitter;

/**
 * FXML Controller class
 *
 * @author j
 */
public class VentanaSplitterController extends ControladorGeneral implements Initializable {
    
    static int idS = 0;
    ControladorGeneral controlador;
    Stage stage;
    ElementoGrafico elemG;
    VentanaSplitterController splitterControl;
    static double posX, posY;
    
    @FXML
    RadioButton rbtn1310, rbtn1550;
    
    @FXML
    ComboBox cboxNumeroSalidas, cboxSalidas, cboxConectarA;
    
    @FXML
    Button btnConectar, btnDesconectar, btnCrear, btnCancelar, btnModificar;
    
    @FXML
    TextField txtPerdidaInsercion;
    
    @FXML
    Label lblSalida, lblConectarA;
    
    @FXML
    Separator separator;
    
    @FXML
    private Pane Pane1;
    
    @FXML
    private ScrollPane scroll;
    
    private final String perdidasValidas[][] = {{"1,0", "2.7", "4.0"},   //2
                                                {"1,1", "5.3", "7.6"},   //4
                                                {"1,2", "7.9", "10.9"},  //8
                                                {"1,3", "10.5", "14.5"}, //16
                                                {"1,4", "12.8", "18.1"}, //32
                                                {"1,5", "15.5", "21.5"}};//64

    public static double getPosX() {
        return posX;
    }

    public static void setPosX(double posX) {
        VentanaSplitterController.posX = posX;
    }

    public static int getIdS() {
        return idS;
    }

    public static void setIdS(int idS) {
        VentanaSplitterController.idS = idS;
    }

    public static double getPosY() {
        return posY;
    }

    public static void setPosY(double posY) {
        VentanaSplitterController.posY = posY;
    }
    
    public boolean validarPerdida(double perdida,int salidas) {
        for (String[] perdidasValida : perdidasValidas) {
            if (perdidasValida[0].compareTo(String.valueOf("1") + "," + String.valueOf(salidas)) == 0) {
                return perdida >= Double.parseDouble(perdidasValida[1]) && perdida <= Double.parseDouble(perdidasValida[2]);
            }
        }
        return false;
    }
    
    public String buscarPerdidas(int salidas) {
        for (String[] perdidasValida : perdidasValidas) {
            if (perdidasValida[0].compareTo(String.valueOf("1") + "," + String.valueOf(salidas)) == 0) {
                return "min: " + String.valueOf(perdidasValida[1]) + ", max: " + String.valueOf(perdidasValida[2]);
            }
        }
        return "";
    }
    
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
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
        btnConectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        lblSalida.setVisible(false);
        cboxSalidas.setVisible(false);
        btnModificar.setVisible(false);
    }  
    
    @FXML
    public void Desconectar(ActionEvent event){
        for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
                if(splitterControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                    Componente comp= controlador.getElementos().get(elemento2);
                    comp.setConectadoEntrada(false);
                    comp.setElementoConectadoEntrada("");
                    System.out.println(comp.getNombre());
                    break;
                }
        }
        splitterControl.cboxConectarA.getSelectionModel().select(0);
        if(elemG.getComponente().isConectadoSalida()){
        elemG.getComponente().setConectadoSalida(false);
        elemG.getComponente().setElementoConectadoSalida("");
        elemG.getComponente().getLinea().setVisible(false);
        }
        cerrarVentana(event);
    }
    
    public void imprimir(ActionEvent event){
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
        
        perdida = Double.parseDouble(txtPerdidaInsercion.getText());
        txtPerdidaInsercion.setText(String.valueOf(perdida));
        cboxSalidas.getItems().removeAll(cboxSalidas.getItems());
        for(int i = 0; i<salidas;i++){
            cboxSalidas.getItems().addAll(String.valueOf(i+1));
            cboxSalidas.getSelectionModel().selectFirst();
        }
        
        if (!validarPerdida(Double.parseDouble(txtPerdidaInsercion.getText()),cboxNumeroSalidas.getSelectionModel().getSelectedIndex())) {
           System.out.println("The loss must be " + buscarPerdidas(cboxNumeroSalidas.getSelectionModel().getSelectedIndex()));
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nThe loss must be " +  buscarPerdidas(cboxNumeroSalidas.getSelectionModel().getSelectedIndex()));
            alert.showAndWait();
        }
        else{
            Splitter s= new Splitter();
            s.setConectadoEntrada(false);
            s.setConectadoSalida(false);
            s.setPerdidaInsercion(perdida);
            s.setSalidas(salidas);
            s.setLongitudOnda(longitudOnda);
            s.setNombre("splitter");
            s.setIdS(idS);
            idS++;
            guardarSplitter(s);
            cerrarVentana(event);
        }
        
    }
    
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
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\nSplitter created!");
        alert.showAndWait();
        
        Tooltip proSplitter = new Tooltip();
        proSplitter.setText("Name: "+s.getNombre()+
            "\nId = "+s.getIdS()+
            "\nInput: "+s.getElementoConectadoEntrada()+
            "\nOutput :"+s.getElementoConectadoSalida()+
            "\nWavelenght: "+s.getLongitudOnda()+" nm"+
            "\nNumber of outputs: "+s.getSalidas()+
            "\nInsertion Loss: "+s.getPerdidaInsercion()+" dB");
        elem.getDibujo().setTooltip(proSplitter);
    }

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
                    elem.getDibujo().setLayoutY(Pane1.getChildren().get(j).getLayoutY()+event.getY()+1);}
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
                    try{
                        Stage stage1 = new Stage(StageStyle.UTILITY);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaSplitter.fxml"));
                        Parent root = loader.load();
                        
                        //Se crea una instancia del controlador del conector
                        VentanaSplitterController splitterController = (VentanaSplitterController) loader.getController();
                        splitterController.init(controlador, stage, Pane1, scroll);
                        //splitterController.init(controlador, this.stage, this.Pane1);
                        /*Se necesito usar otro init de forma que el controller sepa cual es el elemento
                            con el que se esta trabajando ademas de que se manda el mismo controller para 
                            iniciar con los valores del elemento mandado.
                        */
                        splitterController.init2(elem,splitterController);
                        splitterController.btnCrear.setVisible(false);
                        splitterController.separator.setVisible(true);
                        splitterController.btnConectar.setVisible(true);
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
                    mostrarMenuChiquito(elem);
                }
        });
    }
    
    public void mostrarMenuChiquito(ElementoGrafico dibujo){
        // create a menu
        ContextMenu contextMenu = new ContextMenu();

        // create menuitems
        MenuItem menuItem1 = new MenuItem("-Duplicated");
        MenuItem menuItem3 = new MenuItem("-Deleted");

        menuItem1.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    //System.out.println(dibujo.getId()+"----"+controlador.getElementos().get(elemento).getId());
                    Splitter aux=new Splitter();
                    Splitter aux1=(Splitter)controlador.getElementos().get(elemento);
                    aux.setConectadoEntrada(false);
                    aux.setConectadoSalida(false);
                    aux.setLongitudOnda(aux1.getLongitudOnda());
                    aux.setNombre(aux1.getNombre());
                    aux.setPerdidaInsercion(aux1.getPerdidaInsercion());
                    aux.setSalidas(aux1.getSalidas());
                    aux.setIdS(idS);
                    guardarSplitter2(aux,dibujo);
                    //System.out.println(aux);
                    idS++;
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
                    Splitter aux= (Splitter)controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux); 
                }
            }    
            
            dibujo.getDibujo().setVisible(false);

        });

        // add menu items to menu
        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem3);
        dibujo.getDibujo().setContextMenu(contextMenu);
    }
    
     public void guardarSplitter2(Splitter s, ElementoGrafico el) {
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
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText("\n¡Splitter created!");
        alert.showAndWait();
        
        Tooltip proSplitter = new Tooltip();
        proSplitter.setText("Name: "+s.getNombre()+
            "\nId = "+s.getIdS()+
            "\nInput: "+s.getElementoConectadoEntrada()+
            "\nOutput :"+s.getElementoConectadoSalida()+
            "\nWavelenght: "+s.getLongitudOnda()+" nm"+
            "\nNumber of outputs: "+s.getSalidas()+
            "\nInsertion Loss: "+s.getPerdidaInsercion()+" dB");
        elem.getDibujo().setTooltip(proSplitter);
    }
    
    @FXML
    public void modificar(ActionEvent event){
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
            if(aux.isConectadoSalida()){}
            aux.setConectadoSalida(true);
            
            for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
                if(splitterControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
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
        perdida = Double.parseDouble(txtPerdidaInsercion.getText());
        txtPerdidaInsercion.setText(String.valueOf(perdida));
        cboxSalidas.getItems().removeAll(cboxSalidas.getItems());
        for(int i = 0; i<salidas;i++){
            cboxSalidas.getItems().addAll(String.valueOf(i+1));
            cboxSalidas.getSelectionModel().selectFirst();
        }
        if (!validarPerdida(Double.parseDouble(txtPerdidaInsercion.getText()),cboxNumeroSalidas.getSelectionModel().getSelectedIndex())) {
           System.out.println("The loss must be " + buscarPerdidas(cboxNumeroSalidas.getSelectionModel().getSelectedIndex()));
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\nThe loss must be " +  buscarPerdidas(cboxNumeroSalidas.getSelectionModel().getSelectedIndex()));
            alert.showAndWait();
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
            
            aux.setPerdidaInsercion(perdida);
            aux.setSalidas(salidas);
            aux.setLongitudOnda(longitudOnda);
            aux.setNombre("splitter");
            cerrarVentana(event);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("\nModified splitter!");
            alert.showAndWait();
            
            Tooltip proSplitter = new Tooltip();
            proSplitter.setText("Name: "+aux.getNombre()+
                "\nId = "+aux.getIdS()+
                "\nInput: "+aux.getElementoConectadoEntrada()+
                "\nOutput :"+aux.getElementoConectadoSalida()+
                "\nWavelenght: "+aux.getLongitudOnda()+" nm"+
                "\nNumber of outputs: "+aux.getSalidas()+
                "\nInsertion Loss: "+aux.getPerdidaInsercion()+" dB");
            elemG.getDibujo().setTooltip(proSplitter);

            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
            }
        }
    }
    
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
                System.out.println(spl.getSalidas()+"\t"+spl.getLongitudOnda());
                /*No se como poner lo de las salidas xd
                Actualizacion de hoy 21/05 xd no se si esta bien jsjs*/
                splitterControl.cboxSalidas.getItems().add("Empty");
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
            }
        }
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
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        elemG.getComponente().setLinea(line);    
    }
    
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
    
    public void borrarLinea(Line linea){
        linea.setVisible(false);
    }
    
     public boolean outSideParentBoundsX( Bounds childBounds, double newX, double newY) {

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

    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1, ScrollPane scroll) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.scroll=scroll;
    }
    
}
