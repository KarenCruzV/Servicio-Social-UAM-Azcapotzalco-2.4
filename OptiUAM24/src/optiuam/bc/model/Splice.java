/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optiuam.bc.model;

public class Splice extends Component {
    
    /*Attribute Declaration*/
    private final double maximum_loss = .5;
    private int type; // 0 mechanic | 1 fusion
    private double insertion_loss;// 50 max na min mechanic | 50 max na min fusion
    private int wavelength; // 1360 max 1260 min 1310 nm window | 1580 max 1480 min 1550 nm window
    
    
    /*Constructor*/

    public Splice() {
    }

    public Splice(int type, double insertion_loss, int wavelength, String name, int id) {
        this.type = type;
        this.insertion_loss = insertion_loss;
        this.wavelength = wavelength;
        this.name = name;
        this.id = id;
    }
    
    
    /*Getter and Setter*/

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public double getMaximum_loss() {
        return maximum_loss;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    
    
    /*toString*/

    @Override
    public String toString() {
        return super.toString() + ", " + type + ", " + insertion_loss + 
                ", " + wavelength;
    }
    
}
