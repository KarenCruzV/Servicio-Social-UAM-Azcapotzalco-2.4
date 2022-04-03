/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.modelo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private int id; // identificador del dibujo, el mismo que el de el componente
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public ElementoGrafico(ControladorGeneral controlador, Pane panel, int id, String componente) {
        this.controlador = controlador;
        this.panel = panel;
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
    
    public void dibujarComponente(){
        
        if (componente.compareTo("splitter64")==0){
            dibujo.setGraphic(new ImageView(new Image("iconos/dibujo_splitter64.png")));
            //dibujo.setIcon(new ImageIcon("iconos/dibujo_splitter64.png"));
            //dibujo.setBounds(10,20,100,75);
            dibujo.getBoundsInLocal();
        }
        else if(componente.compareTo("splitter128")==0){
            dibujo.setGraphic(new ImageView(new Image("iconos/dibujo_splitter128.png")));
            //dibujo.setBounds(10,20,150,113);
            dibujo.getBoundsInLocal();
        }
        else if(componente.compareTo("splitter32")==0){
            dibujo.setGraphic(new ImageView(new Image("iconos/dibujo_splitter32.png")));
            //dibujo.setBounds(10,20,150,113);
            dibujo.getBoundsInLocal();
        }
        else if(componente.compareTo("splitter16")==0){
            dibujo.setGraphic(new ImageView(new Image("iconos/dibujo_splitter16.png")));
            //dibujo.setBounds(10,20,150,113);
            dibujo.getBoundsInLocal();
        }
        else if(componente.compareTo("fibra")==0){
            dibujo.setGraphic(new ImageView(new Image("iconos/dibujo_fibra.png")));
            //dibujo.setBounds(10,20,150,113);
            dibujo.getBoundsInLocal();
        }
        else if(componente.compareTo("conector")==0){
            dibujo.setGraphic(new ImageView(new Image("iconos/dibujo_conectorR.png")));
            //dibujo.setBounds(10,20,150,113);
            dibujo.getBoundsInLocal();
        }
        else if(componente.compareTo("empalme")==0){
            dibujo.setGraphic(new ImageView(new Image("iconos/dibujo_empalme.png")));
            //dibujo.setBounds(10,20,150,113);
            dibujo.getBoundsInLocal();
        }
        
        else{
            dibujo.setGraphic(new ImageView(new Image("iconos/dibujo_"+componente+".png")));
            //dibujo.setBounds(10,20,150,113);
            dibujo.getBoundsInLocal();;
        }
        //dibujo.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        panel.getChildren().add(dibujo);
        dibujo.setVisible(true);
        
        /*MouseAdapter ml =new MouseAdapter() {
            int x_pressed = 0;
            int y_pressed = 0;
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                //dibujarContorno(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                //borrarContorno(evt);
            }

            public void mousePressed(MouseEvent me) {
                x_pressed = (int) me.getX();
                y_pressed = (int) me.getY();
            }
            
            @Override
            public void mouseDragged(MouseEvent me) {
       
                    dibujo.setLocation(me.getXOnScreen()-x_pressed, me.getYOnScreen()- y_pressed-150);
                //dibujo.repaint();
                //panel.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent me) {
                MenuEmergente menu = new MenuEmergente();
                if(me.getButton()==1){//click izquierdo
                        controlador.mostrarVentanaElemento(id);
                        dibujarContorno(me);
                }
                if(me.getButton()==3){//click derecho
                        menu.setVisible(true);
                        menu.setLocation(me.getXOnScreen()-x_pressed, me.getYOnScreen()- y_pressed-150);
                }
                
            }
           
        };
        
        dibujo.addMouseListener(ml);
        dibujo.addMouseMotionListener(ml);
        dibujo.setToolTipText(id);
        panel.updateUI();*/
    }
    
}
