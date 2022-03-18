/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.model;

public class Fiber extends Component {
    
    /*Attribute Declaration*/
    
    private int wavelength; // 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window
    private int mode; // 0 singlemode | 1 multimode
    private int type; // 0 smf28 | 1 mm50 | 2 otro
    private double km_length;// Fiber optic cable length
    private double dispersion;// smf28 1310 = 0 1550 = 18
                              // mm50 1310 = 3.5
                              // other any value for both waves
    
    private double attenuation; // for smf28 0.32 1310 nm window | 0.18 1550 nm window db/km
                               //for mm50 // 1550 <=.36 dB/KM
    
    
    /*Constructor*/

    public Fiber() {
    }

    public Fiber(int wavelength, int mode, int type, double km_length, double attenuation, 
            double dispersion, String name, int id) {
        this.wavelength = wavelength;
        this.mode = mode;
        this.type = type;
        this.km_length = km_length;
        this.dispersion = dispersion;
        this.attenuation = attenuation;
        this.name = name;
        this.id = id;
    }
    
    
    /*Getter and Setter*/

    public int getWavelength() {
        return wavelength;
    }

    public void setWavelength(int wavelength) {
        this.wavelength = wavelength;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getKm_length() {
        return km_length;
    }

    public void setKm_length(double km_length) {
        this.km_length = km_length;
    }

    public double getDispersion() {
        return dispersion;
    }

    public void setDispersion(double dispersion) {
        this.dispersion = dispersion;
    }

    public double getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(double attenuation) {
        this.attenuation = attenuation;
    }
    
    
    /*toString*/

    @Override
    public String toString() {
        return super.toString() +  ", " + wavelength + ", " + mode + ", " 
                + type + ", " + km_length + ", " + dispersion + ", " + attenuation;
    }
    
}
