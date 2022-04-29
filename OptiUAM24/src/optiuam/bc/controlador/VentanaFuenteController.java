
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
import optiuam.bc.modelo.Fuente;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class VentanaFuenteController extends ControladorGeneral implements Initializable {
    
    static int idFuente = 0;
    ControladorGeneral controlador;
    Stage stage;
    ElementoGrafico elemG;
    VentanaFuenteController fuenteControl;
    static Line linea;
    static double posX, posY;
    
    @FXML
    RadioButton rbtn1310, rbtn1550, rbtnLaser, rbtnLed;
    
    @FXML
    TextField txtPotencia, txtAnchuraEspectro, txtVelocidad;
    
    @FXML
    Button btnPulso, btnCrear, btnDesconectar, btnCancelar, btnModificar;
    
    @FXML
    Label lblConectarA;
    
    @FXML
    ComboBox cboxConectarA;
    
    @FXML
    Separator separator;
    
    @FXML
    private Pane Pane1;

    @FXML
    private ScrollPane scroll;
    
    public static Line getLinea() {
        return linea;
    }

    public static void setLinea(Line linea) {
        VentanaFuenteController.linea = linea;
    }

    public static double getPosX() {
        return posX;
    }

    public static void setPosX(double posX) {
        VentanaFuenteController.posX = posX;
    }

    public static double getPosY() {
        return posY;
    }

    public static void setPosY(double posY) {
        VentanaFuenteController.posY = posY;
    }

    public void imprimir(ActionEvent event){
        int longitudOnda=0, tipo=0, id = 0;
        double potencia, anchura, velocidad;
        
        if(rbtnLaser.isSelected()){
            tipo = 0;
        }
        else if(rbtnLed.isSelected()){
            tipo = 1;
        }
        else if(rbtn1310.isSelected()){
            longitudOnda=1310;
            //System.out.println(1310);
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
            //System.out.println(1550);
        }
        
        potencia = Double.parseDouble(txtPotencia.getText());
        anchura = Double.parseDouble(txtAnchuraEspectro.getText());
        velocidad = Double.parseDouble(txtVelocidad.getText());
        
        if (txtPotencia.getText().compareTo("")==0 || !txtPotencia.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("Valor de la potencia invalido");
        }
        else if(txtAnchuraEspectro.getText().compareTo("")==0 || !txtAnchuraEspectro.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("Valor de la anchura invalido");
        }
        else if(txtVelocidad.getText().compareTo("")==0 || !txtVelocidad.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("Valor de la velocidad invalido");
        }
        else if((tipo==0 &&Double.parseDouble(txtAnchuraEspectro.getText())<=0) ||
           (tipo==0 &&Double.parseDouble(txtAnchuraEspectro.getText())>1)){
            System.out.println("\nEl valor de la anchura debe ser max 1 nm");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El valor de la anchura debe ser max 1 nm");
            alert.showAndWait();
        }
        else if((tipo == 1 &&Double.parseDouble(txtAnchuraEspectro.getText())< (double)(0.01)) ||
           (tipo==1 &&Double.parseDouble(txtAnchuraEspectro.getText())> 1)){
            System.out.println("\nEl valor de la anchura debe ser min: 0.01 nm  max: 1.0 nm");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El valor de la anchura debe ser min: 0.01 nm  max: 1.0 nm");
            alert.showAndWait();
        } 
        else{
            Fuente fuente= new Fuente();
            fuente.setAnchura(anchura);
            fuente.setIdFuente(idFuente);
            fuente.setLongitudOnda(longitudOnda);
            fuente.setNombre("fuente");
            fuente.setPotencia(potencia);
            fuente.setTipo(tipo);
            fuente.setVelocidad(velocidad);
            fuente.setConectadoEntrada(false);
            fuente.setConectadoSalida(false);
            guardarFuente(fuente);
            //System.out.println(fuente);
            idFuente++;
            cerrarVentana(event);
        }

    }
    
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage s = (Stage) source.getScene().getWindow();
        s.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnPulso.setVisible(false);
        separator.setVisible(false);
        btnDesconectar.setVisible(false);
        lblConectarA.setVisible(false);
        cboxConectarA.setVisible(false);
        btnModificar.setVisible(false);
    }    
    
    @FXML
    private void Desconectar(ActionEvent event){
        for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
                if(fuenteControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                    Componente poyo= controlador.getElementos().get(elemento2);
                    poyo.setConectadoEntrada(false);
                    poyo.setElementoConectadoEntrada("");
                    System.out.println(poyo.getNombre());
                    break;
                }
        }
                
        fuenteControl.cboxConectarA.getSelectionModel().select(0);
        elemG.getComponente().setConectadoSalida(false);
        elemG.getComponente().setElementoConectadoSalida("");
        
        //elemG.getComponente().getElementoConectadoEntrada().getComponente().setConectadoEntrada(false);
        //elemG.getComponente().getElementoConectadoEntrada().getComponente().setElementoConectadoEntrada(null);
        elemG.getComponente().getLinea().setVisible(false);
        cerrarVentana(event);
    }
    
    @FXML
    private void modificar(ActionEvent event){
        Fuente aux = (Fuente) elemG.getComponente();
        int longitudOnda=0, tipo=0, id = 0;
        double potencia, anchura, velocidad;
        boolean cEntrada, cSalida;

        if(rbtnLaser.isSelected()){
            tipo = 0;
        }
        else if(rbtnLed.isSelected()){
            tipo = 1;
        }
        else if(rbtn1310.isSelected()){
            longitudOnda=1310;
            //System.out.println(1310);
        }else if(rbtn1550.isSelected()){
            longitudOnda=1550;
            //System.out.println(1550);
        }
        if((fuenteControl.cboxConectarA.getSelectionModel().getSelectedIndex())==0){
            Desconectar(event);
            //aux.setElementoConectadoSalida(fuenteControl.cboxConectarA.getId().toString());
        }else{
            if(aux.isConectadoSalida()){
                //Desconectar();
            }
            aux.setConectadoSalida(true);

            for(int elemento2=0; elemento2<controlador.getDibujos().size();elemento2++){
                if(fuenteControl.cboxConectarA.getSelectionModel().getSelectedItem().toString().equals(controlador.getDibujos().get(elemento2).getDibujo().getText())){
                    ElementoGrafico poyo= controlador.getDibujos().get(elemento2);
                    aux.setElementoConectadoSalida(poyo.getDibujo().getText());
                    aux.setConectadoSalida(true);
                    System.out.println(controlador.getDibujos().get(elemento2).getComponente().toString());
                    
                    poyo.getComponente().setElementoConectadoEntrada(elemG.getDibujo().getText());
                    poyo.getComponente().setConectadoEntrada(true);
                    //System.out.println(poyo.getComponente().getElementoConectadoEntrada().getDibujo().getText());
                    //System.out.println(fuenteControl.cboxConectarA.getSelectionModel().getSelectedItem().toString());
                    //System.out.println(controlador.getDibujos().get(elemento2).getDibujo().getText());
                    break;
                }
            }
            dibujarLinea(elemG);
            //System.out.println(fuenteControl.cboxConectarA.getSelectionModel().getSelectedItem().toString());
            //aux.setElementoConectadoSalida(fuenteControl.cboxConectarA.);
        }
        potencia = Double.parseDouble(txtPotencia.getText());
        anchura = Double.parseDouble(txtAnchuraEspectro.getText());
        velocidad = Double.parseDouble(txtVelocidad.getText());

        if (txtPotencia.getText().compareTo("")==0 || !txtPotencia.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("Valor de la potencia invalido");
        }
        else if(txtAnchuraEspectro.getText().compareTo("")==0 || !txtAnchuraEspectro.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("Valor de la anchura invalido");
        }
        else if(txtVelocidad.getText().compareTo("")==0 || !txtVelocidad.getText().matches("[+-]?\\d*(\\.\\d+)?")){
            System.out.println("Valor de la velocidad invalido");
        }
        else if((tipo==0 &&Double.parseDouble(txtAnchuraEspectro.getText())<=0) ||
           (tipo==0 &&Double.parseDouble(txtAnchuraEspectro.getText())>1)){
            System.out.println("\nEl valor de la anchura debe ser max 1 nm");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El valor de la anchura debe ser max 1 nm");
            alert.showAndWait();
        }
        else if((tipo == 1 &&Double.parseDouble(txtAnchuraEspectro.getText())< (double)(0.01)) ||
           (tipo==1 &&Double.parseDouble(txtAnchuraEspectro.getText())> 1)){
            System.out.println("\nEl valor de la anchura debe ser min: 0.01 nm  max: 1.0 nm");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El valor de la anchura debe ser min: 0.01 nm  max: 1.0 nm");
            alert.showAndWait();
        } 
        else{
            aux.setAnchura(anchura);
            //aux.setIdFuente(idFuente);
            aux.setLongitudOnda(longitudOnda);
            aux.setNombre("fuente");
            aux.setPotencia(potencia);
            aux.setTipo(tipo);
            aux.setVelocidad(velocidad);

            //System.out.println(fuente);
            cerrarVentana(event);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("\n¡Fuente modificada!");
            alert.showAndWait();

            System.out.println(aux.toString());
            for(int h=0; h<controlador.getElementos().size(); h++){
                System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
                System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getDibujo().getText());
            }
        }
    }
    
    @FXML
    private void configurarPulso(ActionEvent event) throws IOException {
        //System.out.println("Pulso");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("VentanaPulso.fxml"));
            Scene scene = new Scene(root);
            Image ico = new Image("images/acercaDe.png");
            Stage st = new Stage(StageStyle.UTILITY);
            st.getIcons().add(ico);
            st.setTitle("OptiUAM BC Pulse Configuation");
            st.setScene(scene);
            st.showAndWait();
            st.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(VentanaFuenteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void init(ControladorGeneral controlador, Stage stage, Pane Pane1,ScrollPane scroll) {
        this.controlador=controlador;
        this.stage=stage;
        this.Pane1=Pane1;
        this.scroll=scroll;
    }

    private void guardarFuente(Fuente fuente) {
        fuente.setId(controlador.getContadorElemento());
        controlador.getElementos().add(fuente);
        Label dibujo= new Label();
        ElementoGrafico elem= new ElementoGrafico();
        
        fuente.setPosX(dibujo.getLayoutX());
        fuente.setPosY(dibujo.getLayoutY());
        setPosX(fuente.getPosX());
        setPosY(fuente.getPosY());
        System.out.println("Coordenada inicial: "+getPosX()+", "+getPosY());
        
        elem.setComponente(fuente);
        elem.setId(controlador.getContadorElemento());
        
        dibujo.setGraphic(new ImageView(new Image("images/dibujo_fuente.png")));
        dibujo.setText(fuente.getNombre() + "_"+ fuente.getIdFuente());
        dibujo.setContentDisplay(ContentDisplay.TOP);
        elem.setDibujo(dibujo);
        controlador.getDibujos().add(elem);
        eventos(elem);
        Pane1.getChildren().add(elem.getDibujo());
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("\n¡Fuente creada!");
        alert.showAndWait();
    }

    private void eventos(ElementoGrafico elem) {
        elem.getDibujo().setOnMouseDragged((MouseEvent event) -> {
                if(event.getButton()==MouseButton.PRIMARY){
                    double x=scroll.getHvalue();
                    double y= Pane1.getLayoutY();
                    //System.out.println(Pane1.getLayoutX()+" "+Pane1.getLayoutY()+" sc "+scroll.getHvalue());
                    if(elem.getDibujo().getLayoutX()>=0.0){
                        elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                        elem.getDibujo().setLayoutX((scroll.getHvalue()*200)+event.getSceneX()-20);
                    }else{
                        elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                        elem.getDibujo().setLayoutX(0.0);
                    }
                    if(elem.getDibujo().getLayoutY()>=0.0){
                        elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                        elem.getDibujo().setLayoutY((scroll.getVvalue()*200)+event.getSceneY()-175);
                    }else{
                        elem.getDibujo().setCursor(Cursor.CLOSED_HAND);
                        elem.getDibujo().setLayoutY(0);
                    }
                    
                    //setPosX(event.getX());
                    //setPosY(event.getY());
                    //System.out.println("Coordenadas fuente: " + getPosX()+" ,"+getPosY());
                    //dibujarLinea(elem);
                    if(elem.getComponente().isConectadoSalida()==true){
                        borrarLinea(elem.getComponente().getLinea());
                        dibujarLinea(elem);
                    }
                }
        });
            elem.getDibujo().setOnMouseEntered((MouseEvent event) -> {
                elem.getDibujo().setStyle("-fx-border-color: darkblue;");
                elem.getDibujo().setCursor(Cursor.OPEN_HAND);
                //Esta cochinada no sirve :c
                Tooltip tt= new Tooltip();
                for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                    Fuente fue= (Fuente)controlador.getElementos().get(elemento);
                    
                    String name= "NOMBRE: "+fue.getNombre();
                    String id= "ID= "+fue.getIdFuente();
                    String conE= "Entrada:"+fue.getElementoConectadoEntrada();
                    String conS= "Salida:"+fue.getElementoConectadoSalida();
                    tt.setText(name);
                    //elem.getDibujo().setTooltip(tt);
                    Tooltip.install(elem.getDibujo(), tt);
                }
               
                
            }
        });
            elem.getDibujo().setOnMouseExited((MouseEvent event) -> {
                elem.getDibujo().setStyle("");
        });
            elem.getDibujo().setOnMouseClicked((MouseEvent event) -> {
                if(event.getButton()==MouseButton.PRIMARY){
                    //System.out.println("Hola fuente"+elem.getId());
                    try{
                        Stage stage1 = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaFuente.fxml"));
                        Parent root = loader.load();
                        //Se crea una instancia del controlador del conector
                        VentanaFuenteController fuenteController = (VentanaFuenteController) loader.getController();
                        //fuenteController.init(controlador, stage, Pane1);
                        /*Se necesito usar otro init de forma que el controller sepa cual es el elemento
                            con el que se esta trabajando ademas de que se manda el mismo controller para 
                            iniciar con los valores del elemento mandado.
                        */
                        fuenteController.init(controlador, this.stage, this.Pane1,this.scroll);
                        fuenteController.init2(elem,fuenteController);
                        fuenteController.btnCrear.setVisible(false);
                        fuenteController.btnPulso.setVisible(true);
                        fuenteController.separator.setVisible(true);
                        fuenteController.btnDesconectar.setVisible(true);
                        fuenteController.lblConectarA.setVisible(true);
                        fuenteController.cboxConectarA.setVisible(true);
                        fuenteController.btnModificar.setVisible(true);
                        
                        Scene scene = new Scene(root);
                        Image ico = new Image("images/acercaDe.png");
                        stage1.getIcons().add(ico);
                        stage1.setTitle("OptiUAM BC Fuente");
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
        Tooltip tt= new Tooltip();
        // create menuitems
        MenuItem menuItem1 = new MenuItem("-Duplicar");
        //MenuItem menuItem2 = new MenuItem("-Girar");
        MenuItem menuItem3 = new MenuItem("-Eliminar");
        MenuItem menuItem4 = new MenuItem("-Propiedades");

        menuItem1.setOnAction(e ->{
            System.out.println("Duplicar");
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    System.out.println(dibujo.getId()+"----"+controlador.getElementos().get(elemento).getId());
                    Fuente fuenteAux=new Fuente();
                    Fuente fuenteAux1=(Fuente)controlador.getElementos().get(elemento);
                    fuenteAux.setAnchura(fuenteAux1.getAnchura());
                    fuenteAux.setConectadoEntrada(false);
                    fuenteAux.setConectadoSalida(false);
                    fuenteAux.setLongitudOnda(fuenteAux1.getLongitudOnda());
                    fuenteAux.setPotencia(fuenteAux1.getPotencia());
                    fuenteAux.setTipo(fuenteAux1.getTipo());
                    fuenteAux.setNombre("fuente");
                    fuenteAux.setPulso(fuenteAux1.getA0(),fuenteAux1.getT0(),fuenteAux1.getW0(),fuenteAux1.getC(),fuenteAux1.getM());
                    fuenteAux.setVelocidad(fuenteAux1.getVelocidad());
                    fuenteAux.setIdFuente(idFuente);
                    guardarFuente(fuenteAux);
                    System.out.println(fuenteAux);
                    idFuente++;
                    break;
                }
            }
        });
        
        menuItem4.setOnAction(e ->{
            //Tooltip tt= new Tooltip();
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Fuente fue= (Fuente)controlador.getElementos().get(elemento);
                    
                    String name= "NOMBRE: "+fue.getNombre();
                    String id= "ID= "+fue.getIdFuente();
                    String conE= "Entrada:"+fue.getElementoConectadoEntrada();
                    String conS= "Salida:"+fue.getElementoConectadoSalida();
                    tt.setText(name+"\n"+id+"\n"+conE+"\n"+conS);
                    System.out.println(tt.getText());
                dibujo.getDibujo().setTooltip(tt);
                }
            }
                
        });

        menuItem3.setOnAction(e ->{
            for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
                if(dibujo.getId()==controlador.getElementos().get(elemento).getId()){
                    Fuente aux= (Fuente)controlador.getElementos().get(elemento);
                    controlador.getDibujos().remove(dibujo);
                    controlador.getElementos().remove(aux); 
                }
            }    
            if(dibujo.getComponente().isConectadoSalida()==true){
                borrarLinea(linea);
            }
            dibujo.getDibujo().setVisible(false);

        });

        // add menu items to menu
        contextMenu.getItems().add(menuItem1);
        //contextMenu.getItems().add(menuItem2);
        contextMenu.getItems().add(menuItem3);
        contextMenu.getItems().add(menuItem4);
        dibujo.getDibujo().setContextMenu(contextMenu);
        //dibujo.getDibujo().setTooltip(tt);
    }
        
    private void init2(ElementoGrafico elem, VentanaFuenteController fuenteController) {
        this.elemG = elem;
        this.fuenteControl=fuenteController;
        //this.scroll=scroll;
        fuenteControl.cboxConectarA.getItems().add("Desconected");
        if(elemG.getComponente().isConectadoSalida()==true){
            fuenteControl.cboxConectarA.getSelectionModel().select(elemG.getComponente().getElementoConectadoSalida());
        }else{
            fuenteControl.cboxConectarA.getSelectionModel().select(0);
        }
        
        for(int elemento=0; elemento<controlador.getElementos().size(); elemento++){
           
            if(elem.getId()==controlador.getElementos().get(elemento).getId()){
                Fuente fue = (Fuente)controlador.getElementos().get(elemento);
                System.out.println(fue.getTipo()+"\t"+fue.getLongitudOnda());
                if(fue.getTipo()==0){
                    fuenteControl.rbtnLaser.setSelected(true);
                }else if(fue.getTipo()==1){
                    fuenteControl.rbtnLed.setSelected(true);
                }
                if(fue.getLongitudOnda()==1310){
                    fuenteControl.rbtn1310.setSelected(true);
                }else if(fue.getLongitudOnda()==1550){
                    fuenteControl.rbtn1550.setSelected(true);
                }
                fuenteControl.txtAnchuraEspectro.setText(String.valueOf(fue.getAnchura()));
                fuenteControl.txtPotencia.setText(String.valueOf(fue.getPotencia()));
                fuenteControl.txtVelocidad.setText(String.valueOf(fue.getVelocidad()));
            }
            if("conector".equals(controlador.getElementos().get(elemento).getNombre())){
                if(!controlador.getElementos().get(elemento).isConectadoEntrada()){
                    fuenteControl.cboxConectarA.getItems().add(controlador.getDibujos().get(elemento).getDibujo().getText());
                }
            }
        }
    }

    private void dibujarLinea(ElementoGrafico elemG) {
        Line line= new Line();   
        line.setStartX(elemG.getDibujo().getLayoutX()+45);
        line.setStartY(elemG.getDibujo().getLayoutY()+7);
        ElementoGrafico aux= new ElementoGrafico();
        for(int it=0; it<controlador.getDibujos().size();it++){
            if(elemG.getComponente().getElementoConectadoSalida()==controlador.getDibujos().get(it).getDibujo().getText()){
                aux=controlador.getDibujos().get(it);
            }
        }
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);
        line.setEndX(aux.getDibujo().getLayoutX());
        line.setEndY(aux.getDibujo().getLayoutY());
        setLinea(line);
        //System.out.println("Se dibujo una linea");
        line.setVisible(true);
        Pane1.getChildren().add(line); 
        elemG.getComponente().setLinea(line);
              
    }
    
    private void borrarLinea(Line line){
        line.setVisible(false);
    }
    
}
