/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.model;

public class Connector extends Component {
    
    /*Attribute Declaration*/
    
    private double insertion_loss;// 0.5 max na min single fiber| 1.0 max na min multi fiber
    private int wavelength; // 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window
    private int mode;
    private char orientation ='R';

    
    /*Constructor*/
    
    public Connector() {
    }

    public Connector(int wavelength, int mode, double insertion_loss, String name, int id) {
        this.insertion_loss = insertion_loss;
        this.wavelength = wavelength;
        this.mode = mode;
        this.name = name;
        this.id = id;
    }
    
    
    /*Getter and Setter*/

    public double getInsertion_loss() {
        return insertion_loss;
    }

    public void setInsertion_loss(double insertion_loss) {
        this.insertion_loss = insertion_loss;
    }

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

    public char getOrientation() {
        return orientation;
    }

    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }
    
    
    /*toString*/

    @Override
    public String toString() {
        return super.toString() + ", " + insertion_loss + ", " + wavelength + 
                ", " + mode + ", " + orientation;
    }
    
}
