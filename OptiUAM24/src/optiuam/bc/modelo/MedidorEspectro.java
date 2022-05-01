
package optiuam.bc.modelo;

import java.util.ArrayList;

public class MedidorEspectro extends Componente {
    
    private char orientacion ='R';
    private int idEspectro;

    public int getIdEspectro() {
        return idEspectro;
    }

    public void setIdEspectro(int idEspectro) {
        this.idEspectro = idEspectro;
    }
    
    public MedidorEspectro(String name, int id,String elementoConectado, boolean conectado) {
        this.nombre = name;
        this.id = id;
    }

    public MedidorEspectro() {
    }
    
    public float[] calcularEspectro(){
        float valores[] = null;
        return valores;
    }
    
    

    /*Getter and Setter*/
    
    public char getOrientacion() {
        return orientacion;
    }
    
    public void setOrientacion(char orientacion) {
        this.orientacion = orientacion;
    }

    
    /*toString*/
    
    @Override
    public String toString() {
        return super.toString() + idEspectro/*orientacion*/; 
    }
    
}