
package optiuam.bc.modelo;

import optiuam.bc.controlador.ControladorGeneral;

public class MedidorPotencia extends Componente {
    
    private Double sensibilidad = 0.0;
    private int idPotencia;
    ControladorGeneral c;

    public int getIdPotencia() {
        return idPotencia;
    }

    public void setIdPotencia(int idPotencia) {
        this.idPotencia = idPotencia;
    }
    
    public MedidorPotencia(String name, int id,String elementoConectado, boolean conectado) {
        this.nombre = name;

        this.id = id;
    }

    public MedidorPotencia() {
    }
    
    public double calcularPerdidas(){
        return  0.0;
    }

    /*toString*/
    
    @Override
    public String toString() {
        return super.toString()+","+idPotencia/*sensibilidad*/;
    }
    
    /*Getter and Setter*/
    
    public Double getSensibilidad() {
        return sensibilidad;
    }

    public void setSensibilidad(Double sensibilidad) {
        this.sensibilidad = sensibilidad;
    }
    
}