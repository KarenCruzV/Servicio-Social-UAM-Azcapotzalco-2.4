/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.modelo;

import java.awt.event.MouseAdapter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import optiuam.bc.controlador.ControladorGeneral;

/**
 *
 * @author Arturo Borja
 */
public class ElementoGrafico {
    private ControladorGeneral controlador; 
    @FXML
    private Label dibujo; //etiqueta para colocar el elemento
    @FXML
    private Label title; //etiqueta del elemento.
    @FXML                              
    private int id; // identificador del dibujo, el mismo que el de el componente
    private Componente componente; // identificador del componente

    public Label getDibujo() {
        return dibujo;
    }

    public void setDibujo(Label dibujo) {
        this.dibujo = dibujo;
    }

    public Label getTitle() {
        return title;
    }

    public void setTitle(Label title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    public ElementoGrafico(ControladorGeneral controlador, int id, Componente componente) {
        this.controlador = controlador;
        this.id = id;
        this.componente = componente;
    }

    public ElementoGrafico() {
        
    }
    
    public void dibujarFibra(){
        //dibujo.setGraphic(new ImageView(new Image("iconos/dibujo_fibra.png")));
        /*Image image1 = new Image(getClass().getResourceAsStream("iconos/dibujo_fibra.png"));
        ImageView imageView1 = new ImageView(image1);
        imageView1.setX(50);
        imageView1.setY(50);
        Pane1.getChildren().add(imageView1);*/
        Pane pane= new Pane();
        Image image = new Image(getClass().getResourceAsStream("/images/dibujo_fibra.png"));
        ImageView imgView = new ImageView(image);
        pane.getChildren().add(imgView);
        
    }
    
    public void dibujarConector(){}
    
    public void dibujarEmpalme() {}
    
    public void dibujarFuente() {}
    
    public void dibujarSplitter() {}
    
    

    @Override
    public String toString() {
        return "ElementoGrafico{" + "controlador=" + controlador + ", dibujo=" + dibujo + ", title=" + title + ", id=" + id + ", componente=" + componente + '}';
    }
    
}
