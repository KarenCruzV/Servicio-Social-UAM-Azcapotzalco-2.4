
package optiuam.bc.modelo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import optiuam.bc.controlador.ControladorGeneral;

/**
 * Clase ElementoGrafico la cual contiene los atributos principales de un elemento grafico
 * @author Daniel Hernandez
 * Editado por:
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class ElementoGrafico {
    
    /**Controlador del elemento grafico*/
    private ControladorGeneral controlador; 
    
    /**Etiqueta para colocar el elemento grafico*/
    @FXML
    private Label dibujo; 
    
    /**Etiqueta del elemento grafico*/
    @FXML
    private Label title; 
    
    /**Identificador del componente*/
    private Componente componente; 
    
    /**Identificador del elemento grafico, es igual al identificador del componente*/
    @FXML                              
    private int id; 
 
    /**
     * Metodo constructor sin parametros
     */
    public ElementoGrafico() {
        
    }
    
    /**
     * Metodo constructor con parametros
     * @param controlador Controlador del elemento grafico
     * @param id Identificador del elemento grafico
     * @param componente Identificador del componente
     */
    public ElementoGrafico(ControladorGeneral controlador, int id, Componente componente) {
        this.controlador = controlador;
        this.id = id;
        this.componente = componente;
    }
    
    /**
     * Metodo que muestra el elemento grafico
     * @return dibujo
     */
    public Label getDibujo() {
        return dibujo;
    }

    /**
     * Metodo que modifica el elemento grafico
     * @param dibujo Etiqueta para colocar el elemento grafico
     */
    public void setDibujo(Label dibujo) {
        this.dibujo = dibujo;
    }

    /**
     * Metodo que muestra la etiqueta del elemento grafico
     * @return title
     */
    public Label getTitle() {
        return title;
    }

    /**
     * Metodo que modifica la etiqueta del elemento grafico
     * @param title Etiqueta del elemento grafico
     */
    public void setTitle(Label title) {
        this.title = title;
    }

    /**
     * Metodo que muestra el identificador del elemento grafico
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo que modifica el identificador del elemento grafico
     * @param id Identificador del elemento grafico
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metodo que muestra el identificador del componente
     * @return componente
     */
    public Componente getComponente() {
        return componente;
    }

    /**
     * Metodo que modifica el identificador del componente
     * @param componente Identificador del componente
     */
    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    /**
     * Metodo toString que retorna los atributos de un elemento grafico
     * @return controlador, dibujo, title, id, componente
     */
    @Override
    public String toString() {
        return dibujo.getText() +"," + id;
    }
    
}
