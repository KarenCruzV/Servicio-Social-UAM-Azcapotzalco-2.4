/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.modelo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private Pane panel; // panel para dibujar el conector
    private String id; // identificador del dibujo, el mismo que el de el componente
    private String componente; // identificador del componente

    public ControladorGeneral getControlador() {
        return controlador;
    }

    public void setControlador(ControladorGeneral controlador) {
        this.controlador = controlador;
    }

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

    public Pane getPanel() {
        return panel;
    }

    public void setPanel(Pane panel) {
        this.panel = panel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public ElementoGrafico(ControladorGeneral controlador, Label dibujo, Label title, Pane panel, String id, String componente) {
        this.controlador = controlador;
        this.dibujo = dibujo;
        this.title = title;
        this.panel = panel;
        this.id = id;
        this.componente = componente;
    }

    public ElementoGrafico() {
    }
    
}
