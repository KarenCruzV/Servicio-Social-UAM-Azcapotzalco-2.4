
package optiuam.bc.modelo;

public class Fibra extends Componente {
    
    /*Attribute Declaration*/
    
    private int longitudOnda; // 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window
    private int mode; // 0 monomodo | 1 multimodo
    private int tipo; // 0 smf28 | 1 mm50 | 2 otro
    private double longitud_km;// Fibra optic cable length
    private double dispersion;// smf28 1310 = 0 1550 = 18
                              // mm50 1310 = 3.5
                              // other any value for both waves
    
    private double atenuacion; // for smf28 0.32 1310 nm window | 0.18 1550 nm window db/km
                               //for mm50 // 1550 <=.36 dB/KM
    
    
    /*Constructor*/

    public Fibra() {
    }

    public Fibra(int wavelength, int mode, int type, double km_length, double attenuation, 
            double dispersion, String name, int id) {
        this.longitudOnda = wavelength;
        this.mode = mode;
        this.tipo = type;
        this.longitud_km = km_length;
        this.dispersion = dispersion;
        this.atenuacion = attenuation;
        this.nombre = name;
        this.id = id;
    }
    
    
    /*Getter and Setter*/

    public int getLongitudOnda() {
        return longitudOnda;
    }

    public void setLongitudOnda(int longitudOnda) {
        this.longitudOnda = longitudOnda;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getLongitud_km() {
        return longitud_km;
    }

    public void setLongitud_km(double longitud_km) {
        this.longitud_km = longitud_km;
    }

    public double getDispersion() {
        return dispersion;
    }

    public void setDispersion(double dispersion) {
        this.dispersion = dispersion;
    }

    public double getAtenuacion() {
        return atenuacion;
    }

    public void setAtenuacion(double atenuacion) {
        this.atenuacion = atenuacion;
    }
    
    
    /*toString*/

    @Override
    public String toString() {
        return super.toString() +  ", " + longitudOnda + ", " + mode + ", " 
                + tipo + ", " + longitud_km + ", " + dispersion + ", " + atenuacion;
    }
    
}