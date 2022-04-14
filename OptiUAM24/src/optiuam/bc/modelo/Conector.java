
package optiuam.bc.modelo;

public class Conector extends Componente {
    
    /*Attribute Declaration*/
    
    private double perdidaInsercion;// 0.5 max na min single fiber| 1.0 max na min multi fiber
    private int longitudOnda; // 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window
    private int modo;
    private char orientacion ='R';
    private int idConector;

    
    /*Constructor*/
    
    public Conector() {
    }
    
    public Conector(String name, int id,String elementoConectado, boolean conectado,int wavelength, int mode, double insertion_loss) {
        this.perdidaInsercion = insertion_loss;
        this.longitudOnda = wavelength;
        this.modo = mode;
        this.elementoConectado=elementoConectado;
        this.conectado=conectado;
        this.nombre = name;
        this.id = id;
    }
    
    
    /*Getter and Setter*/

    public double getPerdidaInsercion() {
        return perdidaInsercion;
    }

    public void setPerdidaInsercion(double perdidaInsercion) {
        this.perdidaInsercion = perdidaInsercion;
    }

    public int getLongitudOnda() {
        return longitudOnda;
    }

    public void setLongitudOnda(int longitudOnda) {
        this.longitudOnda = longitudOnda;
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public char getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(char orientacion) {
        this.orientacion = orientacion;
    }

    public int getIdConector() {
        return idConector;
    }

    public void setIdConector(int idConector) {
        this.idConector = idConector;
    }
    
    
    /*toString*/

    @Override
    public String toString() {
        return super.toString() + "," + longitudOnda + 
                "," + modo+ "," + perdidaInsercion +","+idConector;
    }
    
}