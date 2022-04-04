
package optiuam.bc.vista;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import optiuam.bc.controlador.ControladorGeneral;
import optiuam.bc.controlador.VentanaFibraController;
import optiuam.bc.modelo.Componente;
import optiuam.bc.modelo.ElementoGrafico;

public class VentanaPrincipal implements Initializable {
    
    @FXML
        private ImageView viewFibra,viewFuente, viewConector,viewSplitter,viewEmpalme, viewPotencia,viewEspectro;
    @FXML
        private Button btnFibra, btnFuente, btnConector, btnSplitter, btnEmpalme, btnPotencia, btnEspectro;
    
    private Image fibraI, fuenteI, conectorI, splitterI, empalmeI, potenciaI, espectroI, fondo;
    
    @FXML
    public Pane Pane1;
    static ControladorGeneral controlador= new ControladorGeneral();

    
    @FXML
    private void abrirVentanaFibra(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);
        Parent root = FXMLLoader.load(getClass().getResource("VentanaFibra.fxml"));
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);
        stage.setTitle("OptiUAM BC Fibra");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        leerAuxiliar();
        System.out.print(controlador.getContadorElemento());
        for(int h=0; h<controlador.getContadorElemento(); h++){
            System.out.print("\telemento: "+controlador.getElementos().get(h).toString());
            System.out.println("\tdibujo: "+controlador.getDibujos().get(h).getTitle());
        }
    }
    
    @FXML
    private void abrirVentanaFuente(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);
        Parent root = FXMLLoader.load(getClass().getResource("VentanaFuente.fxml"));
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);

        stage.setTitle("OptiUAM BC Fuente");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        System.out.println("hola");
        leerAuxiliar();
        
    }
    
    @FXML
    private void abrirVentanaSplitter(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);
        Parent root = FXMLLoader.load(getClass().getResource("VentanaSplitter.fxml"));
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);

        stage.setTitle("OptiUAM BC Splitter");
        stage.setScene(scene);
        stage.showAndWait();
        stage.setResizable(false);
        leerAuxiliar();
    }
    
    @FXML
    private void abrirVentanaConector(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);
        Parent root = FXMLLoader.load(getClass().getResource("VentanaConector.fxml"));
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);
        stage.setTitle("OptiUAM BC Conector");
        stage.setScene(scene);
        stage.showAndWait();
        stage.setResizable(false);
        leerAuxiliar();
    }
    
    @FXML
    private void abrirVentanaEmpalme(ActionEvent event) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);;
        Parent root = FXMLLoader.load(getClass().getResource("VentanaEmpalme.fxml"));
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        stage.getIcons().add(ico);

        stage.setTitle("OptiUAM BC Empalme");
        stage.setScene(scene);
        stage.showAndWait();
        stage.setResizable(false);
        leerAuxiliar();
    }
    
    @FXML
    private void abrirVentanaPotencia(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("VentanaPotencia.fxml"));
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.getIcons().add(ico);

        stage.setTitle("OptiUAM BC Medidor Potencia");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
    
    @FXML
    private void abrirVentanaEspectro(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("VentanaEspectro.fxml"));
        Scene scene = new Scene(root);
        Image ico = new Image("images/acercaDe.png"); 
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.getIcons().add(ico);

        stage.setTitle("OptiUAM BC Medidor Espectro");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
    @FXML
    public void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        
    }
    private void leerAuxiliar() throws FileNotFoundException{
        File doc =
        new File("auxiliar.txt");
        Scanner obj = new Scanner(doc);
        Componente componente= new Componente();
        StringTokenizer st = new StringTokenizer(obj.nextLine(),",");
        String nombre= st.nextToken();
        componente.setNombre(nombre);
        controlador.setContadorElemento(controlador.getContadorElemento()+1);
        componente.setId(controlador.getContadorElemento());
        int id=Integer.parseInt(st.nextToken());
        componente.setConectado(Boolean.parseBoolean(st.nextToken()));
        controlador.getElementos().add(componente);
        
        Label dibujo= new Label();
        Label title= new Label();
        if(nombre.equals("fibra")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_fibra.png")));
        }else if(nombre.equals("fuente")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_fuenteR.png")));
        }
        else if(nombre.equals("conector")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_conectorR.png")));
        }else if(nombre.equals("empalme")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_empalme.png")));
        }
        else if(nombre.equals("splitter16")){
            dibujo.setGraphic(new ImageView(new Image("images/dibujo_splitter16.png")));
            dibujo.setLayoutX(100);
            dibujo.setLayoutY(100);
        }
        title.setText(nombre);
        //ElementoGrafico grafico= new ElementoGrafico();
        //grafico.setComponente(nombre);
        //grafico.setId(cont.getContadorElemento()+1);
        //cont.getDibujos().add(grafico);
        ElementoGrafico elem= new ElementoGrafico();
        elem.setComponente(nombre);
        elem.setDibujo(dibujo);
        elem.setTitle(title);
        elem.setId(controlador.getContadorElemento());
        controlador.getDibujos().add(elem);
        Pane1.getChildren().add(dibujo);
        
        
    }
    
    public void añadirControlador(){
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fibraI=new Image("images/ico_fibra.png"); 
        fuenteI=new Image("images/ico_fuente.png"); 
        conectorI=new Image("images/ico_conector.png"); 
        potenciaI=new Image("images/ico_potencia.png"); 
        espectroI=new Image("images/ico_espectro.png"); 
        empalmeI=new Image("images/ico_empalme.png"); 
        splitterI=new Image("images/ico_splitter.png"); 
        //fondo= new Image("/images/fondo.png"); 
        
        viewFibra.setImage(fibraI);
        viewFuente.setImage(fuenteI);
        viewConector.setImage(conectorI);
        viewPotencia.setImage(potenciaI);
        viewEspectro.setImage(espectroI);
        viewEmpalme.setImage(empalmeI);
        viewSplitter.setImage(splitterI);
    }    

    

}
