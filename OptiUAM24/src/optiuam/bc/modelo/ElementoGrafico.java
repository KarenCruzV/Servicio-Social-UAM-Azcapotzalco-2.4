
package optiuam.bc.modelo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    
    @Override
    public String toString() {
        return "ElementoGrafico{" + "controlador=" + controlador + 
                ", dibujo=" + dibujo + ", title=" + title + ", id=" + id + 
                ", componente=" + componente + '}';
    }
    
}
