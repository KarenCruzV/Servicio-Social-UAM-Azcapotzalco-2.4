
package optiuam.bc.modelo;

public class Empalme extends Componente {
    
    /*Attribute Declaration*/
    private final double perdidaMaxima = .5;
    private int tipo; // 0 mechanic | 1 fusion
    private double perdidaInsercion;// 50 max na min mechanic | 50 max na min fusion
    private int longitudOnda; // 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window
    private int idEmpalme;
    
    /*Constructor*/

    public Empalme() {
    }

    public Empalme(String name, int id,String elementoConectado, boolean conectado,int type, double insertion_loss, int wavelength) {
        this.tipo = type;
        this.perdidaInsercion = insertion_loss;
        this.longitudOnda = wavelength;
        this.nombre = name;
        this.id = id;
    }
    
    
    /*Getter and Setter*/

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

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

    public double getPerdidaMaxima() {
        return perdidaMaxima;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String name) {
        this.nombre = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmpalme() {
        return idEmpalme;
    }

    public void setIdEmpalme(int idEmpalme) {
        this.idEmpalme = idEmpalme;
    }
    
    
    /*toString*/

    @Override
    public String toString() {
        return super.toString() + "," + tipo + "," + perdidaInsercion + 
                "," + longitudOnda+","+idEmpalme;
    }
    
}